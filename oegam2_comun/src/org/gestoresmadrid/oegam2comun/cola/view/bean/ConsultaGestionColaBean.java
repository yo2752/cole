package org.gestoresmadrid.oegam2comun.cola.view.bean;

import java.io.Serializable;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaGestionColaBean implements Serializable{

	private static final long serialVersionUID = -7873902382571254179L;
	
	private String numExpediente;
	private String numColegiado;
	private String tipoTramite;
	private String cola;
	private String estado;
	private String proceso;
	private FechaFraccionada fecha;
	private String matricula;
	private String bastidor;
	private GestionColaBean gestionCola;
	
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getCola() {
		return cola;
	}
	public void setCola(String cola) {
		this.cola = cola;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public FechaFraccionada getFecha() {
		return fecha;
	}
	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public GestionColaBean getGestionCola() {
		return gestionCola;
	}
	public void setGestionCola(GestionColaBean gestionCola) {
		this.gestionCola = gestionCola;
	}
	
}
