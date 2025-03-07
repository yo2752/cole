package org.gestoresmadrid.oegam2comun.evolucionAtex5.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionAtex5FilterBean implements Serializable{

	private static final long serialVersionUID = 4436916701815429761L;
	
	@FilterSimpleExpression(name="id.numExpediente")
	private BigDecimal numExpediente;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	
}
