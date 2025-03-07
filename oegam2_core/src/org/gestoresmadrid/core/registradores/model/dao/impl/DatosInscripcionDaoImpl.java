package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.DatosInscripcionDao;
import org.gestoresmadrid.core.registradores.model.vo.DatosInscripcionVO;
import org.springframework.stereotype.Repository;

@Repository
public class DatosInscripcionDaoImpl extends GenericDaoImplHibernate<DatosInscripcionVO> implements DatosInscripcionDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -997356348392718695L;
	
}
