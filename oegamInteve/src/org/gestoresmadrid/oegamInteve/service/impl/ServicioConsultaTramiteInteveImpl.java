package org.gestoresmadrid.oegamInteve.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamInteve.service.ServicioConsultaTramiteInteve;
import org.gestoresmadrid.oegamInteve.service.ServicioTramiteInteve;
import org.gestoresmadrid.oegamInteve.view.bean.ConsultaTramiteInteveBean;
import org.gestoresmadrid.oegamInteve.view.bean.ResultadoTramiteInteveBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaTramiteInteveImpl implements ServicioConsultaTramiteInteve {

	private static final long serialVersionUID = -5252830829794686181L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaTramiteInteveImpl.class);

	@Autowired
	ServicioTramiteInteve servicioTramiteInteve;

	@Autowired
	ServicioComunTramiteTrafico servicioComunTramiteTrafico;

	@Override
	public List<ConsultaTramiteInteveBean> convertirListaEnBeanPantallaConsulta(List<TramiteTraficoInteveVO> listaBBDD) {
		List<ConsultaTramiteInteveBean> listaBean = new ArrayList<>();
		for (TramiteTraficoInteveVO traficoInteveVO : listaBBDD) {
			ConsultaTramiteInteveBean consultaTramiteInteveBean = new ConsultaTramiteInteveBean();
			consultaTramiteInteveBean.setEstado(EstadoTramiteTrafico.convertirTexto(traficoInteveVO.getEstado().toString()));
			consultaTramiteInteveBean.setNumExpediente(traficoInteveVO.getNumExpediente());
			consultaTramiteInteveBean.setFechaPresentacion(new Fecha(traficoInteveVO.getFechaPresentacion()));
			consultaTramiteInteveBean.setDescContrato(traficoInteveVO.getContrato().getColegiado().getNumColegiado() + " - " + traficoInteveVO.getContrato().getVia());
			if (traficoInteveVO.getTramitesInteves() != null && !traficoInteveVO.getTramitesInteves().isEmpty()) {
				for (TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO : traficoInteveVO.getTramitesInteves()) {
					if (tramiteTraficoSolInteveVO.getMatricula() != null && !tramiteTraficoSolInteveVO.getMatricula().isEmpty()) {
						consultaTramiteInteveBean.addMatricula(tramiteTraficoSolInteveVO.getMatricula());
					}
					if (tramiteTraficoSolInteveVO.getBastidor() != null && !tramiteTraficoSolInteveVO.getBastidor().isEmpty()) {
						consultaTramiteInteveBean.addBastidor(tramiteTraficoSolInteveVO.getBastidor());
					}
					if (tramiteTraficoSolInteveVO.getNive() != null && !tramiteTraficoSolInteveVO.getNive().isEmpty()) {
						consultaTramiteInteveBean.addNive(tramiteTraficoSolInteveVO.getNive());
					}
				}
			}
			listaBean.add(consultaTramiteInteveBean);
		}
		return listaBean;
	}

	@Override
	public void borrarZip(File fichero) {
		try {
			servicioTramiteInteve.borrarZip(fichero);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de borrar el ZIP generado, error: ", e);
		}
	}

	@Override
	public ResultadoTramiteInteveBean descargarSolicitudes(String ids) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			if (ids != null && !ids.isEmpty()) {
				String[] numsExpedientes = ids.split(",");
				List<TramiteTraficoSolInteveVO> listaTramitesDescargar = new ArrayList<>();
				for (String numExpediente : numsExpedientes) {
					List<TramiteTraficoSolInteveVO> listaSolInteveExpediente = servicioTramiteInteve.getListaTramitesIntevePorExpediente(new BigDecimal(numExpediente));
					validarListaSolInteveDescarga(numExpediente, listaSolInteveExpediente, listaTramitesDescargar, resultado);
					if (resultado.getError()) {
						break;
					}
				}
				if (!resultado.getError()) {
					if (!listaTramitesDescargar.isEmpty()) {
						resultado = servicioTramiteInteve.obtenerInformesTramites(listaTramitesDescargar);
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se han encontrado informes aptos para descargar.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún trámite para poder descargar sus informes.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los informes de los expedientes seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los informes de los expedientes seleccionados.");
		}
		return resultado;
	}

	private void validarListaSolInteveDescarga(String numExpediente, List<TramiteTraficoSolInteveVO> listaSolInteveExpediente, List<TramiteTraficoSolInteveVO> listaTramitesDescargar,
			ResultadoTramiteInteveBean resultado) {
		if (listaSolInteveExpediente == null || listaSolInteveExpediente.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado informes para el expediente: " + numExpediente);
		} else {
			Boolean existeInteve = Boolean.FALSE;
			for (TramiteTraficoSolInteveVO traficoSolInteveVO : listaSolInteveExpediente) {
				if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(traficoSolInteveVO.getEstado())) {
					listaTramitesDescargar.add(traficoSolInteveVO);
					existeInteve = Boolean.TRUE;
				}
			}
			if (!existeInteve) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen informes Finalizados Telemáticamente para el expediente: " + numExpediente);
			}
		}
	}

	@Override
	public ResultadoTramiteInteveBean cambiarEstado(String ids, String estadoNuevo, BigDecimal idUsuario, Boolean tienePermisosAdmin) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			if (tienePermisosAdmin) {
				if (ids != null && !ids.isEmpty()) {
					String[] numsExpedientes = ids.split(",");
					for (String sNumExp : numsExpedientes) {
						try {
							ResultadoTramiteInteveBean resultCambEstdo = servicioTramiteInteve.cambioEstado(new BigDecimal(sNumExp), estadoNuevo, idUsuario.longValue());
							if (resultCambEstdo.getError()) {
								resultado.addResumenError(resultCambEstdo.getMensaje());
							} else {
								resultado.addResumenOk("Expediente: " + sNumExp + " cambiado el estado correctamente.");
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de cambiar el estado del tramite: " + sNumExp + ", error: ", e);
							resultado.addResumenError("Ha sucedido un error a la hora de cambiar el estado del trámite: " + sNumExp);
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe de seleccionar algún expediente para cambiarle el estado.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No tiene permisos para poder realizar esta acción.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado a los tramites seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado a los trámites seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean validarTramites(String numsExpedientes, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			if (numsExpedientes != null && !numsExpedientes.isEmpty()) {
				String[] sNumsExpedientes = numsExpedientes.split(",");
				for (String sNumExp : sNumsExpedientes) {
					try {
						ResultadoTramiteInteveBean resultValidar = servicioTramiteInteve.validarTramite(new BigDecimal(sNumExp), idUsuario);
						if (resultValidar.getError()) {
							resultado.addResumenError(resultValidar.getMensaje());
						} else {
							resultado.addResumenOk(resultValidar.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de validar el tramite: " + sNumExp + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de validar el trámite: " + sNumExp);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para validar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los tramites seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar los trámites seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean solicitarTramites(String numsExpedientes, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			if (numsExpedientes != null && !numsExpedientes.isEmpty()) {
				String[] sNumsExpedientes = numsExpedientes.split("_");
				for (String sNumExp : sNumsExpedientes) {
					try {
						ResultadoTramiteInteveBean resultValidar = servicioTramiteInteve.solicitarTramite(new BigDecimal(sNumExp), idUsuario);
						if (resultValidar.getError()) {
							resultado.addResumenError(resultValidar.getMensaje());
						} else {
							resultado.addResumenOk(resultValidar.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de solicitar el inteve para el tramite: " + sNumExp + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de solicitar el inteve para el trámite: " + sNumExp);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para solicitar su inteve.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los inteves para los tramites seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los inteves para los trámites seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean reiniciarEstados(String numsExpedientes, Long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			if (numsExpedientes != null && !numsExpedientes.isEmpty()) {
				String[] sNumsExpedientes = numsExpedientes.split(",");
				for (String sNumExp : sNumsExpedientes) {
					try {
						ResultadoTramiteInteveBean resultValidar = servicioTramiteInteve.reiniciarEstados(new BigDecimal(sNumExp), idUsuario);
						if (resultValidar.getError()) {
							resultado.addResumenError(resultValidar.getMensaje());
						} else {
							resultado.addResumenOk(resultValidar.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de reiniciar los estados del tramite: " + sNumExp + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de reiniciar los estados del trámite: " + sNumExp);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para reiniciar los estados.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reiniciar los estados de los tramites seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reiniciar los estados de los trámites seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoTramiteInteveBean eliminarTramites(String ids, long idUsuario) {
		ResultadoTramiteInteveBean resultado = new ResultadoTramiteInteveBean(Boolean.FALSE);
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] sNumsExpedientes = ids.split(",");
				for (String sNumExp : sNumsExpedientes) {
					try {
						ResultadoTramiteInteveBean resultEliminar = servicioTramiteInteve.eliminar(new BigDecimal(sNumExp), idUsuario);
						if (resultEliminar.getError()) {
							resultado.addResumenError(resultEliminar.getMensaje());
						} else {
							resultado.addResumenOk(resultEliminar.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de borrar el tramite: " + sNumExp + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de borrar el trámite: " + sNumExp);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para borrar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de borrar los tramites seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de borrar los trámites seleccionados.");
		}
		return resultado;
	}

}