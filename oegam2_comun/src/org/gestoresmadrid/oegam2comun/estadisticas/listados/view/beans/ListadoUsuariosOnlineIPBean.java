package org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

public class ListadoUsuariosOnlineIPBean {

	@FilterSimpleExpression(name = "fechaFin", restriction = FilterSimpleExpressionRestriction.ISNULL)
	private boolean fechaFin = Boolean.TRUE;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "frontal")
	private Integer frontal;

	private String password;

	public ListadoUsuariosOnlineIPBean() {}

	public boolean getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(boolean fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Integer getFrontal() {
		return frontal;
	}

	public void setFrontal(Integer frontal) {
		this.frontal = frontal;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
