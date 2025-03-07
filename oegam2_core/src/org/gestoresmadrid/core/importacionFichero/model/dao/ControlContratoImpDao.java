package org.gestoresmadrid.core.importacionFichero.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.importacionFichero.model.vo.ControlContratoImpVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ControlContratoImpDao extends Serializable, GenericDao<ControlContratoImpVO> {

	ControlContratoImpVO getControlContratoImp(Long tipo, String tipo2);
}
