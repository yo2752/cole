package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the OPERACION_REGISTRO database table.
 */
@Entity
@Table(name = "OPERACION_REGISTRO")
public class OperacionRegistroVO implements Serializable {

	private static final long serialVersionUID = -3029769498604720051L;

	@EmbeddedId
	private OperacionRegistroPK id;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	public OperacionRegistroPK getId() {
		return id;
	}

	public void setId(OperacionRegistroPK id) {
		this.id = id;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}