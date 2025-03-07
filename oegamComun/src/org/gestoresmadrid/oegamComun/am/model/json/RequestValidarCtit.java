package org.gestoresmadrid.oegamComun.am.model.json;

import java.io.Serializable;

public class RequestValidarCtit implements Serializable{

	private static final long serialVersionUID = 7329794234816424020L;

	String tipoOperacion;
	
	Long idUsuario;
	
	String numExpediente;
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}


}
