package org.gestoresmadrid.core.general.model.dao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.HistoricoCreditoDao;
import org.gestoresmadrid.core.general.model.vo.CreditoVO;
import org.gestoresmadrid.core.general.model.vo.HistoricoCreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.web.OegamExcepcion;

@Repository
public class HistoricoCreditoDaoImpl extends GenericDaoImplHibernate<HistoricoCreditoVO> implements HistoricoCreditoDao {

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
	public List<HistoricoCreditoVO> consultaCreditosAcumuladosMesPorColegiado(Long idContrato) {
		List<HistoricoCreditoVO> listaFinal = new ArrayList<>();
		FechaFraccionada fecha = utilesFecha.getFechaFracionadaActual();
		fecha.setDiaInicio("01");

		Fecha fechaFinal;
		try {
			fechaFinal = utilesFecha.getPrimerLaborablePosterior(utilesFecha.getFechaActual());
		} catch (ParseException | OegamExcepcion e) {
			log.error("Se ha producido un error al obtener los creditos disponible ", e);
			fechaFinal = utilesFecha.getFechaActual();
		}

		fecha.setDiaFin(fechaFinal.getDia());
		fecha.setMesFin(fechaFinal.getMes());
		fecha.setAnioFin(fechaFinal.getAnio());

		Criteria criteria = getCurrentSession().createCriteria(HistoricoCreditoVO.class);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		criteria.createAlias("credito", "credito", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("credito.tipoCreditoVO", "creditoTipoCreditoVO", CriteriaSpecification.LEFT_JOIN);

		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("tipoCredito"))
				.add(Projections.alias(Projections.groupProperty("creditoTipoCreditoVO.descripcion"), "descripcion"))
				.add(Projections.alias(Projections.sum("cantidadSumada"), "sumada")));

		criteria.add(Restrictions.isNotNull("cantidadSumada"));
		criteria.add(Restrictions.ge("fecha", fecha.getFechaInicio()));
		criteria.add(Restrictions.lt("fecha", fecha.getFechaFin()));
		criteria.addOrder(Order.asc("tipoCredito"));
		@SuppressWarnings("unchecked")
		List<Object[]> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			for (Object[] element : lista) {
				HistoricoCreditoVO historicoCredito = new HistoricoCreditoVO();
				historicoCredito.setCredito(new CreditoVO());
				historicoCredito.getCredito().setTipoCreditoVO(new TipoCreditoVO());
				historicoCredito.getCredito().getTipoCreditoVO().setTipoCredito((String) element[0]);
				historicoCredito.setTipoCredito((String) element[0]);
				historicoCredito.getCredito().getTipoCreditoVO().setDescripcion((String) element[1]);
				historicoCredito.setCantidadSumada((BigDecimal) element[2]);
				listaFinal.add(historicoCredito);
			}
			return listaFinal;
		}
		return Collections.emptyList();
	}

}