package org.gestoresmadrid.core.trafico.materiales.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedPaqueteVO;

public interface PedPaqueteDao extends GenericDao<PedPaqueteVO>, Serializable {

	Long getNumPedPaquetes(Long pedidoId);

}
