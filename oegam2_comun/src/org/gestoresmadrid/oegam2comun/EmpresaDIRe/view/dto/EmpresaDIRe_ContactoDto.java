package org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.dto;

import java.io.Serializable;

public class EmpresaDIRe_ContactoDto implements Serializable {

	private static final long serialVersionUID = 1830178671813328107L;

	// Datos b�sicos sacados de la documentaci�n

	private Number id;
	private String nombre;
	private String apellido1;

	private String apellido2;
	private String descripcion;
	public Number getId() {
		return id;
	}
	public void setId(Number id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}	