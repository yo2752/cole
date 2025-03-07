package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.ViaDao;
import org.gestoresmadrid.core.direccion.model.vo.ViaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ViaDaoImpl extends GenericDaoImplHibernate<ViaVO> implements ViaDao {

	private static final long serialVersionUID = 5072414144301157488L;

	@Override
	public ViaVO getVia(String idProvincia, String idMunicipio, String idTipoVia, String via) {
		List<Criterion> listCriterion = new ArrayList<>();
		if (idProvincia != null && !idProvincia.isEmpty()) {
			listCriterion.add(Restrictions.eq("provincia.idProvincia", idProvincia));
		}
		if (idMunicipio != null && !idMunicipio.isEmpty()) {
			listCriterion.add(Restrictions.eq("idMunicipio", idMunicipio));
		}
		if (idTipoVia != null && !idTipoVia.isEmpty()) {
			listCriterion.add(Restrictions.eq("tipoViaIne.idTipoVia", idTipoVia));
		}
		if (via != null && !via.isEmpty()) {
			listCriterion.add(Restrictions.eq("via", via.toUpperCase().trim()));
		}
		List<ViaVO> lista = buscarPorCriteria(listCriterion, null, null);
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<String> listadoViasUnicasPorTipoVia(String idProvincia) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ViaVO.class);
		if (idProvincia != null && !idProvincia.isEmpty()) {
			criteria.add(Restrictions.eq("provincia.idProvincia", idProvincia));
		}
		criteria.setProjection(Projections.distinct(Projections.property("tipoViaIne.idTipoVia")));
		List<String> lista = (List<String>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<ViaVO> listadoVias(String idProvincia, String idMunicipio, String idTipoVia) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ViaVO.class);
		if (idProvincia != null && !idProvincia.isEmpty()) {
			criteria.add(Restrictions.eq("provincia.idProvincia", idProvincia));
		}
		if (idMunicipio != null && !idMunicipio.isEmpty()) {
			criteria.add(Restrictions.eq("idMunicipio", idMunicipio));
		}
		if (idTipoVia != null && !idTipoVia.isEmpty()) {
			criteria.add(Restrictions.eq("tipoViaIne.idTipoVia", idTipoVia));
		}

		criteria.addOrder(Order.asc("via"));

		List<ViaVO> lista = (List<ViaVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}