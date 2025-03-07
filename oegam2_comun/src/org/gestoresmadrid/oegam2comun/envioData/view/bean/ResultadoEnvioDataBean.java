package org.gestoresmadrid.oegam2comun.envioData.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.envioData.view.dto.EnvioDataDto;

public class ResultadoEnvioDataBean implements Serializable{

	private static final long serialVersionUID = 9143274686303416508L;
	
	private Boolean 			error;
	private String 				mensaje;
	private List<String>		listaMensajes;
	private List<EnvioDataDto> 	listaEnvioData;

	public ResultadoEnvioDataBean(Boolean error) {
		this.error = error;
	}
	
	public void addListaMensaje(String mensaje){
		if(listaMensajes == null){
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
	public List<EnvioDataDto> getListaEnvioData() {
		return listaEnvioData;
	}
	public void setListaEnvioData(List<EnvioDataDto> listaEnvioData) {
		this.listaEnvioData = listaEnvioData;
	}

}
