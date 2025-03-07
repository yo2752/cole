package org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafSolInfoDto;

public class ResultadoTramSolInfoBean implements Serializable{

	private static final long serialVersionUID = -6067630721289382786L;
	
	private Boolean error;
	private String mensajeError;
	private List<String> listaMensajeError;
	private TramiteTrafSolInfoDto tramiteTrafSolInfo;
	private String nombreXml;
	private File ficheroXml;
	private BigDecimal numExpediente;
	
	public ResultadoTramSolInfoBean(Boolean error) {
		super();
		this.error = error;
	}
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	public List<String> getListaMensajeError() {
		return listaMensajeError;
	}
	public void setListaMensajeError(List<String> listaMensajeError) {
		this.listaMensajeError = listaMensajeError;
	}
	public TramiteTrafSolInfoDto getTramiteTrafSolInfo() {
		return tramiteTrafSolInfo;
	}
	public void setTramiteTrafSolInfo(TramiteTrafSolInfoDto tramiteTrafSolInfo) {
		this.tramiteTrafSolInfo = tramiteTrafSolInfo;
	}
	public String getNombreXml() {
		return nombreXml;
	}
	public void setNombreXml(String nombreXml) {
		this.nombreXml = nombreXml;
	}
	public File getFicheroXml() {
		return ficheroXml;
	}
	public void setFicheroXml(File ficheroXml) {
		this.ficheroXml = ficheroXml;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}
