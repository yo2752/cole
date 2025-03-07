package org.gestoresmadrid.oegamBajas.service.impl;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.trafico.webService.model.service.ServicioWebServiceConsultaBtv;
import org.gestoresmadrid.oegamBajas.service.ServicioPersistenciaBaja;
import org.gestoresmadrid.oegamBajas.service.ServicioWebCheckBtv;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.oegamComun.mensajesProcesos.service.ServicioComunMensajeProcesos;
import org.gestoresmadrid.oegamComun.mensajesProcesos.view.dto.MensajesProcesosDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gescogroup.blackbox.BTVConsultaLocator;
import com.gescogroup.blackbox.BTVWS;
import com.gescogroup.blackbox.BTVWSBindingStub;
import com.gescogroup.blackbox.BtvCodigoDescripcion;
import com.gescogroup.blackbox.BtvsoapConsultaRespuesta;
import com.gescogroup.blackbox.BtvsoapPeticion;
import com.gescogroup.blackbox.ws.SoapConsultaBtvWSHandler;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebCheckBtvImpl implements ServicioWebCheckBtv {

	private static final long serialVersionUID = 2739727764698987427L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebCheckBtvImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioPersistenciaBaja servicioPersistenciaBaja;

	@Autowired
	Utiles utiles;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	ServicioComunMensajeProcesos servicioComunMensajeProcesos;

	@Override
	public ResultadoBajasBean procesarCheckBtv(ColaDto solicitud) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			String xml = recogerXml(solicitud);
			if (xml != null && !xml.isEmpty()) {
				TramiteTrafBajaVO tramiteTrafBajaVO = servicioPersistenciaBaja.getTramiteBaja(solicitud.getIdTramite(),
						Boolean.TRUE);
				llamadaWS(tramiteTrafBajaVO, xml, resultado);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(
						"Ha sucedido un error a la hora de obtener el xml con los datos del vehiculo para enviarlo a la consulta de BTV.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de procesar la solicitud de CheckBTV, error: ", e,
					solicitud.getIdTramite().toString());
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private void llamadaWS(TramiteTrafBajaVO tramiteTrafBajaVO, String xml, ResultadoBajasBean resultado) {
		try {
			BtvsoapPeticion soapPeticionBtv = generarPeticionSoapConsultatv(xml, tramiteTrafBajaVO);
			if (soapPeticionBtv != null) {
				BtvsoapConsultaRespuesta btvConsultaRespuesta = getStubBtv(tramiteTrafBajaVO.getNumExpediente())
						.consultarBTV(soapPeticionBtv);
				log.info("-------" + ServicioWebServiceConsultaBtv.TEXTO_WS_CONSULTA_BTV
						+ " Recibida Respuesta WS ------");
				if (btvConsultaRespuesta != null) {
					gestionarResultadoWS(btvConsultaRespuesta, resultado);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora realizar la peticion de consulta de BTV.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora realizar la peticion de consulta de BTV.");
			}
		} catch (Exception e) {
			log.error("Error en la llamada al WS de CheckBTV: ", e, tramiteTrafBajaVO.getNumExpediente().toString());
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		}
	}

	private void gestionarResultadoWS(BtvsoapConsultaRespuesta btvConsultaRespuesta, ResultadoBajasBean resultado) {
		if (btvConsultaRespuesta.getResultado() != null) {
			log.info("resultado checkBTV para " + btvConsultaRespuesta.getNum_expediente() + ": "
					+ btvConsultaRespuesta.getResultado());
		}
		if ("".equals(btvConsultaRespuesta.getResultado())
				|| ConstantesProcesos.ERROR.toUpperCase().equals(btvConsultaRespuesta.getResultado())) {
			comprobarErroresRecuperables(btvConsultaRespuesta.getErrores(), resultado);
		} else if (ConstantesProcesos.TRAMITABLE.toUpperCase()
				.equalsIgnoreCase(btvConsultaRespuesta.getResultado())) {
			resultado.setEstadoNuevo(new Long(EstadoTramiteTrafico.Tramitable.getValorEnum()));
			resultado.setResultadoConsuta(ConstantesProcesos.TRAMITABLE);
			resultado.setRespuesta(ConstantesProcesos.TRAMITABLE);
		} else if (ConstantesProcesos.INFORME_REQUERIDO.toUpperCase()
				.equalsIgnoreCase(btvConsultaRespuesta.getResultado())) {
			resultado.setEstadoNuevo(new Long(EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum()));
			resultado.setResultadoConsuta(ConstantesProcesos.INFORME_REQUERIDO);
			resultado.setRespuesta(ConstantesProcesos.INFORME_REQUERIDO + gestionarMensajeError(btvConsultaRespuesta));
		} else if (ConstantesProcesos.NO_TRAMITABLE_BTV.toUpperCase()
				.equalsIgnoreCase(btvConsultaRespuesta.getResultado())) {
			resultado.setEstadoNuevo(new Long(EstadoTramiteTrafico.No_Tramitable.getValorEnum()));
			resultado.setResultadoConsuta(ConstantesProcesos.NO_TRAMITABLE_BTV);
			resultado.setRespuesta(ConstantesProcesos.NO_TRAMITABLE_BTV + gestionarMensajeError(btvConsultaRespuesta));
		} else if (ConstantesProcesos.TRAMITABLE_JEFATURA_TRAFICO.toUpperCase()
				.equalsIgnoreCase(btvConsultaRespuesta.getResultado())) {
			resultado.setEstadoNuevo(new Long(EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum()));
			resultado.setResultadoConsuta(ConstantesProcesos.TRAMITABLE_JEFATURA_TRAFICO);
			resultado.setRespuesta(
					ConstantesProcesos.TRAMITABLE_JEFATURA_TRAFICO + gestionarMensajeError(btvConsultaRespuesta));
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA));
			resultado.setRespuesta(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA);
		}
	}

	private String gestionarMensajeError(BtvsoapConsultaRespuesta btvConsultaRespuesta) {
		String mensaje = null;
		if (btvConsultaRespuesta.getErrores() != null && btvConsultaRespuesta.getErrores().length > 0) {
			for (BtvCodigoDescripcion btvCodigoDescripcion : btvConsultaRespuesta.getErrores()) {
				if (mensaje == null) {
					mensaje = btvCodigoDescripcion.getDescripcion();
				} else {
					mensaje += ", " + btvCodigoDescripcion.getDescripcion();
				}
				if (mensaje.length() > 300) {
					mensaje = mensaje.substring(0, 265) + ".....";
				}
			}
		}
		if (mensaje == null) {
			mensaje = "";
		}
		return mensaje;
	}

	private void comprobarErroresRecuperables(BtvCodigoDescripcion[] btvCodigoDescripcion,
			ResultadoBajasBean resultado) {
		String mensaje = null;
		List<String> listaCodErrores = new ArrayList<>();
		try {
			if (btvCodigoDescripcion != null) {
				for (BtvCodigoDescripcion codDescripcion : btvCodigoDescripcion) {
					if (codDescripcion.getDescripcion() != null && !codDescripcion.getDescripcion().isEmpty()) {
						if (mensaje == null || mensaje.isEmpty()) {
							mensaje = codDescripcion.getDescripcion();
						} else {
							mensaje += " - " + codDescripcion.getDescripcion();
						}
					} else {
						MensajesProcesosDto mensajesProcesosDto = servicioComunMensajeProcesos
								.getMensaje(codDescripcion.getCodigo());
						String error = null;

						error = mensajesProcesosDto != null ? mensajesProcesosDto.getDescripcion() : codDescripcion.getCodigo();

						if (mensaje == null || mensaje.isEmpty()) {
							mensaje = error;
						} else {
							mensaje += " - " + error;
						}
					}
					listaCodErrores.add(codDescripcion.getCodigo());
				}
			}
			if (mensaje != null && !mensaje.isEmpty()) {
				resultado.setEstadoNuevo(new Long(EstadoTramiteTrafico.No_Tramitable.getValorEnum()));
				resultado.setResultadoConsuta(ConstantesProcesos.NO_TRAMITABLE_BTV);
				resultado.setRespuesta(ConstantesProcesos.NO_TRAMITABLE_BTV + mensaje);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setExcepcion(
						new Exception(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA));
				resultado.setRespuesta(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar los errores de la respuesta del WS");
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
			resultado.setRespuesta(e.toString());
		}
	}

	private BTVWSBindingStub getStubBtv(BigDecimal numExpediente) throws MalformedURLException, ServiceException {
		BTVWSBindingStub stubBtv = null;
		String timeOut = gestorPropiedades.valorPropertie(ServicioWebServiceConsultaBtv.TIMEOUT_PROP_BTV);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(ServicioWebServiceConsultaBtv.WEBSERVICE_CONSULTA_BTV));
		BTVWS btvService = null;
		if (miUrl != null) {
			BTVConsultaLocator btvConsultaLocator = new BTVConsultaLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),
					btvConsultaLocator.getBTVConsultaWSDDServiceName());

			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = btvConsultaLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapConsultaBtvWSHandler.class);

			Map<String, Object> filesConfig = new HashMap<>();
			filesConfig.put(SoapConsultaBtvWSHandler.PROPERTY_KEY_ID, numExpediente);
			logHandlerInfo.setHandlerConfig(filesConfig);

			list.add(logHandlerInfo);

			btvService = btvConsultaLocator.getBTVConsulta(miUrl);
			stubBtv = (BTVWSBindingStub) btvService;
			stubBtv.setTimeout(Integer.parseInt(timeOut));
		}
		return stubBtv;
	}

	private BtvsoapPeticion generarPeticionSoapConsultatv(String xml, TramiteTrafBajaVO tramiteTrafBajaVO) {
		BtvsoapPeticion soapPeticionBtv = null;
		if (tramiteTrafBajaVO.getContrato().getColegiado() != null
				&& tramiteTrafBajaVO.getContrato().getColegiado().getUsuario() != null
				&& tramiteTrafBajaVO.getContrato().getColegio() != null) {
			soapPeticionBtv = new BtvsoapPeticion();
			soapPeticionBtv.setDoi_gestor(tramiteTrafBajaVO.getContrato().getColegiado().getUsuario().getNif());
			soapPeticionBtv.setDoi_gestoria(tramiteTrafBajaVO.getContrato().getColegiado().getUsuario().getNif());
			soapPeticionBtv.setDoi_plataforma(tramiteTrafBajaVO.getContrato().getColegio().getCif());
			soapPeticionBtv.setNum_expediente_propio(tramiteTrafBajaVO.getNumExpediente().toString());
			try {
				soapPeticionBtv.setXmlB64(utiles.doBase64Encode(xml.getBytes(StandardCharsets.UTF_8)));
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de pasar a base64 el XML de envio, error: ", e,
						tramiteTrafBajaVO.getNumExpediente().toString());
				return null;
			}
		}
		return soapPeticionBtv;
	}

	private String recogerXml(ColaDto solicitud) {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(solicitud.getIdTramite());
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CONSULTA_BTV,
					ConstantesGestorFicheros.XML_ENVIO, fecha, solicitud.getXmlEnviar(),
					ConstantesGestorFicheros.EXTENSION_XML);
			if (ficheroAenviar != null && ficheroAenviar.getFile() != null) {
				FileInputStream fis = new FileInputStream(ficheroAenviar.getFile());
				byte[] by = IOUtils.toByteArray(fis);
				xml = new String(by, StandardCharsets.UTF_8);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el documento, error: ", e, solicitud.getIdTramite().toString());
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e, solicitud.getIdTramite().toString());
		}
		return xml;
	}

	@Override
	public void cambiarEstadoCheckBtv(ColaDto cola, BigDecimal estadoNuevo, String respuesta, BigDecimal idUsuario) {
		try {
			servicioPersistenciaBaja.finalizarSolicitudCheckBtv(cola.getIdTramite(), estadoNuevo, respuesta, idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado al expediente de baja: "
					+ cola.getIdTramite() + ", error: ", e);
		}
	}
}