package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.ColegioProvinciaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ColegioProvinciaDao extends GenericDao<ColegioProvinciaVO>, Serializable {

	List<ColegioProvinciaVO> listadoColegioProvincia(String idProvincia);
}
