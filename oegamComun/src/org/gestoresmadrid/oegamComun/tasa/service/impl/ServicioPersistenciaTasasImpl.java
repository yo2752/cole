package org.gestoresmadrid.oegamComun.tasa.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.tasas.model.dao.EvolucionTasaDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaBajaDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaCtitDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaDuplicadoDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaInteveDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaMatwDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaPermInternDao;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaPK;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaBajaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaCtitVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaDuplicadoVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaInteveVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaMatwVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaPermInternVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioPersistenciaTasas;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioPersistenciaTasasPegatinas;
import org.gestoresmadrid.oegamComun.tasa.view.bean.ResultadoTasaBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class ServicioPersistenciaTasasImpl implements ServicioPersistenciaTasas {

	private static final long serialVersionUID = 3146026749489826855L;

	@Autowired
	TasaDao tasaDao;
	
	@Autowired
	TasaMatwDao tasaMatwDao;
	
	@Autowired
	TasaCtitDao tasaCtitDao;
	
	@Autowired
	TasaDuplicadoDao tasaDuplicadoDao;
	
	@Autowired
	TasaBajaDao tasaBajaDao;
	
	@Autowired
	TasaInteveDao tasaInteveDao;
	
	@Autowired
	TasaPermInternDao tasaPermInternDao;
	
	@Autowired
	EvolucionTasaDao evolucionTasaDao;
	
	@Autowired
	ServicioPersistenciaTasasPegatinas servicioPersistenciaTasasPegatinas;
	
	@Override
	@Transactional
	public ResultadoBean cambiarFormatoATasaElectronica(TasaVO tasa, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			tasaDao.guardar(tasa);
			servicioPersistenciaTasasPegatinas.borrarTasa(tasa.getCodigoTasa());
			insertarEvolucionTasa(tasa.getCodigoTasa(), null, ServicioComunTasa.CAMBIOFORMATO, null, new Date(), new BigDecimal(idUsuario));
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de cambiar el formato de la tasa a electronica con codigo: " + tasa.getCodigoTasa() +", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el formato de la tasa a electronica con codigo: " + tasa.getCodigoTasa());
		}
		if(resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	private void insertarEvolucionTasa(String codigoTasa, BigDecimal numExpediente, String accion, String motivo, Date fecha, BigDecimal idUsuario) {
		EvolucionTasaVO evolucionTasa = new EvolucionTasaVO();
		evolucionTasa.setAccion(accion);
		evolucionTasa.setMotivoBloqueo(motivo);
		if (numExpediente != null) {
			evolucionTasa.setNumExpediente(numExpediente);
		} else {
			evolucionTasa.setNumExpediente(null);
		}
		EvolucionTasaPK id = new EvolucionTasaPK();
		id.setCodigoTasa(codigoTasa);
		id.setFechaHora(fecha);
		evolucionTasa.setId(id);
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		evolucionTasa.setUsuario(usuario);
		evolucionTasaDao.guardar(evolucionTasa);
	}

	@Override
	@Transactional(readOnly = true)
	public TasaVO getTasa(String codigoTasa, BigDecimal numExpediente, String tipoTasa) {
		return tasaDao.getTasa(codigoTasa, numExpediente, tipoTasa);
	}

	@Override
	@Transactional(readOnly = true)
	public TasaVO getTasaLibre(Long idContrato, String tipoTasa) {
		return tasaDao.getTasaLibre(idContrato, tipoTasa);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TasaVO> getTasasLibres(Long idContrato, String tipoTasa) {
		return tasaDao.getTasasLibres(idContrato, tipoTasa);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TasaVO> getTasasLibresMatriculacion(Long idContrato) {
		return tasaDao.getTasasLibresMatriculacion(idContrato);
	}

	@Override
	@Transactional
	public void actualizar(TasaVO tasa) {
		tasaDao.actualizar(tasa);
	}

	@Override
	@Transactional
	public TasaVO guardarActualizarConEvo(TasaVO tasa, EvolucionTasaVO evolucionTasa) {
		tasaDao.guardarOActualizar(tasa);
		evolucionTasaDao.guardar(evolucionTasa);
		return tasa;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TasaVO> obtenerTasasContrato(Long idContrato, String tipoTasa, int maxResult) {
		return tasaDao.obtenerTasasContrato(idContrato, tipoTasa, maxResult);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TasaInteveVO> obtenerTasasInteveContrato(Long idContrato, String tipoTasa, int maxResult) {
		return tasaInteveDao.obtenerTasasInteveContrato(idContrato, tipoTasa, maxResult);
	}
	
	@Override
	@Transactional
	public ResultadoTasaBean desasignarYAsignarTasasTramite(String codigoTasaAsignar, String tipoTasaAsignar, TasaVO tasaDesasignar, BigDecimal numExpediente, Long idUsuario) {
		ResultadoTasaBean resultado = new ResultadoTasaBean(Boolean.FALSE);
		TasaVO tasaAsignarBBDD = tasaDao.getTasa(codigoTasaAsignar, null, tipoTasaAsignar);
		if (tasaAsignarBBDD != null && tasaAsignarBBDD.getNumExpediente() != null && tasaAsignarBBDD.getNumExpediente().compareTo(numExpediente) != 0) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa que desea asignar ya esta asignada al expediente: " + tasaAsignarBBDD.getNumExpediente());
		} else {
			Date fecha = new Date();
			tasaDesasignar.setFechaAsignacion(null);
			tasaDesasignar.setNumExpediente(null);
			tasaDao.actualizar(tasaDesasignar);
			evolucionTasaDao.guardar(crearEvolucion(tasaDesasignar.getCodigoTasa(), null, fecha, ServicioComunTasa.DESASIGNAR, idUsuario));
			tasaAsignarBBDD.setFechaAsignacion(fecha);
			tasaAsignarBBDD.setNumExpediente(numExpediente);
			tasaDao.actualizar(tasaAsignarBBDD);
			evolucionTasaDao.guardar(crearEvolucion(codigoTasaAsignar, null, fecha, ServicioComunTasa.ASIGNAR, idUsuario));
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoTasaBean asignarTasasTramite(String codigoTasaAsignar, String tipoTasaAsignar, BigDecimal numExpediente, Long idUsuario) {
		ResultadoTasaBean resultado = new ResultadoTasaBean(Boolean.FALSE);
		Date fecha = new Date();
		TasaVO tasaAsignarBBDD = tasaDao.getTasa(codigoTasaAsignar, null, tipoTasaAsignar);
		if (tasaAsignarBBDD != null && tasaAsignarBBDD.getNumExpediente() != null && tasaAsignarBBDD.getNumExpediente().compareTo(numExpediente) != 0) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa que desea asignar ya esta asignada al expediente: " + tasaAsignarBBDD.getNumExpediente());
		} else {
			tasaAsignarBBDD.setFechaAsignacion(fecha);
			tasaAsignarBBDD.setNumExpediente(numExpediente);
			tasaDao.actualizar(tasaAsignarBBDD);
			evolucionTasaDao.guardar(crearEvolucion(codigoTasaAsignar, null, fecha, ServicioComunTasa.ASIGNAR, idUsuario));
		}
		return resultado;
	}

	private EvolucionTasaVO crearEvolucion(String codigoTasa, BigDecimal numExpediente, Date fecha, String estado, Long idUsuario) {
		EvolucionTasaVO evolucionTasaVO = new EvolucionTasaVO();
		EvolucionTasaPK id = new EvolucionTasaPK();
		id.setCodigoTasa(codigoTasa);
		id.setFechaHora(fecha);
		evolucionTasaVO.setId(id);
		evolucionTasaVO.setAccion(estado);
		evolucionTasaVO.setNumExpediente(numExpediente);
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario);
		evolucionTasaVO.setUsuario(usuario);
		return evolucionTasaVO;
	}
	
	@Override
	@Transactional
	public void borrarTasa(String codigoTasa) {
		tasaDao.borrar(getTasa(codigoTasa, null, null));
	}

	@Override
	@Transactional
	public void guardarTasaAlmacenMatw(TasaMatwVO tasaMatwVO) {
		tasaMatwDao.guardarOActualizar(tasaMatwVO);
		insertarEvolucionTasa(tasaMatwVO.getCodigoTasa(), null, ServicioComunTasa.CAMBIOALMACEN, null, new Date(), new BigDecimal(tasaMatwVO.getUsuario().getIdUsuario()));
	}
	
	@Override
	@Transactional
	public void guardarTasaAlmacenBaja(TasaBajaVO tasaBajaVO) {
		tasaBajaDao.guardarOActualizar(tasaBajaVO);
		insertarEvolucionTasa(tasaBajaVO.getCodigoTasa(), null, ServicioComunTasa.CAMBIOALMACEN, null, new Date(), new BigDecimal(tasaBajaVO.getUsuario().getIdUsuario()));
	}
	
	@Override
	@Transactional
	public void guardarTasaAlmacenCtit(TasaCtitVO tasaCtitVO) {
		tasaCtitDao.guardarOActualizar(tasaCtitVO);
		insertarEvolucionTasa(tasaCtitVO.getCodigoTasa(), null, ServicioComunTasa.CAMBIOALMACEN, null, new Date(), new BigDecimal(tasaCtitVO.getUsuario().getIdUsuario()));
	}
	
	@Override
	@Transactional
	public void guardarTasaAlmacenDuplicado(TasaDuplicadoVO tasaDuplicadoVO) {
		tasaDuplicadoDao.guardarOActualizar(tasaDuplicadoVO);
		insertarEvolucionTasa(tasaDuplicadoVO.getCodigoTasa(), null, ServicioComunTasa.CAMBIOALMACEN, null, new Date(), new BigDecimal(tasaDuplicadoVO.getUsuario().getIdUsuario()));
	}
	
	@Override
	@Transactional
	public void guardarTasaAlmacenInteve(TasaInteveVO tasaInteveVO) {
		tasaInteveDao.guardarOActualizar(tasaInteveVO);
		insertarEvolucionTasa(tasaInteveVO.getCodigoTasa(), null, ServicioComunTasa.CAMBIOALMACEN, null, new Date(), new BigDecimal(tasaInteveVO.getUsuario().getIdUsuario()));
	}

	@Override
	@Transactional
	public void guardarTasaAlmacenPermIntern(TasaPermInternVO tasaPermInterVO) {
		tasaPermInternDao.guardarOActualizar(tasaPermInterVO);
		insertarEvolucionTasa(tasaPermInterVO.getCodigoTasa(), null, ServicioComunTasa.CAMBIOALMACEN, null, new Date(), new BigDecimal(tasaPermInterVO.getUsuario().getIdUsuario()));
	}

	@Override
	@Transactional
	public ResultadoBean desasignarTasaBBDD(String codigoTasa, BigDecimal numExpediente, String tipoTasa, Long idContrato, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		TasaVO tasaBBDD = tasaDao.getTasa(codigoTasa, numExpediente, tipoTasa);
		if (tasaBBDD != null && tasaBBDD.getNumExpediente() != null) {
			tasaBBDD.setFechaAsignacion(null);
			tasaBBDD.setNumExpediente(null);
			tasaDao.actualizar(tasaBBDD);
			evolucionTasaDao.guardar(crearEvolucion(codigoTasa, numExpediente, new Date(), ServicioComunTasa.DESASIGNAR, idUsuario));
		}else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha podido desasignar la tasa " + tasaBBDD.getCodigoTasa() + " ya que no tiene un número de expediente asignado"); 
		}
		return resultado;
	}
	
/*	@Override
	@Transactional
	public void bloquearComboTasa(String codigoTasa, Long idUsuario) {
		TasaVO tasaVO = getTasa(codigoTasa, null, null);
		tasaVO.setComboTasaBloqueado(EstadoComboTasa.Bloqueado.getValorEnum());
		tasaDao.guardarOActualizar(tasaVO);
	}*/
}
