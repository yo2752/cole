package org.gestoresmadrid.oegamComun.am.model.json;

import java.io.Serializable;

public class RequestValidarMatw implements Serializable{

	private static final long serialVersionUID = -987778671306687660L;

	String tipoOperacion;
	
	Long idUsuario;
	
	String peticionDistintivo;
	
	String numExpediente;

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getPeticionDistintivo() {
		return peticionDistintivo;
	}

	public void setPeticionDistintivo(String peticionDistintivo) {
		this.peticionDistintivo = peticionDistintivo;
	}
	
}
