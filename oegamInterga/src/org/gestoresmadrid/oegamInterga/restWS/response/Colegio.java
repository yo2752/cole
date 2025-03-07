package org.gestoresmadrid.oegamInterga.restWS.response;

import java.io.Serializable;

public class Colegio implements Serializable {

	private static final long serialVersionUID = 7481048139231301197L;

	private String doi;

	private String nombre;

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
