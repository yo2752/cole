package org.gestoresmadrid.oegamInterga.view.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoIntergaBean implements Serializable {

	private static final long serialVersionUID = 1387019939801236057L;

	private Boolean error;
	private String mensaje;
	private int code;
	private String codigoError;
	private List<String> listaMensajes;
	private String estado;
	private Object response;
	private int numeroEjecuciones;
	private File fichero;

	public ResultadoIntergaBean(Boolean error) {
		super();
		this.error = error;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public void addListaMensaje(String mensaje) {
		if (listaMensajes == null || listaMensajes.isEmpty()) {
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getNumeroEjecuciones() {
		return numeroEjecuciones;
	}

	public void setNumeroEjecuciones(int numeroEjecuciones) {
		this.numeroEjecuciones = numeroEjecuciones;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}
}
