package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class OtroImporteDto implements Serializable{

	private static final long serialVersionUID = 4295437396218128234L;

	private long idOtroImporte;

	private String condicionante;

	private Timestamp fecCreacion;

	private Timestamp fecModificacion;

	private BigDecimal importe;

	private String observaciones;

	private BigDecimal porcentaje;

	private BigDecimal porcentajeBase;

	private String tipoOtroImporte;

	private BigDecimal idDatosFinancieros;

	public long getIdOtroImporte() {
		return idOtroImporte;
	}

	public void setIdOtroImporte(long idOtroImporte) {
		this.idOtroImporte = idOtroImporte;
	}

	public String getCondicionante() {
		return condicionante;
	}

	public void setCondicionante(String condicionante) {
		this.condicionante = condicionante;
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

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public BigDecimal getPorcentajeBase() {
		return porcentajeBase;
	}

	public void setPorcentajeBase(BigDecimal porcentajeBase) {
		this.porcentajeBase = porcentajeBase;
	}

	public String getTipoOtroImporte() {
		return tipoOtroImporte;
	}

	public void setTipoOtroImporte(String tipoOtroImporte) {
		this.tipoOtroImporte = tipoOtroImporte;
	}

	public BigDecimal getIdDatosFinancieros() {
		return idDatosFinancieros;
	}

	public void setIdDatosFinancieros(BigDecimal idDatosFinancieros) {
		this.idDatosFinancieros = idDatosFinancieros;
	}
}
