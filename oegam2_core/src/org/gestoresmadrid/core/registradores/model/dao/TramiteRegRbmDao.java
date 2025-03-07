package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegRbmVO;


public interface TramiteRegRbmDao extends GenericDao<TramiteRegRbmVO>, Serializable {

	TramiteRegRbmVO getTramiteRegRbm(BigDecimal idTramiteRegistro, boolean completo);

}
