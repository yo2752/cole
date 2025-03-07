package org.gestoresmadrid.oegam2comun.sega.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoBTVBean;

public interface ServicioWebServiceBtv extends Serializable{
	
	public final String TIMEOUT_PROP_TRAMITAR_BTV = "webservice.consultaBtv.timeOut";
	public final String WEBSERVICE_TRAMITAR_BTV = "webService.url.sega.btv";
	public final String UTF_8 = "UTF-8";
	public static final String TEXTO_WS_TRAMITAR_BTV = "TRAMITAR_BTV";

	ResultadoBTVBean procesarSolicitudBTV(BigDecimal numExpediente, String xmlEnviar, BigDecimal idUsuario, BigDecimal idContrato);

	void cambiarEstado(ColaBean cola, EstadoTramiteTrafico estadoTramite, String respuesta);

	void tratarDevolverCredito(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato);

}
