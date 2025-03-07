package org.gestoresmadrid.oegam2comun.sega.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatwBean;

public interface ServicioWebServiceMatwSega extends Serializable{

	public static final String UTF_8   = "UTF-8";
	public static final String URL_WS  = "webService.url.sega.matw";
	public static final String TIMEOUT = "webservice.matw.timeout";
	public static final String TEXTO_MATW_LOG = "LOG_MATW_SEGA:";
	public static final String NOTIFICACION = "PROCESO MATRICULACIÓN FINALIZADO";
	
	ResultadoMatwBean tramitarPeticion(ColaBean solicitud);

	ResultadoMatwBean cambiarEstadoTramite(BigDecimal idTramite, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario);

	void actualizarRespuestaMatriculacion(BigDecimal numExpediente, String respuesta);

	ResultadoMatwBean descontarCreditos(BigDecimal numExpediente, BigDecimal idContrato, BigDecimal idUsuario);

}
