package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans;

import java.io.Serializable;
import java.util.List;

public class ResumenJustificanteProfBean implements Serializable{

	private static final long serialVersionUID = 6047328533279598778L;
	
	public Integer numOk;
	public Integer numError;
	public List<String> listaOK;
	public List<String> listaErrores;
	public Boolean esImpresion;
	public Boolean esForzarJp;
	public Boolean esPteAutoColegio;
	public Boolean esAnular;
	public Boolean esGenerarJp;
	
	public ResumenJustificanteProfBean() {
		super();
		this.esAnular = Boolean.FALSE;
		this.esForzarJp = Boolean.FALSE;
		this.esImpresion = Boolean.FALSE;
		this.esPteAutoColegio = Boolean.FALSE;
		this.esGenerarJp = Boolean.FALSE;
	}
	
	public Boolean getEsGenerarJp() {
		return esGenerarJp;
	}
	public void setEsGenerarJp(Boolean esGenerarJp) {
		this.esGenerarJp = esGenerarJp;
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
	public List<String> getListaOK() {
		return listaOK;
	}
	public void setListaOK(List<String> listaOK) {
		this.listaOK = listaOK;
	}
	public List<String> getListaErrores() {
		return listaErrores;
	}
	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}
	public Boolean getEsImpresion() {
		return esImpresion;
	}
	public void setEsImpresion(Boolean esImpresion) {
		this.esImpresion = esImpresion;
	}
	public Boolean getEsForzarJp() {
		return esForzarJp;
	}
	public void setEsForzarJp(Boolean esForzarJp) {
		this.esForzarJp = esForzarJp;
	}
	public Boolean getEsPteAutoColegio() {
		return esPteAutoColegio;
	}
	public void setEsPteAutoColegio(Boolean esPteAutoColegio) {
		this.esPteAutoColegio = esPteAutoColegio;
	}
	public Boolean getEsAnular() {
		return esAnular;
	}
	public void setEsAnular(Boolean esAnular) {
		this.esAnular = esAnular;
	}
	
	
}
