package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.JobDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.JobVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class JobDaoImpl extends GenericDaoImplHibernate<JobVO> implements JobDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1319022469337617756L;

	@SuppressWarnings("unchecked")
	@Override
	public List<JobVO> findJobByEstado(Long estado) {
		Criteria criteria = getCurrentSession().createCriteria(JobVO.class, "job");
		criteria.add(Restrictions.eq("estado", estado));
		criteria.addOrder(Order.asc("jobId"));
		return criteria.list();
	}

	@Override
	public JobVO findJobByPrimaryKey(Long id) {
		Criteria criteria = getCurrentSession().createCriteria(JobVO.class, "job");
		criteria.createAlias("job.contrato", "contrato");
		criteria.createAlias("contrato.colegiado", "contratoColegiado");

		criteria.add(Restrictions.eq("jobId", id));
		
		return (JobVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobVO> findJobByDocDistintivo(Long docDistintivo) {
		Criteria criteria = getCurrentSession().createCriteria(JobVO.class, "job");
		criteria.add(Restrictions.eq("docDistintivo", docDistintivo));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobVO> findJobByJefatura(String jefatura) {
		Criteria criteria = getCurrentSession().createCriteria(JobVO.class);
		criteria.add(Restrictions.eq("jefatura", jefatura));
		criteria.add(Restrictions.eq("estado", new Long(0)));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobVO> findJobByColegio(Boolean colegio) {
		Criteria criteria = getCurrentSession().createCriteria(JobVO.class, "job");
		criteria.add(Restrictions.eq("colegio", (colegio)? "S": "N"));
		criteria.add(Restrictions.eq("estado", new Long(0)));
		criteria.createAlias("docPermDstv", "docPermDstv",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}


}
