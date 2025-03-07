package org.gestoresmadrid.oegam2comun.consultasTGate.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.consultasTGate.model.vo.ConsultasTGateVO;

public interface ServicioConsultasTGate extends Serializable {

	void guardar(ConsultasTGateVO consultasTGateVO);
}
