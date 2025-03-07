package org.gestoresmadrid.core.evolucionRemesas.model.dao.impl;

import org.gestoresmadrid.core.evolucionRemesas.model.dao.EvolucionRemesasDao;
import org.gestoresmadrid.core.evolucionRemesas.model.vo.EvolucionRemesasVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionRemesasDaoImpl extends GenericDaoImplHibernate<EvolucionRemesasVO> implements EvolucionRemesasDao{

	private static final long serialVersionUID = 73329639995927505L;

}
