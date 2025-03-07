package org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.dto.EmpresaTelematicaDto;

public class ResultadoEmpresaTelematicaBean {

	private Boolean error;
	private String mensajeError;
	private List<String> listaMensajeError;
	private List<String> listaMensajeOK;
	private EmpresaTelematicaDto empresaTelematica;
	private String nombreXml;
	private File ficheroXml;
	private Long id;

	public ResultadoEmpresaTelematicaBean(Boolean error) {
		super();
		this.error = error;
		this.listaMensajeError = new ArrayList<>();
		this.listaMensajeOK = new ArrayList<>();
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
	public EmpresaTelematicaDto getEmpresaTelematica() {
		return empresaTelematica;
	}
	public void setEmpresaTelematica(EmpresaTelematicaDto empresaTelematica) {
		this.empresaTelematica = empresaTelematica;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getListaMensajeOK() {
		return listaMensajeOK;
	}

	public void setListaMensajeOK(List<String> listaMensajeOK) {
		this.listaMensajeOK = listaMensajeOK;
	}

}