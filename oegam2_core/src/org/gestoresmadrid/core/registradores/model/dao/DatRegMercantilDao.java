package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.DatRegMercantilVO;


public interface DatRegMercantilDao extends GenericDao<DatRegMercantilVO>, Serializable {
	
	public DatRegMercantilVO getDatRegMercantil(String id);

}
