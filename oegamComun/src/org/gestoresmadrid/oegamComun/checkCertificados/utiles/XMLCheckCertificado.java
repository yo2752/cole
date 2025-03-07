package org.gestoresmadrid.oegamComun.checkCertificados.utiles;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.gestoresmadrid.oegamComun.checkCertificados.jaxb.SolicitudPruebaCertificado;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class XMLCheckCertificado implements Serializable{

	private static final long serialVersionUID = 892440191345481644L;
	
	public static final String NAME_CONTEXT = "org.gestoresmadrid.oegamComun.checkCertificados.jaxb";
	private static final String XML_ENCODING = "UTF-8";
	private static ILoggerOegam log = LoggerOegam.getLogger(XMLCheckCertificado.class);
	private JAXBContext context;
	
	private JAXBContext getContext() throws JAXBException, ClassNotFoundException{
		if (context == null){
			context = JAXBContext.newInstance(getClases(NAME_CONTEXT));
		}
		return context;
	}
	
	@SuppressWarnings("rawtypes")
	private Class[] getClases(String paquete) throws ClassNotFoundException {
		ArrayList<Class> arrayReturn = new ArrayList<Class>();
		Reflections reflections = new Reflections(new ConfigurationBuilder().setScanners(new SubTypesScanner(false /* don't exclude Object.class */), 
        	new ResourcesScanner()).setUrls(ClasspathHelper.forPackage(paquete))
        	.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(paquete)))); 
		Object[] clasesList = ((HashSet)reflections.getSubTypesOf(Object.class)).toArray();
		for (int cont = 0; cont < clasesList.length; cont ++) {
			String nombreClase = clasesList[cont].toString();
			if(nombreClase.contains("class")) {
				nombreClase = nombreClase.substring("class ".length());
				arrayReturn.add(Class.forName(nombreClase));
			}
		}
		Class[] array = new Class[arrayReturn.size()];
		return arrayReturn.toArray(array);
	}
	
	/**
	 * Método que obtiene el String XML que se monta a partir de un objeto al que se le realiza un marshall.
	 * 
	 * @param xmlObject Objeto que contiene los datos que se quieren pasar a XML
	 * @return Devuelve un String que contiene el XML
	 * @throws JAXBException
	 * @throws ClassNotFoundException 
	 */
	private String toXML(Object xmlObject) throws JAXBException, ClassNotFoundException {
		StringWriter writer = new StringWriter();
		
		Marshaller m = getContext().createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING);
		m.marshal(xmlObject, writer);
		writer.flush();
		return writer.toString();
	}
	
	public String toXMLSolicitudPruebaCert(SolicitudPruebaCertificado solicitudPruebaCertificado) {
		String xmlString = null;
		try {
			xmlString = toXML((Object)solicitudPruebaCertificado);
		}
		catch(Exception e) {
			log.error("error al crear el xml de la prueba para obtener la caducidad del certificado");
			log.error(e);
		}
		return xmlString;
	}
	
}
