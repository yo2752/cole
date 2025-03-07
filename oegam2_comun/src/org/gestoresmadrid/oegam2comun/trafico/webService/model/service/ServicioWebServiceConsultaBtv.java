package org.gestoresmadrid.oegam2comun.trafico.webService.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.consultaBTV.view.dto.ResultadoConsultaBTV;

public interface ServicioWebServiceConsultaBtv extends Serializable {

	public final String TIMEOUT_PROP_BTV = "webservice.consultaBtv.timeOut";
	public final String WEBSERVICE_CONSULTA_BTV = "webservice.consultaBtv.url";
	public final String UTF_8 = "UTF-8";
	public static final String TEXTO_WS_CONSULTA_BTV = "CONSULTA_BTV";

	ResultadoConsultaBTV procesarSolicitudConsultaBTV(BigDecimal idTramite, String xmlEnviar, BigDecimal idUsuario, BigDecimal idContrato);

	void cambiarEstadoConsultaBtv(ColaDto cola, EstadoTramiteTrafico estadoNuevo, String respuesta);
}
