package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service;

import java.io.Serializable;
import java.math.BigDecimal;

import escrituras.beans.ResultBean;

public interface ServicioLicEnviarSolicitudRestWS extends Serializable {

	public final String NOMBRE_HOST = "nombreHostProceso";

	ResultBean enviarSolicitud(BigDecimal numExpediente, Long idUsuario, Long idContrato, String aliasColegiado);

	ResultBean validarSolicitud(BigDecimal numExpediente, Long idUsuario, Long idContrato, String aliasColegiado);

	ResultBean enviarSolicitudRest(BigDecimal numExpediente, String xml, BigDecimal idUsuario);

	ResultBean validarSolicitudRest(BigDecimal numExpediente, String nombreXml, BigDecimal idUsuario);

	void finalizarValidacion(BigDecimal numExpediente, String respuesta, BigDecimal estado, BigDecimal idUsuario);

	void finalizarEnvio(BigDecimal numExpediente, String respuesta, BigDecimal estado, BigDecimal idUsuario);
}
