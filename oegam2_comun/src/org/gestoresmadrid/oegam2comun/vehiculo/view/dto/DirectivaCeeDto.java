package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;

public class DirectivaCeeDto implements Serializable {

	private static final long serialVersionUID = -3144402207435340820L;

	private String descripcion;

	private String idDirectivaCEE;

	private String criterioConstruccion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdDirectivaCEE() {
		return idDirectivaCEE;
	}

	public void setIdDirectivaCEE(String idDirectivaCEE) {
		this.idDirectivaCEE = idDirectivaCEE;
	}

	public String getCriterioConstruccion() {
		return criterioConstruccion;
	}

	public void setCriterioConstruccion(String criterioConstruccion) {
		this.criterioConstruccion = criterioConstruccion;
	}
}