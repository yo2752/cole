package org.gestoresmadrid.core.trafico.materiales.model.dao;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.UsuarioColegioVO;

public interface UsuarioColegioDao extends GenericDao<UsuarioColegioVO> {
	UsuarioColegioVO findAll();
}
