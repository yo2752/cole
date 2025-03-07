package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.TipoCargoVO;

public interface TipoCargoDao extends GenericDao<TipoCargoVO>, Serializable {

	List<TipoCargoVO> getTipoCargo();
}
