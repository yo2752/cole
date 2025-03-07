package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.vo.TipoMotorVO;

public interface ServicioTipoMotor extends Serializable {
	TipoMotorVO getTipoMotor(String tipoMotor);
	List<TipoMotorVO> getListaTiposMotores();
}