package org.gestoresmadrid.core.modelos.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelos.model.vo.OficinaLiquidadoraVO;

public interface OficinaLiquidadoraDao extends GenericDao<OficinaLiquidadoraVO>, Serializable{

	List<OficinaLiquidadoraVO> getListaOficinas(String idProvinciaOficinaLiquid);

}
