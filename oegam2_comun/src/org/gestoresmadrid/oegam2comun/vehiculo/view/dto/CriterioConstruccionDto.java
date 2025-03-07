package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.view.dto.ClaveMatriculacionDto;

public class CriterioConstruccionDto implements Serializable {

	private static final long serialVersionUID = 4444524146717023409L;

	private String descripcion;

	private String idCriterioConstruccion;

	private ClaveMatriculacionDto claveMatriculacionDto;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdCriterioConstruccion() {
		return idCriterioConstruccion;
	}

	public void setIdCriterioConstruccion(String idCriterioConstruccion) {
		this.idCriterioConstruccion = idCriterioConstruccion;
	}

	public ClaveMatriculacionDto getClaveMatriculacionDto() {
		return claveMatriculacionDto;
	}

	public void setClaveMatriculacionDto(ClaveMatriculacionDto claveMatriculacionDto) {
		this.claveMatriculacionDto = claveMatriculacionDto;
	}
}