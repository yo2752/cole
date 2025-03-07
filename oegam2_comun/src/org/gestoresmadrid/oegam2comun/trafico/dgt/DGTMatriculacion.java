package org.gestoresmadrid.oegam2comun.trafico.dgt;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCardMatwDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gescogroup.blackbox.MATWServiceLocator;
import com.gescogroup.blackbox.MATWWS;
import com.gescogroup.blackbox.MATWWSBindingStub;
import com.gescogroup.blackbox.MatwError;
import com.gescogroup.blackbox.MatwRequest;
import com.gescogroup.blackbox.MatwResponse;

import colas.constantes.ConstantesProcesos;
import matw.beans.RespuestaMatw;
import matw.beans.RespuestaMatwInfoMatricula;
import matw.beans.RespuestaMatwListadoErroresError;
import matw.beans.RespuestaMatwResultado;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Component
public class DGTMatriculacion implements Serializable {

	private static final long serialVersionUID = 8555104931365269690L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(DGTMatriculacion.class);

	private static final String UTF_8 = "UTF-8";
	private static final String DATEFORMAT = "dd/MM/yyyy HH:mm:ss";

	private static final String WEBSERVICE_MATW = "webservice.matw.url";
	private final static String TIMEOUT = "webservice.matw.timeOut";

	private final static String TEXTO_MATW_LOG = "LOG_MATW:";
	private final static int TIMEOUT_MATW = 120000;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	public RespuestaMatw presentarDGTMatw(String peticionXML, DatosCardMatwDto datosCardMatwDto, BigDecimal numExpediente) throws Exception, OegamExcepcion {
		log.info(TEXTO_MATW_LOG + " ENTRA EN BLACK BOX: " + numExpediente);
		MatwRequest req = completarRequestMatw(peticionXML, datosCardMatwDto, numExpediente);
		RespuestaMatw respuestaMatw = new RespuestaMatw();

		try {
			MATWWSBindingStub stub = getStubMatw();
			MatwResponse respuesta = stub.sendMATW(req);
			log.info(" ");
			log.info("------ " + TEXTO_MATW_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(TEXTO_MATW_LOG + " idTramite: " + numExpediente);
			log.info(TEXTO_MATW_LOG + " dossierNumber Respuesta: " + respuesta.getDossierNumber());

			/*
			 * VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX: dossierNumber (String): Número que identifica el trámite dentro de la aplicación Black Box. codeHG (String): Código de homologación de gestores que sirve para verificar la validez del número de expediente. dgtResponse (String):
			 * Resultado íntegro del trámite MATW que la DGT devuelve. errorCodes (List): Lista de parejas de código de error y valor para la petición tramitada. plateNumber (String): Matrícula asignada al vehículo en caso de que el proceso de matriculación finaliza correctamente.
			 */
			RespuestaMatwInfoMatricula arg0 = new RespuestaMatwInfoMatricula();
			RespuestaMatwListadoErroresError[] arg3 = null;
			RespuestaMatwResultado arg5 = null;

			String matricula = respuesta.getPlateNumber();
			log.info(TEXTO_MATW_LOG + " matricula: " + matricula);

			arg0.setMatricula(matricula);
			arg0.setPC_Temporal_PDF(respuesta.getLicense());
			if (respuesta.getItvCard() != null) {
				arg0.setFicha_tecnica_PDF(respuesta.getItvCard());
			}

			String distintivo = respuesta.getDistintivo();
			String distintivoTipo = respuesta.getDistintivoTipo();

			MatwError[] bbErrores = respuesta.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				respuestaMatw.setListadoErrores(null);
				arg5 = RespuestaMatwResultado.OK;
				log.info(TEXTO_MATW_LOG + " RESPUESTA SIN ERRORES");
			} else {
				log.error(TEXTO_MATW_LOG + " RESPUESTA CON ERRORES");
				arg3 = new RespuestaMatwListadoErroresError[bbErrores.length];
				for (int i = 0; i < bbErrores.length; i++) {
					arg3[i] = new RespuestaMatwListadoErroresError();
					arg3[i].setCodigo(bbErrores[i].getKey());
					arg3[i].setDescripcion(bbErrores[i].getMessage());
					log.error(TEXTO_MATW_LOG + " " + ConstantesProcesos.PROCESO_MATW + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO + " " + arg3[i].getCodigo());
					log.error(TEXTO_MATW_LOG + " " + ConstantesProcesos.PROCESO_MATW + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " "
							+ arg3[i].getDescripcion());
				}

				arg5 = RespuestaMatwResultado.ERROR;
				log.error(TEXTO_MATW_LOG + " RESPUESTA CON ERRORES: " + arg5);
			}

			SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
			arg0.setFechaMatriculacion(sdf.format(new Date()));
			respuestaMatw.setInfoMatricula(arg0);
			respuestaMatw.getInfoMatricula().setMatricula(matricula);
			respuestaMatw.setNumeroExpedienteColegio(respuesta.getDossierNumber());
			respuestaMatw.setListadoErrores(arg3);
			respuestaMatw.setResultado(arg5);
			respuestaMatw.setDistintivo(distintivo);
			respuestaMatw.setDistintivoTipo(distintivoTipo);
		} catch (Exception e) {
			log.error(TEXTO_MATW_LOG + " EXCEPCION: " + e);
			throw e;
		}
		log.info(TEXTO_MATW_LOG + " SALE DE MATW");

		return respuestaMatw;
	}

