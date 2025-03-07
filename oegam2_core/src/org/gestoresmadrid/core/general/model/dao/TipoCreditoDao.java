package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface TipoCreditoDao extends GenericDao<TipoCreditoVO>, Serializable {

	TipoCreditoVO getTipoCredito(String tipoCredito);

	List<TipoCreditoVO> getTiposCreditos();

	List<TipoCreditoVO> getTiposCreditosIncrementales();
}
