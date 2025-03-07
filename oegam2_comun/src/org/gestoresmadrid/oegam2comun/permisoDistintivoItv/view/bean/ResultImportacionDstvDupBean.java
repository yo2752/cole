package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultImportacionDstvDupBean implements Serializable{

	private static final long serialVersionUID = -732599936974492421L;
	
	private Boolean error;
	private String mensaje;
	private List<String> listaMensajes;
	private ResumenImportacionDstvBean resumen;
	private List<DuplicadoDstvBean> listaDuplicados;
	
	public ResultImportacionDstvDupBean(Boolean error) {
		super();
		this.error = error;
	}

	public void addResumenOK(String mensaje){
		if(resumen == null){
			resumen = new ResumenImportacionDstvBean();
		}
		resumen.addListaOk(mensaje);
	}
	
	public void addResumenKO(String mensaje){
		if(resumen == null){
			resumen = new ResumenImportacionDstvBean();
		}
		resumen.addListaKO(mensaje);
	}
	
	public void addListaDuplicados(DuplicadoDstvBean duplicado){
		if(listaDuplicados == null || listaDuplicados.isEmpty()){
			listaDuplicados = new ArrayList<DuplicadoDstvBean>();
		}
		listaDuplicados.add(duplicado);
	}
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public List<String> getListaMensajes() {
		return listaMensajes;
	}
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}


	public ResumenImportacionDstvBean getResumen() {
		return resumen;
	}


	public void setResumen(ResumenImportacionDstvBean resumen) {
		this.resumen = resumen;
	}


	public List<DuplicadoDstvBean> getListaDuplicados() {
		return listaDuplicados;
	}


	public void setListaDuplicados(List<DuplicadoDstvBean> listaDuplicados) {
		this.listaDuplicados = listaDuplicados;
	}
	
}
