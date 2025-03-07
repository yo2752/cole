package org.gestoresmadrid.core.cola.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.cola.enumerados.EstadoCola;
import org.gestoresmadrid.core.cola.model.dao.ColaDao;
import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;

@Repository
public class ColaDaoImpl extends GenericDaoImplHibernate<ColaVO> implements ColaDao {

	private static final long serialVersionUID = -7858682682684035907L;
	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public int getNumColasProceso(String proceso, String nodo, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		criteria.add(Restrictions.eq("proceso", proceso));
		criteria.add(Restrictions.eq("idContrato", new BigDecimal(idContrato)));
		if (nodo != null && !nodo.isEmpty()) {
			criteria.add(Restrictions.eq("nodo", nodo));
		}
		criteria.add(Restrictions.ne("estado", new BigDecimal(EstadoCola.HILO_CREADO.getValorEnum())));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (int) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColaVO> getColasActivasProceso(String proceso, BigDecimal idContrato, String xmlEnviar) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		criteria.add(Restrictions.eq("proceso", proceso));
		if (idContrato != null) {
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		if (xmlEnviar != null && !xmlEnviar.isEmpty()) {
			criteria.add(Restrictions.ilike("xmlEnviar", xmlEnviar + "%"));
		}
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(EstadoCola.PENDIENTES_ENVIO.getValorEnum()), new BigDecimal(EstadoCola.EJECUTANDO.getValorEnum()) }));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColaVO> getColasCreadasPorProcesoIdTramite(String proceso, String nodo, BigDecimal idTramite) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		criteria.add(Restrictions.eq("proceso", proceso));
		criteria.add(Restrictions.eq("nodo", nodo));
		criteria.add(Restrictions.eq("idTramite", idTramite));
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(EstadoCola.PENDIENTES_ENVIO.getValorEnum()), new BigDecimal(EstadoCola.EJECUTANDO.getValorEnum()), new BigDecimal(
				EstadoCola.ERROR_SERVICIO.getValorEnum()) }));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColaVO> getColasCreadasPorProceso(String proceso, String nodo) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		criteria.add(Restrictions.eq("proceso", proceso));
		criteria.add(Restrictions.eq("nodo", nodo));
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(EstadoCola.PENDIENTES_ENVIO.getValorEnum()), new BigDecimal(EstadoCola.EJECUTANDO.getValorEnum()), new BigDecimal(
				EstadoCola.ERROR_SERVICIO.getValorEnum()) }));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColaVO> getColasActivasProceso(String proceso, String nodo) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		criteria.add(Restrictions.eq("proceso", proceso));
		criteria.add(Restrictions.eq("nodo", nodo));
		criteria.add(Restrictions.ne("estado", new BigDecimal(EstadoCola.HILO_CREADO.getValorEnum())));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ColaVO getColaSolicitudProceso(String proceso, String cola, String nodo) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		criteria.add(Restrictions.eq("proceso", proceso));
		criteria.add(Restrictions.eq("cola", cola));
		criteria.add(Restrictions.eq("nodo", nodo));
		criteria.add(Restrictions.eq("estado", new BigDecimal(EstadoCola.PENDIENTES_ENVIO.getValorEnum())));
		criteria.addOrder(Order.asc("fechaHora"));
		List<ColaVO> listaBBDD = criteria.list();
		if (listaBBDD != null && !listaBBDD.isEmpty()) {
			return listaBBDD.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ColaVO getColaPrincipal(String proceso, String cola, String nodo) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		criteria.add(Restrictions.eq("proceso", proceso));
		criteria.add(Restrictions.eq("cola", cola));
		criteria.add(Restrictions.eq("nodo", nodo));
		criteria.add(Restrictions.eq("estado", new BigDecimal(EstadoCola.HILO_CREADO.getValorEnum())));
		List<ColaVO> listaBBDD = criteria.list();
		if (listaBBDD != null && !listaBBDD.isEmpty()) {
			return listaBBDD.get(0);
		}
		return null;
	}

	@Override
	public ColaVO getCola(Long idEnvio) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		criteria.add(Restrictions.eq("idEnvio", idEnvio));
		return (ColaVO) criteria.uniqueResult();
	}

	@Override
	public ColaVO getColaPorIdTramite(BigDecimal idTramite, String proceso) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		if (idTramite != null) {
			listCriterion.add(Restrictions.eq("idTramite", idTramite));
		}
		if (proceso != null && !proceso.isEmpty()) {
			listCriterion.add(Restrictions.eq("proceso", proceso));
		}

		List<ColaVO> lista = buscarPorCriteria(listCriterion, null, null);
		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<ColaVO> getHiloNuevo(ColaVO colaVO) {
		try {
			String[] namedParemeters = { "nodo1", "proceso1", "nodo2", "proceso2", "nodo3", "proceso3" };
			String[] namedValues = { colaVO.getNodo(), colaVO.getProceso(), colaVO.getNodo(), colaVO.getProceso(), colaVO.getNodo(), colaVO.getProceso() };

			return listNamedQuery(ColaVO.COLA_BUSQUEDA_HILO_ACTIVO, namedParemeters, namedValues);
		} catch (HibernateException e) {

		}
		return null;
	}

	@Override
	public String getHilo(ColaVO colaVO) {
		Session session = getCurrentSession();
		String hql = "select cola from COLA " + "where cola in (select cola from COLA " + "where nodo = '" + colaVO.getNodo() + "' " + "and proceso = '" + colaVO.getProceso() + "' group by cola "
				+ "having count(*) = (select min(count(*)) from COLA " + "where nodo = '" + colaVO.getNodo() + "' " + "and proceso = '" + colaVO.getProceso() + "' "
				+ "and cola in (select distinct cola from COLA where estado = 0 " + "and nodo = '" + colaVO.getNodo() + "' " + "and proceso = '" + colaVO.getProceso() + "') " + "group by cola )) "
				+ "and rownum=1";

		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<String> l = query.list();

		if (l != null && !l.isEmpty()) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public List<ColaVO> getListaColaTramites(ColaVO colaVO) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		listCriterion.add(Restrictions.eq("proceso", colaVO.getProceso()));

		Criterion crt1 = Restrictions.eq("idTramite", colaVO.getIdTramite());

		Criterion crt2 = Restrictions.and(Restrictions.eq("xmlEnviar", colaVO.getXmlEnviar()), Restrictions.and(Restrictions.and(Restrictions.ne("proceso", "INFOWS"), Restrictions.ne("proceso",
				"IVTM")), Restrictions.ne("proceso", "CONSULTA_EITV")));

		listCriterion.add(Restrictions.or(crt1, crt2));

		listCriterion.add(Restrictions.eq("estado", colaVO.getEstado()));

		return buscarPorCriteria(listCriterion, null, null);
	}

	// Mantis 22887
	// Recupera el total de procesos para el dia de hoy
	@Override
	public List<ColaVO> getListaColaTramitesHoy() {

		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		try {
			utilesFecha.setHoraEnDate(fin, horaFInDia);
			utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
			listCriterion.add(Restrictions.between("fechaHora", fecha.getFecha(), fin));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buscarPorCriteria(listCriterion, null, null);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> solicitudesAgrupadasPorHilos(String proceso) {
		List<Object[]> result;
		if (proceso != null && !proceso.isEmpty()) {
			SQLQuery sqlQuery = getCurrentSession().createSQLQuery(
					"SELECT cola, count(case when estado =1 then estado else null end ), count(case when estado =9 then estado else null end ) FROM cola where proceso=:proceso group by cola order by to_number(cola)");
			sqlQuery.setString("proceso", proceso);
			result = (List<Object[]>) sqlQuery.list();
		} else {
			result = Collections.emptyList();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> solicitudesAgrupadasPorHilos() {
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(
				"select proceso, count(case when estado <>0 then estado else null end) from cola where proceso in (select proceso from proceso where estado = 1) group by proceso order by proceso");
		return (List<Object[]>) sqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColaVO> getSolicitudesCola(String numExpediente, String proceso, String tipoTramite, String estado, String cola, String matricula, String bastidor, FechaFraccionada fecha,
			String nodo) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);

		if (numExpediente != null && !numExpediente.isEmpty()) {
			criteria.add(Restrictions.eq("idTramite", new BigDecimal(numExpediente)));
		}

		if (proceso != null && !proceso.isEmpty()) {
			criteria.add(Restrictions.eq("proceso", proceso));
		}

		if (tipoTramite != null && !tipoTramite.isEmpty()) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		}

		if (estado != null && !estado.isEmpty()) {
			criteria.add(Restrictions.eq("estado", new BigDecimal(estado)));
		} else {
			criteria.add(Restrictions.ne("estado", new BigDecimal(EstadoCola.HILO_CREADO.getValorEnum())));
		}

		if (cola != null && !cola.isEmpty()) {
			criteria.add(Restrictions.eq("cola", cola));
		}

		if (matricula != null && !matricula.isEmpty()) {
			criteria.add(Restrictions.eq("matricula", matricula));
		}

		if (bastidor != null && !bastidor.isEmpty()) {
			criteria.add(Restrictions.eq("bastidor", bastidor));
		}

		if (fecha != null && !fecha.isfechaInicioNula()) {
			criteria.add(Restrictions.ge("fechaHora", fecha.getFechaMinInicio()));
		}

		if (fecha != null && !fecha.isfechaFinNula()) {
			criteria.add(Restrictions.le("fechaHora", fecha.getFechaMaxFin()));
		}

		if (nodo != null && !nodo.isEmpty()) {
			criteria.add(Restrictions.eq("nodo", nodo));
		}
		criteria.addOrder(Order.asc("idEnvio"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColaVO> getSolicitudesColaMonitorizacion(String proceso, String estado, FechaFraccionada fecha, String hostPeticiones1, String hostPeticiones2) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);

		if (proceso != null && !proceso.isEmpty()) {
			criteria.add(Restrictions.eq("proceso", proceso));
		}

		if (estado != null && !estado.isEmpty()) {
			criteria.add(Restrictions.eq("estado", new BigDecimal(estado)));
		} else {
			criteria.add(Restrictions.ne("estado", new BigDecimal(EstadoCola.HILO_CREADO.getValorEnum())));
		}

		if (fecha != null && !fecha.isfechaInicioNula()) {
			criteria.add(Restrictions.ge("fechaHora", fecha.getFechaMinInicio()));
		}

		if (fecha != null && !fecha.isfechaFinNula()) {
			criteria.add(Restrictions.le("fechaHora", fecha.getFechaMaxFin()));
		}

		criteria.add(Restrictions.or(Restrictions.eq("nodo", hostPeticiones1), Restrictions.eq("nodo", hostPeticiones2)));

		criteria.addOrder(Order.asc("idEnvio"));
		return criteria.list();
	}

	@Override
	public Integer recuperarNumPendientesPorProceso(String proceso, String[] numHost, FechaFraccionada fechaFracionadaActual) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerMaxCola() {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		criteria.setProjection(Projections.max("cola"));
		return (String) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColaVO> existeColaTramiteProceso(BigDecimal idTramite, String proceso) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		criteria.add(Restrictions.eq("idTramite", idTramite));
		criteria.add(Restrictions.eq("proceso", proceso));
		return criteria.list();
	}

	@Override
	public String getHilo(String nodo, String proceso) {
		Session session = getCurrentSession();
		String hql = "select cola from COLA " + "where cola in (select cola from COLA " + "where nodo = '" + nodo + "' " + "and proceso = '" + proceso + "' group by cola "
				+ "having count(*) = (select min(count(*)) from COLA " + "where nodo = '" + nodo + "' " + "and proceso = '" + proceso + "' "
				+ "and cola in (select distinct cola from COLA where estado = 0 " + "and nodo = '" + nodo + "' " + "and proceso = '" + proceso + "') " + "group by cola )) " + "and rownum=1";

		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<String> l = query.list();

		if (l != null && !l.isEmpty()) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public List<Object[]> consultaCreditosBloqueadosPorContrato(BigDecimal idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ColaVO.class);
		if (idContrato != null) {
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(1), new BigDecimal(9) }));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("tipoTramite")).add(Projections.rowCount()));
		@SuppressWarnings("unchecked")
		List<Object[]> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

}
