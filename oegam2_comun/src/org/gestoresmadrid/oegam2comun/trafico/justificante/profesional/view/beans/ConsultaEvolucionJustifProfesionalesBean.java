package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaEvolucionJustifProfesionalesBean {

	@FilterSimpleExpression(name = "id.numExpediente")
	private BigDecimal numExpediente;

	@FilterSimpleExpression(name = "id.idJustificanteInterno")
	private Long idJustificanteInterno;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Long getIdJustificanteInterno() {
		return idJustificanteInterno;
	}

	public void setIdJustificanteInterno(Long idJustificanteInterno) {
		this.idJustificanteInterno = idJustificanteInterno;
	}
}
