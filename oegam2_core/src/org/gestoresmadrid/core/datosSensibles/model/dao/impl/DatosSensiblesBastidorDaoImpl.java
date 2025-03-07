package org.gestoresmadrid.core.datosSensibles.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesBastidorDao;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DatosSensiblesBastidorDaoImpl extends GenericDaoImplHibernate<DatosSensiblesBastidorVO> implements DatosSensiblesBastidorDao {

	private static final long serialVersionUID = 1262236370405642661L;

	@Override
	public List<DatosSensiblesBastidorVO> existeBastidor(String bastidor, String idGrupo) {
		return getBastidores(bastidor, idGrupo, null, null, null, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatosSensiblesBastidorVO> getBastidores(String bastidor, String idGrupo, BigDecimal estado, Date fechaDesde, Date fechaHasta, BigDecimal tipoBastidor) {

		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(DatosSensiblesBastidorVO.class);

		if (bastidor != null && !bastidor.isEmpty()) {
			criteria.add(Restrictions.eq("id.bastidor", bastidor));
		}
		if (idGrupo != null && !idGrupo.isEmpty()) {
			criteria.add(Restrictions.eq("id.idGrupo", idGrupo));
		}

		if (estado != null) {
			criteria.add(Restrictions.eq("estado", estado));
		}

		if (fechaDesde != null) {
			criteria.add(Restrictions.ge("fechaAlta", fechaDesde));
		}

		if (fechaHasta != null) {
			criteria.add(Restrictions.lt("fechaAlta", fechaHasta));
		}

		if (tipoBastidor != null) {
			criteria.add(Restrictions.eq("tipoBastidor", tipoBastidor));
		}

		criteria.createAlias("grupo", "grupo", CriteriaSpecification.LEFT_JOIN);

		return criteria.list();
	}

	@Override
	public void borradoLogico(DatosSensiblesBastidorVO datosSensiblesBastidorVO) {
		actualizar(datosSensiblesBastidorVO);
	}

	@Override
	public List<DatosSensiblesBastidorVO> buscarPorBastidor(String bastidor) {
		if (bastidor != null) {
			return getBastidores(bastidor.toUpperCase(), null, null, null, null, null);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<DatosSensiblesBastidorVO> buscarPorBastidorYGrupo(DatosSensiblesBastidorVO bastidorVO) {
		if ((bastidorVO != null && bastidorVO.getId() != null) && (bastidorVO.getGrupo() != null && bastidorVO.getGrupo().getIdGrupo() != null)) {
			return getBastidores(bastidorVO.getId().getBastidor(), bastidorVO.getGrupo().getIdGrupo(), null, null, null, null);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<DatosSensiblesBastidorVO> getlistaGruposPorBastidor(String bastidor, BigDecimal estado) {
		return getBastidores(bastidor, null, estado, null, null, null);
	}

	@Override
	public List<DatosSensiblesBastidorVO> getBastidorPorId(DatosSensiblesBastidorVO bastidorVO) {
		if (bastidorVO != null && bastidorVO.getId() != null) {
			return getBastidores(bastidorVO.getId().getBastidor(), bastidorVO.getId().getIdGrupo(), null, null, null, null);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<String> getListadoBastidoresSensibles() {
		Criteria criteria = getCurrentSession().createCriteria(DatosSensiblesBastidorVO.class);
		criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

		criteria.setProjection(Projections.distinct(Projections.property("id.bastidor")));

		@SuppressWarnings("unchecked")
		List<String> lista = (List<String>) criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}
}
