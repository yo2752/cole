package org.gestoresmadrid.oegam2comun.mensajes.procesos.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.mensajes.procesos.model.dao.MensajesProcesosDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloMensajesProcesosPaginated")
@Transactional(readOnly = true)
public class ModeloMensajesProcesosPaginatedImpl extends AbstractModelPagination {

	@Resource
	private MensajesProcesosDao mensajesProcesosDao;

	@Override
	protected GenericDao<?> getDao() {
		return mensajesProcesosDao;
	}
}