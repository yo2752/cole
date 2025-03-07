package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TipoCreacionDto implements Serializable {

	private static final long serialVersionUID = -3032881484535205001L;

	private String descripcionCreacion;

	private BigDecimal idTipoCreacion;

	public String getDescripcionCreacion() {
		return descripcionCreacion;
	}

	public void setDescripcionCreacion(String descripcionCreacion) {
		this.descripcionCreacion = descripcionCreacion;
	}

	public BigDecimal getIdTipoCreacion() {
		return idTipoCreacion;
	}

	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}
}