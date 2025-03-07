package org.gestoresmadrid.core.ivtmMatriculacion.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.ConsultasIvtmVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ConsultasIvtmDao extends GenericDao<ConsultasIvtmVO>, Serializable {

	BigDecimal idPeticionMax(String numColegiado);
}
