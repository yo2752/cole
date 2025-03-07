package viafirma.utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.io.IOUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.x509.NoSuchStoreException;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.viafirma.cliente.ViafirmaClient;
import org.viafirma.cliente.ViafirmaClientFactory;
import org.viafirma.cliente.exception.InternalException;
import org.viafirma.cliente.firma.Policy;
import org.viafirma.cliente.firma.TypeFile ;
import org.viafirma.cliente.firma.TypeFormatSign;
import org.viafirma.cliente.firma.TypeSign;
import org.viafirma.cliente.util.Constantes;
import org.viafirma.cliente.util.OptionalRequest;
import org.viafirma.cliente.util.PolicyParams;
import org.viafirma.cliente.vo.Documento;
import org.viafirma.cliente.vo.FirmaInfoViafirma;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

/*
 * NOTA: Respecto a la modificación para que el dpr se firme:
 * En servidor: método firmarDocumentoDPR 
 * El usuario a través del applet: método firmarDocumentoDPRusuario
 */
public class UtilesViafirma {

	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesViafirma.class);
	private static final ILoggerOegam logReg = LoggerOegam.getLogger("Registradores");

	private static final String FIRMAR = "firmar";
	private static final String DOCUMENTO_FIRMADO_CON_EL_ALIAS = "documento firmado con el alias:";
	private static final String CADENA_XML = "CADENA XML = ";
	private static final String FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR = "firmarDocumentoMatriculacionServidor";
	private static final String URL_VIAFIRMA_REST = "url_viafirmaRest";
	private static final String URL_VIAFIRMA = "url_viafirma";
	private static final String URL_VIAFIRMA_WS = "url_viafirmaWS";
	private static final String DATOS_A_FIRMAR = "DATOS A FIRMAR";
	private static final String ERROR = "ERROR";
	private static final String TRAFICO_CLAVES_PASSWORD_COLEGIO = "trafico.claves.colegio.password";
	private static final String TRAFICO_CLAVES_ALIAS_COLEGIO = "trafico.claves.colegio.alias";
	private static final String NODEID = "D0";

	public final String UTF_8 = "UTF-8";

	ViafirmaClient viafirmaOegam;
	ViafirmaClient viafirmaRegistro;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	public UtilesViafirma() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @param str576
	 * @param alias
	 * @return la firma separada del documento original
	 * @throws NoSuchStoreException
	 * @throws CMSException
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws CertificateException
	 */
	public String firmar576(String str576, String alias) throws Exception {
		String idFirmaDetached;
		byte[] datosFirmados = null;
		byte[] datosAfirmar = str576.getBytes();
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		try {
			try {
				idFirmaDetached = viafirmaClient.signByServerWithTypeFileAndFormatSign("576", datosAfirmar, alias, null, TypeFile.txt, TypeFormatSign.CMS_DETACHED);
			} catch (Throwable e) {
				log.error("error firmando ", e);
				throw e;
			}
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			datosFirmados = viafirmaClient.getDocumentoCustodiado(idFirmaDetached);
			log.info(datosFirmados);
		} catch (InternalException e) {
			log.error(e);
			return ERROR;
		}catch (Throwable e) {
			log.error(e);
			return ERROR;
		}
		return Base64.encode(datosFirmados);

	}

	public String firmarMensajeMatriculacionServidor(String cadenaXML, String alias) {

		log.info(Claves.CLAVE_LOG_ENVIO_MATRICULACION_INICIO + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR);
		log.info(CADENA_XML + cadenaXML);
		String idFirma;
		String xmlFirmado;
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		try {
			byte[] datosAfirmar = cadenaXML.getBytes();

			log.info(DATOS_A_FIRMAR + datosAfirmar);

			String password = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");

			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", datosAfirmar, alias, password, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED, NODEID);

			log.info(DOCUMENTO_FIRMADO_CON_EL_ALIAS + alias);
			log.info(Claves.CLAVE_LOG_ENVIO_MATRICULACION_FIN + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR);
		} catch (InternalException e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR + e.getMessage());
			idFirma = ERROR;
		}catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR + e.getMessage());
			idFirma = ERROR;
		}

		if (!idFirma.equals(ERROR)) {
			xmlFirmado = getDocumentoFirmado(idFirma);
			log.info("xmlFirmado=" + xmlFirmado);
		} else {
			xmlFirmado = ERROR;
		}

		return xmlFirmado;
	}

	public byte[] firmarJustificanteProfesional(byte[] datosAfirmar, String alias) {

		log.info("PAGO_TASAS: Inicio-- firmar Pago Tasasr");
		String idFirma = null;

		if (!ViafirmaClientFactory.isInit()) {
			ViafirmaClientFactory.init(gestorPropiedades.valorPropertie(URL_VIAFIRMA), gestorPropiedades.valorPropertie(URL_VIAFIRMA_WS));
		}

		ViafirmaClient viafirmaClient = ViafirmaClientFactory.getInstance();
		try {

			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmado.xml", datosAfirmar, alias, null, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED, NODEID);

			log.info(DOCUMENTO_FIRMADO_CON_EL_ALIAS + alias);
		} catch (InternalException e) {
			log.error("Error al firmar datos de justificante profesional", e);
		}catch (Throwable e) {
			log.error("Error al firmar datos de justificante profesional", e);
		}
		if (idFirma != null) {
			try {
				if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
					Thread.sleep(1500);
				}
				return viafirmaClient.getDocumentoCustodiado(idFirma);
			} catch (Exception e) {
				log.error("Error recuperando el documento firmado", e);
			}
		}
		return null;
	}

	public String firmarPruebaCertificadoCaducidad(String cadenaXML, String alias) {
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		String idFirma = null;
		try {
			byte[] datosAfirmar = new String(cadenaXML.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");
			String password = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");
			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", datosAfirmar, alias, password, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED);

		} catch (UnsupportedEncodingException e) {
			log.error("Error a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		} catch (InternalException e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		} catch (SOAPFaultException e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		} catch (Exception e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		}catch (Throwable e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		}

		return idFirma;
	}
	
	public String firmarPruebaCertificadoCaducidadSS(String cadenaXML, String alias) {
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		String idFirma = null;
		try {
			byte[] datosAfirmar = new String(cadenaXML.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");

			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", datosAfirmar, alias, null, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED);

		} catch (UnsupportedEncodingException e) {
			log.error("Error a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		} catch (InternalException e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		} catch (SOAPFaultException e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		} catch (Exception e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		}catch (Throwable e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		}

		return idFirma;
	}

	// Firma el tramite de matriculacion en servidor. Debe devolver el identificador de viafirma.
	public String firmarMensajeMatriculacionServidor_MATW(String cadenaXML, String alias) {

		log.info(Claves.CLAVE_LOG_ENVIO_MATRICULACION_INICIO + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR);
		log.info(CADENA_XML + cadenaXML);
		String idFirma;
		String xmlFirmado;
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		try {
			byte[] datosAfirmar = new String(cadenaXML.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");

			log.info(DATOS_A_FIRMAR + datosAfirmar);

			String password = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");

			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", datosAfirmar, alias, password, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED);

			// pasamos null para HSM, o el mismo que aliasColegio
			log.info(DOCUMENTO_FIRMADO_CON_EL_ALIAS + alias);
			log.info(Claves.CLAVE_LOG_ENVIO_MATRICULACION_FIN + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR);
		} catch (InternalException e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR + e.getMessage());
			idFirma = ERROR;
		} catch (UnsupportedEncodingException e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR + e.getMessage());
			idFirma = ERROR;
		}catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR + e.getMessage());
			idFirma = ERROR;
		}

		if (!idFirma.equals(ERROR)) {
			xmlFirmado = getDocumentoFirmadoMatw(idFirma);
			log.info("xmlFirmado=" + xmlFirmado);
		} else {
			xmlFirmado = ERROR;
		}

		return xmlFirmado;
	}
	
	public byte[] firmarNotificacionesTransporte(byte[] ficheroPDF, String alias) {
		byte[] firma = null;
		try {
			Documento documento = new Documento("doc.pdf", ficheroPDF, TypeFile.PDF, TypeFormatSign.PAdES_BASIC);
			Policy policy = new Policy();
		    policy.setTypeSign(TypeSign.ATTACHED);
		    policy.setTypeFormatSign(TypeFormatSign.PAdES_BASIC);
		    policy.addParameter(PolicyParams.DIGITAL_SIGN_PAGE.getKey(),"0");
		    policy.addParameter(PolicyParams.DIGITAL_SIGN_RECTANGLE.getKey(), new
		    		org.viafirma.cliente.vo.Rectangle(40,10,550,75));
		    policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_HIDE_STATUS.getKey(),"true");
		    policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_TYPE.getKey(),"QR-BAR-H");
		    policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_TEXT.getKey(), "Firmado por [CN] con DNI [SERIALNUMBER]");
		    ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			String idFirma = viafirmaClient.signByServerWithPolicy(policy, documento, alias, null);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirma);
		} catch(Exception e){
			log.error("Error en la firma de atex5", e);
		}catch (Throwable e) {
			log.error("Error en la firma de atex5", e);
		}
		return firma;
	}
	
	public byte[] firmarPdfImagen(byte[] ficheroPDF, String alias, String imagen){
		log.info("Inicio firmarPdfImagen");
		String idFirma;
		byte[] docFirmado = null;
		try {
			ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			String password = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");
			java.awt.Rectangle position = new java.awt.Rectangle(500, 125, 75, 75);
			byte[] imageStamp = IOUtils.toByteArray(new FileInputStream(imagen));
			idFirma = viafirmaClient. signByServerPDFWithImageStamp("prueba.pdf", ficheroPDF, alias, password, position, imageStamp);
			if (!idFirma.equals(ERROR)) {
				if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
					Thread.sleep(1500);
				}
				docFirmado = viafirmaClient.getDocumentoCustodiado(idFirma);
			}
		}catch(Exception e){
			log.error("Ha sucedido un error a la hora de firmar el pdf, error:",e);
		}catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de firmar el pdf, error:", e);
		}
		return docFirmado;
	}

	public byte[] firmarPDFServidorImagen(byte[] ficheroPDF, String alias, String password, String imagen) {

		log.info(Claves.CLAVE_LOG_ENVIO_MATRICULACION_INICIO + "firmarPDFServidor");
		log.info("ficheroPDF" + ficheroPDF);
		String idFirma;
		byte[] docFirmado = null;
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		try {
			byte[] datosAfirmar = ficheroPDF;

			log.info(DATOS_A_FIRMAR + datosAfirmar);
			log.info(DOCUMENTO_FIRMADO_CON_EL_ALIAS + alias);

			java.awt.Rectangle position = new java.awt.Rectangle(500, 125, 75, 75);

			// Datos imagen de firma
			byte[] imageStamp = null;
			try {
				imageStamp = IOUtils.toByteArray(new FileInputStream(imagen));
			} catch (FileNotFoundException e) {
				log.error("No se encuentra la imagen de firmado", e);
			} catch (IOException e) {
				log.error("Error accediendo a la imagen de firmado", e);
			}

			// Datos a firmar
			// Iniciamos la firma en el servidor xnoccio
			idFirma = viafirmaClient.signByServerPDFWithImageStamp("prueba.pdf", datosAfirmar, alias, password, position, imageStamp);

			log.info("Firmar PDF en Servidor. idFirma " + idFirma);
		} catch (InternalException e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR, e);
			idFirma = ERROR;
		}catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + FIRMAR_DOCUMENTO_MATRICULACION_SERVIDOR, e);
			idFirma = ERROR;
		}

		if (!idFirma.equals(ERROR)) {
			try {
				if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
					Thread.sleep(1500);
				}
				docFirmado = viafirmaClient.getDocumentoCustodiado(idFirma);
				log.info("docFirmado=" + docFirmado);
			} catch (Exception e) {
				log.error("Error recuperando documento custodiado con idFirma " + idFirma, e);
			}

		} else {
			docFirmado = null;
		}

		return docFirmado;
	}

	// Firma el tramite de transmision en servidor. Debe devolver el identificador de viafirma.
	public String firmarMensajeTransmisionServidorColegio(String cadenaXML) throws OegamExcepcion {

		log.info("ENVIO_TRANSMISION: Inicio-- firmar documento transmisión servidor");
		log.info(CADENA_XML + cadenaXML);
		String idFirma;

		if (!ViafirmaClientFactory.isInit()) {
			ViafirmaClientFactory.init(gestorPropiedades.valorPropertie(URL_VIAFIRMA), gestorPropiedades.valorPropertie(URL_VIAFIRMA_REST));
		}

		ViafirmaClient viafirmaClient = ViafirmaClientFactory.getInstance();
		try {

			byte[] datosAfirmar = cadenaXML.getBytes();

			log.info(DATOS_A_FIRMAR + datosAfirmar);

			String aliasColegio = gestorPropiedades.valorPropertie(TRAFICO_CLAVES_ALIAS_COLEGIO);
			String passColegio = gestorPropiedades.valorPropertie(TRAFICO_CLAVES_PASSWORD_COLEGIO);

			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmado", datosAfirmar, aliasColegio, passColegio, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED, NODEID);

			log.info(DOCUMENTO_FIRMADO_CON_EL_ALIAS + aliasColegio);
			log.info("ENVIO_TRANSMISION: Inicio-- firmar documento transmisión servidor");
		} catch (InternalException e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmar documento transmisión servidor", e);
			idFirma = ERROR;
		}catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmar documento transmisión servidor", e);
			idFirma = ERROR;
		}

		return idFirma;
	}

	// Firma el tramite de transmisión en servidor. Debe devolver el identificador de viafirma.
	public String firmarMensajeTransmisionServidor(String cadenaXML, String alias) {

		log.info("Inicio--firmarMensajeTransmisionServidor");
		log.info("CadenaXML: " + cadenaXML);
		String idFirma;
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		try {
			byte[] datosAfirmar = cadenaXML.getBytes();

			log.info("DATOS A FIRMAR: " + datosAfirmar);
			
			
	
				String password = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");
	
				idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmadoColegiado", datosAfirmar, alias, password, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED, NODEID);
	
				log.info("documento firmado con el alias " + alias);

			
		} catch (InternalException e) {
			log.error("ERROR--firmarMensajeTransmisionServidor: " + e.getMessage());
			idFirma = ERROR;
		}

		catch (javax.xml.ws.soap.SOAPFaultException e) {
			log.error("ERROR--firmarMensajeTransmisionServidor: " + e.getMessage());
			idFirma = ERROR;
		}

		catch (Exception e) {
			log.error("ERROR--firmarMensajeTransmisionServidor: " + e.getMessage());
			idFirma = ERROR;
		}
		catch (Throwable e) {
			log.error("ERROR--firmarMensajeTransmisionServidor: " + e.getMessage());
			idFirma = ERROR;
		}

		return idFirma;
	}

	/**
	 * @param idFirma
	 * @return el String del documento firmado.
	 * @throws OegamExcepcion
	 */
	public String getDocumentoFirmado(String idFirma) {

		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		byte[] documentoCustodiado;
		try {
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			documentoCustodiado = viafirmaClient.getDocumentoCustodiado(idFirma);
			log.info("documento= " + documentoCustodiado);
		} catch (InternalException e) {
			documentoCustodiado = null;
			log.error(e);
		}catch (Throwable e) {
			documentoCustodiado = null;
			log.error(e);
		}
		return new String(documentoCustodiado);
	}

	/**
	 * @param idFirma
	 * @return el String del documento firmado.
	 * @throws OegamExcepcion
	 */
	public byte[] getBytesDocumentoFirmado(String idFirma) {

		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		byte[] documentoCustodiado;
		try {
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			documentoCustodiado = viafirmaClient.getDocumentoCustodiado(idFirma);
			log.info("documento= " + documentoCustodiado);
		} catch (InternalException e) {
			documentoCustodiado = null;
			log.error(e);
		}catch (Throwable e) {
			documentoCustodiado = null;
			log.error(e);
		}
		return documentoCustodiado;
	}

	public String getDocumentoFirmadoMatw(String idFirma) {

		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		byte[] bytesDocumentoCustodiado;
		String stringDocumentoCustodiado = null;
		try {
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			bytesDocumentoCustodiado = viafirmaClient.getDocumentoCustodiado(idFirma);
			stringDocumentoCustodiado = new String(bytesDocumentoCustodiado, "UTF-8");
		} catch (InternalException e) {
			bytesDocumentoCustodiado = null;
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			bytesDocumentoCustodiado = null;
			log.error(e);
		}catch (Throwable e) {
			bytesDocumentoCustodiado = null;
			log.error("Error a la hora de firmar datos, error: ", e);
		}
		return stringDocumentoCustodiado;
	}

	/**
	 * Extrae del xml firmado por viafirma el tag ds:Signature
	 * @param xmlFirmado devuelto por viafirma
	 * @return el tag ds:Signature en String
	 * @throws Exception si no se establece el indice de inicio o fin del elemento buscado
	 */
	public String getDsSignature(String xmlFirmadoViafirma) throws Exception {
		// Establece los índices de apertura y cierre del tag:
		String aperturaTag = "<ds:Signature";
		String cierreTag = "</ds:Signature>";
		int indiceUno = xmlFirmadoViafirma.indexOf(aperturaTag);
		int indiceDos = xmlFirmadoViafirma.indexOf(cierreTag);
		if (indiceUno != -1 && indiceDos != -1) {
			String dsSignature = xmlFirmadoViafirma.substring(indiceUno, indiceDos + cierreTag.length());
			return dsSignature;
		} else {
			throw new Exception("La cadena recibida carece del tag de apertura o cierre del elemento 'ds:Signature'");
		}
	}

	/**
	 * Verifica un XML firmado con XMLDsig
	 * @param documentData array de bytes del XML firmado
	 * @return
	 * @throws Exception
	 */
	public boolean validarFirmaXMLDSIG(byte[] documentData) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Validando firma de " + new String(documentData));
		}
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);

		FirmaInfoViafirma info = viafirmaClient.checkSignedDocumentValidityByFileType(documentData, TypeFormatSign.XMLDSIG);

		return info.isSigned() && info.isValid();
	}

	public String firmarConsultaTarjetaEitv(byte[] cadena, String alias) {
		log.info("Consulta Tarjeta Eitv: Inicio-- firmar Datos");
		String idFirma = null;

		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);

		try {
			if (log.isInfoEnabled()) {
				log.info(DATOS_A_FIRMAR + cadena);
			}
			String password = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");
			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", cadena, alias, password, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED, NODEID);

		} catch (InternalException e) {
			log.error("Error a la hora de firmar datos de la consulta de tarjeta Eitv", e);
		}catch (Throwable e) {
			log.error("Error a la hora de firmar datos de la consulta de tarjeta Eitv, error: ", e);
		}
		return idFirma;
	}

	public byte[] firmarXmlPorAlias(byte[] cadena, String alias) {
		log.info("firmarXmlPorAlias: Inicio-- firmar Datos");
		byte[] docFirmado = null;
		try {
			if (log.isInfoEnabled()) {
				log.info(DATOS_A_FIRMAR + cadena);
			}
			docFirmado = firmarXMLTrafico(cadena, alias);
		} catch (InternalException e) {
			log.error("Error a la hora de firmar datos, error: ", e);
		}catch (Throwable e) {
			log.error("Error a la hora de firmar datos, error: ", e);
		}
		return docFirmado;
	}
	
	public byte[] firmarNotificacionesSS(byte[] xml, String alias) {
		byte[] firma = null;
		try {
			Documento documento = new Documento("doc.xml", xml, TypeFile.XML, TypeFormatSign.XADES_BES);
			Policy policy = new Policy();
			policy.setTypeFormatSign(TypeFormatSign.XADES_BES);
			policy.setTypeSign(TypeSign.ENVELOPED);
			policy.addParameter(PolicyParams.NODE_ID_TO_SIGN.getKey(), "Body");
			policy.addParameter(PolicyParams.ENVELOPED_TARGET_NODE.getKey(), "Security");

			ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);

			String idFirma = viafirmaClient.signByServerWithPolicy(policy, documento, alias, null);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirma);
		} catch(Exception e){
			log.error("Error en la firma de la SS", e);
		}catch (Throwable e) {
			log.error("Error en la firma de la SS, error: ", e);
		}
		return firma;
	}
	
	public byte[] firmarXmlModeloPresentacion(byte[] cadena, String alias){
		log.info("firmarXmlModelosPresentacion: Inicio-- firmar Datos");
		byte[] docFirmado = null;
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		try {
			if (log.isInfoEnabled()) {
				log.info(DATOS_A_FIRMAR + cadena);
			}
			String password = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");
			String idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", cadena, alias, password, TypeFile.XML, TypeFormatSign.XADES_BES);
			if (idFirma != null) {
				if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
					Thread.sleep(1500);
				}
				docFirmado = viafirmaClient.getDocumentoCustodiado(idFirma);
			}
		} catch (InternalException e) {
			log.error("Error a la hora de firmar datos, error: ", e);
		} catch (Throwable e) {
			log.error("Error a la hora de firmar datos, error: ", e);
		}
		return docFirmado;
	}
	
	public byte[] firmarXMLLicenciasCam(byte[] xml, String alias) {
		byte[] firma = null;
		try {
			ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			String password = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");
			String idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", xml, alias, password, TypeFile.XML, TypeFormatSign.XADES_BES);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirma);
		} catch (Throwable e) {
			log.error("Error en la firma XML de Licencias Cam: ", e);
		}
		return firma;
	}
	
	public byte[] firmarPdfLicenciasCam(byte[] ficheroPDF, String alias) {
		byte[] firma = null;
		try {
			Documento documento = new Documento("doc.pdf", ficheroPDF, TypeFile.PDF, TypeFormatSign.PAdES_BASIC);
			Policy policy = new Policy();
		    policy.setTypeSign(TypeSign.ATTACHED);
		    policy.setTypeFormatSign(TypeFormatSign.PAdES_BASIC);
		    policy.addParameter(PolicyParams.DIGITAL_SIGN_PAGE.getKey(),"0");
		    policy.addParameter(PolicyParams.DIGITAL_SIGN_RECTANGLE.getKey(), new
		    		org.viafirma.cliente.vo.Rectangle(40,10,550,75));
		    policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_HIDE_STATUS.getKey(),"true");
		    policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_TYPE.getKey(),"QR-BAR-H");
		    policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_TEXT.getKey(), "Firmado por [CN] con DNI [SERIALNUMBER]");
		    ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			String idFirma = viafirmaClient.signByServerWithPolicy(policy, documento, alias, null);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirma);
		} catch(Exception e){
			log.error("Error en la firma PDF de Licencias CAM", e);
		}catch (Throwable e) {
			log.error("Error en la firma PDF de Licencias CAM", e);
		}
		return firma;
	}
	
	public byte[] firmarDocumentoLicenciasCam(byte[] cadena, String alias) {
		byte[] firma = null;
		try {
			ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			viafirmaClient.addOptionalRequest(OptionalRequest.PEM_X509);
			String idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmado", cadena, alias, null, null, TypeFormatSign.CAdES_BES);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirma);
		} catch (InternalException e) {
			log.error("Error en la firma Documentos de Licencias CAM", e);
		} catch (Throwable e) {
			log.error("Error en la firma Documentos de Licencias CAM", e);
		}
		return firma;
	}
	
	public String firmaSS(byte[] bytes, String string) {
		log.info("Consulta Tarjeta Eitv: Inicio-- firmar Datos");
		String idFirma = null;

		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);

		try {
			if (log.isInfoEnabled()) {
				log.info(DATOS_A_FIRMAR + bytes);
			}
			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", bytes, string, "", TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED, "Body");

		} catch (InternalException e) {
			log.error("Error a la hora de firmar datos ", e);
		}catch (Throwable e) {
			log.error("Error a la hora de firmar datos, error: ", e);
		}
		return idFirma;
	}

	public byte[] firmarAcuseSS(byte[] datos, String alias) {
		String idFirma = null;
		String nodo = "ProsaData";
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		byte[] documentoCustodiado = null;

		Documento documento = new Documento("doc.xml", datos, TypeFile.XML, TypeFormatSign.XMLDSIG);

		try {
			Policy policy = new Policy();
            policy.setTypeSign(TypeSign.ENVELOPED);
            policy.setTypeFormatSign(TypeFormatSign.XMLDSIG);
            policy.addParameter(PolicyParams.NODE_ID_TO_SIGN.getKey(), nodo);
            policy.addParameter(PolicyParams.ENVELOPED_TARGET_NODE.getKey(), "ELEMENTOAFIRMAR");
			idFirma = viafirmaClient.signByServerWithPolicy(policy, documento, alias, null);
			

			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", datos, alias, "", TypeFile.XML, TypeFormatSign.XMLDSIG, nodo);
			
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			documentoCustodiado = viafirmaClient.getDocumentoCustodiado(idFirma);
			
		} catch (InternalException e) {
			log.error("Error ", e);
		} catch (SOAPFaultException e2) {
			e2.printStackTrace();
		} catch (Throwable e) {
			log.error("Error a la hora de firmar datos, error: ", e);
		}

		return documentoCustodiado;
	}
	
	public byte[] firmarXmlEscrituras(String aFirmar, String alias) throws InternalException, Exception{
		String idFirma = "";
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		
		try {
			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", aFirmar.getBytes("ISO-8859-1"), alias, "", TypeFile.XML, TypeFormatSign.XMLDSIG);
		} catch (Exception e) {
			log.error("Error ", e);
		}catch (Throwable e) {
			log.error("Error a la hora de firmar datos, error: ", e);
		}
		
		if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
			Thread.sleep(1500);
		}
		byte[] documentoCustodiado = viafirmaClient.getDocumentoCustodiado(idFirma);
		
		return documentoCustodiado;
		
	}
	
	public String firmarPagoTasas(byte[] cadena, String alias) {

		log.info("PAGO_TASAS: Inicio-- firmar Pago Tasasr");
		String idFirma = null;

		if (!ViafirmaClientFactory.isInit()) {
			ViafirmaClientFactory.init(gestorPropiedades.valorPropertie(URL_VIAFIRMA), gestorPropiedades.valorPropertie(URL_VIAFIRMA_WS));
		}

		ViafirmaClient viafirmaClient = ViafirmaClientFactory.getInstance();
		try {
			if (log.isInfoEnabled()) {
				log.info(DATOS_A_FIRMAR + cadena);
			}

			viafirmaClient.addOptionalRequest(OptionalRequest.PEM_X509);

			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmado", cadena, alias, null, TypeFile.TXT, TypeFormatSign.CAdES_BES);

			log.info(DOCUMENTO_FIRMADO_CON_EL_ALIAS + alias);
		} catch (InternalException e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmar datos de la compra de tasas", e);
			idFirma = ERROR;
		}catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmar datos de la compra de tasas", e);
			idFirma = ERROR;
		}

		return idFirma;
	}

	/**
	 * Obtiene el certificado con el que se realizo la firma en formato PEM (si al realizar la firma se incluyo la opcion viafirmaClient.addOptionalRequest(OptionalRequest.PEM_X509);)
	 * @param codFirma
	 * @return
	 */
	public String obtieneCertificadoPEM(String codFirma) {

		if (!ViafirmaClientFactory.isInit()) {
			ViafirmaClientFactory.init(gestorPropiedades.valorPropertie(URL_VIAFIRMA), gestorPropiedades.valorPropertie(URL_VIAFIRMA_REST));
		}

		ViafirmaClient viafirmaClient = ViafirmaClientFactory.getInstance();
		String certificadoFirma = null;
		try {
			certificadoFirma = viafirmaClient.getSignInfo(codFirma).getProperties().get("pem");

		} catch (InternalException e) {

			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "obtener la clave pública del Certificado ", e);
		}catch (Throwable e) {
			log.error("ERROR", e);
			
		}

		return certificadoFirma;
	}

	public String recuperaCertificadoColegiado(String alias) {
		String idFirma = null;
		String certificado = null;

		try {
			ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);

			byte[] xml = new byte[0];
			viafirmaClient.addOptionalRequest(OptionalRequest.PEM_X509);

			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmado", xml, alias, null, TypeFile.TXT, TypeFormatSign.CAdES_BES);

			certificado = obtieneCertificadoPEM(idFirma);
		} catch (InternalException e) {
			log.error("ERROR", e);
			idFirma = ERROR;
		}catch (Throwable e) {
			log.error("ERROR", e);
			idFirma = ERROR;
		}

		return certificado;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////// REGISTRADORES //////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////////

	// Firma el dpr el usuario con el applet y su certificado
	public void firmarDocumentoDPRusuario(String mensajeDPR, String idTramite, HttpServletRequest request, HttpServletResponse response, Integer modelo) throws OegamExcepcion {

		try {
			logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "firmarDocumentoDPRusuario");
			ViafirmaClient viafirmaClient = getForzarViafirmaClient(ConstantesViafirma.REGISTRADORES);

			byte[] datosAfirmar = mensajeDPR.getBytes();


			String idTemporal = viafirmaClient.prepareFirmaWithTypeFileAndFormatSign("dpr_" + idTramite + ".xml", TypeFile.XML, TypeFormatSign.CMS_DETACHED, datosAfirmar);

			request.getSession().setAttribute("tipoMensajeFirmado", "dpr");
			if (modelo != null) {
				request.getSession().setAttribute("modeloFirmado", modelo);
			}
			logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "firmarDocumentoDPRusuario");

			viafirmaClient.solicitarFirma(idTemporal, request, response, ConstantesViafirma.URL_RESPUESTA_VIAFIRMA);
			getForzarViafirmaClient(ConstantesViafirma.OEGAM);
		} catch (Throwable e) {
			try {
				logReg.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmarDocumentoDPRusuario", e);
				request.setAttribute("mensajeError", "Ocurrió el error: " + e);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			} catch (Exception e1) {
				logReg.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmarDocumentoDPRusuario", e);
			}
		}
	}

	// Firma el rm en servidor. Debe devolver el identificador de viafirma.
	public String firmarMensajeRMenServidor(String mensajeRM) {
		logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "firmarDocumentoRMenServidor");
		try {
			ViafirmaClient viafirmaClient = getForzarViafirmaClient(ConstantesViafirma.REGISTRADORES);

			byte[] datosAfirmar = new String(mensajeRM.getBytes(UTF_8), UTF_8).getBytes(UTF_8);

			String alias = gestorPropiedades.valorPropertie("trafico.claves.colegio.alias");
			String pass = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");

			String idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmado", datosAfirmar, alias, pass, TypeFile.XML, TypeFormatSign.CMS_DETACHED);

			logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "firmarDocumentoRMenServidor");
			getForzarViafirmaClient(ConstantesViafirma.OEGAM);
			return idFirma;
		} catch (Throwable e) {
			logReg.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmarDocumentoRMenServidor", e);
		}
		return ERROR;
	}

	/*
	 * Método que prepara la firma de la documentacion aportada de una escritura mediante el applet de viafirma. Los formatos soportados en registro para dicha documentación son: PCKS7, PDF , RTF, DOC, TIF, ASC, XLS, XML,JPG, JPEG y ZIP
	 */
	public void firmarDocumentacionEscritura(byte[] bytesDocumentacion, String idTramite, String extension, HttpServletRequest request, HttpServletResponse response) throws OegamExcepcion {
		try {
			logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "firmarDocumentacionEscritura");
			ViafirmaClient viafirmaClient = getForzarViafirmaClient(ConstantesViafirma.REGISTRADORES);

			TypeFile tipoFichero = null;
			// Prepara la solicitud de firma según el tipo de fichero a firmar:
			if (extension.equalsIgnoreCase(".PKCS7")) {
				tipoFichero = TypeFile.p7s;
			} else if (extension.equalsIgnoreCase(".PDF")) {
				tipoFichero = TypeFile.pdf;
			} else if (extension.equalsIgnoreCase(".RTF")) {
				tipoFichero = TypeFile.rtf;
			} else if (extension.equalsIgnoreCase(".DOC")) {
				tipoFichero = TypeFile.doc;
			} else if (extension.equalsIgnoreCase(".TIF")) {
				tipoFichero = TypeFile.tif;
			} else if (extension.equalsIgnoreCase(".XLS")) {
				tipoFichero = TypeFile.xls;
			} else if (extension.equalsIgnoreCase(".XML")) {
				tipoFichero = TypeFile.XML;
			} else if (extension.equalsIgnoreCase(".JPG")) {
				tipoFichero = TypeFile.jpg;
			} else if (extension.equalsIgnoreCase(".JPEG")) {
				tipoFichero = TypeFile.jpeg;
			} else if (extension.equalsIgnoreCase(".ZIP")) {
				tipoFichero = TypeFile.zip;
			}

			String idTemporal = viafirmaClient.prepareFirmaWithTypeFileAndFormatSign("docEscritura_" + idTramite + extension, tipoFichero, TypeFormatSign.CMS_DETACHED, bytesDocumentacion);

			request.getSession().setAttribute("tipoMensajeFirmado", "documentacionEscritura");

			logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "firmarDocumentacionEscritura");

			viafirmaClient.solicitarFirma(idTemporal, request, response, ConstantesViafirma.URL_RESPUESTA_VIAFIRMA);

			logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "firmarDocumentacionEscritura");
			getForzarViafirmaClient(ConstantesViafirma.OEGAM);
		} catch (Throwable e) {
			try {
				logReg.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmarDocumentacionEscritura", e);
				request.setAttribute("mensajeError", "Ocurrió el error: " + e);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			} catch (Exception e1) {
				logReg.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmarDocumentacionEscritura", e);
			}
		}
	}

	/**
	 * Firma en servidor con hsm un array de bytes de la documentación de una notificacion.
	 * @param bytesAfirmar
	 * @param idTramite
	 * @param extension (del fichero)
	 * @param alias (del certificado)
	 * @return El identificador de firma devuelto por Viafirma
	 * @param request
	 * @return El identificador de firma obtenido de viafirma
	 * @throws Exception
	 */
	public String signHsmDocNotificacion(HttpServletRequest request, byte[] bytesAfirmar, String idTramite, String extension, String alias) throws Throwable {

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " signHsmDocNotificacion");

		String idFirma;

		TypeFile tipoFichero = null;
		// Prepara la solicitud de firma según el tipo de fichero a firmar:
		if (extension.equalsIgnoreCase(".PKCS7")) {
			tipoFichero = TypeFile.p7s;
		} else if (extension.equalsIgnoreCase(".PDF")) {
			tipoFichero = TypeFile.pdf;
		} else if (extension.equalsIgnoreCase(".RTF")) {
			tipoFichero = TypeFile.rtf;
		} else if (extension.equalsIgnoreCase(".DOC")) {
			tipoFichero = TypeFile.doc;
		} else if (extension.equalsIgnoreCase(".TIF")) {
			tipoFichero = TypeFile.tif;
		} else if (extension.equalsIgnoreCase(".XLS")) {
			tipoFichero = TypeFile.xls;
		} else if (extension.equalsIgnoreCase(".XML")) {
			tipoFichero = TypeFile.XML;
		} else if (extension.equalsIgnoreCase(".JPG")) {
			tipoFichero = TypeFile.jpg;
		} else if (extension.equalsIgnoreCase(".JPEG")) {
			tipoFichero = TypeFile.jpeg;
		} else if (extension.equalsIgnoreCase(".ZIP")) {
			tipoFichero = TypeFile.zip;
		}

		ViafirmaClient viafirmaClient = getForzarViafirmaClient(ConstantesViafirma.REGISTRADORES);

		idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("notificacion_" + idTramite + extension, bytesAfirmar, alias, null, tipoFichero, TypeFormatSign.CMS_DETACHED);

		// Pone en sesión los bytes de la firma de la documentación y su sello de tiempo:
		request.getSession().setAttribute("firmaResumenAcuseBytes", XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma));
		request.getSession().setAttribute("selloResumenAcuseBytes", XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma));

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + " signHsmDocNotificacion");
		getForzarViafirmaClient(ConstantesViafirma.OEGAM);
		return idFirma;

	}
	
	/**
	 * Firma en servidor con hsm un array de bytes de la documentación de una notificacion.
	 * @param bytesAfirmar
	 * @param idTramite
	 * @param extension (del fichero)
	 * @param alias (del certificado)
	 * @return El identificador de firma devuelto por Viafirma
	 * @param request
	 * @return El identificador de firma obtenido de viafirma
	 * @throws Exception
	 */
	public String signHsmDocNotificacion(byte[] bytesAfirmar, String idTramite, String extension, String alias) throws Throwable {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " signHsmDocNotificacion");
		String idFirma;
		TypeFile tipoFichero = null;
		// Prepara la solicitud de firma según el tipo de fichero a firmar:
		if (extension.equalsIgnoreCase(".PKCS7")) {
			tipoFichero = TypeFile.p7s;
		} else if (extension.equalsIgnoreCase(".PDF")) {
			tipoFichero = TypeFile.pdf;
		} else if (extension.equalsIgnoreCase(".RTF")) {
			tipoFichero = TypeFile.rtf;
		} else if (extension.equalsIgnoreCase(".DOC")) {
			tipoFichero = TypeFile.doc;
		} else if (extension.equalsIgnoreCase(".TIF")) {
			tipoFichero = TypeFile.tif;
		} else if (extension.equalsIgnoreCase(".XLS")) {
			tipoFichero = TypeFile.xls;
		} else if (extension.equalsIgnoreCase(".XML")) {
			tipoFichero = TypeFile.XML;
		} else if (extension.equalsIgnoreCase(".JPG")) {
			tipoFichero = TypeFile.jpg;
		} else if (extension.equalsIgnoreCase(".JPEG")) {
			tipoFichero = TypeFile.jpeg;
		} else if (extension.equalsIgnoreCase(".ZIP")) {
			tipoFichero = TypeFile.zip;
		}
		ViafirmaClient viafirmaClient = getForzarViafirmaClient(ConstantesViafirma.REGISTRADORES);
		idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("notificacion_" + idTramite + extension, bytesAfirmar, alias, null, tipoFichero, TypeFormatSign.CMS_DETACHED);
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + " signHsmDocNotificacion");
		getForzarViafirmaClient(ConstantesViafirma.OEGAM);
		return idFirma;
	}

	/**
	 * Firma en servidor con hsm un array de bytes de la documentación de una escritura.
	 * @param bytesAfirmar
	 * @param idTramite
	 * @param extension (del fichero)
	 * @param alias (del certificado)
	 * @return El identificador de firma devuelto por Viafirma
	 * @param request
	 * @return El identificador de firma obtenido de viafirma
	 * @throws Exception
	 */
	public String signHsmDocEscritura(HttpServletRequest request, byte[] bytesAfirmar, String idTramite, String extension, String alias) throws Throwable {

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " signHsmDocEscritura");

		String idFirma;

		TypeFile tipoFichero = null;
		// Prepara la solicitud de firma según el tipo de fichero a firmar:
		if (extension.equalsIgnoreCase(".PKCS7")) {
			tipoFichero = TypeFile.p7s;
		} else if (extension.equalsIgnoreCase(".PDF")) {
			tipoFichero = TypeFile.pdf;
		} else if (extension.equalsIgnoreCase(".RTF")) {
			tipoFichero = TypeFile.rtf;
		} else if (extension.equalsIgnoreCase(".DOC")) {
			tipoFichero = TypeFile.doc;
		} else if (extension.equalsIgnoreCase(".TIF")) {
			tipoFichero = TypeFile.tif;
		} else if (extension.equalsIgnoreCase(".XLS")) {
			tipoFichero = TypeFile.xls;
		} else if (extension.equalsIgnoreCase(".XML")) {
			tipoFichero = TypeFile.XML;
		} else if (extension.equalsIgnoreCase(".JPG")) {
			tipoFichero = TypeFile.jpg;
		} else if (extension.equalsIgnoreCase(".JPEG")) {
			tipoFichero = TypeFile.jpeg;
		} else if (extension.equalsIgnoreCase(".ZIP")) {
			tipoFichero = TypeFile.zip;
		}

		ViafirmaClient viafirmaClient = getForzarViafirmaClient(ConstantesViafirma.REGISTRADORES);

		idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("docEscritura_" + idTramite + extension, bytesAfirmar, alias, null, tipoFichero, TypeFormatSign.CMS_DETACHED);

		request.getSession().setAttribute("firmaPdfEscrituraBytes", XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma));
		request.getSession().setAttribute("selloPdfEscrituraBytes", XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma));

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + " signHsmDocEscritura");
		
		 getForzarViafirmaClient(ConstantesViafirma.OEGAM);

		return idFirma;

	}

	/**
	 * Firma en servidor con hsm un array de bytes del xml dpr de una escritura.
	 * @param request
	 * @param bytesAfirmar
	 * @param idTramite
	 * @param extension (del fichero)
	 * @param alias (del certificado)
	 * @return El identificador de firma devuelto por Viafirma
	 * @throws Exception
	 */
	public String signHsmDpr(HttpServletRequest request, byte[] bytesAfirmar, String idTramite, String alias) throws Throwable {

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " signHsmDpr");

		String idFirma = null;
		ViafirmaClient viafirmaClient = getForzarViafirmaClient(ConstantesViafirma.REGISTRADORES);

		idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("dpr_" + idTramite + ".xml", bytesAfirmar, alias, null, TypeFile.XML, TypeFormatSign.CMS_DETACHED);

		request.getSession().setAttribute("firmaDprBytes", XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma));
		request.getSession().setAttribute("selloDprBytes", XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma));

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + " signHsmDpr");
		
		getForzarViafirmaClient(ConstantesViafirma.OEGAM);
		return idFirma;

	}
	
	/**
	 * Firma en servidor con hsm un array de bytes del xml dpr de una escritura.
	 * @param bytesAfirmar
	 * @param idTramite
	 * @param extension (del fichero)
	 * @param alias (del certificado)
	 * @return El identificador de firma devuelto por Viafirma
	 * @throws Exception
	 */
	public String signHsmDpr(byte[] bytesAfirmar, String idTramite, String alias) throws Throwable {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " signHsmDpr");

		String idFirma = null;
		ViafirmaClient viafirmaClient = getForzarViafirmaClient(ConstantesViafirma.REGISTRADORES);

		idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("dpr_" + idTramite + ".xml", bytesAfirmar, alias, null, TypeFile.XML, TypeFormatSign.CMS_DETACHED);

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + " signHsmDpr");
		getForzarViafirmaClient(ConstantesViafirma.OEGAM);
		return idFirma;
	}

	// Para pruebas. Reconstruye el array de bytes para verificar la integridad del contenido:
	public void pruebaIntegridad(byte[] contenido) {
		System.out.println("Contenido del fichero:");
		for (byte temp : contenido) {
			char c = (char) temp;
			System.out.print(c);
		}
	}

	public void firmarPDFAcuse(byte[] resumenPdf, String idTramite, String tipoMensaje, HttpServletRequest request, HttpServletResponse response) throws OegamExcepcion {

		try {

			logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "firmarPDFAcuse");

			ViafirmaClient viafirmaClient = getForzarViafirmaClient(ConstantesViafirma.REGISTRADORES);

			String idTemporal = viafirmaClient.prepareFirmaWithTypeFileAndFormatSign("resumen_" + tipoMensaje + "_" + idTramite + ".txt", TypeFile.TXT, TypeFormatSign.CMS_DETACHED, resumenPdf);

			request.getSession().setAttribute("tipoMensajeFirmado", "resumenAcuse");

			logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "firmarPDFAcuse");

			viafirmaClient.solicitarFirma(idTemporal, request, response, ConstantesViafirma.URL_RESPUESTA_VIAFIRMA);
			getForzarViafirmaClient(ConstantesViafirma.OEGAM);
		} catch (Throwable e) {
			try {
				logReg.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmarPDFAcuse", e);
				request.setAttribute("mensajeError", "Ocurrió el error: " + e);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			} catch (Exception e1) {
				logReg.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "firmarPDFAcuse", e1);
			}
		}
	}

	/*
	 * Proceso de firma de un array de bytes con un certificado digital a través del software de viafirma.
	 */
	public void firmarSigcer(byte[] datosAfirmar, HttpServletRequest request, HttpServletResponse response) {

		logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + FIRMAR);
		try {
			ViafirmaClient viafirmaClient = getForzarViafirmaClient(ConstantesViafirma.SIGCER);
			// Invocación al método de ViafirmaClient que prepara la solicitud de la firma
			String idTemporal = viafirmaClient.prepareFirmaWithTypeFileAndFormatSign("rm.xml", TypeFile.XML, TypeFormatSign.CMS_DETACHED, datosAfirmar);

			logReg.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + FIRMAR);
			viafirmaClient.solicitarFirma(idTemporal, request, response, ConstantesViafirma.URL_RESPUESTA_VIAFIRMA);
			getForzarViafirmaClient(ConstantesViafirma.OEGAM);
		} catch (Throwable e) {
			try {
				logReg.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + FIRMAR, e);
				request.setAttribute("mensajeError", "Ocurrió el error: " + e);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			} catch (Exception e1) {
				logReg.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + FIRMAR, e1);
			}
		}
	}

	/**
	 * Obtiene una instancia de ViafirmaClient para operaciones en Viafirma
	 * @param aplicacion que instancia el cliente y api con la que se inicializará
	 * @return Instancia de ViafirmaClient para una determinada aplicación del manager de viafirma
	 * @throws Throwable
	 */
	public synchronized ViafirmaClient getClient(String aplicacion) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		if (!ViafirmaClientFactory.isInit()) {
			String apiKey = null;
			String passKey = null;
			if (aplicacion != null && !aplicacion.isEmpty()) {
				apiKey = gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_APP_KEY);
				passKey = gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_PASS_KEY);
			}
			if (apiKey != null && passKey != null) {
				if(ConstantesViafirma.REGISTRADORES.equals(aplicacion)){
					Properties properties = new Properties();
					properties.put(Constantes.PARAM_CONFIG_VIAFIRMA_CLIENT, URL_VIAFIRMA + ";" + URL_VIAFIRMA_WS + ";" + apiKey + ";" + passKey);
					Long maxSize = 100 * 1024l * 1024l;
					String maxSizeProperty = String.valueOf(maxSize);
					properties.put(Constantes.PARAM_MAX_SIZE, maxSizeProperty);
					ViafirmaClientFactory.init(properties);
				} else {
					ViafirmaClientFactory.init(gestorPropiedades.valorPropertie(URL_VIAFIRMA), gestorPropiedades.valorPropertie(URL_VIAFIRMA_WS), null, gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_APP_KEY),
							gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_PASS_KEY));
				}
			} else {
				ViafirmaClientFactory.init(gestorPropiedades.valorPropertie(URL_VIAFIRMA), gestorPropiedades.valorPropertie(URL_VIAFIRMA_WS));
			}
		}
		return ViafirmaClientFactory.getInstance();
	}
	
	public synchronized ViafirmaClient getForzarViafirmaClient(String aplicacion) {
			String apiKey = null;
			String passKey = null;
			if (aplicacion != null && !aplicacion.isEmpty()) {
				apiKey = gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_APP_KEY);
				passKey = gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_PASS_KEY);
			}
			if (apiKey != null && passKey != null) {
				if(ConstantesViafirma.REGISTRADORES.equals(aplicacion)){
					Properties properties = new Properties();
					properties.put(Constantes.PARAM_CONFIG_VIAFIRMA_CLIENT, gestorPropiedades.valorPropertie(URL_VIAFIRMA) + ";" +  gestorPropiedades.valorPropertie(URL_VIAFIRMA_WS) + ";" + apiKey + ";" + passKey);
					Long maxSize = 100 * 1024l * 1024l;
					String maxSizeProperty = String.valueOf(maxSize);
					properties.put(Constantes.PARAM_MAX_SIZE, maxSizeProperty);
					ViafirmaClientFactory.init(properties);
				} else {
					ViafirmaClientFactory.init(gestorPropiedades.valorPropertie(URL_VIAFIRMA), gestorPropiedades.valorPropertie(URL_VIAFIRMA_WS), null, gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_APP_KEY),
							gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_PASS_KEY));
				}
			} else {
				ViafirmaClientFactory.init(gestorPropiedades.valorPropertie(URL_VIAFIRMA), gestorPropiedades.valorPropertie(URL_VIAFIRMA_WS));
			}
		return ViafirmaClientFactory.getInstance();
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////// FIN REGISTRADORES ////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////////

	public byte[] firmarXMLTrafico(byte[] Afirmar, String aliasColegiado) throws InternalException {
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		String codFirma=null;
		try {
			codFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", Afirmar, aliasColegiado, null, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
		} catch (Throwable e) {
			log.error("Error recuperando el soap firmado de eeff, error: ", e);
		}
		return viafirmaClient.getDocumentoCustodiado(codFirma);
	}

	public byte[] firmarConsultasDev(byte[] xml, String alias) {
		byte[] firma = null;
		try {
			Documento documento = new Documento("doc.xml", xml, TypeFile.XML, TypeFormatSign.XADES_BES);
			Policy policy = new Policy();
			policy.setTypeFormatSign(TypeFormatSign.XADES_BES);
			policy.setTypeSign(TypeSign.ENVELOPED);
			policy.addParameter(PolicyParams.NODE_ID_TO_SIGN.getKey(), "Body");
			policy.addParameter(PolicyParams.ENVELOPED_TARGET_NODE.getKey(), "Security");

			ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);

			String idFirma = viafirmaClient.signByServerWithPolicy(policy, documento, alias, null);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirma);
		} catch(Exception e){
			log.error("Error en la firma de consultas dev", e);
		}catch (Throwable e) {
			log.error("Error recuperando el soap firmado de eeff, error: ", e);
		}
		return firma;
	}
	
	public byte[] firmarSoapAtex5(byte[] xml, String alias) {
		byte[] firma = null;
		try {
			Documento documento = new Documento("doc.xml", xml, TypeFile.XML, TypeFormatSign.XMLDSIG);
			Policy policy = new Policy();
		    policy.setTypeSign(TypeSign.ENVELOPED);
		    policy.setTypeFormatSign(TypeFormatSign.XMLDSIG);
		    policy.addParameter(PolicyParams.NODE_ID_TO_SIGN.getKey(), "Body");
		    policy.addParameter(PolicyParams.ENVELOPED_TARGET_NODE.getKey(), "Security");
		    ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			

			String idFirma = viafirmaClient.signByServerWithPolicy(policy, documento, alias, null);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirma);
		} catch(Exception e){
			log.error("Error en la firma de atex5", e);
		}catch (Throwable e) {
			log.error("Error recuperando el soap firmado de eeff, error: ", e);
		}
		return firma;
	}

	public byte[] firmarCompraTasas(byte[] xml, String alias) {
		byte[] firma = null;
		try {
			Documento documento = new Documento("doc.xml", xml, TypeFile.XML, TypeFormatSign.XADES_BES);
			Policy policy = new Policy();
			policy.setTypeSign(TypeSign.ENVELOPED);
			policy.setTypeFormatSign(TypeFormatSign.XADES_BES);
			policy.addParameter(PolicyParams.NODE_ID_TO_SIGN.getKey(), "Body");
			policy.addParameter(PolicyParams.ENVELOPED_TARGET_NODE.getKey(), "Security");

			ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);

			String idFirma = viafirmaClient.signByServerWithPolicy(policy, documento, alias, null);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirma);
		} catch(Exception e){
			log.error("Error en la firma de compra de tasas", e);
		}catch (Throwable e) {
			log.error("Error recuperando el soap firmado de eeff, error: ", e);
		}
		return firma;
	}

	public String firmarEEFF(byte[] datosAfirmar, String alias) {
		log.info("EEFF: Inicio-- firmar EEFF");
		String idFirma = null;
		ViafirmaClient viafirmaClient = null;
		String docFirmado = null;
		try {
			viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmado.xml", datosAfirmar, alias, null, TypeFile.XML, TypeFormatSign.XADES_T_ENVELOPED);
			log.info(DOCUMENTO_FIRMADO_CON_EL_ALIAS + alias);
			if (idFirma != null) {
				if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
					Thread.sleep(1500);
				}
				byte[] documentoCustodiado = viafirmaClient.getDocumentoCustodiado(idFirma);
				docFirmado = new String(documentoCustodiado,"UTF-8");
			}
		} catch (InternalException e) {
			log.error("Ha sucedido un error a la hora de firmar el xml, error: ", e);
		} catch (UnsupportedEncodingException e) {
			log.error("Error recuperando el documento firmado", e);
		}catch (Throwable e) {
			log.error("Error recuperando el soap firmado de eeff, error: ", e);
		}
		return docFirmado;
	}

	public String firmarHandlerEEFF(String aFirmar, String alias) {
		log.info("EEFF: Inicio-- firmar EEFF");
		String idFirma = null;
		ViafirmaClient viafirmaClient = null;
		String docFirmado = null;
		try {
			viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			byte[] datosAfirmar = aFirmar.getBytes();
			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmado.xml", datosAfirmar, alias, null, TypeFile.XML, TypeFormatSign.XMLDSIG,"Body");
			log.info("Peticion Soap de EEFF firmado con el alias: " + alias);
			if (idFirma != null) {
				if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
					Thread.sleep(1500);
				}
				byte[] documentoCustodiado = viafirmaClient.getDocumentoCustodiado(idFirma);
				docFirmado = new String(documentoCustodiado,"UTF-8");
			}
		} catch (InternalException e) {
			log.error("Ha sucedido un error a la hora de firmar la peticion soap de eeff, error: ", e);
		} catch (UnsupportedEncodingException e) {
			log.error("Error recuperando el soap firmado de eeff, error: ", e);
		} catch (Throwable e) {
				log.error("Error recuperando el soap firmado de eeff, error: ", e);
		}
		return docFirmado;
	}
	
	public byte[] firmarIvtm(byte[] xml, String alias) throws Exception {
		byte[] firma = null;
		try {
			Documento documento = new Documento("doc.xml", xml, TypeFile.XML, TypeFormatSign.XMLDSIG);
			Policy policy = new Policy();
		    policy.setTypeSign(TypeSign.ENVELOPED);
		    policy.setTypeFormatSign(TypeFormatSign.XMLDSIG);
		    policy.addParameter(PolicyParams.NODE_ID_TO_SIGN.getKey(), "Body");
		    policy.addParameter(PolicyParams.ENVELOPED_TARGET_NODE.getKey(), "Security");
		    ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			
		    String idFirma = viafirmaClient.signByServerWithPolicy(policy, documento, alias, null);
		    if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirma);
		    	

		}catch (Throwable e) {
			log.error(e);
		
		}
		return firma;
	}
	
	
	 public byte[] firmarCAYC(byte[] xml, String alias) throws Exception {
	     byte[] firma = null;
	 
	     String temporal= new String (xml);
	     temporal=temporal.replaceAll(" xmlns=\"\"", "");
	 	 String Trozo="xmlns=\"http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices\"";
	 	 temporal=temporal.replaceAll(Trozo, "");
	     xml=temporal.getBytes();
	    
			try {
			
				ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			    String idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmado.xml", xml, alias, null, TypeFile.XML, TypeFormatSign.XMLDSIG,"Body");
			    if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
					Thread.sleep(1500);
				}
				firma = viafirmaClient.getDocumentoCustodiado(idFirma);

				
			} catch(Exception e){
				log.error("Error en la firma de compra de CAYC", e);
			}catch (Throwable e) {
				log.error("Error recuperando el soap firmado de CAYC, error: ", e);
			}
			return firma;
	  }
  
	 public byte[] firmarLopd(byte[] pdf, String aliasColegiado) {
		byte[] firma = null;
		try {
			Documento documento = new Documento("doc.pdf", pdf, TypeFile.PDF, TypeFormatSign.PAdES_BASIC);
			Policy policy = new Policy();
			policy.setTypeSign(TypeSign.ATTACHED);
			policy.setTypeFormatSign(TypeFormatSign.PAdES_BASIC);
			policy.addParameter(PolicyParams.DIGITAL_SIGN_PAGE.getKey(),"5");
			policy.addParameter(PolicyParams.DIGITAL_SIGN_RECTANGLE.getKey(), new
			    		org.viafirma.cliente.vo.Rectangle(40,125,500,75));
			policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_HIDE_STATUS.getKey(),"true");
			policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_TYPE.getKey(),"QR-BAR-H");
			policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_TEXT.getKey(), "Firmado por [CN] con DNI [SERIALNUMBER]");
			ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
			String idFirma = viafirmaClient.signByServerWithPolicy(policy, documento, aliasColegiado, null);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirma);
				
			documento = new Documento("doc.pdf", firma, TypeFile.PDF, TypeFormatSign.PAdES_BASIC);
			policy = new Policy();
			policy.setTypeSign(TypeSign.ATTACHED);
			policy.setTypeFormatSign(TypeFormatSign.PAdES_BASIC);
			policy.addParameter(PolicyParams.DIGITAL_SIGN_PAGE.getKey(),"5");
			policy.addParameter(PolicyParams.DIGITAL_SIGN_RECTANGLE.getKey(), new
			    		org.viafirma.cliente.vo.Rectangle(40,225,500,75));
			policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_HIDE_STATUS.getKey(),"true");
			policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_TYPE.getKey(),"QR-BAR-H");
			policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_TEXT.getKey(), "Firmado por [CN] con DNI [SERIALNUMBER]");
			String aliasColegio = gestorPropiedades.valorPropertie("trafico.claves.colegio.alias");
			String idFirmaColegio = viafirmaClient.signByServerWithPolicy(policy, documento, aliasColegio, null);
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			firma = viafirmaClient.getDocumentoCustodiado(idFirmaColegio);
		} catch(Exception e){
			log.error("Error en la firma de atex5", e);
		}catch (Throwable e) {
			log.error("Error en la firma de atex5", e);
		}
		return firma;
	 }
		
	 public byte[] firmarAutorizacionColegial(byte[] pdf, String aliasColegiado) {
			byte[] firma = null;
			try {
				Documento documento = new Documento("doc.pdf", pdf, TypeFile.PDF, TypeFormatSign.PAdES_BASIC);
				Policy policy = new Policy();
				ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
				policy = new Policy();
				policy.setTypeSign(TypeSign.ATTACHED);
				policy.setTypeFormatSign(TypeFormatSign.PAdES_BASIC);
				policy.addParameter(PolicyParams.DIGITAL_SIGN_PAGE.getKey(),"0");
				policy.addParameter(PolicyParams.DIGITAL_SIGN_RECTANGLE.getKey(), new
				    		org.viafirma.cliente.vo.Rectangle(150,80,300,40));
				policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_HIDE_STATUS.getKey(),"true");
				policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_TYPE.getKey(),"QR-BAR-H");
				policy.addParameter(PolicyParams.DIGITAL_SIGN_STAMPER_TEXT.getKey(), "Firmado por [CN] con DNI [SERIALNUMBER]");
				String aliasColegio = gestorPropiedades.valorPropertie("trafico.claves.colegio.alias");
				String idFirmaColegio = viafirmaClient.signByServerWithPolicy(policy, documento, aliasColegio, null);
				if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
					Thread.sleep(1500);
				}
				firma = viafirmaClient.getDocumentoCustodiado(idFirmaColegio);
			} catch(Exception e){
				log.error("Error en la firma de autorizacion colegial", e);
			}catch (Throwable e) {
				log.error("Error en la firma de autorizacion colegial", e);
			}
			return firma;
		 }
  
	 public byte[] firmarHandlerXmlDSig(byte[] datosAfirmar, String alias) {
			log.info("Inicio-- firmar XMLDSIG");
			try {
				ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
				String idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("firmado.xml", datosAfirmar, alias, null, TypeFile.XML, TypeFormatSign.XMLDSIG,"Body");
				log.info("Peticion Soap XMLDSIG firmada con el alias: " + alias);
				if (idFirma != null) {
					if ("SI".equals(gestorPropiedades.valorPropertie("viafirma.sleep"))) {
						Thread.sleep(1500);
					}
					return viafirmaClient.getDocumentoCustodiado(idFirma);
				}
			} catch (Throwable e) {
					log.error("Error recuperando el soap firmado con tipo firma XMLDSIG, error: ", e);
			}
			return null;
		}

	public byte[] firmarInteveCompleto(byte[] datosAfirmar, String alias) {
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		try {
			Documento documento = new Documento("firma.xml", datosAfirmar, TypeFile.XML, TypeFormatSign.XMLDSIG);
			Policy policy = new Policy();
			policy.setTypeFormatSign(TypeFormatSign.XMLDSIG);
			policy.setTypeSign(TypeSign.ENVELOPED);
			policy.addParameter(PolicyParams.NODE_ID_TO_SIGN.getKey(), "Body");
	               policy.addParameter(PolicyParams.ENVELOPED_TARGET_NODE.getKey(), "Security");
			String idSign = viafirmaClient.signByServerWithPolicy(policy, documento, alias, null);
			System.out.println(idSign);
			FirmaInfoViafirma info = viafirmaClient.getSignInfo(idSign);
			System.out.println("La validación de la firma XMLDSIG es: " + info.isValid());
			return viafirmaClient.getDocumentoCustodiado(idSign);
		} catch (InternalException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
