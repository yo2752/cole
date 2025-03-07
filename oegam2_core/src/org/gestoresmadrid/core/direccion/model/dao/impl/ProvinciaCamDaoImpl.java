package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.ProvinciaCamDao;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaCamVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ProvinciaCamDaoImpl extends GenericDaoImplHibernate<ProvinciaCamVO> implements ProvinciaCamDao{

	private static final long serialVersionUID = 2284915498376450223L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProvinciaCamVO> getLista() {
		Criteria criteria = getCurrentSession().createCriteria(ProvinciaCamVO.class);
		return criteria.list();
	}

	@Override
	public ProvinciaCamVO getProvincia(String idProvincia) {
		Criteria criteria = getCurrentSession().createCriteria(ProvinciaCamVO.class);
		criteria.add(Restrictions.eq("idProvincia", idProvincia));
		return (ProvinciaCamVO) criteria.uniqueResult();
	}
}
