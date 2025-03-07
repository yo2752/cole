package org.gestoresmadrid.oegamDocBase.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResumenDocBaseBean implements Serializable{

	private static final long serialVersionUID = 7399535960011532196L;
	
	private List<String> listaOk;
	private Long numOk;
	private List<String> listaError;
	private Long numError;

	public void addListaOk(String mensaje){
		if(listaOk == null){
			listaOk = new ArrayList<String>(); 
		}
		listaOk.add(mensaje);
		addNumOk();
	}
	
	public void addNumOk(){
		if(numOk == null){
			numOk = new Long(0);
		}
		numOk++;
	}
	
	public void addListaError(String mensaje){
		if(listaError == null){
			listaError = new ArrayList<String>(); 
		}
		listaError.add(mensaje);
		addNumError();
	}
	
	public void addNumError(){
		if(numError == null){
			numError = new Long(0);
		}
		numError++;
	}
	
	public List<String> getListaOk() {
		return listaOk;
	}
	public void setListaOk(List<String> listaOk) {
		this.listaOk = listaOk;
	}
	public Long getNumOk() {
		return numOk;
	}
	public void setNumOk(Long numOk) {
		this.numOk = numOk;
	}
	public List<String> getListaError() {
		return listaError;
	}
	public void setListaError(List<String> listaError) {
		this.listaError = listaError;
	}
	public Long getNumError() {
		return numError;
	}
	public void setNumError(Long numError) {
		this.numError = numError;
	}

}
