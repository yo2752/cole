package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.TipoInteresReferenciaDao;
import org.gestoresmadrid.core.registradores.model.vo.TipoInteresReferenciaVO;
import org.springframework.stereotype.Repository;

@Repository
public class TipoInteresReferenciaDaoImpl extends GenericDaoImplHibernate<TipoInteresReferenciaVO> implements TipoInteresReferenciaDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6608723315516000491L;
}