package org.gestoresmadrid.core.evolucionAtex5.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.evolucionAtex5.model.vo.EvolucionAtex5VO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EvolucionAtex5Dao extends Serializable,GenericDao<EvolucionAtex5VO>{

	List<EvolucionAtex5VO> getListaEvolucionesPorNumExp(BigDecimal numExpediente);

}
