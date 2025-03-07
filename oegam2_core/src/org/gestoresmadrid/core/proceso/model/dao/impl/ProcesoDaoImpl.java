package org.gestoresmadrid.core.proceso.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.proceso.model.dao.ProcesoDao;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoPK;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ProcesoDaoImpl extends GenericDaoImplHibernate<ProcesoVO> implements ProcesoDao {

	private static final long serialVersionUID = -7669621637095034043L;

	/**
	 * @see ProcesoDao#tomarProcesos(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProcesoVO> tomarProcesos(String nodo) {
		List<ProcesoVO> lista = null;
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProcesoVO.class);
		criteria.add(Restrictions.eq("id.nodo", nodo));
		lista = criteria.list();
		return lista;
	}

	@Override
	public ProcesoVO getProceso(String proceso, String nodo) {
		Criteria criteria = getCurrentSession().createCriteria(ProcesoVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		return (ProcesoVO) criteria.uniqueResult();
	}

	@Override
	public ProcesoVO getProcesoVO(String proceso) {
		Criteria criteria = getCurrentSession().createCriteria(ProcesoVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		return (ProcesoVO) criteria.uniqueResult();
	}

	@Override
	public List<ProcesoVO> list(ProcesoVO filter, String... initialized) {
		Session session = getCurrentSession();

		Criteria criteria = session.createCriteria(ProcesoVO.class);

		// Recuperamos entidades Lazzy
		for (String param : initialized) {
			if (param.isEmpty()) {
				continue;
			}
			criteria.setFetchMode(param, FetchMode.JOIN);
		}
		if (filter.getId() != null) {
			if (filter.getId().getNodo() != null) {
				criteria.add(Restrictions.eq("id.nodo", filter.getId().getNodo()));
			}
			if (filter.getId().getProceso() != null) {
				criteria.add(Restrictions.eq("id.proceso", filter.getId().getProceso()));
			}
		}
		// TODO En un futuro, puede ser recomendable incluir el filtrado por el resto de campos
		if (filter.getTipo() != null) {
			criteria.add(Restrictions.eq("tipo", filter.getTipo()));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<ProcesoVO> listaColegiados = criteria.list();
		return listaColegiados;
	}

	@Override
	public ProcesoVO get(String nodo, String proceso) {
		ProcesoPK pk = new ProcesoPK();
		pk.setNodo(nodo);
		pk.setProceso(proceso);
		return get(pk);
	}

	@Override
	public ProcesoVO get(ProcesoPK procesoPk) {
		Session session = getCurrentSession();
		return (ProcesoVO) session.get(ProcesoVO.class, procesoPk);
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Obtiene la lista de entidad Proceso para un nodo en concreto, si se pasa una lista de nombres como parámetro. Si no se pasa lista, devuelve todos
	 * @param nodo
	 * @param procesosPatron
	 * @return listaProcesos
	 */
	public ArrayList<ProcesoVO> getProcesosPatron(String nodo, ArrayList<String> procesosPatron) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProcesoVO.class);

		criteria.add(Restrictions.eq("id.nodo", nodo));
		if (procesosPatron != null && procesosPatron.size() > 0) {
			criteria.add(Restrictions.in("id.proceso", procesosPatron));
		}
		return (ArrayList<ProcesoVO>) criteria.list();
	}

	/**
	 * @see ProcesoDao#getIntentosMaximos(String, String)
	 */
	@Override
	public BigDecimal getIntentosMaximos(String nombreProceso, String nodo) {
		Criteria criteria = getCurrentSession().createCriteria(ProcesoVO.class);
		criteria.add(Restrictions.eq("id.nodo", nodo));
		criteria.add(Restrictions.eq("id.proceso", nombreProceso));
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property("nIntentosMax"));
		criteria.setProjection(proList);
		return (BigDecimal) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcesoVO> getProceso(String nodo, Long orden) {
		List<ProcesoVO> lista = null;
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProcesoVO.class);
		criteria.add(Restrictions.eq("id.nodo", nodo));
		criteria.add(Restrictions.eq("orden", orden));
		lista = criteria.list();
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcesoVO> getListaProcesosOrdenados(String nodo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProcesoVO.class);
		criteria.add(Restrictions.eq("id.nodo", nodo));
		criteria.addOrder(Order.asc("orden"));
		return criteria.list();
	}

	@Override
	public ProcesoVO getProcesoPorProcesoYNodo(String proceso, String nodo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProcesoVO.class);
		criteria.add(Restrictions.eq("id.nodo", nodo));
		criteria.add(Restrictions.eq("id.proceso", proceso));
		return  (ProcesoVO) criteria.uniqueResult();
	}

	@Override
	public ProcesoVO getProcesoN(String proceso) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProcesoVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		return  (ProcesoVO) criteria.uniqueResult();
	}

	@Override
	public List<ProcesoVO> getListaProcesos() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProcesoVO.class);
		return criteria.list();
	}
}
