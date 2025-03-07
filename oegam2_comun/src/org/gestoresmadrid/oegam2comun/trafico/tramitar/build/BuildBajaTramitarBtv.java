package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosEspecificos.DatosColegio;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosEspecificos.DatosExpediente;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosEspecificos.DatosGestor;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosEspecificos.DatosGestoria;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosEspecificos.Indicadores;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosEspecificos.Solicitante;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosEspecificos.Tasas;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosEspecificos.Titular;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosInteresado;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosRemitente;
import org.gestoresmadrid.oegam2comun.btv.view.xml.DatosVehiculo;
import org.gestoresmadrid.oegam2comun.btv.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.btv.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.btv.view.xml.TipoOrganismo;
import org.gestoresmadrid.oegam2comun.btv.view.xml.TipoTextoLegal;
import org.gestoresmadrid.oegam2comun.btv.view.xml.TipoTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.utiles.XmlBTVFactory;
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
public class BuildBajaTramitarBtv implements Serializable{

	private static final long serialVersionUID = 4799480280715635980L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildBajaTramitarBtv.class);
	
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


	public SolicitudRegistroEntrada rellenarXmlTramitacionBtv(TramiteTrafBajaDto tramiteTraficoBajaDto) {
		ObjectFactory objectFactory = new ObjectFactory();
		SolicitudRegistroEntrada solicitudRegistroEntrada = objectFactory.createSolicitudRegistroEntrada();
		solicitudRegistroEntrada.setDatosFirmados(objectFactory.createDatosFirmados());
		/*--------------------- Datos Especificos --------------------------------------*/
		
		solicitudRegistroEntrada.getDatosFirmados().setDatosEspecificos(objectFactory.createDatosEspecificos());
		DatosColegio datosColegio = objectFactory.createDatosEspecificosDatosColegio();
		datosColegio.setId(tramiteTraficoBajaDto.getContrato().getColegioDto().getColegio());
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosColegio(datosColegio);
		
		DatosExpediente datosExpediente = objectFactory.createDatosEspecificosDatosExpediente();
		datosExpediente.setJefatura(tramiteTraficoBajaDto.getJefaturaTraficoDto().getJefatura());
		datosExpediente.setMotivoTransmision(MotivoBaja.convertirMotivosBtvXml(tramiteTraficoBajaDto.getMotivoBaja()));
		datosExpediente.setTipoTramite(TipoTramite.TIPO_TRAMITE);
		datosExpediente.setFechaTramite(utilesFecha.invertirCadenaFechaSinSeparador(utilesFecha.formatoFecha("dd/MM/yyyy", new Date())));
		if (tramiteTraficoBajaDto.getJefaturaTraficoDto().getSucursal() == null || tramiteTraficoBajaDto.getJefaturaTraficoDto().getSucursal().isEmpty()) {
			datosExpediente.setSucursal("");
		} else {
			datosExpediente.setSucursal(tramiteTraficoBajaDto.getJefaturaTraficoDto().getSucursal());
		}
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosExpediente(datosExpediente);

		DatosGestor datosGestor = objectFactory.createDatosEspecificosDatosGestor();
		datosGestor.setDOI(tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getNif());
		datosGestor.setFiliacion("FISICA");
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosGestor(datosGestor);
		
		DatosGestoria datosGestoria = objectFactory.createDatosEspecificosDatosGestoria();
		datosGestoria.setId(tramiteTraficoBajaDto.getContrato().getColegiadoDto().getNumColegiado());
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosGestoria(datosGestoria);
		
		DatosVehiculo datosVehiculo = objectFactory.createDatosVehiculo();
		if (tramiteTraficoBajaDto.getVehiculoDto().getBastidor() != null && !tramiteTraficoBajaDto.getVehiculoDto().getBastidor().isEmpty()){
			if(tramiteTraficoBajaDto.getVehiculoDto().getBastidor().length() > 6){
				int tamBastidor = tramiteTraficoBajaDto.getVehiculoDto().getBastidor().length();
				datosVehiculo.setBastidor(tramiteTraficoBajaDto.getVehiculoDto().getBastidor().substring(tamBastidor - 6,tamBastidor));
			}else{
				datosVehiculo.setBastidor(tramiteTraficoBajaDto.getVehiculoDto().getBastidor());
			}
		} else {
			datosVehiculo.setBastidor("");
		}
		datosVehiculo.setDatosMatriculacion(objectFactory.createDatosMatriculacion());
		datosVehiculo.getDatosMatriculacion().setMatricula(tramiteTraficoBajaDto.getVehiculoDto().getMatricula());
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosVehiculo(datosVehiculo);
		
		Indicadores indicadores = objectFactory.createDatosEspecificosIndicadores();
		indicadores.setPaisDestino(tramiteTraficoBajaDto.getPais().getSigla2());
		
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setNumeroExpedienteGestor(tramiteTraficoBajaDto.getNumExpediente().toString());
		
		if(tramiteTraficoBajaDto.getAdquiriente() != null && tramiteTraficoBajaDto.getAdquiriente().getPersona() != null
			&& tramiteTraficoBajaDto.getAdquiriente().getPersona().getNif() != null && !tramiteTraficoBajaDto.getAdquiriente().getPersona().getNif().isEmpty()){
			Solicitante solicitante = objectFactory.createDatosSolicitante();
			solicitante.setDoi(tramiteTraficoBajaDto.getAdquiriente().getPersona().getNif());
			if(tramiteTraficoBajaDto.getRepresentanteAdquiriente() != null && tramiteTraficoBajaDto.getRepresentanteAdquiriente().getPersona() != null 
					&& tramiteTraficoBajaDto.getRepresentanteAdquiriente().getPersona().getNif() != null && !tramiteTraficoBajaDto.getRepresentanteAdquiriente().getPersona().getNif().isEmpty()){
				solicitante.setDatosRepresentante(objectFactory.createDatosRepresentante());
				solicitante.getDatosRepresentante().setDoi(tramiteTraficoBajaDto.getRepresentanteAdquiriente().getPersona().getNif());
			}
			solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setSolicitante(solicitante);
			indicadores.setAcreditacionPosesion("SI");
		}
		
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setIndicadores(indicadores);
		
		
		Tasas tasas = objectFactory.createDatosEspecificosTasas();
		if(tramiteTraficoBajaDto.getTasa() != null && tramiteTraficoBajaDto.getTasa().getCodigoTasa() != null 
				&& !tramiteTraficoBajaDto.getTasa().getCodigoTasa().isEmpty()){
			tasas.setTasaTramite(tramiteTraficoBajaDto.getTasa().getCodigoTasa());
		} else {
			tasas.setTasaTramite("");
		}
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setTasas(tasas);
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setTextoLegal(TipoTextoLegal.TEXTO_LEGAL);
		
		Titular titular = objectFactory.createDatosTitular();
		titular.setDoi(tramiteTraficoBajaDto.getTitular().getPersona().getNif());
		if(tramiteTraficoBajaDto.getRepresentanteTitular() != null && tramiteTraficoBajaDto.getRepresentanteTitular().getPersona() != null
			&& tramiteTraficoBajaDto.getRepresentanteTitular().getPersona().getNif() != null && !tramiteTraficoBajaDto.getRepresentanteTitular().getPersona().getNif().isEmpty()){
			titular.setDatosRepresentante(objectFactory.createDatosRepresentante());
			titular.getDatosRepresentante().setDoi(tramiteTraficoBajaDto.getRepresentanteTitular().getPersona().getNif());
		}
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setTitular(titular);
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setFirmaGestor("");
		
		/*--------------------- Datos Genericos --------------------------------------*/
		
		solicitudRegistroEntrada.getDatosFirmados().setDatosGenericos(objectFactory.createDatosGenericos());
		
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setAsunto(objectFactory.createAsunto());
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setDestino(objectFactory.createDestino());
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setOrganismo(TipoOrganismo.DGT);
		
		int coma = tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getApellidosNombre().indexOf(",");
		String nombreColegiado = tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getApellidosNombre().substring(coma + 1,
				tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getApellidosNombre().length());
		String apeColegiado = tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getApellidosNombre().substring(0, coma);
		
		DatosInteresado datosInteresados = objectFactory.createDatosInteresado();
		datosInteresados.setInteresado(objectFactory.createInteresado());
		datosInteresados.getInteresado().setApellidos(apeColegiado);
		datosInteresados.getInteresado().setCorreoElectronico(tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getCorreoElectronico());
		datosInteresados.getInteresado().setDocumentoIdentificacion(objectFactory.createDocumentoIdentificacionInteresado());
		datosInteresados.getInteresado().getDocumentoIdentificacion().setNumero(tramiteTraficoBajaDto.getContrato().getColegiadoDto().getUsuario().getNif());
		datosInteresados.getInteresado().setNombre(nombreColegiado);
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setInteresados(datosInteresados);
		
		DatosRemitente datosRemitente = objectFactory.createDatosRemitente();
		datosRemitente.setCorreoElectronico(tramiteTraficoBajaDto.getContrato().getColegioDto().getCorreoElectronico());
		datosRemitente.setDocumentoIdentificacion(objectFactory.createDocumentoIdentificacionRemitente());
		datosRemitente.getDocumentoIdentificacion().setNumero(tramiteTraficoBajaDto.getContrato().getColegioDto().getCif());
		datosRemitente.setNombre(tramiteTraficoBajaDto.getContrato().getColegioDto().getNombre());
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setRemitente(datosRemitente);
		solicitudRegistroEntrada.setVersion("1.0");
		solicitudRegistroEntrada.setFirma("");
		
		return solicitudRegistroEntrada;
	}
	
	


	public ResultBean firmaColegiado(SolicitudRegistroEntrada solicitudRegistroEntrada, ColegiadoDto colegiadoDto) {
		ResultBean resultado = new ResultBean(false);
		try {
			byte[] xmlFirmado = firmarXml(solicitudRegistroEntrada,colegiadoDto.getAlias());
			if (xmlFirmado != null) {
				try {
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setFirmaGestor(new String(Base64.encodeBase64(xmlFirmado), "UTF-8"));
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

	private byte[] firmarXml(SolicitudRegistroEntrada solicitudRegistroEntrada, String alias) throws UnsupportedEncodingException {
		byte[] xmlFirmado = null;
		String xmlAfirmar = getXmlAfirmarPorTag(solicitudRegistroEntrada);
		if (xmlAfirmar != null) {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			byte[] datosAfirmar = new String(xmlAfirmar.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");
			xmlFirmado = utilesViafirma.firmarXmlPorAlias(datosAfirmar, alias);
		}
		return xmlFirmado;
	}
	
	private String getXmlAfirmarPorTag(SolicitudRegistroEntrada solicitudRegistroEntrada) {
		XmlBTVFactory xmlBTVFactory = new XmlBTVFactory();
		String xml = xmlBTVFactory.toXML(solicitudRegistroEntrada);
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
	
	public ResultBean firmaColegio(SolicitudRegistroEntrada solicitudRegistroEntrada, String alias) {
		ResultBean resultado = new ResultBean(false);
		try {
			byte[] xmlFirmado = firmarXml(solicitudRegistroEntrada, alias);
			if (xmlFirmado != null) {
				try {
					solicitudRegistroEntrada.setFirma(new String(Base64.encodeBase64(xmlFirmado), "UTF-8"));
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

	public ResultBean validarXml(SolicitudRegistroEntrada solicitudRegistroEntrada, String nombreXml) {
		ResultBean resultado = new ResultBean(false);
		File fichero = new File(nombreXml);
		try {
			XmlBTVFactory xmlBTVFactory = new XmlBTVFactory();
			Marshaller marshaller = (Marshaller) xmlBTVFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudRegistroEntrada, new FileOutputStream(nombreXml));
			// Lo validamos contra el XSD
			try {
				xmlBTVFactory.validarXMLBTV(fichero);
			} catch (OegamExcepcion e) {
				resultado.setError(true);
				resultado.setMensaje("Error en la validacion del expediente: " + e.getMensajeError1());
			}
			if(!resultado.getError()){
				resultado.addAttachment(ServicioTramiteTraficoBaja.XML, xmlBTVFactory.toXML(solicitudRegistroEntrada));
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
