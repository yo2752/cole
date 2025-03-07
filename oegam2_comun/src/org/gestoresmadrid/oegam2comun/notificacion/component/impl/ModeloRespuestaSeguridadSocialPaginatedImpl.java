package org.gestoresmadrid.oegam2comun.notificacion.component.impl;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.notificacion.model.dao.RespuestaSSDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloRespuestaSeguridadSocialPaginatedImpl")
@Transactional(readOnly = true)
public class ModeloRespuestaSeguridadSocialPaginatedImpl extends AbstractModelPagination {
	
	@Autowired
	private RespuestaSSDao respuestaSSDao;
	
	@Override
	protected GenericDao<?> getDao() {
		return respuestaSSDao;
	}
	
	public RespuestaSSDao getRespuestaSSDao() {
		return respuestaSSDao;
	}
	
	public void setRespuestaSSDao(RespuestaSSDao respuestaSSDao) {
		this.respuestaSSDao = respuestaSSDao;
	}
	
}