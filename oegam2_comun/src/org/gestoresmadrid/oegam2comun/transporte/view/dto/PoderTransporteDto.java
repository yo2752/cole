package org.gestoresmadrid.oegam2comun.transporte.view.dto;

import java.io.Serializable;
import java.util.Date;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

public class PoderTransporteDto implements Serializable{

	private static final long serialVersionUID = 6384361413634600265L;
	
	private Long idPoderTransporte;
	
	private String nifPoderdante;
	
	private String apellido1Poderdante;
	
	private String apellido2Poderdante;
	
	private String nombrePoderdante;
	
	private String nifEmpresa;
	
	private String nombreEmpresa;
	
	private String idProvincia;
	
	private String idMunicipio;
	
	private String nombreMunicipio;
	
	private String nombreProvincia;
	
	private String pueblo;
	
	private String idTipoVia;
	
	private String descTipoVia;
	
	private String nombreVia; 
	
	private String codPostal;
	
	private String numeroVia;
	
	private ContratoDto contrato;
	
	private Long idUsuarioAlta;
	
	private Date fechaAlta;

	public Long getIdPoderTransporte() {
		return idPoderTransporte;
	}

	public void setIdPoderTransporte(Long idPoderTransporte) {
		this.idPoderTransporte = idPoderTransporte;
	}

	public String getNifPoderdante() {
		return nifPoderdante;
	}

	public void setNifPoderdante(String nifPoderdante) {
		this.nifPoderdante = nifPoderdante;
	}

	public String getApellido1Poderdante() {
		return apellido1Poderdante;
	}

	public void setApellido1Poderdante(String apellido1Poderdante) {
		this.apellido1Poderdante = apellido1Poderdante;
	}

	public String getApellido2Poderdante() {
		return apellido2Poderdante;
	}

	public void setApellido2Poderdante(String apellido2Poderdante) {
		this.apellido2Poderdante = apellido2Poderdante;
	}

	public String getNombrePoderdante() {
		return nombrePoderdante;
	}

	public void setNombrePoderdante(String nombrePoderdante) {
		this.nombrePoderdante = nombrePoderdante;
	}

	public String getNifEmpresa() {
		return nifEmpresa;
	}

	public void setNifEmpresa(String nifEmpresa) {
		this.nifEmpresa = nifEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getPueblo() {
		return pueblo;
	}

	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}

	public String getDescTipoVia() {
		return descTipoVia;
	}

	public void setDescTipoVia(String descTipoVia) {
		this.descTipoVia = descTipoVia;
	}

	public String getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getNumeroVia() {
		return numeroVia;
	}

	public void setNumeroVia(String numeroVia) {
		this.numeroVia = numeroVia;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public Long getIdUsuarioAlta() {
		return idUsuarioAlta;
	}

	public void setIdUsuarioAlta(Long idUsuarioAlta) {
		this.idUsuarioAlta = idUsuarioAlta;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	
}
