package org.gestoresmadrid.oegam2comun.pegatinas.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaPegatinas;

import escrituras.beans.ResultBean;

public interface ServicioPegatinas extends Serializable{
	
	ResultBean creaSolicitudPeticionStock(int cantidad, String tipo, String jefatura, int idPeticionStock);

	ResultBean creaSolicitudBajaPeticionStock(String identificadorPeticion, String jefatura);

	ResultBean creaSolicitudRecepcionStock(String identificadorPeticion, String jefatura);

	RespuestaPegatinas generaRespuesta(ColaBean solicitud);
}
