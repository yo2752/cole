package org.gestoresmadrid.core.trafico.interga.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;

public interface IntervinienteIntergaDao extends GenericDao<IntervinienteIntergaVO>, Serializable {

	IntervinienteIntergaVO getIntervinienteTrafico(BigDecimal numExpediente, String nif, String numColegiado, String tipoTramiteInterga);

	IntervinienteIntergaVO getIntervinienteTraficoTipo(BigDecimal numExpediente, String tipoTramiteInterga);
}
