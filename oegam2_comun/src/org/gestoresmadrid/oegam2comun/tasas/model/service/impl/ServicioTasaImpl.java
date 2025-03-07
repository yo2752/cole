package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.EstadoTasaBloqueo;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoAlmacenTasa;
import org.gestoresmadrid.core.tasas.model.dao.TasaBajaDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaCargadaDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaCtitDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaDuplicadoDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaInteveDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaMatwDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaPermInternDao;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.core.tasas.model.enumeration.TipoTasaDGT;
import org.gestoresmadrid.core.tasas.model.vo.TasaBajaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaCargadaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaCtitVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaDuplicadoVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaInteveVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaMatwVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaPegatinaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaPermInternVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioEvolucionTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCambioServicioBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioPersistenciaTasas;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioPersistenciaTasasPegatinas;
import org.gestoresmadrid.oegamComun.tasa.view.bean.ResultadoTasaBean;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTraficoDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTasaImpl implements ServicioTasa {

	private static final long serialVersionUID = 8712761267363546093L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTasaImpl.class);

	@Autowired
	TasaDao tasaDao;
	
	@Autowired
	TasaCtitDao tasaCtitDao;
	
	@Autowired
	TasaMatwDao tasaMatwDao;
	
	@Autowired
	TasaBajaDao tasaBajaDao;
	
	@Autowired
	TasaDuplicadoDao tasaDuplicadoDao;
	
	@Autowired
	TasaPermInternDao tasaPermInternDao;
	
	@Autowired
	TasaInteveDao tasaInteveDao;

	@Autowired
	TasaCargadaDao tasaCargadaDao;

	@Autowired
	ServicioEvolucionTasa servicioEvolucionTasa;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioPersistenciaTasasPegatinas servicioPersistenciaTasasPegatinas;
	
	@Autowired
	ServicioPersistenciaTasas servicioPersistenciaTasas;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	Conversor conversor;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public TasaDto getTasaExpediente(String codigoTasa, BigDecimal numExpediente, String tipoTasa) {
		TasaVO tasaVO = null;
		try {
			tasaVO = tasaDao.getTasa(null, numExpediente, tipoTasa);
			if (tasaVO != null) {
				return conversor.transform(tasaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener la tasa para el trámite: " + numExpediente, e, numExpediente.toString());
		} finally {
			if (tasaVO != null) {
				tasaDao.evict(tasaVO);
			}
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public TasaDto getTasaExpedienteDuplicados(String codigoTasa, BigDecimal numExpediente, String tipoTasa) {
		TasaVO tasaVO = null;
		try {
			tasaVO = tasaDao.getTasa(codigoTasa, numExpediente, tipoTasa);
			if (tasaVO != null) {
				return conversor.transform(tasaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener la tasa para el trámite: " + numExpediente, e, numExpediente.toString());
		} finally {
			if (tasaVO != null) {
				tasaDao.evict(tasaVO);
			}
		}
		return null;
	}
	
	@Override
	@Transactional
	public TasaDto getTasaCodigoTasa(String codigoTasa) {
		try {
			TasaVO tasaVO = tasaDao.getTasa(codigoTasa, null, null);
			if (tasaVO != null) {
				TasaDto tasaDto = conversor.transform(tasaVO, TasaDto.class);
				if (tasaVO.getNumExpediente() != null) {
					TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(tasaVO.getNumExpediente(), false);
					if (tramiteTrafico != null) {
						tasaDto.setTramiteTrafico(conversor.transform(tramiteTrafico, TramiteTraficoDto.class));
					}
				}
				return tasaDto;
			}
		} catch (Exception e) {
			log.error("Error al obtener la tasa con codigo: " + codigoTasa, e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public TasaDto getTasaPegatinaCodigoTasa(String codigoTasa) {
		try {
			TasaPegatinaVO tasaPegatinaVO = servicioPersistenciaTasasPegatinas.getTasaVO(codigoTasa);
			if (tasaPegatinaVO != null) {
				return conversor.transform(tasaPegatinaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener la tasa con codigo: " + codigoTasa, e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TasaDto> getListaTasasPegatinasPorCodigos(List<String> listaTasas) {
		try {
			List<TasaPegatinaVO> lista = servicioPersistenciaTasasPegatinas.getListaTasasPorCodigos(listaTasas);
			if (lista != null && !lista.isEmpty()) {
				return conversor.transform(lista, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener la lista con las tasas de pegatinas");
		}
		return null;
	}

	@Override
	@Transactional
	public String eliminarTasaPegatina(String codigoTasa) {
		try {
			return servicioPersistenciaTasasPegatinas.eliminar(codigoTasa);
		} catch (TransactionalException e) {
			return e.getMessage();
		}
	}

	@Override
	public ResultBean cambiarFormato(String codigosTasaSeleccion, Integer formato, BigDecimal idUsuario) {
		ResultBean respuesta = new ResultBean(Boolean.FALSE);
		try {
			String[] codigosTasa = codigosTasaSeleccion.split("-");
			if (FormatoTasa.ELECTRONICO.getCodigo() == formato) {
				for (String codigoTasa : codigosTasa) {
					ResultBean respuestaCF = cambiarAPegatina(codigoTasa, idUsuario);
					if (respuestaCF != null && respuestaCF.getError()) {
						respuesta.addMensajeALista(respuestaCF.getMensaje());
					}
				}
			} else if (FormatoTasa.PEGATINA.getCodigo() == formato) {
				for (String codigoTasa : codigosTasa) {
					ResultBean respuestaCF = cambiarAElectronico(codigoTasa, idUsuario);
					if (respuestaCF != null && respuestaCF.getError()) {
						respuesta.addMensajeALista(respuestaCF.getMensaje());
					}
				}
			} else {
				respuesta.setError(Boolean.TRUE);
				respuesta.addMensajeALista("No tiene un formato correcto.");

			}
		} catch (Exception e) {
			respuesta.setError(Boolean.TRUE);
			respuesta.addMensajeALista("Error al cambiar el formato de la tasa.");
			log.error("Error al cambiar el formato de la tasa", e);
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ResultBean bloquearTasa(String codigosTasaSeleccion, String motivo, BigDecimal idUsuario) {
		ResultBean respuesta = new ResultBean(Boolean.FALSE);
		try {
			String[] codigosTasa = codigosTasaSeleccion.split("-");
			for (String codigoTasa : codigosTasa) {
				ResultBean respuestaCF = bloquear(codigoTasa, motivo, idUsuario);
				if (respuestaCF != null && respuestaCF.getError()) {
					respuesta.addMensajeALista(respuestaCF.getMensaje());
				}
			}
		} catch (Exception e) {
			respuesta.setError(Boolean.TRUE);
			respuesta.addMensajeALista("Error al bloquear las tasas.");
			log.error("Error al bloquear las tasas", e);
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ResultBean desbloquearTasa(String codigosTasaSeleccion, BigDecimal idUsuario) {
		ResultBean respuesta = new ResultBean(Boolean.FALSE);
		try {
			String[] codigosTasa = codigosTasaSeleccion.split("-");
			for (String codigoTasa : codigosTasa) {
				ResultBean respuestaCF = desbloquear(codigoTasa, idUsuario);
				if (respuestaCF != null && respuestaCF.getError()) {
					respuesta.addMensajeALista(respuestaCF.getMensaje());
				}
			}
		} catch (Exception e) {
			respuesta.setError(Boolean.TRUE);
			respuesta.addMensajeALista("Error al desbloquear las tasas.");
			log.error("Error al desbloquear las tasas", e);
		}
		return respuesta;
	}

	private ResultBean bloquear(String codigoTasa, String motivo, BigDecimal idUsuario) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		try {
			TasaVO tasa = getTasaVO(codigoTasa);
			if (tasa != null) {
				if (StringUtils.isBlank(tasa.getBloqueada()) || EstadoTasaBloqueo.DESBLOQUEADA.getValorEnum().equals(tasa.getBloqueada())) {
					if (tasa.getNumExpediente() != null) {
						TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(tasa.getNumExpediente(), false);
						if (tramiteTrafico != null) {
							if (tramiteTrafico.getEstado() != null && new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()).equals(tramiteTrafico.getEstado())) {
								ResultBean resultadoDes = servicioTramiteTrafico.desasignarTasaTramites(tramiteTrafico, codigoTasa);
								if (resultadoDes != null && !resultadoDes.getError()) {
									tasa.setNumExpediente(null);
									tasa.setFechaAsignacion(null);
									tasa.setBloqueada(EstadoTasaBloqueo.BLOQUEADA.getValorEnum());
									try {
										tasaDao.guardar(tasa);
									} catch (Exception e) {
										result.setError(Boolean.TRUE);
										result.addMensajeALista("Error al bloquear la tasa " + codigoTasa + ".");
										log.error("Error al bloquear la tasa " + codigoTasa, e);
									}
									if (result != null && !result.getError()) {
										servicioEvolucionTasa.insertarEvolucionTasa(codigoTasa, null, ServicioEvolucionTasa.BLOQUEO, motivo, new Date(), idUsuario);
									}
								} else {
									result.setError(Boolean.TRUE);
									result.setMensaje("La tasa " + codigoTasa + " no puede desagsinarse del trámite.");
								}
							} else {
								result.setError(Boolean.TRUE);
								result.setMensaje("La tasa " + codigoTasa + " que intenta bloquear esta asiganada a un trámite en estado no 'Iniciado'.");
							}
						} else {
							result.setError(Boolean.TRUE);
							result.setMensaje("La tasa " + codigoTasa + " no encuentra el trámite asignado.");
						}
					} else {
						tasa.setBloqueada(EstadoTasaBloqueo.BLOQUEADA.getValorEnum());
						try {
							tasaDao.guardar(tasa);
						} catch (Exception e) {
							result.setError(Boolean.TRUE);
							result.addMensajeALista("Error al bloquear la tasa " + codigoTasa + ".");
							log.error("Error al bloquear la tasa " + codigoTasa, e);
						}
						if (result != null && !result.getError()) {
							servicioEvolucionTasa.insertarEvolucionTasa(codigoTasa, null, ServicioEvolucionTasa.BLOQUEO, motivo, new Date(), idUsuario);
						}
					}
				} else {
					result.setError(Boolean.TRUE);
					result.setMensaje("La tasa " + codigoTasa + " ya está bloqueada.");
				}
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("No se ha encontrado la tasa " + codigoTasa + ".");
			}
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error en el bloqueo de la tasa " + codigoTasa + ".");
			log.error("Error en el bloqueo de la tasa " + codigoTasa, e);
		}
		return result;
	}

	private ResultBean desbloquear(String codigoTasa, BigDecimal idUsuario) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		try {
			TasaVO tasa = getTasaVO(codigoTasa);
			if (tasa != null) {
				if (EstadoTasaBloqueo.BLOQUEADA.getValorEnum().equals(tasa.getBloqueada())) {
					tasa.setBloqueada(EstadoTasaBloqueo.DESBLOQUEADA.getValorEnum());
					try {
						tasaDao.actualizar(tasa);
					} catch (Exception e) {
						result.setError(Boolean.TRUE);
						result.addMensajeALista("Error al desbloquear la tasa " + codigoTasa + ".");
						log.error("Error al desbloquear la tasa " + codigoTasa, e);
					}
					if (result != null && !result.getError()) {
						servicioEvolucionTasa.insertarEvolucionTasa(codigoTasa, null, ServicioEvolucionTasa.DESBLOQUEO, null, new Date(), idUsuario);
					}

				} else {
					result.setError(Boolean.TRUE);
					result.setMensaje("La tasa " + codigoTasa + " ya está desbloqueada.");
				}
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("No se ha encontrado la tasa " + codigoTasa + ".");
			}
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error en el desbloqueo de la tasa " + codigoTasa + ".");
			log.error("Error en el desbloqueo de la tasa " + codigoTasa, e);
		}
		return result;
	}

	private ResultBean cambiarAPegatina(String codigoTasa, BigDecimal idUsuario) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		try {
			TasaVO tasa = servicioPersistenciaTasas.getTasa(codigoTasa, null, null);
			if (tasa != null) {
				if (tasa.getNumExpediente() == null) {
					TasaPegatinaVO tasaPegatina = new TasaPegatinaVO();
					tasaPegatina.setCodigoTasa(tasa.getCodigoTasa());
					tasaPegatina.setContrato(tasa.getContrato());
					tasaPegatina.setFechaAlta(new Date());
					tasaPegatina.setFechaFinVigencia(tasa.getFechaFinVigencia());
					tasaPegatina.setPrecio(tasa.getPrecio());
					tasaPegatina.setTipoTasa(tasa.getTipoTasa());
					tasaPegatina.setUsuario(tasa.getUsuario());
					tasaPegatina.setImportadoIcogam(BigDecimal.ZERO);
					ResultadoBean resultG = servicioPersistenciaTasasPegatinas.cambiarFormatoATasaPegatina(tasaPegatina, idUsuario.longValue());
					if(resultG.getError()) {
						result.setError(Boolean.TRUE);
						result.setMensaje(resultG.getMensaje());
					}
				} else {
					result.setError(Boolean.TRUE);
					result.setMensaje("La tasa " + codigoTasa + " está asignada, por lo que no puede cambiar de formato.");
				}
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("No se ha encontrado la tasa " + codigoTasa + ".");
			}
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error en el cambio de formato de la tasa " + codigoTasa + ".");
			log.error("Error en el cambio de formato de la tasa " + codigoTasa, e);
		}
		return result;
	}

	private ResultBean cambiarAElectronico(String codigoTasa, BigDecimal idUsuario) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		try {
			TasaPegatinaVO tasaPegatina = servicioPersistenciaTasasPegatinas.getTasaVO(codigoTasa);
			if (tasaPegatina != null) {
				TasaVO tasa = new TasaVO();
				tasa.setCodigoTasa(tasaPegatina.getCodigoTasa());
				tasa.setContrato(tasaPegatina.getContrato());
				tasa.setFechaAlta(new Date());
				tasa.setFechaFinVigencia(tasaPegatina.getFechaFinVigencia());
				tasa.setImportadoIcogam(BigDecimal.ZERO);
				tasa.setPrecio(tasaPegatina.getPrecio());
				tasa.setTipoTasa(tasaPegatina.getTipoTasa());
				tasa.setUsuario(tasaPegatina.getUsuario());
				ResultadoBean resultCbTasa = servicioPersistenciaTasas.cambiarFormatoATasaElectronica(tasa, idUsuario.longValue());
				if(resultCbTasa.getError()) {
					result.setError(Boolean.TRUE);
					result.setMensaje(resultCbTasa.getMensaje());
				}
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("No se ha encontrado la tasa de pegatina " + codigoTasa + ".");
			}
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error en el cambio de formato de la tasa pegatina " + codigoTasa + ".");
			log.error("Error en el cambio de formato de la tasa pegatina " + codigoTasa, e);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean desasignarTasaExpediente(String codigoTasa, BigDecimal numExpediente, String tipoTasa) {
		ResultBean respuesta = new ResultBean();
		TasaDto tasa = null;
		if (numExpediente != null) {
			tasa = getTasaExpediente(codigoTasa, numExpediente, tipoTasa);
			if (tasa != null && !tasa.getCodigoTasa().equals(codigoTasa)) {
				ResultBean result = desasignarTasa(tasa);
				if (result.getError()) {
					respuesta.setError(true);
					respuesta.addMensajeALista("No se puede desasignar tasa");
				}
			}
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ResultBean desasignarTasaExpedienteDuplicados(String codigoTasaNuevo, String codigoTasaAntiguo, BigDecimal numExpediente, String tipoTasa) {
		ResultBean respuesta = new ResultBean();
		TasaDto tasa = null;
		if (numExpediente != null) {
			tasa = getTasaExpedienteDuplicados(codigoTasaAntiguo, numExpediente, tipoTasa);
			if (tasa != null && !tasa.getCodigoTasa().equals(codigoTasaNuevo)) {
				ResultBean result = desasignarTasa(tasa);
				if (result.getError()) {
					respuesta.setError(true);
					respuesta.addMensajeALista("No se puede desasignar tasa");
				}
			}
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ResultBean desasignarTasa(TasaDto tasaDto) {
		ResultBean resultado = new ResultBean();
		TasaVO tasa = null;
		try {
			tasa = tasaDao.getTasa(tasaDto.getCodigoTasa(), null, null);
		} catch (Exception e) {
			log.error("Error al recuperar la tasa para el trámite: " + tasaDto.getNumExpediente(), e, tasa.getNumExpediente().toString());
		} finally {
			if (tasa != null) {
				tasaDao.evict(tasa);
			}
		}
		try {
			if (tasa.getNumExpediente() != null && tasa.getNumExpediente().equals(tasaDto.getNumExpediente())) {
				log.debug("Tasa desagsinada para el trámite: " + tasaDto.getNumExpediente());
				tasa.setNumExpediente(null);
				tasa.setFechaAsignacion(null);
				// Guardamos la evolución de la tasa antes de actualiar
				servicioEvolucionTasa.insertarEvolucionTasa(tasa, ServicioEvolucionTasa.DESASIGNAR);
				tasa = (TasaVO) tasaDao.guardarOActualizar(tasa);
			}
			if (tasa != null && tasa.getNumExpediente() != null && tasa.getNumExpediente().equals(tasaDto.getNumExpediente())) {
				resultado.setError(false);
				resultado.addMensajeALista("No se ha podido desasignar la tasa");
			}
		} catch (Exception e) {
			log.error("Error al desagsinar la tasa para el trámite: " + tasaDto.getNumExpediente(), e, tasaDto.getNumExpediente().toString());
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean desasignarTasaInforme(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVO) {
		ResultBean resultado = new ResultBean();
		TasaVO tasa = null;
		try {
			tasa = tasaDao.getTasa(tramiteTrafSolInfoVO.getTasa().getCodigoTasa(), null, null);
		} catch (Exception e) {
			log.error("Error al recuperar la tasa para el trámite: " + tramiteTrafSolInfoVO.getId().getNumExpediente(), e, tramiteTrafSolInfoVO.getId().getNumExpediente().toString());
		} finally {
			if (tasa != null) {
				tasaDao.evict(tasa);
			}
		}
		try {
			if (tasa.getNumExpediente() != null) {
				TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(tasa.getNumExpediente(), false);
				if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(tramiteTrafico.getTipoTramite()) && tramiteTrafico.getNumExpediente().equals(tramiteTrafSolInfoVO.getId().getNumExpediente())) {
					log.debug("Tasa desagsinada para el trámite: " + tramiteTrafSolInfoVO.getId().getNumExpediente());
					tasa.setNumExpediente(null);
					tasa.setFechaAsignacion(null);
					// Guardamos la evolución de la tasa antes de actualiar
					servicioEvolucionTasa.insertarEvolucionTasa(tasa, ServicioEvolucionTasa.DESASIGNAR);
					tasa = (TasaVO) tasaDao.guardarOActualizar(tasa);
				}
			}
			if (tasa != null && tasa.getNumExpediente() != null && tasa.getNumExpediente().equals(tramiteTrafSolInfoVO.getId().getNumExpediente())) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("No se ha podido desasignar la tasa");
			}
		} catch (Exception e) {
			log.error("Error al desagsinar la tasa para el trámite: " + tramiteTrafSolInfoVO.getId().getNumExpediente(), e, tramiteTrafSolInfoVO.getId().getNumExpediente().toString());
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("No se ha podido desasignar la tasa");
		}
		return resultado;
	}

	@Override
	@Transactional
	public boolean sePuedeAsignarTasa(String codigoTasa, BigDecimal numExpediente) {
		TasaVO tasa = null;
		try {
			if (codigoTasa != null && !codigoTasa.equals("") && !codigoTasa.equals("-1")) {
				tasa = tasaDao.getTasa(codigoTasa, null, null);
				if (tasa == null) {
					return false;
				} else {
					if (tasa.getNumExpediente() == null) {
						return true;
					} else if (tasa.getNumExpediente() != null && !tasa.getNumExpediente().equals(numExpediente)) {
						return false;
					} else if (tasa.getNumExpediente() != null && tasa.getNumExpediente().equals(numExpediente)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en la función sePuedeAsignarTasa", e, numExpediente.toString());
		}
		return false;
	}

	@Override
	@Transactional
	public ResultBean asignarTasaExpediente(String codigoTasa, BigDecimal numExpediente) {
		ResultBean respuesta = new ResultBean();
		if (comprobarTasa(codigoTasa, numExpediente)) {
			ResultBean result = asignarTasa(codigoTasa, numExpediente);
			if (result.getError()) {
				respuesta.setError(true);
				respuesta.addMensajeALista("No se puede asignar tasa");
			}
		} else {
			respuesta.setError(true);
			respuesta.addMensajeALista("No se puede asignar tasa, la tasa no existe o en uso");
		}
		return respuesta;
	}

	@Override
	@Transactional
	public boolean comprobarTasa(String codigoTasa, BigDecimal numExpediente) {
		if (codigoTasa != null && !codigoTasa.isEmpty() && !"-1".equals(codigoTasa)) {
			if (sePuedeAsignarTasa(codigoTasa, numExpediente)) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	@Override
	public ResultBean asignarTasa(String codigoTasa, BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean();
		try {
			TasaVO tasa = tasaDao.getTasa(codigoTasa, null, null);
			if (tasa != null) {
				if (tasa.getNumExpediente() == null) {
					tasa.setNumExpediente(numExpediente);
					tasa.setFechaAsignacion(new Date());
					resultado.addMensajeALista("Tasa asignada al tramite con numero de expediente " +numExpediente);
					// Guardamos la evolución de la tasa antes de actualiar
					servicioEvolucionTasa.insertarEvolucionTasa(tasa, ServicioEvolucionTasa.ASIGNAR);
					tasa = (TasaVO) tasaDao.actualizar(tasa);

					if (tasa != null) {
						resultado.setError(false);
						log.debug("Tasa asignada al trámite: " + numExpediente);
					} else {
						resultado.setError(true);
						resultado.addMensajeALista("No se ha podido asignar la tasa");
					}
				} else if (!numExpediente.equals(tasa.getNumExpediente())) {
					resultado.setError(true);
					resultado.addMensajeALista("Tasa asignada a otro trámite");
				}
			} else {
				resultado.addMensajeALista("No existe la tasa");
			}
		} catch (Exception e) {
			resultado.setError(true);
			resultado.addMensajeALista("No se ha podido asignar la tasa");
			log.error("Error al asignar la tasa al trámite: " + numExpediente, e, numExpediente.toString());
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoCambioServicioBean asignarTasaExp(String codigoTasa, BigDecimal numExpediente, String tipoTasa) {
		ResultadoCambioServicioBean resultado = new ResultadoCambioServicioBean(Boolean.FALSE);
		try {
			TasaVO tasa = tasaDao.getTasa(codigoTasa, null, null);
			if (tasa != null) {
				if (tasa.getNumExpediente() == null) {
					tasa.setNumExpediente(numExpediente);
					tasa.setFechaAsignacion(new Date());
					// Guardamos la evolución de la tasa antes de actualiar
					servicioEvolucionTasa.insertarEvolucionTasa(tasa, ServicioEvolucionTasa.ASIGNAR);
					tasa = (TasaVO) tasaDao.actualizar(tasa);

					if (tasa != null) {
						resultado.setError(false);
						log.debug("Tasa asignada al trámite: " + numExpediente);
					} else {
						resultado.setError(true);
						resultado.addMensajeALista("No se ha podido asignar la tasa");
					}
				} else if (!numExpediente.equals(tasa.getNumExpediente())) {
					resultado.setError(true);
					resultado.addMensajeALista("Tasa asignada a otro trámite");
				}
			} else {
				resultado.addMensajeALista("No existe la tasa");
			}
		} catch (Exception e) {
			resultado.setError(true);
			resultado.addMensajeALista("No se ha podido asignar la tasa");
			log.error("Error al asignar la tasa al trámite: " + numExpediente, e, numExpediente.toString());
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean asignarMicroTasa(String codigoTasa, String numExpedientes, BigDecimal idUsuario, Date fecha) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			TasaVO tasa = tasaDao.getTasa(codigoTasa, null, null);
			if (tasa != null) {
				if (tasa.getNumExpediente() == null) {
					tasa.setNumExpediente(new BigDecimal(numExpedientes));
					tasa.setFechaAsignacion(fecha);
					tasaDao.actualizar(tasa);
					resultado = servicioEvolucionTasa.insertarEvolucionTasa(codigoTasa, new BigDecimal(numExpedientes), TipoActualizacion.AMT.getValorEnum(), null, fecha, idUsuario);
				} else {
					resultado.setError(true);
					resultado.addMensajeALista("No se ha podido asignar la tasa porque ya esta asignada a otro expediente.");
				}
			} else {
				resultado.addMensajeALista("No existe la tasa");
			}
		} catch (Exception e) {
			resultado.setError(true);
			resultado.addMensajeALista("No se ha podido asignar la tasa");
			log.error("Error al asignar la tasa al trámite, error: ", e, numExpedientes.toString());
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean desasignarMicroTasa(String codigoTasa, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (codigoTasa != null) {
				TasaVO tasaBBDD = getTasaVO(codigoTasa);
				if (tasaBBDD != null) {
					tasaBBDD.setFechaAsignacion(null);
					tasaBBDD.setNumExpediente(null);
					tasaDao.actualizar(tasaBBDD);
					resultado = servicioEvolucionTasa.insertarEvolucionTasa(codigoTasa, null, TipoActualizacion.DMT.getValorEnum(), null, new Date(), idUsuario);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La tasa con codigo: " + codigoTasa + " no se encuentra dada de alta en el sistema.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar una tasa para desasinar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de desasignar la tasa: " + codigoTasa + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de desasignar la tasa: " + codigoTasa);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean desasignarTasaDuplicar(String codigoTasa, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (codigoTasa != null) {
				TasaVO tasaBBDD = getTasaVO(codigoTasa);
				if (tasaBBDD != null) {
					tasaBBDD.setFechaAsignacion(null);
					tasaBBDD.setNumExpediente(null);
					tasaDao.actualizar(tasaBBDD);
					resultado = servicioEvolucionTasa.insertarEvolucionTasa(codigoTasa, null, TipoActualizacion.DDT.getValorEnum(), null, new Date(), idUsuario);
					resultado.setMensaje("Se ha desasignado la tasa: " + codigoTasa);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La tasa con codigo: " + codigoTasa + " no se encuentra dada de alta en el sistema.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar una tasa para desasinar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de desasignar la tasa: " + codigoTasa + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de desasignar la tasa: " + codigoTasa);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public TasaVO getTasaVO(String codigoTasa) {
		try {
			if (codigoTasa != null && !codigoTasa.isEmpty()) {
				return tasaDao.getTasa(codigoTasa, null, null);
			}
		} catch (Exception e) {
			log.error("Error al obtener la tasa con codigo: " + codigoTasa, e);
		}
		return null;
	}

	@Transactional
	@Override
	public List<TasaDto> obtenerTasasContrato(Long idContrato, String tipoTasa) {
		int maxResult = 20;
		try {
			String maxResultProp = gestorPropiedades.valorPropertie("maximo.resultados.tasa");
			if (StringUtils.isNotBlank(maxResultProp)) {
				maxResult = Integer.parseInt(maxResultProp);
			}

			List<TasaVO> listaVO = tasaDao.obtenerTasasContrato(idContrato, tipoTasa, maxResult);
			if (listaVO != null && listaVO.size() > 0 && !listaVO.isEmpty()) {
				return conversor.transform(listaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener las tasa para el contrato: " + idContrato, e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public List<TasaDto> obtenerTasasBajaContrato(Long idContrato, String tipoTasa) {
		int maxResult = 20;
		try {
			String maxResultProp = gestorPropiedades.valorPropertie("maximo.resultados.tasa");
			if (StringUtils.isNotBlank(maxResultProp)) {
				maxResult = Integer.parseInt(maxResultProp);
			}

			List<TasaBajaVO> listaVO = tasaBajaDao.obtenerTasasBajaContrato(idContrato, tipoTasa, maxResult);
			if (listaVO != null && listaVO.size() > 0 && !listaVO.isEmpty()) {
				return conversor.transform(listaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener las tasa para el contrato: " + idContrato, e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public List<TasaDto> obtenerTasasInteveContrato(Long idContrato, String tipoTasa) {
		int maxResult = 20;
		try {
			String maxResultProp = gestorPropiedades.valorPropertie("maximo.resultados.tasa");
			if (StringUtils.isNotBlank(maxResultProp)) {
				maxResult = Integer.parseInt(maxResultProp);
			}

			List<TasaInteveVO> listaVO = tasaInteveDao.obtenerTasasInteveContrato(idContrato, tipoTasa, maxResult);
			if (listaVO != null && listaVO.size() > 0 && !listaVO.isEmpty()) {
				return conversor.transform(listaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener las tasa para el contrato: " + idContrato, e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public List<TasaDto> obtenerTasasDuplicadoContrato(Long idContrato, String tipoTasa) {
		int maxResult = 20;
		try {
			String maxResultProp = gestorPropiedades.valorPropertie("maximo.resultados.tasa");
			if (StringUtils.isNotBlank(maxResultProp)) {
				maxResult = Integer.parseInt(maxResultProp);
			}

			List<TasaDuplicadoVO> listaVO = tasaDuplicadoDao.obtenerTasasDuplicadoContrato(idContrato, tipoTasa, maxResult);
			if (listaVO != null && listaVO.size() > 0 && !listaVO.isEmpty()) {
				return conversor.transform(listaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener las tasa para el contrato: " + idContrato, e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public List<TasaDto> obtenerTasasPermIntContrato(Long idContrato, String tipoTasa) {
		int maxResult = 20;
		try {
			String maxResultProp = gestorPropiedades.valorPropertie("maximo.resultados.tasa");
			if (StringUtils.isNotBlank(maxResultProp)) {
				maxResult = Integer.parseInt(maxResultProp);
			}

			List<TasaPermInternVO> listaVO = tasaPermInternDao.obtenerTasasPermIntContrato(idContrato, tipoTasa, maxResult);
			if (listaVO != null && listaVO.size() > 0 && !listaVO.isEmpty()) {
				return conversor.transform(listaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener las tasa para el contrato: " + idContrato, e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public List<TasaDto> obtenerTasasCtitContrato(Long idContrato, String tipoTasa) {
		int maxResult = 20;
		try {
			String maxResultProp = gestorPropiedades.valorPropertie("maximo.resultados.tasa");
			if (StringUtils.isNotBlank(maxResultProp)) {
				maxResult = Integer.parseInt(maxResultProp);
			}

			List<TasaCtitVO> listaVO = tasaCtitDao.obtenerTasasCtitContrato(idContrato, tipoTasa, maxResult);
			if (listaVO != null && listaVO.size() > 0 && !listaVO.isEmpty()) {
				return conversor.transform(listaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener las tasa para el contrato: " + idContrato, e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public List<TasaDto> obtenerTasasMatwContrato(Long idContrato, String tipoTasa) {
		int maxResult = 20;
		try {
			String maxResultProp = gestorPropiedades.valorPropertie("maximo.resultados.tasa");
			if (StringUtils.isNotBlank(maxResultProp)) {
				maxResult = Integer.parseInt(maxResultProp);
			}

			List<TasaMatwVO> listaVO = tasaMatwDao.obtenerTasasMatwContrato(idContrato, tipoTasa, maxResult);
			if (listaVO != null && listaVO.size() > 0 && !listaVO.isEmpty()) {
				return conversor.transform(listaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener las tasa para el contrato: " + idContrato, e);
		}
		return Collections.emptyList();
	}

	@Transactional
	@Override
	public List<TasaDto> obtenerTodasTasasContrato(Long idContrato, String tipoTasa) {
		try {
			List<TasaVO> listaVO = tasaDao.obtenerTasasContrato(idContrato, tipoTasa, 0);
			if (listaVO != null && listaVO.size() > 0 && !listaVO.isEmpty()) {
				return conversor.transform(listaVO, TasaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener las tasa para el contrato: " + idContrato, e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public TasaDto getDetalleTasaCargada(String codigoTasa, Integer formatoTasa) {
		TasaDto tasadto = null;
		TasaCargadaVO tasacargada = tasaCargadaDao.detalle(codigoTasa, formatoTasa);
		if (tasacargada != null) {
			tasadto = conversor.transform(tasacargada, TasaDto.class);
		}
		return tasadto;
	}

	@Override
	public ResultadoAtex5Bean getTasaAtex5(Long idContrato) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		int maxResult = 20;
		try {
			String maxResultProp = gestorPropiedades.valorPropertie("maximo.resultados.tasa");
			if (StringUtils.isNotBlank(maxResultProp)) {
				maxResult = Integer.parseInt(maxResultProp);
			}
			List<TasaVO> listaVO = tasaDao.obtenerTasasContrato(idContrato, TipoTasa.CuatroUno.getValorEnum(), maxResult);
			if (listaVO != null && !listaVO.isEmpty()) {
				resultado.setCodigoTasa(listaVO.get(0).getCodigoTasa());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El contrato no tiene tasas disponibles.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener las tasas del contrato, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener las tasas del contrato.");
		}
		return resultado;
	}

	@Override
	public ResultBean desasignarTasaInteve(String codigoTasa, BigDecimal numExpediente, String tipoTasa, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean();
		TasaVO tasaVO = null;
		try {
			TasaVO tasaBBDD = getTasaVO(codigoTasa);
			if (tasaBBDD != null) {
				tasaBBDD.setFechaAsignacion(null);
				tasaBBDD.setNumExpediente(null);
				tasaDao.actualizar(tasaBBDD);
				resultado = servicioEvolucionTasa.insertarEvolucionTasa(codigoTasa, null, TipoActualizacion.DTI.getValorEnum(), null, new Date(), idUsuario);
				resultado.setMensaje("Se ha desasignado la tasa: " + codigoTasa);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La tasa con codigo: " + codigoTasa + " no se encuentra dada de alta en el sistema.");
			}

		} catch (Exception e) {
			log.error("Error al obtener la tasa para el trámite: " + numExpediente, e, numExpediente.toString());
		} finally {
			if (tasaVO != null) {
				tasaDao.evict(tasaVO);
			}

		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultadoCambioServicioBean desasignarTasaExp(String codigoTasa, BigDecimal numExpediente, String tipoTasa) {
		ResultadoCambioServicioBean respuesta = new ResultadoCambioServicioBean(Boolean.FALSE);
		TasaDto tasa = null;
		if (numExpediente != null) {
			tasa = getTasaExpediente(codigoTasa, numExpediente, tipoTasa);
			if (tasa != null && !tasa.getCodigoTasa().equals(codigoTasa)) {
				ResultBean result = desasignarTasa(tasa);
				if (result.getError()) {
					respuesta.setError(Boolean.TRUE);
					respuesta.addMensajeALista("No se puede desasignar tasa");
				}
			}
		}
		return respuesta;
	}
	
	@Override
	public ResultBean desasignarTasaXml(String codigoTasa, BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean();
		TasaVO tasaVO = null;
		try {
			TasaVO tasaBBDD = getTasaVO(codigoTasa);
			if (tasaBBDD != null) {
				tasaBBDD.setFechaAsignacion(null);
				tasaBBDD.setNumExpediente(null);
				tasaDao.actualizar(tasaBBDD);
				resultado = servicioEvolucionTasa.insertarEvolucionTasa(codigoTasa, numExpediente, ServicioEvolucionTasa.DESASIGNAR, null, new Date(), idUsuario);
				resultado.addMensajeALista("Se ha desasignado la tasa: " + codigoTasa + " del expediente " + numExpediente);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("La tasa con codigo: " + codigoTasa + " no se encuentra dada de alta en el sistema.");
			}

		} catch (Exception e) {
			log.error("Error al obtener la tasa para el trámite: " + numExpediente, e, numExpediente.toString());
		} finally {
			if (tasaVO != null) {
				tasaDao.evict(tasaVO);
			}

		}

		return resultado;
	}

	@Override
	public ResultadoTasaBean cambiarAlmacen(String codigoTasa, String almacenNuevo, BigDecimal idUsuario) {
		ResultadoTasaBean resultado = new ResultadoTasaBean(Boolean.FALSE);
		try {
			String[] codigosTasa = codigoTasa.split("-");
				for (String codTasa : codigosTasa) {
					TasaVO tasaVO = servicioPersistenciaTasas.getTasa(codTasa, null, null);
					ResultadoTasaBean resultValida = validacionesCambioAlmacen(tasaVO,almacenNuevo);
					if(!resultValida.getError()) {
						if(tasaVO != null) {
							if(almacenNuevo != null && !TipoAlmacenTasa.SIN_ALMACEN.getValorEnum().equals(almacenNuevo)) {
								if(TipoAlmacenTasa.ALMACEN_MATW.getValorEnum().equals(almacenNuevo)) {
									gestionarAlmacenMatw(tasaVO,almacenNuevo);
								}
								else if(TipoAlmacenTasa.ALMACEN_CTIT.getValorEnum().equals(almacenNuevo)) {
									gestionarAlmacenCtit(tasaVO,almacenNuevo);
								}
								else if(TipoAlmacenTasa.ALMACEN_BAJA.getValorEnum().equals(almacenNuevo)) {
									gestionarAlmacenBaja(tasaVO,almacenNuevo);
								}
								else if(TipoAlmacenTasa.ALMACEN_DUPLICADO.getValorEnum().equals(almacenNuevo)) {
									gestionarAlmacenDuplicado(tasaVO,almacenNuevo);
								}
								else if(TipoAlmacenTasa.ALMACEN_INTEVE.getValorEnum().equals(almacenNuevo)) {
									gestionarAlmacenInteve(tasaVO,almacenNuevo);
								}
								else if(TipoAlmacenTasa.ALMACEN_PERMISO_INT.getValorEnum().equals(almacenNuevo)) {
									gestionarAlmacenPermisoInt(tasaVO,almacenNuevo);
								}
								resultado.addListaMensajeOk("La tasa " + tasaVO.getCodigoTasa() + " se ha almacenado en el almacén: " +almacenNuevo);
							}else {
								gestionarTasaSinAlmacen(tasaVO);
								resultado.addListaMensajeOk("Para la tasa " + tasaVO.getCodigoTasa() + " se le ha desvinculado el almacén.");
							}
						}else {
							resultado.setError(Boolean.TRUE);
							resultado.addListaMensajeError("No existe tasa en BBDD para cambiar de almacén.");
						}
					}else {
						resultado.setError(Boolean.TRUE);
						resultado.addListaMensajeError(resultValida.getMensaje());
					}
				}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al cambiar el almacén de la tasa.");
			log.error("Error al cambiar el almacén de la tasa", e);
		}
		return resultado;
	}
	
	private ResultadoTasaBean validacionesCambioAlmacen(TasaVO tasaVO, String almacenNuevo) {
		ResultadoTasaBean result = new ResultadoTasaBean (Boolean.FALSE);
		if(TipoAlmacenTasa.ALMACEN_MATW.getValorEnum().equals(almacenNuevo) && !TipoTasaDGT.UnoUno.getValorEnum().equals(tasaVO.getTipoTasa())
				&& !TipoTasaDGT.UnoDos.getValorEnum().equals(tasaVO.getTipoTasa())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El almacén " + almacenNuevo + " al que se quiere vincular la tasa " +tasaVO.getCodigoTasa()+ ", no es admitido para ese tipo de tasa.");
		}else if(TipoAlmacenTasa.ALMACEN_CTIT.getValorEnum().equals(almacenNuevo) && !TipoTasaDGT.UnoCinco.getValorEnum().equals(tasaVO.getTipoTasa())
				&& !TipoTasaDGT.UnoDos.getValorEnum().equals(tasaVO.getTipoTasa()) && !TipoTasaDGT.UnoSeis.getValorEnum().equals(tasaVO.getTipoTasa())
				&& !TipoTasaDGT.CuatroUno.getValorEnum().equals(tasaVO.getTipoTasa())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El almacén " + almacenNuevo + " al que se quiere vincular la tasa " +tasaVO.getCodigoTasa()+ ", no es admitido para ese tipo de tasa.");
		}else if(TipoAlmacenTasa.ALMACEN_BAJA.getValorEnum().equals(almacenNuevo) && !TipoTasaDGT.CuatroUno.getValorEnum().equals(tasaVO.getTipoTasa())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El almacén " + almacenNuevo + " al que se quiere vincular la tasa " +tasaVO.getCodigoTasa()+ ", no es admitido para ese tipo de tasa.");
		}else if(TipoAlmacenTasa.ALMACEN_DUPLICADO.getValorEnum().equals(almacenNuevo) && !TipoTasaDGT.CuatroCuatro.getValorEnum().equals(tasaVO.getTipoTasa())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El almacén " + almacenNuevo + " al que se quiere vincular la tasa " +tasaVO.getCodigoTasa()+ ", no es admitido para ese tipo de tasa.");
		}else if(TipoAlmacenTasa.ALMACEN_INTEVE.getValorEnum().equals(almacenNuevo) && !TipoTasaDGT.CuatroUno.getValorEnum().equals(tasaVO.getTipoTasa())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El almacén " + almacenNuevo + " al que se quiere vincular la tasa " +tasaVO.getCodigoTasa()+ ", no es admitido para ese tipo de tasa.");
		}else if(TipoAlmacenTasa.ALMACEN_PERMISO_INT.getValorEnum().equals(almacenNuevo) && !TipoTasaDGT.CuatroCinco.getValorEnum().equals(tasaVO.getTipoTasa())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El almacén " + almacenNuevo + " al que se quiere vincular la tasa " +tasaVO.getCodigoTasa()+ ", no es admitido para ese tipo de tasa.");
		}else if (TipoAlmacenTasa.SIN_ALMACEN.getValorEnum().equals(almacenNuevo) && tasaVO.getTipoAlmacen() == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("La tasa " + tasaVO.getCodigoTasa() + " no tiene almacén asignado por lo que no se puede desvincular");
		}
		return result;
		
	}

	private void gestionarAlmacenMatw(TasaVO tasaVO, String almacenNuevo) {
		TasaMatwVO tasaMatwVO = conversor.transform(tasaVO, TasaMatwVO.class);
		tasaMatwVO.setTipoAlmacen(TipoAlmacenTasa.ALMACEN_MATW.getValorEnum());
		tasaMatwVO.setTipoTramite(TipoTramiteTrafico.Matriculacion.getValorEnum());
		servicioPersistenciaTasas.guardarTasaAlmacenMatw(tasaMatwVO);
	}
	
	private void gestionarAlmacenCtit(TasaVO tasaVO, String almacenNuevo) {
		TasaCtitVO tasaCtitVO = conversor.transform(tasaVO, TasaCtitVO.class);
		tasaCtitVO.setTipoAlmacen(TipoAlmacenTasa.ALMACEN_CTIT.getValorEnum());
		tasaCtitVO.setTipoTramite(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
		servicioPersistenciaTasas.guardarTasaAlmacenCtit(tasaCtitVO);
		
	}
	
	private void gestionarAlmacenBaja(TasaVO tasaVO, String almacenNuevo) {
		TasaBajaVO tasaBajaVO = conversor.transform(tasaVO, TasaBajaVO.class);
		tasaBajaVO.setTipoAlmacen(TipoAlmacenTasa.ALMACEN_BAJA.getValorEnum());
		tasaBajaVO.setTipoTramite(TipoTramiteTrafico.Baja.getValorEnum());
		servicioPersistenciaTasas.guardarTasaAlmacenBaja(tasaBajaVO);
	}
	
	private void gestionarAlmacenDuplicado(TasaVO tasaVO, String almacenNuevo) {
		TasaDuplicadoVO tasaDuplicadoVO = conversor.transform(tasaVO, TasaDuplicadoVO.class);
		tasaDuplicadoVO.setTipoAlmacen(TipoAlmacenTasa.ALMACEN_DUPLICADO.getValorEnum());
		tasaDuplicadoVO.setTipoTramite(TipoTramiteTrafico.Duplicado.getValorEnum());
		servicioPersistenciaTasas.guardarTasaAlmacenDuplicado(tasaDuplicadoVO);
	}

	private void gestionarAlmacenInteve(TasaVO tasaVO, String almacenNuevo) {
		TasaInteveVO tasaInteveVO = conversor.transform(tasaVO, TasaInteveVO.class);
		tasaInteveVO.setTipoAlmacen(TipoAlmacenTasa.ALMACEN_INTEVE.getValorEnum());
		tasaInteveVO.setTipoTramite(TipoTramiteTrafico.Inteve.getValorEnum());
		servicioPersistenciaTasas.guardarTasaAlmacenInteve(tasaInteveVO);
	}
	
	private void gestionarAlmacenPermisoInt(TasaVO tasaVO, String almacenNuevo) {
		TasaPermInternVO tasaPermInterVO = conversor.transform(tasaVO, TasaPermInternVO.class);
		tasaPermInterVO.setTipoAlmacen(TipoAlmacenTasa.ALMACEN_PERMISO_INT.getValorEnum());
		tasaPermInterVO.setTipoTramite(TipoTramiteTrafico.PermisonInternacional.getValorEnum());
		servicioPersistenciaTasas.guardarTasaAlmacenPermIntern(tasaPermInterVO);
	}

	private void gestionarTasaSinAlmacen(TasaVO tasaVO) {
		if(TipoAlmacenTasa.ALMACEN_MATW.getValorEnum().equals(tasaVO.getTipoAlmacen())){
			TasaMatwVO tasaMatwVO = conversor.transform(tasaVO, TasaMatwVO.class);
			tasaMatwVO.setTipoAlmacen(null);
			tasaMatwVO.setTipoTramite(null);
			servicioPersistenciaTasas.guardarTasaAlmacenMatw(tasaMatwVO);
		}else if(TipoAlmacenTasa.ALMACEN_CTIT.getValorEnum().equals(tasaVO.getTipoAlmacen())){
			TasaCtitVO tasaCtitVO = conversor.transform(tasaVO, TasaCtitVO.class);
			tasaCtitVO.setTipoAlmacen(null);
			tasaCtitVO.setTipoTramite(null);
			servicioPersistenciaTasas.guardarTasaAlmacenCtit(tasaCtitVO);
		}else if(TipoAlmacenTasa.ALMACEN_BAJA.getValorEnum().equals(tasaVO.getTipoAlmacen())){
			TasaBajaVO tasaBajaVO = conversor.transform(tasaVO, TasaBajaVO.class);
			tasaBajaVO.setTipoAlmacen(null);
			tasaBajaVO.setTipoTramite(null);
			servicioPersistenciaTasas.guardarTasaAlmacenBaja(tasaBajaVO);
		}else if(TipoAlmacenTasa.ALMACEN_DUPLICADO.getValorEnum().equals(tasaVO.getTipoAlmacen())){
			TasaDuplicadoVO tasaDuplicadoVO = conversor.transform(tasaVO, TasaDuplicadoVO.class);
			tasaDuplicadoVO.setTipoAlmacen(null);
			tasaDuplicadoVO.setTipoTramite(null);
			servicioPersistenciaTasas.guardarTasaAlmacenDuplicado(tasaDuplicadoVO);
		}else if(TipoAlmacenTasa.ALMACEN_INTEVE.getValorEnum().equals(tasaVO.getTipoAlmacen())){
			TasaInteveVO tasaInteveVO = conversor.transform(tasaVO, TasaInteveVO.class);
			tasaInteveVO.setTipoAlmacen(null);
			tasaInteveVO.setTipoTramite(null);
			servicioPersistenciaTasas.guardarTasaAlmacenInteve(tasaInteveVO);
		}else if(TipoAlmacenTasa.ALMACEN_PERMISO_INT.getValorEnum().equals(tasaVO.getTipoAlmacen())){
			TasaPermInternVO asaPermInternVO = conversor.transform(tasaVO, TasaPermInternVO.class);
			asaPermInternVO.setTipoAlmacen(null);
			asaPermInternVO.setTipoTramite(null);
			servicioPersistenciaTasas.guardarTasaAlmacenPermIntern(asaPermInternVO);
		}
	}
	
	@Override
	public List<TasaDto> getTasasLibres(Long idContrato , String tipoTasa) {
		log.info("ModeloTrafico: --- INICIO --- obtenerCodigosTasaLibres.");

		try {
			List<TasaVO> tasasVO = servicioPersistenciaTasas.getTasasLibres(idContrato.longValue(), tipoTasa);

			return conversor.transform(tasasVO, TasaDto.class);

		} catch (Throwable e) {
			log.error("Error al obtenerTasasLibres" + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<TasaDto> getTasasPorNumExpediente(BigDecimal numExpediente) {
		List<TasaVO> tasasVO = tasaDao.getTasasPorNumExpediente(numExpediente); 
		return conversor.transform(tasasVO, TasaDto.class);
	}

}
