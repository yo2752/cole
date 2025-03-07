package org.gestoresmadrid.core.personas.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;

public interface PersonaDao extends GenericDao<PersonaVO>, Serializable {

	PersonaVO getPersona(String nif, String numColegiado);

	List<PersonaVO> getListaNuevasPersonasPorFecha(Date fechaUltimoEnvioData);

	String obtenerCodigoMandato(String numColegiado);
}
