package org.gestoresmadrid.oegam2comun.estadisticas.listados.model.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.administracion.model.dao.UsuarioLoginDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloListadoUsuariosIPPaginated")
@Transactional(readOnly = true)
public class modeloListadoUsuariosIPPaginatedImpl extends AbstractModelPagination {

	@Resource
	private UsuarioLoginDao usuarioLoginDao;

	@Override
	protected GenericDao<?> getDao() {
		return usuarioLoginDao;
	}

	public UsuarioLoginDao getUsuarioLoginDao() {
		return usuarioLoginDao;
	}

	public void setUsuarioLoginDao(UsuarioLoginDao usuarioLoginDao) {
		this.usuarioLoginDao = usuarioLoginDao;
	}

}
