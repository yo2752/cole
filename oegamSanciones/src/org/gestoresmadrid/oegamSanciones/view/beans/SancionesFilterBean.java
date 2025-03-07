package org.gestoresmadrid.oegamSanciones.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class SancionesFilterBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7014093651237960670L;

	@FilterSimpleExpression(name = "motivo")
	private Integer motivo;
	
	@FilterSimpleExpression(name = "estadoSancion")
	private Integer estadoSancion = null;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "nombre", restriction = FilterSimpleExpressionRestriction.LIKE_PORCENTAJE)
	private String nombre;

	@FilterSimpleExpression(name = "apellidos", restriction = FilterSimpleExpressionRestriction.LIKE_PORCENTAJE)
	private String apellidos;
	
	@FilterSimpleExpression(name = "dni")
	private String dni = null;
	
	@FilterSimpleExpression(name = "estado")
	private Integer estado = null;
	
	@FilterSimpleExpression(name = "fechaPresentacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;

	public Integer getMotivo() {
		return motivo;
	}

	public void setMotivo(Integer motivo) {
		this.motivo = motivo;
	}

	public Integer getEstadoSancion() {
		return estadoSancion;
	}

	public void setEstadoSancion(Integer estadoSancion) {
		this.estadoSancion = estadoSancion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

}
