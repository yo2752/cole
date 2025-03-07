package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoReduccionVO;

public interface TipoReduccionDao extends GenericDao<TipoReduccionVO>, Serializable {
	TipoReduccionVO getTipoReduccion(String tipoReduccion);
	List<TipoReduccionVO> getListaTipoReducciones();
}