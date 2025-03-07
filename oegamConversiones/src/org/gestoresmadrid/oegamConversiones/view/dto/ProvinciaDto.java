package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;

public class ProvinciaDto implements Serializable {

	private static final long serialVersionUID = 4299078723774858443L;

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