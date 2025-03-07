package org.gestoresmadrid.core.trafico.inteve.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.inteve.model.dao.TramiteTraficoInteveDao;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.FechaFraccionada;

@Repository
public class TramiteTraficoInteveDaoImpl  extends GenericDaoImplHibernate<TramiteTraficoInteveVO> implements TramiteTraficoInteveDao {

	private static final long serialVersionUID = 6113532562363481430L;

	@Override
	public TramiteTraficoInteveVO getTramiteInteve(BigDecimal numExpediente, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoInteveVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		if(tramiteCompleto){
			criteria.createAlias("tramitesInteves", "tramitesInteves", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficosPersona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN);
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (TramiteTraficoInteveVO) criteria.uniqueResult();
	}

	@Override
	public int numeroElementos(String bastidor, String codigoTasa, BigDecimal estado, FechaFraccionada fechaAlta,
			FechaFraccionada fechaPresentacion, Long idContrato, String matricula, String nive,
			BigDecimal numExpediente, String tipoInforme, String tipoTramite){
		int result = -1;
		try {
			Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoInteveVO.class);
			if (StringUtils.isNotBlank(bastidor) || StringUtils.isNotBlank(matricula) || StringUtils.isNotBlank(nive)
					|| StringUtils.isNotBlank(codigoTasa) || StringUtils.isNotBlank(tipoInforme)) {
				criteria.createAlias("tramitesInteves", "tramitesInteves", CriteriaSpecification.LEFT_JOIN);
			}
			if (StringUtils.isNotBlank(bastidor)) {
				criteria.add(Restrictions.eq("tramitesInteves.bastidor", bastidor));
			}
			if (StringUtils.isNotBlank(codigoTasa)) {
				criteria.add(Restrictions.eq("tramitesInteves.codigoTasa", codigoTasa));
			}
			if (estado != null) {
				criteria.add(Restrictions.eq("estado", estado));
			}
			if (fechaAlta != null && !fechaAlta.isfechaNula()) {
				criteria.add(dameRestriccionBetween("fechaAlta",fechaAlta));
			}
			if (fechaPresentacion != null && !fechaPresentacion.isfechaNula()) {
				criteria.add(dameRestriccionBetween("fechaPresentacion",fechaPresentacion));
			}
			if (idContrato != null) {
				criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
				criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
			}
			if (StringUtils.isNotBlank(matricula)) {
				criteria.add(Restrictions.eq("tramitesInteves.matricula", matricula));
			}
			if (StringUtils.isNotBlank(nive)) {
				criteria.add(Restrictions.eq("tramitesInteves.nive", nive));
			}
			if (numExpediente != null) {
				criteria.add(Restrictions.eq("numExpediente", numExpediente));
			}
			if (StringUtils.isNotBlank(tipoInforme)) {
				criteria.add(Restrictions.eq("tramitesInteves.tipoInforme", tipoInforme));
			}
			if (StringUtils.isNotBlank(tipoTramite)) {
				criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
			}

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.setProjection(Projections.rowCount());
			return (Integer) criteria.uniqueResult();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al obtener  el número de elementos por criterios del objeto de tipo " + getType().getName(), e);
		}
		return result;
	}

	@Override
	public List<TramiteTraficoInteveVO> buscarPorCriteria(int firstResult, int maxResults, String dir, String sort,
			String bastidor, String codigoTasa, BigDecimal estado, FechaFraccionada fechaAlta,
			FechaFraccionada fechaPresentacion, Long idContrato, String matricula, String nive,
			BigDecimal numExpediente, String tipoInforme, String tipoTramite) {

		List<TramiteTraficoInteveVO> result = null;
		try {
			Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoInteveVO.class);
			if (StringUtils.isNotBlank(bastidor) || StringUtils.isNotBlank(matricula) || StringUtils.isNotBlank(nive)
					|| StringUtils.isNotBlank(codigoTasa) || StringUtils.isNotBlank(tipoInforme)) {
				criteria.createAlias("tramitesInteves", "tramitesInteves", CriteriaSpecification.LEFT_JOIN);
			}
			if (StringUtils.isNotBlank(bastidor)) {
				criteria.add(Restrictions.eq("tramitesInteves.bastidor", bastidor));
			}
			if (StringUtils.isNotBlank(codigoTasa)) {
				criteria.add(Restrictions.eq("tramitesInteves.codigoTasa", codigoTasa));
			}
			if (estado != null) {
				criteria.add(Restrictions.eq("estado", estado));
			}
			if (fechaAlta != null && !fechaAlta.isfechaNula()) {
				criteria.add(dameRestriccionBetween("fechaAlta",fechaAlta));
			}
			if (fechaPresentacion != null && !fechaPresentacion.isfechaNula()) {
				criteria.add(dameRestriccionBetween("fechaPresentacion",fechaPresentacion));
			}
			if (idContrato != null) {
				criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
				criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
			}
			if (StringUtils.isNotBlank(matricula)) {
				criteria.add(Restrictions.eq("tramitesInteves.matricula", matricula));
			}
			if (StringUtils.isNotBlank(nive)) {
				criteria.add(Restrictions.eq("tramitesInteves.nive", nive));
			}
			if (numExpediente != null) {
				criteria.add(Restrictions.eq("numExpediente", numExpediente));
			}
			if (StringUtils.isNotBlank(tipoInforme)) {
				criteria.add(Restrictions.eq("tramitesInteves.tipoInforme", tipoInforme));
			}
			if (StringUtils.isNotBlank(tipoTramite)) {
				criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
			}

			if (firstResult > 0) {
				criteria.setFirstResult(firstResult);
			}
			if (maxResults > 0) {
				criteria.setMaxResults(maxResults);
			}

			try {
				addOrder(criteria, sort, dir);
			} catch (SecurityException e) {
				log.error("No acceciendo al campo de ordenacion", e);
			} catch (NoSuchFieldException e) {
				log.error("No existe el campo de ordenacion", e);
			}
			result = criteria.list();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al hacer una búsqueda por criterios con paginación del objeto de tipo " + getType().getName(), e);
			throw e;
		}
		return result;
	}

	private Criterion dameRestriccionBetween(String propiedad, FechaFraccionada value){
		if(value!=null){
			FechaFraccionada fechaFraccionada = value;
			Date fechaIni = fechaFraccionada.getFechaMinInicio();
			Date fechaFin = fechaFraccionada.getFechaMaxFin();
			if (fechaIni != null && fechaFin != null) {
				return Restrictions.between(propiedad, fechaIni, fechaFin);
			} else if (fechaIni != null) {
				return Restrictions.ge(propiedad, fechaIni);
			} else if (fechaFin != null) {
				return Restrictions.le(propiedad, fechaFin);
			}
		}
		return null;
	}

}