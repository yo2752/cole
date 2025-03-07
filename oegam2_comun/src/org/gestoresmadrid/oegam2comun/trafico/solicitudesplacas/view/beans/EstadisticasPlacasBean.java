package org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans;

import utilidades.estructuras.FechaFraccionada;

public class EstadisticasPlacasBean {

	private FechaFraccionada fecha;
	private boolean agrTipoVehiculo;
	private boolean agrTipoCredito;
	private boolean numColegiado;
	
	public FechaFraccionada getFecha() {
		return fecha;
	}
	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}
	public boolean isAgrTipoVehiculo() {
		return agrTipoVehiculo;
	}
	public void setAgrTipoVehiculo(boolean agrTipoVehiculo) {
		this.agrTipoVehiculo = agrTipoVehiculo;
	}
	public boolean isAgrTipoCredito() {
		return agrTipoCredito;
	}
	public void setAgrTipoCredito(boolean agrTipoCredito) {
		this.agrTipoCredito = agrTipoCredito;
	}
	public boolean isNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(boolean numColegiado) {
		this.numColegiado = numColegiado;
	}
	
}
