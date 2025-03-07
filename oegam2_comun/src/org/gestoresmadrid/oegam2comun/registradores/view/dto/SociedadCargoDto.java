package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

import utilidades.estructuras.Fecha;

public class SociedadCargoDto implements Serializable {

	private static final long serialVersionUID = 4251362621500547329L;

	private String nifCargo;

	private String cifSociedad;

	private String numColegiado;

	private String codigoCargo;

	private Long anios;

	private Long meses;

	private Fecha fechaInicio;

	private Fecha fechaFin;

	private Fecha fechaValidezInicial;

	private Fecha fechaValidezFinal;

	private String indefinido;

	private String listaEjercicios;

	private PersonaDto personaCargo;

	private PersonaDto personaSociedad;

	public String getNifCargo() {
		return nifCargo;
	}

	public void setNifCargo(String nifCargo) {
		this.nifCargo = nifCargo;
	}

	public String getCifSociedad() {
		return cifSociedad;
	}

	public void setCifSociedad(String cifSociedad) {
		this.cifSociedad = cifSociedad;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public Long getAnios() {
		return anios;
	}

	public void setAnios(Long anios) {
		this.anios = anios;
	}

	public Long getMeses() {
		return meses;
	}

	public void setMeses(Long meses) {
		this.meses = meses;
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

	public Fecha getFechaValidezInicial() {
		return fechaValidezInicial;
	}

	public void setFechaValidezInicial(Fecha fechaValidezInicial) {
		this.fechaValidezInicial = fechaValidezInicial;
	}

	public Fecha getFechaValidezFinal() {
		return fechaValidezFinal;
	}

	public void setFechaValidezFinal(Fecha fechaValidezFinal) {
		this.fechaValidezFinal = fechaValidezFinal;
	}

	public String getIndefinido() {
		return indefinido;
	}

	public void setIndefinido(String indefinido) {
		this.indefinido = indefinido;
	}

	public String getListaEjercicios() {
		return listaEjercicios;
	}

	public void setListaEjercicios(String listaEjercicios) {
		this.listaEjercicios = listaEjercicios;
	}

	public PersonaDto getPersonaCargo() {
		return personaCargo;
	}

	public void setPersonaCargo(PersonaDto personaCargo) {
		this.personaCargo = personaCargo;
	}

	public PersonaDto getPersonaSociedad() {
		return personaSociedad;
	}

	public void setPersonaSociedad(PersonaDto personaSociedad) {
		this.personaSociedad = personaSociedad;
	}
}