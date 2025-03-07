package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;

public class ResultadoInformacionMateriales implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7638238999320937417L;
	
	private Long id;
	private String nombre;
	private String descripcion;
	private Double precio;
	private String color;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
