package org.gestoresmadrid.core.evolucionConsultaKo.model.dao.impl;

import org.gestoresmadrid.core.evolucionConsultaKo.model.dao.EvolucionConsultaKoDao;
import org.gestoresmadrid.core.evolucionConsultaKo.model.vo.EvolucionConsultaKoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionConsultaKoDaoImpl extends GenericDaoImplHibernate<EvolucionConsultaKoVO> implements EvolucionConsultaKoDao{

	private static final long serialVersionUID = -8104923304615303238L;

}
