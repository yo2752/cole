package org.gestoresmadrid.core.historicocreditos.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.CreditoFacturadoDao;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.FechaFraccionada;

@Repository
public class CreditoFacturadoDaoImpl extends GenericDaoImplHibernate<CreditoFacturadoVO> implements CreditoFacturadoDao {

	private static final long serialVersionUID = 1116055021444922677L;

	@Override
	public CreditoFacturadoVO getCreditoFacturadoVO(Long id, String... initialized) {
		try {
			Criteria criteria = getCurrentSession().createCriteria(CreditoFacturadoVO.class);
			criteria.add(Restrictions.eq("idCreditoFacturado", id));
			anadirFetchModesJoin(criteria, initialized);
			return (CreditoFacturadoVO) criteria.uniqueResult();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al buscar un objeto de tipo CreditoFacturadoVO", e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditoFacturadoVO> getListaCreditoFacturadoPorTramite(String tipTramite) {

		Criteria criteria = getCurrentSession().createCriteria(CreditoFacturadoVO.class);
		criteria.createAlias("tipoTramite", "tipoTramite", CriteriaSpecification.LEFT_JOIN);

		if (StringUtils.isNotBlank(tipTramite)) {
			criteria.add(Restrictions.eq("tipoTramite.tipoTramite", tipTramite));
		}

		return criteria.list();
	}

	public String getTipoCredito(String tipoTramite) {
		Criteria criteria = getCurrentSession().createCriteria(TipoCreditoTramiteVO.class);
		criteria.add(Restrictions.eq("tipoTramite", tipoTramite));

		criteria.setProjection(Projections.property("tipoCredito"));
		return (String) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditoFacturadoVO> buscarCreditoFacturadoTramite(CreditoFacturadoTramiteVO creditoFacturadoTramite) {
		Criteria criteria = getCurrentSession().createCriteria(CreditoFacturadoVO.class);
		criteria.createAlias("creditoFacturadoTramites", "creditoFacturadoTramite");
		criteria.add(Restrictions.eq("creditoFacturadoTramite.idTramite", creditoFacturadoTramite.getIdTramite()));

		return criteria.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditoFacturadoVO> getListaExportarHistroricoFacturado(ConceptoCreditoFacturado concepto, Long idContrato, String tipoCredito, String tramite, FechaFraccionada fechaFraccionada) {

		Criteria criteria = getCurrentSession().createCriteria(CreditoFacturadoVO.class);

		if (concepto != null) {
			criteria.add(Restrictions.eq("concepto", concepto));
		}

		if (idContrato != null) {
			criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		}

		if (fechaFraccionada != null) {
			Date fechaIni = fechaFraccionada.getFechaMinInicio();
			Date fechaFin = fechaFraccionada.getFechaMaxFin();
			if (fechaIni != null && fechaFin != null) {
				criteria.add(Restrictions.between("fecha", fechaIni, fechaFin));
			} else if (fechaIni != null) {
				criteria.add(Restrictions.ge("fecha", fechaIni));
			} else if (fechaFin != null) {
				criteria.add(Restrictions.le("fecha", fechaFin));
			}
		}

		if (StringUtils.isNotBlank(tramite)) {
			criteria.add(Restrictions.eq("creditoFacturadoTramites.idTramite", tramite));
		}

		if ((tipoCredito != null) && (!tipoCredito.equals(""))) {
			criteria.add(Restrictions.eq("tipoTramite.tipoCredito.tipoCredito", tipoCredito));
		}

		criteria.createAlias("tipoTramite", "tipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tipoTramite.tipoCredito", "tipoTramiteTipoCredito", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("creditoFacturadoTramites", "creditoFacturadoTramites", CriteriaSpecification.LEFT_JOIN);

		return criteria.list();
	}

}
