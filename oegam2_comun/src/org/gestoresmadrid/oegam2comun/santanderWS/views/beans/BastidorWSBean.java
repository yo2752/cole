package org.gestoresmadrid.oegam2comun.santanderWS.views.beans;

import java.io.Serializable;

public class BastidorWSBean implements Serializable{

	private static final long serialVersionUID = 3780258121073494960L;
	
	private String numeroBastidor;
	private String tipoVehiculo;
	private String fechaEnvio;
	
	public String getNumeroBastidor() {
		return numeroBastidor;
	}
	public void setNumeroBastidor(String numeroBastidor) {
		this.numeroBastidor = numeroBastidor;
	}
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	public String getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	
}
