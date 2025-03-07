package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CertifCargoDto implements Serializable {

	private static final long serialVersionUID = -6876680437177090268L;

	private BigDecimal identificador;

	private String numColegiado;

	private String idFirma;

	private BigDecimal idTramiteRegistro;

	private String nifCargo;

	private String cifSociedad;

	private String codigoCargo;

	private SociedadCargoDto sociedadCargo;

	// Para el XML
	private String apellidos;

	private String tipoPersonalidad;

	private String tipoDocumento;

	private String descCargo;

	public BigDecimal getIdentificador() {
		return identificador;
	}

	public void setIdentificador(BigDecimal identificador) {
		this.identificador = identificador;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getIdFirma() {
		return idFirma;
	}

	public void setIdFirma(String idFirma) {
		this.idFirma = idFirma;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

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

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public SociedadCargoDto getSociedadCargo() {
		return sociedadCargo;
	}

	public void setSociedadCargo(SociedadCargoDto sociedadCargo) {
		this.sociedadCargo = sociedadCargo;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTipoPersonalidad() {
		return tipoPersonalidad;
	}

	public void setTipoPersonalidad(String tipoPersonalidad) {
		this.tipoPersonalidad = tipoPersonalidad;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDescCargo() {
		return descCargo;
	}

	public void setDescCargo(String descCargo) {
		this.descCargo = descCargo;
	}
}