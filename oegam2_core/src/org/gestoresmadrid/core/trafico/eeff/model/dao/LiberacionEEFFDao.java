package org.gestoresmadrid.core.trafico.eeff.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.eeff.model.vo.LiberacionEEFFVO;

public interface LiberacionEEFFDao extends Serializable, GenericDao<LiberacionEEFFVO>{

	LiberacionEEFFVO getLiberacionEEFFPorExpediente(BigDecimal numExpediente);

}
