package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.Date;

public class DatosPersonaAtex5Dto implements Serializable{

	private static final long serialVersionUID = -4393511393430993013L;
	
	private String apellido1;
	private String apellido2;
	private String dirElectronicaVial;
	private Date fechaNacimiento;
	private String nif;
	private String nombre;
	private String sexo;
	private String tipoPersona;
	private String cif;
	private String razonSocial;
	private DatosDomicilioAtex5Dto domicilio;
	private DatosDomicilioAtex5Dto domicilioIne;
	
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getDirElectronicaVial() {
		return dirElectronicaVial;
	}
	public void setDirElectronicaVial(String dirElectronicaVial) {
		this.dirElectronicaVial = dirElectronicaVial;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public DatosDomicilioAtex5Dto getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(DatosDomicilioAtex5Dto domicilio) {
		this.domicilio = domicilio;
	}
	public DatosDomicilioAtex5Dto getDomicilioIne() {
		return domicilioIne;
	}
	public void setDomicilioIne(DatosDomicilioAtex5Dto domicilioIne) {
		this.domicilioIne = domicilioIne;
	}
	
}
