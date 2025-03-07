package org.gestoresmadrid.oegamComun.vehiculo.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;

public class ServicioTraficoDto implements Serializable {

	private static final long serialVersionUID = 362646815244393167L;

	private String descripcion;

	private TipoTramiteDto tipoTramiteDto;

	private String idServicio;

	private String permitidos;

	private String noPermitidos;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoTramiteDto getTipoTramiteDto() {
		return tipoTramiteDto;
	}

	public void setTipoTramiteDto(TipoTramiteDto tipoTramiteDto) {
		this.tipoTramiteDto = tipoTramiteDto;
	}

	public String getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getPermitidos() {
		return permitidos;
	}

	public void setPermitidos(String permitidos) {
		this.permitidos = permitidos;
	}

	public String getNoPermitidos() {
		return noPermitidos;
	}

	public void setNoPermitidos(String noPermitidos) {
		this.noPermitidos = noPermitidos;
	}
}