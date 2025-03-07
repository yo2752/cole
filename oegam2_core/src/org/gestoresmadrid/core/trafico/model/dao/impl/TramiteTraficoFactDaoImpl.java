package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoFactDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafFacturacionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TramiteTraficoFactDaoImpl extends GenericDaoImplHibernate<TramiteTrafFacturacionVO> implements TramiteTraficoFactDao {

	private static final long serialVersionUID = -7649404768887948456L;

	@Override
	public TramiteTrafFacturacionVO getTramiteTraficoFactura(BigDecimal numExpediente, String codigoTasa) {
		List<Criterion> listCriterion = new ArrayList<>();
		if (numExpediente != null) {
			listCriterion.add(Restrictions.eq("id.numExpediente", numExpediente));
		}

		if (codigoTasa != null && !"".equals(codigoTasa)) {
			listCriterion.add(Restrictions.eq("id.codigoTasa", codigoTasa));
		}

		List<TramiteTrafFacturacionVO> lista = buscarPorCriteria(listCriterion, null, null);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public TramiteTrafFacturacionVO getNifFacturacionPorNumExp(String numeroExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafFacturacionVO.class);
		if(numeroExpediente != null) {
			criteria.add(Restrictions.eq("id.numExpediente", new BigDecimal(numeroExpediente)));
		}
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (TramiteTrafFacturacionVO) criteria.uniqueResult();
	}

	@Override
	public TramiteTrafFacturacionVO getTramiteTraficoFact(BigDecimal numExpediente, Boolean tramiteCompleto) {
		// TODO Auto-generated method stub
		return null;
	}

}