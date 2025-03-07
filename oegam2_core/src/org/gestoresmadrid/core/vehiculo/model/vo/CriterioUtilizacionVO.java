package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the CRITERIO_UTILIZACION database table.
 */
@Entity
@Table(name = "CRITERIO_UTILIZACION")
public class CriterioUtilizacionVO implements Serializable {

	private static final long serialVersionUID = -3016339497829020998L;

	@Id
	@Column(name = "ID_CRITERIO_UTILIZACION")
	private String idCriterioUtilizacion;

	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdCriterioUtilizacion() {
		return idCriterioUtilizacion;
	}

	public void setIdCriterioUtilizacion(String idCriterioUtilizacion) {
		this.idCriterioUtilizacion = idCriterioUtilizacion;
	}
}