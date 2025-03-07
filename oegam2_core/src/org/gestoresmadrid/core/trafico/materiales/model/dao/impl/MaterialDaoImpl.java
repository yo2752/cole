package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MaterialDaoImpl extends GenericDaoImplHibernate<MaterialVO> implements MaterialDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1319022469337617756L;

	@Override
	@SuppressWarnings("unchecked")
	public List<MaterialVO> getListaMateriales() {
		return getCurrentSession().createCriteria(MaterialVO.class).list();	
	}

	@Override
	public MaterialVO findByPrimaryKey(Long materialId) {
		Criteria criteria = getCurrentSession().createCriteria(MaterialVO.class, "material");
		criteria.add(Restrictions.eq("materialId", materialId));
		
		return (MaterialVO) criteria.uniqueResult();
	}

	@Override
	public MaterialVO findMaterialByTipo(String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(MaterialVO.class, "material");
		criteria.add(Restrictions.eq("tipo", tipo));
		
		return (MaterialVO) criteria.uniqueResult();
	}

}
