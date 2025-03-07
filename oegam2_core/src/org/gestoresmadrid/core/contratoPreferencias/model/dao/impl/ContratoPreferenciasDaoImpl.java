package org.gestoresmadrid.core.contratoPreferencias.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.contratoPreferencias.model.dao.ContratoPreferenciasDao;
import org.gestoresmadrid.core.general.model.vo.ContratoPreferenciaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ContratoPreferenciasDaoImpl extends GenericDaoImplHibernate<ContratoPreferenciaVO> implements ContratoPreferenciasDao{

	private static final long serialVersionUID = 3530430502014751012L;

	@Override
	public BigDecimal obtenerOrdenDocBase(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoPreferenciaVO.class);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		ProjectionList listaProyection = Projections.projectionList();
		listaProyection.add(Projections.property("ordenDocbaseYb"));
		criteria.setProjection(listaProyection);
		return (BigDecimal) criteria.uniqueResult();
	}
	
}
