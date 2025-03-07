package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;

public interface TasaProcedureDao extends Serializable {

	/**
	 * Guardar tasa por PQ, actualizando la tasa que devuelve
	 * @param tasa
	 * @return En caso de error, devuelve el mensaje con la descripcion, null si todo va bien (ausencia de noticias, son buenas noticias)
	 * @throws TransactionalException
	 */
	String guardar(TasaVO tasa);

}
