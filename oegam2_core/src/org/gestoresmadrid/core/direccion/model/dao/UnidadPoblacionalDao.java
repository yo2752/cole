package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.UnidadPoblacionalVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface UnidadPoblacionalDao extends GenericDao<UnidadPoblacionalVO>, Serializable {

	List<UnidadPoblacionalVO> getUnidadPoblacional(String idMunicipio, String idProvincia);
}
