package org.gestoresmadrid.core.modelos.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelos.model.dao.TipoImpuestoDao;
import org.gestoresmadrid.core.modelos.model.vo.TipoImpuestoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoImpuestoDaoImpl extends GenericDaoImplHibernate<TipoImpuestoVO> implements TipoImpuestoDao{

	private static final long serialVersionUID = -3056206466429552481L;
	
	@Override
	public TipoImpuestoVO getTipoimpuestoPorConceptoYModelo(String concepto, String modelo, String version) {
		Criteria criteria = getCurrentSession().createCriteria(TipoImpuestoVO.class);
		criteria.add(Restrictions.eq("concepto.id.concepto", concepto));
		criteria.add(Restrictions.eq("modelo.id.modelo", modelo));
		criteria.add(Restrictions.eq("modelo.id.version", version));
		criteria.createAlias("concepto", "concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo", "modelo",CriteriaSpecification.LEFT_JOIN);	
		return (TipoImpuestoVO) criteria.uniqueResult();
	}
	
	@Override
	public TipoImpuestoVO getTipoimpuestoPorConcepto(String concepto) {
		Criteria criteria = getCurrentSession().createCriteria(TipoImpuestoVO.class);
		criteria.add(Restrictions.eq("concepto.id.concepto", concepto));
		return (TipoImpuestoVO) criteria.uniqueResult();
	}

}
