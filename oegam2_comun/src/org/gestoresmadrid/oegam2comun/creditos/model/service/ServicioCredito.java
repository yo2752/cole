package org.gestoresmadrid.oegam2comun.creditos.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.TipoCreditoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.creditos.view.dto.CreditoDto;
import org.gestoresmadrid.oegam2comun.impresion.view.dto.ImprimirTramiteTraficoDto;

import trafico.utiles.enumerados.TipoTransferencia;
import trafico.utiles.enumerados.TipoTransferenciaActual;
import escrituras.beans.ResultBean;

/**
 * Servicio destinado a Creditos
 */
public interface ServicioCredito extends Serializable {

	public static String CREDITOS_DISPONIBLES = "creditosDisponibles";
	public static String CREDITOS_TOTALES = "creditosTotales";
	public static String CREDITOS_BLOQUEADOS = "creditosBlock";

	/**
	 * Valida si el colegiado tiene cr�ditos suficientes para realizar la operaci�n
	 * @param tipoTramite String, tipo de tr�mite
	 * @param idContrato String, identificador del contrato del colegiado
	 * @param cantidadCreditos BigDecimal, n�mero de cr�ditos a validar
	 * @return
	 */
	ResultBean validarCreditos(String tipoTramite, BigDecimal idContrato, BigDecimal cantidadCreditos);

	/**
	 * Descuenta al colegiado el n�mero de cr�ditos de la operaci�n
	 * @param tipoTramite String, tipo de tr�mite
	 * @param idContrato String, identificador del contrato del colegiado
	 * @param cantidadCreditos BigDecimal, n�mero de cr�ditos a validar
	 * @param idUsuario BigDecimal, identificador del usuario
	 * @return
	 */
	ResultBean descontarCreditos(String tipoTramite, BigDecimal idContrato, BigDecimal cantidadCreditos, BigDecimal idUsuario, ConceptoCreditoFacturado concepto, String... tramites);

	/**
	 * Descuenta al colegiado el n�mero de cr�ditos de la operaci�n
	 * @param tipoTramite String, tipo de tr�mite
	 * @param idContrato String, identificador del contrato del colegiado
	 * @param cantidadCreditos BigDecimal, n�mero de cr�ditos a validar
	 * @param idUsuario BigDecimal, identificador del usuario
	 * @return
	 */
	ResultBean devolverCreditos(String tipoTramite, BigDecimal idContrato, int cantidadCreditos, BigDecimal idUsuario, ConceptoCreditoFacturado concepto, String... tramites);

	/**
	 * Carga cr�ditos al colegiado el n�mero de cr�ditos de la operaci�n (se utiliza sobre todo para la carga de cr�ditos)
	 * @param tipoCredito String, tipo del cr�dito
	 * @param idContrato String, identificador del contrato del colegiado
	 * @param cantidadCreditos BigDecimal, n�mero de cr�ditos a validar
	 * @param idUsuario BigDecimal, identificador del usuario
	 * @return
	 */
	ResultBean cargoCreditos(String tipoCredito, BigDecimal idContrato, int cantidadCreditos, BigDecimal idUsuario);

	/**
	 * Devuelve el tipo de credito a facturar
	 * @param tipoTramite String, tipo de tr�mite
	 * @param tipoTransferenciaActual
	 * @param tipoTransferencia
	 * @return
	 */
	TipoTramiteTrafico getTipoCreditosACobrar(TipoTramiteTrafico tipoTramite, TipoTransferenciaActual tipoTransferenciaActual, TipoTransferencia tipoTransferencia);

	ResultBean descontarCreditosPorExpedientes(String[] numsExpedientes, ImprimirTramiteTraficoDto imprimirTramiteTrafico, BigDecimal idContrato, BigDecimal idUsuario);

	/**
	 * Devuelve el numero de creditos que se han anotado para ese tramite, descontando los devueltos
	 * @param idTramite identificador del tramite
	 * @param conceptoGasto (opcional), si es nulo todos los creditos se consideran gasto, si no solo los que tengan ese concepto.
	 * @param conceptoDevolucion (opcional), si se informa conceptoGasto y conceptoDevolucion, los creditos con este concepto se restan al total.
	 * @return
	 */
	int cantidadCreditosAnotados(String idTramite, ConceptoCreditoFacturado conceptoGasto, ConceptoCreditoFacturado conceptoDevolucion);

	String obtenerDescripcionTipoCredito(String tipoCredito);

	List<CreditoDto> busquedaCreditosPorContrato(Long idContrato);

	ResultBean descontarCreditosAM(String tipoTramite, BigDecimal idContrato, BigDecimal cantidadCreditos, BigDecimal idUsuario, ConceptoCreditoFacturado concepto, String... tramites);

	List<TipoCreditoTramiteVO> getListaTiposCreditosTramite(String tipoCredito);

}
