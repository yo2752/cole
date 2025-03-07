package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;

public interface EvolucionTasaDao extends GenericDao<EvolucionTasaVO>, Serializable {
	List<EvolucionTasaVO> getListaEvolucionesPorTasa(String codigoTasa);

}