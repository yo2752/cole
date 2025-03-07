package org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;

import escrituras.beans.ResultBean;

public interface ServicioWebServiceVehiculosPrematriculados extends Serializable {
	
	public static String RECUPERABLE = "RECUPERABLE";

	ResultBean tramitarPeticion(ColaDto solicitud);
}
