package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;

public class GrupoBienDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4400651894604691608L;

	private String codigo;

	private String nombre;

	private String tipo;

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
