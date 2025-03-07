package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TIPO_CREACION database table.
 */
@Entity
@Table(name = "TIPO_CREACION")
public class TipoCreacionVO implements Serializable {

	private static final long serialVersionUID = 4830182286729720318L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_TIPO_CREACION")
	private BigDecimal idTipoCreacion;

	@Column(name = "DESCRIPCION_CREACION")
	private String descripcionCreacion;

	public TipoCreacionVO() {}

	public BigDecimal getIdTipoCreacion() {
		return this.idTipoCreacion;
	}

	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}

	public String getDescripcionCreacion() {
		return this.descripcionCreacion;
	}

	public void setDescripcionCreacion(String descripcionCreacion) {
		this.descripcionCreacion = descripcionCreacion;
	}

}