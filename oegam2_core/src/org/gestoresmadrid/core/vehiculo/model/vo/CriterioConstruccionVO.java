package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the CRITERIO_CONSTRUCCION database table.
 */
@Entity
@Table(name = "CRITERIO_CONSTRUCCION")
public class CriterioConstruccionVO implements Serializable {

	private static final long serialVersionUID = -8989130577302293051L;

	@Id
	@Column(name = "ID_CRITERIO_CONSTRUCCION")
	private String idCriterioConstruccion;

	private String descripcion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CLAVE_MATRICULACION")
	private ClaveMatriculacionVO claveMatriculacion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdCriterioConstruccion() {
		return idCriterioConstruccion;
	}

	public void setIdCriterioConstruccion(String idCriterioConstruccion) {
		this.idCriterioConstruccion = idCriterioConstruccion;
	}

	public ClaveMatriculacionVO getClaveMatriculacion() {
		return claveMatriculacion;
	}

	public void setClaveMatriculacion(ClaveMatriculacionVO claveMatriculacion) {
		this.claveMatriculacion = claveMatriculacion;
	}
}