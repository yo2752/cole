package org.gestoresmadrid.core.arrendatarios.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.arrendatarios.model.vo.ArrendatarioVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ArrendatarioDao extends Serializable, GenericDao<ArrendatarioVO> {

	BigDecimal generarNumExpediente(String numColegiado, String tipoOperacion) throws Exception;

	ArrendatarioVO getArrendatarioPorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto);
	
	ArrendatarioVO getArrendatarioPorId(Long idArrendatario, Boolean tramiteCompleto);
	
	
	
	
	
}
