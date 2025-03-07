package org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.dao.EvoDuplicadoPermisoConducirDao;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.EvolucionDuplicadoPermCondVO;
import org.springframework.stereotype.Repository;

@Repository
public class EvoDuplicadoPermisoConducirImpl extends GenericDaoImplHibernate<EvolucionDuplicadoPermCondVO> implements EvoDuplicadoPermisoConducirDao {

	private static final long serialVersionUID = 4133350468676984307L;
}
