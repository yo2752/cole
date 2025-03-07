package org.gestoresmadrid.oegam2comun.evolucionConsultaDev.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.evolucionConsultaDev.model.vo.EvolucionConsultaDevVO;
import org.gestoresmadrid.oegam2comun.evolucionConsultaDev.view.dto.EvolucionConsultaDevDto;

public interface ServicioEvolucionConsultaDev extends Serializable{

	List<EvolucionConsultaDevDto> convertirListaVOToDto(List<EvolucionConsultaDevVO> list);

	void guardarEvolucion(Long idConsultaDev, Long idUsuario, Date fecha, BigDecimal estadoAnt, BigDecimal estadoNuevo, String tipoActuacion);

	int getNumPeticionesWS(Long idConsultaDev);

}
