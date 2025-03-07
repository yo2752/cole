package es.globaltms.gestorDocumentos.ws;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * Manejador genérico para capturar las peticiones a un WS y almacenar la
 * request y el response en clientes generados con Apache CXF .
 * 
 * @author ext_drv
 *
 */
public abstract class GenericWSHandlerCXF implements SOAPHandler<SOAPMessageContext> {

	/* INICIO ATRIBUTOS/CONSTANTES */

	protected Date fechaFichero;
	protected BigDecimal numExpediente;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private UtilesFecha utilesFecha;

	/* FIN ATRIBUTOS/CONSTANTES */

	/* CONSTRUCTOR */
	public GenericWSHandlerCXF() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		fechaFichero = new Date();
	}

	public void init(SOAPMessageContext arg){

	}

	/* INICIO MÉTODOS PÚBLICOS */

	public boolean handleMessage(SOAPMessageContext arg0) {
		Boolean isRequest = (Boolean) arg0.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if(arg0.containsKey("numExpediente")){
			numExpediente = (BigDecimal) arg0.get("numExpediente");
		}

		if (isRequest){
			if (getTipoDocumentoRequest() != null && !getTipoDocumentoRequest().isEmpty()) {
				SOAPMessageContext messageContext = (SOAPMessageContext) arg0;
				try {
					byte[] xml = getXmlMessage(messageContext.getMessage());
					getLogger().info("Request que se envía: " + xml);

					Document doc = createDOMFromXML(xml);
					setDOMRequest(doc);

					if (formatearRequest()) {
						String encoding = (String) messageContext.getMessage().getProperty(SOAPMessage.CHARACTER_SET_ENCODING);
						String xmlFormateado = formatear(encoding == null ? new String(xml) : new String (xml, encoding));
						xml = new String (xmlFormateado.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);
					}

					guardarDocumento(getNombreFicheroRequest(), getTipoDocumentoRequest(), getSubtipoDocumentoRequest(), fechaFichero, xml, Boolean.TRUE);
	
				} catch (Exception e) {
					getLogger().error("No se pudo recuperar el XML del mensaje que se envía", e);
				}
			}

		} else { // Response
			if(getTipoDocumentoResponse() != null && !getTipoDocumentoResponse().isEmpty()){
				SOAPMessageContext messageContext = (SOAPMessageContext) arg0;
				try {
					byte[] xml = getXmlMessage(messageContext.getMessage());
					getLogger().info("Response que se recibe: " + xml);

					Document doc = createDOMFromXML(xml);
					setDOMResponse(doc);

					if (formatearResponse()) {
						String encoding = (String) messageContext.getMessage().getProperty(SOAPMessage.CHARACTER_SET_ENCODING);
						String xmlFormateado = formatear(encoding == null ? new String(xml) : new String (xml, encoding));
						xml = new String (xmlFormateado.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);
					}

					guardarDocumento(getNombreFicheroResponse(), getTipoDocumentoResponse(), getSubtipoDocumentoResponse(), fechaFichero, xml, Boolean.TRUE);

				} catch (Exception e) {
					getLogger().error("No se pudo recuperar el XML del mensaje que se recibe", e);
				}
			}
		}

		return true;

	}

	public boolean handleFault(SOAPMessageContext arg0) {
		SOAPMessageContext messageContext = (SOAPMessageContext) arg0;
		try {
			getLogger().error("Soap Fault: " + getXmlMessage(messageContext.getMessage()));
		} catch (Exception e) {
			getLogger().error("No se pudo recuperar el XML del fault", e);
		}
		return true;
	}

	public void close(MessageContext context) {
		// TODO Auto-generated method stub
	}

	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public byte[] getXmlMessage(SOAPMessage message) throws Exception {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		message.writeTo(os);
		return os.toByteArray();
//		String encoding = (String) message.getProperty(SOAPMessage.CHARACTER_SET_ENCODING);
//		if (encoding == null) {
//			return new String(os.toByteArray());
//		} else {
//			return new String(os.toByteArray(), encoding);
//		}
	}

	/* FIN MÉTODOS PÚBLICOS */

	/* INICIO MÉTODOS PRIVADOS */

	/**
	 * Crea un DOM a partir del parseo de un xml.
	 * 
	 * @param xml
	 * @return
	 */
	private Document createDOMFromXML(byte[] xml) {

		Document doc = null;

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream stream = new ByteArrayInputStream(xml);
			doc = db.parse(stream);

		} catch (ParserConfigurationException e) {
			getLogger().error("createDOMFromXML-ParserConfigurationException ", e);
		} catch (SAXException e) {
			getLogger().error("createDOMFromXML-SAXException ", e);
		} catch (IOException e) {
			getLogger().error("createDOMFromXML-IOException ", e);
		}

		return doc;
	}

	/**
	 * Método para formatear un xml.
	 * 
	 * @param xml
	 * @return
	 */
	public String formatear(String xml) {

		try {
			final InputSource src = new InputSource(new StringReader(xml));
			final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src)
					.getDocumentElement();
			final Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));

			final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			final LSSerializer writer = impl.createLSSerializer();

			writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
			writer.getDomConfig().setParameter("xml-declaration", keepDeclaration);
			return writer.writeToString(document);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Método para guardar un documento xml.
	 * 
	 * @param nombreFichero
	 * @param tipoDocumento
	 * @param subtipoDocumento
	 * @param fecha
	 * @param xml
	 * @param sobreescribir
	 * @return
	 */
	private Boolean guardarDocumento(String nombreFichero, String tipoDocumento, String subtipoDocumento, Date fecha,
			byte[] xmlByte, Boolean sobreescribir) {

		getLogger().info("INICIO - guardarDocumento(): " + "nombreFichero='" + limpiaStringSiNulo(nombreFichero) + "', "
				+ "tipoDocumento='" + limpiaStringSiNulo(tipoDocumento) + "', " + "subtipoDocumento='"
				+ limpiaStringSiNulo(subtipoDocumento) + "'");

		Boolean resultadoGuardar = Boolean.TRUE;

		try {
			// Se guarda el Documento Base en el disco
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(tipoDocumento);
			fichero.setSubTipo(subtipoDocumento);
			fichero.setFecha(utilesFecha.getFechaConDate(fecha));
			fichero.setNombreDocumento(nombreFichero);
			fichero.setSobreescribir(Boolean.TRUE.equals(sobreescribir));
			fichero.setFicheroByte(xmlByte);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);

			gestorDocumentos.guardarByte(fichero);

		} catch (OegamExcepcion e) {
			getLogger().error("Error guardando el documento ", e);
			resultadoGuardar = Boolean.FALSE;
		} finally {
			getLogger().info("FIN - guardarDocumento()");
		}

		return resultadoGuardar;
	}

	/**
	 * Trata un strign, devolviendo '' si es nulo.
	 * 
	 * @param string
	 *            string.
	 * @return String - resultado.
	 */
	private String limpiaStringSiNulo(String string) {
		return (string != null) ? string : "";
	}

	/* FIN MÉTODOS PRIVADOS */

	/* INICIO GETTERS/SETTERS */

	/* FIN GETTERS/SETTERS */

	/* INICIO METODOS SOBREESCRIBIBLES */
	protected boolean formatearRequest() {
		return true;
	}

	protected boolean formatearResponse() {
		return true;
	}
	/* FIN METODOS SOBREESCRIBIBLES */

	/* INICIO MÉTODOS ABSTRACTOS */

	public abstract String getTipoDocumentoRequest();

	public abstract String getTipoDocumentoResponse();

	public abstract String getSubtipoDocumentoRequest();

	public abstract String getSubtipoDocumentoResponse();

	public abstract String getNombreFicheroRequest();

	public abstract String getNombreFicheroResponse();

	public abstract void setDOMRequest(Document doc);

	public abstract void setDOMResponse(Document doc);

	public abstract ILoggerOegam getLogger();

	/* FIN MÉTODOS ABSTRACTOS */

}