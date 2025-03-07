package org.gestoresmadrid.oegamCreditos.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.oegamCreditos.view.bean.CreditosDisponiblesBean;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.dto.HistoricoCreditoDto;

import utilidades.estructuras.FechaFraccionada;

public interface ServicioResumenCargaCreditos extends Serializable {

	public String CANTIDAD_SUMADA_TOTAL = "cantidadSumadaTotal";
	public String CANTIDAD_RESTADA_TOTAL = "cantidadRestadaTotal";

	public ResultCreditosBean cantidadesTotalesResumen(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception;

	public int numeroElementos(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception;

	public String obtenerCreditosGBJA(FechaFraccionada fechaAlta);

	public ResultCreditosBean exportarTablaCompleta(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta);

	public ResultCreditosBean exportarTablaCompletaMes(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHast, BigDecimal precioCredito);

	public List<CreditosDisponiblesBean> creditosDisponiblesPorColegiado(Long idContrato);

	public List<HistoricoCreditoDto> consultaCreditosAcumuladosMesPorColegiado(Long idContrato);
}
