package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import utilidades.estructuras.Fecha;

public class FacturaRegistroDto implements Serializable{

	private static final long serialVersionUID = -2652487658412916162L;

	private long idFactura;

	private Fecha fechaPago;

	private String idTransferencia;

	private String cifEmisor;

	private String numSerie;

	private String ejercicio;

	private String numFactura;

	private Fecha fechaFactura;

	private BigDecimal idTramiteRegistro;

	private Fecha fechaEnvio;

	public long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}

	public String getIdTransferencia() {
		return idTransferencia;
	}

	public void setIdTransferencia(String idTransferencia) {
		this.idTransferencia = idTransferencia;
	}

	public String getCifEmisor() {
		return cifEmisor;
	}

	public void setCifEmisor(String cifEmisor) {
		this.cifEmisor = cifEmisor;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public Fecha getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Fecha fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Fecha getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Fecha fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Fecha getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Fecha fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

}
