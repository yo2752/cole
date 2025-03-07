package org.gestoresmadrid.core.trafico.inteve.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;

public interface TramiteTraficoSolInteveDao extends GenericDao<TramiteTraficoSolInteveVO>, Serializable {

	TramiteTraficoSolInteveVO getTramiteSolInteve(BigDecimal numExpediente, String matricula, String bastidor, String nive);

	List<TramiteTraficoSolInteveVO> getListaSolicitudesNoFinalizadas(BigDecimal numExpediente);

	TramiteTraficoSolInteveVO getTramiteSolIntevePorId(Long idTramiteSolInteve);

	List<TramiteTraficoSolInteveVO> getListaTramitesSolInteve(BigDecimal numExpediente);

	List<TramiteTraficoSolInteveVO> getDuplicados(Long idContrato, String matricula, String bastidor, String nive);
}
