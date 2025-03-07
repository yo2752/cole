package org.gestoresmadrid.core.empresaDIRe.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPRESA_DIRE_CONTACTOS")
public class EmpresaDIRe_ContactoVO implements Serializable {
	
	private static final long serialVersionUID = 5199440606227746846L;

	
	@Id
	@Column(name="id")
	private BigDecimal id;	
	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;	
	@Column(name="NOMBRE")
	private String nombre;
	@Column(name="Apellido1")
	private String apellido1;
	@Column(name="Apellido2")
	private String apellido2;
	@Column(name="Descripcion")
	private String descripcion;
	
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}
