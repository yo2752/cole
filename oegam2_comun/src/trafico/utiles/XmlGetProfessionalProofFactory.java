package trafico.utiles;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class XmlGetProfessionalProofFactory {

	private static final String UTF_8 = "UTF-8";
	private static final String NAME_CONTEXT = "trafico.beans.schemas.generated.getJustificantesProf.getProProof.generated";
	private static final String XML_ENCODING = UTF_8;
	
	private JAXBContext context;
	private static ILoggerOegam log = LoggerOegam.getLogger(XmlGetProfessionalProofFactory.class);
	
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
	public String toXML(Object xmlObject) throws JAXBException {
		StringWriter writer = new StringWriter();
		
		Marshaller m = getContext().createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING);
		m.marshal(xmlObject, writer);
		writer.flush();
		
		return writer.toString();
	}
}
