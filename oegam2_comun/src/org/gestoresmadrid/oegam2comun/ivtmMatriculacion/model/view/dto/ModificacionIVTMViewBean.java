package org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ModificacionIVTMViewBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3807169326845543340L;

	@FilterSimpleExpression(name = "nrc")
	private String nrc;

	@FilterSimpleExpression(name = "fechaRequerida", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaRequerida;

	
	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;
	
	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;
	
	@FilterSimpleExpression(name = "bastidor")
	private String bastidor;

	

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}


	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public FechaFraccionada getFechaRequerida() {
		return fechaRequerida;
	}

	public void setFechaRequerida(FechaFraccionada fechaRequerida) {
		this.fechaRequerida = fechaRequerida;
	}


	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}


	
}
