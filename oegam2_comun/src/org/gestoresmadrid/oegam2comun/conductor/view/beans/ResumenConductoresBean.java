package org.gestoresmadrid.oegam2comun.conductor.view.beans;

import java.io.Serializable;
import java.util.List;

public class ResumenConductoresBean implements Serializable{

	private static final long serialVersionUID = -3435952789983438686L;

	public Integer numOk;
	public Integer numError;
	public List<String> listaOk;
	public List<String> listaErrores;
	
	public ResumenConductoresBean() {
		super();
	 
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
