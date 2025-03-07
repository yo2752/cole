package org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

public class ConsultaEEFFDto implements Serializable {

	private static final long serialVersionUID = 3472093241269926194L;

	private String numColegiado;

	private Boolean realizado;

	private Date fechaRealizacion;

	private String tarjetaBastidor;

	private String tarjetaNive;

	private String firCif;

	private String firMarca;

	private String respuesta;

	private String nifRepresentado;

	private String nombreRepresentado;

	private BigDecimal numExpediente;

	private BigDecimal numExpedienteTramite;

	private ContratoDto contrato;

	private String estado;

	private Date fechaAlta;

	private ResultadoConsultaEEFFDto resultadoConsultaEEFF;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getNumExpedienteTramite() {
		return numExpedienteTramite;
	}

	public void setNumExpedienteTramite(BigDecimal numExpedienteTramite) {
		this.numExpedienteTramite = numExpedienteTramite;
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

	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(Date fechaRealizacion) {
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

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ResultadoConsultaEEFFDto getResultadoConsultaEEFF() {
		return resultadoConsultaEEFF;
	}

	public void setResultadoConsultaEEFF(ResultadoConsultaEEFFDto resultadoConsultaEEFF) {
		this.resultadoConsultaEEFF = resultadoConsultaEEFF;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

}