package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.TipoViaCamVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface TipoViaCamDao extends GenericDao<TipoViaCamVO>, Serializable{

	List<TipoViaCamVO> getListaTipoVias();

	TipoViaCamVO getTipoVia(String idTipoVia);

}
