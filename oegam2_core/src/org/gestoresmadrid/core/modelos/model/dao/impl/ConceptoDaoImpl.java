package org.gestoresmadrid.core.modelos.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelos.model.dao.ConceptoDao;
import org.gestoresmadrid.core.modelos.model.vo.ConceptoVO;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ConceptoDaoImpl  extends GenericDaoImplHibernate<ConceptoVO> implements ConceptoDao{

	private static final long serialVersionUID = -1109505054868914444L;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConceptoVO> getListaConceptosPorModelo(ModeloVO modelo) {
		Criteria criteria = getCurrentSession().createCriteria(ConceptoVO.class);
		criteria.add(Restrictions.eq("modelo.id.modelo", modelo.getId().getModelo()));
		criteria.add(Restrictions.eq("modelo.id.version", modelo.getId().getVersion()));
		criteria.add(Restrictions.eq("estado", BigDecimal.ONE));
		criteria.createAlias("modelo", "modelo",CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("descConcepto"));
		return criteria.list();
	}
	
	@Override
	public ConceptoVO getConceptoPorAbreviatura(String abreviatura) {
		Criteria criteria = getCurrentSession().createCriteria(ConceptoVO.class);
		criteria.add(Restrictions.eq("id.concepto", abreviatura));
		return (ConceptoVO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConceptoVO> getListaTodosConceptos(String modelo, String version) {
		Criteria criteria = getCurrentSession().createCriteria(ConceptoVO.class);
		if(modelo != null && !modelo.isEmpty()){
			criteria.add(Restrictions.eq("modelo.id.modelo", modelo));
		}
		if(version != null && !version.isEmpty()){
			criteria.add(Restrictions.eq("modelo.id.version", version));
		}
		criteria.add(Restrictions.eq("estado", BigDecimal.ONE));
		criteria.addOrder(Order.asc("descConcepto"));
		criteria.createAlias("modelo", "modelo",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
}
