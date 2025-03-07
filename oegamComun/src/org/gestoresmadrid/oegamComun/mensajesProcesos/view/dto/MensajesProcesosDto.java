package org.gestoresmadrid.oegamComun.mensajesProcesos.view.dto;


public class MensajesProcesosDto {
	
	private String codigo;
	
	private String mensaje;
	
	private String descripcion;
	
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
