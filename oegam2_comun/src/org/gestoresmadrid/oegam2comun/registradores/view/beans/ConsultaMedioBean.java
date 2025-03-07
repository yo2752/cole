package org.gestoresmadrid.oegam2comun.registradores.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

public class ConsultaMedioBean {

	@FilterSimpleExpression(name = "tipoMedio")
	private String tipoMedio;

	@FilterSimpleExpression(name = "descMedio", restriction = FilterSimpleExpressionRestriction.ILIKE_PORCENTAJE)
	private String descMedio;

	public String getTipoMedio() {
		return tipoMedio;
	}

	public void setTipoMedio(String tipoMedio) {
		this.tipoMedio = tipoMedio;
	}

	public String getDescMedio() {
		return descMedio;
	}

	public void setDescMedio(String descMedio) {
		this.descMedio = descMedio;
	}
}
