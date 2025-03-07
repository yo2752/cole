package org.gestoresmadrid.oegam2comun.administracion.service;

import java.io.Serializable;

public interface ServicioRecargaStatusOegam extends Serializable {

	public static final String PROPERTY_FRONTALES_ACTIVOS = "frontales.activos.ip";
	public static final String URL_RECARGA_STATUS_OEGAM = "/recargaStatusOegamServlet";

	void refrescarStatus();
}
