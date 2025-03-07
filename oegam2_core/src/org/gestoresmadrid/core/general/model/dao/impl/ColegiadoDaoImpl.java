package org.gestoresmadrid.core.general.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.ColegiadoDao;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ColegiadoDaoImpl extends GenericDaoImplHibernate<ColegiadoVO> implements ColegiadoDao {

	private static final long serialVersionUID = 3616195798555875646L;
	private static final String USUARIO = "usuario";

	// COLEGIADO
	@Override
	public ColegiadoVO getColegiado(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(ColegiadoVO.class);
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}
		criteria.createAlias(USUARIO, USUARIO, CriteriaSpecification.LEFT_JOIN);
		@SuppressWarnings("unchecked")
		List<ColegiadoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public ColegiadoVO getColegiadoPorUsuario(BigDecimal idUsario) {
		Criteria criteria = getCurrentSession().createCriteria(ColegiadoVO.class);
		criteria.add(Restrictions.eq("usuario.idUsuario", idUsario.longValue()));
		criteria.createAlias(USUARIO, USUARIO, CriteriaSpecification.LEFT_JOIN);
		return (ColegiadoVO) criteria.uniqueResult();
	}
}