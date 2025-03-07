package org.gestoresmadrid.oegam2comun.pegatinas.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class PegatinasStockBean implements Serializable{

	private static final long serialVersionUID = -6566994514611564799L;

	@FilterSimpleExpression(name = "idStock")
	private Integer idStock;
	
	@FilterSimpleExpression(name = "tipo")
	private String tipo;
	
	@FilterSimpleExpression(name = "stock")
	private Integer stock;
	
	@FilterSimpleExpression(name = "jefatura")
	private String jefatura;

	public Integer getIdStock() {
		return idStock;
	}

	public void setIdStock(Integer idStock) {
		this.idStock = idStock;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

}
