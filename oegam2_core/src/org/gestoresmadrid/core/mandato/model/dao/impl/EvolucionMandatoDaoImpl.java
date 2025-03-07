package org.gestoresmadrid.core.mandato.model.dao.impl;

import org.gestoresmadrid.core.mandato.model.dao.EvolucionMandatoDao;
import org.gestoresmadrid.core.mandato.model.vo.EvolucionMandatoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionMandatoDaoImpl extends GenericDaoImplHibernate<EvolucionMandatoVO> implements EvolucionMandatoDao {

	private static final long serialVersionUID = -8727266413667407260L;

}
