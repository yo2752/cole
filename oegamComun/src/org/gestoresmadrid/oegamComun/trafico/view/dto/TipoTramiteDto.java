package org.gestoresmadrid.oegamComun.trafico.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.accesos.view.dto.AplicacionDto;

public class TipoTramiteDto implements Serializable {

	private static final long serialVersionUID = 8004289767498651840L;

	private String descripcion;

	private String tipoTramite;

	private Integer activo;

	private String codigoAplicacion;
	
	private AplicacionDto aplicacion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}

	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	public AplicacionDto getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionDto aplicacion) {
		this.aplicacion = aplicacion;
	}
}