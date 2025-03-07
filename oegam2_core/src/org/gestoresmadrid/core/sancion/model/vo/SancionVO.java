package org.gestoresmadrid.core.sancion.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SANCION database table.
 * 
 */
@Entity
@Table(name="SANCION")
public class SancionVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_SANCION")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEC_SANCION")
	@SequenceGenerator(name="SEC_SANCION", sequenceName="SANCION_SEQ")
	private Integer idSancion;

	private String apellidos;

	private String dni;
	
	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	private Integer estado;

	@Column(name="ESTADO_SANCION")
	private Integer estadoSancion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_PRESENTACION")
	private Date fechaPresentacion;

	private Integer motivo;

	private String nombre;

	public SancionVO() {
	}

	public Integer getIdSancion() {
		return this.idSancion;
	}

	public void setIdSancion(Integer idSancion) {
		this.idSancion = idSancion;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getEstadoSancion() {
		return this.estadoSancion;
	}

	public void setEstadoSancion(Integer estadoSancion) {
		this.estadoSancion = estadoSancion;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaPresentacion() {
		return this.fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Integer getMotivo() {
		return this.motivo;
	}

	public void setMotivo(Integer motivo) {
		this.motivo = motivo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
}