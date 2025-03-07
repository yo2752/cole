package org.gestoresmadrid.oegam2comun.impresion.modelos.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

public class ImpresionModelo600601FilterBean implements Serializable{

	private static final long serialVersionUID = -1010916259372012390L;
	
	@FilterSimpleExpression(name="numExpediente", restriction = FilterSimpleExpressionRestriction.IN)
	private BigDecimal[] numExpedientes;

	public BigDecimal[] getNumExpedientes() {
		return numExpedientes;
	}

	public void setNumExpedientes(BigDecimal[] numExpedientes) {
		this.numExpedientes = numExpedientes;
	}
	
}
