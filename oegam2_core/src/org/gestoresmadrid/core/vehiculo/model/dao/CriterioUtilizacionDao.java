package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CriterioUtilizacionVO;

public interface CriterioUtilizacionDao extends GenericDao<CriterioUtilizacionVO>, Serializable {

	List<CriterioUtilizacionVO> getCriterioUtilizacion(String... criterioUtilizacion);
}
