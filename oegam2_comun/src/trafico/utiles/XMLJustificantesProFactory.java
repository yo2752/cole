package trafico.utiles;

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
import org.gestoresmadrid.oegam2comun.utiles.XmlConsultaBTVFactory;
import org.xml.sax.SAXException;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class XMLJustificantesProFactory {

	private static final String UTF_8 = "UTF-8";
	private static final String NAME_CONTEXT = "trafico.beans.schemas.generated.justificantesProf.generated";
	private final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String XML_ENCODING = UTF_8;
	private static final String RUTA_XSD = "/trafico/schemas/justificantesPro/InterfazProfProof.xsd";
	private static final String AVISO_NIF_PATRON_NO_VALIDO = "cvc-pattern-valid"; 
	
	private JAXBContext context;
	private Schema schema; 
	private SchemaFactory factory;
	private URL esquemaURL; 
	private static ILoggerOegam log = LoggerOegam.getLogger(XMLJustificantesProFactory.class);
	
	public JAXBContext getContext() throws JAXBException{
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
	
	private URL getEsquemaURL() {
		if (esquemaURL == null){
			esquemaURL = XmlConsultaBTVFactory.class.getResource(RUTA_XSD);
		}
		return esquemaURL; 
	}
	
	private Schema getSchema(String MY_SCHEMA) throws SAXException {
		if (schema==null) {
			schema = getFactory().newSchema(new StreamSource(MY_SCHEMA));
		}
		return schema;
	}

	/**
	 * Método que obtiene el String XML que se monta a partir de un objeto al que se le realiza un marshall.
	 * 
	 * @param xmlObject Objeto que contiene los datos que se quieren pasar a XML
	 * @return Devuelve un String que contiene el XML
	 * @throws OegamExcepcion 
	 * @throws JAXBException
	 */
	public String toXML(Object xmlObject) throws OegamExcepcion {
		StringWriter writer = new StringWriter();
		try {
			Marshaller m = getContext().createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING);
			m.marshal(xmlObject, writer);
			writer.flush();
		}
		catch (JAXBException e ) {
			log.error(e);
			throw new OegamExcepcion(e.getMessage());
		}
		return writer.toString();
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
	 * Método que valida un XML contra el xsd del Justificante Profesional (InterfazProfProof.xsd)
	 * @param fichero xml a validar
	 * @return Devolverá XML_CORRECTO si es válido, en cualquier otro caso devolverá el string de la excepción de validación
	 * @throws OegamExcepcion
	 */
	public void validarJustificanteProfesional(File fichero) throws OegamExcepcion {
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
