package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.FicheroInfo;

import escrituras.beans.ResultBean;

public interface ServicioLicEnviarDocuRestWS extends Serializable {

	public final String NOMBRE_HOST = "nombreHostProceso";

	ResultBean enviarDocumentacion(BigDecimal numExpediente, Long idUsuario, Long idContrato, String aliasColegiado, ArrayList<FicheroInfo> documentacion);

	ResultBean enviarDocumentacionRest(BigDecimal numExpediente, String xml);

	void finalizarEnvioDocumentacion(BigDecimal numExpediente, String respuesta, BigDecimal idUsuario);
}
