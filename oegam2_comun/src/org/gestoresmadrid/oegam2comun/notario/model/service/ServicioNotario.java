package org.gestoresmadrid.oegam2comun.notario.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.notario.view.dto.NotarioDto;

public interface ServicioNotario extends Serializable{

	/**
	 * metodo que devuelve el notario por su id
	 * @param codigo
	 * @return
	 */
	NotarioDto getNotarioPorId(String codigo);
	
	/**
	 * Guarda o actualiza un notario.
	 * @param notario
	 */
	NotarioDto guardarNotario(NotarioDto notario);

}
