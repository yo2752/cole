package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.oegam2comun.consultaBTV.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.consultaBTV.view.xml.SolicitudTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.utiles.XmlConsultaBTVFactory;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildBajaCheckBtv implements Serializable{

	private static final long serialVersionUID = 6939362313597419529L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildBajaCheckBtv.class);

	@Autowired
	Utiles utiles;

	public SolicitudTramite rellenarXmlCheckBTV(TramiteTrafBajaDto tramiteTraficoBaja) {
		ObjectFactory objectFactory = new ObjectFactory();
		SolicitudTramite solicitudTramite = objectFactory.createSolicitudTramite();
		solicitudTramite.setDatosFirmados(objectFactory.createDatosFirmados());
		solicitudTramite.getDatosFirmados().setCifGestoria(tramiteTraficoBaja.getContrato().getColegiadoDto().getUsuario().getNif());
		solicitudTramite.getDatosFirmados().setDoiTitular1(tramiteTraficoBaja.getTitular().getPersona().getNif());
		solicitudTramite.getDatosFirmados().setMatricula(tramiteTraficoBaja.getVehiculoDto().getMatricula());
		solicitudTramite.getDatosFirmados().setNifGestor(tramiteTraficoBaja.getContrato().getColegiadoDto().getUsuario().getNif());
		solicitudTramite.getDatosFirmados().setTextoLegal("");
		solicitudTramite.setVersion("1.0");
 		return solicitudTramite;
	}
	

	public ResultBean realizarFirmaColegiado(SolicitudTramite solicitudTramite,	String alias) {
		ResultBean resultado = new ResultBean(false);
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
	

	public ResultBean validarXmlConsultaBTV(SolicitudTramite solicitudTramite, String nombreXml) {
		ResultBean resultado = new ResultBean(false);
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
				resultado.addAttachment(ServicioTramiteTraficoBaja.XML, xmlConsultaBTVFactory.toXML(solicitudTramite));
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
}
