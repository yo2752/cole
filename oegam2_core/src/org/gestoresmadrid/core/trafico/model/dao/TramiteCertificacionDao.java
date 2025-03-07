package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteCertificacionVO;

public interface TramiteCertificacionDao extends GenericDao<TramiteCertificacionVO>, Serializable {

	String getCertificacion(BigDecimal numExpediente, String hash, String version);

}