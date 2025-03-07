package org.gestoresmadrid.core.datosSensibles.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesNifDao;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesNifVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DatosSensiblesNifDaoImpl extends GenericDaoImplHibernate<DatosSensiblesNifVO> implements DatosSensiblesNifDao {

	private static final long serialVersionUID = 6750755525958222450L;

	@Override
	public List<DatosSensiblesNifVO> existeNif(String nif, String idGrupo) {
		return getListaNif(nif, idGrupo, null, null, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatosSensiblesNifVO> getListaNif(String nif, String idGrupo, BigDecimal estado, Date fechaDesde, Date fechaHasta) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(DatosSensiblesNifVO.class);

		if (nif != null) {
			criteria.add(Restrictions.eq("id.nif", nif));
		}

		if (idGrupo != null) {
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

		criteria.createAlias("grupo", "grupo", CriteriaSpecification.LEFT_JOIN);

		return criteria.list();
	}

	@Override
	public void borradoLogico(DatosSensiblesNifVO datosSensiblesNifVO) {
		actualizar(datosSensiblesNifVO);
	}

	@Override
	public List<DatosSensiblesNifVO> getListaGruposPorNif(String nif, BigDecimal estado) {
		return getListaNif(nif, null, estado, null, null);
	}

	@Override
	public List<DatosSensiblesNifVO> getNif(DatosSensiblesNifVO datosSensiblesNifVO) {
		if (datosSensiblesNifVO != null && datosSensiblesNifVO.getId() != null) {
			return getListaNif(datosSensiblesNifVO.getId().getNif(), null, null, null, null);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<DatosSensiblesNifVO> getNifPorId(DatosSensiblesNifVO datosSensiblesNifVO) {
		if (datosSensiblesNifVO != null && datosSensiblesNifVO.getId() != null) {
			return getListaNif(datosSensiblesNifVO.getId().getNif(), datosSensiblesNifVO.getId().getIdGrupo(), null, null, null);
		} else {
			return Collections.emptyList();
		}
	}

	// Mantis 0025269 Eduardo Puerro
	@Override
	public List<DatosSensiblesNifVO> buscarporNif(String nif) {
		return getListaNif(nif, null, null, null, null);
	}

	@Override
	public List<String> getListadoNifsSensibles() {
		Criteria criteria = getCurrentSession().createCriteria(DatosSensiblesNifVO.class);
		criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

		criteria.setProjection(Projections.distinct(Projections.property("id.nif")));

		@SuppressWarnings("unchecked")
		List<String> lista = (List<String>) criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}
}
