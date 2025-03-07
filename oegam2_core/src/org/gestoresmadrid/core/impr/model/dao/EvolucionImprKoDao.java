package org.gestoresmadrid.core.impr.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.impr.model.vo.EvolucionImprKoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;


public interface EvolucionImprKoDao extends GenericDao<EvolucionImprKoVO>, Serializable {

	public List<EvolucionImprKoVO> getListEvoImprKo(Long idImprKo);
}