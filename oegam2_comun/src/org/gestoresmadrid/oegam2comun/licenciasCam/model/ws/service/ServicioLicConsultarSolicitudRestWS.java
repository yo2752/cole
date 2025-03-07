package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service;

import java.io.Serializable;
import java.math.BigDecimal;

import escrituras.beans.ResultBean;

public interface ServicioLicConsultarSolicitudRestWS extends Serializable {

	ResultBean consultarSolicitudRest(BigDecimal numExpediente, Long idUsuario);
}
