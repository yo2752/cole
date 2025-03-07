package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

public class ConsultaJustificantePantallaBean implements Serializable{

	private static final long serialVersionUID = 8622267940949683064L;

	public File fichero;

	private BigDecimal numExpediente;
	private BigDecimal idJustificante;
	private String matricula;
	private String numColegiado;
	private String fechaIni;
	private String fechaFin;
	private BigDecimal estado;
	private Long idJustificanteInterno;
	private String codigoVerificacion;
	private String idContrato;
	private Boolean error;
	private String mensaje;
	private BigDecimal diasValidez;
	private String tipoTramite;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getIdJustificante() {
		return idJustificante;
	}

	public void setIdJustificante(BigDecimal idJustificante) {
		this.idJustificante = idJustificante;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Long getIdJustificanteInterno() {
		return idJustificanteInterno;
	}

	public void setIdJustificanteInterno(Long idJustificanteInterno) {
		this.idJustificanteInterno = idJustificanteInterno;
	}

	public String getCodigoVerificacion() {
		return codigoVerificacion;
	}

	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	public String getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public BigDecimal getDiasValidez() {
		return diasValidez;
	}

	public void setDiasValidez(BigDecimal diasValidez) {
		this.diasValidez = diasValidez;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

}