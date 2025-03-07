package com.matriculasWS.servicioWeb;


import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import javax.xml.bind.DatatypeConverter;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.SOAPPart;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.utils.Mapping;
import org.apache.axis.utils.XMLUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Clase encargada de securizar los mensajes SOAP de petici�n realizados desde
 * un cliente.
 *
 *
 */
public class ClientHandler extends BasicHandler {
	static{
		org.apache.xml.security.Init.init();
	}

	private static final long serialVersionUID = 1L;

	// Opciones de seguridad

	// Localizaci�n del keystore con certificado y clave privada de usuario
	private String keystoreLocation = null;

	// Tipo de keystore
	private String keystoreType = null;

	// Clave del keystore
	private String keystorePassword = null;

	// Alias del certificado usado para firmar el tag soapBody de la petici�n y
	// que ser� alojado en el token BinarySecurityToken
	private String keystoreCertAlias = null;

	// Password del certificado usado para firmar el tag soapBody de la petici�n
	// y que ser� alojado en el token BinarySecurityToken
	private String keystoreCertPassword = null;
	
	private KeyStore almacen;
	private PrivateKey clavePrivada;
	private PublicKey clavePublica;
	private java.security.cert.X509Certificate certificado;

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	/**
	 * Constructor que inicializa el atributo securityOption
	 *
	 * @param securityOption
	 *            opci�n de seguridad.
	 * @throws Exception
	 */
	public ClientHandler() {
		try {
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

			keystoreLocation = gestorPropiedades.valorPropertie("security.keystore.location");
			keystoreType = gestorPropiedades.valorPropertie("security.keystore.type");
			keystorePassword = gestorPropiedades.valorPropertie("security.keystore.password");
			keystoreCertAlias = gestorPropiedades.valorPropertie("security.keystore.cert.alias");
			keystoreCertPassword = gestorPropiedades.valorPropertie("security.keystore.cert.password");

		} catch (Exception e) {
			System.err.println("Error leyendo el fichero de configuraci�n de securizaci�n");
		}
	}

