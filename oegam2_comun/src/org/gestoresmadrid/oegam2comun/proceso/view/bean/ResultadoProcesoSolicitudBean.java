package org.gestoresmadrid.oegam2comun.proceso.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.model.beans.ColaBean;

public class ResultadoProcesoSolicitudBean implements Serializable{

	private static final long serialVersionUID = -8504514680859484156L;

	private String mensaje;
	private Boolean existeSolicitud;
	private Boolean error;
	private ColaBean colaBean;

	public ResultadoProcesoSolicitudBean(Boolean error) {
		this.error = error;
		this.existeSolicitud = Boolean.TRUE;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public ColaBean getColaBean() {
		return colaBean;
	}
	public void setColaBean(ColaBean colaBean) {
		this.colaBean = colaBean;
	}
	public Boolean getExisteSolicitud() {
		return existeSolicitud;
	}
	public void setExisteSolicitud(Boolean existeSolicitud) {
		this.existeSolicitud = existeSolicitud;
	}

}