package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;

/**
 * Representa el pais registro
 */
public class PaisRegistroDto implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1997747046122071971L;

	private String codigo;

	private String nombre;

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
}
