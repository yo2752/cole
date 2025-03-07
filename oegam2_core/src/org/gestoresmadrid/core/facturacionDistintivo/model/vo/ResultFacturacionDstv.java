package org.gestoresmadrid.core.facturacionDistintivo.model.vo;

import java.io.Serializable;

public class ResultFacturacionDstv implements Serializable {

	private static final long serialVersionUID = 4012525901222701750L;

	private Long cantidadDstv;
	private Long cantidadDup;
	private String tipo;
	private String fecha;
	private String numColegiado;
	private String via;
	private String estado;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getCantidadDstv() {
		return cantidadDstv;
	}

	public void setCantidadDstv(Long cantidadDstv) {
		this.cantidadDstv = cantidadDstv;
	}

	public Long getCantidadDup() {
		return cantidadDup;
	}

	public void setCantidadDup(Long cantidadDup) {
		this.cantidadDup = cantidadDup;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

}