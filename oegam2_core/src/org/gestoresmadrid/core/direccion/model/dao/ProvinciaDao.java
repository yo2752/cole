package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ProvinciaDao extends GenericDao<ProvinciaVO>, Serializable {

	ProvinciaVO getProvincia(String idProvincia);

	ProvinciaVO getProvinciaPorNombre(String nombre);

	List<ProvinciaVO> getLista();
}
