package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.MedioDao;
import org.gestoresmadrid.core.registradores.model.vo.MedioVO;
import org.springframework.stereotype.Repository;

@Repository
public class MedioDaoImpl extends GenericDaoImplHibernate<MedioVO> implements MedioDao {

	private static final long serialVersionUID = 5010503386092291735L;
}
