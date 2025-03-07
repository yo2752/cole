package com.matriculasIvtmWS.integracion.bean;

import java.io.Serializable;

public class TipoFecha implements Serializable{

	private static final long serialVersionUID = 7186497031419097098L;
	
	private Integer dia;
	private Integer mes;
	private Integer anio;
	
	public TipoFecha() {
		super();
	}

	public TipoFecha(Integer dia,Integer mes,Integer anio) {
		this.dia=dia;
		this.mes=mes;
		this.anio=anio;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	
}
