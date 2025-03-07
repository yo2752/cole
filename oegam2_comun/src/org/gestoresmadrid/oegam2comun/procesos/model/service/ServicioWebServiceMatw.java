package org.gestoresmadrid.oegam2comun.procesos.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatwBean;

public interface ServicioWebServiceMatw extends Serializable {

	public static final String UTF_8 = "UTF-8";
	public static final String NOTIFICACION = "PROCESO MATRICULACIÓN FINALIZADO";

	public static final String CORRECTO = "CORRECTO";

	ResultadoMatwBean tramitarPeticion(ColaDto solicitud);

	void actualizarRespuestaMatriculacion(BigDecimal numExpediente, BigDecimal idUsuario, String respuesta);
}
