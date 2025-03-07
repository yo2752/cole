package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AcuerdoDto implements Serializable {

	private static final long serialVersionUID = -292254150621080491L;

	private Long idAcuerdo;

	private String aceptacion;

	private String cifSociedad;

	private String circunstancia;

	private String codigoCargo;

	private String facultades;

	private String nifCargo;

	private String numColegiado;

	private String tipoAcuerdo;

	private Long idReunion;

	private BigDecimal idTramiteRegistro;

	private SociedadCargoDto sociedadCargo;

	// Para el XML
	private String apellidos;

	private String tipoPersonalidad;

	private String tipoDocumento;

	private String descCargo;

	public Long getIdAcuerdo() {
		return idAcuerdo;
	}

	public void setIdAcuerdo(Long idAcuerdo) {
		this.idAcuerdo = idAcuerdo;
	}

	public String getAceptacion() {
		return aceptacion;
	}

	public void setAceptacion(String aceptacion) {
		this.aceptacion = aceptacion;
	}

	public String getCifSociedad() {
		return cifSociedad;
	}

	public void setCifSociedad(String cifSociedad) {
		this.cifSociedad = cifSociedad;
	}

	public String getCircunstancia() {
		return circunstancia;
	}

	public void setCircunstancia(String circunstancia) {
		this.circunstancia = circunstancia;
	}

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public String getFacultades() {
		return facultades;
	}

	public void setFacultades(String facultades) {
		this.facultades = facultades;
	}

	public String getNifCargo() {
		return nifCargo;
	}

	public void setNifCargo(String nifCargo) {
		this.nifCargo = nifCargo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getTipoAcuerdo() {
		return tipoAcuerdo;
	}

	public void setTipoAcuerdo(String tipoAcuerdo) {
		this.tipoAcuerdo = tipoAcuerdo;
	}

	public Long getIdReunion() {
		return idReunion;
	}

	public void setIdReunion(Long idReunion) {
		this.idReunion = idReunion;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
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