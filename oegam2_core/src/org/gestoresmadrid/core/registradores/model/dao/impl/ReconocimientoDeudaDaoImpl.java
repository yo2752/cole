package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.ReconocimientoDeudaDao;
import org.gestoresmadrid.core.registradores.model.vo.ReconocimientoDeudaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ReconocimientoDeudaDaoImpl extends GenericDaoImplHibernate<ReconocimientoDeudaVO> implements ReconocimientoDeudaDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6863220726409863156L;

	@Override
	public ReconocimientoDeudaVO getReconocimientoDeuda(String id) {
		Criteria criteria = getCurrentSession().createCriteria(ReconocimientoDeudaVO.class);
		criteria.add(Restrictions.eq("idReconocimientoDeuda", Long.parseLong(id)));
		return (ReconocimientoDeudaVO) criteria.uniqueResult();
	}
}