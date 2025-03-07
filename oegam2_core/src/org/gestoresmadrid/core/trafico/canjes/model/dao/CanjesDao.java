package org.gestoresmadrid.core.trafico.canjes.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.canjes.model.vo.CanjesVO;

public interface CanjesDao extends GenericDao<CanjesVO>, Serializable{

	String generarIdCanje() throws Exception;

	CanjesVO buscarCanjePorId(String dninie, String numColegiado);


}
