package org.gestoresmadrid.oegam2comun.trafico.eitv.dgt;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.NamingException;
import javax.xml.rpc.ServiceException;

import org.gestoresmadrid.oegam2comun.comun.BeanDatosFiscales;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gescogroup.blackbox.EITVQueryServiceLocator;
import com.gescogroup.blackbox.EITVQueryWS;
import com.gescogroup.blackbox.EITVQueryWSBindingStub;
import com.gescogroup.blackbox.EitvQueryError;
import com.gescogroup.blackbox.EitvQueryWSRequest;
import com.gescogroup.blackbox.EitvQueryWSResponse;

import colas.constantes.ConstantesProcesos;
import es.dgt.www.vehiculos.esquema.RespuestaMatriculacion;
import trafico.utiles.UtilesWSMate;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Component
public class DGTSolicitudEitv implements Serializable {

	private static final long serialVersionUID = -8884977586380128602L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(DGTSolicitudEitv.class);

	private static final String UTF_8 = "UTF-8";
	private final static String TEXTO_MATEEITV_LOG = "LOG_MATEEITV:";
	private final static String TIMEOUT_PROP_EITV = "webservice.eitv.timeOut";
	private static final String WEBSERVICE_MATE_EITV = "webservice.eitv.url";
	private final static int TIMEOUT_MATEGE = 120000;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;

	/**
	 * Realiza la presentación del registro de matriculación que recibe como parámetros en el servicio MATEGE de la DGT y devuelve la respuesta que recibe como resultado.
	 * @param peticionXML String con formato XML q contiene la información del registro de matriculacion a presentar
	 * @return Devuelve el resultado de la matriculación que es un objeto {@link RespuestaMatriculacion}
	 * @throws Exception
	 * @throws OegamExcepcion
	 */
	public EitvQueryWSResponse presentarDGTEitv(String peticionXML, BeanDatosFiscales datosFiscales, String id, String nive, String bastidor) throws Exception, OegamExcepcion {
		log.info(TEXTO_MATEEITV_LOG + " ENTRA EN BLACK BOX");

		EitvQueryWSRequest req = completaRequest(peticionXML, datosFiscales, id, nive, bastidor);
		EitvQueryWSResponse respuestaEitv = new EitvQueryWSResponse();

		try {
			new UtilesWSMate().cargarAlmacenesTrafico();
			respuestaEitv = getStubEitv().getDataEITV(req);
			log.info(" ");
			log.info("------ " + TEXTO_MATEEITV_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(TEXTO_MATEEITV_LOG + " customDossierNumber Request: " + req.getCustomDossierNumber());
			log.info(TEXTO_MATEEITV_LOG + " dossierNumber Respuesta: " + respuestaEitv.getDossierNumber());

			/*
			 * VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX: dossierNumber (String): Número que identifica el trámite* dentro de la aplicación Black Box. codeHG (String): Código de homologación de gestores que sirve para verificar la validez del número de expediente. dgtResponse (String):
			 * Resultado íntegro del trámite MATEGE que la DGT devuelve. errorCodes (List): Lista de parejas de código de error y valor para la petición tramitada. plateNumber (String): Matrícula asignada al vehículo en caso de que el proceso de matriculación finaliza correctamente.
			 */
			log.info(respuestaEitv.getCodeHG());
			log.info(respuestaEitv.getDossierNumber());
			log.info(respuestaEitv.getErrorCodes());
			// log.info(respuestaEitv.getXmldata());

			EitvQueryError[] bbErrores = respuestaEitv.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(TEXTO_MATEEITV_LOG + " RESPUESTA SIN ERRORES");
			} else {
				log.error(TEXTO_MATEEITV_LOG + " RESPUESTA CON ERRORES");
				String respuestaError = "";
				if (bbErrores != null) {
					listarErroresMateITV(bbErrores);
					respuestaError += getMensajeError(bbErrores);
					log.error(TEXTO_MATEEITV_LOG + " Errores: " + respuestaError);
				}
			}
		} catch (Exception e) {
			log.error(TEXTO_MATEEITV_LOG + " EXCEPCION: " + e);
			throw e;
		}
		log.info(TEXTO_MATEEITV_LOG + " SALE DE EITV");

		return respuestaEitv;
	}

