package com.matriculasIvtmWS.integracion.bean;

import java.io.Serializable;

public class ResultadoWS implements Serializable{

	private static final long serialVersionUID = 5354670457996552230L;
	
	private String numAutoliquidacion;
	private String matricula;
	private String bastidor;
	private String descripcion;
	
	public ResultadoWS() {
		super();
	}

	public String getNumAutoliquidacion() {
		return numAutoliquidacion;
	}
	
	public void setNumAutoliquidacion(String numAutoliquidacion) {
		this.numAutoliquidacion = numAutoliquidacion;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getBastidor() {
		return bastidor;
	}
	
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
