package org.gestoresmadrid.core.notificacion.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.notificacion.model.vo.RespuestaSSVO;

public interface RespuestaSSDao extends GenericDao<RespuestaSSVO>, Serializable {
	
	public List<RespuestaSSVO> getListadoRespuestas();
	
	public List<RespuestaSSVO> getListadoRespuestaPorFecha(Date fechaInicio, Date fechaFin);
	
	public void insertarRespuesta(Integer idCodigo, String estado, String numColegiado);
	
	public List<RespuestaSSVO> getResultadoByIdCodigoNumColegiado(Integer idCodigo, String numColegiado);
	
	public List<RespuestaSSVO> getPDFrespuesta(Integer idCodigo, String numColegiado);
	
	public void insertRespuestaNotificaciones(List<RespuestaSSVO> listaResultadoNotificaciones);
	
}