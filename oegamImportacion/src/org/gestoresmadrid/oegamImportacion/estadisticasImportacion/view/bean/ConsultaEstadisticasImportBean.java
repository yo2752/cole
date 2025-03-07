package org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean;

import java.io.Serializable;

import utilidades.estructuras.Fecha;

public class ConsultaEstadisticasImportBean implements Serializable {

	private static final long serialVersionUID = 2127537285902209808L;

	private Long idImportacionFich;
	private String numColegiado;
	private String tipo;
	private String nombre;
	private String origen;
	private String estado;
	private String numError;
	private String numOk;
	private Fecha fecha;

	public Long getIdImportacionFich() {
		return idImportacionFich;
	}

	public void setIdImportacionFich(Long idImportacionFich) {
		this.idImportacionFich = idImportacionFich;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getNumError() {
		return numError;
	}

	public void setNumError(String numError) {
		this.numError = numError;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public String getNumOk() {
		return numOk;
	}

	public void setNumOk(String numOk) {
		this.numOk = numOk;
	}
}