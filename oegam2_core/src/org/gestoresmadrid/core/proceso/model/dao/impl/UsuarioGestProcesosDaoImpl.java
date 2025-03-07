package org.gestoresmadrid.core.proceso.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.proceso.model.dao.UsuarioGestProcesosDao;
import org.gestoresmadrid.core.proceso.model.vo.UsuarioGestProcesosVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioGestProcesosDaoImpl extends GenericDaoImplHibernate<UsuarioGestProcesosVO> implements UsuarioGestProcesosDao {

	private static final long serialVersionUID = 6719728957539142277L;

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioGestProcesosVO> getUsuarioGestProcesosByIdUsuario(Long idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioGestProcesosVO.class);
		if (idUsuario != null) {
			criteria.add(Restrictions.eq("idUsuario", idUsuario));
		}
		criteria.addOrder(Order.desc("fechaAlta"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioGestProcesosVO> getUserProcesosByIdUsuarioAndUser(Long idUsuario, String username) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioGestProcesosVO.class);
		if (idUsuario != null) {
			criteria.add(Restrictions.eq("idUsuario", idUsuario));
		}
		if (username != null && !username.isEmpty()) {
			criteria.add(Restrictions.eq("userName", username));
		}
		criteria.addOrder(Order.desc("fechaAlta"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioGestProcesosVO> getUsuarioGestProcesosByUsername(String username) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioGestProcesosVO.class);
		if (username != null) {
			criteria.add(Restrictions.eq("userName", username));
		}
		criteria.addOrder(Order.desc("fechaAlta"));
		return criteria.list();
	}
}
