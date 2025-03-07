package org.gestoresmadrid.oegam2comun.datosSensibles.views.beans;

import java.math.BigDecimal;

import utilidades.estructuras.FechaFraccionada;

public class DatosSensiblesBean {

	private FechaFraccionada fecha;
	private String tipoAgrupacion;
	private String textoAgrupacion;
	private BigDecimal tiempoDatosSensibles;
	private String tipoControl;
	private String tipoFichero;
	private String grupo;
	private Long estado;
	
	public String getTipoAgrupacion() {
		return tipoAgrupacion;
	}
	public void setTipoAgrupacion(String tipoAgrupacion) {
		this.tipoAgrupacion = tipoAgrupacion;
	}
	public String getTextoAgrupacion() {
		return textoAgrupacion;
	}
	public void setTextoAgrupacion(String textoAgrupacion) {
		this.textoAgrupacion = textoAgrupacion;
	}
	public BigDecimal getTiempoDatosSensibles() {
		return tiempoDatosSensibles;
	}
	public void setTiempoDatosSensibles(BigDecimal tiempoDatosSensibles) {
		this.tiempoDatosSensibles = tiempoDatosSensibles;
	}
	public FechaFraccionada getFecha() {
		return fecha;
	}
	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}
	public String getTipoControl() {
		return tipoControl;
	}
	public void setTipoControl(String tipoControl) {
		this.tipoControl = tipoControl;
	}
	public String getTipoFichero() {
		return tipoFichero;
	}
	public void setTipoFichero(String tipoFichero) {
		this.tipoFichero = tipoFichero;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
}
