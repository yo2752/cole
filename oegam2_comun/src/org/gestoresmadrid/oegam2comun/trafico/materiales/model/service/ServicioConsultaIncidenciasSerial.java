package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaSerialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;

public interface ServicioConsultaIncidenciasSerial extends Serializable {
	IncidenciaSerialVO obtenerIncidenciaSerial(IncidenciaVO incidenciaVO, String serial);
	IncidenciaSerialVO obtenerIncidenciaSerial(IncidenciaVO incidenciaVO, Long incidenciaInv);
	IncidenciaSerialVO obtenerIncidenciaByIncidenciaConsejo(Long incidenciaInvId);
}
