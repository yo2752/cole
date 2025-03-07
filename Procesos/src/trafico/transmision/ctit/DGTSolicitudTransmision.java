package trafico.transmision.ctit;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.gescogroup.blackbox.CTITServiceLocator;
import com.gescogroup.blackbox.CTITWS;
import com.gescogroup.blackbox.CTITWSBindingStub;
import com.gescogroup.blackbox.CtitCheckError;
import com.gescogroup.blackbox.CtitFullError;
import com.gescogroup.blackbox.CtitFullImpediment;
import com.gescogroup.blackbox.CtitNotificationError;
import com.gescogroup.blackbox.CtitNotificationImpediment;
import com.gescogroup.blackbox.CtitRequest;
import com.gescogroup.blackbox.CtitTradeError;
import com.gescogroup.blackbox.CtitTradeImpediment;
import com.gescogroup.blackbox.CtitsoapFullResponse;
import com.gescogroup.blackbox.CtitsoapNotificationResponse;
import com.gescogroup.blackbox.CtitsoapRequest;
import com.gescogroup.blackbox.CtitsoapResponse;
import com.gescogroup.blackbox.CtitsoapTradeResponse;
import com.gescogroup.blackbox.ws.SoapConsultaCheckCtitWSHandler;

import colas.procesos.utiles.UtilesProcesos;
import trafico.beans.DatosCTITBean;
import trafico.utiles.UtilesWSCTIT;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * Clase que genera la Solicitud de Transmisión de los trámites que se quieren
 * presentar en la DGT
 */
public class DGTSolicitudTransmision {
	private static final String UTF_8					= "UTF-8";
	private static final String FULL					= "FULL";
	private static final String TRADE					= "TRADE";
	private static final String NOTIFICATION			= "NOTIFICATION";
	private static final String WEBSERVICE_TRANSMISION	= "webservice.ctit.url";
	private static final String TIMEOUT					= "webservice.ctit.timeOut";
	private static final String TEXTO_FULL_LOG 			= "LOG_FULL_CTIT:";
	private static final String TEXTO_TRADE_LOG 		= "LOG_TRADE_CTIT:";
	private static final String TEXTO_NOTIFICATION_LOG	= "LOG_NOTIFICATION_CTIT:";
	private static final String TEXTO_CHECK_LOG			= "LOG_CHECK_CTIT:";
	private static final int TIMEOUT_TRANSMISION = 120000;

	private static ILoggerOegam log = LoggerOegam.getLogger(DGTSolicitudTransmision.class);

	private UtilesProcesos utilesProcesos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	public DGTSolicitudTransmision() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Realiza la presentación del registro de transmisión que recibe como
	 * parámetros en el servicio CTIT de la DGT y devuelve la respuesta que
	 * recibe como resultado.
	 * 
	 * @param peticionXML
	 *            String con formato XML que contiene la información del registro
	 *            de transmision a presentar
	 * @return Devuelve el resultado de la transmisión que es un objeto
	 *         {@link CtitsoapFullResponse}
	 * @throws Exception
	 * @throws OegamExcepcion 
	 */
	public CtitsoapFullResponse fullCTITTransmision(String peticionXML, DatosCTITBean datosCTITBean) throws Exception, OegamExcepcion {
		log.info(TEXTO_FULL_LOG + " ENTRA EN BLACK BOX");
		CtitRequest req = completaRequest(peticionXML, datosCTITBean, FULL);
		CtitsoapFullResponse respuesta;

		try {
			CTITWSBindingStub stub = getStubCTIT();
			new UtilesWSCTIT().cargarAlmacenesTrafico();

			respuesta = stub.fullCTIT(req);
			log.info(" ");
			log.info("------" + TEXTO_FULL_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(TEXTO_FULL_LOG + " customDossierNumber Request: " + req.getCustomDossierNumber());
			log.info(TEXTO_FULL_LOG + " dossierNumber Response: " + respuesta.getDossierNumber());

			/* VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX:
			 * dossierNumber (String): Número que identifica el trámite
			 * dentro de la aplicación Black Box. */
			CtitFullError[] bbErrores = respuesta.getErrorCodes();
			String respuestaError = "";
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(TEXTO_FULL_LOG + " NO HAY LISTA DE ERRORES");
			} else {
				log.error(TEXTO_FULL_LOG + " HAY LISTA DE ERRORES");
				if (bbErrores != null) {
					getUtilesProcesos().listarErroresFullCTIT(bbErrores);
					respuestaError += getUtilesProcesos().getMensajeError(bbErrores);
					log.error(TEXTO_FULL_LOG + " Errores: "+ respuestaError);
				}
			}
			CtitFullImpediment[] listadoImpedimentos = respuesta.getImpedimentCodes();
			if (listadoImpedimentos != null) {
				respuestaError += getUtilesProcesos().getImpedimentosFull(listadoImpedimentos);
				log.error(TEXTO_FULL_LOG + " Impedimentos: " + respuestaError);
			}
		} catch (Exception e) {
			log.error(TEXTO_FULL_LOG + " EXCEPCION: " , e);
			throw e;
		}
		//log.info(TEXTO_FULL_LOG + " SALE DE CTIT");

		return respuesta;
	}

