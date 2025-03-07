package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.general.model.dao.UsuarioPermisoDao;
import org.gestoresmadrid.core.general.model.vo.SecUsuarioPermisoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioPermisoDaoImpl extends GenericDaoImplHibernate<SecUsuarioPermisoVO> implements UsuarioPermisoDao{

	private static final long serialVersionUID = 512435131363502674L;

	@SuppressWarnings("unchecked")
	@Override
	public List<SecUsuarioPermisoVO> getlistaPermisosPorUsuario(Long idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(SecUsuarioPermisoVO.class);
		criteria.add(Restrictions.eq("id.idUsuario", idUsuario));
		criteria.createAlias("usuario", "usuario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("secPermiso", "secPermiso",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
}
