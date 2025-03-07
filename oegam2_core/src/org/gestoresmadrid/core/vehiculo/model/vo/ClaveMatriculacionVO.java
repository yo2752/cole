package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the CLAVE_MATRICULACION database table.
 */
@Entity
@Table(name = "CLAVE_MATRICULACION")
public class ClaveMatriculacionVO implements Serializable {

	private static final long serialVersionUID = -8480055392710929127L;

	@Id
	@Column(name = "ID_CLAVE")
	private BigDecimal idClave;

	private String clave;
	
	@Column(name = "NOMBRE_CLAVE")
	private String nombreClave;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public BigDecimal getIdClave() {
		return idClave;
	}

	public void setIdClave(BigDecimal idClave) {
		this.idClave = idClave;
	}

	public String getNombreClave() {
		return nombreClave;
	}

	public void setNombreClave(String nombreClave) {
		this.nombreClave = nombreClave;
	}
}