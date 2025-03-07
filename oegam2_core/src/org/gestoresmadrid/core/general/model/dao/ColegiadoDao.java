package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ColegiadoDao extends GenericDao<ColegiadoVO>, Serializable {

	ColegiadoVO getColegiado(String numColegiado);

	ColegiadoVO getColegiadoPorUsuario(BigDecimal idUsario);
}
