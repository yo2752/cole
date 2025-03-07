package org.gestoresmadrid.oegamImportacion.trafico.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.AutoliquidacionBean;

public interface ServicioTramiteTraficoTransmisionImportacion extends Serializable {

	TramiteTrafTranVO getTramiteTransmisionVO(BigDecimal numExpediente, boolean tramiteCompleto);

	ResultadoBean autoliquidacion(AutoliquidacionBean autoliquidacion, Long idContrato);
}
