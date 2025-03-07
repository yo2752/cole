package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.MunicipioSitesVO;

public interface ServicioMunicipioSites extends Serializable {
	public MunicipioSitesVO getMunicipio(String idMunicipio, String idProvincia);
}
