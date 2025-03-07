package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;

public class ResultadoCheckBTVBean implements Serializable{

	private static final long serialVersionUID = -247777245072559994L;

	private boolean error;
	private Exception excepcion;
	private String mensajeError;
	private List<String> listaMensajes;
	private EstadoTramiteTrafico estadoNuevo;
	private String resultadoConsuta;
	private String respuesta;

	public ResultadoCheckBTVBean(boolean error, String mensajeError) {
		super();
		this.error = error;
		this.mensajeError = mensajeError;
	}

	public ResultadoCheckBTVBean(boolean error, List<String> listaMensajes) {
		super();
		this.error = error;
		this.listaMensajes = listaMensajes;
	}

	public ResultadoCheckBTVBean(boolean error, Exception excepcion, String mensajeError, List<String> listaMensajes) {
		super();
		this.mensajeError = mensajeError;
		this.listaMensajes = listaMensajes;
		this.error = error;
		this.excepcion = excepcion;
	}

	public ResultadoCheckBTVBean(boolean error, Exception excepcion) {
		super();
		this.error = error;
		this.excepcion = excepcion;
	}

	public ResultadoCheckBTVBean(boolean error) {
		super();
		this.error = error;
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

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public EstadoTramiteTrafico getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(EstadoTramiteTrafico estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public String getResultadoConsuta() {
		return resultadoConsuta;
	}

	public void setResultadoConsuta(String resultadoConsuta) {
		this.resultadoConsuta = resultadoConsuta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}