package org.gestoresmadrid.oegam2comun.sega.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCheckBTVBean;

public interface ServicioWebServiceCheckBtvSega extends Serializable{

	public final String TIMEOUT_PROP_BTV = "webservice.consultaBtv.timeOut";
	public final String WEBSERVICE_CONSULTA_BTV = "webService.url.sega.checkBtv";
	public final String UTF_8 = "UTF-8";
	public static final String TEXTO_WS_CONSULTA_BTV = "CONSULTA_BTV";
	
	ResultadoCheckBTVBean procesarCheckBTV(BigDecimal idTramite, String xmlEnviar, BigDecimal idUsuario,BigDecimal idContrato);
	
	void cambiarEstadoConsultaBtv(ColaBean cola, EstadoTramiteTrafico estadoNuevo, String respuesta);

	
	
}
