package org.gestoresmadrid.oegamComun.evolucionContrato.view.bean;

import java.io.Serializable;
import java.util.Date;

public class EvolucionContratoBean implements Serializable{

	private static final long serialVersionUID = 8701617315803021002L;

	private String razonSocial;
	private String tipoAct;
	private Date fechaCambio;
	private String estadoAnt;
	private String estadoNuevo;
	private String apellidosNombre;
	private String motivo;
	private String solicitante;
	
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getTipoAct() {
		return tipoAct;
	}
	public void setTipoAct(String tipoAct) {
		this.tipoAct = tipoAct;
	}
	public Date getFechaCambio() {
		return fechaCambio;
	}
	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
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
	public String getApellidosNombre() {
		return apellidosNombre;
	}
	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	
}
