package trafico.utiles;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class XMLGAFactory {

	//Contextos importación
	private JAXBContext CETImportacionContext = null;
	
	private JAXBContext MatriculacionContext = null;
	private JAXBContext MatriculacionAntiguaContext = null;
	private JAXBContext MatriculacionMatWContext = null;

	//Contextos exportación
	private JAXBContext TransmisionExportacionContext = null; 
	private JAXBContext BajaExportacionContext = null;
	private JAXBContext DuplicadoExportacionContext = null;
	private JAXBContext SolicitudExportacionContext = null;
	
	private JAXBContext FactSigaExportacionContext = null;
	
	private JAXBContext ConsultaEEFFContext = null;
	
	private Schema schema; 
	private SchemaFactory factory;
	private final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	
	public JAXBContext getConsultaEEFFContext() throws JAXBException {
		if(ConsultaEEFFContext == null){
			ConsultaEEFFContext = JAXBContext.newInstance("trafico.beans.jaxb.consultaEEFF");
		}
		return ConsultaEEFFContext;
	}

	public JAXBContext getMatriculacionContext() throws JAXBException {
		if (MatriculacionContext == null) {
			MatriculacionContext = JAXBContext.newInstance("trafico.beans.jaxb.matriculacion");
		}
		return MatriculacionContext;
	}
	
	public JAXBContext getMatriculacionAntiguaContext() throws JAXBException {
		if (MatriculacionAntiguaContext == null) {
			MatriculacionAntiguaContext = JAXBContext.newInstance("trafico.beans.jaxb.matriculacion.antigua");
		}
		return MatriculacionAntiguaContext;
	}
	
	public JAXBContext getMatriculacionMatWContext() throws JAXBException {
		if (MatriculacionMatWContext == null) {
			MatriculacionMatWContext = JAXBContext.newInstance("trafico.beans.jaxb.matw");
		}
		return MatriculacionMatWContext;
	}
	
	public JAXBContext getCETImportacionContext()  throws JAXBException {
		if (CETImportacionContext == null) {
			CETImportacionContext = JAXBContext.newInstance("trafico.beans.jaxb.cet_import");
		}
		return CETImportacionContext;
	}
	
	public JAXBContext getTransExportacionContext() throws JAXBException {
		if (TransmisionExportacionContext == null) {
			TransmisionExportacionContext = JAXBContext.newInstance("trafico.beans.jaxb.ga_trans_exportar");
		}
		return TransmisionExportacionContext;
	}
	
	public JAXBContext getBajaExportacionContext() throws JAXBException {
		if (BajaExportacionContext == null) {
			BajaExportacionContext = JAXBContext.newInstance("trafico.beans.jaxb.baja");
		}
		return BajaExportacionContext;
	}
	
	public JAXBContext getDuplicadoExportacionContext() throws JAXBException{
		if (DuplicadoExportacionContext == null){
			DuplicadoExportacionContext = JAXBContext.newInstance("trafico.beans.jaxb.duplicados");
		}
		return DuplicadoExportacionContext;
		
	}
	
	public JAXBContext getFactSigaExportacionContext() throws JAXBException {
		if (FactSigaExportacionContext == null) {
			FactSigaExportacionContext = JAXBContext.newInstance("facturacion.beans.jaxb.siga_export");
		}
		return FactSigaExportacionContext;
	}
	// Mantis 14125. David Sierra
	public JAXBContext getSolicitudExportacionContext() throws JAXBException{
		if (SolicitudExportacionContext == null){
			SolicitudExportacionContext = JAXBContext.newInstance("trafico.beans.jaxb.solicitud");
		}
		return SolicitudExportacionContext;
		
	}
	
	private SchemaFactory getFactory() {
		if (factory==null) {
			factory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
		}
		return factory;
	}

	public Schema getSchema(String MY_SCHEMA) throws SAXException {
		if (schema==null) {
			schema = getFactory().newSchema(new StreamSource(MY_SCHEMA));
		}
		return schema;
	}
}