package org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaEstadisticasImportFilterBean implements Serializable {

	private static final long serialVersionUID = -615722346478738076L;

	@FilterSimpleExpression(name = "idContrato")
	private Long idContrato;

	@FilterSimpleExpression(name = "tipo")
	private String tipo;

	@FilterSimpleExpression(name = "nombre")
	private String nombre;

	@FilterSimpleExpression(name = "origen")
	private String origen;

	@FilterSimpleExpression(name = "estado")
	private String estado;

	@FilterSimpleExpression(name = "fecha", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecha;

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}