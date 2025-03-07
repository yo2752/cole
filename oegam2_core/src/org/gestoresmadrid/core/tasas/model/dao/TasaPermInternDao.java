package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaPermInternVO;

public interface TasaPermInternDao extends GenericDao<TasaPermInternVO>, Serializable {

	List<TasaPermInternVO> obtenerTasasPermIntContrato(Long idContrato, String tipoTasa, int maxResult);
}
