package org.gestoresmadrid.core.pegatinas.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasVO;

public interface PegatinasDao extends GenericDao<PegatinasVO>, Serializable{
	
	List<PegatinasVO> getAllPegatinas();
	
	PegatinasVO getPegatinaByIdPegatina(Integer idPegatina);
	
	void cambiarEstadoPegatina(String idPegatina, Integer estado, String descrEstado);
	
	PegatinasVO getPegatinaByExpediente(BigDecimal expediente);

	PegatinasVO getPegatinasByJefatura(String jefatura);

	String getNumExpedienteByMatricula(String matricula);
}
