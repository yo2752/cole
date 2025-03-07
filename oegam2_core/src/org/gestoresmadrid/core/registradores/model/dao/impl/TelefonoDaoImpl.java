package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.TelefonoDao;
import org.gestoresmadrid.core.registradores.model.vo.TelefonoVO;
import org.springframework.stereotype.Repository;

@Repository
public class TelefonoDaoImpl extends GenericDaoImplHibernate<TelefonoVO> implements TelefonoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8236301054656953915L;
}