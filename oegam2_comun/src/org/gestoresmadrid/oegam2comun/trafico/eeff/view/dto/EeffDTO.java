package org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto;

import java.io.Serializable;

import utilidades.estructuras.Fecha;

public class EeffDTO implements Serializable {

	private static final long serialVersionUID = 1057155671700495379L;

	private Integer numColegiado;

	private boolean realizado;

	private Fecha fechaRealizacion;

	private String tarjetaBastidor;

	private String tarjetaNive;

	private String firCif;

	private String firMarca;

	private String respuesta;

	private String nifRepresentado;

	private String nombreRepresentado;

	public Integer getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(Integer numColegiado) {
		this.numColegiado = numColegiado;
	}

	public boolean isRealizado() {
		return realizado;
	}

	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}

	public Fecha getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(Fecha fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public String getTarjetaBastidor() {
		return tarjetaBastidor;
	}

	public void setTarjetaBastidor(String tarjetaBastidor) {
		this.tarjetaBastidor = tarjetaBastidor;
	}

	public String getTarjetaNive() {
		return tarjetaNive;
	}

	public void setTarjetaNive(String tarjetaNive) {
		this.tarjetaNive = tarjetaNive;
	}

	public String getFirCif() {
		return firCif;
	}

	public void setFirCif(String firCif) {
		this.firCif = firCif;
	}

	public String getFirMarca() {
		return firMarca;
	}

	public void setFirMarca(String firMarca) {
		this.firMarca = firMarca;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getNifRepresentado() {
		return nifRepresentado;
	}

	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}

	public String getNombreRepresentado() {
		return nombreRepresentado;
	}

	public void setNombreRepresentado(String nombreRepresentado) {
		this.nombreRepresentado = nombreRepresentado;
	}

}