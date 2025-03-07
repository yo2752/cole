package org.gestoresmadrid.oegam2comun.trafico.ivtm.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaIvtmViewBean implements Serializable{
	
	
	private static final long serialVersionUID = -3807169326845543340L;

	@FilterSimpleExpression(name = "matricula")
	private String matricula;

	@FilterSimpleExpression(name = "tipo")
	private String tipo;

	@FilterSimpleExpression(name = "fechaReq", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaBusqueda;	

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	
	public FechaFraccionada getFechaBusqueda() {
		return fechaBusqueda;
	}

	public void setFechaBusqueda(FechaFraccionada fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}

	

}
