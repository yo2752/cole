package org.gestoresmadrid.oegamPermisoInternacional.view.bean;

import java.io.Serializable;

public class TramitePermisoInternDocBean implements Serializable {

	private static final long serialVersionUID = 8219589481177915317L;

	int numero;
	String numExpediente;
	String nifTitular;
	String fechaPresentacion;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNifTitular() {
		return nifTitular;
	}

	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
}