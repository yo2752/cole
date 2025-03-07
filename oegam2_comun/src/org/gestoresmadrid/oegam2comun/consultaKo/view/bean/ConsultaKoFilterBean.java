package org.gestoresmadrid.oegam2comun.consultaKo.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaKoFilterBean implements Serializable{

	private static final long serialVersionUID = 2327186601336625507L;

	@FilterSimpleExpression(name ="matricula")
	private String matricula;

	@FilterSimpleExpression(name ="estado")
	private String estado;

	@FilterSimpleExpression(name = "fecha", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecha;

	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationship(name="contrato", joinType = Criteria.LEFT_JOIN)
	private Long idContrato;

	@FilterSimpleExpression(name ="tipoTramite")
	private String tipoTramite;

	@FilterSimpleExpression(name ="tipo")
	private String tipo;

	@FilterSimpleExpression(name ="jefatura")
	private String jefatura;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

}