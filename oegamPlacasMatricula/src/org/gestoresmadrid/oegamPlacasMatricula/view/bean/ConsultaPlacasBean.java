package org.gestoresmadrid.oegamPlacasMatricula.view.bean;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaPlacasBean {
	private String numColegiado;
	private String numExpediente;
	private FechaFraccionada fecha;
	private String matricula;
	private String tipoMatricula;
	
	
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public FechaFraccionada getFecha() {
		return fecha;
	}
	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getTipoMatricula() {
		return tipoMatricula;
	}
	public void setTipoMatricula(String tipoMatricula) {
		this.tipoMatricula = tipoMatricula;
	}
	
}
