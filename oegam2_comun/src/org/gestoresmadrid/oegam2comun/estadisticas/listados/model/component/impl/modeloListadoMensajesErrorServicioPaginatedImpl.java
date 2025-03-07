package org.gestoresmadrid.oegam2comun.estadisticas.listados.model.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.mensajeErrorServicio.model.dao.MensajeErrorServicioDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloListadoMensajesErrorServicioPaginated")
@Transactional(readOnly = true)
public class modeloListadoMensajesErrorServicioPaginatedImpl extends AbstractModelPagination {

	@Resource
	private MensajeErrorServicioDao mensajeErrorServicioDao;

	@Override
	protected GenericDao<?> getDao() {
		return mensajeErrorServicioDao;
	}

}
