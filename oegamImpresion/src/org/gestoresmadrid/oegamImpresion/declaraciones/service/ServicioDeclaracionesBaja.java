package org.gestoresmadrid.oegamImpresion.declaraciones.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioDeclaracionesBaja extends Serializable {

	public static final String TRANSMITENTE = "Transmitente";
	public static final String TITULAR = "Titular";
	public static final String ADQUIRIENTE = "Adquiriente";

	ResultadoImpresionBean imprimirDeclaracion(List<BigDecimal> listaExpediente, String tipoInterviniente, String tipoImpreso);
}
