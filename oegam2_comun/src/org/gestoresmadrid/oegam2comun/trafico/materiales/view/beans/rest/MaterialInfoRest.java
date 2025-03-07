package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="material")
@XmlAccessorType(XmlAccessType.FIELD)
public class MaterialInfoRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -151655039447316657L;
	
    @XmlElement(name="id", required = true)
    private Long   id;
    
    @XmlElement(name="nombre", required = true)
	private String nombre;
    
    @XmlElement(name="descripcion", required = true)
	private String descripcion;
    
    @XmlElement(name="precio", required = true )
	private Double precio;
    
    @XmlElement(name="color", required = true)
	private String color;
    
    @XmlElement(name="codigo", required = true)
	private String codigo;
	
	public MaterialInfoRest(Long id, String nombre, String descripcion, Double precio, String color) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.color = color;
	}
	
	public MaterialInfoRest() {}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

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
