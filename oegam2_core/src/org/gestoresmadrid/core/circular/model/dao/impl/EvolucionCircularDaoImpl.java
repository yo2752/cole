package org.gestoresmadrid.core.circular.model.dao.impl;

import org.gestoresmadrid.core.circular.model.dao.EvolucionCircularDao;
import org.gestoresmadrid.core.circular.model.vo.EvolucionCircularVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionCircularDaoImpl extends GenericDaoImplHibernate<EvolucionCircularVO> implements EvolucionCircularDao{

	private static final long serialVersionUID = -238487145579793340L;

}
