package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.MotivoItvDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MotivoItvVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MotivoItvDaoImpl extends GenericDaoImplHibernate<MotivoItvVO> implements MotivoItvDao {

	private static final long serialVersionUID = 6592064117102710290L;

	@Override
	public MotivoItvVO getMotivoItv(String idMotivoItv) {
		Criteria criteria = getCurrentSession().createCriteria(MotivoItvVO.class);
		criteria.add(Restrictions.eq("idMotivoItv", idMotivoItv));
		return (MotivoItvVO) criteria.uniqueResult();
	}

	@Override
	public List<MotivoItvVO> getListaMotivoItv() {
		Criteria criteria = getCurrentSession().createCriteria(MotivoItvVO.class);
		criteria.addOrder(Order.asc("descripcion"));
		@SuppressWarnings("unchecked")
		List<MotivoItvVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}
}
