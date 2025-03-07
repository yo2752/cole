package com.matriculaWS.signature;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.utils.Mapping;
import org.apache.axis.utils.XMLUtils;
import org.apache.log4j.Logger;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.web.OegamExcepcion;

public class ServerResponseSigningHandler extends BasicHandler {

	private static final long serialVersionUID = -217318094472286401L;

static {
      org.apache.xml.security.Init.init();
   }
   
	@Autowired
	protected GestorDocumentos gestorDocumentos;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

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
	
	private static final Logger log  = Logger.getLogger(ServerResponseSigningHandler.class);
	
	public ServerResponseSigningHandler() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		log.info("Inicializando el constructor de firma de respuesta");
		log.info("Recuperando el fichero de propiedades con la configuracion de seguridad");
		
		log.info("inicializando valores de seguridad");
		keystoreLocation = gestorPropiedades.valorPropertie("security.keystore.location");
		keystoreType = gestorPropiedades.valorPropertie("security.keystore.type");
		keystorePassword = gestorPropiedades.valorPropertie("security.keystore.password");
		keystoreCertAlias = gestorPropiedades.valorPropertie("security.keystore.cert.alias");
		keystoreCertPassword = gestorPropiedades.valorPropertie("security.keystore.cert.password");
		log.info("Valores inicializados");
	}
   
   public void GeneraCertificado() throws Exception {
		
		// Extrae la clave privada y el certificado del almacén de claves
		try {
			log.info("Recuperando valores del certificado para la firma");
			almacen = KeyStore.getInstance((String) keystoreType);
			FileInputStream fis = new FileInputStream((String) keystoreLocation);
			almacen.load(fis, ((String) keystorePassword).toCharArray());
			clavePrivada = (PrivateKey) almacen.getKey((String) keystoreCertAlias,
					((String) keystoreCertPassword).toCharArray());
			certificado = (java.security.cert.X509Certificate)almacen.getCertificate((String) keystoreCertAlias);
			clavePublica = certificado.getPublicKey();
			log.info("Valores del certificado recuperados");
		} catch (Exception e) {
			log.error(e);
			throw AxisFault.makeFault(e);
		}
	}
   
   public void invoke(MessageContext msgContext) throws AxisFault {
	   log.info("Firmando respuesta: ");
      try {
    	  	GeneraCertificado();
			
			// Obtención del documento XML que representa la petición SOAP
			Message msg = msgContext.getCurrentMessage();

			SOAPEnvelope env = (SOAPEnvelope) msg.getSOAPEnvelope();
			log.info("Añadiendo ds:Signature en la cabecera");
			env.addMapping(new Mapping("http://www.w3.org/2000/09/xmldsig#","ds"));
			SOAPHeaderElement cabecera = new SOAPHeaderElement(XMLUtils
					.StringToElement("http://www.w3.org/2000/09/xmldsig#","Signature", ""));
			env.addHeader(cabecera);

			Document doc = env.getAsDocument();
			log.info("Generando la firma");
			XMLSignature firma = new XMLSignature(doc, "http://xml-security",
					XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1,
					Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

			Element elementoFirma = (Element) ((Element) doc.getDocumentElement().getFirstChild()).getElementsByTagNameNS("*", "Signature").item(0);//(Element) ((Element) doc.getFirstChild()).getElementsByTagNameNS("*", "Signature").item(0);
			Element sobre = (Element) elementoFirma.getParentNode();
			sobre.replaceChild(firma.getElement(), elementoFirma);
			
			log.info("Identificando Soap:Body para realizar la firma");
			Element cuerpo = (Element) ((Element) doc.getFirstChild()).getElementsByTagNameNS("*", "Body").item(0);
			String identifica = cuerpo.getAttribute("Id");
			if (identifica == null || identifica.equals("")) {
				identifica = new String("MsgBody");
				cuerpo.setAttribute("Id", identifica);
			}
			log.info("Generando el Transformer");
			Transforms transforms = new Transforms(doc);
			transforms.addTransform("http://www.w3.org/2001/10/xml-exc-c14n#");
						firma.addDocument("#" + identifica,	transforms,
							org.apache.xml.security.utils.Constants.ALGO_ID_DIGEST_SHA1);			
			// Obtiene el certificado y la clave privada y firma
			log.info("Firmando con certificado");
			firma.addKeyInfo(certificado);
			firma.addKeyInfo(clavePublica);
			firma.sign(clavePrivada);
			
			log.info("Deserializando");
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
			
			log.info("Respuesta firmada correctamente.");
			log.info("Respuesta:\n"+msg.getSOAPPartAsString());
			String numeroNombreFichero  = "9999" + utilesFecha.formatoFecha("ddMMyyHHmmssSSS",new Date());
			try {
				gestorDocumentos.guardarFichero(ProcesosEnum.IVTM.toString(), "MATRICULASWS",Utilidades.transformExpedienteFecha(new BigDecimal(numeroNombreFichero)), "IVTMMATRICULASWS_REQ_"+numeroNombreFichero, ".soap", msg.getSOAPPartAsBytes());
			} catch (OegamExcepcion e) {
				log.error("No se ha podido guardar el fichero con la petición");
			}
      } catch (Exception e) {
    	 e.printStackTrace();
         log.error(e);
         throw AxisFault.makeFault(e);
      }
   }
}