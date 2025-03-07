package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;

public class UsuarioInfoRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 89683069726016738L;
	private Long           id;
	private String         nombre;
	private String         email;
	private Long           rol;
	private ColegioRest    colegio;
	private DelegacionRest delegacion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getRol() {
		return rol;
	}
	public void setRol(Long rol) {
		this.rol = rol;
	}
	public ColegioRest getColegio() {
		return colegio;
	}
	public void setColegio(ColegioRest colegio) {
		this.colegio = colegio;
	}
	public DelegacionRest getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(DelegacionRest delegacion) {
		this.delegacion = delegacion;
	}
	
}
