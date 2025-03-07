package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;

public class JustificanteProfDto implements Serializable {

	private static final long serialVersionUID = 6067908851814415827L;

	private Long idJustificanteInterno;

	private String codigoVerificacion;

	private BigDecimal diasValidez;

	private String documentos;

	private BigDecimal estado;

	private Date fechaFin;

	private Date fechaInicio;
	
	private Date fechaAlta;

	private BigDecimal idJustificante;

	private String verificado;

	private BigDecimal numExpediente;
	
	private String observaciones;
	
	private String motivo;
	
	private TramiteTrafDto tramiteTrafico;

	public JustificanteProfDto() {}

	public Long getIdJustificanteInterno() {
		return this.idJustificanteInterno;
	}

	public void setIdJustificanteInterno(Long idJustificanteInterno) {
		this.idJustificanteInterno = idJustificanteInterno;
	}

	public String getCodigoVerificacion() {
		return this.codigoVerificacion;
	}

	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	public BigDecimal getDiasValidez() {
		return this.diasValidez;
	}

	public void setDiasValidez(BigDecimal diasValidez) {
		this.diasValidez = diasValidez;
	}

	public String getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public BigDecimal getIdJustificante() {
		return this.idJustificante;
	}

	public void setIdJustificante(BigDecimal idJustificante) {
		this.idJustificante = idJustificante;
	}

	public String getVerificado() {
		return this.verificado;
	}

	public void setVerificado(String verificado) {
		this.verificado = verificado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public TramiteTrafDto getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTrafDto tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
}