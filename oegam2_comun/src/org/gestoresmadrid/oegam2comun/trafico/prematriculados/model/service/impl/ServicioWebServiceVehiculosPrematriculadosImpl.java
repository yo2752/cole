package org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.impl;

import java.util.List;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.comun.BeanDatosFiscales;
import org.gestoresmadrid.oegam2comun.comun.DatosFiscales;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContratoColegiado;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.trafico.eitv.dgt.DGTSolicitudEitv;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioVehiculosPrematriculados;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioWebServiceVehiculosPrematriculados;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.VehiculoPrematriculadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gescogroup.blackbox.EitvQueryError;
import com.gescogroup.blackbox.EitvQueryWSResponse;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceVehiculosPrematriculadosImpl implements ServicioWebServiceVehiculosPrematriculados {

	private static final long serialVersionUID = -129993409085949232L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceVehiculosPrematriculadosImpl.class);

	@Autowired
	ServicioVehiculosPrematriculados servicioVehiculosPrematriculados;

	@Autowired
	ServicioMensajesProcesos servicioMensajesProcesos;

	@Autowired
	DatosFiscales datosFiscales;

	@Autowired
	DGTSolicitudEitv dgtSolicitudEitv;

	@Autowired
	ServicioContratoColegiado servicioContratoColegiado;

	@Override
	public ResultBean tramitarPeticion(ColaDto solicitud) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			String numColegiado = servicioContratoColegiado.getNumColegiadoPorContrato(solicitud.getIdContrato().longValue());
			List<VehiculoPrematriculadoDto> vehiculos = servicioVehiculosPrematriculados.buscar(new Long[] { solicitud.getIdTramite().longValue() }, numColegiado);
			if (vehiculos == null || vehiculos.size() != 1 || vehiculos.get(0) == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede recuperar la información del trámite necesaria para invocar el servicio");
			} else {
				String xmlSolicitud = servicioVehiculosPrematriculados.recogerXmlEitv(solicitud.getIdTramite().longValue());
				if (xmlSolicitud == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede recuperar la información del trámite necesaria para invocar el servicio");
				} else {
					resultado = llamadaWS(solicitud, xmlSolicitud, vehiculos.get(0));
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la tramitacion de EITV para el tramite: " + solicitud.getIdTramite() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private ResultBean llamadaWS(ColaDto solicitud, String peticionXML, VehiculoPrematriculadoDto vehiculo) throws Exception, OegamExcepcion {
		ResultBean result = new ResultBean(Boolean.FALSE);
		try {
			BeanDatosFiscales beanDatosFiscales = datosFiscales.obtenerDatosFiscalesDelColegiado(vehiculo.getNumColegiado());
			EitvQueryWSResponse respuestaWS = dgtSolicitudEitv.presentarDGTEitv(peticionXML, beanDatosFiscales, solicitud.getIdTramite().toString(), vehiculo.getNive(), vehiculo.getBastidor());

			boolean error = compruebaError(respuestaWS);
			if (error) {
				result.setError(Boolean.TRUE);
				if (respuestaWS != null && respuestaWS.getErrorCodes() != null && respuestaWS.getErrorCodes().length > 0) {
					String respuesta = getMensajeError(respuestaWS.getErrorCodes());
					result.addAttachment(RECUPERABLE, compruebaErroresRecuperables(respuestaWS.getErrorCodes()));
					result.setMensaje(respuesta);
				}
				servicioVehiculosPrematriculados.guardarRespuestaDatosTecnicos(false, respuestaWS.getErrorCodes(0).getMessage(), vehiculo.getId());
			} else {
				servicioVehiculosPrematriculados.guardarRespuestaDatosTecnicos(true, "OK", vehiculo.getId());
				servicioVehiculosPrematriculados.guardarXmlRespuestaDatosTecnicos(respuestaWS.getXmldata(), vehiculo.getId(), vehiculo.getFechaAlta());
			}
		} catch (Exception ex) {
			throw ex;
		}
		return result;
	}

	private String getMensajeError(EitvQueryError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			if (listadoErrores[i].getMessage() != null) {
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
		}
		return mensajeError.toString();
	}

	private boolean compruebaError(EitvQueryWSResponse respuestaWS) {
		EitvQueryError[] bbErrores = respuestaWS.getErrorCodes();
		if (null == bbErrores || bbErrores.length == 0) {
			log.info("LOG_MATECARD: RESPUESTA SIN ERRORES");
			return false;
		} else {
			log.error("respuesta con errores");
			return true;
		}
	}

	private Boolean compruebaErroresRecuperables(EitvQueryError[] errorCodes) {
		Boolean recuperable = true;
		for (EitvQueryError error : errorCodes) {
			recuperable = servicioMensajesProcesos.tratarMensaje(error.getKey(), error.getMessage());
			if (!recuperable) {
				break;
			}
		}
		return recuperable;
	}
}
