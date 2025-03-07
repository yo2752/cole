package org.gestoresmadrid.procesos.model.jobs;

import java.io.File;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionProceso;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TramiteNoRecuperadoExcepcion;
import colas.constantes.ConstantesProcesos;
import colas.procesos.utiles.UtilesProcesos;
import escrituras.beans.ResultBean;

/**
 * Proceso para la impresión de trámites masivos
 */
public class ProcesoImprimirTramites extends AbstractProcesoBase {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoImprimirTramites.class);

	@Autowired
	ServicioImpresionProceso servicioImpresionProceso;

	private UtilesProcesos utilesProcesos;

	@Override
	public void doExecute() throws JobExecutionException {
		ColaBean colaBean = new ColaBean();
		String tipoImpreso = null;
		String tipoTramite = null;
		String numColegiado = null;
		String[] numExpedientes = null;
		try {
			// Recupera la solicitud de la tabla cola:
			colaBean = tomarSolicitud();
			if (colaBean == null) {
				throw new SinSolicitudesExcepcion("No existe ninguna solicitud para la cola");
			}
			log.info("Proceso " + ConstantesProcesos.IMPRIMIR_TRAMITES + " -- Solicitud " + ConstantesProcesos.IMPRIMIR_TRAMITES + " encontrada");

			ResultBean resultado = null;

			File fichero = servicioImpresionProceso.buscarFichero(colaBean);
			if (fichero == null) {
				throw new Throwable("No existe el fichero de petición");
			}

			resultado = servicioImpresionProceso.leerFichero(fichero);

			if (resultado != null) {
				tipoImpreso = (String) resultado.getAttachment("tipoImpreso");
				tipoTramite = (String) resultado.getAttachment("tipoTramite");
				numColegiado = (String) resultado.getAttachment("numColegiado");
				numExpedientes = (String[]) resultado.getAttachment("numExpedientes");

				log.info("Numero de expediente a imprimir: " + numExpedientes);
				
				resultado = servicioImpresionProceso.procesar(colaBean, tipoImpreso, tipoTramite, numColegiado, numExpedientes);

				if (resultado != null && !resultado.getError()) {
					colaBean.setRespuesta(resultado.getListaMensajes().get(0));
					String tipoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					String nombreFichero = (String) resultado.getAttachment("nombreFichero");
					getUtilesProcesos().creaNotificacion(colaBean, resultado.getListaMensajes().get(0));
					peticionCorrecta();

					if (fichero != null) {
						servicioImpresionProceso.borradoRecursivoFichero(fichero);
					}

					getModeloSolicitud().borrarSolicitud(colaBean.getIdEnvio(), resultado.getListaMensajes().get(0));

					servicioImpresionProceso.enviarCorreo(nombreFichero, resultado, colaBean, numExpedientes, tipoImpreso, tipoTramite, numColegiado);

					actualizarUltimaEjecucion(colaBean, tipoEjecucion, null);
				} else {
					finalizarTransaccionError(colaBean, tipoTramite, tipoImpreso, numExpedientes, generarMensaje(resultado), ConstantesProcesos.EJECUCION_NO_CORRECTA, "Error en la ImpresionMasiva: "
							+ resultado.getListaMensajes().get(0));
				}
			}
		} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion) {
			sinPeticiones();
		} catch (TramiteNoRecuperadoExcepcion tramiteNoRecuperadoExcepcion) {
			finalizarTransaccionError(colaBean, tipoTramite, tipoImpreso, numExpedientes, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO,
					ConstantesProcesos.EJECUCION_EXCEPCION, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);
		} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
			finalizarTransaccionError(colaBean, tipoTramite, tipoImpreso, numExpedientes, ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO, ConstantesProcesos.EJECUCION_EXCEPCION,
					ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO);
		} catch (OegamExcepcion oegamEx) {
			log.error("Proceso " + ConstantesProcesos.IMPRIMIR_TRAMITES + " -- OegamExcepcion", oegamEx);
			String error = oegamEx.getMensajeError1();
			if (error == null || error.equals("")) {
				error = oegamEx.toString();
			}
			finalizarTransaccionError(colaBean, tipoTramite, tipoImpreso, numExpedientes, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error, ConstantesProcesos.EJECUCION_EXCEPCION, error);
		} catch (Exception e) {
			log.error("Proceso " + ConstantesProcesos.IMPRIMIR_TRAMITES + " -- Exception", e);
			String error = e.getMessage();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			finalizarTransaccionError(colaBean, tipoTramite, tipoImpreso, numExpedientes, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error, ConstantesProcesos.EJECUCION_EXCEPCION, error);
		} catch (Throwable e) {
			log.error("Proceso " + ConstantesProcesos.IMPRIMIR_TRAMITES + " -- Throwable", e);
			String error = e.getMessage();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			finalizarTransaccionError(colaBean, tipoTramite, tipoImpreso, numExpedientes, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error, ConstantesProcesos.EJECUCION_EXCEPCION, error);
		}
	}

	protected String generarMensaje(ResultBean resultBean) {
		String mensaje = "";
		for (String mens : resultBean.getListaMensajes()) {
			mensaje += mens + ". ";
		}
		return mensaje;
	}

	protected void finalizarTransaccionError(ColaBean colaBean, String tipoTramite, String tipoImpreso, String[] numExpedientes, String mensajeRecuperable, String tipoEjecucion, String error) {
		boolean recuperable = getServicioProcesos().tratarRecuperable(colaBean, mensajeRecuperable);
		if (!recuperable) {
			servicioImpresionProceso.devolverCreditos(tipoTramite, tipoImpreso, servicioImpresionProceso.convertirNumExp(numExpedientes), colaBean.getIdContrato(), colaBean.getIdUsuario());
			errorNoRecuperable();
		} else {
			peticionRecuperable();
		}
		if (colaBean.getRespuesta() == null) {
			colaBean.setRespuesta(error);
		}
		actualizarUltimaEjecucion(colaBean, tipoEjecucion, error);
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.IMPRIMIRTRAMITES.getNombreEnum();
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

	public ServicioImpresionProceso getServicioImpresionProceso() {
		return servicioImpresionProceso;
	}

	public void setServicioImpresionProceso(ServicioImpresionProceso servicioImpresionProceso) {
		this.servicioImpresionProceso = servicioImpresionProceso;
	}
}