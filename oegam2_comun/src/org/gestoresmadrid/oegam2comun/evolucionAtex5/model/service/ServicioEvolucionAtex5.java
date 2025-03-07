package org.gestoresmadrid.oegam2comun.evolucionAtex5.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.evolucionAtex5.model.vo.EvolucionAtex5VO;
import org.gestoresmadrid.oegam2comun.evolucionAtex5.view.dto.EvolucionAtex5Dto;

import escrituras.beans.ResultBean;

public interface ServicioEvolucionAtex5 extends Serializable {

	void guardarEvolucion(BigDecimal numExpediente, Long idUsuario, Date fecha, BigDecimal estadoAnt, BigDecimal estadoNuevo, String tipoActuacion);

	List<EvolucionAtex5Dto> convertirListaVoToDto(List<EvolucionAtex5VO> lista);

	ResultBean eliminarEvolucionConsulta(BigDecimal numExpediente);
}
