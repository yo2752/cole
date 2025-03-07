package org.gestoresmadrid.core.trafico.justificante.profesional.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.dao.JustificanteProfDao;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class JustificanteProfDaoImpl extends GenericDaoImplHibernate<JustificanteProfVO> implements JustificanteProfDao {

	private static final long serialVersionUID = -8251405447374319750L;

	@Override
	public List<JustificanteProfVO> getJustificante(Long idJustificanteInterno, BigDecimal numExpediente, BigDecimal estado) {
		if (idJustificanteInterno != null || numExpediente != null) {
			List<Criterion> listCriterion = new ArrayList<Criterion>();
			if (idJustificanteInterno != null) {
				listCriterion.add(Restrictions.eq("idJustificanteInterno", idJustificanteInterno));
			}

			if (numExpediente != null) {
				listCriterion.add(Restrictions.eq("tramiteTrafico.numExpediente", numExpediente));
			}

			if (estado != null && (idJustificanteInterno != null || numExpediente != null)) {
				listCriterion.add(Restrictions.eq("estado", estado));
			}
			return buscarPorCriteria(listCriterion, null, null);
		}
		return null;
	}

	@Override
	public JustificanteProfVO getJustificanteProfPorIdInterno(Long idJustificanteInterno) {
		Criteria criteria = getCurrentSession().createCriteria(JustificanteProfVO.class);
		criteria.add(Restrictions.eq("idJustificanteInterno", idJustificanteInterno));
		criteria.createAlias("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.vehiculo", "tramiteTraficoVehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.jefaturaTrafico", "tramiteTraficoJefatura", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.contrato", "tramiteTraficoContrato", CriteriaSpecification.LEFT_JOIN);
		return (JustificanteProfVO) criteria.uniqueResult();
	}

	@Override
	public JustificanteProfVO getJustificanteProfPorIdJustificante(BigDecimal idJustificante) {
		Criteria criteria = getCurrentSession().createCriteria(JustificanteProfVO.class);
		criteria.add(Restrictions.eq("idJustificante", idJustificante));
		criteria.createAlias("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.vehiculo", "tramiteTraficoVehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.jefaturaTrafico", "tramiteTraficoJefatura", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.contrato", "tramiteTraficoContrato", CriteriaSpecification.LEFT_JOIN);
		return (JustificanteProfVO) criteria.uniqueResult();
	}

	@Override
	public JustificanteProfVO getJustificanteProfPorNumExpediente(BigDecimal numExpediente, Boolean justificanteCompleto, BigDecimal estadoJustificante) {
		Criteria criteria = getCurrentSession().createCriteria(JustificanteProfVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		if (estadoJustificante != null) {
			criteria.add(Restrictions.eq("estado", estadoJustificante));
		}
		if (justificanteCompleto) {
			criteria.createAlias("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tramiteTrafico.vehiculo", "tramiteTraficoVehiculo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tramiteTrafico.jefaturaTrafico", "tramiteTraficoJefatura", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tramiteTrafico.contrato", "tramiteTraficoContrato", CriteriaSpecification.LEFT_JOIN);
		}
		criteria.addOrder(Order.desc("idJustificante"));
		List<JustificanteProfVO> lista = (List<JustificanteProfVO>) criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public JustificanteProfVO getIdJustificanteInternoPorIdJustificante(String idJustificante) {
		Criteria criteria = getCurrentSession().createCriteria(JustificanteProfVO.class);
		criteria.add(Restrictions.eq("idJustificante", new BigDecimal(Long.parseLong(idJustificante))));
		return (JustificanteProfVO) criteria.uniqueResult();
	}

	@Override
	public JustificanteProfVO getJustificanteCodigoVerificacion(String codigoVerficiacion) {
		Criteria criteria = getCurrentSession().createCriteria(JustificanteProfVO.class);
		criteria.add(Restrictions.eq("codigoVerificacion", codigoVerficiacion));
		return (JustificanteProfVO) criteria.uniqueResult();
	}

	@Override
	public List<JustificanteProfVO> getJustificantes(String numColegiado, String matricula, String nif) {
		Criteria criteria = getCurrentSession().createCriteria(JustificanteProfVO.class);

		Criteria criteriaTramiteTrafico = criteria.createCriteria("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		criteriaTramiteTrafico.add(Restrictions.in("tipoTramite", new String[] { TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), TipoTramiteTrafico.Duplicado.getValorEnum(),
				TipoTramiteTrafico.Transmision.getValorEnum() }));

		if ((numColegiado != null && !"".equals(numColegiado)) || (matricula != null && !"".equals(matricula))) {
			Criteria criteriaVehiculo = criteriaTramiteTrafico.createCriteria("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
			if (numColegiado != null && !"".equals(numColegiado)) {
				criteriaVehiculo.add(Restrictions.eq("numColegiado", numColegiado));
			}
			if (matricula != null && !"".equals(matricula)) {
				criteriaVehiculo.add(Restrictions.eq("matricula", matricula));
			}
		}

		if (nif != null && !"".equals(nif)) {
			Criteria criteriaInterviniente = criteriaTramiteTrafico.createCriteria("intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN);

			criteriaInterviniente.add(Restrictions.or(Restrictions.and(Restrictions.eq("id.nif", nif), Restrictions.eq("id.tipoInterviniente", TipoInterviniente.Adquiriente.getValorEnum())),
					Restrictions.and(Restrictions.eq("id.nif", nif), Restrictions.eq("id.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()))));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<JustificanteProfVO> lista = criteria.list();

		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}

	@Override
	public Integer comprobarJustificantesPorMatriculaNifYFecha(Long idJustificanteInterno, String matricula, String nif, Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(JustificanteProfVO.class);

		if (idJustificanteInterno != null) {
			criteria.add(Restrictions.ne("idJustificanteInterno", idJustificanteInterno));
		}

		if (fecha != null) {
			criteria.add(Restrictions.gt("fechaFin", fecha));
		}

		Criteria criteriaTramiteTrafico = criteria.createCriteria("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		criteriaTramiteTrafico.add(Restrictions.in("tipoTramite", new String[] { TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), TipoTramiteTrafico.Duplicado.getValorEnum(),
				TipoTramiteTrafico.Transmision.getValorEnum() }));

		if ((matricula != null && !"".equals(matricula))) {
			Criteria criteriaVehiculo = criteriaTramiteTrafico.createCriteria("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
			if (matricula != null && !"".equals(matricula)) {
				criteriaVehiculo.add(Restrictions.eq("matricula", matricula));
			}
		}

		if (nif != null && !"".equals(nif)) {
			Criteria criteriaInterviniente = criteriaTramiteTrafico.createCriteria("intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN);

			criteriaInterviniente.add(Restrictions.or(Restrictions.and(Restrictions.eq("id.nif", nif), Restrictions.eq("id.tipoInterviniente", TipoInterviniente.Adquiriente.getValorEnum())),
					Restrictions.and(Restrictions.eq("id.nif", nif), Restrictions.eq("id.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()))));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public Integer existenJustificantesPorMatriculaEnEstadoPendiente(Long idJustificanteInterno, String matricula) {
		Criteria criteria = getCurrentSession().createCriteria(JustificanteProfVO.class);

		if (idJustificanteInterno != null) {
			criteria.add(Restrictions.ne("idJustificanteInterno", idJustificanteInterno));
		}

		BigDecimal[] estado = new BigDecimal[2];
		estado[0] = new BigDecimal(EstadoJustificante.Pendiente_DGT.getValorEnum());
		estado[1] = new BigDecimal(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum());
		criteria.add(Restrictions.in("estado", estado));

		Criteria criteriaTramiteTrafico = criteria.createCriteria("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		criteriaTramiteTrafico.add(Restrictions.in("tipoTramite", new String[] { TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), TipoTramiteTrafico.Duplicado.getValorEnum(),
				TipoTramiteTrafico.Transmision.getValorEnum() }));

		if ((matricula != null && !"".equals(matricula))) {
			Criteria criteriaVehiculo = criteriaTramiteTrafico.createCriteria("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
			if (matricula != null && !"".equals(matricula)) {
				criteriaVehiculo.add(Restrictions.eq("matricula", matricula));
			}
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JustificanteProfVO> obtenerJustificantesNoFinalizados(Date fechaHoy, Date fechaMenosDiezDias) {
		Criteria criteria = getCurrentSession().createCriteria(JustificanteProfVO.class);

		List<BigDecimal> estadosFinalizados = new ArrayList<BigDecimal>();
		estadosFinalizados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()));
		estadosFinalizados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		estadosFinalizados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
		estadosFinalizados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

		criteria.add(Restrictions.not(Restrictions.in("tramiteTrafico.estado", estadosFinalizados)));
		criteria.add(Restrictions.between("fechaInicio", fechaMenosDiezDias, fechaHoy));

		criteria.add(Restrictions.eq("tramiteTrafico.tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		criteria.createAlias("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.vehiculo", "tramiteTraficoVehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.intervinienteTraficos", "tramiteTraficoIntervinientes", CriteriaSpecification.LEFT_JOIN);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public Integer numeroTramitesJustificantes(Date fechaHoy, Date fechaMenosDiezDias, String matricula, String numColegiado, String dniAdquiriente) {
		Criteria criteria = getCurrentSession().createCriteria(JustificanteProfVO.class);

		List<BigDecimal> estadosFinalizados = new ArrayList<BigDecimal>();
		estadosFinalizados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()));
		estadosFinalizados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		estadosFinalizados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
		estadosFinalizados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

		criteria.add(Restrictions.not(Restrictions.in("tramiteTrafico.estado", estadosFinalizados)));
		criteria.add(Restrictions.between("fechaInicio", fechaMenosDiezDias, fechaHoy));

		criteria.add(Restrictions.eq("tramiteTrafico.tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		criteria.add(Restrictions.eq("tramiteTraficoVehiculo.matricula", matricula));
		criteria.add(Restrictions.eq("tramiteTrafico.numColegiado", numColegiado));
		criteria.add(Restrictions.and(Restrictions.eq("tramiteTraficoIntervinientes.id.nif", dniAdquiriente), Restrictions.eq("tramiteTraficoIntervinientes.id.tipoInterviniente",
				TipoInterviniente.Adquiriente.getValorEnum())));

		criteria.createAlias("tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.vehiculo", "tramiteTraficoVehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTrafico.intervinienteTraficos", "tramiteTraficoIntervinientes", CriteriaSpecification.LEFT_JOIN);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public List<JustificanteProfVO> listadoJustificantesNoUltimadosEstadisticas(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones) {
		try {
			Criteria crit = createCriterion(filter);
				
			anadirEntitiesJoin(crit, entitiesJoin);
			anadirFetchModesJoin(crit, fetchModeJoinList);
			anadirListaProyecciones(crit, listaProyecciones);
			crit.addOrder(Order.asc("tramiteTraficoVehiculo.matricula"));

			@SuppressWarnings("unchecked")
			List<JustificanteProfVO> lista = crit.list();
			if (lista != null && !lista.isEmpty()) {
				return lista;
			}
		} catch (HibernateException | SecurityException e) {
			log.error("Un error ha ocurrido al obtener la lista de justificantes no ultimados por criterios del objeto de tipo " + getType().getName(), e);
		}
		return Collections.emptyList();
	}
	
}