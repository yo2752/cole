package org.gestoresmadrid.oegamImportacion.bean;

import java.io.Serializable;

public class SolicitudInteveBean implements Serializable{

	private static final long serialVersionUID = 3422257757075428375L;
	
	private String matricula;
	private String bastidor;
	private String nive;
	private String codigoTasa;
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
	public String getCodigoTasa() {
		return codigoTasa;
	}
	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}
	
	
}
