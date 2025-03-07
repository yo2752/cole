package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionPresentacionJptVO;

public interface EvolucionPresentacionJptDao extends GenericDao<EvolucionPresentacionJptVO>, Serializable {

	List<EvolucionPresentacionJptVO> obtenerEvolucion(BigDecimal numExpediente);
}
