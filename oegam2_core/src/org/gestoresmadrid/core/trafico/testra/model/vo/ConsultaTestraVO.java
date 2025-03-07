package org.gestoresmadrid.core.trafico.testra.model.vo;

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

@Entity
@Table(name = "CONSULTA_TESTRA")
public class ConsultaTestraVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2934641649745601210L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ID_CONSULTA_TESTRA_SEQ_GEN")
	@SequenceGenerator(name = "ID_CONSULTA_TESTRA_SEQ_GEN", sequenceName = "ID_CONSULTA_TESTRA_SEQ")
	@Column(name = "ID_CONSULTA_TESTRA")
	private Long id;

	@Column(name = "DATO", unique = true, nullable = false)
	private String dato;

	@Column(name = "TIPO")
	private String tipo;

	@Column(name = "FECHA_ALTA", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@Column(name = "FECHA_ULTIMA_SANCION")
	@Temporal(TemporalType.DATE)
	private Date fechaUltimaSancion;

	@Column(name = "NUM_COLEGIADO", nullable = false)
	private String numColegiado;

	@Column(name = "ACTIVO")
	private Short activo;
	
	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;
	
	@Column(name = "FECHA_MODIF")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModif;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaUltimaSancion() {
		return fechaUltimaSancion;
	}

	public void setFechaUltimaSancion(Date fechaUltimaSancion) {
		this.fechaUltimaSancion = fechaUltimaSancion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Short getActivo() {
		return activo;
	}

	public void setActivo(Short activo) {
		this.activo = activo;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Date getFechaModif() {
		return fechaModif;
	}

	public void setFechaModif(Date fechaModif) {
		this.fechaModif = fechaModif;
	}

}
