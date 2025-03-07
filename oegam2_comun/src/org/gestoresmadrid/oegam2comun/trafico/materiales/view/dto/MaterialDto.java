package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;
import java.util.Date;

public class MaterialDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8075627393524567176L;
	
	private Long   materialId;
	private String nombre;
	private String tipo;
	private String descripcion;
	private Double precio;
	private String color;
	private String codigo;
	private Date   fecha;
	
	
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
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
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
