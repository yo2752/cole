package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.DatRegMercantilDao;
import org.gestoresmadrid.core.registradores.model.vo.DatRegMercantilVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DatRegMercantilDaoImpl extends GenericDaoImplHibernate<DatRegMercantilVO> implements DatRegMercantilDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7885533599828064863L;

	@Override
	public DatRegMercantilVO getDatRegMercantil(String id) {
		Criteria criteria = getCurrentSession().createCriteria(DatRegMercantilVO.class);
		criteria.add(Restrictions.eq("idDatRegMercantil", Long.parseLong(id)));
		return (DatRegMercantilVO) criteria.uniqueResult();
	}

}
