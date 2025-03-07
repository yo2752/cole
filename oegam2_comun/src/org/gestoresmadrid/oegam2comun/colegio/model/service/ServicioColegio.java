package org.gestoresmadrid.oegam2comun.colegio.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;

public interface ServicioColegio extends Serializable{

	ColegioDto getColegioDto(String colegio);

}
