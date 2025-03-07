package org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ListadoMensajesErrorServicioBean {

	@FilterSimpleExpression(name = "fecha", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecha;

	public ListadoMensajesErrorServicioBean() {}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

}
