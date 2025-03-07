package org.oegam.gestor.distintivos.integracion.bean;

import java.io.Serializable;

import org.oegam.gestor.distintivos.model.Emisor;

public class ResultadoCertBean implements Serializable {

	private static final long serialVersionUID = 8862208384774693458L;

	private Boolean error;
	private String mensaje;
	private Emisor jefatura;
	private String tipoRassb;

	public ResultadoCertBean(Boolean error) {
		super();
		this.error = error;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Emisor getJefatura() {
		return jefatura;
	}

	public void setJefatura(Emisor jefatura) {
		this.jefatura = jefatura;
	}

	public String getTipoRassb() {
		return tipoRassb;
	}

	public void setTipoRassb(String tipoRassb) {
		this.tipoRassb = tipoRassb;
	}

}