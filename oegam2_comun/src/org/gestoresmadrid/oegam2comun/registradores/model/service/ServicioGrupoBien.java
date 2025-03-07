package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.GrupoBienDto;


public interface ServicioGrupoBien extends Serializable{

	public List<GrupoBienDto> getListaGrupoBien();

}
