package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaCargadaVO;

public interface TasaCargadaDao extends GenericDao<TasaCargadaVO>, Serializable {

	/**
	 * Devuelve el detalle
	 * @param codigoTasa
	 * @param formatoTasa 
	 * @return
	 */
	TasaCargadaVO detalle(String codigoTasa, Integer formatoTasa);

}
