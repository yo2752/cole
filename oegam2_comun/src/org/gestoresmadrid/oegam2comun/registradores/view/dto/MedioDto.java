package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;

public class MedioDto implements Serializable {

	private static final long serialVersionUID = -7931176101501112151L;

	private Long idMedio;

	private String descMedio;

	private String tipoMedio;

	public Long getIdMedio() {
		return idMedio;
	}

	public void setIdMedio(Long idMedio) {
		this.idMedio = idMedio;
	}

	public String getDescMedio() {
		return descMedio;
	}

	public void setDescMedio(String descMedio) {
		this.descMedio = descMedio;
	}

	public String getTipoMedio() {
		return tipoMedio;
	}

	public void setTipoMedio(String tipoMedio) {
		this.tipoMedio = tipoMedio;
	}
}