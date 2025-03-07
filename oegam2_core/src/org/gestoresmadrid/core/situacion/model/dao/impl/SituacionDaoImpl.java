package org.gestoresmadrid.core.situacion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.situacion.model.dao.SituacionDao;
import org.gestoresmadrid.core.situacion.model.vo.SituacionVO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class SituacionDaoImpl extends GenericDaoImplHibernate<SituacionVO> implements SituacionDao {

	private static final long serialVersionUID = -7266651840280799097L;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<SituacionVO> getListaSituacion() {
		Criteria criteria = getCurrentSession().createCriteria(SituacionVO.class);
		return criteria.list();
	}
}
