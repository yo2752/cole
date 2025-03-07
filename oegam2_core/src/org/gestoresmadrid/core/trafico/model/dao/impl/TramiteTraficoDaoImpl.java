package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoPresentacionJPT;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.transformer.PresentacionJptResulTransformer;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.presentacion.jpt.model.beans.BeanPresentadoJpt;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionPresentacionJptVO;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafFacturacionVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.core.ybpdf.model.vo.YbpdfVO;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hibernate.entities.trafico.TramiteTrafFacturacion;
import hibernate.entities.trafico.TramiteTrafico;
import utilidades.estructuras.Fecha;

@Repository
public class TramiteTraficoDaoImpl extends GenericDaoImplHibernate<TramiteTraficoVO> implements TramiteTraficoDao {

	private static final long serialVersionUID = 8752357452006643696L;

	private static final String horaFinDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	private static final Short VEHICULO_ACTIVO = 1;

	private static final String JEFATURAS_EXCLUIDAS = "presentacionJPT.jefaturas.excluidas";
	private static final String YERBABUENA_NO_NIVE = "yerbabuena.no.nive";

	@Autowired
	private PresentacionJptResulTransformer presentacionJptResulTransformer;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public List<TramiteTraficoVO> getListaTramitesWS(Long idContrato, Date fechaPresentacionInicio, Date fechaPresentacionFin, String matricula, String bastidor, String numExpediente,
			String nifTitular) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		if (numExpediente != null && !numExpediente.isEmpty()) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		} else if (matricula != null && !matricula.isEmpty()) {
			criteria.add(Restrictions.eq("vehiculo.matricula", matricula));
		} else if (bastidor != null && !bastidor.isEmpty()) {
			criteria.add(Restrictions.eq("vehiculo.bastidor", bastidor));
		} else if (nifTitular != null && !nifTitular.isEmpty()) {
			criteria.add(Restrictions.eq("intervinienteTraficos.nif", nifTitular));
			criteria.add(Restrictions.or(Restrictions.eq("intervinienteTraficos.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()), Restrictions.eq(
					"intervinienteTraficos.tipoInterviniente", TipoInterviniente.Adquiriente.getValorEnum())));
		}
		if (fechaPresentacionInicio != null) {
			Conjunction and = Restrictions.conjunction();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaPresentacionInicio);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			and.add(Restrictions.ge("fechaPresentacion", calendar.getTime()));
			if (fechaPresentacionFin != null) {
				calendar.setTime(fechaPresentacionFin);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
				and.add(Restrictions.lt("fechaPresentacion", calendar.getTime()));
			}
			criteria.add(and);
		}
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(13), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TramiteTraficoVO>) criteria.list();
	}

	@Override
	public TramiteTraficoVO getTramitePorTasa(String codigoTasa) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("tasa.codigoTasa", codigoTasa));
		criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
		return (TramiteTraficoVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> getListaExpedientesPorTasa(String codigoTasa) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("tasa.codigoTasa", codigoTasa));
		criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
		return (List<TramiteTraficoVO>) criteria.list();
	}

	@Override
	public List<TramiteTraficoVO> getListaTramitesKOPendientesPorJefatura(String jefatura) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("excelKoImpr", "N"));
		criteria.add(Restrictions.eq("jefaturaTrafico.jefaturaProvincial", jefatura));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> getListaTramitesPermisosPorDocId(Long idDocPermisos) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("docPermiso", idDocPermisos));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public List<TramiteTraficoVO> getListaTramitesPermisosPorDocIdErroneos(Long docId) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("docPermiso", docId));
		criteria.add(Restrictions.eq("estadoSolPerm", EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum()));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public int comprobarRespuestaTipoErrorValidoImprimir(BigDecimal numExp, String[] listaErrores) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExp));
		criteria.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));
		Disjunction erroresComp = Restrictions.disjunction();
		for (String error : listaErrores) {
			erroresComp.add(Restrictions.like("respuesta", error + "%"));
		}
		criteria.add(erroresComp);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public TramiteTraficoVO getTramite(BigDecimal numExpediente, boolean tramiteCompleto) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		String[] listaFetchModeJoins = null;
		if (numExpediente != null) {
			listCriterion.add(Restrictions.eq("numExpediente", numExpediente));
		}

		listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato", "contrato", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(ColegiadoVO.class, "contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(ColegiadoVO.class, "contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(ProvinciaVO.class, "contrato.provincia", "contrato.provincia", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(UsuarioVO.class, "contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(JefaturaTraficoVO.class, "jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN));

		if (tramiteCompleto) {
			listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DireccionVO.class, "vehiculo.direccion", "vehiculo.direccion", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(PersonaVO.class, "vehiculo.persona", "vehiculo.persona", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(PersonaVO.class, "intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DireccionVO.class, "intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(TasaVO.class, "tasa", "tasa", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DocPermDistItvVO.class, "docPermisoVO", "docPermisoVO", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DocPermDistItvVO.class, "docFichaTecnicaVO", "docFichaTecnica", CriteriaSpecification.LEFT_JOIN));
			List<String> listFetchModeJoins = new ArrayList<String>();
			listFetchModeJoins.add("intervinienteTraficos");
			listFetchModeJoins.add("tramiteFacturacion");
			listaFetchModeJoins = listFetchModeJoins.toArray(new String[0]);
		}

		List<TramiteTraficoVO> lista = buscarPorCriteria(-1, -1, listCriterion, null, null, listaAlias, null, null, listaFetchModeJoins);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public TramiteTraficoSolInteveVO getTramiteInteve(BigDecimal numExpediente, String codigoTasa) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoSolInteveVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		criteria.add(Restrictions.eq("codigoTasa", codigoTasa));

		return (TramiteTraficoSolInteveVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> getListaExpedientesPorListNumExp(BigDecimal[] numExpedientes, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.in("numExpediente", numExpedientes));

		if (tramiteCompleto) {
			criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.jefaturaTrafico", "contratoJefatura", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo.direccion", "vehiculoDireccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo.persona", "vehiculoPersona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficosPersona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficosDireccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("docPermisoVO", "docPermiso", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("docFichaTecnicaVO", "docFichaTecnica", CriteriaSpecification.LEFT_JOIN);
			criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
			criteria.setFetchMode("tramiteFacturacion", FetchMode.JOIN);
		}
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public List<TramiteTraficoVO> getListaExpedientesGenDocBase(BigDecimal[] numExpedientes) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.in("numExpediente", numExpedientes));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefatura", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo.direccion", "vehiculoDireccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo.persona", "vehiculoPersona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficosPersona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficosDireccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docPermisoVO", "docPermiso", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docFichaTecnicaVO", "docFichaTecnica", CriteriaSpecification.LEFT_JOIN);
		criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
		criteria.setFetchMode("tramiteFacturacion", FetchMode.JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> getTramiteClonar(BigDecimal[] numsExpedientes, Boolean tramiteCompleto) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(TramiteTraficoVO.class);
		if (numsExpedientes != null) {
			criteria.add(Restrictions.in("numExpediente", numsExpedientes));
		}
		return criteria.list();
	}

	@Override
	public List<TramiteTraficoVO> getListaTramiteTraficoVO(BigDecimal[] numExpedientes, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.in("numExpediente", numExpedientes));
		if (tramiteCompleto) {
			criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.provincia", "contrato.provincia", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo.direccion", "vehiculo.direccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo.persona", "vehiculo.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("docPermisoVO", "docPermisoVO", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("docFichaTecnicaVO", "docFichaTecnica", CriteriaSpecification.LEFT_JOIN);
		}
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public List<TramiteTraficoVO> findAll() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(getType());
		criteria.list();
		return null;
	}

	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		String numExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFinDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		numExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);

		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAlta", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));

		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(numExpediente + "00000");
		}
		maximoExistente = new BigDecimal(maximoExistente.longValue() + 1);
		return maximoExistente;
	}

	@Override
	public BigDecimal generarNumExpedienteFacturacionTasa(String numColegiado) throws Exception {
		String numExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFinDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		numExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);

		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(TramiteTrafFacturacionVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAplicacion", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("id.numExpediente"));

		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(numExpediente + "90000");
		}
		maximoExistente = new BigDecimal(maximoExistente.longValue() + 1);
		return maximoExistente;
	}

	@Override
	public String getTipoTramite(BigDecimal numExp) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExp));
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.groupProperty("tipoTramite"));
		criteria.setProjection(listaProyecciones);
		return (String) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BeanPresentadoJpt> getTramitesDocumentoBase(String idDoc) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(TramiteTraficoVO.class);
		if (idDoc.length() == 36) {
			// Documento base antiguo
			criteria.add(Restrictions.eq("ybpdf.idYbpdf", idDoc));
		} else {
			// Documento base nuevo
			criteria.createCriteria("documentoBase").add(Restrictions.eq("docId", idDoc));
		}
		criteria.createAlias("vehiculo", "vehiculo");
		criteria.setProjection(presentacionJptResulTransformer.getProjection());
		criteria.setResultTransformer(presentacionJptResulTransformer);
		return criteria.list();
	}

	@Override
	public List<TramiteTraficoVO> recuperaTramitesBloqueantes() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(TramiteTraficoVO.class);

		String habilitarDocBaseNuevo = gestorPropiedades.valorPropertie("habilitar.docBase.nuevo");

		if ("SI".equals(habilitarDocBaseNuevo)) {
			// Identificador de documento base ya asignado (DOCUMENTOS BASE NUEVOS)
			criteria.add(Restrictions.isNotNull("documentoBase.id"));
			criteria.createAlias("documentoBase", "documentoBase");
		} else {
			// Identificador de documento base ya asignado (DOCUMENTOS BASE ANTIGUOS)
			criteria.add(Restrictions.isNotNull("ybpdf"));
		}

		// Estado de los trámites 2, 3 y 4
		criteria.add(Restrictions.disjunction().add(Restrictions.eq("ybestado", new BigDecimal(2))).add(Restrictions.eq("ybestado", new BigDecimal(3))).add(Restrictions.eq("ybestado", new BigDecimal(
				4))));

		criteria.addOrder(Order.asc("numColegiado"));
		// Documento base nuevo
		if ("SI".equals(habilitarDocBaseNuevo)) {
			// Identificador de documento base ya asignado
			criteria.addOrder(Order.asc("documentoBase.id"));

			// Documento base antiguo
		} else {
			// Identificador de documento base ya asignado
			criteria.addOrder(Order.asc("idYbpdf"));
		}

		criteria.addOrder(Order.asc("fechaPresentacion"));

		@SuppressWarnings("unchecked")
		List<TramiteTraficoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<TramiteTraficoVO> obtenerNoPresentados() {
		List<Criterion> listCriterion = new ArrayList<>();
		List<AliasQueryBean> listaAlias = new ArrayList<>();

		String fechaPuestaProduccion = gestorPropiedades.valorPropertie("fechaProduccion.presentacionJPT");
		Date fechaPro = utilesFecha.getFechaDate(fechaPuestaProduccion);

		Date fechaHoy = new Date();
		Date fechaCaducidadPresentacion = utilesFecha.sumarRestarDiasFecha(fechaHoy, -16);

		ArrayList<BigDecimal> estados = new ArrayList<BigDecimal>();
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

		ArrayList<String> tiposTramites = new ArrayList<String>();
		tiposTramites.add(TipoTramiteTrafico.Matriculacion.getValorEnum());
		tiposTramites.add(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());

		String jefaturasExcluidas = gestorPropiedades.valorPropertie(JEFATURAS_EXCLUIDAS);
		if (jefaturasExcluidas != null) {
			String[] jefaturas = jefaturasExcluidas.split(",");
			ArrayList<String> jefaturasEx = new ArrayList<>();
			for (String jefatura : jefaturas) {
				jefaturasEx.add(jefatura);
			}
			listCriterion.add(Restrictions.not(Restrictions.in("jefaturaTrafico.jefaturaProvincial", jefaturasEx)));
		}

		ArrayList<Short> estadosPresentacion = new ArrayList<Short>();
		estadosPresentacion.add(EstadoPresentacionJPT.Presentado.getValorEnum());
		estadosPresentacion.add(EstadoPresentacionJPT.Manual.getValorEnum());

		listCriterion.add(Restrictions.in("tipoTramite", tiposTramites));
		listCriterion.add(Restrictions.in("estado", estados));
		listCriterion.add(Restrictions.or(Restrictions.not(Restrictions.in("presentadoJpt", estadosPresentacion)), Restrictions.isNull("presentadoJpt")));
		listCriterion.add(Restrictions.between("fechaPresentacion", fechaPro, fechaCaducidadPresentacion));

		String noNive = gestorPropiedades.valorPropertie(YERBABUENA_NO_NIVE);
		if (noNive != null && "SI".equals(noNive)) {
			listCriterion.add(Restrictions.or(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()), Restrictions.isNull("vehiculo.nive")));
		}

		ArrayList<String> orden = new ArrayList<>();
		orden.add("numColegiado");
		orden.add("idYbpdf");
		orden.add("documentoBase.id");
		orden.add("fechaPresentacion");

		listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato", "contrato", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(JefaturaTraficoVO.class, "jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(DocumentoBaseVO.class, "documentoBase", "documentoBase", CriteriaSpecification.LEFT_JOIN));

		List<TramiteTraficoVO> lista = buscarPorCriteria(listCriterion, GenericDaoImplHibernate.ordenDes, orden, listaAlias, null);

		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<TramiteTraficoVO> obtenerPresentados(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();

		Date fechaFinPresentado = new Date(fechaPresentado.getTime());
		utilesFecha.setHoraEnDate(fechaFinPresentado, horaFinDia);
		utilesFecha.setSegundosEnDate(fechaFinPresentado, N_SEGUNDOS);

		listCriterion.add(Restrictions.between("fechaPresentacionJpt", fechaPresentado, fechaFinPresentado));
		listCriterion.add(Restrictions.or(Restrictions.eq("ybpdf.carpeta", carpeta), Restrictions.eq("documentoBase.carpeta", carpeta)));
		listCriterion.add(Restrictions.eq("presentadoJpt", EstadoPresentacionJPT.Presentado.getValorEnum()));
		listCriterion.add(Restrictions.eq("evolucionPresentacionJptVO.jefatura", jefaturaJpt));

		listaAlias.add(new AliasQueryBean(YbpdfVO.class, "ybpdf", "ybpdf", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(DocumentoBaseVO.class, "documentoBase", "documentoBase", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(EvolucionPresentacionJptVO.class, "evolucionPresentacionJptVO", "evolucionPresentacionJptVO", CriteriaSpecification.LEFT_JOIN));

		List<TramiteTraficoVO> lista = buscarPorCriteria(listCriterion, null, null, listaAlias, null);

		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<TramiteTraficoVO> obtenerPresentadosNive(Date fechaPresentado, String jefaturaJpt) throws Exception {
		List<Criterion> listCriterion = new ArrayList<>();
		List<AliasQueryBean> listaAlias = new ArrayList<>();

		Date fechaFinPresentado = new Date(fechaPresentado.getTime());
		utilesFecha.setHoraEnDate(fechaFinPresentado, horaFinDia);
		utilesFecha.setSegundosEnDate(fechaFinPresentado, N_SEGUNDOS);

		listCriterion.add(Restrictions.between("fechaPresentacionJpt", fechaPresentado, fechaFinPresentado));
		listCriterion.add(Restrictions.eq("presentadoJpt", EstadoPresentacionJPT.Presentado.getValorEnum()));
		listCriterion.add(Restrictions.isNotNull("vehiculo.nive"));
		listCriterion.add(Restrictions.eq("jefaturaTrafico.jefaturaProvincial", jefaturaJpt));

		listaAlias.add(new AliasQueryBean(YbpdfVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
		// TODO: Contrato o el del trámite
		listaAlias.add(new AliasQueryBean(YbpdfVO.class, "jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN));

		List<TramiteTraficoVO> lista = buscarPorCriteria(listCriterion, null, null, listaAlias, null);

		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public int getCantidadTipoEstadistica(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception {
		int cantidad = 0;
		List<TramiteTraficoVO> result = obtenerPresentados(fechaPresentado, carpeta, jefaturaJpt);
		if (result != null && !result.isEmpty()) {
			return result.size();
		}
		return cantidad;
	}

	@Override
	public int getCantidadTipoEstadisticaNive(Date fechaPresentado, String jefaturaJpt) throws Exception {
		int cantidad = 0;
		List<TramiteTraficoVO> result = obtenerPresentadosNive(fechaPresentado, jefaturaJpt);
		if (result != null && !result.isEmpty()) {
			return result.size();
		}
		return cantidad;
	}

	@Override
	public int getNumColegiadosDistintos(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception {
		List<Criterion> listCriterion = new ArrayList<>();
		List<AliasQueryBean> listaAlias = new ArrayList<>();

		Date fechaFinPresentado = new Date(fechaPresentado.getTime());
		utilesFecha.setHoraEnDate(fechaFinPresentado, horaFinDia);
		utilesFecha.setSegundosEnDate(fechaFinPresentado, N_SEGUNDOS);

		listCriterion.add(Restrictions.between("fechaPresentacionJpt", fechaPresentado, fechaFinPresentado));
		listCriterion.add(Restrictions.or(Restrictions.eq("ybpdf.carpeta", carpeta), Restrictions.eq("documentoBase.carpeta", carpeta)));
		listCriterion.add(Restrictions.eq("presentadoJpt", EstadoPresentacionJPT.Presentado.getValorEnum()));
		listCriterion.add(Restrictions.eq("jefaturaTrafico.jefaturaProvincial", jefaturaJpt));

		listaAlias.add(new AliasQueryBean(YbpdfVO.class, "ybpdf", "ybpdf", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(DocumentoBaseVO.class, "documentoBase", "documentoBase", CriteriaSpecification.LEFT_JOIN));

		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.distinct(Projections.property("numColegiado")));

		List<TramiteTraficoVO> lista = buscarPorCriteria(listCriterion, null, null, listaAlias, listaProyecciones);

		if (lista != null && !lista.isEmpty()) {
			return lista.size();
		}
		return 0;
	}

	@Override
	public int getNumColegiadosDistintosNive(Date fechaPresentado, String jefaturaJpt) throws Exception {
		List<Criterion> listCriterion = new ArrayList<>();
		List<AliasQueryBean> listaAlias = new ArrayList<>();

		Date fechaFinPresentado = new Date(fechaPresentado.getTime());
		utilesFecha.setHoraEnDate(fechaFinPresentado, horaFinDia);
		utilesFecha.setSegundosEnDate(fechaFinPresentado, N_SEGUNDOS);

		listCriterion.add(Restrictions.between("fechaPresentacionJpt", fechaPresentado, fechaFinPresentado));
		listCriterion.add(Restrictions.eq("presentadoJpt", EstadoPresentacionJPT.Presentado.getValorEnum()));
		listCriterion.add(Restrictions.isNotNull("vehiculo.nive"));
		listCriterion.add(Restrictions.eq("jefaturaTrafico.jefaturaProvincial", jefaturaJpt));

		listaAlias.add(new AliasQueryBean(YbpdfVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));

		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.distinct(Projections.property("numColegiado")));

		List<TramiteTraficoVO> lista = buscarPorCriteria(listCriterion, null, null, listaAlias, listaProyecciones);

		if (lista != null && !lista.isEmpty()) {
			return lista.size();
		}
		return 0;
	}

	@Override
	public int getNumTramitePorVehiculo(BigDecimal numExpediente, Long idVehiculo) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		if (numExpediente != null) {
			listCriterion.add(Restrictions.ne("numExpediente", numExpediente));
		}
		if (idVehiculo != null) {
			listCriterion.add(Restrictions.eq("vehiculo.idVehiculo", idVehiculo));
		}

		ArrayList<BigDecimal> estados = new ArrayList<BigDecimal>();
		estados.add(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Anulado.getValorEnum()));

		listCriterion.add(Restrictions.not(Restrictions.in("estado", estados)));

		listCriterion.add(Restrictions.or(Restrictions.eq("vehiculo.activo", VEHICULO_ACTIVO), Restrictions.isNull("vehiculo.activo")));

		listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));

		List<TramiteTraficoVO> lista = buscarPorCriteria(listCriterion, listaAlias, null);

		if (lista != null) {
			return lista.size();
		}
		return 0;
	}

	@Override
	public Integer getNumTramitePorColegiado(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		Date fechaRegIni = new Date();
		Date fechaRegFin = new Date();
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		Conjunction and = Restrictions.conjunction();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaRegIni);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1, calendar.get(Calendar.DATE), 0, 0, 0);
		and.add(Restrictions.ge("fechaRegistroNRE", calendar.getTime()));
		calendar.setTime(fechaRegFin);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		and.add(Restrictions.lt("fechaRegistroNRE", calendar.getTime()));
		criteria.add(and);
		criteria.add(Restrictions.isNotNull("nre"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getListaTramitesMismoContratoPorExpediente(BigDecimal[] numExpedientes) {
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.distinct(Projections.property("contrato.idContrato")));
		List<Criterion> criterionList = new ArrayList<>();
		criterionList.add(Restrictions.in("numExpediente", numExpedientes));
		List<?> lista = buscarPorCriteria(criterionList, null, listaProyecciones);
		if (lista == null) {
			return null;
		}
		return (List<Long>) lista;
	}

	@Override
	public String getTipoTramiteTraficoPorExpedientes(BigDecimal[] numExpedientes) {
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.distinct(Projections.property("tipoTramite")));
		List<Criterion> criterionList = new ArrayList<>();
		criterionList.add(Restrictions.in("numExpediente", numExpedientes));
		List<?> listaTipo = buscarPorCriteria(criterionList, null, listaProyecciones);
		if (listaTipo == null) {
			return null;
		}
		return (String) listaTipo.get(0);
	}

	@Override
	public List<TramiteTraficoVO> getListaTramitesAComprobarDatosSensibles(BigDecimal[] numExpedientes) {
		List<Criterion> listaCriterion = new ArrayList<>();
		listaCriterion.add(Restrictions.in("numExpediente", numExpedientes));

		return buscarPorCriteria(listaCriterion, null, null);
	}

	@Override
	public List<TramiteTraficoVO> getListaIntervTrafPorExpedientes(BigDecimal[] numExpedientes) {
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		listaAlias.add(new AliasQueryBean(IntervinienteTraficoVO.class, "intervinienteTraficos", "interviniente"));
		List<Criterion> listaCriterion = new ArrayList<>();
		listaCriterion.add(Restrictions.in("numExpediente", numExpedientes));
		Criterion matri = Restrictions.eq("tipoTramite", TipoTramiteTrafico.Matriculacion.getValorEnum());
		Criterion trans = Restrictions.in("tipoTramite", new String[] { TipoTramiteTrafico.Transmision.getValorEnum(), TipoTramiteTrafico.TransmisionElectronica.getValorEnum() });
		Criterion intervinienteMatri = Restrictions.eq("interviniente.tipoIntervinienteVO.tipoInterviniente", TipoInterviniente.Titular.getValorEnum());
		Criterion intervinienteTrans = Restrictions.in("interviniente.tipoIntervinienteVO.tipoInterviniente", new String[] { TipoInterviniente.Adquiriente.getValorEnum(),
				TipoInterviniente.TransmitenteTrafico.getValorEnum() });
		Criterion tipoInterviniente = Restrictions.or(Restrictions.and(matri, intervinienteMatri), Restrictions.and(trans, intervinienteTrans));
		listaCriterion.add(tipoInterviniente);

		return buscarPorCriteria(listaCriterion, listaAlias, null);
	}

	@Override
	public List<TramiteTraficoVO> getListaTramitesTraficoPorExpedientesSinFechaPresentacion(BigDecimal[] numExpedientes) {
		List<Criterion> listaCriterion = new ArrayList<>();
		listaCriterion.add(Restrictions.in("numExpediente", numExpedientes));
		listaCriterion.add(Restrictions.isNull("fechaPresentacion"));
		return buscarPorCriteria(listaCriterion, null, null);
	}

	@Override
	public List<TramiteTraficoVO> existeNumExpedientesEstados(BigDecimal[] numExpedientes, BigDecimal[] estados) {
		List<Criterion> listaCriterion = new ArrayList<>();
		listaCriterion.add(Restrictions.in("estado", estados));
		listaCriterion.add(Restrictions.in("numExpediente", numExpedientes));

		return buscarPorCriteria(listaCriterion, null, null);
	}

	@Override
	public List<TramiteTraficoVO> noExisteNumExpedientesEstados(BigDecimal[] numExps, BigDecimal[] estados) {
		List<Criterion> listaCriterion = new ArrayList<>();

		listaCriterion.add(Restrictions.in("numExpediente", numExps));
		for (BigDecimal estado : estados) {
			listaCriterion.add(Restrictions.ne("estado", estado));
		}

		return buscarPorCriteria(listaCriterion, null, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> noExisteNumExpedienteEstados(BigDecimal numExpediente, BigDecimal[] estados) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		for (BigDecimal estado : estados) {
			criteria.add(Restrictions.ne("estado", estado));
		}

		return criteria.list();
	}

	@Override
	public List<TramiteTraficoVO> comprobarTramitesEnEstados(BigDecimal[] numExp, BigDecimal[] estadoTramite) {
		List<Criterion> listaCriterion = new ArrayList<>();
		listaCriterion.add(Restrictions.in("estado", estadoTramite));
		listaCriterion.add(Restrictions.in("numExpediente", numExp));
		return buscarPorCriteria(listaCriterion, null, null);
	}

	public TramiteTraficoVO actualizarRespuestaMateEitv(String respuesta, BigDecimal numExpediente) {
		TramiteTraficoVO tramite = buscarPorExpediente(numExpediente);
		tramite.setRespuesta(respuesta);
		tramite.setNumExpediente(numExpediente);
		tramite = guardarOActualizar(tramite);
		if (tramite != null) {
			return tramite;
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getListaTramitesPorFechas(Date fecha1, Date fecha2) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);

		List<BigDecimal> estados = new ArrayList<>();
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

		List<String> tipoTramites = new ArrayList<>();
		tipoTramites.add(TipoTramiteTrafico.Transmision.getValorEnum());
		tipoTramites.add(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());

		criteria.add(Restrictions.in("tipoTramite", tipoTramites));
		criteria.add(Restrictions.in("estado", estados));

		criteria.add(Restrictions.between("fechaPresentacion", fecha1, fecha2));

		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.groupProperty("numColegiado"));
		listaProyecciones.add(Projections.rowCount()).hashCode();

		criteria.setProjection(listaProyecciones);

		return criteria.list();
	}

	public String obtenerNumColegiadoByNumExpediente(BigDecimal numExpediente) {
		List<Criterion> criterios = new ArrayList<>();
		criterios.add(Restrictions.eq("numExpediente", numExpediente));
		List<TramiteTraficoVO> result = buscarPorCriteria(criterios, null, null);
		TramiteTraficoVO tramiteTraficoVO = (TramiteTraficoVO) result.get(0);
		return tramiteTraficoVO.getNumColegiado();
	}

	@Override
	public int usuarioTienePermisoSobreTramites(BigDecimal[] numExpediente, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.in("numExpediente", numExpediente));
		criteria.add(Restrictions.ne("contrato.idContrato", idContrato));
		criteria.createAlias("contrato", "contrato");
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public Object[] listarTramitesFinalizadosPorTiempo(String tipoTramite, List<BigDecimal> estadosOk, List<BigDecimal> estadosKo, List<String> tipoTransferencia, int segundos) {
		Object[] result = null;
		if (tipoTramite != null && !tipoTramite.isEmpty()) {
			SQLQuery sqlQuery;
			if (tipoTransferencia != null && !tipoTransferencia.isEmpty()) {
				sqlQuery = getCurrentSession().createSQLQuery(
						"select count(case when t.estado in (:estadosOk) then t.estado else null end ) ok, count(case when t.estado in (:estadosKo) then t.estado else null end ) ko from tramite_trafico t inner join tramite_traf_tran tt on t.num_expediente = tt.num_expediente where t.tipo_tramite = :tipoTramite and t.fecha_presentacion >= sysdate - :segundos / 86400 and t.estado in (:estados) and t.fecha_presentacion < sysdate and tt.tipo_transferencia in (:tipoTransferencia)");
			} else {
				sqlQuery = getCurrentSession().createSQLQuery(
						"select count(case when estado in (:estadosOk) then estado else null end ) ok, count(case when estado in (:estadosKo) then estado else null end ) ko from tramite_trafico where tipo_tramite = :tipoTramite and fecha_presentacion >= sysdate - :segundos / 86400 and estado in (:estados) and fecha_presentacion < sysdate");
			}
			sqlQuery.setParameterList("estadosOk", estadosOk);
			sqlQuery.setParameterList("estadosKo", estadosKo);
			sqlQuery.setInteger("segundos", segundos);
			sqlQuery.setParameterList("estados", ListUtils.union(estadosOk, estadosKo));
			sqlQuery.setString("tipoTramite", tipoTramite);
			if (tipoTransferencia != null && !tipoTransferencia.isEmpty()) {
				sqlQuery.setParameterList("tipoTransferencia", tipoTransferencia);
			}
			result = (Object[]) sqlQuery.uniqueResult();
		}
		return result;
	}

	@Override
	public Integer getCountNumTramitesPermisos(Long idDocPerm) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("docPermiso", idDocPerm));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCountNumTramitesPermisosPorJefatura(List<Long> idDocPerms) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.in("docPermiso", idDocPerms));

		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.groupProperty("jefaturaTrafico"));
		listaProyecciones.add(Projections.rowCount()).hashCode();

		criteria.setProjection(listaProyecciones);

		return criteria.list();
	}

	@Override
	public Integer getCountNumTramitesFichas(Long idDocFichas) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("docFichaTecnica", idDocFichas));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCountNumTramitesFichasPorJefatura(List<Long> idDocFichas) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.in("docFichaTecnica", idDocFichas));

		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.groupProperty("jefaturaTrafico"));
		listaProyecciones.add(Projections.rowCount()).hashCode();

		criteria.setProjection(listaProyecciones);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> getListaTramitesFichasTecnicasPorDocId(Long idDocFichaTecnica) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("docFichaTecnica", idDocFichaTecnica));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> getListaTramitesFichasTecnicasPorDocIdErroneas(Long docId) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("docFichaTecnica", docId));
		criteria.add(Restrictions.eq("estadoSolFicha", EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum()));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getListaIdsContratosFinalizadosTelematicamentePorFecha(Date fechaPresentacion, String[] tiposTramites, Boolean esDocBase) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
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
		criteria.add(Restrictions.in("tipoTramite", tiposTramites));
		if (esDocBase) {
			criteria.add(Restrictions.isNull("documentoBase"));
		}
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.setProjection(Projections.distinct(Projections.property("contrato.idContrato")));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> getListaTramitesPorNifTipoIntervienteYFecha(String nifInterviniente, Long idContrato, String tipoInterviniente, String tipoTramite, Date fechaPresentacion) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
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
		criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		criteria.add(Restrictions.isNull("docPermiso"));
		criteria.add(Restrictions.isNull("docFichaTecnica"));
		criteria.add(Restrictions.eq("intervinienteTraficos.id.nif", nifInterviniente));
		criteria.add(Restrictions.eq("intervinienteTraficos.id.tipoInterviniente", tipoInterviniente));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
		return criteria.list();
	}

	@Override
	public List<TramiteTraficoVO> getListaTramitesDocBaseNocturno(Long idContrato, Date fechaPresentacion, String tipoTramite) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
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
		criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.isNull("idDocBase"));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefatura", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoVO> getlistaTramitesCtitFinalizadosPorContratoYFecha(Long idContrato, Date fechaPresentacion) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
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
		criteria.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.isNull("docPermiso"));
		criteria.add(Restrictions.isNull("docFichaTecnica"));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}

	public TramiteTrafico getTramiteTrafico(String numColegiado, String matricula, String tipoTramite) {
		TramiteTrafico tramiteTrafico = null;

		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafico.class);

		criteria.createAlias("vehiculo", "vehiculo");
		if (numColegiado != null) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}

		if (matricula != null) {
			criteria.add(Restrictions.eq("vehiculo.matricula", matricula));
		}
		if (tipoTramite != null) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		}

		tramiteTrafico = (TramiteTrafico) criteria.uniqueResult();

		return tramiteTrafico;
	}

	@Override
	public Integer getPosibleDuplicado(String bastidor, String nif, String tipoTramite, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos", "interviniente");

		ArrayList<BigDecimal> estados = new ArrayList<>();
		estados.add(new BigDecimal(EstadoTramiteTrafico.Anulado.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel_Impreso.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

		criteria.add(Restrictions.eq("vehiculo.bastidor", bastidor));

		if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
			criteria.add(Restrictions.eq("interviniente.id.tipoInterviniente", TipoInterviniente.Adquiriente.getValorEnum()));
		} else {
			criteria.add(Restrictions.eq("interviniente.id.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()));
		}

		criteria.add(Restrictions.eq("interviniente.id.nif", nif));

		criteria.add(Restrictions.not(Restrictions.in("estado", estados)));

		criteria.add(Restrictions.eq("numColegiado", numColegiado));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public TramiteTraficoVO primeraMatricula(Date fechaActual) throws Exception {
		Criteria criteria = primeraOUltimaMatricula(fechaActual);
		criteria.addOrder(Order.asc("v.fechaMatriculacion"));
		criteria.addOrder(Order.asc("v.matricula"));
		List<TramiteTraficoVO> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public TramiteTraficoVO ultimaMatricula(Date fechaActual) throws Exception {
		Criteria criteria = primeraOUltimaMatricula(fechaActual);
		criteria.addOrder(Order.desc("v.fechaMatriculacion"));
		criteria.addOrder(Order.desc("v.matricula"));
		List<TramiteTraficoVO> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	private Criteria primeraOUltimaMatricula(Date fechaActual) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);

		criteria.createAlias("vehiculo", "v");

		Conjunction andFecha = Restrictions.conjunction();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaActual);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		andFecha.add(Restrictions.ge("v.fechaMatriculacion", calendar.getTime()));
		calendar.setTime(fechaActual);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		andFecha.add(Restrictions.lt("v.fechaMatriculacion", calendar.getTime()));
		criteria.add(andFecha);

		Conjunction andTipVeh = Restrictions.conjunction();
		andTipVeh.add(Restrictions.not(Restrictions.like("v.tipoVehiculo", "R%")));
		andTipVeh.add(Restrictions.not(Restrictions.like("v.tipoVehiculo", "S%")));
		andTipVeh.add(Restrictions.not(Restrictions.like("v.tipoVehiculo", "7%")));
		andTipVeh.add(Restrictions.not(Restrictions.like("v.tipoVehiculo", "8%")));
		andTipVeh.add(Restrictions.not(Restrictions.like("v.tipoVehiculo", "9%")));
		criteria.add(andTipVeh);

		ArrayList<BigDecimal> estados = new ArrayList<>();
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
		estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

		criteria.add(Restrictions.in("estado", estados));
		criteria.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.Matriculacion.getValorEnum()));

		return criteria;
	}

	@Override
	public List<Object[]> getListaResumenEstadisticaNRE06(Date fechaInicio, Date fechaFin) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		utilesFecha.setHoraEnDate(fechaInicio, "00:00");
		utilesFecha.setSegundosEnDate(fechaInicio, 00);
		utilesFecha.setHoraEnDate(fechaFin, "23:59");
		utilesFecha.setSegundosEnDate(fechaFin, 59);
		if (fechaInicio != null) {
			criteria.add(Restrictions.ge("fechaRegistroNRE", fechaInicio));
		}
		if (fechaFin != null) {
			criteria.add(Restrictions.le("fechaRegistroNRE", fechaFin));
		}
		criteria.add(Restrictions.isNotNull("nre"));

		criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(Projections.groupProperty(("numColegiado")))
				.add(Projections.groupProperty("fechaRegistroNRE")));

		criteria.addOrder(Order.asc("fechaRegistroNRE"));
		return criteria.list();
	}

	@Override
	public int numeroElementosConsultaTramite(Long idContrato, String estado, Date fechaAltaDesde, Date fechaAltaHasta, Date fechaPresentacionDesde, Date fechaPresentacionHasta, String tipoTramite,
			String refPropia, BigDecimal numExpediente, String nifTitular, String nifFacturacion, String bastidor, String matricula, String tipoTasa, String nive, String presentadoJPT, String conNive)
			throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);

		if (idContrato != null) {
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		}

		if (StringUtils.isNotBlank(estado)) {
			if (EstadoTramiteTrafico.Telematicos.getValorEnum().equals(estado)) {
				ArrayList<BigDecimal> estadosPermitidos = new ArrayList<BigDecimal>();
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));
				criteria.add(Restrictions.in("estado", estadosPermitidos));
			} else if (EstadoTramiteTrafico.TelematicosYPdf.getValorEnum().equals(estado)) {
				ArrayList<BigDecimal> estadosPermitidos = new ArrayList<BigDecimal>();
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));
				criteria.add(Restrictions.in("estado", estadosPermitidos));
			} else {
				criteria.add(Restrictions.eq("estado", new BigDecimal(estado)));
			}
		}

		if (fechaAltaDesde != null) {
			criteria.add(Restrictions.ge("fechaAlta", fechaAltaDesde));
		}

		if (fechaAltaHasta != null) {
			utilesFecha.setHoraEnDate(fechaAltaHasta, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaAltaHasta, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaAlta", fechaAltaHasta));
		}

		if (fechaPresentacionDesde != null) {
			criteria.add(Restrictions.ge("fechaPresentacion", fechaPresentacionDesde));
		}

		if (fechaPresentacionHasta != null) {
			utilesFecha.setHoraEnDate(fechaPresentacionHasta, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaPresentacionHasta, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaPresentacion", fechaPresentacionHasta));
		}

		if (StringUtils.isNotBlank(tipoTramite)) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		} else {
			String ocultarSolicitudes = gestorPropiedades.valorPropertie("ocultar.solicitudes.consulta.tramite");
			ArrayList<String> tiposTramitePermitidos = new ArrayList<String>();
			tiposTramitePermitidos.add(TipoTramiteTrafico.Solicitud_Inteve.getValorEnum());
			tiposTramitePermitidos.add(TipoTramiteTrafico.Inteve.getValorEnum());
			if ("SI".equals(ocultarSolicitudes)) {
				tiposTramitePermitidos.add(TipoTramiteTrafico.Solicitud.getValorEnum());
			}
			criteria.add(Restrictions.not(Restrictions.in("tipoTramite", tiposTramitePermitidos)));
		}

		if (StringUtils.isNotBlank(refPropia)) {
			criteria.add(Restrictions.like("refPropia", "%" + refPropia + "%"));
		}

		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		if (StringUtils.isNotBlank(nifTitular)) {
			criteria.createAlias("intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("intervinienteTraficos.id.nif", nifTitular.toUpperCase().replaceAll(" ", "")));
			if (tipoTramite.equals(TipoTramiteTrafico.Transmision.getValorEnum()) || tipoTramite.equals(TipoTramiteTrafico.TransmisionElectronica.getValorEnum())) {
				criteria.add(Restrictions.eq("intervinienteTraficos.id.tipoInterviniente", TipoInterviniente.Adquiriente.getValorEnum()));
			} else if (tipoTramite.equals(TipoTramiteTrafico.Solicitud.getValorEnum())) {
				criteria.add(Restrictions.eq("intervinienteTraficos.id.tipoInterviniente", TipoInterviniente.Solicitante.getValorEnum()));
			} else {
				criteria.add(Restrictions.eq("intervinienteTraficos.id.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()));
			}
		}

		if (StringUtils.isNotBlank(nifFacturacion)) {
			DetachedCriteria subquery = DetachedCriteria.forClass(TramiteTrafFacturacion.class).add(Restrictions.eq("nif", nifFacturacion.toUpperCase().replaceAll(" ", ""))).setProjection(Projections
					.property("id.numExpediente"));
			criteria.add(Subqueries.propertyIn("numExpediente", subquery));
		}

		if (StringUtils.isNotBlank(bastidor) || StringUtils.isNotBlank(matricula) || StringUtils.isNotBlank(nive) || StringUtils.isNotBlank(conNive)) {
			criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
			if (StringUtils.isNotBlank(bastidor)) {
				if (bastidor.length() >= 17) {
					criteria.add(Restrictions.eq("vehiculo.bastidor", bastidor.toUpperCase()));
				} else {
					criteria.add(Restrictions.like("vehiculo.bastidor", bastidor.toUpperCase() + "%"));
				}
			}
			if (StringUtils.isNotBlank(matricula)) {
				criteria.add(Restrictions.eq("vehiculo.matricula", matricula.toUpperCase().replaceAll(" ", "")));
			}
			if (StringUtils.isNotBlank(nive)) {
				criteria.add(Restrictions.like("vehiculo.nive", "%" + nive + "%"));
			} else if (StringUtils.isNotBlank(conNive)) {
				if ("SI".equals(conNive)) {
					criteria.add(Restrictions.isNotNull("vehiculo.nive"));
				} else if ("NO".equals(conNive)) {
					criteria.add(Restrictions.isNull("vehiculo.nive"));
				}
			}
		}

		if (StringUtils.isNotBlank(tipoTasa)) {
			criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("tasa.tipoTasa", tipoTasa));
		}

		if (StringUtils.isNotBlank(presentadoJPT)) {
			if ("SI".equals(presentadoJPT)) {
				criteria.add(Restrictions.eq("presentadoJpt", EstadoPresentacionJPT.Presentado.getValorEnum()));
			} else if ("NO".equals(presentadoJPT)) {
				criteria.add(Restrictions.or(Restrictions.isNull("presentadoJpt"), Restrictions.eq("presentadoJpt", EstadoPresentacionJPT.No_Presentado.getValorEnum())));
			}
		}

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<Object> buscarConsultaTramite(Long idContrato, String estado, Date fechaAltaDesde, Date fechaAltaHasta, Date fechaPresentacionDesde, Date fechaPresentacionHasta,
			String tipoTramite, String refPropia, BigDecimal numExpediente, String nifTitular, String nifFacturacion, String bastidor, String matricula, String tipoTasa, String nive,
			String presentadoJPT, String conNive, int firstResult, int maxResult, String dir, String sort) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);

		if (idContrato != null) {
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		}

		if (StringUtils.isNotBlank(estado)) {
			if (EstadoTramiteTrafico.Telematicos.getValorEnum().equals(estado)) {
				ArrayList<BigDecimal> estadosPermitidos = new ArrayList<>();
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));
				criteria.add(Restrictions.in("estado", estadosPermitidos));
			} else if (EstadoTramiteTrafico.TelematicosYPdf.getValorEnum().equals(estado)) {
				ArrayList<BigDecimal> estadosPermitidos = new ArrayList<>();
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
				estadosPermitidos.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));
				criteria.add(Restrictions.in("estado", estadosPermitidos));
			} else {
				criteria.add(Restrictions.eq("estado", new BigDecimal(estado)));
			}
		}

		if (fechaAltaDesde != null) {
			criteria.add(Restrictions.ge("fechaAlta", fechaAltaDesde));
		}

		if (fechaAltaHasta != null) {
			utilesFecha.setHoraEnDate(fechaAltaHasta, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaAltaHasta, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaAlta", fechaAltaHasta));
		}

		if (fechaPresentacionDesde != null) {
			criteria.add(Restrictions.ge("fechaPresentacion", fechaPresentacionDesde));
		}

		if (fechaPresentacionHasta != null) {
			utilesFecha.setHoraEnDate(fechaPresentacionHasta, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaPresentacionHasta, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaPresentacion", fechaPresentacionHasta));
		}

		if (StringUtils.isNotBlank(tipoTramite)) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		} else {
			String ocultarSolicitudes = gestorPropiedades.valorPropertie("ocultar.solicitudes.consulta.tramite");
			ArrayList<String> tiposTramitePermitidos = new ArrayList<String>();
			tiposTramitePermitidos.add(TipoTramiteTrafico.Solicitud_Inteve.getValorEnum());
			tiposTramitePermitidos.add(TipoTramiteTrafico.Inteve.getValorEnum());
			if ("SI".equals(ocultarSolicitudes)) {
				tiposTramitePermitidos.add(TipoTramiteTrafico.Solicitud.getValorEnum());
			}
			criteria.add(Restrictions.not(Restrictions.in("tipoTramite", tiposTramitePermitidos)));
		}

		if (StringUtils.isNotBlank(refPropia)) {
			criteria.add(Restrictions.ilike("refPropia", "%" + refPropia + "%"));
		}

		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		if (StringUtils.isNotBlank(nifTitular)) {
			criteria.createAlias("intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("intervinienteTraficos.id.nif", nifTitular.toUpperCase().replaceAll(" ", "")));
			if (tipoTramite.equals(TipoTramiteTrafico.Transmision.getValorEnum()) || tipoTramite.equals(TipoTramiteTrafico.TransmisionElectronica.getValorEnum())) {
				criteria.add(Restrictions.eq("intervinienteTraficos.id.tipoInterviniente", TipoInterviniente.Adquiriente.getValorEnum()));
			} else if (tipoTramite.equals(TipoTramiteTrafico.Solicitud.getValorEnum())) {
				criteria.add(Restrictions.eq("intervinienteTraficos.id.tipoInterviniente", TipoInterviniente.Solicitante.getValorEnum()));
			} else {
				criteria.add(Restrictions.eq("intervinienteTraficos.id.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()));
			}
		}

		if (StringUtils.isNotBlank(nifFacturacion)) {
			DetachedCriteria subquery = DetachedCriteria.forClass(TramiteTrafFacturacion.class).add(Restrictions.eq("nif", nifFacturacion.toUpperCase().replaceAll(" ", ""))).setProjection(Projections
					.property("id.numExpediente"));
			criteria.add(Subqueries.propertyIn("numExpediente", subquery));
		}

		if (StringUtils.isNotBlank(bastidor) || StringUtils.isNotBlank(matricula) || StringUtils.isNotBlank(nive) || StringUtils.isNotBlank(conNive)) {
			if (StringUtils.isNotBlank(bastidor)) {
				if (bastidor.length() >= 17) {
					criteria.add(Restrictions.eq("vehiculo.bastidor", bastidor.toUpperCase()));
				} else {
					criteria.add(Restrictions.like("vehiculo.bastidor", bastidor.toUpperCase() + "%"));
				}
			}
			if (StringUtils.isNotBlank(matricula)) {
				criteria.add(Restrictions.eq("vehiculo.matricula", matricula.toUpperCase().replaceAll(" ", "")));
			}
			if (StringUtils.isNotBlank(nive)) {
				criteria.add(Restrictions.like("vehiculo.nive", "%" + nive + "%"));
			} else if (StringUtils.isNotBlank(conNive)) {
				if ("SI".equals(conNive)) {
					criteria.add(Restrictions.isNotNull("vehiculo.nive"));
				} else if ("NO".equals(conNive)) {
					criteria.add(Restrictions.isNull("vehiculo.nive"));
				}
			}
		}

		if (StringUtils.isNotBlank(tipoTasa)) {
			criteria.add(Restrictions.eq("tasa.tipoTasa", tipoTasa));
		}

		if (StringUtils.isNotBlank(presentadoJPT)) {
			if ("SI".equals(presentadoJPT)) {
				criteria.add(Restrictions.eq("presentadoJpt", EstadoPresentacionJPT.Presentado.getValorEnum()));
			} else if ("NO".equals(presentadoJPT)) {
				criteria.add(Restrictions.or(Restrictions.isNull("presentadoJpt"), Restrictions.eq("presentadoJpt", EstadoPresentacionJPT.No_Presentado.getValorEnum())));
			}
		}

		criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);

		if (firstResult > 0) {
			criteria.setFirstResult(firstResult);
		}

		if (maxResult > 0) {
			criteria.setMaxResults(maxResult);
		}

		if (StringUtils.isNotBlank(dir) && StringUtils.isNotBlank(sort)) {
			if ("desc".equals(dir)) {
				criteria.addOrder(Order.desc(sort));
			} else {
				criteria.addOrder(Order.asc(sort));
			}
		} else {
			criteria.addOrder(Order.asc("numExpediente"));
		}

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("numExpediente"));
		projections.add(Projections.property("refPropia"));
		projections.add(Projections.property("vehiculo.bastidor"));
		projections.add(Projections.property("vehiculo.matricula"));
		projections.add(Projections.property("tasa.tipoTasa"));
		projections.add(Projections.property("tasa.codigoTasa"));
		projections.add(Projections.property("tipoTramite"));
		projections.add(Projections.property("estado"));
		projections.add(Projections.property("presentadoJpt"));
		projections.add(Projections.property("respuesta"));
		criteria.setProjection(projections);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTramitePorNRE() throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		Date fechaRegIni = new Date();
		Date fechaRegFin = new Date();
		utilesFecha.setHoraEnDate(fechaRegIni, "00:00");
		utilesFecha.setSegundosEnDate(fechaRegIni, 00);
		utilesFecha.setHoraEnDate(fechaRegFin, "23:59");
		utilesFecha.setSegundosEnDate(fechaRegFin, 59);
		fechaRegIni = utilesFecha.restarMeses(fechaRegIni, 1, null, null, null);
		fechaRegIni = utilesFecha.restarDias(fechaRegIni, -1, 23, 59, 59);
		criteria.add(Restrictions.ge("fechaRegistroNRE", fechaRegIni));
		criteria.add(Restrictions.le("fechaRegistroNRE", fechaRegFin));
		criteria.add(Restrictions.isNotNull("nre"));
		criteria.setProjection(Projections.projectionList().add(Projections.rowCount()).add(Projections.groupProperty(("numColegiado")))
				.add(Projections.groupProperty("fechaRegistroNRE")));
		criteria.addOrder(Order.asc("fechaRegistroNRE"));
		return criteria.list();
	}

	@Override
	public List<TramiteTraficoVO> listadoPantallasEstadisticas(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones) {
		try {
			Criteria crit = createCriterion(filter);
			anadirEntitiesJoin(crit, entitiesJoin);
			anadirFetchModesJoin(crit, fetchModeJoinList);
			anadirListaProyecciones(crit, listaProyecciones);

			@SuppressWarnings("unchecked")
			List<TramiteTraficoVO> lista = crit.list();
			if (lista != null && !lista.isEmpty()) {
				return lista;
			}

		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al obtener el número de elementos por criterios del objeto de tipo " + getType().getName(), e);
		}
		return Collections.emptyList();
	}

	@Override
	public String obtenerRespuestaTramiteTrafico(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		TramiteTraficoVO tramiteTraficoVO =  (TramiteTraficoVO) criteria.uniqueResult();
		String respuesta = tramiteTraficoVO.getRespuesta();
		getCurrentSession().evict(tramiteTraficoVO);
		return respuesta;
	}

	/*
	 * @Override public TramiteTraficoVO asociarExpediente(BigDecimal numExpediente, String numExpedienteAsociado) { TramiteTraficoVO tramite = buscarPorExpediente(numExpediente); // Expedientes_asociados tramite. setExpedientesAsociados(numExpedienteAsociado); tramite.setFechaUltModif(new Date());
	 * tramite = (TramiteTraficoVO) guardarOActualizar(tramite); if (tramite != null) { return tramite; } return null; }
	 * @Override public int usuarioTienePermisoSobreTramites(BigDecimal[] numExpediente, Long idContrato) { Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoVO.class); criteria.add(Restrictions.in("numExpediente", numExpediente));
	 * criteria.add(Restrictions.ne("contrato.idContrato", idContrato)); criteria.createAlias("contrato", "contrato"); criteria.setProjection(Projections.rowCount()); return (Integer) criteria.uniqueResult(); } /*
	 * @Override public TramiteTraficoVO asociarExpediente(BigDecimal numExpediente, String numExpedienteAsociado) { TramiteTraficoVO tramite = buscarPorExpediente(numExpediente); // Expedientes_asociados tramite. setExpedientesAsociados(numExpedienteAsociado); tramite.setFechaUltModif(new Date());
	 * tramite = (TramiteTraficoVO) guardarOActualizar(tramite); if (tramite != null) { return tramite; } return null; }
	 * @Override public TramiteTraficoVO desAsociarExpediente(BigDecimal numExpediente) { TramiteTraficoVO tramite = buscarPorExpediente(numExpediente); //List<String> listaExpedAsoc = getListaTramitesAsociadosExpediente(tramite.getNumExpediente()); // Expedientes_asociados if (tramite != null &&
	 * tramite.getExpedientesAsociados()!=null) { tramite. setExpedientesAsociados(null); tramite.setFechaUltModif(new Date()); tramite = (TramiteTraficoVO) guardarOActualizar(tramite); return tramite; } return null; }
	 * @Override public List<String> getListaTramitesAsociadosExpediente(BigDecimal numExpediente) { TramiteTraficoVO tramite = buscarPorExpediente(numExpediente); // Lista de Expedientes Asociados List<String> listaExpedAsoc = new ArrayList<String>(); if (tramite != null) { String[]
	 * Expedientes_Asoc = tramite.getExpedientesAsociados().split(","); for (String Exp_Asoc : Expedientes_Asoc) { listaExpedAsoc.add(Exp_Asoc); } } if (listaExpedAsoc != null) { return (List<String>) listaExpedAsoc; } return null; }
	 */

	/*
	 * @Override public List<BeanPresentadoJpt> getTramitesAsociadosPorExpediente(String n_expediente) { Session session = getCurrentSession(); Criteria criteria = session.createCriteria(TramiteTraficoVO.class); // SE considera que no es nulo el campo expedientes_asociados
	 * criteria.add(Restrictions.isNotNull("expedientesasociados")); } return criteria.list(); }
	 */
}
