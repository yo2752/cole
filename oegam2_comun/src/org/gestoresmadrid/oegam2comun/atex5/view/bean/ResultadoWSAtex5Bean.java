package org.gestoresmadrid.oegam2comun.atex5.view.bean;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;

public class ResultadoWSAtex5Bean implements Serializable {

	private static final long serialVersionUID = -874717157000796758L;

	private Boolean error;

	private Exception excepcion;

	private String mensajeError;

	private String matricula;

	private String bastidor;

	private List<String> listaMensajes;

	private String respuestaWS;
	
	private EstadoAtex5 estado;

	public ResultadoWSAtex5Bean() {
		super();
		this.error = Boolean.FALSE;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public String getRespuestaWS() {
		return respuestaWS;
	}

	public void setRespuestaWS(String respuestaWS) {
		this.respuestaWS = respuestaWS;
	}

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

	public EstadoAtex5 getEstado() {
		return estado;
	}

	public void setEstado(EstadoAtex5 estado) {
		this.estado = estado;
	}
}
