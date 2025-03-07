package org.gestoresmadrid.core.estacionITV.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.estacionITV.model.vo.EstacionITVVO;
import org.gestoresmadrid.core.model.dao.GenericDao;


public interface EstacionITVDao extends GenericDao<EstacionITVVO>, Serializable {

	List<EstacionITVVO> getEstacionesItv(String idProvincia);
	

}
