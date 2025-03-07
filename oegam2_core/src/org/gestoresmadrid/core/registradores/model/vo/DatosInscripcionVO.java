package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the DATOS_INSCRIPCION database table.
 * 
 */
@Entity
@Table(name="DATOS_INSCRIPCION")
public class DatosInscripcionVO implements Serializable {

	private static final long serialVersionUID = -5131574506730322739L;

	@Id
	@SequenceGenerator(name = "datos_inscripcion_secuencia", sequenceName = "DATOS_INSCRIPCION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "datos_inscripcion_secuencia")
	@Column(name="ID_DATOS_INSCRIPCION")
	private long idDatosInscripcion;

	@Column(name="CODIGO_RBM")
	private String codigoRbm;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INSCRIPCION")
	private Date fechaInscripcion;

	@Column(name="NUMERO_INSCRIPCION_BIEN")
	private BigDecimal numeroInscripcionBien;

	@Column(name="NUMERO_REGISTRAL_BIEN")
	private BigDecimal numeroRegistralBien;

	public DatosInscripcionVO() {
	}

	public long getIdDatosInscripcion() {
		return this.idDatosInscripcion;
	}

	public void setIdDatosInscripcion(long idDatosInscripcion) {
		this.idDatosInscripcion = idDatosInscripcion;
	}

	public String getCodigoRbm() {
		return this.codigoRbm;
	}

	public void setCodigoRbm(String codigoRbm) {
		this.codigoRbm = codigoRbm;
	}

	public Timestamp getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public Date getFechaInscripcion() {
		return this.fechaInscripcion;
	}

	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public BigDecimal getNumeroInscripcionBien() {
		return this.numeroInscripcionBien;
	}

	public void setNumeroInscripcionBien(BigDecimal numeroInscripcionBien) {
		this.numeroInscripcionBien = numeroInscripcionBien;
	}

	public BigDecimal getNumeroRegistralBien() {
		return this.numeroRegistralBien;
	}

	public void setNumeroRegistralBien(BigDecimal numeroRegistralBien) {
		this.numeroRegistralBien = numeroRegistralBien;
	}

}