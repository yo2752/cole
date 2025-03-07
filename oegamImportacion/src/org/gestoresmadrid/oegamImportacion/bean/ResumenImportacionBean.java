package org.gestoresmadrid.oegamImportacion.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResumenImportacionBean implements Serializable{

	private static final long serialVersionUID = -7263759695627367275L;
	
	public Integer numOk;
	public Integer numError;
	public List<String> listaOk;
	public List<String> listaErrores; 
	private String tipoTramite = "";
	private String mensajeError="";
	private int guardadosBien = 0;
	private int guardadosMal = 0;
	private Boolean error;
	
	public ResumenImportacionBean(String tipoTramite) {
		this.tipoTramite=tipoTramite;
	}

	public ResumenImportacionBean() {
		this.error = Boolean.FALSE;
	}

	public void addListaOk(String mensaje){
		if(listaOk == null || listaOk.isEmpty()){
			listaOk = new ArrayList<String>();
			numOk = new Integer(0);
		}
		listaOk.add(mensaje);
		numOk++;
	}
	
	public void addListaKO(String mensaje){
		if(listaErrores == null || listaErrores.isEmpty()){
			listaErrores = new ArrayList<String>();
			numError = new Integer(0);
		}
		listaErrores.add(mensaje);
		numError++;
	}
	
	public void addListaKO(List<String> listaMensajesError) {
		if (listaErrores == null || listaErrores.isEmpty()) {
			listaErrores = new ArrayList<String>();
		}
		for(String mensajeError : listaMensajesError) {
			listaErrores.add(mensajeError);
		}
	}
	
	public void addListaOK(List<String> listaMensajesOk) {
		if (listaOk == null || listaOk.isEmpty()) {
			listaOk = new ArrayList<String>();
		}
		for(String mensajeError : listaMensajesOk) {
			listaOk.add(mensajeError);
		}
	}

	public Integer getNumOk() {
		return numOk;
	}

	public void setNumOk(Integer numOk) {
		this.numOk = numOk;
	}

	public Integer getNumError() {
		return numError;
	}

	public void setNumError(Integer numError) {
		this.numError = numError;
	}

	public List<String> getListaOk() {
		return listaOk;
	}

	public void setListaOk(List<String> listaOk) {
		this.listaOk = listaOk;
	}

	public List<String> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public int getGuardadosBien() {
		return guardadosBien;
	}

	public void setGuardadosBien(int guardadosBien) {
		this.guardadosBien = guardadosBien;
	}

	public int getGuardadosMal() {
		return guardadosMal;
	}

	public void setGuardadosMal(int guardadosMal) {
		this.guardadosMal = guardadosMal;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}
	
	public int addCorrecto() {
		this.guardadosBien++;
		return this.guardadosBien;
	}
	
	public int addIncorrecto() {
		this.guardadosMal++;
		return this.guardadosMal;
	}
}
