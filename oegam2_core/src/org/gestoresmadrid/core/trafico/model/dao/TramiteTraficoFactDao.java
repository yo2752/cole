package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafFacturacionVO;

public interface TramiteTraficoFactDao extends GenericDao<TramiteTrafFacturacionVO>, Serializable {

	TramiteTrafFacturacionVO getTramiteTraficoFactura(BigDecimal numExpediente, String codigoTasa);

	TramiteTrafFacturacionVO getTramiteTraficoFact(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafFacturacionVO getNifFacturacionPorNumExp(String numeroExpediente);
}
