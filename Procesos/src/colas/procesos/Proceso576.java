package colas.procesos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import colas.procesos.utiles.UtilesProcesos;
//import trafico.modelo.ModeloMatriculacionGenerica;
import escrituras.beans.ResultBean;
import hibernate.entities.general.Colegiado;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import procesos.enumerados.RetornoProceso;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.daos.pq_tramite_trafico.BeanPQCAMBIAR_ESTADO_S_V;
import trafico.beans.matriculacion.modelo576.RespuestaJsonRecibida;
import trafico.modelo.ModeloMatriculacion;
import trafico.modelo.ModeloPresentacionAEAT;
import trafico.utiles.UtilesConversionesTrafico;
import trafico.utiles.constantes.ConstantesAEAT;
import trafico.utiles.enumerados.TipoCreacion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TramiteNoRecuperadoExcepcion;

/**
 * Gestiona las solicitudes de presentación del 576 (trámites de matriculación)
 *
 */
public class Proceso576 extends ProcesoBase {

	private static final ILoggerOegam log = LoggerOegam.getLogger("Proceso576");
	private Integer hiloActivo;

	private ModeloMatriculacion modeloMatriculacion;
	private ModeloPresentacionAEAT modeloPresentacionAEAT;
	private UtilesProcesos utilesProcesos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);

		ColaBean colaBean = new ColaBean();
		boolean erroresDatosRequeridos = false;
		boolean erroresFormato = false;
		boolean erroresPresentacion = false;
		TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean = new TramiteTraficoMatriculacionBean();

		try {
			colaBean = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_576, hiloActivo.toString());
			if (colaBean != null && colaBean.getIdEnvio() != null) {
				log.debug(
						"**************************************************************************************************************************************************");
				log.debug("Recuperada solicitud con id envio: " + colaBean.getIdEnvio());
			}
			log.debug("Recuperando datos del contrato...");
			ContratoUsuarioVO contrato = utilesColegiado.getContratoUsuario(colaBean.getIdUsuario().toString());
			if (contrato == null || contrato.getId().getIdContrato() == null) {
				String mensajeError = "No se han recuperado los datos del contrato debido a un error que no lanza excepcion";
				throw new Exception(mensajeError);
			}
			log.debug("Recuperados los datos del contrato. Id contrato: " + contrato.getId().getIdContrato());
			Colegiado colegiado = utilesColegiado.getColegiado(contrato.getContrato().getColegiado().getNumColegiado());
			log.debug("Recuperando datos del colegiado...");
			if (colegiado == null) {
				String mensajeError = "No se han recuperado los datos del colegiado debido a un error que no lanza excepcion";
				throw new Exception(mensajeError);
			}
			log.debug("Recuperados datos del colegiado. Numero de colegiado: " + colegiado.getNumColegiado());

			try {
				HashMap<String, Object> mapaObtenerDetalle = null;
				log.debug("Recuperando los detalles del tramite...");
				log.debug("Tipo de tramite: MATRICULACIÓN");
				mapaObtenerDetalle = getModeloMatriculacion().obtenerDetalle(colaBean.getIdTramite(),
						contrato.getContrato().getColegiado().getNumColegiado(),
						new BigDecimal(contrato.getId().getIdContrato()));

				ResultBean resultadoObtenerDetalle = (ResultBean) mapaObtenerDetalle.get(ConstantesPQ.RESULTBEAN);
				if (resultadoObtenerDetalle.getError()) {
					String mensajeError = "No se ha recuperado el tramite de la solicitud debido al siguiente error: "
							+ resultadoObtenerDetalle.getMensaje();
					throw new TramiteNoRecuperadoExcepcion(mensajeError);
				}
				traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) mapaObtenerDetalle
						.get(ConstantesPQ.BEANPANTALLA);
			} catch (Exception ex) {
				String mensajeError = "No se ha recuperado el tramite de la solicitud debido a la siguiente excepcion: "
						+ ex;
				throw new TramiteNoRecuperadoExcepcion(mensajeError);
			}
			BigDecimal numExpediente = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente();
			log.debug("Recuperados los detalles del tramite. Numero de expediente: " + numExpediente);

			// ***************************************************************************************************************************************

			String respuesta = "";
			ResultBean resultadoPresentacion = new ResultBean();
			log.debug("Invocacion a getModeloPresentacionAEAT().tramitar576. Expediente: " + numExpediente);
			Map<String, Object> resultsTramitacion = getModeloPresentacionAEAT()
					.tramitar576(traficoTramiteMatriculacionBean, contrato.getContrato().getColegiado().getAlias());
			@SuppressWarnings("unchecked")
			ArrayList<String> datosRequeridos = (ArrayList<String>) resultsTramitacion.get("datosRequeridos");
			if (datosRequeridos != null && !datosRequeridos.isEmpty()) {
				respuesta += numExpediente
						+ " - Faltan los siguientes datos requeridos para la presentacion del 576: - ";
				String faltanDatos = "";
				for (int i = 0; i < datosRequeridos.size(); i++) {
					if ((i == 0) || (i % 4 != 0 && i < datosRequeridos.size())) {
						faltanDatos += datosRequeridos.get(i) + ", ";
					} else if (i % 4 == 0) {
						faltanDatos += datosRequeridos.get(i) + " - ";
					} else if (i == datosRequeridos.size()) {
						faltanDatos += datosRequeridos.get(i);
					}
				}
				respuesta += faltanDatos;
				erroresDatosRequeridos = true;
				log.info("La presentacion del 576 en la AEAT del expediente: " + colaBean.getIdTramite()
						+ " no ha tenido exito debido al siguiente error: " + respuesta);
			}
			@SuppressWarnings("unchecked")
			ArrayList<String> datosErroresFormato = (ArrayList<String>) resultsTramitacion.get("erroresFormato");
			String cadenaErroresFormato = "";
			if (datosErroresFormato != null && !datosErroresFormato.isEmpty()) {
				if (!respuesta.equals("")) {
					respuesta += " - ";
				}
				respuesta += "Errores de formato que bloquean la presentación del 576: - ";
				for (int i = 0; i < datosErroresFormato.size(); i++) {
					if (i != datosErroresFormato.size()) {
						cadenaErroresFormato += datosErroresFormato.get(i) + " - ";
					} else {
						cadenaErroresFormato += datosErroresFormato.get(i);
					}
				}
				respuesta += cadenaErroresFormato;
				erroresFormato = true;
				log.info("La presentacion del 576 en la AEAT del expediente: " + colaBean.getIdTramite()
						+ " no ha tenido exito debido al siguiente error: " + respuesta);
			}
			// Se obtiene el resultado de la presentación del Modelo 576
			resultadoPresentacion = (ResultBean) resultsTramitacion.get("resultadoPresentacion");
			if (resultadoPresentacion != null) {
				// Si tenemos errores en la presentación
				if (resultadoPresentacion.getError()) {
					if (!respuesta.equals("")) {
						respuesta += " - ";
					}
					respuesta += "Errores en el proceso de presentacion en la AEAT - ";
					String cadErroresPresentacion = "";

					@SuppressWarnings("unchecked")
					List<String> errores = (List<String>) resultadoPresentacion
							.getAttachment(ConstantesAEAT.DELIMITADOR_ERRORES);
					if (!CollectionUtils.isEmpty(errores)) {
						for (String error : errores) {
							String mensajeError = error.trim();
							mensajeError = error.replaceAll(" +", " ");
							if (errores.indexOf(error) == errores.size() - 1) {
								cadErroresPresentacion += mensajeError;
							} else {
								cadErroresPresentacion += mensajeError + " - ";
							}
						}
					} else if (!CollectionUtils.isEmpty(resultadoPresentacion.getListaMensajes())) {
						for (String mensajeError : resultadoPresentacion.getListaMensajes()) {
							if (mensajeError != null && !mensajeError.equals("")) {
								String[] arrayErroresPresentacion = mensajeError
										.split(ConstantesAEAT.DELIMITADOR_ERRORES);
								for (String cadError : arrayErroresPresentacion) {
									if (cadError != null && !cadError.equals("")) {
										cadErroresPresentacion += cadError;
									}
								}
							}
						}
					}
					respuesta += cadErroresPresentacion;
					erroresPresentacion = true;
					log.info("La presentacion del 576 en la AEAT del expediente: " + colaBean.getIdTramite()
							+ " no ha tenido exito debido al siguiente error: " + respuesta);
					// Si no hay errores en la presentación
				} else {
					respuesta = "La presentacion del 576 en la AEAT del expediente: " + colaBean.getIdTramite()
							+ " se ha realizado correctamente. ";
					if (null != resultadoPresentacion.getAttachment(ConstantesSession.RESPUESTA576)) {
						// Se extrae de la respuesta el CEM:
						String cem = ((RespuestaJsonRecibida) resultadoPresentacion
								.getAttachment("respuestaJsonRecibida")).getRespuesta().getCorrecta().getCem();
						if (!StringUtils.isEmpty(cem)) {
							if (!cem.equals("")) {
								traficoTramiteMatriculacionBean.getTramiteTraficoBean().setCemIedtm(cem);
								log.info(respuesta);
								log.info("Se ha recibido el CEM:" + cem + " para el expediente: " + numExpediente);
							}
						} else if (!erroresDatosRequeridos && !erroresFormato) {
							erroresPresentacion = true;
							respuesta = "La presentacion del 576 ha sido correcta pero no se ha podido extraer el cem de la respuesta de la AEAT - ";
							respuesta += "Imprima el pdf con la informacion de la presentacion para obtener el CEM";
							log.info("La presentacion del 576 en la AEAT del expediente: " + colaBean.getIdTramite()
									+ " no ha tenido exito debido al siguiente error: "
									+ " La presentacion ha sido correcta pero no se ha podido extraer el CEM de la respuesta");
						}
					}
				}
			} else if (!erroresDatosRequeridos && !erroresFormato) {
				erroresPresentacion = true;
				respuesta = "Ha ocurrido un error indeterminado durante la presentacion del 576";
				log.info("La presentacion del 576 en la AEAT del expediente: " + colaBean.getIdTramite()
						+ " no ha tenido exito debido al siguiente error: "
						+ " El resultbean de la presentacion se ha devuelto sin valor (null)");
			}

			// **********************************************************************************

			log.debug("Cambiando a iniciado el estado del expediente: " + colaBean.getIdTramite());
			BeanPQCAMBIAR_ESTADO_S_V beanPqCambiarEstadoSV = UtilesConversionesTrafico
					.convertirTramiteTraficoABeanPQCAMB_ESTADO_S_V(
							traficoTramiteMatriculacionBean.getTramiteTraficoBean(), colaBean.getIdUsuario());
			beanPqCambiarEstadoSV.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
			beanPqCambiarEstadoSV.execute();
			log.debug("Cambiado el estado del expediente: " + colaBean.getIdTramite() + " a iniciado");

			traficoTramiteMatriculacionBean.getTramiteTraficoBean()
					.setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
			respuesta = verificacionRespuesta(respuesta);
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().setRespuesta(respuesta);
			log.debug("Guardando el expediente: " + colaBean.getIdTramite() + " ...");
			ResultBean resultBean = guardarTramite(traficoTramiteMatriculacionBean, colaBean);
			if (resultBean.getError()) {
				throw new Exception("Error al guardar el tramite: "
						+ traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()
						+ " tras la presentacion del 576 debido al siguiente error: " + resultBean.getMensaje());
			} else {
				try {
					servicioTramiteTraficoMatriculacion.actualizarRespuesta576Matriculacion(numExpediente, respuesta);
				} catch (Exception e) {
					log.error("Error al actualizar la respuesta 576 del trámite de matriculación: " + numExpediente, e);
				}
			}

			// *********************************************************************************************

			String textoNotificacion = "";
			String tipoEjecucion = "";
			if (erroresDatosRequeridos == false && erroresFormato == false && erroresPresentacion == false) {
				textoNotificacion = "Presentacion 576 correcta. CEM actualizado";
				tipoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
			} else {
				textoNotificacion = "No se ha podido presentar el 576 en la AEAT";
				tipoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
			}
			log.debug("Creando la siguiente notificacion: " + textoNotificacion + " para el expediente: "
					+ numExpediente);
			getUtilesProcesos().creaNotificacion(colaBean, textoNotificacion);
			log.debug("Creada la notificacion para el expediente: " + numExpediente);
			peticionCorrecta(jobExecutionContext);
			log.debug("Borrando la solicitud: " + colaBean.getIdEnvio());
			getModeloSolicitud().borrarSolicitud(colaBean.getIdEnvio(), respuesta);
			log.debug("Borrada la solicitud: " + colaBean.getIdEnvio());

			colaBean.setRespuesta(respuesta != null && !respuesta.equals("") ? respuesta : textoNotificacion);

			log.debug("Actualizando la ultima ejecucion del proceso del tipo: " + tipoEjecucion);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, tipoEjecucion, respuesta,
					ConstantesProcesos.PROCESO_576);
			log.debug("Actualizada la ultima ejecucion del proceso");

		} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion) {
			sinPeticiones(jobExecutionContext);
		} catch (TramiteNoRecuperadoExcepcion tramiteNoRecuperadoExcepcion) {
			tratarRecuperable(jobExecutionContext, colaBean,
					ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO,
					traficoTramiteMatriculacionBean);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION,
					ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO,
					ConstantesProcesos.PROCESO_576);
		} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
			tratarRecuperable(jobExecutionContext, colaBean, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1(),
					traficoTramiteMatriculacionBean);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION,
					ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO, ConstantesProcesos.PROCESO_576);
		} catch (OegamExcepcion oegamEx) {
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR,
					traficoTramiteMatriculacionBean);
			String error = oegamEx.getMensajeError1();
			if (error == null || error.equals("")) {
				error = oegamEx.toString();
			}
			log.error("OegamExcepcion: ", oegamEx);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error,
					ConstantesProcesos.PROCESO_576);
		} catch (Exception e) {
			String error = e.getMessage();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			log.error("Exception: ", e);
			tratarRecuperable(jobExecutionContext, colaBean,
					ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error, traficoTramiteMatriculacionBean);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error,
					ConstantesProcesos.PROCESO_576);
		} catch (Throwable e) {
			String error = e.getMessage();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			log.error("Throwable: ", e);
			tratarRecuperable(jobExecutionContext, colaBean,
					ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error, traficoTramiteMatriculacionBean);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error,
					ConstantesProcesos.PROCESO_576);
		}
	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		log.error("cambiar numero instancias en Proceso576");
	}

	private ResultBean guardarTramite(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean,
			ColaBean colaBean) throws Throwable {
		ResultBean resultBean = new ResultBean();

		traficoTramiteMatriculacionBean.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));

		List<ContratoUsuarioVO> contratos = utilesColegiado.getContratosUsuario(colaBean.getIdUsuario().toString());
		for (ContratoUsuarioVO elemento : contratos) {
			HashMap<String, Object> resultadoModelo = null;
			resultadoModelo = getModeloMatriculacion().guardarTramite(traficoTramiteMatriculacionBean,
					elemento.getContrato().getColegiado().getNumColegiado(),
					new BigDecimal(elemento.getId().getIdContrato()), colaBean.getIdUsuario(),
					new BigDecimal(elemento.getId().getIdContrato()));
			resultBean = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
			if (!resultBean.getError()) {
				return resultBean;
			}
		}
		return resultBean;
	}

	private void tratarRecuperable(JobExecutionContext jobExecutionContext, ColaBean colaBean, String respuestaError,
			TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {

		String nodo = gestorPropiedades.valorPropertie("nombreHostProceso");
		BigDecimal numIntentos = getModeloProcesos().getIntentosMaximos(colaBean.getProceso(), nodo);
		log.error("Error RECUPERABLE");
		log.error("Solicitud: " + colaBean.getIdEnvio()
				+ " - Error en la presentacion del 576 debido al siguiente error: " + respuestaError);
		if (colaBean.getNumeroIntento().intValue() < numIntentos.intValue()) {
			log.error("Solicitud: " + colaBean.getIdEnvio() + " - Numero de intentos=" + colaBean.getNumeroIntento());
			log.error("Solicitud: " + colaBean.getIdEnvio() + " - Numero maximo de intentos =" + numIntentos);
			getModeloSolicitud().errorSolicitud(colaBean.getIdEnvio(), respuestaError);
			jobExecutionContext.getMergedJobDataMap().put("retorno", RetornoProceso.ERROR_RECUPERABLE);
		} else {
			log.error("Solicitud: " + colaBean.getIdEnvio()
					+ " - Superado el numero de intentos maximos permitidos para la cola. Sale de la cola.");
			// Queda en iniciado con la respuesta de error y se borra la solicitud
			try {
				String respuesta = "Ha ocurrido un error durante la presentacion del 576 en la AEAT";
				if (traficoTramiteMatriculacionBean.getTramiteTraficoBean() != null) {
					BeanPQCAMBIAR_ESTADO_S_V beanPqCambiarEstadoSV = UtilesConversionesTrafico
							.convertirTramiteTraficoABeanPQCAMB_ESTADO_S_V(
									traficoTramiteMatriculacionBean.getTramiteTraficoBean(), colaBean.getIdUsuario());
					beanPqCambiarEstadoSV.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
					beanPqCambiarEstadoSV.execute();
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().setRespuesta(respuesta);
					ResultBean resultBean = guardarTramite(traficoTramiteMatriculacionBean, colaBean);
					if (resultBean.getError()) {
						log.error("Error al guardar el tramite: "
								+ traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()
								+ " tras la presentacion del 576 debido al siguiente error: "
								+ resultBean.getMensaje());
					} else {
						try {
							servicioTramiteTraficoMatriculacion.actualizarRespuesta576Matriculacion(
									traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(),
									respuesta);
						} catch (Exception e) {
							log.error("Error al actualizar la respuesta 576 del trámite de matriculación: "
									+ traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(), e);
						}
					}
				}

				String textoNotificacion = "No se ha podido presentar el 576 en la AEAT";
				getUtilesProcesos().creaNotificacion(colaBean, textoNotificacion);
				peticionCorrecta(jobExecutionContext);
				getModeloSolicitud().borrarSolicitud(colaBean.getIdEnvio(), respuesta);
				colaBean.setRespuesta(textoNotificacion);

				ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_NO_CORRECTA,
						textoNotificacion, ConstantesProcesos.PROCESO_576);
			} catch (Throwable ex) {
				if (traficoTramiteMatriculacionBean.getTramiteTraficoBean() != null) {
					log.error("Error en la presentacion del 576 del expediente: "
							+ traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()
							+ " debido al siguiente error: " + ex, ex);
				} else {
					log.error("Error en la presentacion del 576 debido al siguiente error: " + ex, ex);
				}
			}
		}
	}

	private String verificacionRespuesta(String respuesta) {
		if (respuesta.length() <= 387) {
			return respuesta;
		} else {
			do {
				respuesta = respuesta.substring(0, respuesta.lastIndexOf(" - ")) + "...";
			} while (respuesta.length() > 387);
		}
		return respuesta;
	}

	/* ***************************************************************** */
	/* MODELOS ********************************************************* */
	/* ***************************************************************** */

	public ModeloMatriculacion getModeloMatriculacion() {
		if (modeloMatriculacion == null) {
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
	}

	public ModeloPresentacionAEAT getModeloPresentacionAEAT() {
		if (modeloPresentacionAEAT == null) {
			modeloPresentacionAEAT = new ModeloPresentacionAEAT();
		}
		return modeloPresentacionAEAT;
	}

	public void setModeloPresentacionAEAT(ModeloPresentacionAEAT modeloPresentacionAEAT) {
		this.modeloPresentacionAEAT = modeloPresentacionAEAT;
	}

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