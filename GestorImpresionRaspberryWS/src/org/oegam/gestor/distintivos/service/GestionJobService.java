package org.oegam.gestor.distintivos.service;

import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.vo.JobVO;
import org.oegam.gestor.distintivos.model.Job;

public interface GestionJobService {
	List<Job> obtenerJobs(Long estado);

	List<Job> findJobByJefatura(String jefatura);

	List<Job> findJobByColegio(Boolean colegio);

	Job obtenerJobByPrimaryKey(Long idJob);

	JobVO obtenerJobVOByPrimaryKey(Long idJob);

	JobVO guardarJob(JobVO job);

	List<JobVO> findJobByDocDistintivo(Long docDistintivo);

	void deleteJobs(List<JobVO> listaJobs);

	List<Job> findJobColegioByTipoRassb(String jefatura, String tipoRassb);
}