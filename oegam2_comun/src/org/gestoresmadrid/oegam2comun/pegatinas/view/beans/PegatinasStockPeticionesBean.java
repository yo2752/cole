package org.gestoresmadrid.oegam2comun.pegatinas.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class PegatinasStockPeticionesBean implements Serializable{

	private static final long serialVersionUID = -6566994514611564799L;

	@FilterSimpleExpression(name = "idPeticion")
	private Integer idPeticion;
	
	@FilterSimpleExpression(name = "idStock")
	private Integer idStock;
	
	@FilterSimpleExpression(name = "estado")
	private Integer estado;
	
	@FilterSimpleExpression(name = "descrEstado")
	private String descrEstado;
	
	@FilterSimpleExpression(name = "tipo")
	private String tipo;
	
	@FilterSimpleExpression(name = "numPegatinas")
	private Integer numPegatinas;
	
	@FilterSimpleExpression(name = "fecha", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecha;

	public Integer getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}

	public Integer getIdStock() {
		return idStock;
	}

	public void setIdStock(Integer idStock) {
		this.idStock = idStock;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getDescrEstado() {
		return descrEstado;
	}

	public void setDescrEstado(String descrEstado) {
		this.descrEstado = descrEstado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getNumPegatinas() {
		return numPegatinas;
	}

	public void setNumPegatinas(Integer numPegatinas) {
		this.numPegatinas = numPegatinas;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

}
