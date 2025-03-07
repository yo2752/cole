package org.gestoresmadrid.core.transporte.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.transporte.model.vo.PoderTransporteVO;

public interface PoderTransporteDao extends Serializable, GenericDao<PoderTransporteVO>{

	PoderTransporteVO getPoderPorId(Long idPoderTransporte);

}
