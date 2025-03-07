package org.gestoresmadrid.core.evolucionSemaforo.model.dao.impl;

import org.gestoresmadrid.core.evolucionSemaforo.model.dao.EvolucionSemaforoDao;
import org.gestoresmadrid.core.evolucionSemaforo.model.vo.EvolucionSemaforoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionSemaforoDaoImpl extends GenericDaoImplHibernate<EvolucionSemaforoVO> implements EvolucionSemaforoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8348649347789000567L;

	@Override
	public EvolucionSemaforoVO obtenerEvolucionSemaforoVO(EvolucionSemaforoVO semaforo) {
		// TODO Auto-generated method stub
		return null;
	}

}
