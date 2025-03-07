package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.sega.checkCtit.view.xml.DatosFirmados;
import org.gestoresmadrid.oegam2comun.sega.checkCtit.view.xml.DatosFirmados.ListaAdquirientes;
import org.gestoresmadrid.oegam2comun.sega.checkCtit.view.xml.DatosFirmados.ListaTransmitentes;
import org.gestoresmadrid.oegam2comun.sega.checkCtit.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.sega.checkCtit.view.xml.SolicitudTramite;
import org.gestoresmadrid.oegam2comun.sega.checkCtit.view.xml.TipoTramite;
import org.gestoresmadrid.oegam2comun.sega.utiles.XmlCheckCTITSegaFactory;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import trafico.utiles.XMLPruebaCertificado;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildCheckCtitSega implements Serializable{

	private static final long serialVersionUID = 5817472989605217242L;

	private ILoggerOegam log = LoggerOegam.getLogger(BuildCheckCtitSega.class);

	private static final String VERSION_1_0 = "1.0";
	private static final String UTF_8 = "UTF-8";

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	Utiles utiles;

	public ResultadoCtitBean obtenerSolicitudRegistroEntrada(TramiteTraficoTransmisionBean tramite, BigDecimal idContrato) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		ObjectFactory objFactory = new ObjectFactory();
		SolicitudTramite solicitudTramite = objFactory.createSolicitudTramite();
		DatosFirmados datosFirmados = objFactory.createDatosFirmados();
		// Evitamos el acceso a sesión
		ContratoDto contratoDto = servicioContrato.getContratoDto(tramite.getTramiteTraficoBean().getIdContrato());
		datosFirmados.setNIFGestor(contratoDto.getColegiadoDto().getUsuario().getNif());
		datosFirmados.setMatricula(tramite.getTramiteTraficoBean().getVehiculo().getMatricula());
		datosFirmados.setListaTransmitentes(new ListaTransmitentes());
		datosFirmados.getListaTransmitentes().getDOI().add(tramite.getTransmitenteBean().getPersona().getNif());
		// Cotitulares transmitentes
		if (tramite.getPrimerCotitularTransmitenteBean() != null && tramite.getPrimerCotitularTransmitenteBean().getPersona() != null && tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif() != null && !tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif().isEmpty()) {
			datosFirmados.getListaTransmitentes().getDOI().add(tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif());
		}
		if (tramite.getSegundoCotitularTransmitenteBean() != null && tramite.getSegundoCotitularTransmitenteBean().getPersona() != null && tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif() != null && !tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif().isEmpty()) {
			datosFirmados.getListaTransmitentes().getDOI().add(tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif());
		}
		datosFirmados.setListaAdquirientes(new ListaAdquirientes());
		TipoTransferencia tipoTransferencia = TipoTransferencia.convertir(tramite.getTipoTransferencia().getValorEnum());
		if (!TipoTransferencia.tipo5.equals(tipoTransferencia)) {
			datosFirmados.getListaAdquirientes().getDOI().add(tramite.getAdquirienteBean().getPersona().getNif());
		} else {
			datosFirmados.getListaAdquirientes().getDOI().add(tramite.getPoseedorBean().getPersona().getNif());
		}
		// Antes era tipo1
		if (TipoTransferencia.tipo1.equals(tipoTransferencia) 
			|| TipoTransferencia.tipo2.equals(tipoTransferencia) 
			|| TipoTransferencia.tipo3.equals(tipoTransferencia)) {
			datosFirmados.setTipoTramite(TipoTramite.CTI.toString());
		} else if (TipoTransferencia.tipo4.equals(tipoTransferencia)) {
			datosFirmados.setTipoTramite(TipoTramite.NOT.toString());
		} else if (TipoTransferencia.tipo5.equals(tipoTransferencia)) {
			datosFirmados.setTipoTramite(TipoTramite.ENT.toString());
		}
		datosFirmados.setTextoLegal("");
		// Seteamos los DATOS FIRMADOS
		solicitudTramite.setDatosFirmados(datosFirmados);
		// Seteamos la FIRMA
		solicitudTramite.setFirmaGestor(new byte[0]);
		// Seteamos la VERSION
		solicitudTramite.setVersion(VERSION_1_0);
		try {
			resultado = anhadirFirmasCheckCTITHSM(solicitudTramite, contratoDto.getColegiadoDto().getAlias());
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de firmar la peticion, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de realizar la firma.");
		}
		if (!resultado.getError()) {
			solicitudTramite.setFirmaGestor(resultado.getXmlFimado().getBytes());
			String nombreFichero = ConstantesGestorFicheros.NOMBRE_CHECKCTIT + String.valueOf(tramite.getTramiteTraficoBean().getNumExpediente());
			resultado = validacionXSD(solicitudTramite,nombreFichero);
			if(!resultado.getError()){
				resultado = guardarSolicitud(solicitudTramite,tramite,nombreFichero);
			}
		}
		return resultado;
	}

	private ResultadoCtitBean validacionXSD(SolicitudTramite solicitudTramite, String nombreFichero) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			XmlCheckCTITSegaFactory xmlCheckCTITSegaFactory = new XmlCheckCTITSegaFactory();
			Marshaller marshaller = (Marshaller) xmlCheckCTITSegaFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudTramite, new FileOutputStream(nombreFichero));
			File fichero = new File(nombreFichero);
			try {
				xmlCheckCTITSegaFactory.validarXMLCheckCTIT(fichero);
			} catch (OegamExcepcion e) {
				resultado.setError(true);
				resultado.setMensajeError("Error en la validación del expediente: " + e.getMessage());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el xml de envio contra el xsd, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de validar el xml de envío.");
		}
		return resultado;
	}

	private ResultadoCtitBean guardarSolicitud(SolicitudTramite solicitudTramite, TramiteTraficoTransmisionBean tramite, String nombreFichero) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			XmlCheckCTITSegaFactory xmlFactory = new XmlCheckCTITSegaFactory();
			String xmlFirmado = xmlFactory.toXML(solicitudTramite);
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.CHECKCTIT);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.CTITENVIO); 
			ficheroBean.setNombreDocumento(nombreFichero);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(tramite.getTramiteTraficoBean().getNumExpediente()));
			ficheroBean.setSobreescribir(true);
			gestorDocumentos.crearFicheroXml(ficheroBean, XmlCheckCTITSegaFactory.NAME_CONTEXT, solicitudTramite, xmlFirmado, null);
			resultado.setNombreXml(nombreFichero);
		} catch (OegamExcepcion e) {
			resultado.setMensajeError("No se pudo generar el fichero.");
			resultado.setError(Boolean.TRUE);
			log.error("Error al guardar el fichero, error: ", e);
		} catch (Throwable e) {
			resultado.setMensajeError("No se pudo generar el fichero XML.");
			resultado.setError(Boolean.TRUE);
			log.error("Error al guardar el XML de check CTIT en disco, error: ", e);
		}
		return resultado;
	}

	private ResultadoCtitBean anhadirFirmasCheckCTITHSM(SolicitudTramite solicitudTramite, String alias) {
		XmlCheckCTITSegaFactory xmlCheckCTITFactory = new XmlCheckCTITSegaFactory();
		String xml = xmlCheckCTITFactory.toXML(solicitudTramite);
		// Quitamos los namespaces porque da error
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");

		// Obtenemos los datos que realmente se tienen que firmar
		int inicio = xml.indexOf("<Datos_Firmados>") + 16;
		int fin = xml.indexOf("</Datos_Firmados>");
		String datosFirmados = xml.substring(inicio, fin);
		if (log.isInfoEnabled()) {
			log.info("LOG_CHECKCTIT DATOS FIRMADOS " + datosFirmados);
			log.info("Datos a firmar:" + datosFirmados);
		}

		String encodedAFirmar = null;
		try {
			encodedAFirmar = utiles.doBase64Encode(datosFirmados.getBytes(UTF_8)).replaceAll("\n", "");
			if (log.isInfoEnabled()) {
				log.info("LOG_CHECKCTIT ENCODED A FIRMAR: " + encodedAFirmar);
			}
		} catch (Exception e) {
			log.error("Error al codificar en base 64 el UTF-8");
			// Para mantener el funcionamiento igual que en ModeloTransmision
			encodedAFirmar = "";
		}

		String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
		// Se construye el XML que contiene los datos a firmar
		if (log.isInfoEnabled()) {
			log.info("XML a firmar:" + xmlAFirmar);
		}
		return firmarCheckCTIT(xmlAFirmar, alias);
	}

	private ResultadoCtitBean firmarCheckCTIT(String xmlAFirmar, String alias) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		Boolean noEsCertCaducado = comprobarCaducidadCertificado(alias);
		if(noEsCertCaducado){
			String idFirma = null;
			try {
				UtilesViafirma utilesViafirma = new UtilesViafirma();
				idFirma = utilesViafirma.firmarMensajeTransmisionServidor(xmlAFirmar, alias);
				log.info(idFirma);
				if ("ERROR".equals(idFirma)) {
					// Ha fallado el proceso de firma en servidor
					resultado.setMensajeError("Ha ocurrido un error durante el proceso de firma en servidor del tramite");
					resultado.setError(Boolean.TRUE);
				} else if (idFirma != null) {
					String xmlFirmado = utilesViafirma.getDocumentoFirmado(idFirma);
					if(xmlFirmado != null && !xmlFirmado.isEmpty()){
						resultado.setXmlFimado(xmlFirmado);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensajeError("Ha sucedido un error a la hora de recuperar el xml firmado de checkCtit.");
					}
				}
			} catch (Exception e) {
				resultado.setMensajeError("Ha ocurrido un error durante el proceso de firma en servidor del tramite");
				resultado.setError(Boolean.TRUE);
				log.error("Ha sucedido un error a la hora de realizar la firma, error: ",e);
			}
		} else {
			resultado.setMensajeError("El certificado del colegiado del tramite se encuentra actualmente caducado.");
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private Boolean comprobarCaducidadCertificado(String aliasColegiado) {
		boolean esOk = false;
		SolicitudPruebaCertificado solicitudPruebaCertificado = obtenerSolicitudPruebaCertificado(aliasColegiado);
		XMLPruebaCertificado xmlPruebaCertificado = new XMLPruebaCertificado();
		String xml = xmlPruebaCertificado.toXMLSolicitudPruebaCert(solicitudPruebaCertificado);
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.firmarPruebaCertificadoCaducidad(xml, aliasColegiado);
		if (idFirma != null) {
			esOk = true;
		}
		return esOk;
	}

	public SolicitudPruebaCertificado obtenerSolicitudPruebaCertificado(String alias) {
		SolicitudPruebaCertificado solicitudPruebaCertificado = null;
		trafico.beans.jaxb.pruebaCertificado.ObjectFactory objctFactory = new trafico.beans.jaxb.pruebaCertificado.ObjectFactory();
		solicitudPruebaCertificado = (SolicitudPruebaCertificado) objctFactory.createSolicitudPruebaCertificado();
		trafico.beans.jaxb.pruebaCertificado.DatosFirmados datosFirmados = objctFactory.createDatosFirmados();
		datosFirmados.setAlias(alias);
		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
		return solicitudPruebaCertificado;
	}
}