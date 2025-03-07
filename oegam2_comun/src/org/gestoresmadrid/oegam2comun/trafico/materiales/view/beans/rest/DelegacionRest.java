package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

@XmlRootElement(name="delegacion")
@XmlAccessorType(XmlAccessType.FIELD)
public class DelegacionRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3094411255277115036L;
	
    @XmlElement(name="id", required = true, type = long.class )
    @XmlSchemaType(name="string")
	private Long id;
    
    @XmlElement(name="nombre", required = true)
	private String nombre;
	
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
	
}
