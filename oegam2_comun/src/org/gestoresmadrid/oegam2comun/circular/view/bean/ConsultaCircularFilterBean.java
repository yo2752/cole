package org.gestoresmadrid.oegam2comun.circular.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaCircularFilterBean implements Serializable {


	private static final long serialVersionUID = 3371313182554602717L;
	
	@FilterSimpleExpression(name = "numCircular")
	private String numCircular;

	@FilterSimpleExpression(name = "fechaInicio", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaInicio;

	@FilterSimpleExpression(name = "fechaFin", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaFin;

	@FilterSimpleExpression(name = "idUsuario")
	@FilterRelationship(name="usuarioVO", joinType = Criteria.LEFT_JOIN)
	private Long idUsuario;
	
	@FilterSimpleExpression(name = "estado")
	private String estado;
	
	@FilterSimpleExpression(name = "dias")
	private String numDias;

	public String getNumCircular() {
		return numCircular;
	}

	public void setNumCircular(String numCircular) {
		this.numCircular = numCircular;
	}

	public FechaFraccionada getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(FechaFraccionada fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public FechaFraccionada getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(FechaFraccionada fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNumDias() {
		return numDias;
	}

	public void setNumDias(String numDias) {
		this.numDias = numDias;
	}


	
}
