package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the OFICINA_LIQUIDADORA_620 database table.
 */
@Embeddable
public class OficinaLiquidadora620PK implements Serializable {

	private static final long serialVersionUID = -3976517348001164528L;

	@Column(name = "OFICINA_LIQUIDADORA")
	private String oficinaLiquidadora;

	@Column(name = "ID_PROVINCIA")
	private String idProvincia;

	public OficinaLiquidadora620PK() {}

	public String getOficinaLiquidadora() {
		return oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(String oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
}