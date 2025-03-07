package org.gestoresmadrid.oegamPlacasMatricula.view.bean;

import java.util.ArrayList;

public class ValidacionPlacasBean {

	private Integer idSolicitud;
	private String numColegiado;
	private String fechaSolicitud;
	private String matricula;
	private boolean error;
	private ArrayList<String> mensajes;
	private int noElemento;
	
	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getNumColegiado() {
		return numColegiado;
	}
	
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public boolean isError() {
		return error;
	}
	
	public void setError(boolean error) {
		this.error = error;
	}
	
	public ArrayList<String> getMensajes() {
		return mensajes;
	}
	
	public void setMensajes(ArrayList<String> mensajes) {
		this.mensajes = mensajes;
	}

	public int getNoElemento() {
		return noElemento;
	}

	public void setNoElemento(int noElemento) {
		this.noElemento = noElemento;
	}

}