package org.gestoresmadrid.oegam2comun.personas.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaPersonaBean {

	@FilterSimpleExpression(name = "id.nif")
	private String nif;

	@FilterSimpleExpression(name = "id.numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "nombre")
	private String nombre;

	@FilterSimpleExpression(name = "apellido1RazonSocial")
	private String apellido1RazonSocial;

	@FilterSimpleExpression(name = "apellido2")
	private String apellido2;

	@FilterSimpleExpression(name = "estado")
	private Long estado;

	@FilterSimpleExpression(name = "estadoCivil")
	private String estadoCivil;

	@FilterSimpleExpression(name = "tipoPersona")
	private String tipoPersona;

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

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
}
