package org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto;

import java.math.BigDecimal;

public class EeffConsultaDTO extends EeffDTO {

	private BigDecimal numExpediente;

	private BigDecimal numExpedienteTramite;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getNumExpedienteTramite() {
		return numExpedienteTramite;
	}

	public void setNumExpedienteTramite(BigDecimal numExpedienteTramite) {
		this.numExpedienteTramite = numExpedienteTramite;
	}

}