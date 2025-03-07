package org.gestoresmadrid.core.consultasTGate.model.dao.impl;

import org.gestoresmadrid.core.consultasTGate.model.dao.ConsultasTGateDao;
import org.gestoresmadrid.core.consultasTGate.model.vo.ConsultasTGateVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class ConsultasTGateDaoImpl extends GenericDaoImplHibernate<ConsultasTGateVO> implements ConsultasTGateDao {

	private static final long serialVersionUID = 8898241615632656250L;
}
