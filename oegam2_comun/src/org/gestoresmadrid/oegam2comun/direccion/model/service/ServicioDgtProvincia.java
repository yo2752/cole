package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.direccion.view.dto.DgtProvinciaDto;

public interface ServicioDgtProvincia extends Serializable {

	DgtProvinciaDto getDgtProvincia(String idProvincia);
}