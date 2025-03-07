package org.gestoresmadrid.core.trafico.materiales.model.values;

import java.io.Serializable;

public class CantidadDistintivoValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -298000402464515638L;
	
	private String  numColegiado;
	private String  fechaImpresion;
	private String  tipoDistintivo;
	private Long    cantidad;
	private String via;
	private String provincia;
	private String nombreColegiado;
	
	public String getTipoDistintivo() {
		return tipoDistintivo;
	}
	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}
	public String getFechaImpresion() {
		return fechaImpresion;
	}
	public void setFechaImpresion(String fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getNombreColegiado() {
		return nombreColegiado;
	}
	public void setNombreColegiado(String nombreColegiado) {
		this.nombreColegiado = nombreColegiado;
	}
	
}
