package org.gestoresmadrid.core.trafico.materiales.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.JobVO;

public interface JobDao extends GenericDao<JobVO>, Serializable {
	List<JobVO> findJobByEstado(Long estado);
	JobVO findJobByPrimaryKey(Long id);
	List<JobVO> findJobByDocDistintivo(Long docDistintivo);
	List<JobVO> findJobByJefatura(String jefatura);
	List<JobVO> findJobByColegio(Boolean colegio);
}
