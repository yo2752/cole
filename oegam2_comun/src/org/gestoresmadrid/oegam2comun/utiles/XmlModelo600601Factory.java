package org.gestoresmadrid.oegam2comun.utiles;

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
import org.gestoresmadrid.oegam2comun.consultaBTV.view.xml.SolicitudTramite;
import org.xml.sax.SAXException;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class XmlModelo600601Factory {

	private static final String UTF_8 = "UTF-8";
	private static final String XML_ENCODING_ISO = "ISO-8859-1";
	public static final String NAME_CONTEXT = "org.gestoresmadrid.oegam2comun.modelo600_601.view.xml";
	private final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String MODELO_600_SCHEMAS_INTERFAZ_XSD = "/org/gestoresmadrid/oegam2/modelo600601/schemas/600_V1.2.xsd";
	private static final String MODELO_601_SCHEMAS_INTERFAZ_XSD = "/org/gestoresmadrid/oegam2/modelo600601/schemas/601_V1.2.xsd";
	// Cambiar estas líneas por las de arriba cuando se proceda al desarrollo de los Modelos 600 y 601
//	private static final String MODELO_600_SCHEMAS_INTERFAZ_XSD = "/org/gestoresmadrid/oegam2/modelo600601/schemas/600_V1.3.xsd";
//	private static final String MODELO_601_SCHEMAS_INTERFAZ_XSD = "/org/gestoresmadrid/oegam2/modelo600601/schemas/601_V1.3.xsd";

	private static final String AVISO_NIF_PATRON_NO_VALIDO = "cvc-pattern-valid";

	private URL esquema600URL;
	private URL esquema601URL;

	private Schema schema;
	private JAXBContext context;
	private SchemaFactory factory;
	private ILoggerOegam log = LoggerOegam.getLogger(XmlModelo600601Factory.class);

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

	private URL getEsquema600URL() {
		if (esquema600URL == null){
			esquema600URL = XmlModelo600601Factory.class.getResource(MODELO_600_SCHEMAS_INTERFAZ_XSD);
		}
		return esquema600URL;
	}

	private URL getEsquema601URL() {
		if (esquema601URL == null){
			esquema601URL = XmlModelo600601Factory.class.getResource(MODELO_601_SCHEMAS_INTERFAZ_XSD);
		}
		return esquema601URL;
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
		m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING_ISO);
		m.marshal(xmlObject, writer);
		writer.flush();

		return writer.toString();
	}

	public String toXMLUTF8(Object xmlObject) throws JAXBException {
		StringWriter writer = new StringWriter();

		Marshaller m = getContext().createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
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
	 * Método que genera el String con el formato XML que contiene los datos del objeto {@link SolicitudTramite} que 
	 * recibe como parámetro.
	 * 
	 * @param SolicitudTramite Objeto {@link SolicitudTramite} que contiene los datos que se quieren pasar a XML
	 * @return Devuelve el String que contiene el XML
	 */
	public String toXML(SolicitudTramite solicitudTramite) {
		String xmlString = null;
		try {
			xmlString = toXML((Object)solicitudTramite);
		} catch(Exception e) {
			log.error("Error al generar XML del objeto SolicitudPresentacionModelos");
			log.error(e);
		}
		return xmlString;
	}

	/****************************************/
	/* Métodos necesarios para el simulador */
	/****************************************/
	/**
	 * 
	 * @param fichero File de un XML
	 * @return El fichero xml en String
	 */
	public String xmlFileToString(File fichero) {
		byte[] by = null; 
		try {
			FileInputStream fis;
			fis = new FileInputStream(fichero);
			by = IOUtils.toByteArray(fis);
		} catch (IOException e){
			log.error(e);
		}
		String peticionXML = null;
		try {
			peticionXML = new String (by, UTF_8);
		} catch (UnsupportedEncodingException e1){
			log.error(e1);
		}
		return peticionXML;
	}

	/**
	 * Método que valida un XML contra el xsd del modelo600
	 * @param fichero xml a validar
	 * @return Devolverá XML_CORRECTO si es válido, en cualquier otro caso devolverá el string de la excepción de validación
	 * @throws OegamExcepcion
	 */
	public void validarXMLModelo600(File fichero) throws OegamExcepcion {
		Validator validator;
		String mensaje;
		String snif;
		int pos;
		try {
			validator = getSchema(getEsquema600URL().getFile()).newValidator();
			validator.validate(new StreamSource(fichero));
		} catch (SAXException saxEx) {
			if (saxEx.getMessage().contains(AVISO_NIF_PATRON_NO_VALIDO)) {
				mensaje = saxEx.getMessage();
				pos = mensaje.indexOf("'");
				snif = mensaje.substring(pos, mensaje.indexOf("'", pos + 1));
				log.error("El Formato del NIF: " + snif + "' no es válido.");
			} else {
				log.error(saxEx);
			}
			throw new OegamExcepcion(saxEx.getMessage());
		} catch (Exception ex) {
			log.error(ex);
			throw new OegamExcepcion(ex.getMessage());
		}
	}

	/**
	 * Método que valida un XML contra el xsd del modelo601
	 * @param fichero xml a validar
	 * @return Devolverá XML_CORRECTO si es válido, en cualquier otro caso devolverá el string de la excepción de validación
	 * @throws OegamExcepcion
	 */
	public void validarXMLModelo601(File fichero) throws OegamExcepcion {
		Validator validator;
		String mensaje;
		String snif;
		int pos;
		try {
			validator = getSchema(getEsquema601URL().getFile()).newValidator();
			validator.validate(new StreamSource(fichero));
		} catch (SAXException saxEx) {
			if (saxEx.getMessage().contains(AVISO_NIF_PATRON_NO_VALIDO)) {
				mensaje = saxEx.getMessage();
				pos = mensaje.indexOf("'");
				snif = mensaje.substring(pos, mensaje.indexOf("'", pos + 1));
				log.error("El Formato del NIF: " + snif + "' no es válido.");
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