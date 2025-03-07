package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.oegam2comun.sega.checkBtv.view.xml.DatosFirmados.ListaTransmitentes;
import org.gestoresmadrid.oegam2comun.sega.checkBtv.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.sega.checkBtv.view.xml.SolicitudTramite;
import org.gestoresmadrid.oegam2comun.sega.utiles.XmlConsultaBTVSegaFactory;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildBajaCheckBtvSega implements Serializable{

	private static final long serialVersionUID = 6939362313597419529L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildBajaCheckBtvSega.class);
		
	@Autowired
	Utiles utiles;

	public SolicitudTramite rellenarXmlCheckBTVSega(TramiteTrafBajaDto tramiteTraficoBaja) {
		ObjectFactory objectFactory = new ObjectFactory();
		String doi = tramiteTraficoBaja.getTitular().getPersona().getNif();
		SolicitudTramite solicitudTramiteSega = objectFactory.createSolicitudTramite();
		solicitudTramiteSega.setDatosFirmados(objectFactory.createDatosFirmados());
		solicitudTramiteSega.getDatosFirmados().setListaTransmitentes(new ListaTransmitentes());
		solicitudTramiteSega.getDatosFirmados().getListaTransmitentes().getDOI().add(doi);
		solicitudTramiteSega.getDatosFirmados().setNIFGestor(tramiteTraficoBaja.getContrato().getCif());
		solicitudTramiteSega.getDatosFirmados().setMatricula(tramiteTraficoBaja.getVehiculoDto().getMatricula());
		solicitudTramiteSega.getDatosFirmados().setNIFGestor(tramiteTraficoBaja.getContrato().getColegiadoDto().getUsuario().getNif());
		solicitudTramiteSega.getDatosFirmados().setTextoLegal("");
		solicitudTramiteSega.setVersion("1.0");
 		return solicitudTramiteSega;
	}

	
	public ResultBean realizarFirmaColegiadoSega(org.gestoresmadrid.oegam2comun.sega.checkBtv.view.xml.SolicitudTramite solicitudTramiteSega,	String alias) {
		ResultBean resultado = new ResultBean(false);
		try {
			byte[] xmlFirmado = firmarXml(solicitudTramiteSega,alias);
			if (xmlFirmado != null) {
				try {
					solicitudTramiteSega.setFirmaGestor(new String(Base64.encodeBase64(xmlFirmado),"UTF-8").getBytes());
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
	
	private byte[] firmarXml(org.gestoresmadrid.oegam2comun.sega.checkBtv.view.xml.SolicitudTramite solicitudTramiteSega, String alias) throws UnsupportedEncodingException {
		byte[] xmlFirmado = null;
		String xmlAfirmar = getXmlAfirmarPorTag(solicitudTramiteSega);
		if (xmlAfirmar != null) {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			byte[] datosAfirmar = new String(xmlAfirmar.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");
			xmlFirmado = utilesViafirma.firmarXmlPorAlias(datosAfirmar, alias);
		}
		return xmlFirmado;
	}
	
	private String getXmlAfirmarPorTag(org.gestoresmadrid.oegam2comun.sega.checkBtv.view.xml.SolicitudTramite solicitudTramiteSega)  {
		XmlConsultaBTVSegaFactory xmlConsultaBtvFactorySega = new XmlConsultaBTVSegaFactory();
		String xml = xmlConsultaBtvFactorySega.toXML(solicitudTramiteSega);
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

	
	public ResultBean validarXmlConsultaBTVSega(org.gestoresmadrid.oegam2comun.sega.checkBtv.view.xml.SolicitudTramite solicitudTramiteSega, String nombreXml) {
		ResultBean resultado = new ResultBean(false);
		File fichero = new File(nombreXml);
		try {
			XmlConsultaBTVSegaFactory xmlConsultaBTVFactorySega = new XmlConsultaBTVSegaFactory();
			Marshaller marshaller = (Marshaller) xmlConsultaBTVFactorySega.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudTramiteSega, new FileOutputStream(nombreXml));
			// Lo validamos contra el XSD
			try {
				xmlConsultaBTVFactorySega.validarXMLCheckBTV(fichero);
			} catch (OegamExcepcion e) {
				resultado.setError(true);
				resultado.setMensaje(e.getMensajeError1());
			}
			if(!resultado.getError()){
				resultado.addAttachment(ServicioTramiteTraficoBaja.XML, xmlConsultaBTVFactorySega.toXML(solicitudTramiteSega));
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
