package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;

public class JefaturaTraficoDto implements Serializable {

	private static final long serialVersionUID = -4450755314390438527L;

	private String descripcion;

	private String jefatura;

	private String jefaturaProvincial;

	private String sucursal;

	private ProvinciaDto provinciaDto;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public ProvinciaDto getProvinciaDto() {
		return provinciaDto;
	}

	public void setProvinciaDto(ProvinciaDto provinciaDto) {
		this.provinciaDto = provinciaDto;
	}
}