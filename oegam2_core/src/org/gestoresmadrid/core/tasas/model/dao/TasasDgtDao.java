package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaDgtVO;

public interface TasasDgtDao extends GenericDao<TasaDgtVO>, Serializable {

	/**
	 * Recupera el entidad desde su codigo
	 * @param codigo
	 * @return
	 */
	TasaDgtVO get(String codigo);

	List<TasaDgtVO> getListaTasasDgt();
}
