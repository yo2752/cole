package org.gestoresmadrid.oegam2comun.sega.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.SolicitudRegistroEntrada;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.xml.sax.SAXException;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class XMLMatwSegaFactory {

	private static final String ERROR_AL_GENERAR_XML_DEL_OBJETO_SOLICITUD_REGISTRO_ENTRADA = "Error al generar XML del objeto SolicitudRegistroEntrada";
	private static final String UTF8 = "UTF8";
	public static final String NAME_CONTEXT = "org.gestoresmadrid.oegam2comun.sega.matw.view.xml";
	private static final String TRAFICO_SCHEMAS_INTERFAZ_MATW_SEGA_XSD = "/org/gestoresmadrid/oegam2/trafico/sega/schemas/Interfaz_Matw_Sega.xsd";
	private static final String HTTP_WWW_W3_ORG_2001_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String XML_ENCODING = "UTF-8";
	private static final String XML_VALIDO = "CORRECTO";
	
	private static ILoggerOegam log = LoggerOegam.getLogger(XMLMatwSegaFactory.class);
	
	private JAXBContext context;
	private Validator validator; 
	private URL esquemaURL;
	private Schema schema; 
	private SchemaFactory factory;

	private JAXBContext getContext() throws JAXBException, ClassNotFoundException{
		if (context == null){
			//context = JAXBContext.newInstance(NAME_CONTEXT);
			context = JAXBContext.newInstance(getClases(NAME_CONTEXT));
		}
		return context;
	}
	
	@SuppressWarnings("rawtypes")
	private Class[] getClases(String paquete) throws ClassNotFoundException {
		ArrayList<Class> arrayReturn = new ArrayList<Class>();
		Reflections reflections = new Reflections(new ConfigurationBuilder().setScanners(new SubTypesScanner(false /* don't exclude Object.class */), 
        	new ResourcesScanner()).setUrls(ClasspathHelper.forPackage(paquete))
        	.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(paquete)))); 
		Object[] clasesList = ((HashSet)reflections.getSubTypesOf(Object.class)).toArray();
		for (int cont = 0; cont < clasesList.length; cont ++) {
			String nombreClase = clasesList[cont].toString();
			if(nombreClase.contains("class")) {
				nombreClase = nombreClase.substring("class ".length());
				arrayReturn.add(Class.forName(nombreClase));
			}
		}
		Class[] array = new Class[arrayReturn.size()];
		return arrayReturn.toArray(array);
	}
	
	private SchemaFactory getFactory() {
		if (factory==null) {
			factory = SchemaFactory.newInstance(HTTP_WWW_W3_ORG_2001_XML_SCHEMA);
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
		if (esquemaURL==null){
			esquemaURL = XMLMatwSegaFactory.class.getResource(TRAFICO_SCHEMAS_INTERFAZ_MATW_SEGA_XSD);
		}		
		return esquemaURL; 
	}

	private Validator getValidator() {
		try {
			validator = getSchema(getEsquemaURL().getFile()).newValidator();
		} catch (SAXException e) {
			log.error(e);
		}
		return validator; 
	}
	
	/**
	 * Método que obtiene el String XML que se monta a partir de un objeto al que se le realiza un marshall.
	 * 
	 * @param xmlObject Objeto que contiene los datos que se quieren pasar a XML
	 * @return Devuelve un String que contiene el XML
	 * @throws JAXBException
	 * @throws ClassNotFoundException 
	 */
	private String toXML(Object xmlObject) throws JAXBException, ClassNotFoundException {
		StringWriter writer = new StringWriter();
		
		Marshaller m = getContext().createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING);
		m.marshal(xmlObject, writer);
		writer.flush();
		return writer.toString();
	}
	
	/**
	 * Método que genera el String con el formato XML que contiene los datos del objeto {@link SolicitudRegistroEntrada} que 
	 * recibe como parámetro.
	 * 
	 * @param solicitudRegistroEntrada Objeto {@link SolicitudRegistroEntrada} que contiene los datos que se quieren pasar a XML
	 * @return Devuelve el String que contiene el XML
	 */
	public String toXML(SolicitudRegistroEntrada solicitudRegistroEntrada) {
		String xmlString = null;
		try {
			xmlString = toXML((Object)solicitudRegistroEntrada);
		}
		catch(Exception e) {
			log.error(ERROR_AL_GENERAR_XML_DEL_OBJETO_SOLICITUD_REGISTRO_ENTRADA);
			log.error(e);
		}
		return xmlString;
	}
	
	/****************************************/
	/* Metodos necesarios para el simulador */
	/****************************************/
	/**
	 * 
	 * @param ficheroAenviar File de un xml
	 * @return El fichero xml en String
	 */
	public String xmlFileToString(File ficheroAenviar) {
		byte[] by = null; 
		String  peticionXML = null;
		try {
			FileInputStream fis;
			fis = new FileInputStream(ficheroAenviar);
			by = IOUtils.toByteArray(fis);
		} catch (FileNotFoundException e1) {
			log.error(e1);
			return null;
		} catch (IOException e) {
			log.error(e);
			return null;
		}
		try {
			peticionXML = new String (by, UTF8);
		} catch (UnsupportedEncodingException e1) {
			log.error(e1);
			return null;
		}
		return peticionXML;
	}
	
	/**
	 * Método que valida un XML  contra MATW (InterfazMATW.xsd)
	 * @param fichero xml a validar
	 * @return Devolverá XML_VALIDO si es válido, en cualquier otro caso devolverá el string de la excepción de validación
	 */
	public String validarXMLMATW(File fichero) {
		String resultadoValidar; 
    	try {
			getValidator().validate(new StreamSource(fichero));
			resultadoValidar= XML_VALIDO;
			log.info("resultado validar=" + XML_VALIDO);
    	} catch (SAXException e) {
			log.error(e);
			resultadoValidar = e.getMessage(); 
		} catch (IOException e) {
			log.error(e);
			resultadoValidar = e.getMessage(); 
		}
		return resultadoValidar; 
	}
}
