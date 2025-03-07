package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.general.model.vo.IpNoPermitidasVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

@Repository
public class IpNoPermitidasDaoImpl extends GenericDaoImplHibernate<IpNoPermitidasVO> implements IpNoPermitidasDao {

	private static final long serialVersionUID = 4125058012012455648L;

	@SuppressWarnings("unchecked")
	@Override
	public List<IpNoPermitidasVO> getListadoIpNoPermitidas() {
		Criteria criteria = getCurrentSession().createCriteria(IpNoPermitidasVO.class);

		criteria.createAlias("colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);

		return criteria.list();
	}
}
