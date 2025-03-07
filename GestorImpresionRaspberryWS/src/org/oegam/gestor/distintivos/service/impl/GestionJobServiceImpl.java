package org.oegam.gestor.distintivos.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.materiales.model.dao.JobDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.JobVO;
import org.oegam.gestor.distintivos.constants.GestorImprDocConst;
import org.oegam.gestor.distintivos.model.Job;
import org.oegam.gestor.distintivos.service.GestionJobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class GestionJobServiceImpl implements GestionJobService {

	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionJobServiceImpl.class);

	@Resource JobDao jobDao;

	@Override
	@Transactional(readOnly=true)
	public List<Job> obtenerJobs(Long estado) {
		List<JobVO> jobVOs = jobDao.findJobByEstado(estado);
		return createJobsToJobVOs(jobVOs);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Job> findJobByJefatura(String jefatura) {
		List<JobVO> jobVOs = jobDao.findJobByJefatura(jefatura);
		return createJobsToJobVOs(jobVOs);
	}

	@Override
	public List<Job> findJobColegioByTipoRassb(String jefatura, String tipoRassb) {
		List<JobVO> jobVOs = jobDao.findJobByJefatura(jefatura);
		return createJobsToJobVOsByRassb(jobVOs, tipoRassb);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Job> findJobByColegio(Boolean colegio) {
		// TODO Auto-generated method stub
		List<JobVO> jobVOs = jobDao.findJobByColegio(colegio);
		return createJobsToJobVOs(jobVOs);
	}


	@Override
	@Transactional(readOnly=true)
	public Job obtenerJobByPrimaryKey(Long idJob) {
		JobVO jobVO = obtenerJobVOByPrimaryKey(idJob);
		return createJobToJobVO(jobVO);
	}

	@Override
	@Transactional(readOnly=true)
	public JobVO obtenerJobVOByPrimaryKey(Long idJob) {
		return jobDao.findJobByPrimaryKey(idJob);
	}

	@Override
	@Transactional
	public JobVO guardarJob(JobVO job) {
		return jobDao.guardarOActualizar(job);
	}

	private List<Job> createJobsToJobVOs(List<JobVO> jobVOs) {
		List<Job> jobs = new ArrayList<>();
		for(JobVO item: jobVOs) {
			jobs.add(createJobToJobVO(item));
		}
		return jobs;
	}

	private List<Job> createJobsToJobVOsByRassb(List<JobVO> jobVOs, String tipoRassb) {
		List<Job> jobs = new ArrayList<>();
		for(JobVO item: jobVOs) {
			if("PC_DSTV".equals(tipoRassb)){
				if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(item.getTipoDocumento())
					|| TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(item.getTipoDocumento())){
					jobs.add(createJobToJobVOByRassb(item));
				} else if(TipoDocumentoImprimirEnum.JUSTIFICANTE.getValorEnum().equals(item.getTipoDocumento())) {
					if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(item.getDocPermDstv().getTipo())
						|| TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(item.getDocPermDstv().getTipo())){
						jobs.add(createJobToJobVOByRassb(item));
					}
				}
			} else {
				if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(item.getTipoDocumento())) {
					jobs.add(createJobToJobVOByRassb(item));
				} else if (TipoDocumentoImprimirEnum.JUSTIFICANTE.getValorEnum().equals(item.getTipoDocumento())) {
					if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(item.getDocPermDstv().getTipo())) {
						jobs.add(createJobToJobVOByRassb(item));
					}
				}
			}
		}
		return jobs;
	}

	private Job createJobToJobVOByRassb(JobVO jobVO) {
		Job job = new Job();
		job.setId(jobVO.getJobId());
		job.setStatus(jobVO.getEstado());
		Long impresora = null;
		if (jobVO.getTipoDocumento().equals(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum())) {
			if (jobVO.getTipoDistintivo().equals(TipoDistintivo.CERO.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_0);
				impresora = 1L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.ECO.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_ECO);
				impresora = 1L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.B.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_B);
				impresora = 1L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.C.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_C);
				impresora = 1L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.CEROMT.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_0);
				impresora = 5L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.ECOMT.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_ECO);
				impresora = 5L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.BMT.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_B);
				impresora = 5L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.CMT.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_C);
				impresora = 5L;
			}
		} else if (jobVO.getTipoDocumento().equals(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum())) {
			job.setQueue(GestorImprDocConst.COLA.PERMISO);
			impresora = 1L;
		} else if (jobVO.getTipoDocumento().equals(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum())) {
			job.setQueue(GestorImprDocConst.COLA.FICHA_TECNICA);
			impresora = 2L;
		} else if (jobVO.getTipoDocumento().equals(TipoDocumentoImprimirEnum.JUSTIFICANTE.getValorEnum())) {
			if (TipoDistintivo.CERO.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo())) {
				impresora = 1L;
			} else if (TipoDistintivo.ECO.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo())) {
				impresora = 1L;
			} else if (TipoDistintivo.B.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo())) {
				impresora = 1L;
			} else if (TipoDistintivo.C.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo())) {
				impresora = 1L;
			} else if (TipoDistintivo.CEROMT.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo())) {
				impresora = 2L;
			} else if (TipoDistintivo.CEROMT.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo())) {
				impresora = 5L;
			} else if (TipoDistintivo.ECOMT.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo())) {
				impresora = 5L;
			} else if (TipoDistintivo.BMT.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo())) {
				impresora = 5L;
			} else if (TipoDistintivo.CMT.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo())) {
				impresora = 5L;
			} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(jobVO.getDocPermDstv().getTipo())) {
				impresora = 1L;
			} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(jobVO.getDocPermDstv().getTipo())) {
				impresora = 2L;
			}
			job.setQueue(GestorImprDocConst.COLA.JUSTIFICANTE);
		}
		job.setPrinter(impresora);
		return job;
	}

	private Job createJobToJobVO(JobVO jobVO) {
		Job job = new Job();
		job.setId(jobVO.getJobId());
		job.setStatus(jobVO.getEstado());
		Long impresora = null;
		if (jobVO.getTipoDocumento().equals(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum())) {
			if (jobVO.getTipoDistintivo().equals(TipoDistintivo.CERO.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_0);
				impresora = 1L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.ECO.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_ECO);
				impresora = 1L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.B.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_B);
				impresora = 1L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.C.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_C);
				impresora = 1L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.CEROMT.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_0);
				impresora = 2L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.ECOMT.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_ECO);
				impresora = 2L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.BMT.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_B);
				impresora = 2L;
			} else if (jobVO.getTipoDistintivo().equals(TipoDistintivo.CEROMT.getValorEnum())) {
				job.setQueue(GestorImprDocConst.COLA.DIST_C);
				impresora = 2L;
			}
		} else if (jobVO.getTipoDocumento().equals(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum())) {
			job.setQueue(GestorImprDocConst.COLA.PERMISO);
			impresora = 1L;
		} else if (jobVO.getTipoDocumento().equals(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum())) {
			job.setQueue(GestorImprDocConst.COLA.FICHA_TECNICA);
			impresora = 1L;
		} else if (jobVO.getTipoDocumento().equals(TipoDocumentoImprimirEnum.JUSTIFICANTE.getValorEnum())) {
			job.setQueue(GestorImprDocConst.COLA.JUSTIFICANTE);
			if (TipoDistintivo.CEROMT.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo()) ||
				TipoDistintivo.ECOMT.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo()) ||
				TipoDistintivo.BMT.getValorEnum().equals(jobVO.getDocPermDstv().getTipoDistintivo())) {
				impresora = 2L;
			} else {
				impresora = 1L;
			}
		}
		job.setPrinter(impresora);
		return job;
	}

	@Override
	public List<JobVO> findJobByDocDistintivo(Long docDistintivo) {
		return jobDao.findJobByDocDistintivo(docDistintivo);
	}

	@Override
	@Transactional
	public void deleteJobs(List<JobVO> listaJobs) {
		for (JobVO item: listaJobs) {
			jobDao.borrar(item);
		}
	}

}