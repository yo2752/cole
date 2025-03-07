package org.gestoresmadrid.oegam2comun.notificacion.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.notificacion.model.dao.NotificacionSSDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloConsultaNotificacionPaginated")
@Transactional(readOnly = true)
public class ModeloNotificacionPaginatedImpl extends AbstractModelPagination{

	@Resource
	private NotificacionSSDao consultaNotificacionDao;

	@Autowired
	private ServicioConsultaNotificacion servicioConsultaNotificacion;

	@Override
	protected GenericDao<?> getDao() {
		return consultaNotificacionDao;
	}

	public NotificacionSSDao getConsultaNotificacionDao() {
		return consultaNotificacionDao;
	}

	public void setConsultaNotificacionDao(NotificacionSSDao consultaNotificacionDao) {
		this.consultaNotificacionDao = consultaNotificacionDao;
	}

	public ServicioConsultaNotificacion getServicioConsultaNotificacion() {
		return servicioConsultaNotificacion;
	}

	public void setServicioConsultaNotificacion(
			ServicioConsultaNotificacion servicioConsultaNotificacion) {
		this.servicioConsultaNotificacion = servicioConsultaNotificacion;
	}

	
}
