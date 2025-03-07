package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.PaisRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.PaisRegistroVO;
import org.springframework.stereotype.Repository;

@Repository
public class PaisRegistroDaoImpl extends GenericDaoImplHibernate<PaisRegistroVO> implements PaisRegistroDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3712007636068411450L;
}