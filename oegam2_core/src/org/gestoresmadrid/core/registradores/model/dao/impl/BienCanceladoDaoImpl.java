package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.BienCanceladoDao;
import org.gestoresmadrid.core.registradores.model.vo.BienCanceladoVO;
import org.springframework.stereotype.Repository;

@Repository
public class BienCanceladoDaoImpl extends GenericDaoImplHibernate<BienCanceladoVO> implements BienCanceladoDao {

	private static final long serialVersionUID = 1738758982980952867L;
	
}
