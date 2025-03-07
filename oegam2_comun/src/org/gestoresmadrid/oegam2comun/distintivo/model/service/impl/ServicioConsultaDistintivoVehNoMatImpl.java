package org.gestoresmadrid.oegam2comun.distintivo.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioConsultaDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ConsultaVehNoMatrOegamBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.dto.VehiculoNoMatriculadoOegamDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaDistintivoVehNoMatImpl implements ServicioConsultaDistintivoVehNoMat {

	private static final long serialVersionUID = -8414555872121449767L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaDistintivoVehNoMatImpl.class);

	@Autowired
	ServicioDistintivoVehNoMat servicioDistintivoVehNoMat;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public List<ConsultaVehNoMatrOegamBean> convertirListaEnBeanPantallaConsulta(List<VehNoMatOegamVO> listaBBDD) {
		List<ConsultaVehNoMatrOegamBean> listaPrmDstv = new ArrayList<>();
		for (VehNoMatOegamVO vehNoMatOegamVO : listaBBDD) {
			ConsultaVehNoMatrOegamBean consultaVehNoMatrOegamBean = new ConsultaVehNoMatrOegamBean();
			consultaVehNoMatrOegamBean.setMatricula(vehNoMatOegamVO.getMatricula());
			consultaVehNoMatrOegamBean.setDescContrato(vehNoMatOegamVO.getNumColegiado() + " - " + vehNoMatOegamVO.getContrato().getVia());
			consultaVehNoMatrOegamBean.setTipoDistintivo(TipoDistintivo.convertirValor(vehNoMatOegamVO.getTipoDistintivo()));
			consultaVehNoMatrOegamBean.setEstadoDstv(EstadoPermisoDistintivoItv.convertirTexto(vehNoMatOegamVO.getEstadoSolicitud()));
			consultaVehNoMatrOegamBean.setEstadoPetImp(EstadoPermisoDistintivoItv.convertirTexto(vehNoMatOegamVO.getEstadoImpresion()));
			consultaVehNoMatrOegamBean.setId(vehNoMatOegamVO.getId());
			consultaVehNoMatrOegamBean.setFechaAlta(utilesFecha.formatoFecha("dd/MM/YYYY", vehNoMatOegamVO.getFechaAlta()));

			consultaVehNoMatrOegamBean.setPrimeraImpresion(vehNoMatOegamVO.getPrimeraImpresion() == null ? "N" : vehNoMatOegamVO.getPrimeraImpresion());

			listaPrmDstv.add(consultaVehNoMatrOegamBean);
		}
		return listaPrmDstv;
	}

	@Override
	public ResultadoDistintivoDgtBean solicitarDstv(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsVehsNoMat = codSeleccionados.split("-");
				for (String idVehiculoNotMat : idsVehsNoMat) {
					try {
						VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoOegamDto = new VehiculoNoMatriculadoOegamDto(Long.parseLong(idVehiculoNotMat));
						ResultadoDistintivoDgtBean resultSolicitar = servicioDistintivoVehNoMat.solicitarDstv(vehiculoNoMatriculadoOegamDto, idUsuario);
						if (resultSolicitar.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk(resultSolicitar.getMensaje());
						}
					} catch (Throwable e) {
						log.error("No se ha podido solicitar el distintivo para el vehiculo con id: " + idVehiculoNotMat + ", porque ha sucedido el siguiente error: ", e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("No se ha podido solicitar el distintivo para el vehiculo con id: " + idVehiculoNotMat);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar alguna matricula para poder solicitar su distintivo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los distintivos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los distintivos.");
		}
		return resultado;
	}

	@Override
	public ResultadoDistintivoDgtBean autorizarDstv(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsVehsNoMat = codSeleccionados.split("_");
				for (String idVehiculoNotMat : idsVehsNoMat) {
					try {
						VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoOegamDto = new VehiculoNoMatriculadoOegamDto(Long.parseLong(idVehiculoNotMat));
						ResultadoDistintivoDgtBean resultSolicitar = servicioDistintivoVehNoMat.autorizarImpresionDstv(vehiculoNoMatriculadoOegamDto, idUsuario);
						if (resultSolicitar.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk(resultSolicitar.getMensaje());
						}
					} catch (Throwable e) {
						log.error("No se ha podido autorizar el distintivo para el vehiculo con id: " + idVehiculoNotMat + ", porque ha sucedido el siguiente error: ", e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("No se ha podido autorizar el distintivo para el vehiculo con id: " + idVehiculoNotMat);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar alguna matricula para poder solicitar su distintivo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los distintivos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los distintivos.");
		}
		return resultado;
	}

	@Override
	public ResultadoDistintivoDgtBean solictarImpresionDstv(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsVehsNotMat = codSeleccionados.split("_");
				for (String idVehiculoNotMat : idsVehsNotMat) {
					try {
						VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoOegamDto = new VehiculoNoMatriculadoOegamDto(Long.parseLong(idVehiculoNotMat));
						ResultadoDistintivoDgtBean resultSol = servicioDistintivoVehNoMat.solicitarImpresionDstv(vehiculoNoMatriculadoOegamDto, idUsuario);
						if (resultSol.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSol.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk(resultSol.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de solicitar el distintivo para el id: " + idVehiculoNotMat + ", error: ", e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de solicitar el distintivo para el id: " + idVehiculoNotMat);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar alguna matricula para generar su distintivo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los distintivos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los distintivos seleccionados.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean generarDemandaDistintivos(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsVehNoMatOegam = codSeleccionados.split("_");
				resultado = servicioDistintivoVehNoMat.generarDemandaDistintivos(idsVehNoMatOegam, idUsuario);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar alguna matricula para poder generar sus distintivos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los distintivos de las matriculas seleccionadas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar los distintivos de las matriculas seleccionadas.");
		}
		return resultado;
	}

	@Override
	public ResultadoDistintivoDgtBean revertir(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] sIdVehNotMatOegam = codSeleccionados.split("_");
				for (String idVehNotMatOegam : sIdVehNotMatOegam) {
					try {
						ResultadoDistintivoDgtBean resultRevertir = servicioDistintivoVehNoMat.revertirDistintivo(Long.parseLong(idVehNotMatOegam), idUsuario);
						if (resultRevertir.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultRevertir.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk(resultRevertir.getMensaje());
						}
					} catch (Throwable e) {
						log.error("No se ha podido revertir el distintivo con id: " + idVehNotMatOegam + ", porque ha sucedido el siguiente error: ", e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("No se ha podido revertir el distintivo con id: " + idVehNotMatOegam);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder revertir su distintivo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de revertir los distintivos de los trámites en bloque, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir los distintivos de los trámites.");
		}
		return resultado;
	}

	@Override
	public ResultadoDistintivoDgtBean cambiarEstadoDstv(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsVehNotMatOegam = codSeleccionados.split("-");
				for (String idVehNotMat : idsVehNotMatOegam) {
					try {
						ResultadoDistintivoDgtBean resultCambEstado = servicioDistintivoVehNoMat.cambiarEstadoDstv(Long.parseLong(idVehNotMat), estadoNuevo, idUsuario);
						if (resultCambEstado.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultCambEstado.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk(resultCambEstado.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de cambiar el estado al vehiculo con id: " + idVehNotMat + ", error: ", e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de cambiar el estado al vehiculo con id: " + idVehNotMat);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder cambiar su estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la solicitud de los permisos o distintivos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de la solicitud de los permisos o distintivos.");
		}
		return resultado;
	}

}