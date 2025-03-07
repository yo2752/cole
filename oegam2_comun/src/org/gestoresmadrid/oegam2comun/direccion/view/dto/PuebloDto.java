package org.gestoresmadrid.oegam2comun.direccion.view.dto;

import java.io.Serializable;

public class PuebloDto implements Serializable {

	private static final long serialVersionUID = 7031133103978433741L;

	private String idProvincia;

	private String idMunicipio;

	private String pueblo;

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getPueblo() {
		return pueblo;
	}

	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}
}