	public void invoke(MessageContext msgContext) throws AxisFault {
		try {

			((SOAPPart) msgContext.getRequestMessage().getSOAPPart()).setCurrentMessage(this.createBinarySecurityToken(msgContext),
					SOAPPart.FORM_SOAPENVELOPE);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Securiza, mediante el tag BinarySecurityToken y firma una petici�n SOAP
	 * no securizada.
	 *
	 * @param soapEnvelopeRequest
	 *            Documento xml que representa la petici�n SOAP sin securizar.
	 * @return Un mensaje SOAP que contiene la petici�n SOAP de entrada
	 *         securizada mediante el tag BinarySecurityToken.
	 */
	public SOAPEnvelope createBinarySecurityToken(MessageContext msgContext) {

		try {
			GeneraCertificado();

			return createBinarySecurityTokenFirma(msgContext);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return null;

		}
	}
	
	protected static String convertToPem(X509Certificate cert) throws CertificateEncodingException {
		   String pemCertPre = DatatypeConverter.printBase64Binary(cert.getEncoded());
		   return pemCertPre;
	   }
	
	public void GeneraCertificado() throws Exception {
		
		// Extrae la clave privada y el certificado del almac�n de claves
		try {
			almacen = KeyStore.getInstance((String) keystoreType);
			FileInputStream fis = new FileInputStream((String) keystoreLocation);
			almacen.load(fis, ((String) keystorePassword).toCharArray());
			clavePrivada = (PrivateKey) almacen.getKey((String) keystoreCertAlias,
					((String) keystoreCertPassword).toCharArray());
			certificado = (java.security.cert.X509Certificate)almacen.getCertificate((String) keystoreCertAlias);
			clavePublica = certificado.getPublicKey();
		} catch (Exception e) {
			throw e;
		}
	}
	
	private SOAPEnvelope createBinarySecurityTokenFirma(MessageContext msgContext) {
		
		try {
			GeneraCertificado();
			
			// Obtenci�n del documento XML que representa la petici�n SOAP
			Message msg = msgContext.getCurrentMessage();
			SOAPEnvelope env = (SOAPEnvelope) msg.getSOAPEnvelope();
			env.addMapping(new Mapping("http://www.w3.org/2000/09/xmldsig#","ds"));
			SOAPHeaderElement cabecera = new SOAPHeaderElement(XMLUtils
					.StringToElement("http://www.w3.org/2000/09/xmldsig#","Signature", ""));
			env.addHeader(cabecera);

			Document doc = env.getAsDocument();

			XMLSignature firma = new XMLSignature(doc, "http://xml-security",
					XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1,
					Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

			Element elementoFirma = (Element) ((Element) doc.getDocumentElement().getFirstChild()).getElementsByTagNameNS("*", "Signature").item(0);//(Element) ((Element) doc.getFirstChild()).getElementsByTagNameNS("*", "Signature").item(0);
			Element sobre = (Element) elementoFirma.getParentNode();
			sobre.replaceChild(firma.getElement(), elementoFirma);
			

			Element cuerpo = (Element) ((Element) doc.getFirstChild()).getElementsByTagNameNS("*", "Body").item(0);
			String identifica = cuerpo.getAttribute("Id");
			if (identifica == null || identifica.equals("")) {
				identifica = new String("MsgBody");
				cuerpo.setAttribute("Id", identifica);
			}

			Transforms transforms = new Transforms(doc);
			transforms.addTransform("http://www.w3.org/2001/10/xml-exc-c14n#");
						firma.addDocument("#" + identifica,	transforms,
							org.apache.xml.security.utils.Constants.ALGO_ID_DIGEST_SHA1);			
			// Obtiene el certificado y la clave privada y firma
			/*
			 * BURKE 08/07/2005
			 * Mod 0005 - Extraemos el certificado correspondiente a traves de Locator.
			 */			
			//Locator loc = Locator.getInstance();
			//CertificadoDigital cert = loc.getCertificado("FNMT");
			firma.addKeyInfo(certificado);
			firma.addKeyInfo(clavePublica);
			firma.sign(clavePrivada);
			
			// Crea el deserializador para la firma del documento XML
			Canonicalizer c14n = Canonicalizer.getInstance(new String(
			        Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS));
			byte[] mensajeCanonico = c14n.canonicalizeSubtree(doc);
			InputSource is = new InputSource(new java.io.ByteArrayInputStream(
					mensajeCanonico));
			String tipo = (msgContext.getPastPivot()?Message.RESPONSE:Message.REQUEST);			
			DeserializationContext dser = new DeserializationContext(
					is, msgContext, tipo, env);
			
			dser.parse();
			
			msgContext.getCurrentMessage().setMessageType(tipo);

			// Creaci�n de un nuevo mensaje SOAP a partir del mensaje SOAP
			// securizado formado
	        return env;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;

		}
	}

	/*
	 * Establece el conjunto de propiedades con el que ser� inicializado el
	 * gestor criptogr�fico de WSS4J.
	 *
	 * @return Devuelve el conjunto de propiedades con el que ser� inicializado
	 *         el gestor criptogr�fico de WSS4J.
	 *
	private Properties initializateCryptoProperties() {
		Properties res = new Properties();
		res.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
		res.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", this.keystoreType);
		res.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", this.keystorePassword);
		res.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias", this.keystoreCertAlias);
		res.setProperty("org.apache.ws.security.crypto.merlin.alias.password", this.keystoreCertPassword);
		res.setProperty("org.apache.ws.security.crypto.merlin.file", this.keystoreLocation);
		return res;
	}*/

	/*
	 * Creates and returns an Axis message from a SOAP envelope string.
	 *
	 * @param unsignedEnvelope
	 *            a string containing a SOAP envelope
	 * @return <code>Message</code> the Axis message
	 *
	private Message getAxisMessage(String unsignedEnvelope, MessageContext msgContext) {
		InputStream inStream = new ByteArrayInputStream(unsignedEnvelope.getBytes());
		Message axisMessage = new Message(inStream);
		axisMessage.setMessageContext(msgContext);
		return axisMessage;
	}*/
	
	public static String doBase64Encode(byte[]array){
		return new String(Base64.encodeBase64(array));
	}
	/*
	public static String tofirma(String aFirmar) {
		String firmaFinal = null;
		try {

			String encodedAFirmar = aFirmar.substring(aFirmar.indexOf("<consultaMatriculas"), aFirmar.indexOf("</soapenv:Body>"));
			encodedAFirmar = encodedAFirmar.replaceAll("\n", "");
			encodedAFirmar = encodedAFirmar.replaceAll("\t", "");
			encodedAFirmar = doBase64Encode(encodedAFirmar.getBytes());
			

			String xmlAFirmar = "<AFIRMA><CONTENT Id=\""
					+ MATEGE_NODO_FIRMAR_ID + "\">" + encodedAFirmar
					+ "</CONTENT></AFIRMA>";

			byte[] datosAfirmar = xmlAFirmar.getBytes();

			ViafirmaClientFactory.init("http://192.168.50.27/viafirma",
					"http://192.168.50.27/viafirma/rest");

			ViafirmaClient viafirmaClient = ViafirmaClientFactory.getInstance();

			String idFirma = viafirmaClient
					.signByServerWithTypeFileAndFormatSign(
							"firma_peticion.xml", datosAfirmar, "colegio",
							"1111", TypeFile.XML,
							TypeFormatSign.XADES_EPES_ENVELOPED, NODEID);

			// Con el metodo getBytes que tiene la clase String te devuelve el
			// un arreglo de //bytes
			String firma = new String(
					viafirmaClient.getDocumentoCustodiado(idFirma));
			// Una vez obtenida la firma la tratamos para obtener la petici�n
			// SOAP, que luego mandaremos.
//			firmaFinal = firma.substring(firma.indexOf("<ds:Signature"), firma.indexOf("</AFIRMA>"));
			firmaFinal = aFirmar.substring(0, aFirmar.indexOf("<soapenv:Body"))
					+ "<soapenv:Header>"
					+ firma.substring(firma.indexOf("<ds:Signature"),
							firma.indexOf("</ds:Signature>"))
					+ "</ds:Signature></soapenv:Header>"
					+ aFirmar.substring(aFirmar.indexOf("<soapenv:Body"),
							aFirmar.indexOf("</soapenv:Envelope>")) + "</soapenv:Envelope>";
			firmaFinal = firmaFinal.replaceAll("\n", "");
			firmaFinal = firmaFinal.replaceAll("\t", "");

		} catch (Exception e) {
			System.out.print(e);
		}
		return firmaFinal;

	}*/
}

