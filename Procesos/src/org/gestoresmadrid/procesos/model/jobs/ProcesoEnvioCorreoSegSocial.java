package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoEnvioCorreoSegSocial extends AbstractProcesoBase {
	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoEnvioCorreoSegSocial.class);

	@Autowired
	private ServicioPermisos servicioPermisos;

	@Autowired
	private ServicioConsultaNotificacion servicioConsultaNotificacion;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	public static final String CODIGO_APLICACION = "OEGAM_SEGSOC";
	public static final String CODIGO_FUNCION = "SS2";

	@Override
	public void doExecute() throws JobExecutionException {

		log.info("Proceso " + ProcesosEnum.SS_ENVIO_EMAIL + " -- Procesando petición");

		try {
			if (utilesFecha.esFechaLaborable(true)) {
				if (!hayEnvioData(getProceso())) {

					/**
					 * 1.- Obtenemos los contratos que tienen activado la opción de envío de
					 * descarga y envío de correo automático. 2.- Llamar al servicio que genera la
					 * petición.
					 */

					List<UsuarioFuncionSinAccesoVO> usuariosSinAcceso = servicioPermisos
							.compruebaAcceso(CODIGO_FUNCION);
					List<ContratoDto> contratos = servicioContrato.getContratosPorAplicacion(CODIGO_APLICACION);

					if (contratos != null) {
						List<String> contratosPermitidos = new ArrayList<String>();
						List<String> contratosNoPermitidos = new ArrayList<String>();
						// Eliminar de usuariosTotales los usuariosSinAcceso.
						for (ContratoDto contrato : contratos) {
							contratosPermitidos.add(contrato.getIdContrato().toString());
						}
						if (usuariosSinAcceso != null) {
							for (UsuarioFuncionSinAccesoVO usuario : usuariosSinAcceso) {
								// Para no incluir el contrato de Jacometrezo
								if (!new Long(519).equals(usuario.getId().getIdContrato())) {
									contratosNoPermitidos.add(Long.toString(usuario.getId().getIdContrato()));
								}
							}
						}

						contratosPermitidos.removeAll(contratosNoPermitidos);

						for (String idContrato : contratosPermitidos) {
							ContratoVO contrato = servicioContrato.getContrato(new BigDecimal(idContrato));
							// String numColegiado = contrato.getColegiado().getNumColegiado();
							String alias = contrato.getColegiado().getAlias();
							BigDecimal idUsuario = new BigDecimal(contrato.getColegiado().getIdUsuario());

							if (alias != null) {
								servicioConsultaNotificacion.creaSolicitudCorreo(alias, new BigDecimal(idContrato),
										idUsuario);
							}
						}

						actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA,
								ConstantesProcesos.RECUPERACION_CORRECTA_NOTIFICACIONES, "1");

					} else {
						log.info("Proceso " + ProcesosEnum.SS_ENVIO_EMAIL
								+ " -- No hay usuarios que tengan el permiso SS2 activado");
					}

				} else {
					log.info("Proceso " + ProcesosEnum.SS_ENVIO_EMAIL
							+ " -- El proceso ya se ha ejecutado para el día de hoy");
				}

			} else {
				log.info("Proceso " + ProcesosEnum.SS_ENVIO_EMAIL + " -- Día NO LABORABLE");
			}

			log.info("Proceso " + ProcesosEnum.SS_ENVIO_EMAIL + " -- Peticion Procesada");
			log.info("Proceso " + ProcesosEnum.SS_ENVIO_EMAIL + " -- Proceso ejecutado correctamente");
			peticionCorrecta();

		} catch (Exception e) {
			try {
				String error;
				if (e.getMessage() != null && !e.getMessage().equals("")) {
					error = e.getMessage();
				} else {
					error = e.toString();
				}
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(new ColaBean(), ConstantesProcesos.EJECUCION_EXCEPCION,
						error, ProcesosEnum.SS_ENVIO_EMAIL.getNombreEnum());
				log.error("Proceso " + ProcesosEnum.SS_ENVIO_EMAIL.getNombreEnum()
						+ " -- Error en el proceso notificaciones automáticas Seg.Social. Error Recibido: " + e);
			} catch (Exception ex) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en "
						+ ProcesosEnum.SS_ENVIO_EMAIL.getNombreEnum());
			}

		} catch (OegamExcepcion e) {
			try {
				String error;
				if (e.getMessage() != null && !e.getMessage().equals("")) {
					error = e.getMessage();
				} else {
					error = e.toString();
				}
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(new ColaBean(), ConstantesProcesos.EJECUCION_EXCEPCION,
						error, ProcesosEnum.SS_ENVIO_EMAIL.getNombreEnum());

				log.error("OEGAMERROR: Proceso " + ProcesosEnum.SS_ENVIO_EMAIL.getNombreEnum()
						+ " -- Error en el proceso notificaciones automáticas Seg.Social. Error Recibido: " + e);
			} catch (Exception ex) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en "
						+ ProcesosEnum.SS_ENVIO_EMAIL.getNombreEnum());
			}
		}
	}

	protected String getProceso() {
		return ProcesosEnum.SS_ENVIO_EMAIL.getNombreEnum();
	}

}