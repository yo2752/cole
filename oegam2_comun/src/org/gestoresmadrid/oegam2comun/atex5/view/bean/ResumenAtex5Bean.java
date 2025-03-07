package org.gestoresmadrid.oegam2comun.atex5.view.bean;

import java.io.Serializable;
import java.util.List;

public class ResumenAtex5Bean implements Serializable{

	private static final long serialVersionUID = -3435952789983438686L;

	public Integer numOk;
	public Integer numError;
	public List<String> listaOk;
	public List<String> listaErrores;
	public Boolean esConsultaPersona;
	public Boolean esConsultaVehiculo;
	public Boolean esConsultaEucaris;
	
	public ResumenAtex5Bean() {
		super();
		this.esConsultaPersona = Boolean.FALSE; 
		this.esConsultaVehiculo = Boolean.FALSE; 
		this.esConsultaEucaris = Boolean.FALSE; 
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
	
	public Boolean getEsConsultaPersona() {
		return esConsultaPersona;
	}
	
	public void setEsConsultaPersona(Boolean esConsultaPersona) {
		this.esConsultaPersona = esConsultaPersona;
	}
	
	public Boolean getEsConsultaVehiculo() {
		return esConsultaVehiculo;
	}
	
	public void setEsConsultaVehiculo(Boolean esConsultaVehiculo) {
		this.esConsultaVehiculo = esConsultaVehiculo;
	}
	
	public Boolean getEsConsultaEucaris() {
		return esConsultaEucaris;
	}
	
	public void setEsConsultaEucaris(Boolean esConsultaEucaris) {
		this.esConsultaEucaris = esConsultaEucaris;
	}
	
}
