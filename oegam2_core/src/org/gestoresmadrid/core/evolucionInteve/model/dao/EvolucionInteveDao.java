package org.gestoresmadrid.core.evolucionInteve.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.evolucionInteve.model.vo.EvolucionInteveVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EvolucionInteveDao extends Serializable, GenericDao<EvolucionInteveVO> {

	List<EvolucionInteveVO> getListaEvolucionesPorNumExp(BigDecimal numExpediente);

}