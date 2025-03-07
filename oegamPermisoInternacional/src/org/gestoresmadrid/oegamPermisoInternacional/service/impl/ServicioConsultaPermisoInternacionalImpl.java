package org.gestoresmadrid.oegamPermisoInternacional.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioConsultaPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ConsultaPermisoInternacionalBean;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ResultadoPermInterBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaPermisoInternacionalImpl implements ServicioConsultaPermisoInternacional {

	private static final long serialVersionUID = 2373847430681128513L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaPermisoInternacionalImpl.class);

	@Autowired
	ServicioPermisoInternacional servicioPermisoInternacional;

	@Override
	public List<ConsultaPermisoInternacionalBean> convertirListaEnBeanPantallaConsulta(List<PermisoInternacionalVO> lista) {
		List<ConsultaPermisoInternacionalBean> listaConsulta = new ArrayList<>();
		for (PermisoInternacionalVO permisoInternacionalVO : lista) {
			ConsultaPermisoInternacionalBean bean = new ConsultaPermisoInternacionalBean();
			bean.setCodigoTasa(permisoInternacionalVO.getCodigoTasa());
			bean.setDescContrato(permisoInternacionalVO.getContrato().getColegiado().getNumColegiado() + " - " + permisoInternacionalVO.getContrato().getVia());
			bean.setDoiTitular(permisoInternacionalVO.getDoiTitular());
			bean.setEstado(EstadoTramitesInterga.convertirTexto(permisoInternacionalVO.getEstado()));
			bean.setEstadoDoc(EstadoTramitesInterga.convertirTexto(permisoInternacionalVO.getEstadoImpresion()));
			bean.setIdPermiso(permisoInternacionalVO.getIdPermiso());
			bean.setNumExpediente(permisoInternacionalVO.getNumExpediente());
			bean.setRefPropia(permisoInternacionalVO.getRefPropia());
			listaConsulta.add(bean);
		}
		return listaConsulta;
	}

	@Override
	public ResultadoPermInterBean eliminarTramites(String codSeleccionados, Long idUsuario) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsPermisosInternacional = codSeleccionados.split("_");
				for (String idPermisoInternacional : idsPermisosInternacional) {
					try {
						ResultadoPermInterBean resultValidar = servicioPermisoInternacional.eliminar(new Long(idPermisoInternacional), idUsuario);
						if (resultValidar.getError()) {
							resultado.addResumenError(resultValidar.getMensaje());
						} else {
							resultado.addResumenOK(resultValidar.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de eliminar el tramite de permiso internacional con ID: " + idPermisoInternacional + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de eliminar el tramite de permiso internacional con ID: " + idPermisoInternacional);
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
	public ResultadoPermInterBean validarTramites(String codSeleccionados, Long idUsuario) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsPermisosInternacional = codSeleccionados.split("_");
				for (String idPermisoInternacional : idsPermisosInternacional) {
					try {
						ResultadoPermInterBean resultValidar = servicioPermisoInternacional.validar(new Long(idPermisoInternacional), idUsuario);
						if (resultValidar.getError()) {
							resultado.addResumenError(resultValidar.getMensaje());
						} else {
							resultado.addResumenOK(resultValidar.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de validar el tramite de permiso internacional con ID: " + idPermisoInternacional + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de validar el tramite de permiso internacional con ID: " + idPermisoInternacional);
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
	public ResultadoPermInterBean cambiarEstadoTramites(String codSeleccionados, String estadoNuevo, Long idUsuario) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				if (estadoNuevo != null && !estadoNuevo.isEmpty()) {
					String[] idsPermisosInternacional = codSeleccionados.split("_");
					for (String idPermisoInternacional : idsPermisosInternacional) {
						try {
							ResultadoPermInterBean resultValidar = servicioPermisoInternacional.cambiarEstado(new Long(idPermisoInternacional), estadoNuevo, idUsuario, Boolean.TRUE);
							if (resultValidar.getError()) {
								resultado.addResumenError(resultValidar.getMensaje());
							} else {
								resultado.addResumenOK(resultValidar.getMensaje());
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de cambiar el estado del tramite de permiso internacional con ID: " + idPermisoInternacional + ", error: ", e);
							resultado.addResumenError("Ha sucedido un error a la hora de cambiar el estado del tramite de permiso internacional con ID: " + idPermisoInternacional);
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
	public ResultadoPermInterBean tramitarTramites(String codSeleccionados, Long idUsuario) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsPermisosInternacional = codSeleccionados.split("_");
				for (String idPermisoInternacional : idsPermisosInternacional) {
					try {
						ResultadoPermInterBean resultValidar = servicioPermisoInternacional.tramitar(new Long(idPermisoInternacional), idUsuario);
						if (resultValidar.getError()) {
							resultado.addResumenError(resultValidar.getMensaje());
						} else {
							resultado.addResumenOK(resultValidar.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de tramitar el tramite de permiso internacional con ID: " + idPermisoInternacional + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de tramitar el tramite de permiso internacional con ID: " + idPermisoInternacional);
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
	public ResultadoPermInterBean imprimirTramites(String codSeleccionados, Long idUsuario, Boolean tienePermisoAdmin) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				resultado = validarTramitesImprimir(codSeleccionados, tienePermisoAdmin);
				if (!resultado.getError()) {
					resultado = servicioPermisoInternacional.imprimir(new Long(codSeleccionados), idUsuario);
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

	private ResultadoPermInterBean validarTramitesImprimir(String codSeleccionados, Boolean tienePermisoAdmin) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		if (codSeleccionados == null || codSeleccionados.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar algún tramite para imprimir su permiso internacional.");
		} else if (codSeleccionados.split("_").length > 1) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Solo se permite imprimir de manera individual los tramites.");
		} else if (!tienePermisoAdmin) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene permisos para realizar esta acción sobre los tramites.");
		}
		return resultado;
	}

}