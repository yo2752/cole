package org.gestoresmadrid.oegamPermisoInternacional.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResumenPermisoInternacionalBean implements Serializable{

	private static final long serialVersionUID = -4168557204853265686L;
	
	private Long numOk;
	private List<String> listaOk;
	private Long numError;
	private List<String> listaErrores;
	
	public void addListaResumenOK(String mensaje){
		if(listaOk == null || listaOk.isEmpty()){
			listaOk = new ArrayList<>();
			numOk = 0L;
		}
		listaOk.add(mensaje);
		numOk++;
	}
	
	public void addListaResumenError(String mensaje){
		if(listaErrores == null || listaErrores.isEmpty()){
			listaErrores = new ArrayList<>();
			numError = 0L;
		}
		listaErrores.add(mensaje);
		numError++;
	}
	
	public Long getNumOk() {
		return numOk;
	}
	public void setNumOk(Long numOk) {
		this.numOk = numOk;
	}
	public List<String> getListaOk() {
		return listaOk;
	}
	public void setListaOk(List<String> listaOk) {
		this.listaOk = listaOk;
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
	
	
}
