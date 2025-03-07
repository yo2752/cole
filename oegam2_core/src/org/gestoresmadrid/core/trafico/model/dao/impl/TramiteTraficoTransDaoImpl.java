package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.AcreditacionTrafico;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.ModoAdjudicacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoSINO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.model.enumerados.TipoTransferenciaActual;
import org.gestoresmadrid.core.model.enumerados.TipoVehiculoEnum;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoTransDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

@Repository
public class TramiteTraficoTransDaoImpl extends GenericDaoImplHibernate<TramiteTrafTranVO> implements TramiteTraficoTransDao {

	private static final long serialVersionUID = 351762856234850052L;

	@Override
	public int getNumElementosMasivos(Long idContrato, Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);
		if(idContrato!=null)
			criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		if(fecha!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			Date fecMin = calendar.getTime();
			calendar.setTime(fecha);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
			Date fecMax = calendar.getTime();
			Conjunction and = Restrictions.conjunction();
			and.add(Restrictions.ge("fechaPresentacion", fecMin));
			and.add(Restrictions.lt("fechaPresentacion", fecMax));
			criteria.add(and);
		}
		BigDecimal[] estados = new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) };
		criteria.add(Restrictions.in("estado", estados));
		criteria.add(Restrictions.isNull("docPermiso"));
		criteria.add(Restrictions.eq("imprPermisoCircu", "SI"));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafTranVO> getTramitesMasivos(Long idContrato, Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);
		if(idContrato!=null)
			criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		if(fecha!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			Date fecMin = calendar.getTime();
			calendar.setTime(fecha);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
			Date fecMax = calendar.getTime();
			Conjunction and = Restrictions.conjunction();
			and.add(Restrictions.ge("fechaPresentacion", fecMin));
			and.add(Restrictions.lt("fechaPresentacion", fecMax));
			criteria.add(and);
		}
		BigDecimal[] estados = new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) };
		criteria.add(Restrictions.in("estado", estados));
		criteria.add(Restrictions.eq("imprPermisoCircu", "SI"));
		criteria.add(Restrictions.isNull("docPermiso"));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TramiteTrafTranVO>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> getListaExpedientesPorTasaCambServOInforme(String codigoTasa) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);
		criteria.add(Restrictions.or(Restrictions.eq("tasa1.codigoTasa", codigoTasa), Restrictions.eq("tasa2.codigoTasa", codigoTasa)));
		criteria.createAlias("tasa1", "tasa1", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tasa2", "tasa2", CriteriaSpecification.LEFT_JOIN);
		return (List<TramiteTraficoVO>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> getListaTramitesImprimirImprPorContratoYFecha(Long idContrato, Date fechaPresentacion) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		criteria.add(Restrictions.isNull("docPermiso"));
		criteria.add(Restrictions.eq("imprPermisoCircu", "SI"));
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TramiteTraficoVO>) criteria.list();
	}

	@Override
	public Integer getTramitesFinalizadosTelematicamentePorContrato(Long idContrato, Date fechaPresentacion) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		criteria.add(Restrictions.isNull("docPermiso"));
		criteria.add(Restrictions.eq("imprPermisoCircu", "SI"));
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<TramiteTrafTranVO> getListaTramitesFinalizadosTelematicamentePorContrato(Long idContrato, Date fechaPresentacion) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		criteria.add(Restrictions.isNull("docPermiso"));
		criteria.add(Restrictions.eq("imprPermisoCircu", "SI"));
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return (List<TramiteTrafTranVO>) criteria.list();
	}

	@Override
	public TramiteTrafTranVO getTramiteTransmision(BigDecimal numExpediente, boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.provincia", "contrato.provincia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico.provincia", "jefaturaTrafico.provincia", CriteriaSpecification.LEFT_JOIN);

		if (tramiteCompleto) {
			criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tasa1", "tasa1", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tasa2", "tasa2", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo.direccion", "vehiculo.direccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}

		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
//		criteria.createAlias("tipoMotor", "tipoMotor", CriteriaSpecification.LEFT_JOIN);
//		criteria.createAlias("tipoReduccion", "tipoReduccion", CriteriaSpecification.LEFT_JOIN);
		@SuppressWarnings("unchecked")
		List<TramiteTrafTranVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}

		return null;
	}

	@Override
	public TramiteTrafTranVO datosCTIT(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);

		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN);

		criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<TramiteTrafTranVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public boolean sitexComprobarCTITPrevio(String matricula) {
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		List<Criterion> listCriterion = new ArrayList<>();

		listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo"));

		listCriterion.add(Restrictions.eq("vehiculo.matricula", matricula));
		listCriterion.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));
		listCriterion.add(Restrictions.disjunction().add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()))).add(Restrictions.eq("estado",
				new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))).add(Restrictions.eq("estado",
						new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()))));

		ArrayList<String> tiposTransferencia = new ArrayList<>();
		tiposTransferencia.add(TipoTransferencia.tipo1.getValorEnum());
		tiposTransferencia.add(TipoTransferencia.tipo2.getValorEnum());
		tiposTransferencia.add(TipoTransferencia.tipo3.getValorEnum());
		tiposTransferencia.add(TipoTransferencia.tipo4.getValorEnum());

		Criterion criterionAnd = Restrictions.and(Restrictions.in("tipoTransferencia", tiposTransferencia), Restrictions.eq("imprPermisoCircu", TipoSINO.NO.name()));
		Criterion criterionOr = Restrictions.eq("tipoTransferencia", TipoTransferencia.tipo5.getValorEnum());
		listCriterion.add(Restrictions.or(criterionAnd, criterionOr));

		List<TramiteTrafTranVO> lista = buscarPorCriteria(listCriterion, listaAlias, null);

		if (lista != null && lista.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String recuperarMismoTipoCreditoTramiteTransmision(BigDecimal[] numExpedientes, TipoTramiteTrafico tipo) {
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.rowCount());
		List<Criterion> criterionList = new ArrayList<>();
		Criterion cNumExpedientes = Restrictions.in("numExpediente", numExpedientes);
		criterionList.add(cNumExpedientes);
		Criterion estado = Restrictions.not(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()), new BigDecimal(
				EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()) }));
		criterionList.add(estado);
		Criterion crtt4 = Restrictions.eq("tipoTransferencia", TipoTransferencia.tipo4.getValorEnum());
		Criterion crtt5 = Restrictions.eq("tipoTransferencia", TipoTransferencia.tipo5.getValorEnum());
		Criterion crttT7 = Restrictions.or(crtt4, crtt5);
		Criterion crT7 = Restrictions.and(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()), crttT7);
		Criterion crtt2 = Restrictions.eq("tipoTransferencia", TipoTransferenciaActual.tipo2.getValorEnum());
		Criterion crT2 = Restrictions.and(Restrictions.eq("tipoTramite", TipoTramiteTrafico.Transmision.getValorEnum()), crtt2);
		Criterion tipoTransmision = Restrictions.or(crT7, crT2);
		criterionList.add(tipoTransmision);
		List<?> listaCreditos41 = buscarPorCriteria(criterionList, null, listaProyecciones);
		criterionList = new ArrayList<Criterion>();
		criterionList.add(cNumExpedientes);
		criterionList.add(estado);
		criterionList.add(Restrictions.not(tipoTransmision));
		List<?> listaCreditosOtros = buscarPorCriteria(criterionList, null, listaProyecciones);
		if (listaCreditos41 != null && listaCreditos41.size() == 1 && listaCreditosOtros != null && listaCreditosOtros.size() == 1 && !(((Integer) listaCreditos41.get(0)) != 0
				&& ((Integer) listaCreditosOtros.get(0)) != 0)) {
			if ((Integer) listaCreditos41.get(0) > 0 && (Integer) listaCreditosOtros.get(0) == 0) {
				if (tipo == TipoTramiteTrafico.Transmision) {
					return TipoTransferenciaActual.tipo2.getValorEnum();
				} else {
					return TipoTransferencia.tipo4.getValorEnum();
				}
			} else if ((Integer) listaCreditosOtros.get(0) > 0 && (Integer) listaCreditos41.get(0) == 0) {
				if (tipo == TipoTramiteTrafico.Transmision) {
					return TipoTransferenciaActual.tipo1.getValorEnum();
				} else {
					return TipoTransferencia.tipo1.getValorEnum();
				}
			}
			criterionList = new ArrayList<Criterion>();
			criterionList.add(cNumExpedientes);
			criterionList.add(tipoTransmision);
			List<?> lista = buscarPorCriteria(criterionList, null, listaProyecciones);
			if (lista != null && lista.size() == 1 && ((Integer) lista.get(0) != 0)) {
				if (tipo == TipoTramiteTrafico.Transmision) {
					return TipoTransferenciaActual.tipo2.getValorEnum();
				} else {
					return TipoTransferencia.tipo4.getValorEnum();
				}
			} else {
				if (tipo == TipoTramiteTrafico.Transmision) {
					return TipoTransferenciaActual.tipo1.getValorEnum();
				} else {
					return TipoTransferencia.tipo1.getValorEnum();
				}
			}
		}
		return null;
	}

	@Override
	public TramiteTrafTranVO getTramitePorTasaCamSer(String codigoTasa, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);
		criteria.add(Restrictions.eq("tasa2.codigoTasa", codigoTasa));
		criteria.createAlias("tasa2", "tasa2", CriteriaSpecification.LEFT_JOIN);

		if (tramiteCompleto) {
			criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo.direccion", "vehiculo.direccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
			criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
		}

		return (TramiteTrafTranVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public TramiteTrafTranVO getTramiteAutoliquidarCet(String matricula, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);

		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.add(Restrictions.eq("vehiculo.matricula", matricula));

		ArrayList<String> tipoTramites = new ArrayList<>();
		tipoTramites.add(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
		tipoTramites.add(TipoTramiteTrafico.Transmision.getValorEnum());
		criteria.add(Restrictions.in("tipoTramite", tipoTramites));

		ArrayList<BigDecimal> estados = new ArrayList<>();
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));
		criteria.add(Restrictions.not(Restrictions.in("estado", estados)));

		criteria.add(Restrictions.isNull("factura"));

		criteria.addOrder(Order.desc("fechaAlta"));

		List<TramiteTrafTranVO> list = (List<TramiteTrafTranVO>) criteria.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public Integer getPosibleDuplicado(String bastidor, String matricula, String nif, String tipoTramite, String numColegiado, String tipoTransferencia) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos", "interviniente");

		ArrayList<BigDecimal> estados = new ArrayList<>();
		estados.add(new BigDecimal(EstadoTramiteTrafico.Anulado.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

		if (bastidor != null && !bastidor.isEmpty()) {
			criteria.add(Restrictions.eq("vehiculo.bastidor", bastidor));
		}

		if (matricula != null && !matricula.isEmpty()) {
			criteria.add(Restrictions.eq("vehiculo.matricula", matricula));
		}

		if (TipoTransferencia.tipo5.getValorEnum().equals(tipoTransferencia)) {
			criteria.add(Restrictions.eq("interviniente.id.tipoInterviniente", TipoInterviniente.TransmitenteTrafico.getValorEnum()));
		} else {
			criteria.add(Restrictions.eq("interviniente.id.tipoInterviniente", TipoInterviniente.Adquiriente.getValorEnum()));
		}

		criteria.add(Restrictions.eq("interviniente.id.nif", nif));

		criteria.add(Restrictions.not(Restrictions.in("estado", estados)));

		criteria.add(Restrictions.eq("tipoTransferencia", tipoTransferencia));

		criteria.add(Restrictions.eq("numColegiado", numColegiado));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	// Estadisticas

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerTotalesTramitesCTIT(boolean esTelematico, Date fechaDesde, Date fechaHasta, String idProvincia) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		if (!"TOTALES".equals(idProvincia)) {
			criteria.createAlias("contrato", "contrato");
			criteria.createAlias("contrato.provincia", "prov");
			criteria.add(Restrictions.eq("prov.idProvincia", idProvincia));
		}

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount()).hashCode();
		projectionList.add(Projections.groupProperty("tipoTransferencia"));
		criteria.setProjection(projectionList);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerDetalleTramitesCTIT(boolean esTelematico, Date fechaDesde, Date fechaHasta, String idProvincia) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.add(Restrictions.ge("fechaPresentacion", fechaDesde));
		criteria.add(Restrictions.lt("fechaPresentacion", fechaHasta));

		if (esTelematico) {
			ArrayList<BigDecimal> estados = new ArrayList<>();
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

			criteria.add(Restrictions.in("estado", estados));
		} else {
			criteria.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}
		criteria.createAlias("contrato", "contrato");
		criteria.createAlias("contrato.provincia", "prov");
		if(StringUtils.isNotBlank(idProvincia)) {
			criteria.add(Restrictions.eq("prov.idProvincia", idProvincia));	
		}
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount()).hashCode();
		projectionList.add(Projections.groupProperty("numColegiado"));
		projectionList.add(Projections.groupProperty("estado"));
		criteria.setProjection(projectionList);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerListadoNumerosExpedienteCTIT(boolean esTelematico, Date fechaDesde, Date fechaHasta) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerListadoNumerosExpedienteCTITAnotacionesGest(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.add(Restrictions.ne("respuestaGest", "OK"));

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerDetalleTramitesCTITCarpetaFusion(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.setFetchMode("tv", FetchMode.JOIN);

		criteria.createAlias("vehiculo", "v");
		criteria.createAlias("vehiculo.tipoVehiculoVO", "tv");
		criteria.createAlias("tasa", "tasa");

		criteria.add(Restrictions.eq("tasa.tipoTasa", "1.6"));

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount()).hashCode();
		projectionList.add(Projections.groupProperty("tv.descripcion"));
		criteria.setProjection(projectionList);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerListaExpedientesCTITCarpetaFusion(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.createAlias("vehiculo", "v");
		criteria.createAlias("tasa", "tasa");

		criteria.add(Restrictions.eq("tasa.tipoTasa", "1.6"));

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerDetalleTramitesCTITCarpetaI(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);
		criteria = aniadirCriteriaEstadisticasCTITCarpetaI(criteria);

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount()).hashCode();
		projectionList.add(Projections.groupProperty("tv.descripcion"));
		criteria.setProjection(projectionList);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerListaExpedientesCTITCarpetaI(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);
		criteria = aniadirCriteriaEstadisticasCTITCarpetaI(criteria);

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerListaExpedientesCTITCarpetaB(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.createAlias("intervinienteTraficos", "it");

		criteria.add(Restrictions.disjunction().add(Restrictions.eq("it.tipoIntervinienteVO.tipoInterviniente", TipoInterviniente.CotitularTransmision.getValorEnum())).add(Restrictions.conjunction()
				.add(Restrictions.eq("modoAdjudicacion", ModoAdjudicacion.tipo2.getValorEnum())).add(Restrictions.eq("it.tipoIntervinienteVO.tipoInterviniente", TipoInterviniente.TransmitenteTrafico
						.getValorEnum()))).add(Restrictions.conjunction().add(Restrictions.eq("acreditaHerenciaDonacion", AcreditacionTrafico.Herencia.getValorEnum())).add(Restrictions.eq(
								"it.tipoIntervinienteVO.tipoInterviniente", TipoInterviniente.TransmitenteTrafico.getValorEnum()))));

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.distinct(Projections.property("numExpediente")));
		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerDetalleTramitesCTITVehiculosAgricolas(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.createAlias("vehiculo", "v");
		criteria.createAlias("vehiculo.tipoVehiculoVO", "tv");
		criteria.setFetchMode("tv", FetchMode.JOIN);

		criteria.add(Restrictions.isNotNull("cema"));
		criteria.add(Restrictions.eq("v.vehiculoAgricola", "SI"));
		criteria.add(Restrictions.eq("v.idServicio", "B06"));

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount()).hashCode();
		projectionList.add(Projections.groupProperty("tv.descripcion"));
		criteria.setProjection(projectionList);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerListaExpedientesCTITVehiculosAgricolas(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.createAlias("vehiculo", "v");

		criteria.add(Restrictions.isNotNull("cema"));
		criteria.add(Restrictions.eq("v.vehiculoAgricola", "SI"));
		criteria.add(Restrictions.eq("v.idServicio", "B06"));

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerDetalleTramitesCTITJudicialSubasta(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.add(Restrictions.eq("modoAdjudicacion", ModoAdjudicacion.tipo2.getValorEnum()));

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerListaExpedientesCTITSinCETNiFactura(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.add(Restrictions.disjunction().add(Restrictions.eq("cetItp", "")).add(Restrictions.isNull("cetItp")).add(Restrictions.eq("cetItp", "00000000")));

		criteria.add(Restrictions.disjunction().add(Restrictions.eq("factura", "")).add(Restrictions.isNull("factura")));

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.distinct(Projections.property("numExpediente")));
		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerListaExpedientesCTITEmbargoPrecinto(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.add(Restrictions.like("resCheckCtit", "TRAMITABLE EN JEFATURA"));

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.distinct(Projections.property("numExpediente")));
		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerDetalleTramitesCTITErrorJefatura(Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles) {
		String stringSQL = sqlEstadisticasCTITErrorEnJefaturaDetalle(listadoPosibles);
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(stringSQL);

		sqlQuery.setDate("fechaInicio", fechaDesde);
		sqlQuery.setDate("fechaFin", fechaHasta);

		if (listadoPosibles != null && !listadoPosibles.isEmpty() && listadoPosibles.size() < 1000) {
			sqlQuery.setParameterList("expedientesPosibles", listadoPosibles);
		} else {
			List<BigDecimal> listaAux = new ArrayList<>();
			int i = 1;
			int veces = 0;
			for (BigDecimal numExpediente : listadoPosibles) {
				if (i < 1000) {
					listaAux.add(numExpediente);
				} else {
					sqlQuery.setParameterList("expedientesPosibles" + veces, listaAux);
					i = 1;
					veces++;
					listaAux = new ArrayList<BigDecimal>();
				}
				i++;
			}
		}
		return sqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerListaExpedientesCTITErrorJefatura(Date fechaInicio, Date fechaFin, List<BigDecimal> listadoPosibles) {
		String stringSQL = sqlEstadisticasCTITErrorEnJefaturaListado(listadoPosibles);
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(stringSQL);

		sqlQuery.setDate("fechaInicio", fechaInicio);
		sqlQuery.setDate("fechaFin", fechaFin);

		if (listadoPosibles != null && !listadoPosibles.isEmpty() && listadoPosibles.size() < 1000) {
			sqlQuery.setParameterList("expedientesPosibles", listadoPosibles);
		} else {
			List<BigDecimal> listaAux = new ArrayList<BigDecimal>();
			int i = 1;
			int veces = 0;
			for (BigDecimal numExpediente : listadoPosibles) {
				if (i < 1000) {
					listaAux.add(numExpediente);
				} else {
					sqlQuery.setParameterList("expedientesPosibles" + veces, listaAux);
					veces++;
					i = 1;
					listaAux = new ArrayList<BigDecimal>();
				}
				i++;
			}
		}
		return (List<BigDecimal>) sqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerListaExpedientesCTITEvolucion(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles,
			List<EstadoTramiteTrafico> transicionEstados) {
		Criteria criteria = createCriteriaEstadisticasCTIT(esTelematico, fechaDesde, fechaHasta);

		criteria.createAlias("evolucionTramiteTraficos", "evol");

		String estadoAnterior = null;
		String estadoPosterior = null;

		for (int i = 1; i < transicionEstados.size(); i++) {
			estadoAnterior = transicionEstados.get(i - 1).getValorEnum();
			estadoPosterior = transicionEstados.get(i).getValorEnum();
			Type[] tipos = {Hibernate.STRING, Hibernate.STRING};
			String[] values = {estadoAnterior,estadoPosterior};
			if (estadoAnterior.equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())) {
				criteria.add(Restrictions.sqlRestriction("{alias}.num_expediente in (select num_expediente from evolucion_tramite_trafico where estado_nuevo= ?)", estadoPosterior, Hibernate.STRING));
				criteria.add(Restrictions.sqlRestriction("{alias}.num_expediente not in (select num_expediente from evolucion_tramite_trafico where estado_nuevo='"
						+ EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum() + "')"));
			} else {
				criteria.add(Restrictions.sqlRestriction("{alias}.num_expediente in (select num_expediente from evolucion_tramite_trafico where estado_anterior=? and estado_nuevo=?)",values,tipos));
			}
		}

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.distinct(Projections.property("numExpediente")));

		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerListaTramitesGestorSobreExp(List<BigDecimal> listadoPosibles) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);

		criteria = dividirListaExpedientes(criteria, listadoPosibles);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount()).hashCode();
		projectionList.add(Projections.groupProperty("numColegiado"));
		criteria.setProjection(projectionList);

		return criteria.list();
	}

	private Criteria createCriteriaEstadisticasCTIT(boolean esTelematico, Date fechaDesde, Date fechaHasta) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafTranVO.class);
		criteria.add(Restrictions.ge("fechaPresentacion", fechaDesde));
		criteria.add(Restrictions.lt("fechaPresentacion", fechaHasta));
		criteria.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));
		if (esTelematico) {
			ArrayList<BigDecimal> estados = new ArrayList<>();
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

			criteria.add(Restrictions.in("estado", estados));
		} else {
			criteria.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}
		return criteria;
	}

	private Criteria aniadirCriteriaEstadisticasCTITCarpetaI(Criteria criteria) {
		criteria.createAlias("vehiculo", "v");
		criteria.createAlias("vehiculo.tipoVehiculoVO", "tv");
		criteria.setFetchMode("tv", FetchMode.JOIN);

		List<String> listaTiposVehiculo = new ArrayList<>();
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_ARTICULADO.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_MIXTO.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.BIBLIOBUS.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_TALLER.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_SANITARIO.getValorEnum());

		List<String> listaServicios = new ArrayList<>();
		listaServicios.add("A00");
		listaServicios.add("A01");
		listaServicios.add("A02");
		listaServicios.add("A03");
		listaServicios.add("A04");
		listaServicios.add("A05");
		listaServicios.add("A07");
		listaServicios.add("A08");
		listaServicios.add("A09");
		listaServicios.add("A10");
		listaServicios.add("A11");
		listaServicios.add("A12");
		listaServicios.add("A13");
		listaServicios.add("A14");
		listaServicios.add("A15");
		listaServicios.add("A16");
		listaServicios.add("A18");
		listaServicios.add("A20");
		listaServicios.add("B07");

		List<String> listaTiposVehiculoExcluidosPeso = new ArrayList<>();
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.VEHICULO_ESPECIAL.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.PALA_CARGADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.PALA_EXCAVADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.CARRETILLA_ELEVADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MOTONIVELADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.COMPACTADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.APISONADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.GIROGRAVILLADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MACHACADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.QUITANIEVES.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.VIVIENDA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.BARREDORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.HORMIGONERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.VEHICULO_BASCULANTE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.GRUA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SERVICIO_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.ASPIRADORA_DE_FANGOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MOTOCULTOR.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_AUTOMOTRIZ.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.PALA_CARGADORA_RETROEXCAVADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TREN_HASTA_160_PLAZAS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TRACTOR.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TRACTOCAMION.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TRACTOCARRO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_BOTELLERO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_CAJA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_CISTERNA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_DE_GRUA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_FRIGORIFICO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_HORMIGONERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_JAULA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_PLATAFORMA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_TALLER.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_VIVIENDA_O_CARAVANA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_VOLQUETE_DE_CANTERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_1_EJE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_2_EJES.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_AUTOMOTRIZ.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_BOTELLERO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_CAJA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_CISTERNA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_DE_GRUA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_FRIGORIFICO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_FURGON.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_HORMIGONERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_JAULA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_PLATAFORMA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_TALLER.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_VIVIENDA_O_CARAVANA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_VOLQUETE_DE_CANTERA.getValorEnum());

		criteria.add(Restrictions.disjunction().add(Restrictions.in("tv.tipoVehiculo", listaTiposVehiculo)).add(Restrictions.conjunction().add(Restrictions.sqlRestriction(
				"(CASE peso_mma WHEN 'ND' THEN 0 ELSE to_number(peso_mma) END) > (?)", 6000, Hibernate.INTEGER)).add(Restrictions.not(Restrictions.in("tv.tipoVehiculo",
						listaTiposVehiculoExcluidosPeso)))).add(Restrictions.in("v.idServicio", listaServicios)));
		return criteria;
	}

	private String sqlEstadisticasCTITErrorEnJefaturaDetalle(List<BigDecimal> listadoPosibles) {
		String stringSQL = "select count(caso) as numero, caso from ( " + "SELECT" + " distinct(it.num_expediente)," + " CASE"
				+ "   WHEN ((select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1) THEN 'Entrega Compraventa cuyo Poseedor es de Madrid y el Transmitente de fuera de Madrid'"
				+ "   WHEN ((select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1) THEN 'Interviene Compraventa cuyo Poseedor es de Madrid y el Adquiriente de fuera de Madrid'"
				+ "   WHEN ((select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1) THEN 'Notificación entre Compraventas donde uno es de Madrid y el otro no'"
				+ "   WHEN ((select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1) THEN 'Notificación entre Compraventas donde uno es de Madrid y el otro no'"
				+ " END as caso" + " FROM tramite_trafico t, tramite_traf_tran tr, interviniente_trafico it, direccion d" + " WHERE t.ESTADO=13" + "   AND t.FECHA_PRESENTACION >= :fechaInicio"
				+ "   AND t.FECHA_PRESENTACION < :fechaFin" + " AND t.TIPO_TRAMITE='T7'" + " AND tr.num_expediente = t.num_expediente" + " AND t.num_expediente = it.num_expediente"
				+ " AND it.id_direccion = d.id_direccion" + " AND (" + "   ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1"
				+ "   )" + "	)";

		if (listadoPosibles != null && !listadoPosibles.isEmpty() && listadoPosibles.size() < 1000) {
			stringSQL += " AND t.num_expediente in (:expedientesPosibles)";
		} else {
			int total = 0;
			int veces = 0;
			int i = 1;
			while (total <= listadoPosibles.size()) {
				if (i >= 1000) {
					i = 1;
					if (veces == 0) {
						stringSQL += " AND (t.num_expediente in (:expedientesPosibles0)";
					} else {
						stringSQL += " OR t.num_expediente in (:expedientesPosibles" + veces + ")";
					}
					veces++;
				}
				i++;
				total++;
			}
			stringSQL += ")";
		}
		stringSQL += ") group by caso";

		return stringSQL;
	}

	private String sqlEstadisticasCTITErrorEnJefaturaListado(List<BigDecimal> listadoPosibles) {
		String stringSQL = "SELECT" + " distinct(it.num_expediente)" + " FROM tramite_trafico t, tramite_traf_tran tr, interviniente_trafico it, direccion d" + " WHERE t.ESTADO=13"
				+ "   AND t.FECHA_PRESENTACION >= :fechaInicio" + "   AND t.FECHA_PRESENTACION < :fechaFin" + " AND t.TIPO_TRAMITE='T7'" + " AND tr.num_expediente = t.num_expediente"
				+ " AND t.num_expediente = it.num_expediente" + " AND it.id_direccion = d.id_direccion" + " AND (" + "   ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1"
				+ "   )" + "	)";

		if (listadoPosibles != null && !listadoPosibles.isEmpty() && listadoPosibles.size() < 1000) {
			stringSQL += " AND t.num_expediente in (:expedientesPosibles)";
		} else {
			int total = 0;
			int veces = 0;
			int i = 1;
			while (total <= listadoPosibles.size()) {
				if (i >= 1000) {
					i = 1;
					if (veces == 0) {
						stringSQL += " AND (t.num_expediente in (:expedientesPosibles0)";
					} else {
						stringSQL += " OR t.num_expediente in (:expedientesPosibles" + veces + ")";
					}
					veces++;
				}
				i++;
				total++;
			}
			stringSQL += ")";
		}

		return stringSQL;
	}

	private Criteria dividirListaExpedientes(Criteria criteria, List<BigDecimal> listadoPosibles) {
		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			if (listadoPosibles.size() < 1000) {
				criteria.add(Restrictions.in("numExpediente", listadoPosibles));
			} else {
				int i = 1;
				List<BigDecimal> listaAux = new ArrayList<BigDecimal>();
				Criterion criterionOr = null;
				for (BigDecimal numExpediente : listadoPosibles) {
					if (i < 1000) {
						listaAux.add(numExpediente);
						i++;
					} else {
						if (criterionOr == null) {
							criterionOr = Restrictions.in("numExpediente", listaAux);
						} else {
							criterionOr = Restrictions.or(criterionOr, Restrictions.in("numExpediente", listaAux));
						}
						listaAux = new ArrayList<BigDecimal>();
						i = 1;
					}
				}
				criteria.add(criterionOr);
			}
		}
		return criteria;
	}

	@Override
	public List<TramiteTrafTranVO> listadoPantallasEstadisticas(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones) {
		try {
			Criteria crit = createCriterion(filter);
			anadirEntitiesJoin(crit, entitiesJoin);
			anadirFetchModesJoin(crit, fetchModeJoinList);
			anadirListaProyecciones(crit, listaProyecciones);

			@SuppressWarnings("unchecked")
			List<TramiteTrafTranVO> lista = crit.list();
			if (lista != null && !lista.isEmpty()) {
				return lista;
			}

		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al obtener el número de elementos por criterios del objeto de tipo " + getType().getName(), e);
		}
		return Collections.emptyList();
	}
}