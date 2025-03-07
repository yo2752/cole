package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaDuplicadoVO;

public interface TasaDuplicadoDao extends GenericDao<TasaDuplicadoVO>, Serializable {

	List<TasaDuplicadoVO> obtenerTasasDuplicadoContrato(Long idContrato, String tipoTasa, int maxResult);
	
}
