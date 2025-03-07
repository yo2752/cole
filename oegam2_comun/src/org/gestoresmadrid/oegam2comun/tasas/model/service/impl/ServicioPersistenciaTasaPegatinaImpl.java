package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.tasas.model.dao.TasaPegatinaDao;
import org.gestoresmadrid.core.tasas.model.enumeration.TipoTasaDGT;
import org.gestoresmadrid.core.tasas.model.vo.TasaPegatinaVO;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioEvolucionTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioPersistenciaTasaPegatina;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.oegam2comun.tasas.view.dto.TasaPegatinaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioPersistenciaTasas;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersistenciaTasaPegatinaImpl implements ServicioPersistenciaTasaPegatina, Serializable {

	private static final long serialVersionUID = 4013175994710438848L;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioPersistenciaTasaPegatinaImpl.class);

	private static final int LONGITUD_CODIGO_TASA = 12;

	@Autowired
	private TasaPegatinaDao tasaPegatinaDao;

	@Autowired
	private Conversor conversor;
	
	@Autowired
	ServicioPersistenciaTasas servicioPersistenciaTasas;
	
	@Autowired
	ServicioEvolucionTasa servicioEvolucionTasa;

	@Override
	@Transactional
	public RespuestaTasas guardarTasa(Tasa tasa) {
		RespuestaTasas respuestaTasas = new RespuestaTasas();
		if (TipoTasaDGT.convertir(tasa.getTipoTasa()) == null) {
			respuestaTasas.setError(true);
			respuestaTasas.setMensajeError("Tipo de tasa incorrecto");
		} else if (tasa.getCodigoTasa() == null || tasa.getCodigoTasa().length() != LONGITUD_CODIGO_TASA) {
			respuestaTasas.setError(true);
			respuestaTasas.setMensajeError("Longuitud de la tasa erronea");
		} else {
			// Intento tomar los datos de la tasa si existe
			TasaPegatinaVO detalle = null;
			try {
				detalle = tasaPegatinaDao.detalle(tasa.getCodigoTasa());
			} catch (HibernateException e) {
				throw new TransactionalException(e);
			}
			if (detalle != null) {
				// Ya existe la tasa
				respuestaTasas.setError(true);
				respuestaTasas.setMensajeError("La tasa ya existe no se puede volver a carga");

				// Meter mas errores
				if (tasa.getIdContrato().intValue() != detalle.getContrato().getIdContrato().intValue()) {
					respuestaTasas.setError(true);
					respuestaTasas.setMensajeError("La tasa ya esta dada de alta para otro contrato");
				}
				if (!tasa.getTipoTasa().equals(detalle.getTipoTasa())) {
					respuestaTasas.setError(true);
					respuestaTasas.setMensajeError("La tasa ya esta dada de alta y es de otro tipo");
				}
			} else {
				// Comprobar la informacion de la tasa
				TasaPegatinaVO tasaPegatinaVO = new TasaPegatinaVO();
				tasaPegatinaVO.setCodigoTasa(tasa.getCodigoTasa());
				tasaPegatinaVO.setContrato(new ContratoVO());
				tasaPegatinaVO.getContrato().setIdContrato(tasa.getIdContrato().longValue());
				tasaPegatinaVO.setFechaAlta(tasa.getFechaAlta() != null ? tasa.getFechaAlta() : Calendar.getInstance().getTime());
				if (tasa.getFechaFinVigencia() != null) {
					tasaPegatinaVO.setFechaFinVigencia(tasa.getFechaFinVigencia());
				} else {
					Calendar fechaFinVigencia = Calendar.getInstance();
					fechaFinVigencia.set(Calendar.MONTH, Calendar.DECEMBER);
					fechaFinVigencia.set(Calendar.DAY_OF_MONTH, 31);
					tasaPegatinaVO.setFechaFinVigencia(fechaFinVigencia.getTime());
				}
				tasaPegatinaVO.setPrecio(tasa.getPrecio());
				tasaPegatinaVO.setTipoTasa(tasa.getTipoTasa());
				tasaPegatinaVO.setUsuario(new UsuarioVO());
				tasaPegatinaVO.getUsuario().setIdUsuario(tasa.getIdUsuario().longValue());
				tasaPegatinaVO.setImportadoIcogam(new BigDecimal(tasa.getImportadoIcogam()));
				try {
					tasaPegatinaDao.guardar(tasaPegatinaVO);
				} catch (ConstraintViolationException c) {
					throw new TransactionalException(c);

				} catch (HibernateException e) {
					throw new TransactionalException(e);
				}
			}
		}
		return respuestaTasas;
	}

	@Override
	@Transactional(readOnly = true)
	public TasaPegatinaVO getTasaVO(String codigoTasa) {
		try {
			return tasaPegatinaDao.detalle(codigoTasa);
		} catch (HibernateException e) {
			throw new TransactionalException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<TasaPegatinaVO> getListaTasasPorCodigos(List<String> listaTasas) {
		try {
			return tasaPegatinaDao.getTasasPorLista(listaTasas);
		} catch (HibernateException e) {
			throw new TransactionalException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TasaPegatinaDto getTasaDto(String codigoTasa) {
		TasaPegatinaDto tasaPegatinaDto = null;
		try {
			TasaPegatinaVO tasaPegatinaVO = tasaPegatinaDao.detalle(codigoTasa);
			if (tasaPegatinaVO != null) {
				tasaPegatinaDto = conversor.transform(tasaPegatinaVO, TasaPegatinaDto.class);
			}
		} catch (HibernateException e) {
			throw new TransactionalException(e);
		}
		return tasaPegatinaDto;
	}

	@Override
	@Transactional
	public String eliminar(String codigoTasa) {
		try {
			TasaPegatinaVO tasa = tasaPegatinaDao.detalle(codigoTasa);
			if (tasa != null) {
				if (tasaPegatinaDao.borrar(tasa)) {
					return "OK";
				} else {
					return "No se pudo borrar";
				}
			} else {
				return "el codigo de tasa no corresponde con ninguna tasa de pegatina";
			}
		} catch (HibernateException e) {
			throw new TransactionalException(e);
		}
	}

	@Transactional
	@Override
	public ArrayList<TasaPegatinaDto> obtenerTasasPegatinaContrato(Long idContrato, String tipoTasa) {
		ArrayList<TasaPegatinaDto> listaDto = new ArrayList<TasaPegatinaDto>();
		try {
			List<TasaPegatinaVO> listaVO = tasaPegatinaDao.obtenerTasasPegatinaContrato(idContrato, tipoTasa);
			if (listaVO != null && listaVO.size() > 0) {
				for (TasaPegatinaVO tasaPegatinaVO : listaVO) {
					TasaPegatinaDto tasaPegatinaDto = conversor.transform(tasaPegatinaVO, TasaPegatinaDto.class);
					listaDto.add(tasaPegatinaDto);
				}
				return listaDto;
			}
		} catch (Exception e) {
			LOG.error("Error al obtener las tasa pegatina para el contrato: " + idContrato, e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean actualizarTasaPegatinaFechaAplicacion(String codigoTasa, Date fechaFacturacion) {
		ResultBean result = new ResultBean();
		try {
			TasaPegatinaVO tasaPegatina = getTasaVO(codigoTasa);
			if (tasaPegatina != null) {
				tasaPegatina.setFechaFacturacion(fechaFacturacion);
				tasaPegatinaDao.actualizar(tasaPegatina);
			} else {
				LOG.error("No se ha encontrado la tasa pegatina con codigo: " + codigoTasa);
				result.setError(true);
				result.setMensaje("No existe la tasa");
			}
		} catch (Exception e) {
			LOG.error("Error al actualizar la tasa pegatina: " + codigoTasa, e);
			result.setError(true);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarTasa(TasaPegatinaVO tasaPegatina) {
		ResultBean result = new ResultBean();
		try {
			tasaPegatinaDao.guardar(tasaPegatina);
		} catch (Exception e) {
			LOG.error("Error al guardar la tasa pegatina: " + tasaPegatina.getCodigoTasa(), e);
			result.setError(true);
		}
		return result;
	}
	
	@Override
	@Transactional
	public ResultadoBean cambiarFormatoATasaPegatina(TasaPegatinaVO tasaPegatina, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			tasaPegatinaDao.guardar(tasaPegatina);
			servicioPersistenciaTasas.borrarTasa(tasaPegatina.getCodigoTasa());
			servicioEvolucionTasa.insertarEvolucionTasa(tasaPegatina.getCodigoTasa(), null, ServicioEvolucionTasa.CAMBIOFORMATO, null, new Date(), new BigDecimal(idUsuario));
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de cambiar el formato de la tasa a pegatina con codigo: " + tasaPegatina.getCodigoTasa() +", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el formato de la tasa a pegatina con codigo: " + tasaPegatina.getCodigoTasa());
		}
		if(resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
}
