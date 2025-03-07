
package org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class EvolucionConsultaTramiteTraficoBean implements Serializable{

	private static final long serialVersionUID = -2475974691106589201L;
	
	String fecha;
	BigDecimal numExpediente;
	String estadoAnterior;
	String estadoNuevo;
	String usuario;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
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
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
