package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;

import utilidades.estructuras.Fecha;

public class YbpdfDto implements Serializable {

	private static final long serialVersionUID = -8574273234706642302L;

	private String idYbpdf;

	private Fecha fechaEnvio;

	private String numColegiado;

	private String carpeta;

	private Integer enviarayb;

	public String getIdYbpdf() {
		return idYbpdf;
	}

	public void setIdYbpdf(String idYbpdf) {
		this.idYbpdf = idYbpdf;
	}

	public Fecha getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Fecha fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	public Integer getEnviarayb() {
		return enviarayb;
	}

	public void setEnviarayb(Integer enviarayb) {
		this.enviarayb = enviarayb;
	}
}