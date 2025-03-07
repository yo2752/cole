package org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResumenConsultaTramiteTraficoBean implements Serializable{
	
	private static final long serialVersionUID = -6058926181714658752L;
	
	private Long numOk;
	private List<String> listaOk;
	private Long numError;
	private List<String> listaErrores;
	
	public void addListaResumenOK(String mensaje){
		if(listaOk == null || listaOk.isEmpty()){
			listaOk = new ArrayList<String>();
			numOk = new Long(0);
		}
		listaOk.add(mensaje);
		numOk++;
	}
	
	public void addListaResumenError(String mensaje){
		if(listaErrores == null || listaErrores.isEmpty()){
			listaErrores = new ArrayList<String>();
			numError = new Long(0);
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
