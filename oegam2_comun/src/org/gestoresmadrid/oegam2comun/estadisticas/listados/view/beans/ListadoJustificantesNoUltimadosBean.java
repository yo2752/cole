package org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;

import utilidades.estructuras.FechaFraccionada;

public class ListadoJustificantesNoUltimadosBean {

	@FilterSimpleExpression(name = "idJustificante", restriction = FilterSimpleExpressionRestriction.ISNOTNULL)
	private boolean idJustificante = Boolean.TRUE;

	@FilterSimpleExpression(name = "fechaInicio", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaInicio;

	@FilterSimpleExpression(name = "tramiteTrafico.numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "tramiteTrafico.estado", not = true, restriction = FilterSimpleExpressionRestriction.IN)
	private List<BigDecimal> listaEstados = Arrays.asList(new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente
			.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));

	private String password;

	public ListadoJustificantesNoUltimadosBean() {}

	public boolean isIdJustificante() {
		return idJustificante;
	}

	public void setIdJustificante(boolean idJustificante) {
		this.idJustificante = idJustificante;
	}

	public FechaFraccionada getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(FechaFraccionada fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public List<BigDecimal> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<BigDecimal> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
