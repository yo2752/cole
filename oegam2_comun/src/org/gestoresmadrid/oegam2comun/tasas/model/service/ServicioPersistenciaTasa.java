package org.gestoresmadrid.oegam2comun.tasas.model.service;

import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;

public interface ServicioPersistenciaTasa {

	/**
	 * Guarda una tasa
	 * @param tasa
	 * @return RespuestaTasas
	 * @throws TransactionalException
	 */
	RespuestaTasas guardarTasa(Tasa tasa);
	
}
