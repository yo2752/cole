package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;
import java.util.Date;

import utilidades.estructuras.FechaFraccionada;

public class FacturacionStockBean implements Serializable{

	private static final long serialVersionUID = 1463564616926400622L;

	private Long idContrato;
	private String tipo;
	private String tipoDistintivo;
	private FechaFraccionada fecha;
	private Boolean esDescargable;
	private String nombreFichero;
	private Date fechaGenExcel;

	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipoDistintivo() {
		return tipoDistintivo;
	}
	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}
	public FechaFraccionada getFecha() {
		return fecha;
	}
	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}
	public Boolean getEsDescargable() {
		return esDescargable;
	}
	public void setEsDescargable(Boolean esDescargable) {
		this.esDescargable = esDescargable;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public Date getFechaGenExcel() {
		return fechaGenExcel;
	}
	public void setFechaGenExcel(Date fechaGenExcel) {
		this.fechaGenExcel = fechaGenExcel;
	}
	
}
