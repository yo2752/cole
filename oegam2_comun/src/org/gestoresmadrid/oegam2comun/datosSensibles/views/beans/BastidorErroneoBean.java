package org.gestoresmadrid.oegam2comun.datosSensibles.views.beans;

public class BastidorErroneoBean {

	private String bastidor;
	private String grupo;
	private String mensaje;
	private boolean esRecuperable;
	
	public BastidorErroneoBean(String bastidor, String grupo, String mensaje, boolean esRecuperable) {
		super();
		this.bastidor = bastidor;
		this.mensaje = mensaje;
		this.esRecuperable = esRecuperable;
		this.grupo = grupo;
	}
	
	public BastidorErroneoBean() {
	}

	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isEsRecuperable() {
		return esRecuperable;
	}

	public void setEsRecuperable(boolean esRecuperable) {
		this.esRecuperable = esRecuperable;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
}
