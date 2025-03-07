package org.gestoresmadrid.oegam2comun.conductor.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaConductorFilter {

	@FilterSimpleExpression(name = "fechaIni", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaIni;

	@FilterSimpleExpression(name = "fechaFin", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaFin;

	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;

	@FilterSimpleExpression(name = "nif")
	private String nif;

	@FilterSimpleExpression(name = "doiVehiculo")
	private String doiVehiculo;

	@FilterSimpleExpression(name = "matricula")
	private String matricula;

	@FilterSimpleExpression(name = "bastidor")
	private String bastidor;

	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;

	@FilterSimpleExpression(name = "idContrato")
	private Long idContrato;

	@FilterSimpleExpression(name = "estado")
	private BigDecimal estado;

	@FilterSimpleExpression(name = "operacion")
	private String operacion;

	@FilterSimpleExpression(name = "numRegistro")
	private String numRegistro;

	@FilterSimpleExpression(name = "refPropia")
	private String refPropia;

	@FilterSimpleExpression(name = "fechaPresentacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;

	public String getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public FechaFraccionada getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(FechaFraccionada fechaIni) {
		this.fechaIni = fechaIni;
	}

	public FechaFraccionada getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(FechaFraccionada fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDoiVehiculo() {
		return doiVehiculo;
	}

	public void setDoiVehiculo(String doiVehiculo) {
		this.doiVehiculo = doiVehiculo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

}
