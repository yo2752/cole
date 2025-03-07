package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.TipoViaDao;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoViaDaoImpl extends GenericDaoImplHibernate<TipoViaVO> implements TipoViaDao {

	private static final long serialVersionUID = -8331146887339989173L;

	@Override
	public TipoViaVO getTipoVia(String idTipoVia) {
		Criteria criteria = getCurrentSession().createCriteria(TipoViaVO.class);
		if (idTipoVia != null && !idTipoVia.isEmpty()) {
			criteria.add(Restrictions.eq("idTipoVia", idTipoVia));
		}
		@SuppressWarnings("unchecked")
		List<TipoViaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public TipoViaVO getIdTipoVia(String descTipoVia) {
		Criteria criteria = getCurrentSession().createCriteria(TipoViaVO.class);
		if (descTipoVia != null && !descTipoVia.isEmpty()) {
			criteria.add(Restrictions.eq("nombre", descTipoVia));
		}
		@SuppressWarnings("unchecked")
		List<TipoViaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<TipoViaVO> listadoTipoVias(List<String> listaIdTipoVia) {
		Criteria criteria = getCurrentSession().createCriteria(TipoViaVO.class);
		if (listaIdTipoVia != null && !listaIdTipoVia.isEmpty()) {
			criteria.add(Restrictions.in("idTipoVia", listaIdTipoVia));
		}
		criteria.add(Restrictions.eq("obsoleto", "NO"));

		criteria.addOrder(Order.asc("nombre"));

		List<TipoViaVO> lista = (List<TipoViaVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public TipoViaVO getTipoViaDgt(String idTipoViaDgt) {
		Criteria criteria = getCurrentSession().createCriteria(TipoViaVO.class);
		if (idTipoViaDgt != null && !idTipoViaDgt.isEmpty()) {
			criteria.add(Restrictions.eq("idTipoDgt", idTipoViaDgt));
		}
		@SuppressWarnings("unchecked")
		List<TipoViaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}