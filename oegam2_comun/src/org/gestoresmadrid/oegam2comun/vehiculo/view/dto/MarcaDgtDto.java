package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarcaDgtDto implements Serializable {

	private static final long serialVersionUID = -854264803224772101L;

	private BigDecimal codigoMarca;

	private String marca;

	private String marcaSinEditar;

	private BigDecimal version;

	public BigDecimal getCodigoMarca() {
		return codigoMarca;
	}

	public void setCodigoMarca(BigDecimal codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMarcaSinEditar() {
		return marcaSinEditar;
	}

	public void setMarcaSinEditar(String marcaSinEditar) {
		this.marcaSinEditar = marcaSinEditar;
	}

	public BigDecimal getVersion() {
		return version;
	}

	public void setVersion(BigDecimal version) {
		this.version = version;
	}
}