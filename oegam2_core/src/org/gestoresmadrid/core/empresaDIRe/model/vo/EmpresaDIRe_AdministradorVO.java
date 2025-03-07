package org.gestoresmadrid.core.empresaDIRe.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPRESA_DIRE_ADMINISTRADORES")
public class EmpresaDIRe_AdministradorVO implements Serializable {
	
	private static final long serialVersionUID = 5199440606227746846L;

	
	@Id
	@Column(name="id")
	private BigDecimal id;	
	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;	
	@Column(name="DNI")
	private String dni;
	@Column(name="CERTIFICADO")
	private String certificado;
	@Column(name="DESCRIPCION")
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
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getCertificado() {
		return certificado;
	}
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	public String getDescripcion() {
		return descripcion;
	}

	
	
}
