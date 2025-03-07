package org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean;

import java.io.Serializable;

import utilidades.estructuras.Fecha;

public class EstadisticaImportacionBean implements Serializable{

	private static final long serialVersionUID = -390156563874952433L;

	private String tipo;
	private String tipoError;
	private String descContrato;
	private Fecha fecha;
	private String numOk;
	private String numError;


	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescContrato() {
		return descContrato;
	}

	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public String getTipoError() {
		return tipoError;
	}

	public void setTipoError(String tipoError) {
		this.tipoError = tipoError;
	}

	public String getNumOk() {
		return numOk;
	}

	public void setNumOk(String numOk) {
		this.numOk = numOk;
	}

	public String getNumError() {
		return numError;
	}

	public void setNumError(String numError) {
		this.numError = numError;
	}
}
