package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.Asunto;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosEspecificos;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosEspecificos.DatosExpediente;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosEspecificos.DatosGestor;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosEspecificos.DatosGestoria;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosEspecificos.DatosVehiculo;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosEspecificos.Indicadores;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosEspecificos.Solicitante;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosEspecificos.Tasas;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosEspecificos.Titular;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosGenericos.Interesados;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosInteresado;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.DatosRemitente;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.Destino;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.sega.btv.view.xml.TipoOrganismo;
import org.gestoresmadrid.oegam2comun.sega.utiles.XmlBTVSegaFactory;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.ResultBean;
import trafico.beans.jaxb.pruebaCertificado.DatosFirmados;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildBajaTramitarBtvSega implements Serializable{

	private static final long serialVersionUID = -4920923660527559876L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildBajaTramitarBtvSega.class);
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	public SolicitudPruebaCertificado obtenerSolicitudPruebaCertificado(String alias) {
		SolicitudPruebaCertificado solicitudPruebaCertificado = null;
		trafico.beans.jaxb.pruebaCertificado.ObjectFactory objectFactory = new trafico.beans.jaxb.pruebaCertificado.ObjectFactory();
		solicitudPruebaCertificado = (SolicitudPruebaCertificado) objectFactory.createSolicitudPruebaCertificado();
		DatosFirmados datosFirmados = objectFactory.createDatosFirmados();
		datosFirmados.setAlias(alias);
		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
		return solicitudPruebaCertificado;
	}

	public org.gestoresmadrid.oegam2comun.sega.btv.view.xml.SolicitudRegistroEntrada rellenarXmlTramitacionBtvSega(TramiteTrafBajaDto tramiteTraficoBajaDto) {
		ObjectFactory objectFactorySega = new ObjectFactory();
		SolicitudRegistroEntrada solicitudRegistroEntradaSega = objectFactorySega.createSolicitudRegistroEntrada();
		solicitudRegistroEntradaSega.setDatosFirmados(objectFactorySega.createDatosFirmados());
		/*--------------------- Datos Especificos --------------------------------------*/
		
		solicitudRegistroEntradaSega.getDatosFirmados().setDatosEspecificos(objectFactorySega.createDatosEspecificos());
		DatosEspecificos.DatosColegio datosColegioSega = objectFactorySega.createDatosEspecificosDatosColegio();
		datosColegioSega.setId(tramiteTraficoBajaDto.getContrato().getColegioDto().getColegio());
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setDatosColegio(datosColegioSega);
		
		DatosExpediente datosExpedienteSega = objectFactorySega.createDatosEspecificosDatosExpediente();
		datosExpedienteSega.setJefatura(tramiteTraficoBajaDto.getJefaturaTraficoDto().getJefatura());
		datosExpedienteSega.setMotivoTransmision(MotivoBaja.convertirMotivosBtvXml(tramiteTraficoBajaDto.getMotivoBaja()));
		datosExpedienteSega.setTipoTramite("4");
		datosExpedienteSega.setFechaTramite(utilesFecha.invertirCadenaFechaSinSeparador(utilesFecha.formatoFecha("dd/MM/yyyy", new Date())));
		if (tramiteTraficoBajaDto.getJefaturaTraficoDto().getSucursal() == null || tramiteTraficoBajaDto.getJefaturaTraficoDto().getSucursal().isEmpty()) {
			datosExpedienteSega.setSucursal("");
		} else {
			datosExpedienteSega.setSucursal(tramiteTraficoBajaDto.getJefaturaTraficoDto().getSucursal());
		}
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setDatosExpediente(datosExpedienteSega);

		DatosGestor datosGestorSega = objectFactorySega.createDatosEspecificosDatosGestor();
		datosGestorSega.setDOI(tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getNif());
		datosGestorSega.setFiliacion("FISICA");
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setDatosGestor(datosGestorSega);
		
		DatosGestoria datosGestoriaSega = objectFactorySega.createDatosEspecificosDatosGestoria();
		datosGestoriaSega.setId(tramiteTraficoBajaDto.getContrato().getColegiadoDto().getNumColegiado());
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setDatosGestoria(datosGestoriaSega);
		
		DatosVehiculo datosVehiculoSega = objectFactorySega.createDatosEspecificosDatosVehiculo();
		if (tramiteTraficoBajaDto.getVehiculoDto().getBastidor() != null && !tramiteTraficoBajaDto.getVehiculoDto().getBastidor().isEmpty()){
			if(tramiteTraficoBajaDto.getVehiculoDto().getBastidor().length() > 6){
				int tamBastidor = tramiteTraficoBajaDto.getVehiculoDto().getBastidor().length();
				datosVehiculoSega.setBastidor(tramiteTraficoBajaDto.getVehiculoDto().getBastidor().substring(tamBastidor - 6,tamBastidor));
			}else{
				datosVehiculoSega.setBastidor(tramiteTraficoBajaDto.getVehiculoDto().getBastidor());
			}
		} else {
			datosVehiculoSega.setBastidor("");
		}
		datosVehiculoSega.setDatosMatriculacion(objectFactorySega.createDatosEspecificosDatosVehiculoDatosMatriculacion());
		datosVehiculoSega.getDatosMatriculacion().setMatricula(tramiteTraficoBajaDto.getVehiculoDto().getMatricula());
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setDatosVehiculo(datosVehiculoSega);
		
		Indicadores indicadoresSega = objectFactorySega.createDatosEspecificosIndicadores();
		indicadoresSega.setPaisDestino(tramiteTraficoBajaDto.getPais().getSigla2());
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setIndicadores(indicadoresSega);
		
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setNumeroExpedienteGestor(tramiteTraficoBajaDto.getNumExpediente().toString());
		
		if(tramiteTraficoBajaDto.getAdquiriente() != null && tramiteTraficoBajaDto.getAdquiriente().getPersona() != null
			&& tramiteTraficoBajaDto.getAdquiriente().getPersona().getNif() != null && !tramiteTraficoBajaDto.getAdquiriente().getPersona().getNif().isEmpty()){
			Solicitante solicitanteSega = objectFactorySega.createDatosEspecificosSolicitante();
			solicitanteSega.setDOI(tramiteTraficoBajaDto.getAdquiriente().getPersona().getNif());
			if(tramiteTraficoBajaDto.getRepresentanteAdquiriente() != null && tramiteTraficoBajaDto.getRepresentanteAdquiriente().getPersona() != null 
					&& tramiteTraficoBajaDto.getRepresentanteAdquiriente().getPersona().getNif() != null && !tramiteTraficoBajaDto.getRepresentanteAdquiriente().getPersona().getNif().isEmpty()){
				solicitanteSega.setDatosRepresentante(objectFactorySega.createDatosEspecificosSolicitanteDatosRepresentante());
				solicitanteSega.getDatosRepresentante().setDOI(tramiteTraficoBajaDto.getRepresentanteAdquiriente().getPersona().getNif());
			}
			solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setSolicitante(solicitanteSega);
		}
		
		Tasas tasasSega = objectFactorySega.createDatosEspecificosTasas();
		if(tramiteTraficoBajaDto.getTasa() == null || tramiteTraficoBajaDto.getTasa().getCodigoTasa() == null || tramiteTraficoBajaDto.getTasa().getCodigoTasa().isEmpty()){
			tasasSega.setTasaTramite("N/A");
		} else {
			tasasSega.setTasaTramite(tramiteTraficoBajaDto.getTasa().getCodigoTasa());
		}
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setTasas(tasasSega);
		
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setTextoLegal(org.gestoresmadrid.oegam2comun.sega.btv.view.xml.TipoTextoLegal.ESTE_COLEGIO_DE_GESTORES_ADMINISTRATIVOS_HA_VERIFICADO_QUE_LA_SOLICITUD_DE_BAJA_DEFINITIVA_POR_EXPORTACIÓN_O_TRÁNSITO_COMUNITARIO_PRESENTADA_CUMPLE_TODAS_LAS_OBLIGACIONES_ESTABLECIDAS_EN_EL_REGLAMENTO_GENERAL_DE_VEHÍCULOS_Y_RESTO_DE_NORMATIVA_APLICABLE_ASÍ_COMO_LAS_DERIVADAS_DE_LA_APLICACIÓN_DE_LAS_INSTRUCCIONES_DE_LA_DIRECCIÓN_GENERAL_DE_TRÁFICO_VIGENTES_EN_EL_MOMENTO_DE_LA_SOLICITUD_ENTRE_ELLAS_LA_OBLIGACIÓN_DE_LIQUIDAR_ANTE_EL_DEPARTAMENTO_DE_ADUANAS_LA_LEGAL_EXPORTACIÓN_DEL_VEHÍCULO_EN_EL_CASO_DE_BAJAS_DEFINITIVAS_POR_EXPORTACIÓN_DUA);
		
		Titular titularSega = objectFactorySega.createDatosEspecificosTitular();
		titularSega.setDOI(tramiteTraficoBajaDto.getTitular().getPersona().getNif());
		if(tramiteTraficoBajaDto.getRepresentanteTitular() != null && tramiteTraficoBajaDto.getRepresentanteTitular().getPersona() != null
			&& tramiteTraficoBajaDto.getRepresentanteTitular().getPersona().getNif() != null && !tramiteTraficoBajaDto.getRepresentanteTitular().getPersona().getNif().isEmpty()){
			titularSega.setDatosRepresentante(objectFactorySega.createDatosEspecificosTitularDatosRepresentante());
			titularSega.getDatosRepresentante().setDOI(tramiteTraficoBajaDto.getRepresentanteTitular().getPersona().getNif());
		}
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setTitular(titularSega);
		
		byte[] firmaGestor = solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().getFirmaGestor();
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setFirmaGestor(firmaGestor);
		
		/*--------------------- Datos Genericos --------------------------------------*/
		
		solicitudRegistroEntradaSega.getDatosFirmados().setDatosGenericos(objectFactorySega.createDatosGenericos());
		
		
		Asunto asunto = new Asunto();
			asunto.setCodigo("BTETC");
			asunto.setDescripcion("Solicitud de Baja Telemática por Exportación o Tránsito Comunitario");	
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosGenericos().setAsunto(asunto);
		
		Destino destino = new Destino();
    	destino.setCodigo("101001");
    	destino.setDescripcion("DGT - Vehículos");
    	solicitudRegistroEntradaSega.getDatosFirmados().getDatosGenericos().setDestino(destino);
	
    	solicitudRegistroEntradaSega.getDatosFirmados().getDatosGenericos().setOrganismo(TipoOrganismo.DGT);
		
		int coma = tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getApellidosNombre().indexOf(",");
		String nombreColegiado = tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getApellidosNombre().substring(coma + 1,
				tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getApellidosNombre().length());
		String apeColegiado = tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getApellidosNombre().substring(0, coma);
		
		Interesados interesado = objectFactorySega.createDatosGenericosInteresados();
		DatosInteresado datosInteresadoSega = objectFactorySega.createDatosInteresado();
		datosInteresadoSega.setApellidos(apeColegiado);
		datosInteresadoSega.setCorreoElectronico(tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getCorreoElectronico());
		datosInteresadoSega.setDocumentoIdentificacion(objectFactorySega.createDatosInteresadoDocumentoIdentificacion());
		datosInteresadoSega.getDocumentoIdentificacion().setNumero(tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getNif());
		datosInteresadoSega.setNombre(nombreColegiado);
		interesado.setInteresado(datosInteresadoSega);
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosGenericos().setInteresados(interesado);
		
		DatosRemitente datosRemitenteSega = objectFactorySega.createDatosRemitente();
		datosRemitenteSega.setCorreoElectronico(tramiteTraficoBajaDto.getContrato().getColegioDto().getCorreoElectronico());
		datosRemitenteSega.setDocumentoIdentificacion(objectFactorySega.createDatosRemitenteDocumentoIdentificacion());
		datosRemitenteSega.getDocumentoIdentificacion().setNumero(tramiteTraficoBajaDto.getContrato().getColegioDto().getCif());
		datosRemitenteSega.setNombre(tramiteTraficoBajaDto.getContrato().getColegioDto().getNombre());
		solicitudRegistroEntradaSega.getDatosFirmados().getDatosGenericos().setRemitente(datosRemitenteSega);
		solicitudRegistroEntradaSega.setVersion("1.0");
		byte[] firma = solicitudRegistroEntradaSega.getFirma();
		solicitudRegistroEntradaSega.setFirma(firma);
		
		return solicitudRegistroEntradaSega;
	}

	public ResultBean firmaColegiadoSega(org.gestoresmadrid.oegam2comun.sega.btv.view.xml.SolicitudRegistroEntrada solicitudRegistroEntradaSega, ColegiadoDto colegiadoDto) {
		ResultBean resultado = new ResultBean(false);
		try {
			byte[] xmlFirmado = firmarXml(solicitudRegistroEntradaSega,colegiadoDto.getAlias());
			if (xmlFirmado != null) {
				try {
					solicitudRegistroEntradaSega.getDatosFirmados().getDatosEspecificos().setFirmaGestor(new String(Base64.encodeBase64(xmlFirmado), "UTF-8").getBytes());
				} catch (UnsupportedEncodingException e) {
					log.error("Ha sucedido un error a la hora de codificar en Base 64 el la firma del colegiado, error: ", e);
					resultado.setError(true);
					resultado.setMensaje("Ha sucedido un error a la hora de codificar en Base 64 el la firma del colegiado.");
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Ha surgido un error a la hora de generar el xml para firmar el colegiado.");
			}
		} catch (Exception e) {
			log.error("Ha surgido un error a la hora de firmar el colegiado el tag de datos especificos, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha surgido un error a la hora de firmar el colegiado el tag de datos especificos.");
		}
		return resultado;
	}
	
	private byte[] firmarXml(org.gestoresmadrid.oegam2comun.sega.btv.view.xml.SolicitudRegistroEntrada solicitudRegistroEntradaSega, String alias) throws UnsupportedEncodingException {
		byte[] xmlFirmado = null;
		String xmlAfirmar = getXmlAfirmarPorTag(solicitudRegistroEntradaSega);
		if (xmlAfirmar != null) {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			byte[] datosAfirmar = new String(xmlAfirmar.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");
			xmlFirmado = utilesViafirma.firmarXmlPorAlias(datosAfirmar, alias);
		}
		return xmlFirmado;
	}

	
	private String getXmlAfirmarPorTag(org.gestoresmadrid.oegam2comun.sega.btv.view.xml.SolicitudRegistroEntrada solicitudRegistroEntradaSega) {
		XmlBTVSegaFactory xmlBTVFactorySega = new XmlBTVSegaFactory();
		String xml = xmlBTVFactorySega.toXML(solicitudRegistroEntradaSega);
		// Quitamos los namespaces porque da error
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");
		// Obtenemos los datos que realmente se tienen que firmar
		int inicio = xml.indexOf("<Datos_Firmados>") + 16;
		int fin = xml.indexOf("</Datos_Firmados>");
		String tagAfirmar = xml.substring(inicio, fin);

		String encodedAFirmar = null;
		try {
			encodedAFirmar = utiles.doBase64Encode(tagAfirmar.getBytes("UTF-8"));
			encodedAFirmar = encodedAFirmar.replaceAll("\n", "");
			if (log.isInfoEnabled()) {
				log.info("BTV ENCODED A FIRMAR: " + encodedAFirmar);
			}
		} catch (Exception e) {
			log.error("Error al codificar en base 64 el UTF-8");
			encodedAFirmar = "";
		}
		String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
		return xmlAFirmar;
	}

	public ResultBean firmaColegioSega(org.gestoresmadrid.oegam2comun.sega.btv.view.xml.SolicitudRegistroEntrada solicitudRegistroEntradaSega, String alias) {
		ResultBean resultado = new ResultBean(false);
		try {
			byte[] xmlFirmado = firmarXml(solicitudRegistroEntradaSega, alias);
			if (xmlFirmado != null) {
				try {
					solicitudRegistroEntradaSega.setFirma(new String(Base64.encodeBase64(xmlFirmado), "UTF-8").getBytes());
				} catch (UnsupportedEncodingException e) {
					log.error("Ha sucedido un error a la hora de codificar en Base 64 el la firma del colegio, error: ", e);
					resultado.setError(true);
					resultado.setMensaje("Ha sucedido un error a la hora de codificar en Base 64 el la firma del colegio.");
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Ha sucedido un error a la hora de firmar los datos por parte del colegio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de firmar el colegio la petición de baja telematica, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de firmar el colegio la petición de baja telematica.");
		}
		return resultado;
	}

	public ResultBean validarXmlSega(org.gestoresmadrid.oegam2comun.sega.btv.view.xml.SolicitudRegistroEntrada solicitudRegistroEntradaSega, String nombreXml) {
		ResultBean resultado = new ResultBean(false);
		File fichero = new File(nombreXml);
		try {
			XmlBTVSegaFactory xmlBTVFactorySega = new XmlBTVSegaFactory();
			Marshaller marshaller = (Marshaller) xmlBTVFactorySega.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudRegistroEntradaSega, new FileOutputStream(nombreXml));
			// Lo validamos contra el XSD
			try {
				xmlBTVFactorySega.validarXMLBTV(fichero);
			} catch (OegamExcepcion e) {
				resultado.setError(true);
				resultado.setMensaje("Error en la validacion del expediente: " + e.getMensajeError1());
			}
			if(!resultado.getError()){
				resultado.addAttachment(ServicioTramiteTraficoBaja.XML, xmlBTVFactorySega.toXML(solicitudRegistroEntradaSega));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el xml con el xsd de validación, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el xml con el xsd de validación");
		}
		if(fichero.exists()){
			fichero.delete();
		}
		return resultado;
	}

}
