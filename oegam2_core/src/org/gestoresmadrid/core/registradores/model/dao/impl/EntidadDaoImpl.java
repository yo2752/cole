package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.EntidadDao;
import org.gestoresmadrid.core.registradores.model.vo.EntidadVO;
import org.springframework.stereotype.Repository;

@Repository
public class EntidadDaoImpl extends GenericDaoImplHibernate<EntidadVO> implements EntidadDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 945510437931343781L;

}
