package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDuplicadosDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TramiteTraficoDuplicadosDaoImpl extends GenericDaoImplHibernate<TramiteTrafDuplicadoVO> implements TramiteTraficoDuplicadosDao {

	private static final long serialVersionUID = 1649450229789176436L;

	private static final String horaFinDia = "23:59";
	private static final int N_SEGUNDOS_59 = 59;
	private static final int N_MILISEGUNDOS_999 = 999;

	@Autowired
	UtilesFecha utilesFecha;

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafDuplicadoVO> getTramitesMasivos(Long idContrato, Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafDuplicadoVO.class);
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
		criteria.add(Restrictions.or(Restrictions.isNull("docPermiso"), Restrictions.isNull("docFichaTecnica")));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TramiteTrafDuplicadoVO>) criteria.list();
	}

	@Override
	public int getNumElementosMasivos(Long idContrato, Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafDuplicadoVO.class);
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
		}	BigDecimal[] estados = new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) };
		criteria.add(Restrictions.in("estado", estados));
		criteria.add(Restrictions.or(Restrictions.isNull("docPermiso"), Restrictions.isNull("docFichaTecnica")));
	
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public TramiteTrafDuplicadoVO getTramiteDuplicado(BigDecimal numExpediente, boolean tramiteCompleto) {
		List<Criterion> listCriterion = new ArrayList<>();
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		String[] listaFetchModeJoins = null;
		if (numExpediente != null) {
			listCriterion.add(Restrictions.eq("numExpediente", numExpediente));
		}

		if (tramiteCompleto) {
			listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato", "contrato", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato.colegio", "colegio", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(ColegiadoVO.class, "contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(UsuarioVO.class, "contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DireccionVO.class, "vehiculo.direccion", "vehiculo.direccion", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(JefaturaTraficoVO.class, "jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(PersonaVO.class, "intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DireccionVO.class, "intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(TasaVO.class, "tasa", "tasa", CriteriaSpecification.LEFT_JOIN));

			List<String> listFetchModeJoins = new ArrayList<>();
			listFetchModeJoins.add("intervinienteTraficos");

			listaFetchModeJoins = listFetchModeJoins.toArray(new String[0]);
		}

		List<TramiteTrafDuplicadoVO> lista = buscarPorCriteria(-1, -1, listCriterion, null, null, listaAlias, null, null, listaFetchModeJoins);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public TramiteTrafDuplicadoVO getTramiteDuplicadoSive(BigDecimal numExpediente, boolean tramiteCompleto) {
		List<Criterion> listCriterion = new ArrayList<>();
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		String[] listaFetchModeJoins = null;
		if (numExpediente != null) {
			listCriterion.add(Restrictions.eq("numExpediente", numExpediente));
		}

		if (tramiteCompleto) {
			listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato", "contrato", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato.colegio", "colegio", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(ColegiadoVO.class, "contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(UsuarioVO.class, "contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DireccionVO.class, "vehiculo.direccion", "vehiculo.direccion", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(JefaturaTraficoVO.class, "jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(PersonaVO.class, "intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DireccionVO.class, "intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(TasaVO.class, "tasa", "tasa", CriteriaSpecification.LEFT_JOIN));

			List<String> listFetchModeJoins = new ArrayList<>();
			listFetchModeJoins.add("intervinienteTraficos");

			listaFetchModeJoins = listFetchModeJoins.toArray(new String[0]);
		}

		List<TramiteTrafDuplicadoVO> lista = buscarPorCriteria(-1, -1, listCriterion, null, null, listaAlias, null, null, listaFetchModeJoins);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<TramiteTrafDuplicadoVO> duplicadosExcel(String jefatura) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafDuplicadoVO.class);

		Date fechaHasta = new Date();
		utilesFecha.setHoraEnDate(fechaHasta, horaFinDia);
		utilesFecha.setSegundosEnDate(fechaHasta, N_SEGUNDOS_59);
		utilesFecha.setMilisegundosEnDate(fechaHasta, N_MILISEGUNDOS_999);

		criteria.add(Restrictions.le("fechaPresentacion", fechaHasta));
		criteria.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.Duplicado.getValorEnum()));
		criteria.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio_Excel.getValorEnum())));
		criteria.add(Restrictions.eq("jefaturaTrafico.jefaturaProvincial", jefatura));

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN);

		criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<TramiteTrafDuplicadoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<TramiteTrafDuplicadoVO> getListaTramitesFinalizadosTelematicamentePorContrato(Long idContrato, Date fechaPresentacion) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafDuplicadoVO.class);
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
		criteria.add(Restrictions.eq("imprPermisoCirculacion", "SI"));
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return (List<TramiteTrafDuplicadoVO>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafDuplicadoVO> listaTramitesFinalizadosTelemPorContrato(Long idContrato, Date fechaPresentacion, String tipoSolicitud, Boolean esMoto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafDuplicadoVO.class);
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
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(tipoSolicitud)) {
			criteria.add(Restrictions.isNull("docFichaTecnica"));
			criteria.add(Restrictions.isNotNull("vehiculo.nive"));
		}
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TramiteTrafDuplicadoVO>) criteria.list();
	}
}