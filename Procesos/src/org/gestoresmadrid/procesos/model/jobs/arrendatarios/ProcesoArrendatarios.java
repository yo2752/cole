package org.gestoresmadrid.procesos.model.jobs.arrendatarios;

import java.math.BigDecimal;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.model.service.ServicioWebServiceArrendatario;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultadoWSCaycBean;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoArrendatarios extends AbstractProcesoBase {

	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoArrendatarios.class);
	private static final String NOTIFICACION = "PROCESO ARRENDATARIOS FINALIZADO";
	@Autowired
	private ServicioNotificacion servicioNotificacion;
	@Autowired
	private ServicioWebServiceArrendatario servicioWebServiceArrendatario;
	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;
	
	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean colaBean = null;
		Boolean esLaborable = Boolean.TRUE;
		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}
		if(esLaborable){
			try {
				colaBean = tomarSolicitud();
				if (colaBean == null) {
					sinPeticiones();
				} else {
					new UtilesWSMatw().cargarAlmacenesTrafico();
					String resultadoEjecucion = null;
					String excepcion = null;
					if (colaBean.getXmlEnviar() == null || colaBean.getXmlEnviar().isEmpty()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						excepcion = "No se ha recuperado el tipo de consulta arrendatario correspondiente a la solicitud con identificador: "
								+ colaBean.getIdEnvio();
						log.error(excepcion);
						try {
							finalizarTransaccionConErrorNoRecuperable(colaBean, excepcion);
						} catch (BorrarSolicitudExcepcion e) {
							log.error(
									"Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					} else {
	
						ResultadoWSCaycBean resultado = new ResultadoWSCaycBean();
						// Hacer distincion entre alta y modificacio
						if (colaBean.getXmlEnviar().contains(ConstantesGestorFicheros.XML_ALTA_ARRENDATARIO)) {
							resultado = servicioWebServiceArrendatario.procesarAltaArrendatario(colaBean);
						} else {
							resultado = servicioWebServiceArrendatario.procesarModificacionArrendatario(colaBean);
						}
						if (resultado.getExcepcion() != null) {
							throw resultado.getExcepcion();
						} else if (resultado.getError()) {
							// Ocurrio un error en el servicio
							resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
							colaBean.setRespuesta(resultado.getMensajeError());
							try {
								finalizarTransaccionConErrorNoRecuperable(colaBean, resultado.getMensajeError());
							} catch (BorrarSolicitudExcepcion e) {
								log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: "
										+ e.toString());
								resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
								excepcion = e.toString();
							}
						} else {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
							colaBean.setRespuesta(resultado.getRespuestaWS());
							finalizarTransaccionCorrectaArrendatarios(colaBean, resultadoEjecucion);
							crearNotificacion(colaBean, new BigDecimal(EstadoCaycEnum.Finalizado.getValorEnum()));
						}
					}
					actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
				}
			} catch (Exception e) {
			//	log.error("Ocurrio un error no controlado en el proceso de arrendatario", e);
				String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
				if (colaBean != null && colaBean.getProceso() != null) {
					try {
						if (messageException.length() > 500) {
							messageException = messageException.substring(0, 500);
						}
	
						finalizarTransaccionErrorRecuperableConErrorServico(colaBean,
								ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
					} catch (BorrarSolicitudExcepcion e1) {
						log.error("Error al borrar la solicitud: " + colaBean.getIdEnvio() + ", Error: " + e1.toString());
					}
					actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
				} else {
					log.error("Se ha producido un error y no se ha podido recibir la solicitud");
					actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
				}
			} catch (OegamExcepcion e1) {
				log.error("Ocurrio un error no controlado en el proceso vehículos atex5 al cargar los almacenes de claves",
						e1);
				actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, e1.getMessage());
			}
		} else {
			peticionCorrecta();
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.ARRENDATARIOS.getNombreEnum();
	}

	private void crearNotificacion(ColaBean cola, BigDecimal estadoNuevo) {
		if (cola != null && cola.getIdTramite() != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(new BigDecimal(EstadoCaycEnum.Pdte_Respuesta_DGT.getValorEnum()));
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(cola.getTipoTramite());
			notifDto.setIdTramite(cola.getIdTramite());
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

	@Override
	protected void finalizarTransaccionConErrorNoRecuperable(ColaBean cola, String respuestaError)
			throws BorrarSolicitudExcepcion {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioWebServiceArrendatario.cambiarEstadoConsulta(cola.getIdTramite(), cola.getIdUsuario(),
				EstadoCaycEnum.Finalizado_Con_Error, respuestaError, null);
		crearNotificacion(cola, new BigDecimal(EstadoCaycEnum.Finalizado_Con_Error.getValorEnum()));
	}

	private void finalizarTransaccionCorrectaArrendatarios(ColaBean cola, String resultado) {
		super.finalizarTransaccionCorrecta(cola, resultado);
		servicioWebServiceArrendatario.cambiarEstadoConsulta(cola.getIdTramite(), cola.getIdUsuario(),
				EstadoCaycEnum.Finalizado, "Solicitud correcta", null);
		crearNotificacion(cola, new BigDecimal(EstadoCaycEnum.Finalizado.getValorEnum()));
	}

	@Override
	protected void finalizarTransaccionErrorRecuperableConErrorServico(ColaBean cola, String respuestaError)
			throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentosProceso2(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			servicioProcesos.tratarRecuperable(cola, respuestaError);
			peticionRecuperable();
		} else {
			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		}
	}

}
