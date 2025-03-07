package org.gestoresmadrid.oegam2comun.santanderWS.views.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.santanderWS.views.enums.ResultadoSantanderWSEnum;

public class ResultadoSantanderWS implements Serializable{
	
	private static final long serialVersionUID = 7004811909449993141L;

	private Boolean error;
	private Boolean excepcion;
	private List<String> listaBastidoresErroneos;
	private ResultadoSantanderWSEnum resultado;
	private String mensaje;
	
	public ResultadoSantanderWS() {
		this.error = false;
		this.excepcion = false;
	}

	public Boolean getError() {
		return error;
	}
	
	public void setError(Boolean error) {
		this.error = error;
	}
	
	public ResultadoSantanderWSEnum getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoSantanderWSEnum resultado) {
		this.resultado = resultado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<String> getListaBastidoresErroneos() {
		return listaBastidoresErroneos;
	}

	public void setListaBastidoresErroneos(List<String> listaBastidoresErroneos) {
		this.listaBastidoresErroneos = listaBastidoresErroneos;
	}
	
	
	public void aniadirMensajeError(String mensajeError){
		if(listaBastidoresErroneos == null || listaBastidoresErroneos.isEmpty()){
			this.listaBastidoresErroneos = new ArrayList<String>();
		}
		listaBastidoresErroneos.add(mensajeError);
	}

	public Boolean getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Boolean excepcion) {
		this.excepcion = excepcion;
	}
}
