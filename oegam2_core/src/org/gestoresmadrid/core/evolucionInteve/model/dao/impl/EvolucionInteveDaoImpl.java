package org.gestoresmadrid.core.evolucionInteve.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import org.gestoresmadrid.core.evolucionInteve.model.dao.EvolucionInteveDao;
import org.gestoresmadrid.core.evolucionInteve.model.vo.EvolucionInteveVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;


@Repository
public class EvolucionInteveDaoImpl extends GenericDaoImplHibernate<EvolucionInteveVO> implements EvolucionInteveDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 627019006204719275L;

	@Override
	public List<EvolucionInteveVO> getListaEvolucionesPorNumExp(BigDecimal numExpediente) {
		return null;
	}

}
