package org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans;

import java.util.List;

public class ResumenConsultaInteve {
	
	public Integer numOk;
	public Integer numError;
	public List<String> listaOk;
	public List<String> listaErrores;
	
	
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
