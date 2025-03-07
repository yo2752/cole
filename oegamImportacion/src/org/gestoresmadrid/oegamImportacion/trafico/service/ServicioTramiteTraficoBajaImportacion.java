package org.gestoresmadrid.oegamImportacion.trafico.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;

public interface ServicioTramiteTraficoBajaImportacion extends Serializable {

	TramiteTrafBajaVO getTramiteBajaVO(BigDecimal numExpediente, boolean tramiteCompleto);

}
