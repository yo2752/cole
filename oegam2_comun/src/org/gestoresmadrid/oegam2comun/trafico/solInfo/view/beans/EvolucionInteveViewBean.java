package org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionInteveViewBean implements Serializable{

	private static final long serialVersionUID = -5227904219184279645L;

	@FilterSimpleExpression(name="id.numExpediente")
	private BigDecimal numExpediente;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}