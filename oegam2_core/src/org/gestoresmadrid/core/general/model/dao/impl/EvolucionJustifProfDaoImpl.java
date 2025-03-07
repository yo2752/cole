package org.gestoresmadrid.core.general.model.dao.impl;

import org.gestoresmadrid.core.general.model.dao.EvolucionJustifProfDao;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesPK;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesVO;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionJustifProfDaoImpl extends GenericDaoImplHibernate<EvolucionJustifProfesionalesVO> implements EvolucionJustifProfDao {

	private static final long serialVersionUID = 393477988051807083L;

	@Override
	public void guardarEvolucion(EvolucionJustifProfesionalesVO evolucionJustificanteProfVO) {
		EvolucionJustifProfesionalesPK evolucionJustificanteProfPK = (EvolucionJustifProfesionalesPK) guardar(evolucionJustificanteProfVO);
	}
}
