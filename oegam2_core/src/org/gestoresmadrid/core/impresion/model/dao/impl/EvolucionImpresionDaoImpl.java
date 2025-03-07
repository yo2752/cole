package org.gestoresmadrid.core.impresion.model.dao.impl;

import org.gestoresmadrid.core.impresion.model.dao.EvolucionImpresionDao;
import org.gestoresmadrid.core.impresion.model.vo.EvolucionImpresionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionImpresionDaoImpl extends GenericDaoImplHibernate<EvolucionImpresionVO> implements EvolucionImpresionDao {

	private static final long serialVersionUID = 5181636769949582746L;

}
