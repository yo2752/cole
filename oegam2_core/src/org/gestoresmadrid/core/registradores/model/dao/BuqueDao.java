package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.BuqueRegistroVO;


public interface BuqueDao extends GenericDao<BuqueRegistroVO>, Serializable {

	public BuqueRegistroVO getBuqueRegistro(String id);
	public BuqueRegistroVO getBuquePorPropiedad(BigDecimal id);

}
