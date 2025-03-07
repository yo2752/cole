package org.gestoresmadrid.oegam2comun.mensajes.procesos.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaMensajesProcesoBean {

	@FilterSimpleExpression(name = "codigo")
	private String codigo;

	@FilterSimpleExpression(name = "mensaje")
	private String mensaje;

	@FilterSimpleExpression(name = "descripcion")
	private String descripcion;

	@FilterSimpleExpression(name = "recuperable")
	private String recuperable;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRecuperable() {
		return recuperable;
	}

	public void setRecuperable(String recuperable) {
		this.recuperable = recuperable;
	}
}
