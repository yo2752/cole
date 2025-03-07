package org.gestoresmadrid.core.impresion.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.impresion.model.vo.ImpresionTramiteTraficoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ImpresionTramiteTraficoDao extends GenericDao<ImpresionTramiteTraficoVO>, Serializable {

	List<BigDecimal> obtenerNumExpedientes(Long idImpresion);

	List<ImpresionTramiteTraficoVO> getImpresionesTramiteTrafPorIdImpresion(Long idImpresion);

	ImpresionTramiteTraficoVO getTramiteImpresion(BigDecimal numExpediente, String tipoImpresion);
}
