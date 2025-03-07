package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.MensajeDao;
import org.gestoresmadrid.core.registradores.model.vo.MensajeVO;
import org.springframework.stereotype.Repository;

@Repository
public class MensajeDaoImpl extends GenericDaoImplHibernate<MensajeVO> implements MensajeDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942751473982743640L;
}