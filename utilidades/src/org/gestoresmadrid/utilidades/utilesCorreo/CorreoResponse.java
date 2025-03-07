package org.gestoresmadrid.utilidades.utilesCorreo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CorreoResponse implements Serializable {

	private static final long serialVersionUID = -6999037764856225101L;

	private Boolean error;
	private List<String> listaMensajes;

	public CorreoResponse() {
		init();
	}

	public CorreoResponse(Boolean error) {
		this();
		setError(error);
	}

	/**
	 * El mensaje se anade a la lista, no al atributo mensaje
	 * 
	 * @param error
	 * @param mensaje
	 */
	public CorreoResponse(Boolean error, List<String> lista) {
		this();
		setError(error);
		setListaMensajes(lista);
	}

	public void init() {
		setError(Boolean.FALSE);
		setListaMensajes(new ArrayList<String>());
	}

	public void addMensajeALista(String mensaje) {
		if (listaMensajes == null) {
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}

	/**
	 * Anadimos un mensaje a la lista
	 * 
	 * @param listaMensajes
	 */
	public void addListaMensajes(List<String> listaMensajes) {
		for (String msg : listaMensajes) {
			this.listaMensajes.add(msg);
		}
	}

	public void addError(String error) {
		this.error = Boolean.TRUE;
		addMensajeALista(error);
	}

	/**
	 * Devuelve TRUE si hay error, en caso contrario, devuelve FALSE. Equivalente a
	 * isError()
	 * 
	 * @return boolean
	 */
	public Boolean getError() {
		return this.error;
	}

	/**
	 * Establece esta variable a TRUE si ha ocurrido un error, en caso contrario,
	 * deberia ser FALSE
	 * 
	 * @param error
	 */
	public void setError(Boolean error) {
		this.error = error;
	}

	public List<String> getListaMensajes() {
		return this.listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

}