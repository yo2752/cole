package trafico.utiles;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import trafico.beans.jaxb.solicitud.FORMATOOEGAM2SOLICITUD;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;


//Mantis 14125. David Sierra: Metodos para la conversion de XML
public class XMLSolicitudFactory {
	
private static final String NAME_CONTEXT = "trafico.beans.jaxb.solicitud";
	
	private Schema schema; 
	private JAXBContext context;
	private SchemaFactory factory;
	private final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";  
	
	private static ILoggerOegam log = LoggerOegam.getLogger(XMLSolicitudFactory.class);
	
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

	public Schema getSchema(String MY_SCHEMA) throws SAXException {
		if (schema==null) {
			schema = getFactory().newSchema(new StreamSource(MY_SCHEMA));
		}
		return schema;
	}
	
	/**
	 * Obtiene el objeto {@link FORMATOOEGAM2SOLICITUD} que contiene los datos del XML que recibe
	 * en el String
	 * 
	 * @param xmlString String con formato XML que contiene la peticion realizada
	 * @return Devuelve un objeto {@link FORMATOOEGAM2SOLICITUD} que contiene los datos del XML recibido
	 */
	public FORMATOOEGAM2SOLICITUD getFormatoOegam2Solicitud(String xmlString) {
		FORMATOOEGAM2SOLICITUD FormatoOegam2Solicitud = null;
		try {
			FormatoOegam2Solicitud = (FORMATOOEGAM2SOLICITUD)fromXML(xmlString);
		} catch(Exception e) {
			log.error("Error obteniendo FormatoOegam2Solicitud");
			log.error(e);
		}
		return FormatoOegam2Solicitud;
	}
	
	/**
	 * Obtiene el objeto {@link FORMATOOEGAM2SOLICITUD} que contiene los datos del XML que recibe
	 * en el String
	 * 
	 * @param fichero Fichero con formato XML que contiene la peticion realizada
	 * @return Devuelve un objeto {@link FORMATOOEGAM2SOLICITUD} que contiene los datos del XML recibido
	 */
	public FORMATOOEGAM2SOLICITUD getFormatoOegam2Solicitud(File fichero) {
		FORMATOOEGAM2SOLICITUD FormatoOegam2Solicitud = null;
		try {
			FormatoOegam2Solicitud = (FORMATOOEGAM2SOLICITUD)fromXML(fichero);
		} catch(Exception e) {
			log.error("Error obteniendo FormatoOegam2Solicitud");			
			log.error(e);
		}
		return FormatoOegam2Solicitud;
	}
	
	/**
	 * Realiza el parseo de los datos XML que se reciben en la cadena de caracteres, almacenandolos
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
	 * Realiza el parseo de los datos XML que se reciben en la cadena de caracteres, almacenandolos
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
	 * Método que valida un XML contra el xsd de Solicitud
	 * @param fichero xml a validar
	 * @throws XmlNoValidoExcepcion 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public void validarXMLFORMATOOEGAM2SOLICITUD(File fichero, String rutaEsquema) throws XmlNoValidoExcepcion, IOException {
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
	

}
