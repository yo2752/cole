package org.gestoresmadrid.oegamComun.impr.model.dto;

import java.io.Serializable;

public class DetalleDocImprDTO implements Serializable{

	private static final long serialVersionUID = 2113124780455894965L;
	
	String numExpediente;
	String matricula;
	String tipo;
	
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
