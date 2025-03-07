package com.matriculasIvtmWS.integracion.bean;

import java.io.Serializable;


public class MatriculasWSRequest implements Serializable{

	private static final long serialVersionUID = -4310254165401972723L;

	private TipoFecha fechaInicio;
	private TipoFecha fechaFin;
	private String[] listaAutoliquidaciones;
	private String usuario;
	private String clave;
	
	public MatriculasWSRequest() {
		super();
	}

	public TipoFecha getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(TipoFecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public TipoFecha getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaFin(TipoFecha fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String[] getListaAutoliquidaciones() {
		return listaAutoliquidaciones;
	}

	public void setListaAutoliquidaciones(String[] listaAutoliquidaciones) {
		this.listaAutoliquidaciones = listaAutoliquidaciones;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
