package org.gestoresmadrid.oegam2comun.mandato.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import utilidades.estructuras.Fecha;

public class MandatoDto implements Serializable {

	private static final long serialVersionUID = 7407856087304569994L;

	private Long idMandato;

	private String codigoMandato;

	private Fecha fechaMandato;

	private String empresa;

	private String cif;

	private String nombreAdministrador;

	private String dniAdministrador;

	private Fecha fechaCadDniAdmin;

	private Fecha fechaAlta;

	private Fecha fechaCaducidad;

	private String nombreAdministrador2;

	private String dniAdministrador2;

	private Fecha fechaCadDniAdmin2;

	private Long idContrato;

	private ContratoDto contratoDto;

	private String numColegiado;

	private Boolean visible;

	public Long getIdMandato() {
		return idMandato;
	}

	public void setIdMandato(Long idMandato) {
		this.idMandato = idMandato;
	}

	public String getCodigoMandato() {
		return codigoMandato;
	}

	public void setCodigoMandato(String codigoMandato) {
		this.codigoMandato = codigoMandato;
	}

	public Fecha getFechaMandato() {
		return fechaMandato;
	}

	public void setFechaMandato(Fecha fechaMandato) {
		this.fechaMandato = fechaMandato;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNombreAdministrador() {
		return nombreAdministrador;
	}

	public void setNombreAdministrador(String nombreAdministrador) {
		this.nombreAdministrador = nombreAdministrador;
	}

	public String getDniAdministrador() {
		return dniAdministrador;
	}

	public void setDniAdministrador(String dniAdministrador) {
		this.dniAdministrador = dniAdministrador;
	}

	public Fecha getFechaCadDniAdmin() {
		return fechaCadDniAdmin;
	}

	public void setFechaCadDniAdmin(Fecha fechaCadDniAdmin) {
		this.fechaCadDniAdmin = fechaCadDniAdmin;
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Fecha getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Fecha fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getNombreAdministrador2() {
		return nombreAdministrador2;
	}

	public void setNombreAdministrador2(String nombreAdministrador2) {
		this.nombreAdministrador2 = nombreAdministrador2;
	}

	public String getDniAdministrador2() {
		return dniAdministrador2;
	}

	public void setDniAdministrador2(String dniAdministrador2) {
		this.dniAdministrador2 = dniAdministrador2;
	}

	public Fecha getFechaCadDniAdmin2() {
		return fechaCadDniAdmin2;
	}

	public void setFechaCadDniAdmin2(Fecha fechaCadDniAdmin2) {
		this.fechaCadDniAdmin2 = fechaCadDniAdmin2;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public ContratoDto getContratoDto() {
		return contratoDto;
	}

	public void setContratoDto(ContratoDto contratoDto) {
		this.contratoDto = contratoDto;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
}
