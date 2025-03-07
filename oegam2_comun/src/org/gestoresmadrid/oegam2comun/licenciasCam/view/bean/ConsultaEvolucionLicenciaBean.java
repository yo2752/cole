package org.gestoresmadrid.oegam2comun.licenciasCam.view.bean;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaEvolucionLicenciaBean {

	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}
