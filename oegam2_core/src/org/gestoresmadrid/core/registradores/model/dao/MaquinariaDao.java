package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.MaquinariaRegistroVO;


public interface MaquinariaDao extends GenericDao<MaquinariaRegistroVO>, Serializable {

	public MaquinariaRegistroVO getMaquinariaRegistro(String id);
	public MaquinariaRegistroVO getMaquinariaPorPropiedad(BigDecimal id);

}
