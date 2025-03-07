package org.gestoresmadrid.oegam2comun.model.service;

import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;

public interface ServicioPersistenciaEntidadBancaria {

	/**
	 * Devuelve el listado de todas las entidades bancarias almacenadas en BBDD
	 * @return
	 */
	List<DatoMaestroBean> listAll();

}
