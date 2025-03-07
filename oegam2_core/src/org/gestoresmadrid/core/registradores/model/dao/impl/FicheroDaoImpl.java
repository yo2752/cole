package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.FicheroDao;
import org.gestoresmadrid.core.registradores.model.vo.FicheroVO;
import org.springframework.stereotype.Repository;

@Repository
public class FicheroDaoImpl extends GenericDaoImplHibernate<FicheroVO> implements FicheroDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1815309397804883536L;
}