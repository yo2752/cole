package org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DatosBancariosBean implements Serializable{

	private static final long serialVersionUID = 4534883243909541175L;

	private Long idDatosBancarios;
	
	private BigDecimal formaPago;
	
	private String numColegiado;
	
	private String provContrato;
	
	private String nifPagador;
	
	private Date fechaAlta;
	
	private String numCuenta;
	
	private String estado;
	
	private String descripcion;

	public Long getIdDatosBancarios() {
		return idDatosBancarios;
	}

	public void setIdDatosBancarios(Long idDatosBancarios) {
		this.idDatosBancarios = idDatosBancarios;
	}

	public BigDecimal getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(BigDecimal formaPago) {
		this.formaPago = formaPago;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getProvContrato() {
		return provContrato;
	}

	public void setProvContrato(String provContrato) {
		this.provContrato = provContrato;
	}

	public String getNifPagador() {
		return nifPagador;
	}

	public void setNifPagador(String nifPagador) {
		this.nifPagador = nifPagador;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
