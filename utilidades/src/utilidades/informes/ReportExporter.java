package utilidades.informes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sourceforge.barbecue.env.EnvironmentFactory;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ReportExporter {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ReportExporter.class);
	private static final String LOAD_ENVIRONMENT_OEGAM = "report.load.environment.oegam";

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public ReportExporter() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public Document generarDatasource (String xml) throws ParserConfigurationException {
		Document document = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Un factory
		/*factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");*/
		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(LOAD_ENVIRONMENT_OEGAM))) {
			EnvironmentFactory.setDefaultEnvironment(new EnvironmentDefaultOegam());
		}
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			log.error("Se ha producido un error: ", e);
			throw new ParserConfigurationException ("Se ha producido un error al generar la conexion de datos.");
		} // El documento
		try {
			document = builder.parse(new InputSource(new StringReader(xml)));
		} catch (SAXException e) {
			log.error("Se ha producido un error: ", e);
			throw new ParserConfigurationException ("Se ha producido un error al generar la conexion de datos.");
		} catch (IOException e) {
			log.error("Se ha producido un error: ", e);
			throw new ParserConfigurationException ("Se ha producido un error al generar la conexion de datos.");
		} // Aquí le paso al Document

		return document;
	}

	/**
	 * Método que nos genera un informe en el formato indicado con los datos pasados por parámetros.
	 * 
	 * @param rutaInforme Ruta en la que se aloja la plantilla del informe
	 * @param nombreInforme nombre de la plantilla
	 * @param formato formato de salida del informe generado
	 * @param xmlDatos xml con los datos a imprimir
	 * @param params parametros de entrada del informe
	 * @param beanCollection Coleccion de beans con los datos del informe
	 * @return
	 * @throws JRException 
	 * @throws ParserConfigurationException 
	 * @throws UnknownHostException 
	 */
	public byte[] generarInforme(String rutaInforme, String nombreInforme, String formato, String xmlDatos,
			String tagCabecera, Map<String, Object> params, Collection<?> beanCollection)
			throws ParserConfigurationException, JRException {
		byte[] byteArray = null;

		if (params == null) {
			params = new HashMap<String, Object>();
		}

		// Este data source es para cuando usamos como fuente de datos un fichero XML
		if (xmlDatos != null) {
			try {
				Document document = generarDatasource(xmlDatos);
				params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
			} catch (Exception e) {
				log.error("Error al generar el informe solcitado. No se han encontrado datos.", e);
				throw new JRException("Error al generar el informe solcitado. No se han encontrado datos.", e);
			}
		}

		if (beanCollection != null) {
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(beanCollection);
			params.put(JRParameter.REPORT_DATA_SOURCE, dataSource);
		}

		JasperPrint jasperPrint;
		try {
			jasperPrint = JasperFillManager.fillReport(rutaInforme + nombreInforme + ".jasper", params);
		} catch (JRRuntimeException e) {
			log.error("Se ha producido una excepcion al exportar el informe. " + e);
			throw new JRException("Error al exportar el informe solicitado (" + e.getMessage() + ")"); 
		}

		// Devolvemos un array de bytes

		if (formato.equalsIgnoreCase("xls")) {
			// Generamos el informe en formato de Excel
			try {
				JRXlsExporter jasperXlsExportMgr = new JRXlsExporter();

				ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
				jasperXlsExportMgr.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				jasperXlsExportMgr.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
				jasperXlsExportMgr.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				jasperXlsExportMgr.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, java.lang.Boolean.TRUE);
				jasperXlsExportMgr.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
				jasperXlsExportMgr.exportReport();
				byteArray = xlsReport.toByteArray();
			} catch (JRException jex) {
				log.error("Se ha producido una excepcion al exportar el informe. " + jex);
				throw new JRException("Error al exportar el informe solicitado (" + jex.getMessage() + ")");
			}
		} else if (formato.equalsIgnoreCase("rtf")) {
			// Generamos el informe en formato de texto enriquecido
			try {
				JRRtfExporter jasperRtfExportMgr = new JRRtfExporter();

				ByteArrayOutputStream rtfReport = new ByteArrayOutputStream();
				jasperRtfExportMgr.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				jasperRtfExportMgr.setParameter(JRExporterParameter.OUTPUT_STREAM, rtfReport);
				jasperRtfExportMgr.exportReport();
				byteArray = rtfReport.toByteArray();
			} catch (JRException e) {
				log.error("Se ha producido una excepcion al exportar el informe. " + e);
				throw new JRException("Error al exportar el informe solicitado (" + e.getMessage() + ")");
			}
		} else if (formato.equalsIgnoreCase("docx")) {
			// Generamos el informe en formato Docx
			try {
				JRDocxExporter jasperDocxExportMgr = new JRDocxExporter();

				ByteArrayOutputStream rtfReport = new ByteArrayOutputStream();
				jasperDocxExportMgr.setParameter( JRExporterParameter.JASPER_PRINT, jasperPrint);
				jasperDocxExportMgr.setParameter( JRExporterParameter.OUTPUT_STREAM, rtfReport);
				jasperDocxExportMgr.exportReport();
				byteArray = rtfReport.toByteArray();
			} catch (JRException e) {
				log.error("Se ha producido una excepcion al exportar el informe. " + e);
				throw new JRException("Error al exportar el informe solicitado (" + e.getMessage() + ")");
			}
		} else if (formato.equalsIgnoreCase("xml")) {
			// Generamos el informe en formato de XML
			try {
				JRXmlExporter jasperXmlExportMgr = new JRXmlExporter();

				ByteArrayOutputStream xmlReport = new ByteArrayOutputStream();
				jasperXmlExportMgr.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				jasperXmlExportMgr.setParameter(JRXmlExporterParameter.IS_EMBEDDING_IMAGES, java.lang.Boolean.TRUE);
				jasperXmlExportMgr.setParameter(JRExporterParameter.OUTPUT_STREAM, xmlReport);
				jasperXmlExportMgr.exportReport();
				byteArray = xmlReport.toByteArray();
			} catch (JRException e) {
				log.error("Se ha producido una excepcion al exportar el informe. " + e);
				throw new JRException("Error al exportar el informe solicitado (" + e.getMessage() + ")");
			}
		} else if (formato.equalsIgnoreCase("csv")) {
			// Generamos el informe en formato CSV
			try {
				JRCsvExporter jasperCsvExportMgr = new JRCsvExporter();

				ByteArrayOutputStream csvReport = new ByteArrayOutputStream();
				jasperCsvExportMgr.setParameter( JRExporterParameter.JASPER_PRINT, jasperPrint);
				jasperCsvExportMgr.setParameter( JRExporterParameter.OUTPUT_STREAM, csvReport);
				jasperCsvExportMgr.exportReport();
				byteArray = csvReport.toByteArray();
			} catch (JRException e) {
				log.error("Se ha producido una excepcion al exportar el informe. " + e);
				throw new JRException("Error al exportar el informe solicitado (" + e.getMessage() + ")");
			}
		} else if (formato.equalsIgnoreCase("html")) {
			// Generamos el informe en formato HTML
			try {
				JRHtmlExporter jasperHtmlExportMgr = new JRHtmlExporter();

				ByteArrayOutputStream htmlReport = new ByteArrayOutputStream();
				jasperHtmlExportMgr.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				jasperHtmlExportMgr.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, java.lang.Boolean.FALSE);
				jasperHtmlExportMgr.setParameter(JRExporterParameter.OUTPUT_STREAM, htmlReport);
				jasperHtmlExportMgr.exportReport();
				byteArray = htmlReport.toByteArray();
			} catch (JRException e) {
				log.error("Se ha producido una excepcion al exportar el informe. " + e);
				throw new JRException("Error al exportar el informe solicitado (" + e.getMessage() + ")");
			}
		} else {
			// Generamos el formato PDF por defecto.
			try {
				byteArray = JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				log.error("Se ha producido una excepcion al exportar el informe. " + e);
				throw new JRException("Error al exportar el informe solicitado (" + e.getMessage() + ")");
			}
		}
		return byteArray;
	}

}