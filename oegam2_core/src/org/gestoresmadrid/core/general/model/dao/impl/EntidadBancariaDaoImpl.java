package org.gestoresmadrid.core.general.model.dao.impl;

import org.gestoresmadrid.core.general.model.dao.EntidadBancariaDao;
import org.gestoresmadrid.core.general.model.vo.EntidadBancariaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EntidadBancariaDaoImpl extends GenericDaoImplHibernate<EntidadBancariaVO> implements EntidadBancariaDao {

	private static final long serialVersionUID = 3342296506293174935L;
}