	private EitvQueryWSRequest completaRequest(String peticionXML, BeanDatosFiscales datosFiscales, String id, String nive, String bastidor) throws UnsupportedEncodingException {
		EitvQueryWSRequest req = new EitvQueryWSRequest();
		req.setNive(nive);
		req.setVin(bastidor);
		req.setAgentFiscalId(datosFiscales.getAgentfiscalid());

		/* agencyFiscalId CIF de la gestoría que tramita el expediente. */
		req.setAgencyFiscalId(datosFiscales.getAgencyfiscalld());
		log.info(TEXTO_MATEEITV_LOG + " agencyFiscalId: " + datosFiscales.getAgencyfiscalld());

		/*
		 * customDossierNumber Número de expediente en la plataforma origen. Se obtendrá de TraficoTramiteBean.getNumExpediente()
		 */
		req.setCustomDossierNumber(id);
		log.info(TEXTO_MATEEITV_LOG + " customDossierNumber: " + req.getCustomDossierNumber());

		/*
		 * externalSystemFiscalId CIF de la plataforma que realiza la conexión a la Caja Negra. Es el que se usa para la firma en los PDF. Se obtendrá de TraficoTramiteBean.getNumExpediente()
		 */
		req.setExternalSystemFiscalId(datosFiscales.getExternalSystemFiscall());
		log.info(TEXTO_MATEEITV_LOG + " externalSystemFiscalId: " + req.getExternalSystemFiscalId());

		/* xmlB64 XML en Base 64 con los datos necesarios para matricular el vehículo a la DGT, también denominada Solicitud de registro de entrada. */
		log.debug(TEXTO_MATEEITV_LOG + " PETICION XML =" + peticionXML);

		// req.setXmlB64(UtilesRegistradores.doBase64Encode(peticionXML.getBytes(UTF_8)));
		req.setXmlB64(utiles.doBase64Encode(peticionXML.getBytes(UTF_8)));
		return req;
	}

	public void listarErroresMateITV(EitvQueryError[] listadoErrores) {
		log.error("Listando errores MateITV");
		for (int i = 0; i < listadoErrores.length; i++) {
			log.error(ConstantesProcesos.PROCESO_CONSULTAEITV + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO + " " + listadoErrores[i].getKey());
			// Mantis 16905. David Sierra: Control Nullpointer
			if (listadoErrores[i].getMessage() == null) {
				log.error(ConstantesProcesos.PROCESO_CONSULTAEITV + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + "No devuelve descripcion del error");
			} else {
				log.error(ConstantesProcesos.PROCESO_CONSULTAEITV + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + listadoErrores[i].getMessage());
			}
		}
	}

	public String getMensajeError(EitvQueryError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - ");
			// Mantis 18056. David Sierra: Asignacion de mensajes a errores respuesta
			if ("EITV07002".equals(listadoErrores[i].getKey()) && listadoErrores[i].getMessage() == null) {
				listadoErrores[i].setMessage("No se ha encontrado la tarjeta EITV");
			} else if ("EITV08001".equals(listadoErrores[i].getKey()) && listadoErrores[i].getMessage() == null) {
				listadoErrores[i].setMessage("No se ha podido conectar con el sistema de persistencia");
			} else if ("EITV08005".equals(listadoErrores[i].getKey()) && listadoErrores[i].getMessage() == null) {
				listadoErrores[i].setMessage("No se ha podido realizar la consulta de la tarjeta EITV en el sistema de persistencia");
			} // Mantis 16905
			else if (listadoErrores[i].getMessage() == null) {
				listadoErrores[i].setMessage("Tipo de error: " + listadoErrores[i].getKey());
			} else {
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
			} // Fin Mantis

		}
		return mensajeError.toString();
	}

	/**
	 * El stub de conexion con el webservice de Eitv
	 * @throws OegamExcepcion
	 * @throws NamingException
	 */

	public EITVQueryWSBindingStub getStubEitv() throws MalformedURLException, ServiceException {
		EITVQueryWSBindingStub stubEitv = null;
		String rutaMate = gestorPropiedades.valorPropertie(WEBSERVICE_MATE_EITV);
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT_PROP_EITV);
		URL miURL = new URL(rutaMate);

		EITVQueryWS servicio = new EITVQueryServiceLocator().getEITVQueryService(miURL);

		if (null != miURL) {
			log.info("LOG_MATEEITV: miURL: " + miURL.toString());
		}

		stubEitv = (EITVQueryWSBindingStub) servicio;

		log.info("LOG_MATEEITV: generado stubEitv.");

		stubEitv.setTimeout(Integer.parseInt(timeOut));

		if (timeOut != null && !"".equals(timeOut))
			stubEitv.setTimeout(Integer.parseInt(timeOut));
		else
			stubEitv.setTimeout(TIMEOUT_MATEGE);

		log.info("LOG_MATEEITV: timeout: " + TIMEOUT_MATEGE);

		return stubEitv;
	}
}