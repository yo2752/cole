package org.gestoresmadrid.core.impr.model.dao.impl;

import org.gestoresmadrid.core.impr.model.dao.EvolucionImprDao;
import org.gestoresmadrid.core.impr.model.vo.EvolucionImprVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionImprDaoImpl extends GenericDaoImplHibernate<EvolucionImprVO> implements EvolucionImprDao{
	private static final long serialVersionUID = -9081764785611217155L;
}