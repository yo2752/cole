package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.EvolucionTramiteRegistroVO;

public interface EvolucionTramiteRegistroDao extends GenericDao<EvolucionTramiteRegistroVO>, Serializable {

	List<EvolucionTramiteRegistroVO> getTramiteRegistro(BigDecimal idTramiteRegistro);
}
