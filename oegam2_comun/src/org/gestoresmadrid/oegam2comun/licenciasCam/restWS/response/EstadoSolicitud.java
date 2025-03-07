package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.CodigoError;

public class EstadoSolicitud implements Serializable {

	private static final long serialVersionUID = 692481427618057944L;

	private String numeroExpediente;

	private String idSolicitud;

	private String codigoEstado;

	private String descripcionEstado;

	private List<CodigoError> errores;

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getCodigoEstado() {
		return codigoEstado;
	}

	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public List<CodigoError> getErrores() {
		return errores;
	}

	public void setErrores(List<CodigoError> errores) {
		this.errores = errores;
	}
}
