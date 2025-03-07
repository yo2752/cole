package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.AeronaveRegistroVO;


public interface AeronaveDao extends GenericDao<AeronaveRegistroVO>, Serializable {

	public AeronaveRegistroVO getAeronaveRegistro(String id);
	public AeronaveRegistroVO getAeronavePorPropiedad(BigDecimal id);

}
