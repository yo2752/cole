package org.gestoresmadrid.oegam2comun.circular.view.dto;

import java.io.Serializable;

import utilidades.estructuras.Fecha;

public class CircularDto implements Serializable{

	private static final long serialVersionUID = 4412728872742129201L;

	private Long idCircular;
	
	private String numCircular;
	
	private String estado;
	
	private Fecha fechaInicio;
	
	private Fecha fechaFin;
	
	private String texto;
	
	private Boolean repeticiones;
	
	private Boolean fecha;
	
	private String dias;
	
	public Long getIdCircular() {
		return idCircular;
	}

	public void setIdCircular(Long idCircular) {
		this.idCircular = idCircular;
	}

	public String getNumCircular() {
		return numCircular;
	}

	public void setNumCircular(String numCircular) {
		this.numCircular = numCircular;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Fecha getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Fecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Fecha getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Fecha fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(Boolean repeticiones) {
		this.repeticiones = repeticiones;
	}

	public Boolean getFecha() {
		return fecha;
	}

	public void setFecha(Boolean fecha) {
		this.fecha = fecha;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}
	
}