	private MatwRequest completarRequestMatw(String peticionXML, DatosCardMatwDto datosCardMatwDto, BigDecimal numExpediente) throws UnsupportedEncodingException {
		MatwRequest req = new MatwRequest();
		// req.setForm06ExcemptionValue(form06ExcemptionValue);

		// agencyFiscalId CIF de la gestoría que tramita el expediente.
		req.setAgentFiscalId(datosCardMatwDto.getAgentFiscalId());
		log.info(TEXTO_MATW_LOG + " agencyFiscalId: " + datosCardMatwDto.getAgentFiscalId());

		// externalSystemFiscalId CIF de la plataforma que realiza la conexión a la Caja Negra Es el que se usa para la firma en los PDF. Se obtendrá de TraficoTramiteBean.getNumExpediente()
		req.setExternalSystemFiscalId(datosCardMatwDto.getExternalSystemFiscalId());
		log.info(TEXTO_MATW_LOG + " externalSystemFiscalId: " + req.getExternalSystemFiscalId());

		// serialCardITV
		req.setSerialCardITV(datosCardMatwDto.getSerialCardITV());
		log.info(TEXTO_MATW_LOG + " serialCardITV: " + req.getSerialCardITV());

		// hasForm05 Indica si el expediente tiene un 05 asociado. Matriculación -> Pestaña: 576, 05 y 06, Campo: Reducción, no sujeción o exención solicitada (05)

		Boolean hasForm05 = true;
		String form05Key = datosCardMatwDto.getForm05Key();
		if (null == form05Key || form05Key.trim().length() == 0) {
			form05Key = null;
			hasForm05 = false;
		}

		// form05Key Indica la Clave 05 que se informa en caso de que el expediente tenga un 05 asociado (es decir, si hasForm05 = true).
		log.info(TEXTO_MATW_LOG + " hasForm05: " + hasForm05);
		req.setHasForm05(hasForm05);

		log.info(TEXTO_MATW_LOG + " form05Key: " + form05Key);
		req.setForm05Key(form05Key);

		// hasForm06 Indica si el expediente tiene un 06 asociado. Matriculación -> Pestaña: 576, 05 y 06, Campo: No sujeción o exención solicitada (06)
		Boolean hasForm06 = true;
		String form06ExcemptionValue = datosCardMatwDto.getForm06ExcemptionValue();

		if (null == form06ExcemptionValue || form06ExcemptionValue.trim().length() == 0) {
			form06ExcemptionValue = null;
			hasForm06 = false;
		}

		// form06ExcemptionValue Valor de exención 06 que se informa en caso de que el expediente tenga un 06 asociado. Matriculación -> Pestaña: 576, 05 y 06, Campo: No sujeción o exención solicitada (06) form06ExcemptionType Clave de exención 06 que se informa en caso de que el expediente tenga
		// un 06 asociado.
		String form06ExcemptionType = null;

		if (hasForm06) {
			form06ExcemptionType = datosCardMatwDto.getForm06ExcemptionType();

			if (null == form06ExcemptionValue || form06ExcemptionValue.trim().length() == 0 || form06ExcemptionValue.equalsIgnoreCase("NO HAY")) {
				form06ExcemptionType = "NOSUBJECT";
			} else {
				form06ExcemptionType = "EXCEMPTION";
			}
		}
		req.setHasForm06(hasForm06);
		req.setForm06ExcemptionValue(form06ExcemptionValue);
		req.setForm06ExcemptionType(form06ExcemptionType);

		req.setHasForm576(datosCardMatwDto.isHasForm576());

		if (datosCardMatwDto.getItvCardNew() != null) {
			if (new BigDecimal(1).compareTo(datosCardMatwDto.getItvCardNew()) == 0) {
				req.setItvCardNew(true);
			} else {
				req.setItvCardNew(false);
			}
		}

		// vehicleSerialNumber - Bastidor del vehículo
		String vehicleSerialNumber = datosCardMatwDto.getSerialNumber();
		if (null != vehicleSerialNumber) {
			req.setSerialNumber(vehicleSerialNumber);
		}

		/**
		 * xmlB64 XML en Base 64 con los datos necesarios para matricular el vehículo a la DGT, también denominada Solicitud de registro de entrada.
		 */
		log.debug(TEXTO_MATW_LOG + " PETICION XML =" + peticionXML);
		String xmlObjB64 = utiles.doBase64Encode(peticionXML.getBytes(UTF_8));
		log.debug(TEXTO_MATW_LOG + " BASE64Encoder.encode(peticionXML.getBytes(" + UTF_8 + ")) =" + xmlObjB64);

		req.setXmlB64(xmlObjB64);
		return req;
	}

	private MATWWSBindingStub getStubMatw() throws OegamExcepcion, MalformedURLException, ServiceException {
		String timeout = gestorPropiedades.valorPropertie(TIMEOUT);
		URL miURL = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_MATW));
		MATWWS servicio = new MATWServiceLocator().getMATWService(miURL);
		MATWWSBindingStub stub = (MATWWSBindingStub) servicio;

		stub.setTimeout(timeout != null && !timeout.equals("") ? Integer.parseInt(timeout) : TIMEOUT_MATW);
		new UtilesWSMatw().cargarAlmacenesTrafico();
		return stub;
	}

}