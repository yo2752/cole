package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.TipoMotorDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoMotorVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoMotorDaoIml extends GenericDaoImplHibernate<TipoMotorVO> implements TipoMotorDao, Serializable {

	private static final long serialVersionUID = -8168454344899347666L;

	@Override
	public TipoMotorVO getTipoMotor(String tipoMotor) {
		List<Criterion> listCriterion = new ArrayList<>();
		listCriterion.add(Restrictions.like("tipoMotor", tipoMotor));

		List<TipoMotorVO> lista = buscarPorCriteria(listCriterion, null, null);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoMotorVO> getListaTipoMotores() {
		return getCurrentSession().createCriteria(TipoMotorVO.class).list();
	}

}