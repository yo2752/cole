package org.gestoresmadrid.oegam2comun.trafico.solInfoApp.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.gestoresmadrid.oegam2comun.trafico.solInfoApp.view.xml.FormatoOegam2SolInfo;
import org.xml.sax.SAXException;

public class XmlSolInfoAppFactory {

	private static final String XML_ENCODING = "UTF-8";
	public static final String NAME_CONTEXT = "org.gestoresmadrid.oegam2comun.trafico.solInfoApp.view.xml";
	private final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String SOLICITUDES_INFORMACION_XSD = "org/gestoresmadrid/oegam2/trafico/solInfoApp/schemas/SolicitudesInformacion.xsd";
	
	private static final String AVISO_NIF_PATRON_NO_VALIDO = "cvc-pattern-valid"; 
	private URL esquemaURL; 
	
	private Schema schema; 
	private JAXBContext context;
	private SchemaFactory factory;
	private static Logger log = Logger.getLogger(XmlSolInfoAppFactory.class);
	
	public JAXBContext getContext() throws JAXBException {
		if (context == null){
			context = JAXBContext.newInstance(NAME_CONTEXT);
		}
		return context;
	}
	
	private SchemaFactory getFactory() {
		if (factory==null) {
			factory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
		}
		return factory;
	}
	
	private URL getEsquemaURL() {
		if (esquemaURL == null){
			esquemaURL = XmlSolInfoAppFactory.class.getResource(SOLICITUDES_INFORMACION_XSD);
		}
		return esquemaURL; 
	}
	
	/**
	 * M�todo que obtiene el String XML que se monta a partir de un objeto al que se le realiza un marshall.
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
	
	private Schema getSchema(String MY_SCHEMA) throws SAXException {
		if (schema==null) {
			schema = getFactory().newSchema(new StreamSource(MY_SCHEMA));
		}
		return schema;
	}
	
	/**
	 * M�todo que genera el String con el formato XML que contiene los datos del objeto {@link FormatoOegam2SolInfo} que 
	 * recibe como par�metro.
	 * 
	 * @param FormatoOegam2SolInfo Objeto {@link FormatoOegam2SolInfo} que contiene los datos que se quieren pasar a XML
	 * @return Devuelve el String que contiene el XML
	 */
	public String toXML(FormatoOegam2SolInfo formatoOegam2SolInfo) {
		String xmlString = null;
		try {
			xmlString = toXML((Object)formatoOegam2SolInfo);
		} catch(Exception e) {
			log.error("Error al generar XML del objeto FormatoOegam2SolInfo");
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
	
	/**
	 * M�todo que valida un XML contra el xsd 
	 * @param fichero xml a validar
	 * @return Devolver� XML_CORRECTO si es v�lido, en cualquier otro caso devolver� el string de la excepci�n de validaci�n
	 * @throws Exception
	 */
	public void validarXML(File fichero) throws Exception {
		Validator validator;
		String mensaje, snif;
		int pos;
		try {
			validator = getSchema(getEsquemaURL().getFile()).newValidator();
			validator.validate(new StreamSource(fichero));
		} catch (SAXException saxEx) {
			if (saxEx.getMessage().contains(AVISO_NIF_PATRON_NO_VALIDO)) {
				mensaje = saxEx.getMessage();
				pos = mensaje.indexOf("'");
				snif = mensaje.substring(pos, mensaje.indexOf("'", pos + 1));
				log.error("El Formato del NIF: " + snif + "' no es v�lido.");
			} else {
				log.error(saxEx);
			}
			throw new Exception(saxEx.getMessage());
		} catch (Exception ex) {
			log.error(ex);
			throw new Exception(ex.getMessage());
		}
	}
}
