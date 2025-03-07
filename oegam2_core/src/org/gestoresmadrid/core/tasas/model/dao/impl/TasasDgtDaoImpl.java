package org.gestoresmadrid.core.tasas.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.tasas.model.dao.TasasDgtDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaDgtVO;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class TasasDgtDaoImpl extends GenericDaoImplHibernate<TasaDgtVO> implements TasasDgtDao {

	private static final long serialVersionUID = 353543871031225065L;

	@Override
	public TasaDgtVO get(String codigo) {
		Session session = getCurrentSession();
		return (TasaDgtVO) session.get(TasaDgtVO.class, codigo);
	}

	@Override
	public List<TasaDgtVO> getListaTasasDgt() {
		return buscarPorCriteria(null, null, null);
	}
}
