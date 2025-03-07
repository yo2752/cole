package org.gestoresmadrid.oegam2comun.direccion.view.dto;

import java.io.Serializable;

public class ProvinciaCamDto implements Serializable {

	private static final long serialVersionUID = 6495200341958227139L;

	private String idProvincia;

	private String nombre;

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}