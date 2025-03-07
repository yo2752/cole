package org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoEstadisticaImportacionBean implements Serializable{

	private static final long serialVersionUID = -8392209898349631190L;
	
	private Boolean error;
	private String mensaje;
	private List<String> listaMensajes;
	
	public ResultadoEstadisticaImportacionBean(Boolean error) {
		super();
		this.error = error;
	}
	
	public void addListaMensaje(String mensaje) {
		if(listaMensajes == null || listaMensajes.isEmpty()) {
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
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
	
}
