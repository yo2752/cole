package org.gestoresmadrid.oegamComun.credito.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioGestionCredito extends Serializable {

	ResultadoBean crearSolicitudCreditos(BigDecimal numExpediente, Long idContrato, String tipoTramite, String tipoTramiteCredito, String concepto, String accion, BigDecimal idUsuario);
}
