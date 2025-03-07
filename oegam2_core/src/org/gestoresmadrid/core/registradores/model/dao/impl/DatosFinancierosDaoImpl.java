package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.DatosFinancierosDao;
import org.gestoresmadrid.core.registradores.model.vo.DatosFinancierosVO;
import org.springframework.stereotype.Repository;

@Repository
public class DatosFinancierosDaoImpl extends GenericDaoImplHibernate<DatosFinancierosVO> implements DatosFinancierosDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1769205279750147398L;
	
}
