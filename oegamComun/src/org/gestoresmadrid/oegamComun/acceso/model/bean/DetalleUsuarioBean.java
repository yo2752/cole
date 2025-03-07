package org.gestoresmadrid.oegamComun.acceso.model.bean;

import java.io.Serializable;
import java.util.Date;

public class DetalleUsuarioBean implements Serializable{

	private static final long serialVersionUID = 1115805628446221517L;
	
	String nif;
	Long idUsuario;
	String numColegiado;
	String estadoUsuario;
	String apellidosNombre;
	String anagrama;
	String correoElectronico;
	Date ultimaConexion;
	Date fechaRenovacion;
	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getApellidosNombre() {
		return apellidosNombre;
	}
	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}
	public String getAnagrama() {
		return anagrama;
	}
	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public Date getUltimaConexion() {
		return ultimaConexion;
	}
	public void setUltimaConexion(Date ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}
	public Date getFechaRenovacion() {
		return fechaRenovacion;
	}
	public void setFechaRenovacion(Date fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getEstadoUsuario() {
		return estadoUsuario;
	}
	public void setEstadoUsuario(String estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}
	
}
