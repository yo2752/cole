package org.gestoresmadrid.oegamConversiones.schemas.utiles;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.gestoresmadrid.oegamConversiones.jaxb.solicitud.FORMATOOEGAM2SOLICITUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

@Component
public class XMLSolicitudFactory implements Serializable {

	private static final long serialVersionUID = -501140903779341289L;

	private static final String NAME_CONTEXT = "org.gestoresmadrid.oegamConversiones.jaxb.solicitud";

	private Schema schema;
	private JAXBContext context;
	private SchemaFactory factory;
	private final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	@Autowired
	XMLManejadorErrores xmlManejadorErrores;

	private static ILoggerOegam log = LoggerOegam.getLogger(XMLSolicitudFactory.class);

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

	public Schema getSchema(String MY_SCHEMA) throws SAXException {
		if (schema == null) {
			schema = getFactory().newSchema(new StreamSource(MY_SCHEMA));
		}
		return schema;
	}

	/**
	 * Obtiene el objeto {@link FORMATOOEGAM2SOLICITUD} que contiene los datos del XML que recibe en el String
	 * @param xmlString String con formato XML que contiene la petición realizada
	 * @return Devuelve un objeto {@link FORMATOOEGAM2SOLICITUD} que contiene los datos del XML recibido
	 */
	public FORMATOOEGAM2SOLICITUD getFormatoSolicitud(String xmlString) {
		FORMATOOEGAM2SOLICITUD formatoSolicitud = null;
		try {
			formatoSolicitud = (FORMATOOEGAM2SOLICITUD) fromXML(xmlString);
		} catch (Exception e) {
			log.error("Error obteniendo FORMATOOEGAM2SOLICITUD, error: ", e);
		}
		return formatoSolicitud;
	}

	/**
	 * Obtiene el objeto {@link FORMFORMATOOEGAM2SOLICITUDe contiene los datos del XML que recibe en el String
	 * @param fichero Fichero con formato XML que contiene la petición realizada
	 * @return Devuelve un objeto {@link FORMATOOEGAM2SOLICITUD} que contiene los datos del XML recibido
	 */
	public FORMATOOEGAM2SOLICITUD getFormatoSolicitud(File fichero) {
		FORMATOOEGAM2SOLICITUD formatoSol = null;
		try {
			formatoSol = (FORMATOOEGAM2SOLICITUD) fromXML(fichero);
		} catch (Exception e) {
			log.error("Error obteniendo FORMATOOEGAM2SOLICITUD, error: ", e);
		}
		return formatoSol;
	}

	/**
	 * Realiza el parseo de los datos XML que se reciben en la cadena de caracteres, almacenándolos en el objeto que devuelve.
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
		} catch (Exception e) {
			log.error("Error UNMARSHAL FORMATOGA, error: ", e);
		}
		try {
			bais.close();
		} catch (IOException ioe) {
			// Error al cerrar el flujo de bytes
		}
		return obj;
	}

	/**
	 * Realiza el parseo de los datos XML que se reciben en la cadena de caracteres, almacenándolos en el objeto que devuelve.
	 * @param xmlString String con formato XML
	 * @return Devuelve un objeto que contiene los datos del XML que ha recibido
	 * @throws JAXBException
	 */
	private Object fromXML(File fichero) throws JAXBException {
		Object obj = null;

		Unmarshaller um = getContext().createUnmarshaller();
		try {
			obj = um.unmarshal(fichero);
		} catch (Exception e) {
			log.error("Error UNMARSHAL FORMATOOEGAM2SOLICITUD, error: ", e);
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
	public void validarXMLFORMATOOEGAM2INTEVE(File fichero, String rutaEsquema) throws XmlNoValidoExcepcion, IOException {
		try {
			Schema schema = getSchema(rutaEsquema);
			Validator validator = schema.newValidator();
			xmlManejadorErrores.iniciar();
			validator.setErrorHandler(xmlManejadorErrores);
			validator.validate(new StreamSource(fichero));
			getLineasErroneas(fichero.getName());
		} catch (Exception e) {
			throw new XmlNoValidoExcepcion(e.getMessage());
		}
	}

	private void getLineasErroneas(String nombreFicher) throws XmlNoValidoExcepcion {
		if (xmlManejadorErrores.getListaErrores().size() > 0) {
			String mensaje = "";
			log.error("Errores en la validacion del fichero: " + nombreFicher);
			for (int i = 0; i < xmlManejadorErrores.getListaErrores().size(); i++) {
				log.error(xmlManejadorErrores.getListaErrores().get(i));
				if (i < xmlManejadorErrores.getListaErrores().size() - 1) {
					mensaje += xmlManejadorErrores.getListaErrores().get(i) + " -";
				} else {
					mensaje += xmlManejadorErrores.getListaErrores().get(i);
				}
			}
			log.error(xmlManejadorErrores.getLineasErrores());
			throw new XmlNoValidoExcepcion(mensaje + " - Lineas: " + xmlManejadorErrores.getLineasErrores());
		}
	}
}