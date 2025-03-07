package org.gestoresmadrid.core.general.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.ResumenCargaCreditosDao;
import org.gestoresmadrid.core.general.model.vo.HistoricoCreditoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ResumenCargaCreditosDaoImpl extends GenericDaoImplHibernate<HistoricoCreditoVO> implements ResumenCargaCreditosDao {

	private static final long serialVersionUID = 7986095414287137086L;

	private static final String horaFinDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public List<BigDecimal[]> cantidadesTotales(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(HistoricoCreditoVO.class);
		if (idContrato != null) {
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		if (tipoCredito != null && !tipoCredito.isEmpty()) {
			criteria.add(Restrictions.eq("tipoCredito", tipoCredito));
		}

		if (fechaDesde != null && fechaHasta != null) {
			utilesFecha.setHoraEnDate(fechaHasta, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaHasta, N_SEGUNDOS);
			criteria.add(Restrictions.between("fecha", fechaDesde, fechaHasta));
		} else if (fechaDesde != null) {
			criteria.add(Restrictions.ge("fecha", fechaDesde));
		} else if (fechaHasta != null) {
			utilesFecha.setHoraEnDate(fechaHasta, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaHasta, N_SEGUNDOS);
			criteria.add(Restrictions.le("fecha", fechaHasta));
		}

		aniadirAlias(criteria);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.sum("cantidadSumada"));
		projectionList.add(Projections.sum("cantidadRestada"));
		criteria.setProjection(projectionList);

		@SuppressWarnings("unchecked")
		List<BigDecimal[]> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

	@Override
	public int numeroElementos(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception {
		int result = -1;
		try {
			Criteria criteria = getCurrentSession().createCriteria(HistoricoCreditoVO.class);
			if (idContrato != null) {
				criteria.add(Restrictions.eq("idContrato", idContrato));
			}
			if (tipoCredito != null && !tipoCredito.isEmpty()) {
				criteria.add(Restrictions.eq("tipoCredito", tipoCredito));
			}

			if (fechaDesde != null && fechaHasta != null) {
				utilesFecha.setHoraEnDate(fechaHasta, horaFinDia);
				utilesFecha.setSegundosEnDate(fechaHasta, N_SEGUNDOS);
				criteria.add(Restrictions.between("fecha", fechaDesde, fechaHasta));
			} else if (fechaDesde != null) {
				criteria.add(Restrictions.ge("fecha", fechaDesde));
			} else if (fechaHasta != null) {
				utilesFecha.setHoraEnDate(fechaHasta, horaFinDia);
				utilesFecha.setSegundosEnDate(fechaHasta, N_SEGUNDOS);
				criteria.add(Restrictions.le("fecha", fechaHasta));
			}

			aniadirAlias(criteria);

			ProjectionList listaProyecciones = Projections.projectionList();
			listaProyecciones.add(Projections.groupProperty("tipoCredito"));
			listaProyecciones.add(Projections.groupProperty("idContrato"));
			listaProyecciones.add(Projections.groupProperty("contratoColegiado.numColegiado"));
			listaProyecciones.add(Projections.groupProperty("contrato.razonSocial"));
			listaProyecciones.add(Projections.groupProperty("creditotipoCreditoVO.descripcion"));
			listaProyecciones.add(Projections.groupProperty("credito.creditos"));
			listaProyecciones.add(Projections.sum("cantidadSumada"));
			listaProyecciones.add(Projections.sum("cantidadRestada"));
			criteria.setProjection(listaProyecciones);

			@SuppressWarnings("unchecked")
			List<BigDecimal[]> lista = criteria.list();
			if (lista != null && !lista.isEmpty()) {
				return (Integer) lista.size();
			}
			return 0;

		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al obtener  el número de elementos por criterios del objeto de tipo " + getType().getName(), e);
		}
		return result;

	}

	// Mantis 25837
	@Override
	public List<Object[]> getListaExportarTablaResumenCargaCreditos(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception {
		try {
			Criteria criteria = getCurrentSession().createCriteria(HistoricoCreditoVO.class);
			if (idContrato != null) {
				criteria.add(Restrictions.eq("idContrato", idContrato));
			}
			if (tipoCredito != null && !tipoCredito.isEmpty()) {
				criteria.add(Restrictions.eq("tipoCredito", tipoCredito));
			}

			if (fechaDesde != null && fechaHasta != null) {
				utilesFecha.setHoraEnDate(fechaHasta, horaFinDia);
				utilesFecha.setSegundosEnDate(fechaHasta, N_SEGUNDOS);
				criteria.add(Restrictions.between("fecha", fechaDesde, fechaHasta));
			} else if (fechaDesde != null) {
				criteria.add(Restrictions.ge("fecha", fechaDesde));
			} else if (fechaHasta != null) {
				utilesFecha.setHoraEnDate(fechaHasta, horaFinDia);
				utilesFecha.setSegundosEnDate(fechaHasta, N_SEGUNDOS);
				criteria.add(Restrictions.le("fecha", fechaHasta));
			}

			aniadirAlias(criteria);

			ProjectionList listaProyecciones = Projections.projectionList();
			listaProyecciones.add(Projections.groupProperty("tipoCredito"));
			listaProyecciones.add(Projections.groupProperty("idContrato"));
			listaProyecciones.add(Projections.groupProperty("contrato.via"));
			listaProyecciones.add(Projections.groupProperty("contratoColegiado.numColegiado"));
			listaProyecciones.add(Projections.groupProperty("contrato.razonSocial"));
			listaProyecciones.add(Projections.groupProperty("creditotipoCreditoVO.descripcion"));
			listaProyecciones.add(Projections.groupProperty("credito.creditos"));
			listaProyecciones.add(Projections.sum("cantidadSumada"));
			listaProyecciones.add(Projections.sum("cantidadRestada"));
			criteria.setProjection(listaProyecciones);

			@SuppressWarnings("unchecked")
			List<Object[]> lista = criteria.list();
			if (lista != null && !lista.isEmpty()) {
				return lista;
			}
			Collections.emptyList();

		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al obtener  el número de elementos por criterios del objeto de tipo " + getType().getName(), e);
		}
		return Collections.emptyList();

	}

	private void aniadirAlias(Criteria criteria) {

		criteria.createAlias("credito", "credito", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("credito.tipoCreditoVO", "creditotipoCreditoVO", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.INNER_JOIN);

	}
}
