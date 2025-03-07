package org.oegam.gestor.distintivos.service;

import java.io.IOException;
import java.io.Serializable;

import org.oegam.gestor.distintivos.integracion.bean.FicheroBean;

import utilidades.web.OegamExcepcion;

public interface ServicioCorreo extends Serializable {

	public static final String PROPERTY_URL_CORREO = "gestorcorreo.url";

	void enviarCorreo(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia,
			String direccionesOcultas, String origen, FicheroBean... adjuntos) throws OegamExcepcion, IOException;
}