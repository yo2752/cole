package org.gestoresmadrid.oegam2comun.consultaDev.model.bean;

import java.io.Serializable;
import java.util.List;

public class ResumenConsultaDevBean implements Serializable{

	private static final long serialVersionUID = -8824380656094463010L;
	
	private Boolean resumenCambioEstado;
	private Boolean resumenConsultar;
	private Integer numOk;
	private Integer numError;
	private List<String> listaOk;
	private List<String> listaErrores;
	
	public Boolean getResumenCambioEstado() {
		return resumenCambioEstado;
	}
	public void setResumenCambioEstado(Boolean resumenCambioEstado) {
		this.resumenCambioEstado = resumenCambioEstado;
	}
	public Boolean getResumenConsultar() {
		return resumenConsultar;
	}
	public void setResumenConsultar(Boolean resumenConsultar) {
		this.resumenConsultar = resumenConsultar;
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
