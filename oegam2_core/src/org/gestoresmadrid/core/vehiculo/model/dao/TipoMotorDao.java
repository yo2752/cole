package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoMotorVO;

public interface TipoMotorDao extends GenericDao<TipoMotorVO>, Serializable {
	TipoMotorVO getTipoMotor(String tipoMotor);
	List<TipoMotorVO> getListaTipoMotores();
}