package org.gestoresmadrid.core.accionTramite.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the ACCION_TRAMITE database table.
 * 
 */
@Embeddable
public class AccionTramitePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_EXPEDIENTE", insertable=false, updatable=false)
	private long numExpediente;

	@Column(name="ACCION")
	private String accion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;

	public AccionTramitePK() {
	}
	public long getNumExpediente() {
		return this.numExpediente;
	}
	public void setNumExpediente(long numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getAccion() {
		return this.accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public java.util.Date getFechaInicio() {
		return this.fechaInicio;
	}
	public void setFechaInicio(java.util.Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccionTramitePK)) {
			return false;
		}
		AccionTramitePK castOther = (AccionTramitePK)other;
		return 
			(this.numExpediente == castOther.numExpediente)
			&& this.accion.equals(castOther.accion)
			&& this.fechaInicio.equals(castOther.fechaInicio);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.numExpediente ^ (this.numExpediente >>> 32)));
		hash = hash * prime + this.accion.hashCode();
		hash = hash * prime + this.fechaInicio.hashCode();
		
		return hash;
	}
}