package org.gestoresmadrid.oegam2comun.creditos.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

public interface ServicioHistoricoCredito extends Serializable {

	public String CANTIDAD_SUMADA_TOTAL = "cantidadSumadaTotal";
	public String CANTIDAD_RESTADA_TOTAL = "cantidadRestadaTotal";

	void anotarGasto(String tipoCredito, Long idContrato, BigDecimal idUsuario, BigDecimal cantidadSumada, BigDecimal cantidadRestada);

}
