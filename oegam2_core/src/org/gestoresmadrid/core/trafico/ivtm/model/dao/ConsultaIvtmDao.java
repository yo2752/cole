package org.gestoresmadrid.core.trafico.ivtm.model.dao;


import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.ConsultasIvtmVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ConsultaIvtmDao extends GenericDao<ConsultasIvtmVO>, Serializable {

	BigDecimal idPeticionMax(String numColegiado);
}
