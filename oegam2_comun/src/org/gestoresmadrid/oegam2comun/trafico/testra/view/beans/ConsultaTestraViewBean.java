package org.gestoresmadrid.oegam2comun.trafico.testra.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaTestraViewBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3807169326845543340L;

	@FilterSimpleExpression(name = "dato")
	private String dato;

	@FilterSimpleExpression(name = "tipo")
	private String tipo;

	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;

	@FilterSimpleExpression(name = "fechaUltimaSancion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaUltimaSancion;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression
	private Short activo;

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public FechaFraccionada getFechaUltimaSancion() {
		return fechaUltimaSancion;
	}

	public void setFechaUltimaSancion(FechaFraccionada fechaUltimaSancion) {
		this.fechaUltimaSancion = fechaUltimaSancion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Short getActivo() {
		return activo;
	}

	public void setActivo(Short activo) {
		this.activo = activo;
	}

	
}
