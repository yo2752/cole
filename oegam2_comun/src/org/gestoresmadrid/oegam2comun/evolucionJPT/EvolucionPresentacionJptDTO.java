package org.gestoresmadrid.oegam2comun.evolucionJPT;

import java.util.Date;

public class EvolucionPresentacionJptDTO {

	private String usuario;
	private String tramiteTrafico;
	private String jefatura;
	private Date fechaCambio;
	private String estadoAnterior;
	private String estadoNuevo;
	
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTramiteTrafico() {
		return tramiteTrafico;
	}
	public void setTramiteTrafico(String tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}
	public String getJefatura() {
		return jefatura;
	}
	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	public Date getFechaCambio() {
		return fechaCambio;
	}
	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
	public String getEstadoAnterior() {
		return estadoAnterior;
	}
	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}
	public String getEstadoNuevo() {
		return estadoNuevo;
	}
	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}
	
	
	
}
