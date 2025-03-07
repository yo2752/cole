package org.gestoresmadrid.core.evolucionContrato.model.dao.impl;

import org.gestoresmadrid.core.evolucionContrato.model.dao.EvolucionContratoDao;
import org.gestoresmadrid.core.evolucionContrato.model.vo.EvolucionContratoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionContratoDaoImpl extends GenericDaoImplHibernate<EvolucionContratoVO> implements EvolucionContratoDao{

	private static final long serialVersionUID = 3009696945446600884L;

}
