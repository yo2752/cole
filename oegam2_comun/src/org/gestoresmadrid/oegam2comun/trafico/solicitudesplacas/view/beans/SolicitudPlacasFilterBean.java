package org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class SolicitudPlacasFilterBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7014093651237960670L;
	
	@FilterSimpleExpression(name = "tramiteTrafico.numExpediente")
	private BigDecimal numExpediente;
	
	@FilterSimpleExpression(name = "matricula")
	private String matricula;
	
	@FilterSimpleExpression(name = "refPropia", restriction = FilterSimpleExpressionRestriction.LIKE_PORCENTAJE)
	private String referenciaPropia;
	
	@FilterSimpleExpression(name = "fechaSolicitud", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaSolicitud;

	@FilterSimpleExpression(name = "idContrato")
	private Integer idContrato;
	
	@FilterSimpleExpression(name = "estado")
	private Integer estado;

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	/*public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}*/

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getReferenciaPropia() {
		return referenciaPropia;
	}

	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}

	public FechaFraccionada getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(FechaFraccionada fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
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

}
