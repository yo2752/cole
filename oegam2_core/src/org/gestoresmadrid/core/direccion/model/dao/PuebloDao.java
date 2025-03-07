package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.PuebloVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface PuebloDao extends GenericDao<PuebloVO>, Serializable {

	PuebloVO getPueblo(String idProvincia, String idMunicipio, String pueblo);

	List<PuebloVO> listaPueblos(String idProvincia, String idMunicipio);
}
