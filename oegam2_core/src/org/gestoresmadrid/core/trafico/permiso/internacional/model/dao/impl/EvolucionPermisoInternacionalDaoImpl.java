package org.gestoresmadrid.core.trafico.permiso.internacional.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.dao.EvolucionPermisoInternacionalDao;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.EvolucionPermisoInterVO;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionPermisoInternacionalDaoImpl extends GenericDaoImplHibernate<EvolucionPermisoInterVO> implements EvolucionPermisoInternacionalDao {

	private static final long serialVersionUID = -4445158880532881526L;
}
