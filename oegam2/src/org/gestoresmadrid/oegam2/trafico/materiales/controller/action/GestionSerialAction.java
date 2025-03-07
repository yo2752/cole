package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionSerialAction extends ActionBase {

	private static final long serialVersionUID = -552422519777702455L;

	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionSerialAction.class);

	private String idPerm;

	public String anadirSerial() {
		return "";
	}

	public String getIdPerm() {
		return idPerm;
	}

	public void setIdPerm(String idPerm) {
		this.idPerm = idPerm;
	}

}