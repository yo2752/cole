package org.gestoresmadrid.oegam2comun.trafico.dgt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.TipoConsentimiento;
import org.gestoresmadrid.oegam2comun.sega.utiles.XmlCheckCTITSegaFactory;
import org.gestoresmadrid.oegam2comun.sega.utiles.XmlCtitSegaFactory;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCTITDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import trafico.utiles.UtilesWSCTIT;
import trafico.utiles.XMLPruebaCertificado;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class DGTTransmision implements Serializable {

	private static final long serialVersionUID = -345097163881637136L;

	private static ILoggerOegam log = LoggerOegam.getLogger(DGTTransmision.class);

	private static final String UTF_8 = "UTF-8";
	private static final String FULL = "FULL";
	private static final String TRADE = "TRADE";
	private static final String NOTIFICATION = "NOTIFICATION";

	private static final String WEBSERVICE_TRANSMISION = "webservice.ctit.url";
	private final static String TIMEOUT = "webservice.ctit.timeOut";

	private final static String TEXTO_FULL_LOG = "LOG_FULL_CTIT:";
	private final static String TEXTO_TRADE_LOG = "LOG_TRADE_CTIT:";
	private final static String TEXTO_NOTIFICATION_LOG = "LOG_NOTIFICATION_CTIT:";
	private final static String TEXTO_CHECK_LOG = "LOG_CHECK_CTIT:";

	private static final String CADENA_VACIA = "";

	private static final String ERROR = "ERROR";

	private static final int TIMEOUT_TRANSMISION = 120000;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	public CtitsoapFullResponse fullCTITTransmision(String peticionXML, DatosCTITDto datosCTITDto) throws Exception, OegamExcepcion {
		log.info(TEXTO_FULL_LOG + " ENTRA EN BLACK BOX");
		CtitRequest req = completaRequest(peticionXML, datosCTITDto, FULL);
		CtitsoapFullResponse respuesta;

		try {
			CTITWSBindingStub stub = getStubCTIT();
			new UtilesWSCTIT().cargarAlmacenesTrafico();

			respuesta = stub.fullCTIT(req);
			log.info(" ");
			log.info("------" + TEXTO_FULL_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(TEXTO_FULL_LOG + " customDossierNumber Request:  " + req.getCustomDossierNumber());
			log.info(TEXTO_FULL_LOG + " dossierNumber Response: " + respuesta.getDossierNumber());

			/*
			 * VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX: dossierNumber (String): Número que identifica el trámite dentro de la aplicación Black Box.
			 */
			CtitFullError[] bbErrores = respuesta.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(TEXTO_FULL_LOG + " NO HAY LISTA DE ERRORES");
			} else {
				log.error(TEXTO_FULL_LOG + " HAY LISTA DE ERRORES");
				log.error("Listando errores FullCtit");
				for (int i = 0; i < bbErrores.length; i++) {
					log.error(ConstantesProcesos.PROCESO_FULLCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO + " " + bbErrores[i].getKey());
					log.error(ConstantesProcesos.PROCESO_FULLCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + bbErrores[i].getMessage());
				}
				log.error(TEXTO_FULL_LOG + " Errores: " + getMensajeError(bbErrores));
			}
			CtitFullImpediment[] listadoImpedimentos = respuesta.getImpedimentCodes();
			if (listadoImpedimentos != null) {
				log.error(TEXTO_FULL_LOG + " Impedimentos: " + getImpedimentosFull(listadoImpedimentos));
			}
		} catch (Exception e) {
			log.error(TEXTO_FULL_LOG + " EXCEPCION: " + e);
			throw e;
		}
		return respuesta;
	}

	public CtitsoapTradeResponse tradeCTITTransmision(String peticionXML, DatosCTITDto datosCTITDto) throws Exception, OegamExcepcion {
		log.info(TEXTO_TRADE_LOG + " ENTRA EN BLACK BOX");
		CtitRequest req = completaRequest(peticionXML, datosCTITDto, TRADE);
		CtitsoapTradeResponse respuesta = null;

		try {
			CTITWSBindingStub stub = getStubCTIT();
			// log.info(TEXTO_TRADE_LOG + " generado stub.");

			new UtilesWSCTIT().cargarAlmacenesTrafico();

			respuesta = stub.tradeCTIT(req);
			log.info(" ");
			log.info("------" + TEXTO_TRADE_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(TEXTO_TRADE_LOG + " customDossierNumber Request:  " + req.getCustomDossierNumber());
			log.info(TEXTO_TRADE_LOG + " dossierNumber Response: " + respuesta.getDossierNumber());

			/*
			 * VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX: dossierNumber (String): Número que identifica el trámite dentro de la aplicación Black Box.
			 */
			CtitTradeError[] bbErrores = respuesta.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(TEXTO_TRADE_LOG + " NO HAY LISTA DE ERRORES");
			} else {
				log.error(TEXTO_TRADE_LOG + " HAY LISTA DE ERRORES");

				if (bbErrores != null) {
					log.error("Listando errores TradeCtit");
					for (int i = 0; i < bbErrores.length; i++) {
						log.error(ConstantesProcesos.PROCESO_TRADECTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO + " " + bbErrores[i].getKey());
						log.error(ConstantesProcesos.PROCESO_TRADECTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + bbErrores[i].getMessage());
					}
					log.error(TEXTO_TRADE_LOG + " Errores: " + getMensajeError(bbErrores));
				}
			}
			CtitTradeImpediment[] listadoImpedimentos = respuesta.getImpedimentCodes();
			if (listadoImpedimentos != null) {
				log.error(TEXTO_TRADE_LOG + " Impedimentos: " + getImpedimentosTrade(listadoImpedimentos));
			}
		} catch (Exception e) {
			log.error(TEXTO_TRADE_LOG + " EXCEPCION: " + e);
			throw e;
		}
		return respuesta;
	}

	public CtitsoapNotificationResponse notificationCTITTransmision(String peticionXML, DatosCTITDto datosCTITDto) throws Exception, OegamExcepcion {
		log.info(TEXTO_NOTIFICATION_LOG + " ENTRA EN BLACK BOX");
		CtitRequest req = completaRequest(peticionXML, datosCTITDto, NOTIFICATION);
		CtitsoapNotificationResponse respuesta = null;

		try {
			CTITWSBindingStub stub = getStubCTIT();
			new UtilesWSCTIT().cargarAlmacenesTrafico();

			respuesta = stub.notificationCTIT(req);
			log.info(" ");
			log.info("------" + TEXTO_NOTIFICATION_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(TEXTO_NOTIFICATION_LOG + " customDossierNumber Request:  " + req.getCustomDossierNumber());
			log.info(TEXTO_NOTIFICATION_LOG + " dossierNumber Response: " + respuesta.getDossierNumber());

			/*
			 * VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX: dossierNumber (String): Número que identifica el trámite dentro de la aplicación Black Box.
			 */
			CtitNotificationError[] bbErrores = respuesta.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(TEXTO_NOTIFICATION_LOG + " NO HAY LISTA DE ERRORES");
			} else {
				log.error(TEXTO_NOTIFICATION_LOG + " HAY LISTA DE ERRORES");
				if (bbErrores != null) {
					log.error("Listando errores NotificationCtit");
					for (int i = 0; i < bbErrores.length; i++) {
						log.error(ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO + " " + bbErrores[i].getKey());
						log.error(ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + bbErrores[i].getMessage());
					}
					log.error(TEXTO_NOTIFICATION_LOG + " Errores: " + getMensajeError(bbErrores));
				}
			}
			CtitNotificationImpediment[] listadoImpedimentos = respuesta.getImpedimentCodes();
			if (listadoImpedimentos != null) {
				log.error(TEXTO_TRADE_LOG + " Impedimentos: " + getImpedimentosNotification(listadoImpedimentos));
			}
		} catch (Exception e) {
			log.error(TEXTO_NOTIFICATION_LOG + " EXCEPCION: " + e);
			throw e;
		}
		return respuesta;
	}

	public CtitsoapResponse checkCTITTransmision(String peticionXML, DatosCTITDto datosCTITDto) throws Exception, OegamExcepcion {
		log.info(TEXTO_CHECK_LOG + " ENTRA EN BLACK BOX");
		CtitsoapRequest req = new CtitsoapRequest();

		/*
		 * customDossierNumber Número de expediente en la plataforma origen. Se obtendrá de TraficoTramiteBean.getNumExpediente()
		 */
		req.setCustomDossierNumber(datosCTITDto.getCustomDossierNumber());
		log.info(TEXTO_CHECK_LOG + " customDossierNumber: " + datosCTITDto.getCustomDossierNumber());

		/* agencyFiscalId CIF de la gestoría que tramita el expediente. */
		req.setAgencyFiscalId(datosCTITDto.getAgencyFiscalId());
		log.info(TEXTO_CHECK_LOG + " agencyFiscalId: " + datosCTITDto.getAgencyFiscalId());

		/*
		 * externalSystemFiscalId CIF de la plataforma que realiza la conexión a la Caja Negra Es el que se usa para la firma en los PDF. Se obtendrá de TraficoTramiteBean.getNumExpediente()
		 */
		req.setExternalSystemFiscalId(datosCTITDto.getExternalSystemFiscalID());
		log.info(TEXTO_CHECK_LOG + " externalSystemFiscalId: " + datosCTITDto.getExternalSystemFiscalID());

		/**
		 * xmlB64 XML en Base 64 con los datos necesarios para hacer la transmisión telemática del vehículo, también denominada Solicitud de registro de entrada.
		 */
		log.debug(TEXTO_CHECK_LOG + " PETICION XML =" + peticionXML);

		String xmlObjB64 = utiles.doBase64Encode(peticionXML.getBytes(UTF_8));
		log.debug(TEXTO_CHECK_LOG + " BASE64Encoder.encode(peticionXML.getBytes(" + UTF_8 + ") =" + xmlObjB64);
		req.setXmlB64(xmlObjB64);

		CtitsoapResponse respuesta = null;

		try {
			URL miURL = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_TRANSMISION));
			String timeout = gestorPropiedades.valorPropertie(TIMEOUT);

			CTITServiceLocator ctitLocator = new CTITServiceLocator();

			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(), ctitLocator.getCTITServiceWSDDServiceName());
			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = ctitLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapConsultaCheckCtitWSHandler.class);

			Map<String, Object> filesConfig = new HashMap<String, Object>();

			filesConfig.put(SoapConsultaCheckCtitWSHandler.PROPERTY_KEY_ID, datosCTITDto.getCustomDossierNumber());
			logHandlerInfo.setHandlerConfig(filesConfig);

			list.add(logHandlerInfo);

			CTITWS servicio = ctitLocator.getCTITService(miURL);
			CTITWSBindingStub stub = (CTITWSBindingStub) servicio;
			stub.setTimeout(timeout != null && !timeout.equals("") ? Integer.parseInt(timeout) : TIMEOUT_TRANSMISION);

			new UtilesWSCTIT().cargarAlmacenesTrafico();
			respuesta = stub.checkCTIT(req);
			log.info(" ");
			log.info("------" + TEXTO_CHECK_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(TEXTO_CHECK_LOG + " customDossierNumber Request:  " + req.getCustomDossierNumber());
			log.info(TEXTO_CHECK_LOG + " dossierNumber Response: " + respuesta.getDossierNumber());

			/*
			 * VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX: dossierNumber (String): Número que identifica el trámite dentro de la aplicación Black Box. codeHG (String): Código de homologación de gestores que sirve para verificar la validez del número de expediente. dgtResponse (String):
			 * Resultado íntegro del trámite CTIT que la DGT devuelve. errorCodes (List): Lista de parejas de código de error y valor para la petición tramitada. plateNumber (String): Matrícula asignada al vehículo en caso de que el proceso de transmisión finaliza correctamente.
			 */
			CtitCheckError[] bbErrores = respuesta.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(TEXTO_CHECK_LOG + " NO HAY LISTA DE ERRORES");
			} else {
				log.error(TEXTO_CHECK_LOG + " HAY LISTA DE ERRORES");
				if (bbErrores != null) {
					log.error("Listando errores CheckCtit");
					for (int i = 0; i < bbErrores.length; i++) {
						log.error(ConstantesProcesos.PROCESO_CHECKCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO + " " + bbErrores[i].getKey());
						log.error(ConstantesProcesos.PROCESO_CHECKCTIT + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + bbErrores[i].getMessage());
					}
					log.error(TEXTO_CHECK_LOG + " Errores: " + getMensajeError(bbErrores));
				}
			}
		} catch (Exception e) {
			log.error(TEXTO_CHECK_LOG + " EXCEPCION: " + e);
			throw e;
		}
		return respuesta;
	}

	public ResultadoCtitBean generarXmlSegundoEnvioFullCtit(TramiteTrafTranDto tramite, String xmlPrimerEnvio) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			if (xmlPrimerEnvio != null && !xmlPrimerEnvio.isEmpty()) {
				SolicitudRegistroEntrada solicitudRegistroEntrada = getSolicitudRegistroEntradaToXml(xmlPrimerEnvio, tramite.getNumExpediente());
				if (solicitudRegistroEntrada != null) {
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getTasas().getTasaTramiteOrTasaInforme().add(null);
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getAcreditacionDerecho().setConsentimiento(tramite.getConsentimiento() == null ? TipoConsentimiento.NO : tramite
							.getConsentimiento().equals("true") ? TipoConsentimiento.SI : TipoConsentimiento.N_A);
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setFirmaGestor(new byte[0]);
					solicitudRegistroEntrada.setFirma(new byte[0]);

					resultado = anhadirFirmasTransTelematicaHSM(solicitudRegistroEntrada, tramite.getContrato().getColegiadoDto().getAlias());
					if (!resultado.getError()) {
						String nombreFichero = ConstantesGestorFicheros.NOMBRE_CTIT + tramite.getNumExpediente();
						solicitudRegistroEntrada = resultado.getSolicitudRegistroEntrada();
						resultado = validacionXSD(solicitudRegistroEntrada, nombreFichero);
						if (!resultado.getError()) {
							resultado = guardarXmlEnvioTransTelematico(solicitudRegistroEntrada, tramite.getNumExpediente(), nombreFichero);
						}
					}
					if (resultado.getError()) {
						resultado.setExcepcion(new Exception(resultado.getMensajeError()));
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No se puede crear el xml del segundo envio porque el xml del primer envio esta vacio.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se puede crear el xml del segundo envio porque el xml del primer envio esta vacio.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de crear el xml del segundo envio de FullCtit para el expediente: " + tramite.getNumExpediente() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e.getMessage()));
		}

		return resultado;
	}

	private ResultadoCtitBean validacionXSD(SolicitudRegistroEntrada solicitudRegistroEntrada, String nombreFichero) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			XmlCtitSegaFactory xmlCtitSegaFactory = new XmlCtitSegaFactory();
			Marshaller marshaller = (Marshaller) xmlCtitSegaFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudRegistroEntrada, new FileOutputStream(nombreFichero));
			File fichero = new File(nombreFichero);
			String resultVal = xmlCtitSegaFactory.validarXMLCtitSega(fichero);
			if (!"CORRECTO".equals(resultVal)) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista(resultVal);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el xml de envio contra el xsd, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de validar el xml de envio.");
		}
		return resultado;
	}

	private ResultadoCtitBean guardarXmlEnvioTransTelematico(SolicitudRegistroEntrada solRegEntrada, BigDecimal numExpediente, String nombreFichero) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			XmlCtitSegaFactory xmlFactory = new XmlCtitSegaFactory();
			String xmlFirmado = xmlFactory.toXML(solRegEntrada);
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.CTIT);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.CTITENVIO);
			ficheroBean.setNombreDocumento(nombreFichero);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			ficheroBean.setSobreescribir(true);
			gestorDocumentos.crearFicheroXml(ficheroBean, XmlCheckCTITSegaFactory.NAME_CONTEXT, solRegEntrada, xmlFirmado, null);
			resultado.setNombreXml(nombreFichero);
		} catch (OegamExcepcion e) {
			resultado.setMensajeError("No se pudo generar el fichero.");
			resultado.setError(Boolean.TRUE);
			log.error("Error al guardar el fichero, error: ", e);
		} catch (Throwable e) {
			resultado.setMensajeError("No se pudo generar el fichero XML.");
			resultado.setError(Boolean.TRUE);
			log.error("Error al guardar el XML de CTIT en disco, error: ", e);
		}
		return resultado;
	}

	private SolicitudRegistroEntrada getSolicitudRegistroEntradaToXml(String xmlPrimerEnvio, BigDecimal numExpediente) throws JAXBException, OegamExcepcion {
		FileResultBean file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CTIT, ConstantesGestorFicheros.CTITENVIO, Utilidades.transformExpedienteFecha(numExpediente),
				xmlPrimerEnvio, ConstantesGestorFicheros.EXTENSION_XML);
		if (file != null && file.getFile() != null) {
			Unmarshaller unmarshaller = new XmlCtitSegaFactory().getContext().createUnmarshaller();
			return (SolicitudRegistroEntrada) unmarshaller.unmarshal(file.getFile());
		}
		return null;
	}

	private ResultadoCtitBean anhadirFirmasTransTelematicaHSM(SolicitudRegistroEntrada solRegEntrada, String alias) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		resultado = realizarFirmaColegiado(solRegEntrada, alias);
		if (!resultado.getError()) {
			resultado = realizarFirmaColegio(solRegEntrada, gestorPropiedades.valorPropertie("trafico.claves.colegio.alias"));
		}
		if (!resultado.getError()) {
			resultado.setSolicitudRegistroEntrada(solRegEntrada);
		}
		return resultado;
	}

	private ResultadoCtitBean firmarSolicitud(SolicitudRegistroEntrada solRegEntrada, String alias) throws UnsupportedEncodingException {
		ResultadoCtitBean resultadoCtitBean = new ResultadoCtitBean(Boolean.FALSE);
		XmlCtitSegaFactory xmlCtitSegaFactory = new XmlCtitSegaFactory();
		String xml = xmlCtitSegaFactory.toXML(solRegEntrada);
		// Quitamos los namespaces porque da error
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");

		// Obtenemos los datos que realmente se tienen que firmar
		int inicio = xml.indexOf("<Datos_Firmados>") + 16;
		int fin = xml.indexOf("</Datos_Firmados>");
		String datosFirmados = xml.substring(inicio, fin);
		log.info("LOG_CTIT DATOS FIRMADOS " + datosFirmados);
		log.info("Datos a firmar:" + datosFirmados);

		// Se codifican los datos a firmar en base 64
		String encodedAFirmar = CADENA_VACIA;

		encodedAFirmar = utiles.doBase64Encode(datosFirmados.getBytes(UTF_8));
		encodedAFirmar = encodedAFirmar.replaceAll("\n", CADENA_VACIA);
		log.info("LOG_CTIT ENCODED A FIRMAR: " + encodedAFirmar);

		// Se construye el XML que contiene los datos a firmar
		String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
		log.info("XML a firmar:" + xmlAFirmar);
		if (xmlAFirmar != null && !xmlAFirmar.isEmpty()) {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			String idFirma = utilesViafirma.firmarMensajeTransmisionServidor(xmlAFirmar, alias);
			log.info("idFirma Trans telematica= " + idFirma);
			if (ERROR.equals(idFirma)) {
				resultadoCtitBean.setMensajeError("Ha ocurrido un error durante el proceso de firma en servidor del tramite");
				resultadoCtitBean.setError(Boolean.TRUE);
			} else {
				resultadoCtitBean.setXmlFimado(utilesViafirma.getDocumentoFirmado(idFirma));
				log.info("xml custodiado recuperado a traves del idFirma=" + idFirma);
				log.debug("xml firmado : " + resultadoCtitBean.getXmlFimado());
			}
		}
		return resultadoCtitBean;
	}

	private ResultadoCtitBean realizarFirmaColegiado(SolicitudRegistroEntrada solRegEntrada, String alias) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			Boolean noEsCertCaducado = comprobarCaducidadCertificado(alias);
			if (noEsCertCaducado) {
				resultado = firmarSolicitud(solRegEntrada, alias);
				if (!resultado.getError()) {
					try {
						solRegEntrada.getDatosFirmados().getDatosEspecificos().setFirmaGestor(resultado.getXmlFimado().getBytes(UTF_8));
					} catch (Exception e) {
						resultado.setError(Boolean.TRUE);
						resultado.addMensajeLista("Error al adjuntar la firma del colegiado a la solicitud.");
						log.error("Error al adjuntar la firma del colegiado a la solicitud, error: ", e);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("No se puede realizar la tramitación telemática porque el certificado del colegiado se encuentra caducado en estos momentos.");
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Ha sucedido un error a la hora de firmar la solicitud de transmision por parte del colegiado, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de firmar la solicitud de transmision.");
		}
		return resultado;
	}

	private ResultadoCtitBean realizarFirmaColegio(SolicitudRegistroEntrada solRegEntrada, String alias) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			Boolean noEsCertCaducado = comprobarCaducidadCertificado(alias);
			if (noEsCertCaducado) {
				resultado = firmarSolicitud(solRegEntrada, alias);
				if (!resultado.getError()) {
					try {
						solRegEntrada.setFirma(resultado.getXmlFimado().getBytes(UTF_8));
					} catch (Exception e) {
						resultado.setError(Boolean.TRUE);
						resultado.addMensajeLista("Error al adjuntar la firma del colegiado a la solicitud.");
						log.error("Error al adjuntar la firma del colegiado a la solicitud, error: ", e);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("No se puede realizar la tramitación telemática porque el certificado del colegio se encuentra caducado en estos momentos.");
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Ha sucedido un error a la hora de firmar la solicitud de transmision por parte del colegiado, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de firmar la solicitud de transmision.");
		}
		return resultado;
	}

	private Boolean comprobarCaducidadCertificado(String aliasColegiado) {
		boolean esOk = false;
		SolicitudPruebaCertificado solicitudPruebaCertificado = obtenerSolicitudPruebaCertificado(aliasColegiado);
		XMLPruebaCertificado xmlPruebaCertificado = new XMLPruebaCertificado();
		String xml = xmlPruebaCertificado.toXMLSolicitudPruebaCert(solicitudPruebaCertificado);
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.firmarPruebaCertificadoCaducidad(xml, aliasColegiado);
		if (idFirma != null) {
			esOk = true;
		}
		return esOk;
	}

	private SolicitudPruebaCertificado obtenerSolicitudPruebaCertificado(String alias) {
		SolicitudPruebaCertificado solicitudPruebaCertificado = null;
		trafico.beans.jaxb.pruebaCertificado.ObjectFactory objctFactory = new trafico.beans.jaxb.pruebaCertificado.ObjectFactory();
		solicitudPruebaCertificado = (SolicitudPruebaCertificado) objctFactory.createSolicitudPruebaCertificado();
		trafico.beans.jaxb.pruebaCertificado.DatosFirmados datosFirmados = objctFactory.createDatosFirmados();
		datosFirmados.setAlias(alias);
		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
		return solicitudPruebaCertificado;
	}

	private CtitRequest completaRequest(String peticionXML, DatosCTITDto datosCTITDto, String metodo) throws UnsupportedEncodingException {
		CtitRequest req = new CtitRequest();

		/* customDossierNumber Número de expediente en la plataforma origen. Se obtendrá de TraficoTramiteBean.getNumExpediente() */
		req.setCustomDossierNumber(datosCTITDto.getCustomDossierNumber());
		log.info("LOG_" + metodo + "_CTIT: customDossierNumber: " + datosCTITDto.getCustomDossierNumber());

		/* agencyFiscalId CIF de la gestoría que tramita el expediente. */
		req.setAgencyFiscalId(datosCTITDto.getAgencyFiscalId());
		log.info("LOG_" + metodo + "_CTIT: agencyFiscalId: " + datosCTITDto.getAgencyFiscalId());

		/*
		 * externalSystemFiscalId CIF de la plataforma que realiza la conexión a la Caja Negra Es el que se usa para la firma en los PDF. Se obtendrá de TraficoTramiteBean.getNumExpediente()
		 */
		req.setExternalSystemFiscalId(datosCTITDto.getExternalSystemFiscalID());
		log.info("LOG_" + metodo + "_CTIT: externalSystemFiscalId: " + datosCTITDto.getExternalSystemFiscalID());

		log.debug("LOG_" + metodo + "_CTIT PETICION XML =" + peticionXML);

		String xmlObjB64 = utiles.doBase64Encode(peticionXML.getBytes(UTF_8));
		log.debug("LOG_" + metodo + "_CTIT BASE64Encoder.encode(peticionXML.getBytes(" + UTF_8 + ")) =" + xmlObjB64);
		req.setXmlB64(xmlObjB64);

		if (datosCTITDto.getTara() != null) {
			req.setTara(Integer.parseInt(datosCTITDto.getTara()));
			log.info("LOG_" + metodo + "_CTIT: tara: " + req.getTara());
		}

		if (datosCTITDto.getPlazas() != null) {
			req.setSeatPlaces(datosCTITDto.getPlazas().intValue());
			log.info("LOG_" + metodo + "_CTIT: plazas: " + req.getSeatPlaces());
		}

		if (datosCTITDto.getPesoMma() != null) {
			req.setMma(Integer.parseInt(datosCTITDto.getPesoMma()));
			log.info("LOG_" + metodo + "_CTIT: mma " + req.getMma());
		}

		if (datosCTITDto.getIdServicio() != null) {
			req.setCurrentVehiclePurpose(datosCTITDto.getIdServicio());
			log.info("LOG_" + metodo + "_CTIT: servicio " + req.getCurrentVehiclePurpose());
		}

		if (datosCTITDto.getSellerINECode() != null) {
			req.setSellerINECode(datosCTITDto.getSellerINECode());
		}

		if (datosCTITDto.getFirstMatriculationINECode() != null) {
			req.setFirstMatriculationINECode(datosCTITDto.getFirstMatriculationINECode());
		}

		return req;
	}

	private CTITWSBindingStub getStubCTIT() throws OegamExcepcion, MalformedURLException, ServiceException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_TRANSMISION));
		String timeout = gestorPropiedades.valorPropertie(TIMEOUT);

		CTITWS servicio = new CTITServiceLocator().getCTITService(miURL);

		CTITWSBindingStub stub = (CTITWSBindingStub) servicio;

		stub.setTimeout(timeout != null && !timeout.equals("") ? Integer.parseInt(timeout) : TIMEOUT_TRANSMISION);
		return stub;
	}

	private String getMensajeError(CtitFullError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - ");
			String error = listadoErrores[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", "");
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			mensajeError.append(error);
		}
		return mensajeError.toString();
	}

	private String getMensajeError(CtitTradeError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - ");
			String error = listadoErrores[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", "");
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			mensajeError.append(error);
		}
		return mensajeError.toString();
	}

	private String getMensajeError(CtitNotificationError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - ");
			String error = "";
			if (StringUtils.isNotBlank(listadoErrores[i].getMessage())) {
				error = listadoErrores[i].getMessage().replaceAll("'", "");
				error = error.replaceAll("\"", "");
			} else {
				error = listadoErrores[i].getKey();
			}
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			mensajeError.append(error);
		}
		return mensajeError.toString();
	}

	private String getMensajeError(CtitCheckError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - ");
			String error = listadoErrores[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", "");
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			mensajeError.append(error);
		}
		return mensajeError.toString();
	}

	private String getImpedimentosTrade(CtitTradeImpediment[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer();
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - ");
			String error = listadoImpedimentos[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", "");
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			impedimento.append(error);
		}
		return impedimento.toString();
	}

	private String getImpedimentosNotification(CtitNotificationImpediment[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer();
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - ");
			String error = listadoImpedimentos[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", "");
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			impedimento.append(error);
		}
		return impedimento.toString();
	}

	private String getImpedimentosFull(CtitFullImpediment[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer();
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - ");
			String error = listadoImpedimentos[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", "");
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			impedimento.append(error);
		}
		return impedimento.toString();
	}

}