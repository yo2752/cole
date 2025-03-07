package org.gestoresmadrid.oegamCreditos.service;

import java.io.Serializable;
import java.util.Date;

import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;

public interface ServicioHistoricoCreditos extends Serializable {

	public String CANTIDAD_SUMADA_TOTAL = "cantidadSumadaTotal";
	public String CANTIDAD_RESTADA_TOTAL = "cantidadRestadaTotal";

	public ResultCreditosBean cantidadesTotalesHistorico(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception;

	public String obtenerDescripcionTipoCredito(String tipoCredito);
}
