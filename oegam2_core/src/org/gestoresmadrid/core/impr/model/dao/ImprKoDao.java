package org.gestoresmadrid.core.impr.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.impr.model.vo.ImprKoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ImprKoDao extends GenericDao<ImprKoVO>, Serializable{

	ImprKoVO getImprKoTramite(BigDecimal numExpediente, String tipoImpr);

}
