package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;

import escrituras.beans.ResultBean;

public interface ServicioFacturacionTasa extends Serializable {

	TramiteTrafFacturacionDto getFacturacionTasa(BigDecimal numExpediente, String codigoTasa);

	ResultBean guardar(TramiteTrafFacturacionDto tramiteTrafFacturacionDto, BigDecimal idUsuario);
}
