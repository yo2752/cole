package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.OficinaLiquidadora620VO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface OficinaLiquidadora620Dao extends GenericDao<OficinaLiquidadora620VO>, Serializable {

	List<OficinaLiquidadora620VO> listadoOficinasLiquidadoras(String oficinaLiquidadora);
}
