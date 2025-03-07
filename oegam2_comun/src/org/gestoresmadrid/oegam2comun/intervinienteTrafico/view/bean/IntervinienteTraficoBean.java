package org.gestoresmadrid.oegam2comun.intervinienteTrafico.view.bean;

import java.io.Serializable;
import java.util.Date;

public class IntervinienteTraficoBean implements Serializable{

	private static final long serialVersionUID = 617928473943933151L;

	private String numExpediente;
	private String numColegiado;
	private String nif;
	private String tipoInterviniente;
	private String descTipoInterviniente;
	private String autonomo;
	private String conceptoRepre;
	private String idMotivoTutela;
	private String descMotivo;
	private Date fechaInicio;
	private Date fechaFin;
	
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getTipoInterviniente() {
		return tipoInterviniente;
	}
	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}
	public String getDescTipoInterviniente() {
		return descTipoInterviniente;
	}
	public void setDescTipoInterviniente(String descTipoInterviniente) {
		this.descTipoInterviniente = descTipoInterviniente;
	}
	public String getAutonomo() {
		return autonomo;
	}
	public void setAutonomo(String autonomo) {
		this.autonomo = autonomo;
	}
	public String getConceptoRepre() {
		return conceptoRepre;
	}
	public void setConceptoRepre(String conceptoRepre) {
		this.conceptoRepre = conceptoRepre;
	}
	public String getIdMotivoTutela() {
		return idMotivoTutela;
	}
	public void setIdMotivoTutela(String idMotivoTutela) {
		this.idMotivoTutela = idMotivoTutela;
	}
	public String getDescMotivo() {
		return descMotivo;
	}
	public void setDescMotivo(String descMotivo) {
		this.descMotivo = descMotivo;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
}
