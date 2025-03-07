package org.gestoresmadrid.core.notificacion.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.notificacion.model.vo.NotificacionSSVO;

public interface NotificacionSSDao extends GenericDao<NotificacionSSVO>, Serializable{
	
	List<NotificacionSSVO> getAllNotificaciones();
	
	void insertNotificaciones(List<NotificacionSSVO> listaNotificaciones);
	
	NotificacionSSVO getNotificacionByIdNumColegiado (int codigoNotificacion, String numColegiado);
	
	int recuperaUltimoIdNotificacionByRolNumColegiado (int rol, BigDecimal numColegiado);
	
	boolean cambiarEstadoNotificacion(int codigoNotificacion, int estado, String numColegiado);
	
	List<NotificacionSSVO> obtenerNotificacionesPendientesByNumColegiado(String numColegiado);
	
}
