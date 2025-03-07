package org.gestoresmadrid.procesos.model.jobs;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.procesos.model.service.ServicioWebServiceMatw;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatwBean;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoMatw extends AbstractProceso {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoMatw.class);

	@Autowired
	ServicioWebServiceMatw servicioWebServiceMatw;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaDto colaDto = new ColaDto();
		Boolean esLaborable = true;
		Boolean forzarEjecucion = false;
		String resultadoEjecucion = null;
		String excepcion = null;

		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}
		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.FORZAR_EJECUCION_MATRICULACION))) {
			forzarEjecucion = true;
		}

		if (esLaborable || forzarEjecucion) {
			try {
				log.info("Proceso " + getProceso() + " -- Buscando Solicitud");
				colaDto = tomarSolicitud();
				if (colaDto == null) {
					sinPeticiones();
				} else {
					if (colaDto.getXmlEnviar() == null || colaDto.getXmlEnviar().isEmpty()) {
						finalizarTransaccionConErrorNoRecuperable(colaDto, "No existen el xml de envio.");
						colaDto.setRespuesta("Error: La Solicitud " + colaDto.getIdTramite() + " no contiene xml de envio.");
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					} else {
						ResultadoMatwBean resultado = servicioWebServiceMatw.tramitarPeticion(colaDto);
						if (resultado.getExcepcion() != null) {
							throw new Exception(resultado.getExcepcion());
						} else if (resultado.getError()) {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
							colaDto.setRespuesta(resultado.getMensajeError());
							if (resultado.getEsRecuperable()) {
								finalizarTransaccionErrorRecuperable(colaDto, colaDto.getRespuesta());
							} else {
								finalizarTransaccionConErrorNoRecuperable(colaDto, resultado.getMensajeError());
							}
						} else {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
							colaDto.setRespuesta(resultadoEjecucion);
							finalizarTransaccionCorrecta(colaDto, resultadoEjecucion);
						}
					}
					actualizarUltimaEjecucion(colaDto, resultadoEjecucion, excepcion);
				}
			} catch (Exception e) {
				log.error("Excepcion Matriculación General", e);
				String messageException = getMessageException(e);
				try {
					finalizarTransaccionErrorRecuperable(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION + messageException);
				} catch (Exception e1) {
					log.error("No ha sido posible borrar la solicitud", e1);
				}
				actualizarUltimaEjecucion(colaDto, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			}
		} else {
			peticionCorrecta();
		}
	}

	@Override
	protected void finalizarTransaccionConErrorNoRecuperable(ColaDto cola, String respuestaError) {
		super.finalizarTransaccionConErrorNoRecuperable(cola, respuestaError);
		servicioWebServiceMatw.actualizarRespuestaMatriculacion(cola.getIdTramite(), cola.getIdUsuario(), respuestaError);
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.MATW.getNombreEnum();
	}
}