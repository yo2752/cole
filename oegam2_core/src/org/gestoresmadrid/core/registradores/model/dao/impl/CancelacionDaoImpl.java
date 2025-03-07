package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.CancelacionDao;
import org.gestoresmadrid.core.registradores.model.vo.CancelacionVO;
import org.springframework.stereotype.Repository;

@Repository
public class CancelacionDaoImpl extends GenericDaoImplHibernate<CancelacionVO> implements CancelacionDao {

	private static final long serialVersionUID = 8154842426727388543L;

}
