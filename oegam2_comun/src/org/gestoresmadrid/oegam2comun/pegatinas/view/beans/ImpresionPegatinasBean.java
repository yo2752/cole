package org.gestoresmadrid.oegam2comun.pegatinas.view.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ImpresionPegatinasBean implements Serializable{

	private static final long serialVersionUID = 7446388110301046930L;

	@FilterSimpleExpression(name = "idPegatina")
	private Integer idPegatina;
	
	@FilterSimpleExpression(name = "matricula")
	private String matricula;
	
	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;
	
	@FilterSimpleExpression(name = "estado")
	private Integer estado;
	
	@FilterSimpleExpression(name = "tipo")
	private String tipo;
	
	@FilterSimpleExpression(name = "descrEstado")
	private String descrEstado;
	
	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;
	
	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	@FilterSimpleExpression(name = "jefatura")
	private String jefatura;

	public Integer getIdPegatina() {
		return idPegatina;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public void setIdPegatina(Integer idPegatina) {
		this.idPegatina = idPegatina;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescrEstado() {
		return descrEstado;
	}

	public void setDescrEstado(String descrEstado) {
		this.descrEstado = descrEstado;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
}