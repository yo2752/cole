package org.gestoresmadrid.oegam2comun.pegatinas.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class PegatinasStockHistoricoBean implements Serializable{

	private static final long serialVersionUID = -6566994514611564799L;

	@FilterSimpleExpression(name = "idHistorico")
	private Integer idHistorico;
	
	@FilterSimpleExpression(name = "idStock")
	private Integer idStock;
	
	@FilterSimpleExpression(name = "accion")
	private String accion;
	
	@FilterSimpleExpression(name = "stock")
	private Integer stock;
	
	@FilterSimpleExpression(name = "fecha", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecha;
	
	@FilterSimpleExpression(name = "tipo")
	private String tipo;
	
	@FilterSimpleExpression(name = "matricula")
	private String matricula;

	public Integer getIdHistorico() {
		return idHistorico;
	}

	public void setIdHistorico(Integer idHistorico) {
		this.idHistorico = idHistorico;
	}

	public Integer getIdStock() {
		return idStock;
	}

	public void setIdStock(Integer idStock) {
		this.idStock = idStock;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
}
