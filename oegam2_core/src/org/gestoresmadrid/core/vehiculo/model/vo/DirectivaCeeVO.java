package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the DIRECTIVA_CEE database table.
 */
@Entity
@Table(name = "DIRECTIVA_CEE")
public class DirectivaCeeVO implements Serializable {

	private static final long serialVersionUID = -6444914795830274660L;

	@Id
	@Column(name = "ID_DIRECTIVA_CEE")
	private String idDirectivaCEE;

	private String descripcion;

	@Column(name = "CRITERIO_CONSTRUCCION")
	private String criterioConstruccion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdDirectivaCEE() {
		return idDirectivaCEE;
	}

	public void setIdDirectivaCEE(String idDirectivaCEE) {
		this.idDirectivaCEE = idDirectivaCEE;
	}

	public String getCriterioConstruccion() {
		return criterioConstruccion;
	}

	public void setCriterioConstruccion(String criterioConstruccion) {
		this.criterioConstruccion = criterioConstruccion;
	}
}