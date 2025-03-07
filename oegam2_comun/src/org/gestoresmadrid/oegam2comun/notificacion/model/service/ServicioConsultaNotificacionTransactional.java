package org.gestoresmadrid.oegam2comun.notificacion.model.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.notificacion.model.vo.NotificacionSSVO;
import org.gestoresmadrid.core.notificacion.model.vo.RespuestaSSVO;

public interface ServicioConsultaNotificacionTransactional {
	
	public boolean cambiarEstadoNotificacion(Integer codigoNotificacion, Integer estadoFinal, String numColegiado);
	
	public NotificacionSSVO getNotificacionByIdNumColegiado (Integer codigoNotificacion, String numColegiado);
	
	public Integer getUltimaNotificacionByRolNumColegiado (Integer codigoNotificacion, BigDecimal numColegiado);
	
	public void insertarNotificacion(List<NotificacionSSVO> listaNotificacion);
	
	public List<RespuestaSSVO> listadoTodasRespuestas();
	
	public List<RespuestaSSVO> listadoRespuestasPorFecha(Date fechaInicio, Date fechaFin);
	
	public void insertarRespuesta(Integer idCodigo, String estado, String numColegiado);
	
	public List<RespuestaSSVO> listadoRespuestasPorCodigoNumColegiado(Integer idCodigo, String numColegiado);
	
	public List<RespuestaSSVO> recuperarPDFrespuesta(Integer idCodigo, String numColegiado);
	
	public void insertarResultadoNotificacion(List<RespuestaSSVO> listaRespuestasNotificacion);
	
	public List<NotificacionSSVO> obtenerNotificacionesPendientes(String numColegiado);
	
}