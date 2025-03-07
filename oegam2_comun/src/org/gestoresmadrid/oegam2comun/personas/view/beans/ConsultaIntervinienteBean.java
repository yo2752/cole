package org.gestoresmadrid.oegam2comun.personas.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaIntervinienteBean {

	@FilterSimpleExpression(name = "id.nif")
	private String nif;

	@FilterSimpleExpression(name = "id.numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "id.numExpediente")
	private BigDecimal numExpediente;

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
}
