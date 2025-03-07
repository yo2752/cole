package org.gestoresmadrid.core.evolucionModelo600601.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.evolucionModelo600601.model.vo.EvolucionModelo600_601VO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EvolucionModelo600601Dao extends Serializable,GenericDao<EvolucionModelo600_601VO>{

	List<EvolucionModelo600_601VO> getListaEvolucionPorNumExpediente(BigDecimal numExpediente);

}
