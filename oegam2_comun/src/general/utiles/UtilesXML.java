package general.utiles;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import trafico.utiles.XMLManejadorErrores;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

public class UtilesXML {

	private static Schema schema;
	private static SchemaFactory factory;
	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesXML.class);

	/**
	 * Método que valida un XML contra un xsd cuya ruta se recibe como parámetro
	 * @param fichero xml a validar
	 * @param rutaEsquema ruta del esquema contra el que validar
	 * @throws XmlNoValidoExcepcion 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void validarXML(File fichero, String rutaEsquema) throws XmlNoValidoExcepcion, IOException {
		try{
			Schema schema = getSchema(rutaEsquema);
			Validator validator = schema.newValidator();
			XMLManejadorErrores errores = new XMLManejadorErrores();
			validator.setErrorHandler(errores);
			validator.validate(new StreamSource(fichero));
			if (!errores.getListaErrores().isEmpty()) {
				String mensaje = "";
				log.error("ERROR VALIDACION FICHERO EXPORTACION");
				log.error("Errores en la validacion del fichero: " + fichero.getName());
				for (int i = 0 ; i < errores.getListaErrores().size() ; i++) {
					log.error(errores.getListaErrores().get(i));
					if (i < errores.getListaErrores().size()-1){
						mensaje += errores.getListaErrores().get(i)+". ";
					} else {
						mensaje += errores.getListaErrores().get(i);
					}
				}
				log.error(errores.getLineasErrores());
				throw new XmlNoValidoExcepcion(mensaje + " - Lineas: " + errores.getLineasErrores());
			}
		} catch (Exception e) {
			throw new XmlNoValidoExcepcion(e.getMessage());
		}
	}

	/**
	 * Método que comprueba si el manejador contiene errores y los recupera
	 * @param fichero xml a validar
	 * @param rutaEsquema ruta del esquema contra el que validar
	 * @throws XmlNoValidoExcepcion 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void comprobarErroresValidacion(XMLManejadorErrores errores, String rutaEsquema) throws XmlNoValidoExcepcion, IOException {
		try{
			if (!errores.getListaErrores().isEmpty()) {
				String mensaje = "";
				log.error("ERROR VALIDACION FICHERO EXPORTACION");
				log.error("Errores en la validacion del fichero contra: " + rutaEsquema);
				for (int i = 0; i < errores.getListaErrores().size(); i++) {
					log.error(errores.getListaErrores().get(i));
					if (i < errores.getListaErrores().size()-1){
						mensaje += errores.getListaErrores().get(i)+". ";
					} else {
						mensaje += errores.getListaErrores().get(i);
					}
				}
				log.error(errores.getLineasErrores());
				throw new XmlNoValidoExcepcion(mensaje + " - Lineas: " + errores.getLineasErrores());
			}
		} catch (Exception e) {
			throw new XmlNoValidoExcepcion(e.getMessage());
		}
	}

	private static SchemaFactory getFactory() {
		if (factory==null) {
			factory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
		}
		return factory;
	}

	public static Schema getSchema(String mySchema) throws SAXException {
		if (schema==null) {
			schema = getFactory().newSchema(new StreamSource(mySchema));
		}
		return schema;
	}

}