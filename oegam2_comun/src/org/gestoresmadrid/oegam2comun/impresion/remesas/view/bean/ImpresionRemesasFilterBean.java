package org.gestoresmadrid.oegam2comun.impresion.remesas.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

public class ImpresionRemesasFilterBean implements Serializable{
	
	private static final long serialVersionUID = -8731799314885386932L;
	
	@FilterSimpleExpression(name="numExpediente", restriction = FilterSimpleExpressionRestriction.IN)
	private BigDecimal[] numExpedientes;

	public BigDecimal[] getNumExpedientes() {
		return numExpedientes;
	}

	public void setNumExpedientes(BigDecimal[] numExpedientes) {
		this.numExpedientes = numExpedientes;
	}
	
}