	/**
	 * Realiza la presentación del registro de transmisión que recibe como
	 * parámetros en el servicio CTIT de la DGT y devuelve la respuesta que
	 * recibe como resultado.
	 * 
	 * @param peticionXML
	 *            String con formato XML que contiene la información del registro
	 *            de transmision a presentar
	 * @return Devuelve el resultado de la transmisión que es un objeto
	 *         {@link CtitsoapTradeResponse}
	 * @throws Exception
	 * @throws OegamExcepcion 
	 */
	public CtitsoapTradeResponse tradeCTITTransmision(String peticionXML, DatosCTITBean datosCTITBean) throws Exception, OegamExcepcion {
		log.info(TEXTO_TRADE_LOG + " ENTRA EN BLACK BOX");
		CtitRequest req = completaRequest(peticionXML, datosCTITBean, TRADE);
		CtitsoapTradeResponse respuesta = null;

		try {
			CTITWSBindingStub stub = getStubCTIT();
			//log.info(TEXTO_TRADE_LOG + " generado stub.");

			new UtilesWSCTIT().cargarAlmacenesTrafico();

			respuesta = stub.tradeCTIT(req);
			log.info(" ");
			log.info("------" + TEXTO_TRADE_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(TEXTO_TRADE_LOG + " customDossierNumber Request: " + req.getCustomDossierNumber());
			log.info(TEXTO_TRADE_LOG + " dossierNumber Response: " + respuesta.getDossierNumber());

			/* VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX:
			 * dossierNumber (String): Número que identifica el trámite
			 * dentro de la aplicación Black Box. */
			String respuestaError = "";
			CtitTradeError[] bbErrores = respuesta.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(TEXTO_TRADE_LOG + " NO HAY LISTA DE ERRORES");
			} else {
				log.error(TEXTO_TRADE_LOG + " HAY LISTA DE ERRORES");
				if (bbErrores != null) {
					getUtilesProcesos().listarErroresTradeCTIT(bbErrores);
					respuestaError += getUtilesProcesos().getMensajeError(bbErrores);
					log.error(TEXTO_TRADE_LOG + " Errores: "+ respuestaError);
				}
			}
			CtitTradeImpediment[] listadoImpedimentos = respuesta.getImpedimentCodes();
			if (listadoImpedimentos != null) {
				respuestaError += getUtilesProcesos().getImpedimentosTrade(listadoImpedimentos);
				log.error(TEXTO_TRADE_LOG + " Impedimentos: " + respuestaError);
			}
		} catch (Exception e) {
			log.error(TEXTO_TRADE_LOG + " EXCEPCION: " + e);
			throw e;
		}
		//log.info(TEXTO_TRADE_LOG + " SALE DE CTIT");
		return respuesta;
	}

