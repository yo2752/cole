package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;

public class DuplicadoDstvBean implements Serializable{

	private static final long serialVersionUID = 3422257757075428375L;
	
	private String matricula;
	private String bastidor;
	private String nive;
	private String mensajeAviso;
	
	
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
	public String getNive() {
		return nive;
	}
	public void setNive(String nive) {
		this.nive = nive;
	}
	public String getMensajeAviso() {
		return mensajeAviso;
	}
	public void setMensajeAviso(String mensajeAviso) {
		this.mensajeAviso = mensajeAviso;
	}
	
	
}
