package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;


public interface EvolucionTramiteTraficoDao extends GenericDao<EvolucionTramiteTraficoVO>, Serializable {

	List<EvolucionTramiteTraficoVO> getTramiteTrafico(BigDecimal numExpediente);

	int getNumeroFinalizacionesConError(BigDecimal numExpediente);

	List<EvolucionTramiteTraficoVO> getTramiteFinalizadoErrorAutorizado(BigDecimal numExpediente);
}
