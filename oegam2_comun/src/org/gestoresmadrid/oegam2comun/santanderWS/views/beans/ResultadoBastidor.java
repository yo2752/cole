package org.gestoresmadrid.oegam2comun.santanderWS.views.beans;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.santanderWS.views.enums.ResultadoSantanderWSEnum;

public class ResultadoBastidor implements Serializable{

	private static final long serialVersionUID = -7269264878574107365L;
	
	private Boolean error;
	private ResultadoSantanderWSEnum resultadoError;
	private String mensaje;
	
	public ResultadoBastidor() {
		super();
		this.error = false;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public ResultadoSantanderWSEnum getResultadoError() {
		return resultadoError;
	}
	public void setResultadoError(ResultadoSantanderWSEnum resultadoError) {
		this.resultadoError = resultadoError;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}