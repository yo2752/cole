package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaEvolucionTramiteTraficoBean {

	@FilterSimpleExpression(name = "id.numExpediente")
	private BigDecimal numExpediente;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
}
