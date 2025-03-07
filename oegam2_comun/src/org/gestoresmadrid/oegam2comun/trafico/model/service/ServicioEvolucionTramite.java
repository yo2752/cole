package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.EvolucionTramiteTraficoDto;

public interface ServicioEvolucionTramite extends Serializable {

	void guardar(EvolucionTramiteTraficoDto evolucion);

	void guardar(EvolucionTramiteTraficoVO evolucion);

	int getNumeroFinalizacionesConError(BigDecimal numExpediente);

	List<EvolucionTramiteTraficoVO> getTramiteFinalizadoErrorAutorizado(BigDecimal numExpediente);
}
