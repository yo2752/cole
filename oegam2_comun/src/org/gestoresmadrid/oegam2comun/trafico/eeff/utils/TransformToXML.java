package org.gestoresmadrid.oegam2comun.trafico.eeff.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffConsultaDTO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffDTO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffLiberacionDTO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.Asunto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.DatosEspecificos;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.DatosEspecificos.Operaciones;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.DatosEspecificos.Representacion;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.DatosFirmados;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.DatosGenericos;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.Destino;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.Direccion;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.Operacion;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.Remitente;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.Remitente.DocumentoIdentificacion;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.TipoDatos;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.TipoDatos.Diligencia;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.TipoFIR;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.TipoTarjeta;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.viafirma.cliente.ViafirmaClient;
import org.viafirma.cliente.ViafirmaClientFactory;
import org.viafirma.cliente.exception.InternalException;
import org.viafirma.cliente.firma.TypeFile;
import org.viafirma.cliente.firma.TypeFormatSign;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

import trafico.utiles.UtilesConversiones;
import utilidades.web.OegamExcepcion;

public class TransformToXML {
	
	private static final Logger log = Logger.getLogger(TransformToXML.class);
	private static ObjectFactory objFactory = new ObjectFactory();
	
	public static String transformarDtoLibXml(EeffDTO eeffLib) throws ParseException, OegamExcepcion, JAXBException, SAXException, IOException, InternalException{
		SolicitudRegistroEntrada solicitud = objFactory.createSolicitudRegistroEntrada();
		
		DatosFirmados datosFirmados= objFactory.createDatosFirmados();
		DatosGenericos datosGenericos = objFactory.createDatosGenericos();
		DatosEspecificos datosEspecificos = objFactory.createDatosEspecificos();
		
		//Datos Genericos
		datosGenericos.setRemitente(rellenarRemitente(eeffLib));
//		datosGenericos.setInteresados(rellenarInteresados((EeffLiberacionDTO) eeffLib));//No se utiliza por eso esta comentado
		datosGenericos.setAsunto(rellenarAsunto());
		datosGenericos.setDestino(rellenarDestino());
		datosEspecificos.setRepresentacion(rellenarRepresentacion(eeffLib));
		//Datos Especificos
		if(eeffLib.getClass().equals(EeffLiberacionDTO.class)){
			datosEspecificos.setOperaciones(rellenarOperaciones((EeffLiberacionDTO) eeffLib));
		}else{
			datosEspecificos.setOperaciones(rellenarOperaciones((EeffConsultaDTO) eeffLib));
		}
		
		
		datosFirmados.setDatosGenericos(datosGenericos);
		datosFirmados.setDatosEspecificos(datosEspecificos);
		
		solicitud.setDatosFirmados(datosFirmados);
		
		//Pasando un objeto a xml. 
		XStream xstream = new XStream();
		xstream.processAnnotations(SolicitudRegistroEntrada.class);
		String xmlXStream = xstream.toXML(solicitud);
		
		xmlXStream = xmlXStream.replace("__", "_");
		xmlXStream = xmlXStream.replaceAll("\n *<", "<");
		//Firmar el documento.
		String xmlFirmado= firmarXml(xmlXStream, eeffLib.getNumColegiado().toString());
		
		return xmlFirmado;
	}
	
	private static Remitente rellenarRemitente(EeffDTO eeff){
		//Gestor 
		Remitente remitente = objFactory.createRemitente();
		
		ContratoVO contrato = getUtilesColegiado().getDetalleColegiado(eeff.getNumColegiado().toString());
		if(contrato != null && contrato.getRazonSocial() != null){
			String[] nombreCompletoUsuario = contrato.getRazonSocial().split(",");
			if (nombreCompletoUsuario.length>1){
				remitente.setNombre(nombreCompletoUsuario[1]);
				remitente.setApellidos(nombreCompletoUsuario[0]);
			} else {
				remitente.setNombre(nombreCompletoUsuario[0]);
				remitente.setApellidos(nombreCompletoUsuario[0]);
			}
		}
		if(contrato != null && contrato.getCif()!= null){
			remitente.setDocumentoIdentificacion(crearNif(contrato.getCif()));
		}
		remitente.setCorreoElectronico(null);

		return remitente;
	}
	
