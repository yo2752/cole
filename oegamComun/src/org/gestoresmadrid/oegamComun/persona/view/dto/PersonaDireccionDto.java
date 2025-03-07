package org.gestoresmadrid.oegamComun.persona.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;

import utilidades.estructuras.Fecha;

public class PersonaDireccionDto implements Serializable {

	private static final long serialVersionUID = -7185622093856601554L;

	private String numColegiado;

	private String nif;

	private DireccionDto direccion;

	private Fecha fechaFin;

	private Fecha fechaInicio;

	private PersonaDto persona;

	private Short estado;

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public DireccionDto getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}

	public Fecha getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Fecha fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Fecha getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Fecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}

	public Short getEstado() {
		return estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}
}