	/**
	 * Realiza la presentación del registro de transmisión que recibe como
	 * parámetros en el servicio CTIT de la DGT y devuelve la respuesta que
	 * recibe como resultado.
	 * 
	 * @param peticionXML
	 *            String con formato XML q contiene la información del registro
	 *            de transmision a presentar
	 * @return Devuelve el resultado de la transmisión que es un objeto
	 *         {@link CtitsoapTradeResponse}
	 * @throws Exception
	 * @throws OegamExcepcion
	 */
	public CtitsoapNotificationResponse notificationCTITTransmision(String peticionXML, DatosCTITBean datosCTITBean) throws Exception, OegamExcepcion {
		log.info(TEXTO_NOTIFICATION_LOG + " ENTRA EN BLACK BOX");
		CtitRequest req = completaRequest(peticionXML, datosCTITBean, NOTIFICATION);
		CtitsoapNotificationResponse respuesta = null;

		try {
			CTITWSBindingStub stub = getStubCTIT();
			new UtilesWSCTIT().cargarAlmacenesTrafico();

			respuesta = stub.notificationCTIT(req);
			log.info(" ");
			log.info("------" + TEXTO_NOTIFICATION_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(TEXTO_NOTIFICATION_LOG + " customDossierNumber Request: " + req.getCustomDossierNumber());
			log.info(TEXTO_NOTIFICATION_LOG + " dossierNumber Response: " + respuesta.getDossierNumber());

			/* VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX:
			 * dossierNumber (String): Número que identifica el trámite
			 * dentro de la aplicación Black Box. */
			String respuestaError = "";
			CtitNotificationError[] bbErrores = respuesta.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(TEXTO_NOTIFICATION_LOG + " NO HAY LISTA DE ERRORES");
			} else {
				log.error(TEXTO_NOTIFICATION_LOG + " HAY LISTA DE ERRORES");
				if (bbErrores != null) {
					getUtilesProcesos().listarErroresNotificationCTIT(bbErrores);
					respuestaError += getUtilesProcesos().getMensajeError(bbErrores);
					log.error(TEXTO_NOTIFICATION_LOG + " Errores: "+ respuestaError);
				}
			}
			CtitNotificationImpediment[] listadoImpedimentos = respuesta.getImpedimentCodes();
			if (listadoImpedimentos != null) {
				respuestaError += getUtilesProcesos().getImpedimentosNotification(listadoImpedimentos);
				log.error(TEXTO_TRADE_LOG + " Impedimentos: " + respuestaError);
			}
		} catch (Exception e) {
			log.error(TEXTO_NOTIFICATION_LOG + " EXCEPCION: " + e);
			throw e;
		}
		//log.info(TEXTO_NOTIFICATION_LOG + " SALE DE CTIT");
		return respuesta;
	}

	/**
	 * Realiza la presentación del registro de transmisión que recibe como
	 * parámetros en el servicio CTIT de la DGT y devuelve la respuesta que
	 * recibe como resultado.
	 * 
	 * @param peticionXML
	 *            String con formato XML que contiene la información del registro
	 *            de transmision a presentar
	 * @return Devuelve el resultado de la transmisión que es un objeto
	 *         {@link CtitsoapResponse}
	 * @throws Exception
	 * @throws OegamExcepcion
	 * 
	 */
	public CtitsoapResponse checkCTITTransmision(String peticionXML, DatosCTITBean datosCTITBean) throws Exception, OegamExcepcion {
		log.info(TEXTO_CHECK_LOG + " ENTRA EN BLACK BOX");
		CtitsoapRequest req = new CtitsoapRequest();

		/* customDossierNumber Número de expediente en la plataforma origen.
		 * Se obtendrá de TraficoTramiteBean.getNumExpediente() */
		req.setCustomDossierNumber(datosCTITBean.getCustomDossierNumber());
		log.info(TEXTO_CHECK_LOG + " customDossierNumber: " + datosCTITBean.getCustomDossierNumber());

		/* agencyFiscalId CIF de la gestoría que tramita el expediente. */
		req.setAgencyFiscalId(datosCTITBean.getAgencyFiscalId());
		log.info(TEXTO_CHECK_LOG + " agencyFiscalId: " + datosCTITBean.getAgencyFiscalId());

		/* externalSystemFiscalId CIF de la plataforma que realiza la
		 * conexión a la Caja Negra Es el que se usa para la firma en los
		 * PDF. Se obtendrá de TraficoTramiteBean.getNumExpediente() */
		req.setExternalSystemFiscalId(datosCTITBean.getExternalSystemFiscalID());
		log.info(TEXTO_CHECK_LOG + " externalSystemFiscalId: " + datosCTITBean.getExternalSystemFiscalID());

		/** xmlB64 XML en Base 64 con los datos necesarios para hacer la transmisión telemática del
		 * vehículo, también denominada Solicitud de registro de entrada. */
		log.debug(TEXTO_CHECK_LOG + " PETICION XML =" + peticionXML);

		String xmlObjB64 = utiles.doBase64Encode(peticionXML.getBytes(UTF_8));
		log.debug(TEXTO_CHECK_LOG + " BASE64Encoder.encode(peticionXML.getBytes("+UTF_8+") =" + xmlObjB64);
		req.setXmlB64(xmlObjB64);

		CtitsoapResponse respuesta = null;

		try {
			URL miURL = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_TRANSMISION));
			String timeout = gestorPropiedades.valorPropertie(TIMEOUT);

