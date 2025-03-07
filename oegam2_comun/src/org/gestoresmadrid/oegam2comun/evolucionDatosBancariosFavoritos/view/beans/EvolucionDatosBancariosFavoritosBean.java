package org.gestoresmadrid.oegam2comun.evolucionDatosBancariosFavoritos.view.beans;

import java.io.Serializable;
import java.util.Date;

public class EvolucionDatosBancariosFavoritosBean implements Serializable{

	private static final long serialVersionUID = -3577449326443057677L;

	private Date fechaCambio;
	private String apellidosNombre;
	private String tipoActuacion;
	private String camposModificacion;
	private String estadoAnt;
	private String estadoNuevo;
	private String nifAnt;
	private String nifNuevo;
	private String formaPagoAnt;
	private String formaPagoNueva;
	
	public Date getFechaCambio() {
		return fechaCambio;
	}
	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
	public String getApellidosNombre() {
		return apellidosNombre;
	}
	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}
	public String getTipoActuacion() {
		return tipoActuacion;
	}
	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}
	public String getCamposModificacion() {
		return camposModificacion;
	}
	public void setCamposModificacion(String camposModificacion) {
		this.camposModificacion = camposModificacion;
	}
	public String getEstadoAnt() {
		return estadoAnt;
	}
	public void setEstadoAnt(String estadoAnt) {
		this.estadoAnt = estadoAnt;
	}
	public String getEstadoNuevo() {
		return estadoNuevo;
	}
	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}
	public String getNifAnt() {
		return nifAnt;
	}
	public void setNifAnt(String nifAnt) {
		this.nifAnt = nifAnt;
	}
	public String getNifNuevo() {
		return nifNuevo;
	}
	public void setNifNuevo(String nifNuevo) {
		this.nifNuevo = nifNuevo;
	}
	public String getFormaPagoAnt() {
		return formaPagoAnt;
	}
	public void setFormaPagoAnt(String formaPagoAnt) {
		this.formaPagoAnt = formaPagoAnt;
	}
	public String getFormaPagoNueva() {
		return formaPagoNueva;
	}
	public void setFormaPagoNueva(String formaPagoNueva) {
		this.formaPagoNueva = formaPagoNueva;
	}
	
}
