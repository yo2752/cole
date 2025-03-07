package org.gestoresmadrid.core.datosSensibles.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesMatriculaDao;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DatosSensiblesMatriculaDaoImpl extends GenericDaoImplHibernate<DatosSensiblesMatriculaVO> implements DatosSensiblesMatriculaDao {

	private static final long serialVersionUID = -1179850357208923758L;

	@Override
	public List<DatosSensiblesMatriculaVO> existeMatricula(String matricula, String idGrupo) {
		return getListaMatriculas(matricula, idGrupo, null, null, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatosSensiblesMatriculaVO> getListaMatriculas(String matricula, String idGrupo, BigDecimal estado, Date fechaDesde, Date fechaHasta) {
		Criteria criteria = getCurrentSession().createCriteria(DatosSensiblesMatriculaVO.class);

		if (matricula != null) {
			criteria.add(Restrictions.eq("id.matricula", matricula));
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
	public void borradoLogico(DatosSensiblesMatriculaVO datosSensiblesMatriculaVO) {
		actualizar(datosSensiblesMatriculaVO);
	}

	@Override
	public List<DatosSensiblesMatriculaVO> buscarPorMatricula(DatosSensiblesMatriculaVO matriculaVO) {
		if (matriculaVO != null && matriculaVO.getId() != null) {
			return getListaMatriculas(matriculaVO.getId().getMatricula(), null, null, null, null);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<DatosSensiblesMatriculaVO> getListaGruposPorMatricula(String matricula, BigDecimal estado) {
		return getListaMatriculas(matricula, null, estado, null, null);
	}

	@Override
	public List<DatosSensiblesMatriculaVO> getMatriculaPorId(DatosSensiblesMatriculaVO datosSensiblesMatriculaVO) {
		if (datosSensiblesMatriculaVO != null && datosSensiblesMatriculaVO.getId() != null) {
			return getListaMatriculas(datosSensiblesMatriculaVO.getId().getMatricula(), datosSensiblesMatriculaVO.getId().getIdGrupo(), null, null, null);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<DatosSensiblesMatriculaVO> buscarPorMatricula(String matricula) {
		if (matricula != null) {
			return getListaMatriculas(matricula.toUpperCase(), null, null, null, null);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<String> getListadoMatriculasSensibles() {
		Criteria criteria = getCurrentSession().createCriteria(DatosSensiblesMatriculaVO.class);
		criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

		criteria.setProjection(Projections.distinct(Projections.property("id.matricula")));

		@SuppressWarnings("unchecked")
		List<String> lista = (List<String>) criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}
}
