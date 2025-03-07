package org.gestoresmadrid.core.licencias.model.dao.impl;

import org.gestoresmadrid.core.licencias.model.dao.LcEdificacionDao;
import org.gestoresmadrid.core.licencias.model.vo.LcEdificacionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class LcEdificacionDaoImpl extends GenericDaoImplHibernate<LcEdificacionVO> implements LcEdificacionDao {

	private static final long serialVersionUID = -1838159713143522700L;
}
