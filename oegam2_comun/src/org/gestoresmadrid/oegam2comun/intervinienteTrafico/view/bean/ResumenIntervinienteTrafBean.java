package org.gestoresmadrid.oegam2comun.intervinienteTrafico.view.bean;

import java.io.Serializable;
import java.util.List;

public class ResumenIntervinienteTrafBean implements Serializable{
	
	private static final long serialVersionUID = -7249526331161986718L;
	
	private Boolean resumenEliminar;
	private Integer numOk;
	private Integer numError;
	private List<String> listaOk;
	private List<String> listaErrores;
	
	public Boolean getResumenEliminar() {
		return resumenEliminar;
	}
	public void setResumenEliminar(Boolean resumenEliminar) {
		this.resumenEliminar = resumenEliminar;
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
	
}
