package org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto;

import java.io.Serializable;
import java.util.List;

public class ResultBeanAltaIVTM implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	
	private boolean error;
	private Exception excepcion;
	private String mensajeError;
	private List<String> listaMensajes;
	private String respuesta;
	private boolean isNoTramitable;
	
	public ResultBeanAltaIVTM(){
		super();
		
	}
	
	public ResultBeanAltaIVTM(boolean error, List<String> listaMensajes) {
		super();
		this.error = error;
		this.listaMensajes = listaMensajes;
	}

	public ResultBeanAltaIVTM(boolean error, String mensajeError) {
		super();
		this.error = error;
		this.mensajeError = mensajeError;
		this.isNoTramitable = false;
	} 
	
	public ResultBeanAltaIVTM(boolean error, Exception excepcion) {
		super();
		this.error = error;
		this.excepcion = excepcion;
	}
	
	public ResultBeanAltaIVTM(boolean error, Exception excepcion,
			String mensajeError, List<String> listaMensajes) {
		super();
		this.error = error;
		this.excepcion = excepcion;
		this.mensajeError = mensajeError;
		this.listaMensajes = listaMensajes;
	}
	
	public boolean isError() {
		return error;
	}
	
	public void setError(boolean error) {
		this.error = error;
	}
	
	public Exception getExcepcion() {
		return excepcion;
	}
	
	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}
	
	public String getMensajeError() {
		return mensajeError;
	}
	
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	
	public List<String> getListaMensajes() {
		return listaMensajes;
	}
	
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public boolean isNoTramitable() {
		return isNoTramitable;
	}

	public void setNoTramitable(boolean isNoTramitable) {
		this.isNoTramitable = isNoTramitable;
	}
}
