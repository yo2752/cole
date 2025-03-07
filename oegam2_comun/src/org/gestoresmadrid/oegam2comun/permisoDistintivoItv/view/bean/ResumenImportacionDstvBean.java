package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResumenImportacionDstvBean implements Serializable{

	private static final long serialVersionUID = 5043370611492928698L;

	private List<String> listaOks;
	private List<String> listaKO;
	private Long numOk;
	private Long numError;
	
	public void addListaOk(String mensaje){
		if(listaOks == null || listaOks.isEmpty()){
			listaOks = new ArrayList<String>();
			numOk = new Long(0);
		}
		listaOks.add(mensaje);
		numOk++;
	}
	
	public void addListaKO(String mensaje){
		if(listaKO == null || listaKO.isEmpty()){
			listaKO = new ArrayList<String>();
			numError = new Long(0);
		}
		listaKO.add(mensaje);
		numError++;
	}
	
	public List<String> getListaOks() {
		return listaOks;
	}
	public void setListaOks(List<String> listaOks) {
		this.listaOks = listaOks;
	}
	public List<String> getListaKO() {
		return listaKO;
	}
	public void setListaKO(List<String> listaKO) {
		this.listaKO = listaKO;
	}
	public Long getNumOk() {
		return numOk;
	}
	public void setNumOk(Long numOk) {
		this.numOk = numOk;
	}
	public Long getNumError() {
		return numError;
	}
	public void setNumError(Long numError) {
		this.numError = numError;
	}
	
}
