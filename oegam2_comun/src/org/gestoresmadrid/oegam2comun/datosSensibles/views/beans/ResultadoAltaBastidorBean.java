package org.gestoresmadrid.oegam2comun.datosSensibles.views.beans;

public class ResultadoAltaBastidorBean {

	private String bastidor;
	private String operacion;
	private Boolean resultadoConError;
	private String comentario;
	private Boolean bastidorExistente;
	
	public ResultadoAltaBastidorBean() {
		super();
		this.resultadoConError = Boolean.FALSE;
		this.bastidorExistente = Boolean.FALSE;
	}

	public String getErrorFormateado(){
		return resultadoConError ? "ERROR" : "OK";
	}
	
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public Boolean isResultadoConError() {
		return resultadoConError;
	}
	public void setResultadoConError(Boolean resultadoConError) {
		this.resultadoConError = resultadoConError;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Boolean getResultadoConError() {
		return resultadoConError;
	}

	public Boolean getBastidorExistente() {
		return bastidorExistente;
	}

	public void setBastidorExistente(Boolean bastidorExistente) {
		this.bastidorExistente = bastidorExistente;
	}
	
	
}
