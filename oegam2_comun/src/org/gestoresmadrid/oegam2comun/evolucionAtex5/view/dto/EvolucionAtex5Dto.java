package org.gestoresmadrid.oegam2comun.evolucionAtex5.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

public class EvolucionAtex5Dto implements Serializable{
	
	private static final long serialVersionUID = 4809405987619815932L;

	private BigDecimal numExpediente;
	
	private Date fechaCambio;

	private Long idUsuario;
	
	private String tipoActuacion;
	
	private String estadoAnt;
	
	private String estadoNuevo;
	
	private UsuarioDto usuario;
	
	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getEstadoAnt() {
		return estadoAnt;
	}

	public void setEstadoAnt(String estadoAnt) {
		this.estadoAnt = estadoAnt;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}
