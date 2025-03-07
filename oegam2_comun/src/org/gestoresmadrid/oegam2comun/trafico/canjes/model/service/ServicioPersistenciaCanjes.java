package org.gestoresmadrid.oegam2comun.trafico.canjes.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.canjes.view.dto.CanjesDto;
import org.gestoresmadrid.oegam2comun.trafico.canjes.view.beans.ResultadoCanjesBean;

public interface ServicioPersistenciaCanjes extends Serializable{
	
	ResultadoCanjesBean guardarCanje(CanjesDto canjesDto);

	String generarIdCanje() throws Exception;

	ResultadoCanjesBean modificarImpresion(CanjesDto canjesDto);

}
