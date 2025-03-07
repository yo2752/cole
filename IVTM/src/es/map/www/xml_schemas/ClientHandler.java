package es.map.www.xml_schemas;


import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

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
 * Clase encargada de securizar los mensajes SOAP de petición realizados desde
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

	// Localización del keystore con certificado y clave privada de usuario
	private String keystoreLocation = null;

	// Tipo de keystore
	private String keystoreType = null;

	// Clave del keystore
	private String keystorePassword = null;

	// Alias del certificado usado para firmar el tag soapBody de la petición y
	// que será alojado en el token BinarySecurityToken
	private String keystoreCertAlias = null;

	// Password del certificado usado para firmar el tag soapBody de la petición
	// y que será alojado en el token BinarySecurityToken
	private String keystoreCertPassword = null;

	private KeyStore almacen;
	private PrivateKey clavePrivada;
	private PublicKey clavePublica;
	private java.security.cert.X509Certificate certificado;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	/**
	 * Constructor que inicializa el atributo securityOption
	 *
	 * @param securityOption
	 *            opción de seguridad.
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
			System.err.println("Error leyendo el fichero de configuración de securización");
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
	 * Securiza, mediante el tag BinarySecurityToken y firma una petición SOAP
	 * no securizada.
	 *
	 * @param soapEnvelopeRequest
	 *            Documento xml que representa la petición SOAP sin securizar.
	 * @return Un mensaje SOAP que contiene la petición SOAP de entrada
	 *         securizada mediante el tag BinarySecurityToken.
	 */
	public SOAPEnvelope createBinarySecurityToken(MessageContext msgContext) {
		try {
			return createFirma(msgContext);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return null;
		}
	}

//	protected static String convertToPem(X509Certificate cert) throws CertificateEncodingException {
//		String pemCertPre = DatatypeConverter.printBase64Binary(cert.getEncoded());
//		return pemCertPre;
//	}

	public void GeneraCertificado() throws Exception {
		// Extrae la clave privada y el certificado del almacén de claves
		try {
			almacen = KeyStore.getInstance((String) keystoreType);
			FileInputStream fis = new FileInputStream(keystoreLocation);
			almacen.load(fis, keystorePassword.toCharArray());
			clavePrivada = (PrivateKey) almacen.getKey(keystoreCertAlias,
					keystoreCertPassword.toCharArray());
			certificado = (java.security.cert.X509Certificate)almacen.getCertificate(keystoreCertAlias);
			clavePublica = certificado.getPublicKey();
		} catch (Exception e) {
			throw e;
		}
	}

	private SOAPEnvelope createFirma(MessageContext msgContext) {
		try {
			GeneraCertificado();

			// Obtención del documento XML que representa la petición SOAP
			Message msg = msgContext.getCurrentMessage();
			SOAPEnvelope env = (SOAPEnvelope) msg.getSOAPEnvelope();
			env.addMapping(new Mapping("http://www.w3.org/2000/09/xmldsig#","ds"));
			SOAPHeaderElement cabecera = new SOAPHeaderElement(XMLUtils.StringToElement("http://www.w3.org/2000/09/xmldsig#","Signature", ""));
			env.addHeader(cabecera);

			Document doc = env.getAsDocument();

			XMLSignature firma = new XMLSignature(doc, "http://xml-security",
					XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1,
					Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

			Element elementoFirma = (Element) ((Element) doc.getDocumentElement().getFirstChild()).getElementsByTagNameNS("*", "Signature").item(0);
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

//			Transformer transformerSoap = TransformerFactory.newInstance().newTransformer();
//			transformerSoap.setOutputProperty(OutputKeys.INDENT, "YES");
//			transformerSoap.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
//			StreamResult resultSoap = new StreamResult(new FileOutputStream("C:\\temp\\peticionesIVTM\\signedSoap"+System.currentTimeMillis()+".xml"));  
//			DOMSource sourceSoap = new DOMSource(env.getAsDocument());
//			transformerSoap.transform(sourceSoap, resultSoap);
//			System.out.println("Soap Firmado saved!");

			msgContext.getCurrentMessage().setMessageType(tipo);

			/** Imprimimos la petición firmada */
//			FileWriter fw = new FileWriter("C:\\"+peticion++);
//			fw.write(env.getAsString());
//			fw.flush();
//			fw.close();
			/* Fin de la impresion */
			return env;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}