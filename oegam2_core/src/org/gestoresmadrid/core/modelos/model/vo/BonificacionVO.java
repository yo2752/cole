package org.gestoresmadrid.core.modelos.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BONIFICACION")
public class BonificacionVO implements Serializable{

	private static final long serialVersionUID = 6044625650086896839L;

	@Id
	@Column(name="BONIFICACION")
	private String bonificacion;
	
	@Column(name="MONTO")
	private BigDecimal monto;
	
	@Column(name="DESC_BONIFICACION")
	private String descripcion;
	
	@Column(name="ESTADO")
	private BigDecimal estado;

	public String getBonificacion() {
		return bonificacion;
	}

	public void setBonificacion(String bonificacion) {
		this.bonificacion = bonificacion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

}
