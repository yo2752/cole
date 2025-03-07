package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.OtroImporteVO;

public interface OtroImporteDao extends GenericDao<OtroImporteVO>, Serializable {
	
	public OtroImporteVO getOtroImporte(String id);
}
