package org.gestoresmadrid.core.conductor.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.conductor.model.vo.ConductorVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ConductorDao extends Serializable, GenericDao<ConductorVO>{

	BigDecimal generarNumExpediente(String numColegiado, String tipoOperacion) throws Exception;	
	
	ConductorVO getConductorPorExpediente (BigDecimal numExpediente, Boolean tramiteCompleto);

	ConductorVO getConductorPorId(Long idConductor, Boolean tramiteCompleto);
	
	
	
}
