package org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafInteveDto;

public class ResultadoTramInteveBean {

private static final long serialVersionUID = -6067630721289382786L;
	
	private Boolean error;
	private String mensajeError;
	private List<String> listaMensajeError;
	private TramiteTrafInteveDto tramiteTrafInteve;
	private String nombreXml;
	private File ficheroXml;
	private BigDecimal numExpediente;
	
	public ResultadoTramInteveBean(Boolean error) {
		super();
		this.error = error;
		this.listaMensajeError = new ArrayList<String>();
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
	public TramiteTrafInteveDto getTramiteTrafInteve() {
		return tramiteTrafInteve;
	}
	public void setTramiteTrafInteve(TramiteTrafInteveDto tramiteTrafSolInfo) {
		this.tramiteTrafInteve = tramiteTrafSolInfo;
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
