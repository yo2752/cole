package trafico.utiles;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import trafico.beans.jaxb.transmision.FORMATOGA;

import trafico.utiles.UtilesXmlFactories;
import trafico.utiles.XMLManejadorErrores;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

public class XMLTransmisionFactory {

	private static final String NAME_CONTEXT = "trafico.beans.jaxb.transmision";
	
	private Schema schema; 
	private JAXBContext context;
	private SchemaFactory factory;
	private final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";  
	
	private static ILoggerOegam log = LoggerOegam.getLogger(XMLTransmisionFactory.class);
	
	public JAXBContext getContext() throws JAXBException {
		if (context == null) {
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

	public Schema getSchema(String MY_SCHEMA) throws SAXException {
		if (schema==null) {
			schema = getFactory().newSchema(new StreamSource(MY_SCHEMA));
		}
		return schema;
	}

	/**
	 * Obtiene el objeto {@link formatoOegam2Transmision} que contiene los datos del XML que recibe
	 * en el String
	 * 
	 * @param xmlString String con formato XML que contiene la petición realizada
	 * @return Devuelve un objeto {@link SolicitudRegistroEntrada} que contiene los datos del XML recibido
	 */
	public FORMATOGA getFormatoOegam2Transmision(String xmlString) {
		FORMATOGA formatoOegam2Transmision = null;
		try {
			formatoOegam2Transmision = (FORMATOGA)fromXML(xmlString);
		} catch(Exception e) {
			log.error("Error obteniendo formatoOegam2Transmision");
			log.error(e);
		}
		return formatoOegam2Transmision;
	}
	
	/**
	 * Obtiene el objeto {@link formatoOegam2Transmision} que contiene los datos del XML que recibe
	 * en el String
	 * 
	 * @param fichero Fichero con formato XML que contiene la petición realizada
	 * @return Devuelve un objeto {@link SolicitudRegistroEntrada} que contiene los datos del XML recibido
	 */
	public FORMATOGA getFormatoOegam2Transmision(File fichero) {
		FORMATOGA formatoOegam2Transmision = null;
		try {
			formatoOegam2Transmision = (FORMATOGA)fromXML(fichero);
		} catch(Exception e) {
			log.error("Error obteniendo formatoOegam2Transmision");
			log.error(e);
		}
		return formatoOegam2Transmision;
	}
	
	/**
	 * Obtiene el objeto {@link formatoOegam2Transmision} que contiene los datos del XML que recibe
	 * en el String
	 * 
	 * @param fichero Fichero con formato XML que contiene la petición realizada
	 * @return Devuelve un objeto {@link FORMATOGA} que contiene los datos del XML recibido
	 */
	public FORMATOGA getFormatoOegam2Transmision(FileInputStream fichero) {
		FORMATOGA formatoOegam2Transmision = null;
		try {
			formatoOegam2Transmision = (FORMATOGA)fromXML(fichero);
		} catch(Exception e) {
			log.error("Error obteniendo formatoOegam2Transmision");
			log.error(e);
		}
		return formatoOegam2Transmision;
	}
	
	/**
	 * Realiza el parseo de los datos XML que se reciben en la cadena de caracteres, almacenándolos
	 * en el objeto que devuelve. 
	 * 
	 * @param xmlString String con formato XML
	 * @return Devuelve un objeto que contiene los datos del XML que ha recibido
	 * @throws JAXBException
	 */
	private Object fromXML(String xmlString) throws JAXBException {
		Object obj = null;
		
		Unmarshaller um = getContext().createUnmarshaller();
		ByteArrayInputStream bais = new ByteArrayInputStream(xmlString.getBytes());
		try {
			obj = um.unmarshal(bais);
		} catch(Exception e) {
			log.error("Error UNMARSHAL");
			log.error(e);
		}
		try {
			bais.close();
		} catch(IOException ioe) {
			//Error al cerrar el flujo de bytes
		}
		return obj;
	}
	
	/**
	 * Realiza el parseo de los datos XML que se reciben en la cadena de caracteres, almacenándolos
	 * en el objeto que devuelve. 
	 * 
	 * @param xmlString String con formato XML
	 * @return Devuelve un objeto que contiene los datos del XML que ha recibido
	 * @throws JAXBException
	 */
	private Object fromXML(File fichero) throws JAXBException {
		Object obj = null;
		
		Unmarshaller um = getContext().createUnmarshaller();
		try {
			obj = um.unmarshal(fichero);
		}
		catch(Exception e) {
			log.error("Error UNMARSHAL");
			log.error(e);
		}
		return obj;
	}
	
	/**
	 * Realiza el parseo de los datos XML que se reciben en la cadena de caracteres, almacenándolos
	 * en el objeto que devuelve. 
	 * 
	 * @return Devuelve un objeto que contiene los datos del XML que ha recibido
	 * @throws JAXBException
	 */
	private Object fromXML(FileInputStream fichero) throws JAXBException {
		Object obj = null;
		
		Unmarshaller um = getContext().createUnmarshaller();
		try {
			obj = um.unmarshal(fichero);
		}
		catch(Exception e) {
			log.error("Error UNMARSHAL");
			log.error(e);
		}
		return obj;
	}
	
	/**
	 * Método que valida un XML contra el xsd de Transmision
	 * @param fichero xml a validar
	 * @throws XmlNoValidoExcepcion 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public void validarXMLTRANSMISION(File fichero, String rutaEsquema) throws XmlNoValidoExcepcion, IOException {
    	// Constantes para validacion de Schemas   
    	try {
			Schema schema = getSchema(rutaEsquema);
			Validator validator = schema.newValidator();
			XMLManejadorErrores errores = new XMLManejadorErrores();
			validator.setErrorHandler(errores);
			validator.validate(new StreamSource(fichero));
			if (errores.getListaErrores().size() > 0) {
  				String mensaje = "";
  				log.error("Errores en la validacion del fichero: " + fichero.getName());
  				for (int i = 0 ; i < errores.getListaErrores().size() ; i++) {
  					log.error(errores.getListaErrores().get(i));
  					if ( i < errores.getListaErrores().size()-1) {
  						mensaje += errores.getListaErrores().get(i)+". ";
  					}
  					else {
  						mensaje += errores.getListaErrores().get(i);
  					}
  				}
  				log.error(errores.getLineasErrores());
  				throw new XmlNoValidoExcepcion(mensaje + " - Lineas: " + errores.getLineasErrores());
  			}
  		}
  		catch (Exception e) {
  			throw new XmlNoValidoExcepcion(e.getMessage());
  		}
  	}
	
	public String xmlFileToString(File ficheroAenviar) throws FileNotFoundException {
		return UtilesXmlFactories.xmlFileToString(ficheroAenviar);
	}
}