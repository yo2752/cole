package org.gestoresmadrid.oegam2comun.pegatinas.view.beans;

import java.io.Serializable;

public class PegatinasNotificaBean implements Serializable{

	private static final long serialVersionUID = -6566994514611564799L;

	private String identificadorPegatina;
	
	private String motivo;
	
	private String tipo;

	public String getIdentificadorPegatina() {
		return identificadorPegatina;
	}

	public void setIdentificadorPegatina(String identificadorPegatina) {
		this.identificadorPegatina = identificadorPegatina;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
