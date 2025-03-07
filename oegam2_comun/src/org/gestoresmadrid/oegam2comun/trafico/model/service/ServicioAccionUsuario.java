package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;

import escrituras.beans.ResultadoAccionUsuarioBean;

public interface ServicioAccionUsuario extends Serializable{

	void guardarActionBorrarDatos(String numExpediente, String accion, ResultadoAccionUsuarioBean resultado, String ipAccesso);

}
