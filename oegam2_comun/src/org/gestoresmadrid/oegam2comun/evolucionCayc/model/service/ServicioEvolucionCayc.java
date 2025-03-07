package org.gestoresmadrid.oegam2comun.evolucionCayc.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.evolucionCayc.model.vo.EvolucionCaycVO;
import org.gestoresmadrid.oegam2comun.evolucionCayc.view.dto.EvolucionCaycDto;

public interface ServicioEvolucionCayc extends Serializable {

	void guardarEvolucion(BigDecimal numExpediente, Long idUsuario, Date fecha, BigDecimal estadoAnt, BigDecimal estadoNuevo, String tipoActuacion);
	
	List<EvolucionCaycDto> convertirListaVoToDto(List<EvolucionCaycVO> list);


}
