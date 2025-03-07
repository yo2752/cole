package org.gestoresmadrid.oegam2comun.proceso.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.cola.enumerados.EstadoCola;
import org.gestoresmadrid.core.cola.model.dao.ColaDao;
import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ConsultaGestionColaBean;
import org.gestoresmadrid.oegam2comun.cola.view.bean.SolicitudesColaBean;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesoSolicitud;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioProcesoSolicitudImpl implements ServicioProcesoSolicitud {

	private static final long serialVersionUID = 1514288372933786187L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioProcesoSolicitudImpl.class);

	@Autowired
	ColaDao colaDao;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Override
	@Transactional
	public ResultBean eliminarSolicitudes(String idEnvio) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (idEnvio != null && !idEnvio.isEmpty()) {
				ColaVO colaBBDD = colaDao.getCola(Long.parseLong(idEnvio));
				if (colaBBDD != null) {
					if (!EstadoCola.EJECUTANDO.getValorEnum().equals(colaBBDD.getEstado())) {
						colaDao.borrar(colaBBDD);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La cola con id.Envio: " + idEnvio + ", no se pude eliminar porque se encuentra actualmente en ejecucion.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se pude eliminar la cola porque no existe para el id.Envio: " + idEnvio);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se pude eliminar la cola porque no se ha indicado su id.Envio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar la solicitud con id.envio: " + idEnvio + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar la solicitud con id.Envio: " + idEnvio);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean finalizarErrorServicio(String idEnvio) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (idEnvio != null && !idEnvio.isEmpty()) {
				ColaVO colaBBDD = colaDao.getCola(Long.parseLong(idEnvio));
				if (colaBBDD != null) {
					if (!EstadoCola.EJECUTANDO.getValorEnum().equals(colaBBDD.getEstado())) {
						colaBBDD.setEstado(new BigDecimal(EstadoCola.ERROR_SERVICIO.getValorEnum()));
						colaDao.actualizar(colaBBDD);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La cola con id.Envio: " + idEnvio + ", no se pude finalizar con error servicio porque se encuentra actualmente en ejecucion.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se pude finalizar con error servicio la cola porque no existe para el id.Envio: " + idEnvio);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se pude finalizar con error servicio la cola porque no se ha indicado su id.Envio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de finalizar con error servicio la cola con id.Envio: " + idEnvio + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de finalizar con error servicio la cola con id.Envio: " + idEnvio);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean finalizarError(String idEnvio) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (idEnvio != null && !idEnvio.isEmpty()) {
				ColaVO colaBBDD = colaDao.getCola(Long.parseLong(idEnvio));
				if (colaBBDD != null) {
					if (!EstadoCola.EJECUTANDO.getValorEnum().equals(colaBBDD.getEstado())) {
						colaDao.borrar(colaBBDD);
						if (colaBBDD.getTipoTramite() != null && "T".equals(colaBBDD.getTipoTramite().substring(0, 1))) {
							servicioTramiteTrafico.cambiarEstadoConGestionCreditos(colaBBDD.getIdTramite().toString(), EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum(), colaBBDD
									.getIdUsuario());
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La cola con id.Envio: " + idEnvio + ", no se pude finalizar con error porque se encuentra actualmente en ejecucion.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se pude finalizar con error la cola porque no existe para el id.Envio: " + idEnvio);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se pude finalizar con error la cola porque no se ha indicado su id.Envio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de finalizar con error servicio la cola con id.Envio: " + idEnvio + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de finalizar con error servicio la cola con id.Envio: " + idEnvio);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean reactivarSolicitud(String idEnvio) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (idEnvio != null && !idEnvio.isEmpty()) {
				ColaVO colaBBDD = colaDao.getCola(Long.parseLong(idEnvio));
				if (colaBBDD != null) {
					if (EstadoCola.ERROR_SERVICIO.getValorEnum().equals(colaBBDD.getEstado().toString())) {
						colaBBDD.setEstado(new BigDecimal(EstadoCola.PENDIENTES_ENVIO.getValorEnum()));
						colaDao.actualizar(colaBBDD);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La cola con id.Envio: " + idEnvio + ", no se pude reactivar porque no se encuentra en error servicio.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se pude reactivar la cola porque no existe para el id.Envio: " + idEnvio);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se pude reactivar la cola porque no se ha indicado su id.Envio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reactivar la cola con id.Envio: " + idEnvio + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reactivar la cola con id.Envio: " + idEnvio);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean establecerMaxPrioridad(String idEnvio) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (idEnvio != null && !idEnvio.isEmpty()) {
				ColaVO colaBBDD = colaDao.getCola(Long.parseLong(idEnvio));
				if (colaBBDD != null) {
					if (!EstadoCola.EJECUTANDO.getValorEnum().equals(colaBBDD.getEstado())) {
						if (EstadoCola.PENDIENTES_ENVIO.getValorEnum().equals(colaBBDD.getEstado().toString())) {
							String[] namedParemeters = { "idEnvio" };
							Long[] namedValues = { Long.parseLong(idEnvio) };
							int updates = colaDao.executeNamedQuery(ColaVO.ESTABLECER_MAX_PRIORIDAD_NUEVA, namedParemeters, namedValues);
							if (updates > 1) {
								log.error("Se deberia haber actualizado una unica columna y se han actualizado " + updates + ". Se realiza rollback");
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("Ha sucedido un error a la hora de establecer la máxima prioridad para la cola con id.Envio: " + idEnvio);
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							} else if (updates == 0) {
								log.error("Se deberia haber actualizado una unica columna y no se han actualizado ninguna");
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("Ha sucedido un error a la hora de establecer la máxima prioridad para la cola con id.Envio: " + idEnvio);
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("La cola con id.Envio: " + idEnvio + ", no se pude modificar su prioridad porque se no se encuentra 'Pendiente de Envio'.");
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La cola con id.Envio: " + idEnvio + ", no se pude modificar su prioridad porque se encuentra actualmente en ejecucion.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se pude establecer la máxima prioridad para la cola porque no existe para el id.Envio: " + idEnvio);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se pude establecer la máxima prioridad para la cola porque no se ha indicado su id.Envio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de establecer la máxima prioridad para la cola con id.Envio: " + idEnvio + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de establecer la máxima prioridad para la cola con id.Envio: " + idEnvio);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SolicitudesColaBean> getListaSolcitudesCola(ConsultaGestionColaBean consultaGestionColaBean) {
		try {
			List<SolicitudesColaBean> listaSolicitudesColaBean = null;
			List<ColaVO> listaSolicitudesBBDD = colaDao.getSolicitudesCola(consultaGestionColaBean.getNumExpediente(), consultaGestionColaBean.getProceso(), consultaGestionColaBean.getTipoTramite(),
					consultaGestionColaBean.getEstado(), consultaGestionColaBean.getCola(), consultaGestionColaBean.getMatricula(), consultaGestionColaBean.getBastidor(), consultaGestionColaBean
							.getFecha(), null);
			if (listaSolicitudesBBDD != null && !listaSolicitudesBBDD.isEmpty()) {
				if (StringUtils.isNotBlank(consultaGestionColaBean.getNumColegiado())) {
					listaSolicitudesColaBean = new ArrayList<SolicitudesColaBean>();
					for (ColaVO colaBBDDVO : listaSolicitudesBBDD) {
						if (colaBBDDVO.getIdTramite() != null && colaBBDDVO.getIdTramite().toString().length() >= consultaGestionColaBean.getNumColegiado().length()) {
							String numColExpediente = colaBBDDVO.getIdTramite().toString().substring(0, consultaGestionColaBean.getNumColegiado().length());
							if (numColExpediente.equals(consultaGestionColaBean.getNumColegiado())) {
								listaSolicitudesColaBean.add(conversor.transform(colaBBDDVO, SolicitudesColaBean.class));
							}
						}
					}
				} else {
					listaSolicitudesColaBean = conversor.transform(listaSolicitudesBBDD, SolicitudesColaBean.class);
				}
				return listaSolicitudesColaBean;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de solicitudes en cola, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer buscarPeticionesPendientesMonitorizacion(String proceso) {
		try {
			if (proceso != null && !proceso.isEmpty()) {
				String[] numHost = rellenarHostProcesos();
				return colaDao.recuperarNumPendientesPorProceso(proceso, numHost, utilesFecha.getFechaFracionadaActual());
			} else {
				log.error("No se pueden obtener el numero de peticiones pendientes porque el proceso viene vacio");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el numero de peticiones pendientes, error: ", e);
		}
		return null;
	}

	private String[] rellenarHostProcesos() {
		String[] numHost = new String[3];
		// numHost[0] = gestorPropiedades.valorPropertie(ConstantesProperties.PROPIEDAD_NOMBRE_HOST_SOLICITUD);
		// numHost[1] = gestorPropiedades.valorPropertie(ConstantesProperties.PROPIEDAD_NOMBRE_HOST_SOLICITUD_2);
		// numHost[2] = gestorPropiedades.valorPropertie(ConstantesProperties.PROPIEDAD_NOMBRE_HOST_SOLICITUD_3);
		return numHost;
	}

	@Override
	@Transactional(readOnly = true)
	public ColaVO tomarSolicitud(String proceso, String cola, String nodo) {
		try {
			return colaDao.getColaSolicitudProceso(proceso, cola, nodo);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la cola para el proceso: " + proceso + ", y el hilo: " + cola + ", error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ColaVO tomarSolicitudPorIdEnvio(Long idEnvio) {
		try {
			return colaDao.getCola(idEnvio);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la cola con idEnvio: " + idEnvio + ", error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void actualizarSolicitud(ColaVO colaVO) {
		try {
			if (colaVO != null) {
				colaDao.actualizar(colaVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la solicitud, error: ", e);
		}
	}

	@Override
	@Transactional
	public void borrarCola(ColaVO colaVO) {
		try {
			if (colaVO != null) {
				colaDao.borrar(colaVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar la solicitud, error: ", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> solicitudesAgrupadasPorHilos(String proceso) {
		return colaDao.solicitudesAgrupadasPorHilos(proceso);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> solicitudesAgrupadasPorHilos() {
		return colaDao.solicitudesAgrupadasPorHilos();
	}

}
