package org.gestoresmadrid.oegam2comun.presentacion05.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoPresentacion05Bean implements Serializable {

	private static final long serialVersionUID = 7763349136937972831L;

	private Boolean error;
	private String mensaje;
	private String resultado;
	private List<String> listaMensajes;

	private Presentacion05Bean presentacion05Bean;

	public ResultadoPresentacion05Bean(Boolean error) {
		this.error = error;
	}

	public void addListaMensajes(String mensaje) {
		if (listaMensajes == null || listaMensajes.isEmpty()) {
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public Presentacion05Bean getPresentacion05Bean() {
		return presentacion05Bean;
	}

	public void setPresentacion05Bean(Presentacion05Bean presentacion05Bean) {
		this.presentacion05Bean = presentacion05Bean;
	}
}
