package org.gestoresmadrid.core.evolucionCayc.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.evolucionCayc.model.vo.EvolucionCaycVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EvolucionCaycDao extends Serializable, GenericDao<EvolucionCaycVO> {
	List<EvolucionCaycVO> getListaEvolucionPorNump(BigDecimal numExpediente);	
}
