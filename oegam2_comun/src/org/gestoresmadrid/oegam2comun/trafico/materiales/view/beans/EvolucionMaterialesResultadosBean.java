package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;
import java.util.Date;


public class EvolucionMaterialesResultadosBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 846818164154686982L;
	
	private Long   evolucionMaterialId;
	private Long   materialId;
	private String name;
	private String tipo;
	private String descripcion;
	private Double precio;
	private String color;
	private String codigo;
	private String estado;
	private Date   fechaAlta;

	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Long getEvolucionMaterialId() {
		return evolucionMaterialId;
	}
	public void setEvolucionMaterialId(Long evolucionMaterialId) {
		this.evolucionMaterialId = evolucionMaterialId;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
		
}
