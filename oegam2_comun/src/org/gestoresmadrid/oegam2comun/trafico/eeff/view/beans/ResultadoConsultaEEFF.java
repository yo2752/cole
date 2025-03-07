package org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoConsultaEEFF implements Serializable{

	private static final long serialVersionUID = 6358692956006428898L;
	
	public Integer numOk;
	public Integer numError;
	public List<String> listaOK;
	public List<String> listaErrores;
	public String mensaje;
	public Boolean error;
	
	public ResultadoConsultaEEFF() {
		super();
		this.error = Boolean.FALSE;
	}

	public void addOk(){
		if(numOk== null){
			numOk = 0;
		}
		numOk++;
	}
	
	public void addError(){
		if(numError== null){
			numError = 0;
		}
		numError++;
	}
	
	public void addListaOk(String mensaje){
		if(listaOK == null){
			listaOK =  new ArrayList<String>();
		}
		listaOK.add(mensaje);
	}
	
	public void addListaError(String mensaje){
		if(listaErrores == null){
			listaErrores =  new ArrayList<String>();
		}
		listaErrores.add(mensaje);
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

}
