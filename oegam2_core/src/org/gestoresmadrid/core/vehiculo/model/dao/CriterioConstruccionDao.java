package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CriterioConstruccionVO;

public interface CriterioConstruccionDao extends GenericDao<CriterioConstruccionVO>, Serializable {

	List<CriterioConstruccionVO> getCriterioConstruccion(String... criteriosConstruccion);

	CriterioConstruccionVO getCriteriosConstruccion(String idCriterioConstruccion);
}
