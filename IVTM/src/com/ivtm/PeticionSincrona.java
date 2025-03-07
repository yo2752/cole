package com.ivtm;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.xml.sax.SAXException;

import com.ivtm.constantes.ConstantesIVTMWS;

import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap;
import es.map.www.xml_schemas.PeticionSincronaLocator;
import es.map.www.xml_schemas.PeticionSincronaSoapBindingStub;
import utilidades.web.OegamExcepcion;


public class PeticionSincrona {

	protected static final Logger log = Logger.getLogger(PeticionSincrona.class);
	protected String url;
	protected String stringDatosEspecificos = "";
	protected String subtipoFicheroRespuesta = "";
	protected String rutaEsquema = "";

	@Autowired
	protected GestorDocumentos gestorDocumentos;

	public PeticionSincrona() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public PeticionSincrona(String url){
		this.url = url;
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public Object doPeticion(Object peticion, String idPeticion) throws AxisFault, JAXBException, ParserConfigurationException,
	SAXException, IOException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError,
	ServiceException, Exception, OegamExcepcion{
		log.info(ConstantesIVTMWS.TEXTO_LOG_PROCESO + " Entrando en llamada al WS");
		PeticionSincronaSoapBindingStub req = new PeticionSincronaSoapBindingStub();

		PeticionSincronaLocator loc = new PeticionSincronaLocator();
		req = (PeticionSincronaSoapBindingStub) loc.getPeticionSincrona(new URL(url));
		req.setTimeout(ConstantesIVTMWS.TIEMPO_TIMEOUT);
		log.info(ConstantesIVTMWS.TEXTO_LOG_PROCESO+" -- Construyendo SOAP IVTM");
		String xml = generaXml(peticion, new BigDecimal(idPeticion));
		log.info(ConstantesIVTMWS.TEXTO_LOG_PROCESO+" IVTM Madrid -- Llamada al WS IVTM ");

		Object response = hacerPeticion(req, peticion, xml);
		log.info(ConstantesIVTMWS.TEXTO_LOG_PROCESO+ "IVTM Madrid -- Fin de llamada a WS  IVTM");
		return response;
	}

	protected Object hacerPeticion(PeticionSincronaSoapBindingStub req, Object peticion, String xml) throws ExcepcionMap, RemoteException {
		// Este método debe ser reescrito en cada clase que herede de esta
		return null;
	}

	protected String generaXml(Object peticion, BigDecimal idPeticion)throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException, JAXBException, OegamExcepcion {
		JAXBContext context = JAXBContext.newInstance(peticion.getClass());
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(PeticionSincronaAlta.class.getClassLoader().getResource(this.rutaEsquema));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setSchema(schema);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		StringWriter writer = new StringWriter();
		marshaller.marshal(peticion, writer);
		writer.flush();
		String xml = writer.toString();
		xml = xml.replaceFirst(
			" xmlns:ns2=\"http://www.map.es/scsp/esquemas/datosespecificos\"",
			" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");
		xml = xml.replaceFirst("DatosEspecificos",
				this.stringDatosEspecificos
			+ "http://www.map.es/scsp/esquemas/datosespecificos\"");

		xml = ConstantesIVTMWS.ENVINI + "<soapenv:Body>"
				+ xml.substring(xml.indexOf("<Peticion"))
				+ "</soapenv:Body>" + ConstantesIVTMWS.ENVFIN;
		//TODO Gestor Documentos.
		gestorDocumentos.guardarFichero(ConstantesIVTMWS.TIPO_IVTM_GESTOR_FICHEROS, this.subtipoFicheroRespuesta , Utilidades.transformExpedienteFecha(idPeticion), ConstantesIVTMWS.IVTM_INICIO_NOMBRE_FICHERO_GUARDAR_PETICION + idPeticion.toString(), ".xml", (ConstantesIVTMWS.CABECERA_XML+xml).getBytes());
		return xml;
	}
}