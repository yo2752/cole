package org.gestoresmadrid.oegam2comun.presentacion.jpt.view.beans;


public class TipoEstadisticasJPTBean {

	private Long idTipoEstadistica;
	
	private String descripcion;
	
	private boolean visible;
	
	private String cantidad;
	
	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public Long getIdTipoEstadistica() {
		return idTipoEstadistica;
	}

	public void setIdTipoEstadistica(Long idTipoEstadistica) {
		this.idTipoEstadistica = idTipoEstadistica;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	
}
