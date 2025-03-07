package org.gestoresmadrid.core.facturacionDistintivo.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.facturacionDistintivo.model.dao.FacturacionDstvIncDao;
import org.gestoresmadrid.core.facturacionDistintivo.model.vo.FacturacionDstvIncidenciaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FacturacionDstvIncDaoImpl extends GenericDaoImplHibernate<FacturacionDstvIncidenciaVO> implements FacturacionDstvIncDao{

	private static final long serialVersionUID = 4624352967719919978L;

	@Override
	public List<FacturacionDstvIncidenciaVO> getListaIncidenciasFact(Long idDistintivoFacturado) {
		Criteria criteria = getCurrentSession().createCriteria(FacturacionDstvIncidenciaVO.class);
		criteria.add(Restrictions.eq("idDistintivoFacturado", idDistintivoFacturado));
		criteria.createAlias("usuarioIncidencia", "usuarioIncidencia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("facturacionDstv", "facturacionDstv", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
}
