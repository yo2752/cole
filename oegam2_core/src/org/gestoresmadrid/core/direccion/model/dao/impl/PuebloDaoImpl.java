package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.PuebloDao;
import org.gestoresmadrid.core.direccion.model.vo.PuebloVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PuebloDaoImpl extends GenericDaoImplHibernate<PuebloVO> implements PuebloDao {

	private static final long serialVersionUID = 6201039914889847677L;

	@Override
	public PuebloVO getPueblo(String idProvincia, String idMunicipio, String pueblo) {
		Criteria criteria = getCurrentSession().createCriteria(PuebloVO.class);
		if (idProvincia != null && !"".equals(idProvincia)) {
			criteria.add(Restrictions.eq("id.idProvincia", idProvincia));
		}
		if (idMunicipio != null && !"".equals(idMunicipio)) {
			criteria.add(Restrictions.eq("id.idMunicipio", idMunicipio));
		}
		if (pueblo != null && !"".equals(pueblo)) {
			criteria.add(Restrictions.eq("id.pueblo", pueblo.toUpperCase().trim()));
		}
		@SuppressWarnings("unchecked")
		List<PuebloVO> lista = criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<PuebloVO> listaPueblos(String idProvincia, String idMunicipio) {
		Criteria criteria = getCurrentSession().createCriteria(PuebloVO.class);
		if (idProvincia != null && !"".equals(idProvincia)) {
			criteria.add(Restrictions.eq("id.idProvincia", idProvincia));
		}
		if (idMunicipio != null && !"".equals(idMunicipio)) {
			criteria.add(Restrictions.eq("id.idMunicipio", idMunicipio));
		}

		criteria.addOrder(Order.asc("id.pueblo"));
		@SuppressWarnings("unchecked")
		List<PuebloVO> lista = criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}
}
