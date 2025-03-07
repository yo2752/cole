package org.gestoresmadrid.core.transporte.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.transporte.model.dao.PoderTransporteDao;
import org.gestoresmadrid.core.transporte.model.vo.PoderTransporteVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PoderTransporteDaoImpl  extends GenericDaoImplHibernate<PoderTransporteVO> implements PoderTransporteDao{

	private static final long serialVersionUID = 7482534753148478505L;
	
	@Override
	public PoderTransporteVO getPoderPorId(Long idPoderTransporte) {
		Criteria criteria = getCurrentSession().createCriteria(PoderTransporteVO.class);
		criteria.add(Restrictions.eq("idPoderTransporte", idPoderTransporte));
		criteria.createAlias("contrato","contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado","contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		return (PoderTransporteVO) criteria.uniqueResult();
	}

}
