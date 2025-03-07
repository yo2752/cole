package org.gestoresmadrid.core.tasas.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.tasas.model.dao.TasaPegatinaDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaPegatinaVO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TasaPegatinaDaoImpl extends GenericDaoImplHibernate<TasaPegatinaVO> implements TasaPegatinaDao {

	private static final long serialVersionUID = -8630257477422731464L;

	@Override
	public TasaPegatinaVO detalle(String codigoTasa) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(TasaPegatinaVO.class);
		criteria.add(Restrictions.eq("codigoTasa", codigoTasa));
		criteria.setFetchMode("contrato", FetchMode.JOIN);
		criteria.setFetchMode("contrato.provincia", FetchMode.JOIN);
		criteria.setFetchMode("usuario", FetchMode.JOIN);
		return (TasaPegatinaVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TasaPegatinaVO> getTasasPorLista(List<String> listaTasas) {
		Criteria criteria = getCurrentSession().createCriteria(TasaPegatinaVO.class);
		criteria.add(Restrictions.in("codigoTasa", listaTasas));
		criteria.setFetchMode("contrato", FetchMode.JOIN);
		criteria.setFetchMode("contrato.provincia", FetchMode.JOIN);
		criteria.setFetchMode("usuario", FetchMode.JOIN);
		return criteria.list();
	}

	@Override
	public List<TasaPegatinaVO> obtenerTasasPegatinaContrato(Long idContrato, String tipoTasa) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		if (idContrato != null) {
			listCriterion.add(Restrictions.eq("contrato.idContrato", idContrato));
		}
		if (tipoTasa != null && !"".equals(tipoTasa)) {
			listCriterion.add(Restrictions.eq("tipoTasa", tipoTasa));
		}

		listCriterion.add(Restrictions.isNull("fechaFacturacion"));

		List<TasaPegatinaVO> lista = buscarPorCriteria(listCriterion, null, null);
		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}

	@Override
	public TasaPegatinaVO getTasaPegatina(String codigoTasa, BigDecimal numExpediente, String tipoTasa) {
		Criteria criteria = getCurrentSession().createCriteria(TasaPegatinaVO.class);
		if (codigoTasa != null && !codigoTasa.isEmpty()) {
			criteria.add(Restrictions.eq("codigoTasa", codigoTasa));
		}
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		if (tipoTasa != null && !tipoTasa.isEmpty()) {
			criteria.add(Restrictions.eq("tipoTasa", tipoTasa));
		}

		criteria.createCriteria("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.setFetchMode("contrato.provincia", FetchMode.JOIN);

		return (TasaPegatinaVO) criteria.uniqueResult();
	}
}