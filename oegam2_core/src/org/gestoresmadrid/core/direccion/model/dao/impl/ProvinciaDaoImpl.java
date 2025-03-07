package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.ProvinciaDao;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class ProvinciaDaoImpl extends GenericDaoImplHibernate<ProvinciaVO> implements ProvinciaDao {

	private static final long serialVersionUID = -7246490763076807032L;

	@Override
	public ProvinciaVO getProvincia(String idProvincia) {
		Criteria criteria = getCurrentSession().createCriteria(ProvinciaVO.class);
		if (idProvincia != null && !"".equals(idProvincia)) {
			criteria.add(Restrictions.eq("idProvincia", idProvincia));
		}
		return (ProvinciaVO) criteria.uniqueResult();
	}
	
	@Override
	public ProvinciaVO getProvinciaPorNombre(String nombre) {
		Criteria criteria = getCurrentSession().createCriteria(ProvinciaVO.class);
		if (nombre != null && !"".equals(nombre)) {
			criteria.add(Restrictions.sqlRestriction( " NOMBRE like " + " '%"+nombre.toUpperCase()+"%'"));
		}

		List<ProvinciaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
	
	@Override
	public List<ProvinciaVO> getLista() {
		Criteria criteria = getCurrentSession().createCriteria(ProvinciaVO.class);
		return criteria.list();
	}
}