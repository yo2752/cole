package com.matriculasIvtmWS.integracion.bean;

//TODO MPC. Cambio IVTM. Clase añadida.
import java.io.Serializable;

import utilidades.estructuras.Fecha;

/**
 * Datos de entrada al servicio web de obtención de matriculas a partir de un rango de fechas o a partir de unos 
 * números de autoliquidación proporcionados por el ayuntamiento de madrid.
 * @author rocio.martin
 *
 */
public class IvtmDatosEntradaMatriculasWS implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7956944630296115161L;
	protected Fecha fechaInicio;
	protected Fecha fechaFin;
	protected String[] listaAutoliquidaciones;
	
	public IvtmDatosEntradaMatriculasWS() {
		// TODO Auto-generated constructor stub
	}

	public Fecha getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Fecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Fecha getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Fecha fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String[] getListaAutoliquidaciones() {
		return listaAutoliquidaciones;
	}

	public void setListaAutoliquidaciones(String[] listaAutoliquidaciones) {
		this.listaAutoliquidaciones = listaAutoliquidaciones;
	}
	
	
}
