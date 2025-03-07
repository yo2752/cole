package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.ComunidadAutonomaDao;
import org.gestoresmadrid.core.registradores.model.vo.ComunidadAutonomaVO;
import org.springframework.stereotype.Repository;

@Repository
public class ComunidadAutonomaDaoImpl extends GenericDaoImplHibernate<ComunidadAutonomaVO> implements ComunidadAutonomaDao {

	private static final long serialVersionUID = -3986424106749224617L;

}
