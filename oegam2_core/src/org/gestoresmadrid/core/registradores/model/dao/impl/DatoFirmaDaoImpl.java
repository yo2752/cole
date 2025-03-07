package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.DatoFirmaDao;
import org.gestoresmadrid.core.registradores.model.vo.DatoFirmaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DatoFirmaDaoImpl extends GenericDaoImplHibernate<DatoFirmaVO> implements DatoFirmaDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3331933745380122617L;

	@Override
	public DatoFirmaVO getDatoFirma(String id) {
		Criteria criteria = getCurrentSession().createCriteria(DatoFirmaVO.class);
		criteria.add(Restrictions.eq("idDatoFirma", Long.parseLong(id)));
		return (DatoFirmaVO) criteria.uniqueResult();
	}
}
