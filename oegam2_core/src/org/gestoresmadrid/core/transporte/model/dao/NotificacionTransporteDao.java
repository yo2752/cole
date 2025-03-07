package org.gestoresmadrid.core.transporte.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.transporte.model.vo.NotificacionTransporteVO;

public interface NotificacionTransporteDao extends Serializable, GenericDao<NotificacionTransporteVO>{

	NotificacionTransporteVO getNotificacionPorId(Long idNotificacionTransporte, Boolean notificacionCompleto);

}
