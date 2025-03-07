package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.TipoViaCamDao;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaCamVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoViaCamDaoImpl extends GenericDaoImplHibernate<TipoViaCamVO> implements TipoViaCamDao {

	private static final long serialVersionUID = -7023046394845027095L;

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoViaCamVO> getListaTipoVias() {
		Criteria criteria = getCurrentSession().createCriteria(TipoViaCamVO.class);
		return criteria.list();
	}

	@Override
	public TipoViaCamVO getTipoVia(String idTipoVia) {
		Criteria criteria = getCurrentSession().createCriteria(TipoViaCamVO.class);
		criteria.add(Restrictions.eq("idTipoViaCam", idTipoVia));
		return (TipoViaCamVO) criteria.uniqueResult();
	}
}