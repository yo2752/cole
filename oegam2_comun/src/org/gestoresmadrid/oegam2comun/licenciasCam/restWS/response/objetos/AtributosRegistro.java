package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class AtributosRegistro implements Serializable {

	private static final long serialVersionUID = 2398100705013932188L;

	private String anotacion;

	private String codInteresado;

	private String fechaCreacion;

	private String horaCreacion;

	public String getAnotacion() {
		return anotacion;
	}

	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}

	public String getCodInteresado() {
		return codInteresado;
	}

	public void setCodInteresado(String codInteresado) {
		this.codInteresado = codInteresado;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getHoraCreacion() {
		return horaCreacion;
	}

	public void setHoraCreacion(String horaCreacion) {
		this.horaCreacion = horaCreacion;
	}
}
