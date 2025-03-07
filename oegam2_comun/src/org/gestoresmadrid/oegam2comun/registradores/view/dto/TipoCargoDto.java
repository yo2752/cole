package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;

public class TipoCargoDto implements Serializable {

	private static final long serialVersionUID = -8524404464943033180L;

	private String codigoCargo;

	private String descCargo;

	private String valorXml;

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public String getDescCargo() {
		return descCargo;
	}

	public void setDescCargo(String descCargo) {
		this.descCargo = descCargo;
	}

	public String getValorXml() {
		return valorXml;
	}

	public void setValorXml(String valorXml) {
		this.valorXml = valorXml;
	}
}