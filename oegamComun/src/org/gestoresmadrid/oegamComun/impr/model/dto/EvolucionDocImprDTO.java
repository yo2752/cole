package org.gestoresmadrid.oegamComun.impr.model.dto;

import java.io.Serializable;

public class EvolucionDocImprDTO implements Serializable{

	private static final long serialVersionUID = -4989264097717650856L;

	String docId;
    String operacion;
    String fechaCambio;
    String estadoAnterior;
    String estadoNuevo;
    String usuario;
    
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public String getFechaCambio() {
		return fechaCambio;
	}
	public void setFechaCambio(String fechaCambio) {
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
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
