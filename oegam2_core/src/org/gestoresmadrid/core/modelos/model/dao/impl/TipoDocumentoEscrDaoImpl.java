package org.gestoresmadrid.core.modelos.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelos.model.dao.TipoDocumentoEscrDao;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;
import org.gestoresmadrid.core.modelos.model.vo.TipoDocumentoEscrVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class TipoDocumentoEscrDaoImpl extends GenericDaoImplHibernate<TipoDocumentoEscrVO> implements TipoDocumentoEscrDao{

	private static final long serialVersionUID = 8362610319808050413L;
	
	@Override
	public List<TipoDocumentoEscrVO> getListaPorModelo(ModeloVO modelo) {
		Criteria criteria = getCurrentSession().createCriteria(TipoDocumentoEscrVO.class);
		criteria.add(Restrictions.eq("modelo.id.modelo", modelo.getId().getModelo()));
		criteria.add(Restrictions.eq("modelo.id.version", modelo.getId().getVersion()));
		criteria.createAlias("modelo", "modelo",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}

}
