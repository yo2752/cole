package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaBajaVO;

public interface TasaBajaDao extends GenericDao<TasaBajaVO>, Serializable {

	List<TasaBajaVO> obtenerTasasBajaContrato(Long idContrato, String tipoTasa, int maxResult);
}
