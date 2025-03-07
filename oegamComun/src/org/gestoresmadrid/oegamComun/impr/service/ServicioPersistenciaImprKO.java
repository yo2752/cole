package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.impr.model.vo.ImprKoVO;
import org.gestoresmadrid.core.impr.model.vo.ImprVO;

public interface ServicioPersistenciaImprKO extends Serializable{

	void crearKo(ImprVO imprVO, Long idUsuario);

	ImprKoVO getImprKo(BigDecimal numExpediente, String tipoImpr);

	void crearKoTramite(BigDecimal numExpediente, String tipoImpr, Long idUsuario);

}
