package org.gestoresmadrid.core.bien.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.bien.model.vo.UnidadMetricaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface UnidadMetricaDao extends Serializable, GenericDao<UnidadMetricaVO>{

	List<UnidadMetricaVO> getListaUnidadesMetricas();

}
