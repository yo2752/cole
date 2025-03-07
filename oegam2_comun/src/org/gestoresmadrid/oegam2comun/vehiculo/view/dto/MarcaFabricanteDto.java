package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarcaFabricanteDto implements Serializable {

	private static final long serialVersionUID = -7382821020310859226L;

	private BigDecimal codigoMarca;

	private Long codFabricante;

	public BigDecimal getCodigoMarca() {
		return this.codigoMarca;
	}

	public void setCodigoMarca(BigDecimal codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public Long getCodFabricante() {
		return this.codFabricante;
	}

	public void setCodFabricante(Long codFabricante) {
		this.codFabricante = codFabricante;
	}
}