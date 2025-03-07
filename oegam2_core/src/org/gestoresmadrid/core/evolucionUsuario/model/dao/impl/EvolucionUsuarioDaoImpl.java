package org.gestoresmadrid.core.evolucionUsuario.model.dao.impl;


import org.gestoresmadrid.core.evolucionUsuario.model.dao.EvolucionUsuarioDao;
import org.gestoresmadrid.core.evolucionUsuario.model.vo.EvolucionUsuarioVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionUsuarioDaoImpl extends GenericDaoImplHibernate<EvolucionUsuarioVO> implements EvolucionUsuarioDao{

	private static final long serialVersionUID = 3009696945446600884L;

}
