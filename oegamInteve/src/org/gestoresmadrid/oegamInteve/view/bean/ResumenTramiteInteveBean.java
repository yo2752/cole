package org.gestoresmadrid.oegamInteve.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResumenTramiteInteveBean implements Serializable {

	private static final long serialVersionUID = 5028950626870652240L;

	Long numError;
	List<String> listaErrores;
	Long numOK;
	List<String> listaOK;

	public void addNumError() {
		if (numError == null) {
			numError = 0L;
		}
		numError++;
	}

	public void addNumOk(){
		if (numOK == null) {
			numOK = 0L;
		}
		numOK++;
	}

	public void addListaError(String mensaje) {
		if (listaErrores == null || listaErrores.isEmpty()) {
			listaErrores = new ArrayList<>();
		}
		listaErrores.add(mensaje);
		addNumError();
	}

	public void addListaOK(String mensaje) {
		if (listaOK == null || listaOK.isEmpty()) {
			listaOK = new ArrayList<>();
		}
		listaOK.add(mensaje);
		addNumOk();
	}

	public Long getNumError() {
		return numError;
	}
	public void setNumError(Long numError) {
		this.numError = numError;
	}
	public List<String> getListaErrores() {
		return listaErrores;
	}
	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}
	public Long getNumOK() {
		return numOK;
	}
	public void setNumOK(Long numOK) {
		this.numOK = numOK;
	}
	public List<String> getListaOK() {
		return listaOK;
	}
	public void setListaOK(List<String> listaOK) {
		this.listaOK = listaOK;
	}

}