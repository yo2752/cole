package org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;

import utilidades.estructuras.Fecha;

public class LiberacionEEFFDto implements Serializable {

	private static final long serialVersionUID = 5241019373818584307L;

	private BigDecimal numExpediente;

	private String numFactura;

	private Fecha fechaFactura;

	private BigDecimal importe;

	private Boolean exento;

	private String numColegiado;

	private Boolean realizado;

	private Fecha fechaRealizacion;

	private String tarjetaBastidor;

	private String tarjetaNive;

	private String firCif;

	private String firMarca;

	private String respuesta;

	private String nifRepresentado;

	private String nombreRepresentado;

	private String nif;

	private IntervinienteTraficoDto titular;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public Fecha getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Fecha fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Boolean getExento() {
		return exento;
	}

	public void setExento(Boolean exento) {
		this.exento = exento;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Boolean getRealizado() {
		return realizado;
	}

	public void setRealizado(Boolean realizado) {
		this.realizado = realizado;
	}

	public Fecha getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(Fecha fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public String getTarjetaBastidor() {
		return tarjetaBastidor;
	}

	public void setTarjetaBastidor(String tarjetaBastidor) {
		this.tarjetaBastidor = tarjetaBastidor;
	}

	public String getTarjetaNive() {
		return tarjetaNive;
	}

	public void setTarjetaNive(String tarjetaNive) {
		this.tarjetaNive = tarjetaNive;
	}

	public String getFirCif() {
		return firCif;
	}

	public void setFirCif(String firCif) {
		this.firCif = firCif;
	}

	public String getFirMarca() {
		return firMarca;
	}

	public void setFirMarca(String firMarca) {
		this.firMarca = firMarca;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getNifRepresentado() {
		return nifRepresentado;
	}

	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}

	public String getNombreRepresentado() {
		return nombreRepresentado;
	}

	public void setNombreRepresentado(String nombreRepresentado) {
		this.nombreRepresentado = nombreRepresentado;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public IntervinienteTraficoDto getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteTraficoDto titular) {
		this.titular = titular;
	}

}