package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.MedioDto;

import escrituras.beans.ResultBean;

public interface ServicioMedio extends Serializable {
	
	public static String ID_MEDIO = "idMedio";

	ResultBean guardarMedio(MedioDto medio);
}
