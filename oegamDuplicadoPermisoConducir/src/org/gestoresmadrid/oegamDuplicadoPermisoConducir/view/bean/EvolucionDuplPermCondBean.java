package org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean;

import java.io.Serializable;

public class EvolucionDuplPermCondBean implements Serializable {

	private static final long serialVersionUID = -7567011748701724334L;

	String fecha;
	String operacion;
	String estadoAnterior;
	String estadoNuevo;
	String usuario;

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
