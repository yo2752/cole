package org.gestoresmadrid.oegam2comun.notificacion.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaNotificacionBean implements Serializable{

	private static final long serialVersionUID = 5941195694223377493L;

	@FilterSimpleExpression(name = "estado")
	private Integer estado;
	
	@FilterSimpleExpression(name = "id.codigo")
	private Integer codigo;

	@FilterSimpleExpression(name = "fechaPuestaDisposicion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPuestaDisposicion;

	@FilterSimpleExpression(name = "fechaFinDisponibilidad", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaFinDisponibilidad;

	@FilterSimpleExpression(name = "destinatario")
	private String destinatario;

	@FilterSimpleExpression(name = "descripcionTipoDestinatario")
	private String descripcionTipoDestinatario;

	@FilterSimpleExpression(name = "rol")
	private Integer rol;
	
	@FilterSimpleExpression(name = "id.numColegiado")
	private String numColegiado;

	public Integer getEstado() {
		return estado;
	}
	
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public FechaFraccionada getFechaPuestaDisposicion() {
		return fechaPuestaDisposicion;
	}

	public void setFechaPuestaDisposicion(
			FechaFraccionada fechaPuestaDisposicion) {
		this.fechaPuestaDisposicion = fechaPuestaDisposicion;
	}

	public FechaFraccionada getFechaFinDisponibilidad() {
		return fechaFinDisponibilidad;
	}

	public void setFechaFinDisponibilidad(
			FechaFraccionada fechaFinDisponibilidad) {
		this.fechaFinDisponibilidad = fechaFinDisponibilidad;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getDescripcionTipoDestinatario() {
		return descripcionTipoDestinatario;
	}

	public void setDescripcionTipoDestinatario(
			String descripcionTipoDestinatario) {
		this.descripcionTipoDestinatario = descripcionTipoDestinatario;
	}

	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = rol;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	
	

}