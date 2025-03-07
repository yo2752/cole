package trafico.utiles;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import trafico.beans.schemas.generated.eitv.generated.xmlDataVehiculos.Vehiculo;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class XmlDataVehiculosFactory {

	private static final String NAME_CONTEXT = "trafico.beans.schemas.generated.eitv.generated.xmlDataVehiculos";
	
	private JAXBContext context;
	private static ILoggerOegam log = LoggerOegam.getLogger(XmlDataVehiculosFactory.class);
	
	private JAXBContext getContext() throws JAXBException{
		if (context == null) {
			context = JAXBContext.newInstance(NAME_CONTEXT);
		}
		return context;
	}
	
	/****************************************/
	/* Metodos necesarios para el simulador */
	/****************************************/
	/**
	 * Obtiene el objeto {@link Vehiculo} que contiene los datos del XML que recibe
	 * en el String
	 * 
	 * @param xmlString String con formato XML que contiene la petición realizada
	 * @return Devuelve un objeto {@link Vehiculo} que contiene los datos del XML recibido
	 */
	public Vehiculo getVehiculo(String xmlString) {
		Vehiculo solicitudTramite = null;
		try {
			solicitudTramite = (Vehiculo)fromXML(xmlString);
		} catch(Exception e) {
			log.error("Error obteniendo RegistroResultado");
			log.error(e);
		}
		return solicitudTramite;
	}
	
	/**
	 * Realiza el parseo de los datos XML que se reciben en la cadena de caracteres, almacenándolos
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
}
