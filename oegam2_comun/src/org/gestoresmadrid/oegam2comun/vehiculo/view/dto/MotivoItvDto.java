package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;

public class MotivoItvDto implements Serializable {

	private static final long serialVersionUID = 2870187204853358194L;

	private String descripcion;

	private String idMotivoItv;

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