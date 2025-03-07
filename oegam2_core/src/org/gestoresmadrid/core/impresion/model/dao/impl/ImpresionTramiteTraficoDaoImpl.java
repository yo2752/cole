package org.gestoresmadrid.core.impresion.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.impresion.model.dao.ImpresionTramiteTraficoDao;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionTramiteTraficoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ImpresionTramiteTraficoDaoImpl extends GenericDaoImplHibernate<ImpresionTramiteTraficoVO> implements ImpresionTramiteTraficoDao {

	private static final long serialVersionUID = 4795365068888231440L;

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> obtenerNumExpedientes(Long idImpresion) {
		Criteria criteria = getCurrentSession().createCriteria(ImpresionTramiteTraficoVO.class);

		criteria.add(Restrictions.eq("idImpresion", idImpresion));

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		criteria.setProjection(projectionList);

		return (List<BigDecimal>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImpresionTramiteTraficoVO> getImpresionesTramiteTrafPorIdImpresion(Long idImpresion) {
		Criteria criteria = getCurrentSession().createCriteria(ImpresionTramiteTraficoVO.class);
		criteria.add(Restrictions.eq("idImpresion", idImpresion));
		return criteria.list();
	}
	
	@Override
	public ImpresionTramiteTraficoVO getTramiteImpresion(BigDecimal numExpediente, String tipoImpresion) {
		Criteria criteria = getCurrentSession().createCriteria(ImpresionTramiteTraficoVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		criteria.add(Restrictions.eq("tipoImpresion", tipoImpresion));
		return (ImpresionTramiteTraficoVO) criteria.uniqueResult();
	}
}
