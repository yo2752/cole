package org.gestoresmadrid.oegamImportacion.trafico.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;

public interface ServicioTramiteTraficoDuplicadoImportacion extends Serializable {

	public static String NUMEXPEDIENTE = "NUMEXPEDIENTE";

	TramiteTrafDuplicadoVO getTramiteDuplicado(BigDecimal numExpediente, boolean tramiteCompleto);

}
