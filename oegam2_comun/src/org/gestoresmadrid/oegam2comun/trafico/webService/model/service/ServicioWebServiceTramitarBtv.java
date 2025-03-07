package org.gestoresmadrid.oegam2comun.trafico.webService.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.btv.view.dto.ResultadoBTV;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;

public interface ServicioWebServiceTramitarBtv extends Serializable {

	public final String TIMEOUT_PROP_TRAMITAR_BTV = "webservice.consultaBtv.timeOut";
	public final String WEBSERVICE_TRAMITAR_BTV = "webservice.tramitarBtv.url";
	public final String UTF_8 = "UTF-8";
	public static final String TEXTO_WS_TRAMITAR_BTV = "TRAMITAR_BTV";

	ResultadoBTV procesarSolicitudBTV(BigDecimal numExpediente, String xmlEnviar, BigDecimal idUsuario, BigDecimal idContrato);

	void cambiarEstado(ColaDto cola, EstadoTramiteTrafico estadoTramite, String respuesta);

	void tratarDevolverCredito(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato);
}
