package org.gestoresmadrid.core.modelo600_601.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelo600_601.model.vo.ResultadoModelo600601VO;

public interface ResultadoModelo600601Dao extends GenericDao<ResultadoModelo600601VO>, Serializable{
	
	void insertResultado(ResultadoModelo600601VO resultado);
	
	List<ResultadoModelo600601VO> getResultadoModeloByIdModelo600601 (Long idModelo);
	
	Date getFechaPresentacion (Long idModelo, String justificante);

	ResultadoModelo600601VO getResultadoModeloPorId(Integer idResultadoModelo600601);

}
