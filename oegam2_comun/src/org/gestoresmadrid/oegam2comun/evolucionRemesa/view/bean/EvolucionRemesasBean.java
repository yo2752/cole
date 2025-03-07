package org.gestoresmadrid.oegam2comun.evolucionRemesa.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EvolucionRemesasBean implements Serializable{

	private static final long serialVersionUID = -3955974289685456794L;
	
	private BigDecimal numExpediente;
	private Date fechaCambio;
	private String estadoAnterior;
	private String estadoNuevo;
	private String apellidosNombre;
	
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
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
	public String getApellidosNombre() {
		return apellidosNombre;
	}
	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}
	
	
}
