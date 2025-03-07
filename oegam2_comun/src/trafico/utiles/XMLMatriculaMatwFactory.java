package trafico.utiles;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


/**
 * Esta clase es para la creacion de los XML de Custodia de Matriculacion. Nada que ver con MATE, ni MATE Carton ni Electronic Mate ni Matw
 * @author julio.molina
 *
 */
public class XMLMatriculaMatwFactory {

	private static final String NAME_CONTEXT = "trafico.beans.jaxb.matriculacionFirma";
	private static final String TRAFICO_SCHEMAS_INTERFAZ_MATG_XSD = "/trafico/schemas/MATRICULACION_FIRMA.xsd";
	private static final String HTTP_WWW_W3_ORG_2001_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String XML_VALIDO = "CORRECTO";
	
	private static ILoggerOegam log = LoggerOegam.getLogger(XMLMatriculaMatwFactory.class);
	
	private JAXBContext context;
	private Validator validator; 
	private URL esquemaURL;
	private Schema schema; 
	private  SchemaFactory factory;

	private JAXBContext getContext() throws JAXBException {
		if (context == null){
			context = JAXBContext.newInstance(NAME_CONTEXT);
		}
		return context;
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
		if (esquemaURL==null)
			esquemaURL = XMLMatriculaMatwFactory.class.getResource(TRAFICO_SCHEMAS_INTERFAZ_MATG_XSD);
		return esquemaURL; 
	}

	private Validator getValidator() {
		try {
			validator = getSchema(getEsquemaURL().getFile()).newValidator();
		}
		catch (SAXException e) {
			log.error(e);
		}
		return validator; 
	}
	
	/****************************************/
	/* Metodos necesarios para el simulador */
	/****************************************/
	/**
	 * Método que valida un XML  contra XML de Custodia de Matriculacion.
	 * @param fichero xml a validar
	 * @return Devolverá XML_VALIDO si es válido, en cualquier otro caso devolverá el string de la excepción de validación
	 */
	public String validarXML(File fichero) {
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
