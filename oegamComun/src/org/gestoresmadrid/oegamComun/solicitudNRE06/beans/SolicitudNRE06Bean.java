package org.gestoresmadrid.oegamComun.solicitudNRE06.beans;

import java.io.Serializable;

public class SolicitudNRE06Bean implements Serializable{

	private static final long serialVersionUID = 3004558751258517713L;
	
	private String tramiteVisado;
	private String nifTitular;
	private String apellRazonSocialTitular;
	private String nombreTitular;
	private String nifColegiado;
	private String apellRazonSocialColegiado;
	private String nombreColegiado;
	private String fechaVisado;
	private String refColegio;
	private String numBastidor;
	
	public String getTramiteVisado() {
		return tramiteVisado;
	}
	public void setTramiteVisado(String tramiteVisado) {
		this.tramiteVisado = tramiteVisado;
	}
	public String getNifTitular() {
		return nifTitular;
	}
	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}
	public String getApellRazonSocialTitular() {
		return apellRazonSocialTitular;
	}
	public void setApellRazonSocialTitular(String apellRazonSocialTitular) {
		this.apellRazonSocialTitular = apellRazonSocialTitular;
	}
	public String getNombreTitular() {
		return nombreTitular;
	}
	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}
	public String getNifColegiado() {
		return nifColegiado;
	}
	public void setNifColegiado(String nifColegiado) {
		this.nifColegiado = nifColegiado;
	}
	public String getApellRazonSocialColegiado() {
		return apellRazonSocialColegiado;
	}
	public void setApellRazonSocialColegiado(String apellRazonSocialColegiado) {
		this.apellRazonSocialColegiado = apellRazonSocialColegiado;
	}
	public String getNombreColegiado() {
		return nombreColegiado;
	}
	public void setNombreColegiado(String nombreColegiado) {
		this.nombreColegiado = nombreColegiado;
	}
	public String getFechaVisado() {
		return fechaVisado;
	}
	public void setFechaVisado(String fechaVisado) {
		this.fechaVisado = fechaVisado;
	}
	public String getRefColegio() {
		return refColegio;
	}
	public void setRefColegio(String refColegio) {
		this.refColegio = refColegio;
	}
	public String getNumBastidor() {
		return numBastidor;
	}
	public void setNumBastidor(String numBastidor) {
		this.numBastidor = numBastidor;
	}
}
