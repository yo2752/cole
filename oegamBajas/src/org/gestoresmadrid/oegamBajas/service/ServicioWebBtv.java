package org.gestoresmadrid.oegamBajas.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;

public interface ServicioWebBtv extends Serializable {

	public final String TIMEOUT_PROP_TRAMITAR_BTV = "webservice.consultaBtv.timeOut";
	public final String WEBSERVICE_TRAMITAR_BTV = "webService.url.sega.btv";
	public final String UTF_8 = "UTF-8";
	public static final String TEXTO_WS_TRAMITAR_BTV = "TRAMITAR_BTV";

	ResultadoBajasBean procesarSolicitudBtv(ColaDto solicitud);

	void finalizarSolicitudBtv(BigDecimal numExpediente, BigDecimal nuevoEstado, String respuesta, BigDecimal idUsuario);

}