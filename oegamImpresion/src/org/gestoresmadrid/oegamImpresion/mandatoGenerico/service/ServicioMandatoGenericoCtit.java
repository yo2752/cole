package org.gestoresmadrid.oegamImpresion.mandatoGenerico.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioMandatoGenericoCtit extends Serializable {

	public static final String TRANSMITENTE = "Transmitente";
	public static final String ADQUIRIENTE = "Adquiriente";
	public static final String COMPRAVENTA = "Compraventa";

	ResultadoImpresionBean imprimirMandatoGenerico(List<BigDecimal> listaExpediente, String tipoInterviniente);
}
