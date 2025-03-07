package org.gestoresmadrid.oegamImpresion.declaraciones.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioDeclaracionesCtit extends Serializable {

	public static final String TRANSMITENTE = "Transmitente";
	public static final String ADQUIRIENTE = "Adquiriente";
	public static final String COMPRAVENTA = "Compraventa";
	public static final String PRESENTADOR = "Presentador";

	ResultadoImpresionBean imprimirDeclaracion(List<BigDecimal> listaExpediente, String tipoInterviniente, String tipoImpreso);
}
