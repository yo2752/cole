package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.TipoCargoDao;
import org.gestoresmadrid.core.registradores.model.vo.TipoCargoVO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class TipoCargoDaoImpl extends GenericDaoImplHibernate<TipoCargoVO> implements TipoCargoDao, Serializable {

	private static final long serialVersionUID = 9015097810592540758L;

	@Override
	public List<TipoCargoVO> getTipoCargo() {
		Criteria criteria = getCurrentSession().createCriteria(TipoCargoVO.class);
		@SuppressWarnings("unchecked")
		List<TipoCargoVO> lista = criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista;
		} else {
			return Collections.emptyList();
		}
	}
}
