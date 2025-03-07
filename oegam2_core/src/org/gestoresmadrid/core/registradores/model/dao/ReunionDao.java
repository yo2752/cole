package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.ReunionVO;

public interface ReunionDao extends GenericDao<ReunionVO>, Serializable {

	ReunionVO getReunion(BigDecimal idTramiteRegistro);
}
