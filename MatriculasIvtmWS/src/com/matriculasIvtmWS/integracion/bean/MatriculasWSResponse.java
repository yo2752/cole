package com.matriculasIvtmWS.integracion.bean;

import java.io.Serializable;



public class MatriculasWSResponse implements Serializable{

	private static final long serialVersionUID = 8617966394853106275L;
	
	private ResultadoWS[] listaResultados;
	private String codigoResultado;
	private String mensaje;

	public MatriculasWSResponse() {
		super();
	}

	public ResultadoWS[] getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(ResultadoWS[] listaResultados) {
		this.listaResultados = listaResultados;
	}

	public String getCodigoResultado() {
		return codigoResultado;
	}

	public void setCodigoResultado(String codigoResultado) {
		this.codigoResultado = codigoResultado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
