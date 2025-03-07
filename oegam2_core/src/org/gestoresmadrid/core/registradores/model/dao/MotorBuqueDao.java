package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.MotorBuqueVO;




public interface MotorBuqueDao extends GenericDao<MotorBuqueVO>, Serializable {

	public MotorBuqueVO getMotorBuque(String id);
	public List<MotorBuqueVO> getMotoresPorBuque(long id);

}
