package org.gestoresmadrid.oegam2comun.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TipoContratoDto implements Serializable {

	private static final long serialVersionUID = -7753734314019027366L;

	private BigDecimal idTipoContrato;

	private String tipoContrato;

	public BigDecimal getIdTipoContrato() {
		return idTipoContrato;
	}

	public void setIdTipoContrato(BigDecimal idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}
}