	private static Asunto rellenarAsunto(){
		Asunto asunto = objFactory.createAsunto();
		asunto.setCodigo("OBCT");
		asunto.setDescripcion("Operaciones Básicas de gestión de Custodio virtual de Tarjetas ITV");
		return asunto;
	}
	
	private static Destino rellenarDestino(){
		Destino destino = objFactory.createDestino();
		destino.setCodigo("101001");
		destino.setDescripcion("DGT-Vehículos");
		return destino;
	}
	
	private static Operaciones rellenarOperaciones(EeffLiberacionDTO eeffLib) throws ParseException{
		Operaciones operaciones = new Operaciones();
		Operacion operacion = new Operacion(); //objFactory.createOperacion();
		operacion.setCodigoOperacion("EEFF_LIBERACION");
		operacion.setDatos(rellenarDatos(eeffLib));
		operacion.setFir(rellenarFir(eeffLib));
		operacion.setTarjeta(rellenarTarjeta(eeffLib));
		
		operaciones.setOperacion(operacion);
		
		return operaciones;
	}
	
	
	private static Operaciones rellenarOperaciones(EeffConsultaDTO eeffConsulta) throws ParseException{
		Operaciones operaciones = new Operaciones();
		Operacion operacion = new Operacion(); //objFactory.createOperacion();
		operacion.setCodigoOperacion("EEFF_CONSULTA");
		operacion.setFir(rellenarFir(eeffConsulta));
		operacion.setTarjeta(rellenarTarjeta(eeffConsulta));
		
		operaciones.setOperacion(operacion);
		
		return operaciones;
	}
	
	private static Representacion rellenarRepresentacion(EeffDTO eeffDTO){
		Representacion representado = new Representacion();
		representado.setRepresentado(eeffDTO.getNifRepresentado());
		
		return representado; 
	}
	
	private static TipoDatos rellenarDatos(EeffLiberacionDTO eeffLib) throws ParseException{
		TipoDatos tipoDatos = objFactory.createTipoDatos();
		tipoDatos.setDiligencia(rellenarDiligencia(eeffLib));
		
		return tipoDatos;
	}
	private static Diligencia rellenarDiligencia(EeffLiberacionDTO eeffLib) throws ParseException{
		Diligencia diligencia = objFactory.createTipoDatosDiligencia();
		UtilesConversiones utilesConv = ContextoSpring.getInstance().getBean(UtilesConversiones.class);
		Direccion direccion = objFactory.createDireccion();
		if(eeffLib.getTipoVia()!=null && !eeffLib.getTipoVia().equals(""))
			direccion.setTipoVia(eeffLib.getTipoVia());
		if(eeffLib.getNombreVia()!=null && !eeffLib.getNombreVia().equals(""))
			direccion.setNombreVia(eeffLib.getNombreVia());
		if(eeffLib.getNumero()!=null && !eeffLib.getNumero().equals(""))
			direccion.setNum(eeffLib.getNumero());
		if(eeffLib.getProvincia()!=null && !eeffLib.getProvincia().equals("")){
			
			direccion.setProvincia(utilesConv.getIdProvinciaFromNombre(eeffLib.getProvincia()));
		
		if(eeffLib.getMunicipio()!=null &&!eeffLib.getMunicipio().equals("")){
			
			direccion.setMunicipio(utilesConv.getCodigoMunicipo(eeffLib.getIdDireccion()));
		}
		}
		if(eeffLib.getBloque()!=null && !eeffLib.getBloque().equals(""))
			direccion.setBloque(eeffLib.getBloque());
		if(eeffLib.getCodPostal()!=null && !eeffLib.getCodPostal().equals(""))
			direccion.setCodigoPostal(eeffLib.getCodPostal());
		if(eeffLib.getEscalera()!=null && !eeffLib.getEscalera().equals(""))
			direccion.setEscalera(eeffLib.getEscalera());
		if(eeffLib.getPiso()!=null && !eeffLib.getPiso().equals(""))
			direccion.setPiso(eeffLib.getPiso());
		if(eeffLib.getPuerta()!=null && !eeffLib.getPuerta().equals(""))
			direccion.setPuerta(eeffLib.getPuerta());
			
		diligencia.setDiDireccion(direccion);
		if(eeffLib.getNif()!=null && !eeffLib.getNif().equals(""))
			diligencia.setDiDni(eeffLib.getNif());
		
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
		if(eeffLib.getFechaFactura()!=null && eeffLib.getFechaFactura().getFecha()!=null)
			diligencia.setDiFechaFactura(dataFormat.format(eeffLib.getFechaFactura().getFecha()));
		if(eeffLib.getImporte()!=null && !eeffLib.getImporte().equals(""))
			diligencia.setDiImporte(new BigDecimal( eeffLib.getImporte()));
		if(eeffLib.getNombre()!=null && !eeffLib.getNombre().equals(""))
			diligencia.setDiNombre(eeffLib.getNombre());
		if(eeffLib.getNumFactura()!=null && ! eeffLib.getNumFactura().equals(""))
			diligencia.setDiNumeroFactura(eeffLib.getNumFactura());
		if(eeffLib.getPrimerApellido()!=null && !eeffLib.getPrimerApellido().equals(""))
			diligencia.setDiPrimerApellidoRazonSocial(eeffLib.getPrimerApellido());
		if(eeffLib.getSegundoApellido()!=null && !eeffLib.getSegundoApellido().equals(""))
			diligencia.setDiSegundoApellido(eeffLib.getSegundoApellido());
		
		return diligencia;
	}
	
