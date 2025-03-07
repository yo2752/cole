package org.gestoresmadrid.oegam2comun.registradores.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaEvolucionTramiteRegistroBean {

	@FilterSimpleExpression(name = "id.idTramiteRegistro")
	private BigDecimal idTramiteRegistro;

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}
}
