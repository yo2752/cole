package org.gestoresmadrid.oegam2comun.imputaciones.views.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

import utilidades.estructuras.FechaFraccionada;

public class ImputacionHorasBean {
	
	Long idImputacion;
	
	@FilterSimpleExpression(name="usuario.idUsuario")
	Long idUsuario;
	
	Long idTipoImputacion;
	
	
	FechaFraccionada fecha;
	
	@FilterSimpleExpression(name="fechaImputacion")
	Date fechaConsulta;
	
	BigDecimal horas;
	
	String descImputacion;
	

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdTipoImputacion() {
		return idTipoImputacion;
	}

	public void setIdTipoImputacion(Long idTipoImputacion) {
		this.idTipoImputacion = idTipoImputacion;
	}


	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

	public String getDescImputacion() {
		return descImputacion;
	}

	public void setDescImputacion(String descImputacion) {
		this.descImputacion = descImputacion;
	}

	public BigDecimal getHoras() {
		return horas;
	}

	public void setHoras(BigDecimal horas) {
		this.horas = horas;
	}

	public Long getIdImputacion() {
		return idImputacion;
	}

	public void setIdImputacion(Long idImputacion) {
		this.idImputacion = idImputacion;
	}

	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
}
