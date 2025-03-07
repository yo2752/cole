package org.gestoresmadrid.oegam2comun.sega.utiles;

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
import org.gestoresmadrid.oegam2comun.sega.checkCtit.view.xml.SolicitudTramite;
import org.xml.sax.SAXException;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class XmlCheckCTITSegaFactory {

	private static final String UTF_8 = "UTF-8";
	private static final String TRAFICO_SCHEMAS_INTERFAZ_CHECK_CTIT_SEGA_XSD = "/org/gestoresmadrid/oegam2/trafico/sega/schemas/Interfaz_Check_Ctit_Sega.xsd";
	public static final String NAME_CONTEXT = "org.gestoresmadrid.oegam2comun.sega.checkCtit.view.xml";
	private static final String XML_ENCODING = UTF_8;
	private static final String AVISO_NIF_PATRON_NO_VALIDO = "cvc-pattern-valid"; 
	private Schema schema; 
	private JAXBContext context;
	private SchemaFactory factory;
	private ILoggerOegam log = LoggerOegam.getLogger(XmlCheckCTITSegaFactory.class);
	private final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	 
	private URL esquemaURL; 
	
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

	private Schema getSchema(String MY_SCHEMA) throws SAXException {
		if (schema==null) {
			schema = getFactory().newSchema(new StreamSource(MY_SCHEMA));
		}
		return schema;
	}
	
	private URL getEsquemaURL() {
		if (esquemaURL==null)
		esquemaURL = XmlCheckCTITSegaFactory.class.getResource(TRAFICO_SCHEMAS_INTERFAZ_CHECK_CTIT_SEGA_XSD);
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
	
	/**
	 * M�todo que genera el String con el formato XML que contiene los datos del objeto {@link SolicitudTramite} que 
	 * recibe como par�metro.
	 * 
	 * @param SolicitudTramite Objeto {@link SolicitudTramite} que contiene los datos que se quieren pasar a XML
	 * @return Devuelve el String que contiene el XML
	 */
	public String toXML(SolicitudTramite solicitudTramite) {
		String xmlString = null;
		try {
			xmlString = toXML((Object)solicitudTramite);
		} catch(Exception e) {
			log.error("Error al generar XML del objeto SolicitudTramite");
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
			peticionXML = new String (by, UTF_8);
		} catch (UnsupportedEncodingException e1){
			log.error(e1);
		}
		return peticionXML;
	}	
	
	/**
	 * M�todo que valida un XML contra el xsd del CheckCTIT (InterfazCTITCON.xsd)
	 * @param fichero xml a validar
	 * @return Devolver� XML_CORRECTO si es v�lido, en cualquier otro caso devolver� el string de la excepci�n de validaci�n
	 * @throws OegamExcepcion
	 */
	public void validarXMLCheckCTIT(File fichero) throws OegamExcepcion {
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
			throw new OegamExcepcion(saxEx.getMessage());
		} catch (Exception ex) {
			log.error(ex);
			throw new OegamExcepcion(ex.getMessage());
		}
	}
}
