package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.OtrosBienesRegistroVO;


public interface OtrosBienesDao extends GenericDao<OtrosBienesRegistroVO>, Serializable {

	public OtrosBienesRegistroVO getOtrosBienesRegistro(String id);
	public OtrosBienesRegistroVO getOtrosBienesPorPropiedad(BigDecimal id);

}
