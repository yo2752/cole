package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.OtroImporteDao;
import org.gestoresmadrid.core.registradores.model.vo.OtroImporteVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class OtroImporteDaoImpl extends GenericDaoImplHibernate<OtroImporteVO> implements OtroImporteDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1656512413293692843L;

	@Override
	public OtroImporteVO getOtroImporte(String id) {
		Criteria criteria = getCurrentSession().createCriteria(OtroImporteVO.class);
		criteria.add(Restrictions.eq("idOtroImporte", Long.parseLong(id)));
		return (OtroImporteVO) criteria.uniqueResult();
	}
}