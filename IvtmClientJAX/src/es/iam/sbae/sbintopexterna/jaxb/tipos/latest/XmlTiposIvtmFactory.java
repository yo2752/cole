package es.iam.sbae.sbintopexterna.jaxb.tipos.latest;

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
import org.xml.sax.SAXException;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class XmlTiposIvtmFactory {
	private static final String XML_ENCODING = "UTF-8";
	public static final String NAME_CONTEXT = "es.iam.sbae.sbintopexterna.jaxb.tipos.latest";
	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final String SCHEMAS_TIPOS_IVTM_XSD = "/resources/xsd/tipos.xsd";
	private static final String AVISO_NIF_PATRON_NO_VALIDO = "cvc-pattern-valid";
	private URL esquemaURL;

	private Schema schema;
	private JAXBContext context;
	private SchemaFactory factory;
	private ILoggerOegam log = LoggerOegam.getLogger(XmlTiposIvtmFactory.class);

	public JAXBContext getContext() throws JAXBException {
		if (context == null) {
			context = JAXBContext.newInstance(NAME_CONTEXT);
		}
		return context;
	}

	private SchemaFactory getFactory() {
		if (factory == null) {
			factory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
		}
		return factory;
	}

	private URL getEsquemaURL() {
		if (esquemaURL == null) {
			esquemaURL = XmlTiposIvtmFactory.class.getResource(SCHEMAS_TIPOS_IVTM_XSD);
		}
		return esquemaURL;
	}

	/**
	 * Método que obtiene el String XML que se monta a partir de un objeto al que se
	 * le realiza un marshall.
	 * 
	 * @param xmlObject Objeto que contiene los datos que se quieren pasar a XML
	 * @return Devuelve un String que contiene el XML
	 * @throws JAXBException
	 */
	public String toXML(Object xmlObject) throws JAXBException {
		StringWriter writer = new StringWriter();

		Marshaller m = getContext().createMarshaller();
		m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING);
		m.marshal(xmlObject, writer);
		writer.flush();

		return writer.toString();
	}

	private Schema getSchema(String mySchema) throws SAXException {
		if (schema == null) {
			schema = getFactory().newSchema(new StreamSource(mySchema));
		}
		return schema;
	}

	/**
	 * Método que genera el String con el formato XML que contiene los datos del
	 * objeto {@link SolicitudRegistroEntrada} que recibe como parámetro.
	 * 
	 * @param SolicitudRegistroEntrada Objeto {@link ConsultaDeudasIVTM} que
	 *                                 contiene los datos que se quieren pasar a XML
	 * @return Devuelve el String que contiene el XML
	 */
	public String toXML(ConsultaDeudasIVTM consultaDeudasIVTM) {
		String xmlString = null;
		try {
			xmlString = toXML((Object) consultaDeudasIVTM);
		} catch (Exception e) {
			log.error("Error al generar XML del objeto ConsultaDeudasIVTM");
			log.error(e);
		}
		return xmlString;
	}

	/****************************************/
	/* Métodos necesarios para el simulador */
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
		} catch (IOException e) {
			log.error(e);
		}
		String peticionXML = null;
		try {
			peticionXML = new String(by, XML_ENCODING);
		} catch (UnsupportedEncodingException e1) {
			log.error(e1);
		}
		return peticionXML;
	}

	/**
	 * Método que valida un XML contra el xsd del
	 * 
	 * @param fichero xml a validar
	 * @return Devolverá XML_CORRECTO si es válido, en cualquier otro caso devolverá
	 *         el string de la excepción de validación
	 * @throws OegamExcepcion
	 */
	public void validarXML(File fichero) throws OegamExcepcion {
		Validator validator;
		String mensaje;
		String snif;
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