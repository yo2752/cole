package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.UsuarioColegioDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.UsuarioColegioVO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioColegioDaoImpl extends GenericDaoImplHibernate<UsuarioColegioVO> implements UsuarioColegioDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2911655780070457604L;

	@Override
	public UsuarioColegioVO findAll() {
		// TODO Auto-generated method stub
		Criteria criteria = getCurrentSession().createCriteria(UsuarioColegioVO.class, "usuario");

		return (UsuarioColegioVO) criteria.list().get(0);
	}

}
