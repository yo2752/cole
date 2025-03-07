package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.DatoFirmaVO;




public interface DatoFirmaDao extends GenericDao<DatoFirmaVO>, Serializable {
	
	public DatoFirmaVO getDatoFirma(String id);
	
}
