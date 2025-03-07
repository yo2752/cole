package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.DgtProvinciaDao;
import org.gestoresmadrid.core.direccion.model.vo.DgtProvinciaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DgtProvinciaDaoImpl extends GenericDaoImplHibernate<DgtProvinciaVO> implements DgtProvinciaDao {

	private static final long serialVersionUID = 9040386387225130711L;

	@Override
	public DgtProvinciaVO getDgtProvincia(String idProvincia) {
		Criteria criteria = getCurrentSession().createCriteria(DgtProvinciaVO.class);
		if (idProvincia != null && !idProvincia.isEmpty()) {
			criteria.add(Restrictions.eq("idDgtProvincia", idProvincia));
		}
		@SuppressWarnings("unchecked")
		List<DgtProvinciaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}
