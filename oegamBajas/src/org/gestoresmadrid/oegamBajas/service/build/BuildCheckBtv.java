package org.gestoresmadrid.oegamBajas.service.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegamBajas.checkBtv.view.xml.ObjectFactory;
import org.gestoresmadrid.oegamBajas.checkBtv.view.xml.SolicitudTramite;
import org.gestoresmadrid.oegamBajas.utiles.XmlConsultaBTVFactory;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildCheckBtv implements Serializable{

	private static final long serialVersionUID = 2853993099608435083L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildCheckBtv.class);

	@Autowired
	Utiles utiles;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	UtilesViafirma utilesViafirma;
	
	public UtilesViafirma getUtilesViafirma() {
		if (utilesViafirma == null) {
			utilesViafirma = new UtilesViafirma();
		}
		return utilesViafirma;
	}

	public ResultadoBajasBean generarXml(TramiteTrafBajaVO tramiteTrafBajaVO) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {	
			log.info("Inicio generacion xml checkBtv tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
			log.info("Inicio relleno xml checkBtv tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
			SolicitudTramite solicitudTramite = rellenarXmlCheckBTV(tramiteTrafBajaVO);
			log.info("Fin relleno xml checkBtv tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
			if (solicitudTramite != null) {
				log.info("Inicio firma colegiado xml checkBtv tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
				resultado = realizarFirmaColegiado(solicitudTramite, tramiteTrafBajaVO.getContrato().getColegiado().getAlias());
				log.info("Fin firma colegiado xml checkBtv tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
				if (!resultado.getError()) {
					String nombreXml = ConstantesGestorFicheros.CONSULTA_BTV + "_" + tramiteTrafBajaVO.getNumExpediente();
					log.info("Inicio validacion xml con xsd del xml checkBtv tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
					resultado = validarXmlConsultaBTV(solicitudTramite, nombreXml);
					log.info("Fin validacion xml con xsd del xml checkBtv tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
					if (!resultado.getError()) {
						log.info("Inicio guardado xml con xsd del xml checkBtv tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
						gestorDocumentos.guardarFichero(ConstantesGestorFicheros.CONSULTA_BTV, 
								ConstantesGestorFicheros.XML_ENVIO, utiles.transformExpedienteFecha(tramiteTrafBajaVO.getNumExpediente()), 
								nombreXml, ConstantesGestorFicheros.EXTENSION_XML, resultado.getXml().getBytes("UTF-8"));
						log.info("Fin guardado xml con xsd del xml checkBtv tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
						resultado.setNombreFichero(nombreXml);
					}
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Ha surgido un error a la hora de rellenar el xml con los datos del tramite.");
			}
			log.info("Fin generacion xml checkBtv tramite baja: " + tramiteTrafBajaVO.getNumExpediente());
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el xml de checkBtv para el expediente: " + tramiteTrafBajaVO.getNumExpediente() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el xml de checkBtv para el expediente: " + tramiteTrafBajaVO.getNumExpediente());
		}
		return resultado;
	}
	
	public ResultadoBajasBean validarXmlConsultaBTV(SolicitudTramite solicitudTramite, String nombreXml) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		File fichero = new File(nombreXml);
		try {
			XmlConsultaBTVFactory xmlConsultaBTVFactory = new XmlConsultaBTVFactory();
			Marshaller marshaller = (Marshaller) xmlConsultaBTVFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudTramite, new FileOutputStream(nombreXml));
			// Lo validamos contra el XSD
			try {
				xmlConsultaBTVFactory.validarXMLCheckBTV(fichero);
			} catch (OegamExcepcion e) {
				resultado.setError(true);
				resultado.setMensaje(e.getMensajeError1());
			}
			if(!resultado.getError()){
				resultado.setXml(xmlConsultaBTVFactory.toXML(solicitudTramite));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el XML de CheckBTV con el xsd, error: ",e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el XML de CheckBTV con el xsd");
		}
		if(fichero.exists()){
			fichero.delete();
		}
		return resultado;
	}
	
	public SolicitudTramite rellenarXmlCheckBTV(TramiteTrafBajaVO tramiteTrafBajaVO) {
		ObjectFactory objectFactory = new ObjectFactory();
		SolicitudTramite solicitudTramite = objectFactory.createSolicitudTramite();
		solicitudTramite.setDatosFirmados(objectFactory.createDatosFirmados());
		solicitudTramite.getDatosFirmados().setCifGestoria(tramiteTrafBajaVO.getContrato().getColegiado().getUsuario().getNif());
		for(IntervinienteTraficoVO titular : tramiteTrafBajaVO.getIntervinienteTraficosAsList()){
			if(TipoInterviniente.Titular.getValorEnum().equals(titular.getId().getTipoInterviniente())){
				solicitudTramite.getDatosFirmados().setDoiTitular1(titular.getPersona().getId().getNif());
				break;
			}
		}
		solicitudTramite.getDatosFirmados().setMatricula(tramiteTrafBajaVO.getVehiculo().getMatricula());
		solicitudTramite.getDatosFirmados().setNifGestor(tramiteTrafBajaVO.getContrato().getColegiado().getUsuario().getNif());
		solicitudTramite.getDatosFirmados().setTextoLegal("");
		solicitudTramite.setVersion("1.0");
 		return solicitudTramite;
	}
	
	public ResultadoBajasBean realizarFirmaColegiado(SolicitudTramite solicitudTramite,	String alias) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			byte[] xmlFirmado = firmarXml(solicitudTramite,alias);
			if (xmlFirmado != null) {
				try {
					solicitudTramite.setFirmaGestor(new String(Base64.encodeBase64(xmlFirmado),"UTF-8"));
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
	
	private byte[] firmarXml(SolicitudTramite solicitudTramite, String alias) throws UnsupportedEncodingException {
		byte[] xmlFirmado = null;
		String xmlAfirmar = getXmlAfirmarPorTag(solicitudTramite);
		if (xmlAfirmar != null) {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			byte[] datosAfirmar = new String(xmlAfirmar.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");
			xmlFirmado = utilesViafirma.firmarXmlPorAlias(datosAfirmar, alias);
		}
		return xmlFirmado;
	}
	
	private String getXmlAfirmarPorTag(SolicitudTramite solicitudTramite)  {
		XmlConsultaBTVFactory xmlConsultaBtvFactory = new XmlConsultaBTVFactory();
		String xml = xmlConsultaBtvFactory.toXML(solicitudTramite);
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


	public void borraFicheroSiExisteConExtension(BigDecimal numExpediente, String nombreXml) throws OegamExcepcion {
		gestorDocumentos.borraFicheroSiExisteConExtension(ConstantesGestorFicheros.CONSULTA_BTV, ConstantesGestorFicheros.XML_ENVIO, utiles.transformExpedienteFecha(numExpediente),
				nombreXml, ConstantesGestorFicheros.EXTENSION_XML);
	}
	
}
