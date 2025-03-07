package org.gestoresmadrid.core.favoritos.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface FavoritosFuncionDao extends GenericDao<FuncionVO>, Serializable {
	
	List<FuncionVO> getFuncionCompleta(String descFuncion);
}
