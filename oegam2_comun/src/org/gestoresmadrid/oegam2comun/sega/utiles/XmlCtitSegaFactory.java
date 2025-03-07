package org.gestoresmadrid.oegam2comun.sega.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.xml.sax.SAXException;

import trafico.utiles.UtilesWSCTIT;
import trafico.utiles.XMLManejadorErrores;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.RegistroEntradaExcepcion;


public class XmlCtitSegaFactory {

	private static final String TRAFICO_SCHEMAS_SOLICITUD_REGISTRO_ENTRADA_CTIT_XSD = "/org/gestoresmadrid/oegam2/trafico/sega/schemas/Interfaz_Ctit_Sega.xsd";
	public static final String NAME_CONTEXT = "org.gestoresmadrid.oegam2comun.sega.ctit.view.xml";
	private static final String XML_ENCODING = "UTF-8";
	
	private Schema schema; 
	private JAXBContext context;
	private SchemaFactory factory;
	private final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";  
	private URL esquemaURL; 
	
	private static ILoggerOegam log = LoggerOegam.getLogger(XmlCtitSegaFactory.class);
	
	@Autowired
	private Utiles utiles;

	public XmlCtitSegaFactory() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

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

	private Schema getSchema(String MY_SCHEMA) throws SAXException {
		if (schema==null) {
			schema = getFactory().newSchema(new StreamSource(MY_SCHEMA));
		}
		return schema;
	}
	
	private URL getEsquemaURL() {
		if (esquemaURL==null)
			esquemaURL = XmlCtitSegaFactory.class.getResource(TRAFICO_SCHEMAS_SOLICITUD_REGISTRO_ENTRADA_CTIT_XSD);
		return esquemaURL; 
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
	 * Realiza el parseo de los datos XML que se reciben en la cadena de caracteres, almacenándolos
	 * en el objeto que devuelve. 
	 * 
	 * @param URL 
	 * @return Devuelve un objeto que contiene los datos del XML que ha recibido
	 * @throws JAXBException
	 */
	private Object fromURL(URL url) throws JAXBException {
		Object obj = null;
		
		Unmarshaller um = getContext().createUnmarshaller();
		try {
			obj = um.unmarshal(url);
		} catch(Exception e) {
			log.error("Error UNMARSHAL");
			log.error(e);
		}
		return obj;
	}	
	
	/**
	 * Obtiene el objeto {@link SolicitudRegistroEntrada} que contiene los datos del XML que recibe
	 * en el String
	 * 
	 * @param ruta 
	 * @return Devuelve un objeto {@link SolicitudRegistroEntrada} que contiene los datos del XML recibido
	 * @throws OegamExcepcion 
	 * @throws MalformedURLException 
	 * @throws JAXBException 
	 * @throws OegamExcepcion 
	 */
	public SolicitudRegistroEntrada getSolicitudRegistroEntradaPorNombreFichero(String nombreFichero) throws MalformedURLException, JAXBException, OegamExcepcion {
		SolicitudRegistroEntrada solicitudRegistroEntrada = null;
		URL url = new URL("file", new UtilesWSCTIT().getIpNodo().replace("\\", "").replace("\"",""), new UtilesWSCTIT().getRutaLocalLecturaXml()+nombreFichero);
		log.info(url.getHost()); 
		log.info(url.getPath());
		log.info(url.getPort()); 
		log.info(url.getProtocol());
		log.info(url.getFile());
		
		solicitudRegistroEntrada = (SolicitudRegistroEntrada)fromURL(url);
		if (solicitudRegistroEntrada==null) {
			log.error("no se pudo recuperar el xml para el segundo envío!");
			throw new RegistroEntradaExcepcion("no se pudo recuperar el xml para el segundo envío!"); 
		}
		return solicitudRegistroEntrada;
	}	
	
	/**
	 * 
	 * @param ficheroAenviar File de un xml
	 * @return El fichero xml en String
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public String xmlFileToString(File ficheroAenviar) throws FileNotFoundException  {
		byte[] by = null; 
		
		FileInputStream fis;
		fis = new FileInputStream(ficheroAenviar);
		try {
			by = IOUtils.toByteArray(fis);
		} catch (IOException e) {
			log.error(e);
		} 
		String  peticionXML = null;
		try {
			peticionXML = new String (by, "UTF-8");
		} catch (UnsupportedEncodingException e1){
			log.error(e1);
		}
		return peticionXML;
	}
	
	/**
	 * Método que valida un XML contra el xsd de Transferencia Telemática (Solicitud_Registro_Entrada_CTIT.xsd)
	 * @param fichero xml a validar
	 * @return Devolverá XML_CORRECTO si es válido, en cualquier otro caso devolverá el string de la excepción de validación
	 */
	public String validarXMLCtitSega(File fichero) {
    	// Constantes para validacion de Schemas   
    	try { 
    		Validator validator = getSchema(getEsquemaURL().getFile()).newValidator(); 
    	    XMLManejadorErrores errores = new XMLManejadorErrores();
    	    validator.setErrorHandler(errores);
    	    // validamos el XML, si no lo valida devolverá el string de la excepción, si lo valida devolverá 'CORRECTO'
    	    validator.validate(new StreamSource(fichero));
    	    if (errores.getListaErrores().size()>0) {
    	    	String mensaje = "";
    	    	log.error("Errores en la validacion del fichero de Transmision telematica CTIT: " + fichero.getName());
    	    	for (int i=0;i<errores.getListaErrores().size();i++) {
    	    		log.error(errores.getListaErrores().get(i));
    	    		if (i<errores.getListaErrores().size()-1) mensaje += errores.getListaErrores().get(i)+". ";
    	    		else mensaje += errores.getListaErrores().get(i);
    	    	}
    	    	log.error(errores.getLineasErrores());
    	    	return mensaje + " - Lineas: " + errores.getLineasErrores();
    	    }
    	} catch (SAXException saxEx){
    		log.error(saxEx); 
    		return saxEx.toString();
	  	} catch (Exception ex) {  
	  		log.error(ex);
	  		return ex.toString();
	  	} 
	  	return "CORRECTO";
	}
	
	/*
	 * Graba el marshal del objeto en un fichero que se recibe como parámetro:
	 */
	public void grabarEnFicheroUTF8(Object objeto,boolean formateado,String ruta,String encoding) {
		File fich = new File(ruta);
		utiles.borraFicheroSiExiste(fich.getPath()); 
		Marshaller m;
		try {
			m = getContext().createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, encoding);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formateado);
			m.marshal(objeto, fich);
		} catch (JAXBException e) {
			log.error(e);
		}		
	}	
}