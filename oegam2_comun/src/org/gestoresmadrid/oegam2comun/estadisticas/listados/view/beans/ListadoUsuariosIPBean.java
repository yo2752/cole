package org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ListadoUsuariosIPBean {

	@FilterSimpleExpression(name = "fechaLogin", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaLogin;

	@FilterSimpleExpression(name = "fechaFin")
	private String fechaFin;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "frontal")
	private Integer frontal = null;

	private String password;

	public ListadoUsuariosIPBean() {}

	public FechaFraccionada getFechaLogin() {
		return fechaLogin;
	}

	public void setFechaLogin(FechaFraccionada fechaLogin) {
		this.fechaLogin = fechaLogin;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getFrontal() {
		return frontal;
	}

	public void setFrontal(Integer frontal) {
		this.frontal = frontal;
	}

}
