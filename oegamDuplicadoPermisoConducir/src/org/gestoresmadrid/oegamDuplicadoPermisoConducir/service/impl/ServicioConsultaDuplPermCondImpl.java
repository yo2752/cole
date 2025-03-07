package org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioConsultaDuplPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioDuplicadoPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ConsultaDuplicadoPermConducirBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaDuplPermCondImpl implements ServicioConsultaDuplPermCond {

	private static final long serialVersionUID = -1210130843645543431L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaDuplPermCondImpl.class);

	@Autowired
	ServicioDuplicadoPermCond servicioDuplicadoPermCond;

	@Override
	public List<ConsultaDuplicadoPermConducirBean> convertirListaEnBeanPantallaConsulta(List<DuplicadoPermisoConducirVO> lista) {
		List<ConsultaDuplicadoPermConducirBean> listaConsulta = new ArrayList<ConsultaDuplicadoPermConducirBean>();
		for (DuplicadoPermisoConducirVO duplicadoPermisoConducirVO : lista) {
			ConsultaDuplicadoPermConducirBean bean = new ConsultaDuplicadoPermConducirBean();
			bean.setCodigoTasa(duplicadoPermisoConducirVO.getCodigoTasa());
			bean.setDescContrato(duplicadoPermisoConducirVO.getContrato().getColegiado().getNumColegiado() + " - " + duplicadoPermisoConducirVO.getContrato().getVia());
			bean.setDoiTitular(duplicadoPermisoConducirVO.getDoiTitular());
			bean.setEstado(EstadoTramitesInterga.convertirTexto(duplicadoPermisoConducirVO.getEstado()));
			bean.setEstadoDoc(EstadoTramitesInterga.convertirTexto(duplicadoPermisoConducirVO.getEstadoImpresion()));
			bean.setIdDuplicadoPermCond(duplicadoPermisoConducirVO.getIdDuplicadoPermCond());
			bean.setNumExpediente(duplicadoPermisoConducirVO.getNumExpediente());
			bean.setRefPropia(duplicadoPermisoConducirVO.getRefPropia());
			listaConsulta.add(bean);
		}
		return listaConsulta;
	}

	@Override
	public ResultadoDuplPermCondBean eliminarTramites(String codSeleccionados, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsDuplicadosPermConducir = codSeleccionados.split("_");
				for (String idDuplicadoPermConducir : idsDuplicadosPermConducir) {
					try {
						ResultadoDuplPermCondBean resultValidar = servicioDuplicadoPermCond.eliminar(new Long(idDuplicadoPermConducir), idUsuario);
						if (resultValidar.getError()) {
							resultado.addResumenError(resultValidar.getMensaje());
						} else {
							resultado.addResumenOK(resultValidar.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de eliminar el tramite de duplicado permiso conducir con ID: " + idDuplicadoPermConducir + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de eliminar el tramite de duplicado permiso conducir con ID: " + idDuplicadoPermConducir);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún tramite para eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar los tramites.");
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean validarTramites(String codSeleccionados, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsDuplicadosPermConducir = codSeleccionados.split("_");
				for (String idDuplicadoPermConducir : idsDuplicadosPermConducir) {
					try {
						ResultadoDuplPermCondBean resultValidar = servicioDuplicadoPermCond.validar(new Long(idDuplicadoPermConducir), idUsuario);
						if (resultValidar.getError()) {
							resultado.addResumenError(resultValidar.getMensaje());
						} else {
							resultado.addResumenOK(resultValidar.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de validar el tramite de duplicado permiso conducir con ID: " + idDuplicadoPermConducir + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de validar el tramite de duplicado permiso conducir con ID: " + idDuplicadoPermConducir);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún tramite para validar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar los tramites.");
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean cambiarEstadoTramites(String codSeleccionados, String estadoNuevo, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				if (estadoNuevo != null && !estadoNuevo.isEmpty()) {
					String[] idsDuplicadosPermConducir = codSeleccionados.split("_");
					for (String idDuplicadoPermConducir : idsDuplicadosPermConducir) {
						try {
							ResultadoDuplPermCondBean resultValidar = servicioDuplicadoPermCond.cambiarEstado(new Long(idDuplicadoPermConducir), estadoNuevo, idUsuario, Boolean.TRUE);
							if (resultValidar.getError()) {
								resultado.addResumenError(resultValidar.getMensaje());
							} else {
								resultado.addResumenOK(resultValidar.getMensaje());
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de cambiar el estado del tramite de duplicado permiso conducir con ID: " + idDuplicadoPermConducir + ", error: ", e);
							resultado.addResumenError("Ha sucedido un error a la hora de cambiar el estado del tramite de duplicado permiso conducir con ID: " + idDuplicadoPermConducir);
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe seleccionar algún estado nuevo para actualizar los trámites seleccionados.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún tramite para cambiar su estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de los tramites.");
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean tramitarTramites(String codSeleccionados, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsDuplicadosPermConducir = codSeleccionados.split("_");
				for (String idDuplicadoPermConducir : idsDuplicadosPermConducir) {
					try {
						ResultadoDuplPermCondBean resultValidar = servicioDuplicadoPermCond.tramitar(new Long(idDuplicadoPermConducir), idUsuario);
						if (resultValidar.getError()) {
							resultado.addResumenError(resultValidar.getMensaje());
						} else {
							resultado.addResumenOK(resultValidar.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de tramitar el tramite de duplicado permiso conducir con ID: " + idDuplicadoPermConducir + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de tramitar el tramite de duplicado permiso conducir con ID: " + idDuplicadoPermConducir);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún tramite para tramitar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tramitar los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tramitar los tramites.");
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean imprimirTramites(String codSeleccionados, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				resultado = validarTramitesImprimir(codSeleccionados);
				if (!resultado.getError()) {
					resultado = servicioDuplicadoPermCond.imprimir(new Long(codSeleccionados), idUsuario);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún tramite para tramitar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tramitar los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tramitar los tramites.");
		}
		return resultado;
	}

	private ResultadoDuplPermCondBean validarTramitesImprimir(String codSeleccionados) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		if (codSeleccionados == null || codSeleccionados.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar algún tramite para imprimir su permiso internacional.");
		} else if (codSeleccionados.split("_").length > 1) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Solo se permite imprimir de manera individual los tramites.");
		}
		return resultado;
	}
}
