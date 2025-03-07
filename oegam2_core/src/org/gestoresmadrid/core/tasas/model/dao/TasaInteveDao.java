package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaInteveVO;

public interface TasaInteveDao extends GenericDao<TasaInteveVO>, Serializable {

	List<TasaInteveVO> obtenerTasasInteveContrato(Long idContrato, String tipoTasa, int maxResult);
}
