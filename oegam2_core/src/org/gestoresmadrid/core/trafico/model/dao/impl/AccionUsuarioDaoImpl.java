package org.gestoresmadrid.core.trafico.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.model.dao.AccionUsuarioDao;
import org.gestoresmadrid.core.trafico.model.vo.UsuarioAccionVO;
import org.springframework.stereotype.Repository;

@Repository
public class AccionUsuarioDaoImpl extends GenericDaoImplHibernate<UsuarioAccionVO> implements AccionUsuarioDao{
	
	private static final long serialVersionUID = -8528311622598122380L;

}
