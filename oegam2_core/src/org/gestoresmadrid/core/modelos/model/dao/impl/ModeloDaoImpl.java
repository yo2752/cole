package org.gestoresmadrid.core.modelos.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelos.model.dao.ModeloDao;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ModeloDaoImpl extends GenericDaoImplHibernate<ModeloVO> implements ModeloDao{

	private static final long serialVersionUID = -3795907352759971229L;


	@Override
	public ModeloVO getModeloPorId(String modelo, String version) {
		Criteria criteria = getCurrentSession().createCriteria(ModeloVO.class);
		criteria.add(Restrictions.eq("id.modelo", modelo));
		criteria.add(Restrictions.eq("id.version", version));
		criteria.createAlias("fundamentosExencion","fundamentosExencion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bonificaciones","bonificaciones", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("bonificaciones.estado", BigDecimal.ONE));
		return (ModeloVO) criteria.uniqueResult();
	}
}
