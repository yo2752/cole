package org.gestoresmadrid.core.importacionFichero.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.importacionFichero.model.vo.ControlPeticionesImpVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ControlPeticionesImpDao extends Serializable, GenericDao<ControlPeticionesImpVO> {

	ControlPeticionesImpVO getControlPeticionesImp(String tipo);
}
