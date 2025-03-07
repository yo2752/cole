package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;

public class LcPersonaDto implements Serializable {

	private static final long serialVersionUID = 6511885843653823650L;

	private Long idPersona;

	private String tipoSujeto;

	private String apellido1RazonSocial;

	private String apellido2;

	private String nombre;

	private String tipoDocumento;

	private String nif;

	private String numTelefono1;

	private String numTelefono2;

	private String numFax;

	private String numColegiado;

	private String correoElectronico;

	public LcPersonaDto() {}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getTipoSujeto() {
		return tipoSujeto;
	}

	public void setTipoSujeto(String tipoSujeto) {
		this.tipoSujeto = tipoSujeto;
	}

	public String getApellido1RazonSocial() {
		return apellido1RazonSocial;
	}

	public void setApellido1RazonSocial(String apellido1RazonSocial) {
		this.apellido1RazonSocial = apellido1RazonSocial;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumTelefono1() {
		return numTelefono1;
	}

	public void setNumTelefono1(String numTelefono1) {
		this.numTelefono1 = numTelefono1;
	}

	public String getNumTelefono2() {
		return numTelefono2;
	}

	public void setNumTelefono2(String numTelefono2) {
		this.numTelefono2 = numTelefono2;
	}

	public String getNumFax() {
		return numFax;
	}

	public void setNumFax(String numFax) {
		this.numFax = numFax;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

}