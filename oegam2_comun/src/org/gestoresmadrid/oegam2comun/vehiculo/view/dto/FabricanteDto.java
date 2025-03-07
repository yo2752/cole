package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;

public class FabricanteDto implements Serializable  {

	private static final long serialVersionUID = -1810303191815538895L;

	private Long codFabricante;

	private String fabricante;

	public Long getCodFabricante() {
		return codFabricante;
	}

	public void setCodFabricante(Long codFabricante) {
		this.codFabricante = codFabricante;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
}
