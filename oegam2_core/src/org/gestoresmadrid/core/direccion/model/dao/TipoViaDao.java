package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface TipoViaDao extends GenericDao<TipoViaVO>, Serializable {

	TipoViaVO getTipoVia(String idTipoVia);

	TipoViaVO getIdTipoVia(String descTipoVia);

	List<TipoViaVO> listadoTipoVias(List<String> listaIdTipoVia);

	TipoViaVO getTipoViaDgt(String idTipoViaDgt);
}
