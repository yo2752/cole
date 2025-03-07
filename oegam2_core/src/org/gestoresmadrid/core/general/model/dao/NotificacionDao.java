package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.NotificacionVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface NotificacionDao extends GenericDao<NotificacionVO>, Serializable {

	void createNotification(NotificacionVO notificacion, UsuarioVO usuario, String tipoTramite);

	List<NotificacionVO> getList(NotificacionVO filter, String... initialized);
	
	NotificacionVO getNotificacionById(long idNotificacion);
}
