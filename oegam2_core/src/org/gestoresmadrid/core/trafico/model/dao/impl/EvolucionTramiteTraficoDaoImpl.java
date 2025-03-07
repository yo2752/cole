package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.dao.EvolucionTramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionTramiteTraficoDaoImpl extends GenericDaoImplHibernate<EvolucionTramiteTraficoVO> implements EvolucionTramiteTraficoDao {

	private static final long serialVersionUID = 8750467447341400611L;

	@Override
	public List<EvolucionTramiteTraficoVO> getTramiteTrafico(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionTramiteTraficoVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		}

		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("id.fechaCambio"));

		@SuppressWarnings("unchecked")
		List<EvolucionTramiteTraficoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}

		return Collections.emptyList();
	}

	@Override
	public int getNumeroFinalizacionesConError(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionTramiteTraficoVO.class);
		criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		criteria.add(Restrictions.eq("id.estadoNuevo", new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())));
		return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionTramiteTraficoVO> getTramiteFinalizadoErrorAutorizado(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionTramiteTraficoVO.class);
		criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		criteria.add(Restrictions.eq("id.estadoAnterior", new BigDecimal(EstadoTramiteTrafico.Autorizado.getValorEnum())));
		return criteria.list();
	}

}
