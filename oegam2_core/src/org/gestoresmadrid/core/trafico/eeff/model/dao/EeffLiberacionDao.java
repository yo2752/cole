package org.gestoresmadrid.core.trafico.eeff.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffLiberacionVO;

public interface EeffLiberacionDao extends GenericDao<EeffLiberacionVO>, Serializable {

	List<EeffLiberacionVO> getEeffLiberacionPorNumExpediente(BigDecimal numExpediente);

	/**
	 * Busqueda por parametros rellenados
	 * @param objeto
	 * @return
	 */
	List<EeffLiberacionVO> busquedaPorParametro(EeffLiberacionVO liberacion);
}
