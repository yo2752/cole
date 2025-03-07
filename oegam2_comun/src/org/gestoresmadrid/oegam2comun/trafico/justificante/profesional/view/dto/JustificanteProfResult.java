package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JustificanteProfResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9042832830485593639L;

	private boolean error = false;
	private Exception exception;
	private String mensaje;
	private List<String> listaMensajes;
	private String numeroJustificante;
	private byte[] justificante;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getMensaje() {
		return mensaje;
	}

	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public void addMensaje(String mensaje) {
		if (listaMensajes == null) {
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNumeroJustificante() {
		return numeroJustificante;
	}

	public void setNumeroJustificante(String numeroJustificante) {
		this.numeroJustificante = numeroJustificante;
	}

	public byte[] getJustificante() {
		return justificante;
	}

	public void setJustificante(byte[] justificante) {
		this.justificante = justificante;
	}

}
