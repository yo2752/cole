package org.gestoresmadrid.oegam2comun.impresion.view.dto;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import escrituras.beans.ResultValidarTramitesImprimir;

public class ImprimirTramiteTraficoDto {
	
	@FilterSimpleExpression(name="numExpediente", restriction = FilterSimpleExpressionRestriction.IN)
	private BigDecimal[] numExpedientes;
	private ResultValidarTramitesImprimir resultBeanImprimir;
	private Boolean electronica;
	private String numCreditosDisponibles;
	private String numCreditosTotales;
	private String numCreditosBloqueados;
	
	/**
	 * @return the numExpedientes
	 */
	public BigDecimal[] getNumExpedientes() {
		return numExpedientes;
	}
	/**
	 * @param numExpedientes the numExpedientes to set
	 */
	public void setNumExpedientes(BigDecimal[] numExpedientes) {
		this.numExpedientes = numExpedientes;
	}
	/**
	 * @return the resultBeanImprimir
	 */
	public ResultValidarTramitesImprimir getResultBeanImprimir() {
		return resultBeanImprimir;
	}
	/**
	 * @param resultBeanImprimir the resultBeanImprimir to set
	 */
	public void setResultBeanImprimir(
			ResultValidarTramitesImprimir resultBeanImprimir) {
		this.resultBeanImprimir = resultBeanImprimir;
	}
	/**
	 * @return the electronica
	 */
	public Boolean getElectronica() {
		return electronica;
	}
	/**
	 * @param electronica the electronica to set
	 */
	public void setElectronica(Boolean electronica) {
		this.electronica = electronica;
	}
	/**
	 * @return the numCreditosDisponibles
	 */
	public String getNumCreditosDisponibles() {
		return numCreditosDisponibles;
	}
	/**
	 * @param numCreditosDisponibles the numCreditosDisponibles to set
	 */
	public void setNumCreditosDisponibles(String numCreditosDisponibles) {
		this.numCreditosDisponibles = numCreditosDisponibles;
	}
	/**
	 * @return the numCreditosTotales
	 */
	public String getNumCreditosTotales() {
		return numCreditosTotales;
	}
	/**
	 * @param numCreditosTotales the numCreditosTotales to set
	 */
	public void setNumCreditosTotales(String numCreditosTotales) {
		this.numCreditosTotales = numCreditosTotales;
	}
	/**
	 * @return the numCreditosBloqueados
	 */
	public String getNumCreditosBloqueados() {
		return numCreditosBloqueados;
	}
	/**
	 * @param numCreditosBloqueados the numCreditosBloqueados to set
	 */
	public void setNumCreditosBloqueados(String numCreditosBloqueados) {
		this.numCreditosBloqueados = numCreditosBloqueados;
	}
}
