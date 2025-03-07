package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaMatwVO;

public interface TasaMatwDao extends GenericDao<TasaMatwVO>, Serializable {

	List<TasaMatwVO> obtenerTasasMatwContrato(Long idContrato, String tipoTasa, int maxResult);
}
