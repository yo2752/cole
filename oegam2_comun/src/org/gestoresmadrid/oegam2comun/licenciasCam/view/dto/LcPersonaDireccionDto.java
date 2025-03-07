package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.util.Date;

public class LcPersonaDireccionDto implements Serializable {

	private static final long serialVersionUID = -531708559227840224L;

	private Long idPersonaDireccion;

	private String numColegiado;

	private Long idDireccion;

	private LcDireccionDto lcDireccion;

	private String nif;

	private Date fechaInicio;

	private Date fechaFin;

	public Long getIdPersonaDireccion() {
		return idPersonaDireccion;
	}

	public void setIdPersonaDireccion(Long idPersonaDireccion) {
		this.idPersonaDireccion = idPersonaDireccion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public LcDireccionDto getLcDireccion() {
		return lcDireccion;
	}

	public void setLcDireccion(LcDireccionDto lcDireccion) {
		this.lcDireccion = lcDireccion;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
}