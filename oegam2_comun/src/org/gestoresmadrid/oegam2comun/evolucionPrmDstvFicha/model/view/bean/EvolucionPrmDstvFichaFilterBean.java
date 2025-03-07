package org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionPrmDstvFichaFilterBean implements Serializable{

	private static final long serialVersionUID = -8532530957122100990L;

	@FilterSimpleExpression(name="numExpediente")
	private BigDecimal numExpediente;
	
	@FilterSimpleExpression(name="tipoDocumento")
	private String tipoDocumento;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
}
