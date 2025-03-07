package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the MARCA_FABRICANTE database table.
 */
@Embeddable
public class MarcaFabricantePK implements Serializable {

	private static final long serialVersionUID = -4979063147917776941L;

	@Column(name = "CODIGO_MARCA", insertable = false, updatable = false)
	private Long codigoMarca;

	@Column(name = "COD_FABRICANTE", insertable = false, updatable = false)
	private Long codFabricante;

	public MarcaFabricantePK() {}

	public Long getCodigoMarca() {
		return this.codigoMarca;
	}

	public void setCodigoMarca(Long codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public Long getCodFabricante() {
		return this.codFabricante;
	}

	public void setCodFabricante(Long codFabricante) {
		this.codFabricante = codFabricante;
	}
}