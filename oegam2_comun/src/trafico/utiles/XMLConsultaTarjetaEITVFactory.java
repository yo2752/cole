package trafico.utiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import trafico.beans.schemas.generated.eitv.generated.*;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


public class XMLConsultaTarjetaEITVFactory {

	public static final String NAME_CONTEXT = "trafico.beans.schemas.generated.eitv.generated";
	private static final String XML_ENCODING = "UTF-8";
	
	private JAXBContext context;
	
	private static ILoggerOegam log = LoggerOegam.getLogger(XMLConsultaTarjetaEITVFactory.class);
	
	public JAXBContext getContext() throws JAXBException {
		if (context == null) {
			context = JAXBContext.newInstance(NAME_CONTEXT);
		}
		return context;
	}
	
	/**
	 * Método que obtiene el String XML que se monta a partir de un objeto al que se le realiza un marshall.
	 * 
	 * @param xmlObject Objeto que contiene los datos que se quieren pasar a XML
	 * @return Devuelve un String que contiene el XML
	 * @throws JAXBException
	 */
	private String toXML(Object xmlObject) throws JAXBException {
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
	public String toXML(ConsultaTarjeta consultaTarjeta) {
		String xmlString = null;
		try {
			xmlString = toXML((Object)consultaTarjeta);
		} catch(Exception e) {
			log.error("Error al generar XML del objeto SolicitudRegistroEntrada");
			log.error(e);
		}
		return xmlString;
	}

	public String xmlFileToString(File ficheroAenviar) throws FileNotFoundException {
		return UtilesXmlFactories.xmlFileToString(ficheroAenviar);
	}
}