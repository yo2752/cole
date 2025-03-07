package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import utilidades.estructuras.Fecha;

public class DatosInscripcionDto implements Serializable {

	private static final long serialVersionUID = 3076536116408734978L;
	
	private long idDatosInscripcion;

	private String codigoRbm;

	
	private Timestamp fecCreacion;

	
	private Timestamp fecModificacion;

	
	private Date fechaInscripcion;
	
	private Fecha fechaInscripcionDatInscrip;

	
	private BigDecimal numeroInscripcionBien;

	
	private BigDecimal numeroRegistralBien;


	public long getIdDatosInscripcion() {
		return idDatosInscripcion;
	}


	public void setIdDatosInscripcion(long idDatosInscripcion) {
		this.idDatosInscripcion = idDatosInscripcion;
	}


	public String getCodigoRbm() {
		return codigoRbm;
	}


	public void setCodigoRbm(String codigoRbm) {
		this.codigoRbm = codigoRbm;
	}


	public Timestamp getFecCreacion() {
		return fecCreacion;
	}


	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}


	public Timestamp getFecModificacion() {
		return fecModificacion;
	}


	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}


	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}


	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}


	public Fecha getFechaInscripcionDatInscrip() {
		return fechaInscripcionDatInscrip;
	}


	public void setFechaInscripcionDatInscrip(Fecha fechaInscripcionDatInscrip) {
		this.fechaInscripcionDatInscrip = fechaInscripcionDatInscrip;
	}


	public BigDecimal getNumeroInscripcionBien() {
		return numeroInscripcionBien;
	}


	public void setNumeroInscripcionBien(BigDecimal numeroInscripcionBien) {
		this.numeroInscripcionBien = numeroInscripcionBien;
	}


	public BigDecimal getNumeroRegistralBien() {
		return numeroRegistralBien;
	}


	public void setNumeroRegistralBien(BigDecimal numeroRegistralBien) {
		this.numeroRegistralBien = numeroRegistralBien;
	}

}