			CTITServiceLocator ctitLocator = new CTITServiceLocator();

			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(),ctitLocator.getCTITServiceWSDDServiceName());
			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = ctitLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapConsultaCheckCtitWSHandler.class);

			Map<String, Object> filesConfig = new HashMap<>();

			filesConfig.put(SoapConsultaCheckCtitWSHandler.PROPERTY_KEY_ID, datosCTITBean.getCustomDossierNumber());
			logHandlerInfo.setHandlerConfig(filesConfig);

			list.add(logHandlerInfo);

			CTITWS servicio = new CTITServiceLocator().getCTITService(miURL);
			CTITWSBindingStub stub = (CTITWSBindingStub) servicio;
			stub.setTimeout(timeout != null && !timeout.equals("")?Integer.parseInt(timeout):TIMEOUT_TRANSMISION);
			//log.info(TEXTO_CHECK_LOG + " timeout: " + timeout + ", si es nulo igual a " + TIMEOUT_TRANSMISION);
			new UtilesWSCTIT().cargarAlmacenesTrafico();
			respuesta = stub.checkCTIT(req);
			log.info(" ");
			log.info("------" + TEXTO_CHECK_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(TEXTO_CHECK_LOG + " customDossierNumber Request: " + req.getCustomDossierNumber());
			log.info(TEXTO_CHECK_LOG + " dossierNumber Response: " + respuesta.getDossierNumber());

			/* VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX:
			 * dossierNumber (String): Número que identifica el trámite dentro de la aplicación Black Box.
			 * codeHG (String): Código de homologación de gestores que sirve para verificar la validez del número de expediente.
			 * dgtResponse (String): Resultado íntegro del trámite CTIT que la DGT devuelve.
			 * errorCodes (List): Lista de parejas de código de error y valor para la petición tramitada.
			 * plateNumber (String): Matrícula asignada al vehículo en caso de que el proceso de transmisión finaliza correctamente. */
			CtitCheckError[] bbErrores = respuesta.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(TEXTO_CHECK_LOG + " NO HAY LISTA DE ERRORES");
			} else {
				log.error(TEXTO_CHECK_LOG + " HAY LISTA DE ERRORES");
				String respuestaError = "";
				if (bbErrores != null) {
					getUtilesProcesos().listarErroresCheckCTIT(bbErrores);
					respuestaError += getUtilesProcesos().getMensajeError(bbErrores);
					log.error(TEXTO_CHECK_LOG + " Errores: "+ respuestaError);
				}
			}
		} catch (Exception e) {
			log.error(TEXTO_CHECK_LOG + " EXCEPCION: " + e);
			throw e;
		}
		//log.info(TEXTO_CHECK_LOG + " SALE DE CTIT");
		return respuesta;
	}

	private CtitRequest completaRequest(String peticionXML, DatosCTITBean datosCTITBean, String metodo) throws UnsupportedEncodingException {
		CtitRequest req = new CtitRequest();

		/* customDossierNumber Número de expediente en la plataforma origen. Se obtendrá de TraficoTramiteBean.getNumExpediente() */
		req.setCustomDossierNumber(datosCTITBean.getCustomDossierNumber());
		log.info("LOG_"+metodo+"_CTIT: customDossierNumber: " + datosCTITBean.getCustomDossierNumber());

		/* agencyFiscalId CIF de la gestoría que tramita el expediente. */
		req.setAgencyFiscalId(datosCTITBean.getAgencyFiscalId());
		log.info("LOG_"+metodo+"_CTIT: agencyFiscalId: " + datosCTITBean.getAgencyFiscalId());

		/* externalSystemFiscalId CIF de la plataforma que realiza la conexión a la Caja Negra Es el que se usa para la firma en los PDF.
		 * Se obtendrá de TraficoTramiteBean.getNumExpediente() */
		req.setExternalSystemFiscalId(datosCTITBean.getExternalSystemFiscalID());
		log.info("LOG_"+metodo+"_CTIT: externalSystemFiscalId: " + datosCTITBean.getExternalSystemFiscalID());

		/* xmlB64 XML en Base 64 con los datos necesarios para hacer la transmisión telemática del vehículo, también denominada Solicitud de registro de entrada. */
		log.debug("LOG_"+metodo+"_CTIT PETICION XML =" + peticionXML);

//		String xmlObjB64 = UtilesRegistradores.doBase64Encode(peticionXML.getBytes(UTF_8));
		String xmlObjB64 = utiles.doBase64Encode(peticionXML.getBytes(UTF_8));
		log.debug("LOG_"+metodo+"_CTIT BASE64Encoder.encode(peticionXML.getBytes("+UTF_8+")) =" + xmlObjB64);
		req.setXmlB64(xmlObjB64);

		if (datosCTITBean.getTara() != null) {
			req.setTara(Integer.parseInt(datosCTITBean.getTara()));
			log.info("LOG_"+metodo+"_CTIT: tara: " + req.getTara());
		}

		if (datosCTITBean.getPlazas() != null) {
			req.setSeatPlaces(datosCTITBean.getPlazas().intValue());
			log.info("LOG_"+metodo+"_CTIT: plazas: " + req.getSeatPlaces());
		}

		if (datosCTITBean.getPesoMma() != null) {
			req.setMma(Integer.parseInt(datosCTITBean.getPesoMma()));
			log.info("LOG_"+metodo+"_CTIT: mma " + req.getMma());
		}

		if (datosCTITBean.getIdServicio() != null) {
			req.setCurrentVehiclePurpose(datosCTITBean.getIdServicio());
			log.info("LOG_"+metodo+"_CTIT: servicio " + req.getCurrentVehiclePurpose());
		}

		if (datosCTITBean.getSellerINECode() != null) {
			req.setSellerINECode(datosCTITBean.getSellerINECode());
		}

		if (datosCTITBean.getFirstMatriculationINECode()!=null){
			req.setFirstMatriculationINECode(datosCTITBean.getFirstMatriculationINECode());
		}

		return req;
	}

	private CTITWSBindingStub getStubCTIT() throws OegamExcepcion, MalformedURLException, ServiceException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_TRANSMISION));
		String timeout = gestorPropiedades.valorPropertie(TIMEOUT);

		//if (null != miURL) {
		//	log.info(TEXTO_CTIT_LOG + " miURL: " + miURL.toString());
		//}

		CTITWS servicio = new CTITServiceLocator().getCTITService(miURL);
		//log.info(TEXTO_CTIT_LOG + " generado servicio.");

		CTITWSBindingStub stub = (CTITWSBindingStub) servicio;
		//log.info(TEXTO_CTIT_LOG + " generado stub.");

		stub.setTimeout(timeout != null && !timeout.equals("") ? Integer.parseInt(timeout) : TIMEOUT_TRANSMISION);
		//log.info(TEXTO_CTIT_LOG + " timeout: " + timeout + ", si es nulo igual a " + TIMEOUT_TRANSMISION);
		return stub;
	}

	/* ***************************************************** */
	/* MODELOS ********************************************* */
	/* ***************************************************** */

	public UtilesProcesos getUtilesProcesos() {
		if (utilesProcesos == null) {
			utilesProcesos = new UtilesProcesos();
		}
		return utilesProcesos;
	}

	public void setUtilesProcesos(UtilesProcesos utilesProcesos) {
		this.utilesProcesos = utilesProcesos;
	}
}