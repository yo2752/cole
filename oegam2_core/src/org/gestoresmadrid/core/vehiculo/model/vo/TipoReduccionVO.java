package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TIPO_REDUCCION database table.
 */
@Entity
@Table(name = "TIPO_REDUCCION")
public class TipoReduccionVO implements Serializable {

	private static final long serialVersionUID = 254747539494038028L;

	@Id
	@Column(name = "TIPO_REDUCCION")
	private String tipoReduccion;

	@Column(name = "DESC_TIPO_REDUCCION")
	private String descTipoReduccion;

	@Column(name = "PORCENTAJE_REDUCCION")
	private BigDecimal porcentajeReduccion;

	public TipoReduccionVO() {}

	public String getTipoReduccion() {
		return this.tipoReduccion;
	}

	public void setTipoReduccion(String tipoReduccion) {
		this.tipoReduccion = tipoReduccion;
	}

	public String getDescTipoReduccion() {
		return this.descTipoReduccion;
	}

	public void setDescTipoReduccion(String descTipoReduccion) {
		this.descTipoReduccion = descTipoReduccion;
	}

	public BigDecimal getPorcentajeReduccion() {
		return porcentajeReduccion;
	}

	public void setPorcentajeReduccion(BigDecimal porcentajeReduccion) {
		this.porcentajeReduccion = porcentajeReduccion;
	}

}