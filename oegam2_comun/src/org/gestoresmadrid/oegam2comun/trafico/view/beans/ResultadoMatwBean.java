package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.io.Serializable;
import java.util.List;

import net.gestores.sega.matw.MatwError;

public class ResultadoMatwBean implements Serializable {

	private static final long serialVersionUID = -7117055513877221245L;

	private Boolean error;
	private Exception excepcion;
	private String mensajeError;
	private List<String> listaMensajes;
	private MatwError[] listaErroresMatwWS;
	private Boolean esRecuperable;

	public ResultadoMatwBean(Exception excepcion) {
		this.excepcion = excepcion;
		this.error = Boolean.TRUE;
		this.esRecuperable = Boolean.FALSE;
	}

	public ResultadoMatwBean(Boolean error) {
		this.error = error;
		this.esRecuperable = Boolean.FALSE;
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

	public MatwError[] getListaErroresMatwWS() {
		return listaErroresMatwWS;
	}

	public void setListaErroresMatwWS(MatwError[] listaErroresMatwWS) {
		this.listaErroresMatwWS = listaErroresMatwWS;
	}

	public Boolean getEsRecuperable() {
		return esRecuperable;
	}

	public void setEsRecuperable(Boolean esRecuperable) {
		this.esRecuperable = esRecuperable;
	}

}