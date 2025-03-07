package org.gestoresmadrid.oegam2comun.evolucionCayc.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionCaycFilterBean implements Serializable {

	private static final long serialVersionUID = 4907760409171964352L;
	
	@FilterSimpleExpression(name="numExpediente")
	private BigDecimal numExpediente;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}
