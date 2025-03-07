package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoJustificanteProfesionalBean implements Serializable{

	private static final long serialVersionUID = -2664511020652508752L;

	private boolean error;
	private Exception excepcion;
	private String mensajeError;
	private List<String> listaMensajes;
	private String numExpediente;
	private String respuesta;
	private String nombreXML;
	private Boolean verificado;

	public ResultadoJustificanteProfesionalBean(boolean error) {
		super();
		this.error = error;
	}

	public ResultadoJustificanteProfesionalBean(Exception excepcion) {
		super();
		this.excepcion = excepcion;
		this.error = false;
	}

	public ResultadoJustificanteProfesionalBean(boolean error, String mensajeError) {
		super();
		this.error = error;
		this.mensajeError = mensajeError;
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

	public void addMensajeLista(String mensaje){
		if(listaMensajes == null){
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
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

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNombreXML() {
		return nombreXML;
	}

	public void setNombreXML(String nombreXML) {
		this.nombreXML = nombreXML;
	}

	public Boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}

}