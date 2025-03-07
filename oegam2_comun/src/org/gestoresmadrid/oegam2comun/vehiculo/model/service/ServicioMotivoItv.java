package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.vo.MotivoItvVO;

public interface ServicioMotivoItv extends Serializable {

	MotivoItvVO getMotivoItv(String idMotivoItv);

	List<MotivoItvVO> getListaMotivoItv();
}