	private static TipoFIR rellenarFir(EeffDTO eeffLib){
		TipoFIR fir = objFactory.createTipoFIR();
		
		if(eeffLib.getFirCif()!=null && !eeffLib.getFirCif().equals(""))
			fir.setCif(eeffLib.getFirCif());
		if(eeffLib.getFirMarca()!=null && !eeffLib.getFirMarca().equals(""))
			fir.setMarca(eeffLib.getFirMarca());
		
		return fir;
	}
	
	private static TipoTarjeta rellenarTarjeta(EeffDTO eeff){
		TipoTarjeta tipoTarjeta = objFactory.createTipoTarjeta();
		if(eeff.getTarjetaBastidor()!=null && !eeff.getTarjetaBastidor().equals(""))
			tipoTarjeta.setBastidor(eeff.getTarjetaBastidor());
		if(eeff.getTarjetaNive()!=null && !eeff.getTarjetaNive().equals(""))
			tipoTarjeta.setNive(eeff.getTarjetaNive());
		return tipoTarjeta;
	}
	
	private static DocumentoIdentificacion crearNif(String nif){
		DocumentoIdentificacion documentoId = objFactory.createRemitenteDocumentoIdentificacion();
		documentoId.setNumero(nif);
		return documentoId;
	}
	
	private static String firmarXml(String xml, String numColegiado) throws InternalException, UnsupportedEncodingException{
		String idFirma;
		String xmlFirmado; 
		ViafirmaClient viafirmaClient =getClienteRest(); 
		byte[] Afirmar=xml.getBytes("UTF-8");
		
		String aliasColegiado= getUtilesColegiado().getAlias(numColegiado); 
		String password = "";
		
		idFirma=viafirmaClient.signByServerWithTypeFileAndFormatSign(
				"prueba.xml", Afirmar, aliasColegiado, password, TypeFile.XML, TypeFormatSign.XADES_T_ENVELOPED);
		
		byte[] documentoCustodiado;
	
		documentoCustodiado = viafirmaClient.getDocumentoCustodiado(idFirma);
		
		xmlFirmado=new String(documentoCustodiado,"UTF-8");
	
		return xmlFirmado;
	}
	//cambiar a utilies viafirma 
	private static ViafirmaClient getClienteRest() {
		try {
			if(!ViafirmaClientFactory.isInit()){	
				ViafirmaClientFactory.init("http://192.168.50.27/viafirma",
							"http://192.168.50.27/viafirma/rest");
				}
		} catch (Exception e2) {
			log.error("Error en ViaFirma"+e2);
		}
			
			ViafirmaClient viafirmaClient = ViafirmaClientFactory.getInstance();
		return viafirmaClient;
	}
	
	private static UtilesColegiado getUtilesColegiado() {
		return ContextoSpring.getInstance().getBean(UtilesColegiado.class);
	}

}
