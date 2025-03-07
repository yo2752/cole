package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service;

import java.io.Serializable;
import java.math.BigDecimal;

import escrituras.beans.ResultBean;

public interface ServicioLicRegSolicitudRestWS extends Serializable {

	public final String NOMBRE_HOST = "nombreHostProceso";

	ResultBean registrarSolicitud(BigDecimal numExpediente, Long idUsuario, Long idContrato, String aliasColegiado);

	ResultBean registrarSolicitudRest(BigDecimal numExpediente, String xml, BigDecimal idUsuario);

	void finalizarRegistro(BigDecimal numExpediente, String respuesta, BigDecimal estado, BigDecimal idUsuario);
}
