package org.gestoresmadrid.oegam2comun.notificacion.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.notificacion.model.dao.NotificacionSSDao;
import org.gestoresmadrid.core.notificacion.model.dao.RespuestaSSDao;
import org.gestoresmadrid.core.notificacion.model.vo.NotificacionSSVO;
import org.gestoresmadrid.core.notificacion.model.vo.RespuestaSSVO;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacionTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioConsultaNotificacionTransactionalImpl implements ServicioConsultaNotificacionTransactional {
	
	@Autowired
	private NotificacionSSDao notificacionDao;
	
	@Autowired
	private RespuestaSSDao respuestaSSDao;
	
	@Override
	@Transactional
	public boolean cambiarEstadoNotificacion(Integer codigoNotificacion, Integer estadoFinal, String numColegiado) {
		return notificacionDao.cambiarEstadoNotificacion(codigoNotificacion, estadoFinal, numColegiado);
	}

	@Override
	@Transactional
	public NotificacionSSVO getNotificacionByIdNumColegiado(Integer codigoNotificacion, String numColegiado) {
		return notificacionDao.getNotificacionByIdNumColegiado(codigoNotificacion, numColegiado);
	}
	
	@Override
	@Transactional
	public Integer getUltimaNotificacionByRolNumColegiado(Integer rol, BigDecimal numColegiado) {
		return notificacionDao.recuperaUltimoIdNotificacionByRolNumColegiado(rol, numColegiado);
	}

	@Override
	@Transactional
	public void insertarNotificacion(List<NotificacionSSVO> listaNotificacion) {
		if (listaNotificacion != null) {
			notificacionDao.insertNotificaciones(listaNotificacion);
		}
	}

	@Override
	@Transactional
	public void insertarRespuesta(Integer idCodigo, String estado, String numColegiado) {
		if (idCodigo != null) {
			respuestaSSDao.insertarRespuesta(idCodigo, estado, numColegiado);
		}
	}

	@Override
	@Transactional
	public List<RespuestaSSVO> listadoTodasRespuestas() {
		return respuestaSSDao.getListadoRespuestas();
	}

	@Override
	@Transactional
	public List<RespuestaSSVO> listadoRespuestasPorFecha(Date fechaInicio, Date fechaFin) {
		return respuestaSSDao.getListadoRespuestaPorFecha(fechaInicio, fechaFin);
	}
	
	@Override
	@Transactional
	public List<RespuestaSSVO> listadoRespuestasPorCodigoNumColegiado(Integer idCodigo, String numColegiado) {
		return respuestaSSDao.getResultadoByIdCodigoNumColegiado(idCodigo, numColegiado);
	}
	
	@Override
	@Transactional
	public List<RespuestaSSVO> recuperarPDFrespuesta(Integer idCodigo, String numColegiado) {
		return respuestaSSDao.getPDFrespuesta(idCodigo, numColegiado);
	}
	
	@Override
	@Transactional
	public void insertarResultadoNotificacion(List<RespuestaSSVO> listaRespuestasNotificacion){
		if (listaRespuestasNotificacion != null) {
			respuestaSSDao.insertRespuestaNotificaciones(listaRespuestasNotificacion);
		}
	}
	
	@Override
	@Transactional
	public List<NotificacionSSVO> obtenerNotificacionesPendientes(String numColegiado){
		return notificacionDao.obtenerNotificacionesPendientesByNumColegiado(numColegiado);
	}

}
