package org.gestoresmadrid.oegam2comun.sega.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;

public interface ServicioWebServiceNotificacionCtitSega extends Serializable{

	public static final String UTF_8 = "UTF-8";
	public static final String TIMEOUT = "webservice.ctit.timeOut";	
	public static final String URL_WS  = "webService.url.sega.ctit";
	public static final String NOTIFICACION = "PROCESO NOTIFICACION CTIT FINALIZADO";
	
	ResultadoCtitBean tramitarNotificacionCtit(ColaBean solicitud);

	ResultadoCtitBean descontarCreditos(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato);

	ResultadoCtitBean actualizarTramiteProceso(BigDecimal numExpediente, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario, String respuesta);

}
