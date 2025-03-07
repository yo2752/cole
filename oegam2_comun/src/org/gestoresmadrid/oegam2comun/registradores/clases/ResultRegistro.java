package org.gestoresmadrid.oegam2comun.registradores.clases;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultRegistro {

	private Object obj;
	private List<String> validaciones;
	private String mensaje;
	private boolean error;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMensaje() {
		if (null == this.mensaje)
			this.mensaje = "";
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void addValidacion(String mensaje) {
		if (validaciones == null || validaciones.isEmpty()) {
			validaciones = new ArrayList<String>();
		}
		validaciones.add(mensaje);
	}

	public List<String> getValidaciones() {
		return validaciones;
	}

	public void setValidaciones(List<String> validaciones) {
		this.validaciones = validaciones;
	}

}
