package org.gestoresmadrid.oegam2comun.registradores.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaFinanciadoresBean {

	@FilterSimpleExpression(name = "persona.nombre")
	private String nombre;

	@FilterSimpleExpression(name = "persona.apellido1RazonSocial")
	private String apellido1RazonSocial;

	@FilterSimpleExpression(name = "persona.apellido2")
	private String apellido2;

	@FilterSimpleExpression(name = "nif")
	private String nif;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;
	
	@FilterSimpleExpression(name = "tipoInterviniente")
	private String tipoInterviniente;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1RazonSocial() {
		return apellido1RazonSocial;
	}

	public void setApellido1RazonSocial(String apellido1RazonSocial) {
		this.apellido1RazonSocial = apellido1RazonSocial;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

}
