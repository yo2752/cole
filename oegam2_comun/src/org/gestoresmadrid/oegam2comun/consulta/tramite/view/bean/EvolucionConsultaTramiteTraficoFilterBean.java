package org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionConsultaTramiteTraficoFilterBean implements Serializable {

	private static final long serialVersionUID = 6401101207504244899L;
	
	@FilterSimpleExpression(name = "id.numExpediente")
	private BigDecimal numExpediente;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	
}
