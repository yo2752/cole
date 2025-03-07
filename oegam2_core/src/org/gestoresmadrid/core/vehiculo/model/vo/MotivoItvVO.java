package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the MOTIVO_ITV database table.
 */
@Entity
@Table(name = "MOTIVO_ITV")
public class MotivoItvVO implements Serializable {

	private static final long serialVersionUID = 1704593747045576232L;

	@Id
	@Column(name = "ID_MOTIVO_ITV")
	private String idMotivoItv;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdMotivoItv() {
		return idMotivoItv;
	}

	public void setIdMotivoItv(String idMotivoItv) {
		this.idMotivoItv = idMotivoItv;
	}
}