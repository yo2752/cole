package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the LC_PERSONA_DIRECCION database table.
 */
@Entity
@Table(name = "LC_PERSONA_DIRECCION")
public class LcPersonaDireccionVO implements Serializable {

	private static final long serialVersionUID = 3714725965900038083L;

	@Id
	@SequenceGenerator(name = "lc_persona_direccion", sequenceName = "LC_PERSONA_DIRECCION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_persona_direccion")
	@Column(name = "ID_PERSONA_DIRECCION")
	private Long idPersonaDireccion;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "ID_DIRECCION")
	private Long idDireccion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION", insertable = false, updatable = false)
	private LcDireccionVO lcDireccion;

	@Column(name = "NIF")
	private String nif;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Column(name = "ID_PERSONA")
	private Long idPersona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = false, updatable = false)
	private LcPersonaVO lcPersona;

	public Long getIdPersonaDireccion() {
		return idPersonaDireccion;
	}

	public void setIdPersonaDireccion(Long idPersonaDireccion) {
		this.idPersonaDireccion = idPersonaDireccion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public LcDireccionVO getLcDireccion() {
		return lcDireccion;
	}

	public void setLcDireccion(LcDireccionVO lcDireccion) {
		this.lcDireccion = lcDireccion;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public LcPersonaVO getLcPersona() {
		return lcPersona;
	}

	public void setLcPersona(LcPersonaVO lcPersona) {
		this.lcPersona = lcPersona;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}
}