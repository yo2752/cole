package org.gestoresmadrid.oegam2comun.datosSensibles.views.dto;

import java.util.List;

import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.BastidorErroneoBean;

public class ResultadoDatosSensibles {

	private boolean esBastidor;
	private boolean error;
	private boolean esBastidorActualizar;
	private String mensajeError;
	private List<String> listaMensajes;
	private List<BastidorErroneoBean> listaBastidoresErroneos;
	private List<String> listaMensajesAdvertencias;
	
	
	public ResultadoDatosSensibles() {
		super();
	}

	public ResultadoDatosSensibles(boolean esBastidor, boolean error,
			boolean esBastidorActualizar) {
		super();
		this.esBastidor = esBastidor;
		this.error = error;
		this.esBastidorActualizar = esBastidorActualizar;
	}
	
	public ResultadoDatosSensibles(boolean esBastidor, boolean error,
			String mensajeError) {
		super();
		this.esBastidor = esBastidor;
		this.error = error;
		this.mensajeError = mensajeError;
	}
	
	public ResultadoDatosSensibles(boolean esBastidor, boolean error,
			boolean esBastidorActualizar, String mensajeError) {
		super();
		this.esBastidor = esBastidor;
		this.error = error;
		this.esBastidorActualizar = esBastidorActualizar;
		this.mensajeError = mensajeError;
	}
	
	public ResultadoDatosSensibles(boolean esBastidor, boolean error) {
		super();
		this.esBastidor = esBastidor;
		this.error = error;
	}

	public boolean isEsBastidor() {
		return esBastidor;
	}
	
	public void setEsBastidor(boolean esBastidor) {
		this.esBastidor = esBastidor;
	}
	
	public boolean isError() {
		return error;
	}
	
	public void setError(boolean error) {
		this.error = error;
	}
	
	public boolean isEsBastidorActualizar() {
		return esBastidorActualizar;
	}
	
	public void setEsBastidorActualizar(boolean esBastidorActualizar) {
		this.esBastidorActualizar = esBastidorActualizar;
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

	public List<BastidorErroneoBean> getListaBastidoresErroneos() {
		return listaBastidoresErroneos;
	}

	public void setListaBastidoresErroneos(List<BastidorErroneoBean> listaBastidoresErroneos) {
		this.listaBastidoresErroneos = listaBastidoresErroneos;
	}

	public List<String> getListaMensajesAdvertencias() {
		return listaMensajesAdvertencias;
	}

	public void setListaMensajesAdvertencias(List<String> listaMensajesAdvertencias) {
		this.listaMensajesAdvertencias = listaMensajesAdvertencias;
	}

}
