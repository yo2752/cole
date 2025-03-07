package org.gestoresmadrid.procesos.model.jobs.sega;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceMatwSega;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatwBean;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoMatwSega extends AbstractProcesoBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoMatwSega.class);

	@Autowired
	ServicioWebServiceMatwSega servicioWebServiceMatw;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean colaBean = new ColaBean();
		Boolean esLaborable = Boolean.TRUE;
		String resultadoEjecucion = null;
		String  excepcion = null;
		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}
		if (esLaborable) {
			try {
				log.info("Proceso " + getProceso() + " -- Buscando Solicitud");
				colaBean = tomarSolicitud();
				if (colaBean == null) {
					sinPeticiones();
				} else {
					if(colaBean.getXmlEnviar() == null && !colaBean.getXmlEnviar().isEmpty()){
						try {
							finalizarTransaccionConErrorNoRecuperable(colaBean, "No existen el xml de envio.");
							colaBean.setRespuesta("Error: La Solicitud " + colaBean.getIdTramite() + " no contiene xml de envio.");
							resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					} else {
						new UtilesWSMatw().cargarAlmacenesTrafico();
						ResultadoMatwBean resultado = servicioWebServiceMatw.tramitarPeticion(colaBean);
						if(resultado.getExcepcion() != null){
							throw new Exception(resultado.getExcepcion());
						}else if(resultado.getError()){
							resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
							colaBean.setRespuesta(resultado.getMensajeError());
							try {
								if(resultado.getEsRecuperable()){
									finalizarTransaccionErrorRecuperable(colaBean, colaBean.getRespuesta());
								}else{
									finalizarTransaccionConErrorNoRecuperable(colaBean, resultado.getMensajeError());
								}
							} catch (BorrarSolicitudExcepcion e) {
								log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
								resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
								excepcion = e.toString();
							}
						}else{
							resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
							colaBean.setRespuesta(resultadoEjecucion);
							finalizarTransaccionCorrecta(colaBean, resultadoEjecucion);
						}
					}
					actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
				}
			} catch (Exception e) {
				log.error("Ocurrio un error no controlado en el proceso de MatwSega, error: ", e);
				String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
				if (colaBean != null && colaBean.getProceso() != null) {
					try {
						finalizarTransaccionErrorRecuperableConErrorServico(colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
					} catch (BorrarSolicitudExcepcion e1) {
						log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: ", e1);
					}
					actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
				} else {
					log.error("Se ha producido un error y no se ha podido recibir la solicitud");
				}
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de cargar los almacenes de trafico, error: ", e1);
				String messageException =  e1.getMessage() != null ? e1.getMessage() : e1.toString();
				if (colaBean != null && colaBean.getProceso() != null) {
					try {
						finalizarTransaccionErrorRecuperableConErrorServico(colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
					} catch (BorrarSolicitudExcepcion e2) {
						log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: ",e2);
					}
					actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
				} else {
					log.error("Se ha producido un error y no se ha podido recibir la solicitud");
				}
			}
			
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.MATW_SEGA.getNombreEnum();
	}
	
	@Override
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError)throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioWebServiceMatw.actualizarRespuestaMatriculacion(cola.getIdTramite(),respuestaError);
		ResultadoMatwBean resultadoMatw =  servicioWebServiceMatw.cambiarEstadoTramite(cola.getIdTramite(),EstadoTramiteTrafico.Finalizado_Con_Error, cola.getIdUsuario());
		if(resultadoMatw.getError()){
			marcarSolicitudConErrorServicio(cola, resultadoMatw.getMensajeError());
		}
	}

	@Override
	protected void finalizarTransaccionCorrecta(ColaBean cola, String resultado) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		ResultadoMatwBean resultadoMatw = servicioWebServiceMatw.descontarCreditos(cola.getIdTramite(), cola.getIdContrato(), cola.getIdUsuario());
		if(!resultadoMatw.getError()) {
			resultadoMatw = servicioWebServiceMatw.cambiarEstadoTramite(cola.getIdTramite(),EstadoTramiteTrafico.Finalizado_Telematicamente, cola.getIdUsuario());
			if(resultadoMatw.getError()){
				marcarSolicitudConErrorServicio(cola, resultadoMatw.getMensajeError());
			}
		} else {
			try {
				finalizarTransaccionConErrorNoRecuperable(cola, resultadoMatw.getMensajeError());
			} catch (BorrarSolicitudExcepcion e) {
				log.error("Error al borrar la solicitud: " + cola.getIdEnvio() + ", Error: ",e);
			}
		}
	}
	
	@Override
	protected void finalizarTransaccionErrorRecuperableConErrorServico(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			getModeloSolicitud().errorSolicitud(cola.getIdEnvio(), respuestaError);
			peticionRecuperable();
		} else {
			marcarSolicitudConErrorServicio(cola, respuestaError);
		}
	}
}
