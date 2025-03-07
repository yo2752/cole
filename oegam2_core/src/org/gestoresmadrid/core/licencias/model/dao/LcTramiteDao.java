package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.licencias.model.vo.LcTramiteVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcTramiteDao extends GenericDao<LcTramiteVO>, Serializable {

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	LcTramiteVO getTramiteLc(BigDecimal numExpediente, boolean completo);

	LcTramiteVO cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo);

	Integer getCountTramitesConIdSolicitud();
}
