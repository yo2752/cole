package org.gestoresmadrid.oegam2comun.trafico.eeff.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.SolicitudRegistroEntrada;

import trafico.beans.jaxb.consultaEEFF.RespuestaEEFF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class XmlConsultaEeffFactory {

	private static final String XML_ENCODING = "UTF-8";
	public static final String NAME_CONTEXT = "org.gestoresmadrid.oegam2comun.trafico.eeff.consulta.view.xml";
	private JAXBContext context;
	private ILoggerOegam log = LoggerOegam.getLogger(XmlConsultaEeffFactory.class);
	
	public JAXBContext getContext() throws JAXBException {
		if (context == null){
			context = JAXBContext.newInstance(NAME_CONTEXT);
		}
		return context;
	}
	
	/**
	 * Método que obtiene el String XML que se monta a partir de un objeto al que se le realiza un marshall.
	 * 
	 * @param xmlObject Objeto que contiene los datos que se quieren pasar a XML
	 * @return Devuelve un String que contiene el XML
	 * @throws JAXBException
	 */
	public String toXML(Object xmlObject) throws JAXBException {
		StringWriter writer = new StringWriter();
		
		Marshaller m = getContext().createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING);
		m.marshal(xmlObject, writer);
		writer.flush();
		
		return writer.toString();
	}
	
	
	public RespuestaEEFF getRespuestaEEFF(File file){
		RespuestaEEFF respuestaEEFF = null;
		try {
			Unmarshaller unmarshaller = getContext().createUnmarshaller();
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "ISO-8859-15");
			respuestaEEFF = (RespuestaEEFF) unmarshaller.unmarshal(reader);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar el Unmarshall.");
		}
		return respuestaEEFF;
	}
	
	
	/**
	 * Método que genera el String con el formato XML que contiene los datos del objeto {@link SolicitudRegistroEntrada} que 
	 * recibe como parámetro.
	 * 
	 * @param SolicitudRegistroEntrada Objeto {@link SolicitudRegistroEntrada} que contiene los datos que se quieren pasar a XML
	 * @return Devuelve el String que contiene el XML
	 */
	public String toXML(SolicitudRegistroEntrada solicitudRegistroEntrada) {
		String xmlString = null;
		try {
			xmlString = toXML((Object)solicitudRegistroEntrada);
		} catch(Exception e) {
			log.error("Error al generar XML del objeto SolicitudRegistroEntrada");
			log.error(e);
		}
		return xmlString;
	}
	
	/****************************************/
	/* Metodos necesarios para el simulador */
	/****************************************/
	/**
	 * 
	 * @param fichero File de un xml
	 * @return El fichero xml en String
	 */
	public String xmlFileToString(File fichero) {
		byte[] by = null; 
		try {
			FileInputStream fis;
			fis = new FileInputStream(fichero);
			by = IOUtils.toByteArray(fis);
		}  catch (IOException e){
			log.error(e);
		}
		String  peticionXML = null;
		try {
			peticionXML = new String (by, XML_ENCODING);
		} catch (UnsupportedEncodingException e1){
			log.error(e1);
		}
		return peticionXML;
	}	
	
}
