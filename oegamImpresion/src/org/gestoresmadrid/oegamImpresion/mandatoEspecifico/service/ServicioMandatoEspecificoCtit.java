package org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioMandatoEspecificoCtit extends Serializable {

	public static final String TRANSMITENTE = "Transmitente";
	public static final String ADQUIRIENTE = "Adquiriente";
	public static final String COMPRAVENTA = "Compraventa";

	ResultadoImpresionBean imprimirMandatoEspecifico(List<BigDecimal> listaExpediente, String tipoInterviniente);
}
