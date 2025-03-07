package org.gestoresmadrid.core.personas.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;

/**
 * The persistent class for the PERSONA_DIRECCION database table.
 */
@Entity
@Table(name = "PERSONA_DIRECCION")
public class PersonaDireccionVO implements Serializable {

	private static final long serialVersionUID = -508161278372223321L;

	@EmbeddedId
	private PersonaDireccionPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@ManyToOne
	@JoinColumn(name = "ID_DIRECCION", insertable = false, updatable = false)
	private DireccionVO direccion;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "NIF", referencedColumnName = "NIF", insertable = false, updatable = false),
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false) })
	private PersonaVO persona;

	private Short estado;

	public PersonaDireccionPK getId() {
		return this.id;
	}

	public void setId(PersonaDireccionPK id) {
		this.id = id;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public PersonaVO getPersona() {
		return persona;
	}

	public void setPersona(PersonaVO persona) {
		this.persona = persona;
	}

	public Short getEstado() {
		return estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}
}