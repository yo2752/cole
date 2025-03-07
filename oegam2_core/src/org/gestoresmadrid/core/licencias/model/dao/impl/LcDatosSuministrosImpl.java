package org.gestoresmadrid.core.licencias.model.dao.impl;

import org.gestoresmadrid.core.licencias.model.dao.LcDatosSuministrosDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosSuministrosVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class LcDatosSuministrosImpl extends GenericDaoImplHibernate<LcDatosSuministrosVO> implements LcDatosSuministrosDao {

	private static final long serialVersionUID = -2964635199219368154L;
}
