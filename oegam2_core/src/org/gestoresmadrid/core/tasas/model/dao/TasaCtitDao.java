package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaCtitVO;

public interface TasaCtitDao extends GenericDao<TasaCtitVO>, Serializable {

	List<TasaCtitVO> obtenerTasasCtitContrato(Long idContrato, String tipoTasa, int maxResult);
}
