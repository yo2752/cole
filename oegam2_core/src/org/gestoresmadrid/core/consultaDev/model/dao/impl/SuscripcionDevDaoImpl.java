package org.gestoresmadrid.core.consultaDev.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.consultaDev.model.dao.SuscripcionDevDao;
import org.gestoresmadrid.core.consultaDev.model.vo.SuscripcionDevVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SuscripcionDevDaoImpl extends GenericDaoImplHibernate<SuscripcionDevVO> implements SuscripcionDevDao{

	private static final long serialVersionUID = 5093505032775053259L;

	@Override
	public SuscripcionDevVO getSuscripcionDevPorId(Long idSuscripcion) {
		Criteria criteria = getCurrentSession().createCriteria(SuscripcionDevVO.class);
		criteria.add(Restrictions.eq("idSuscripcionDev", idSuscripcion));
		criteria.createAlias("consultaDev", "consultaDev", CriteriaSpecification.LEFT_JOIN);
		return (SuscripcionDevVO) criteria.uniqueResult();
	}

	@Override
	public List<SuscripcionDevVO> getTodasSuscripcionesDevPorIdConsultaDev(Long idConsultaDev) {

		Criteria criteria = getCurrentSession().createCriteria(SuscripcionDevVO.class);
		criteria.add(Restrictions.eq("idConsultaDev", idConsultaDev));

		return criteria.list();
	}

}