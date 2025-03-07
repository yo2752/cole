package org.gestoresmadrid.core.consultaDev.model.dao.impl;

import org.gestoresmadrid.core.consultaDev.model.dao.ConsultaDevDao;
import org.gestoresmadrid.core.consultaDev.model.vo.ConsultaDevVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ConsultaDevDaoImpl extends GenericDaoImplHibernate<ConsultaDevVO> implements ConsultaDevDao{

	private static final long serialVersionUID = 2363621105166592913L;

	@Override
	public ConsultaDevVO getConsultaDevPorId(Long idConsultaDev,Boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaDevVO.class);
		criteria.add(Restrictions.eq("idConsultaDev", idConsultaDev));
		if(completo){
			criteria.createAlias("contrato","contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado","contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("suscripciones","suscripciones", CriteriaSpecification.LEFT_JOIN);
		}
		return (ConsultaDevVO) criteria.uniqueResult();
	}
}
