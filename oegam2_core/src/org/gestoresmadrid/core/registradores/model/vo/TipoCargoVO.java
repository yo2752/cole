package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TIPO_CARGO database table.
 */
@Entity
@Table(name = "TIPO_CARGO")
public class TipoCargoVO implements Serializable {

	private static final long serialVersionUID = 6758462857943494665L;

	@Id
	@Column(name = "CODIGO_CARGO")
	private String codigoCargo;

	@Column(name = "DESC_CARGO")
	private String descCargo;

	@Column(name = "VALOR_XML")
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