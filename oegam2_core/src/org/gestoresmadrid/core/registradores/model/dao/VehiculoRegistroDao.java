package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.VehiculoRegistroVO;

public interface VehiculoRegistroDao extends GenericDao<VehiculoRegistroVO>, Serializable {
	public VehiculoRegistroVO getVehiculoRegistro(String id);
	public VehiculoRegistroVO getVehiculoPorPropiedad(BigDecimal id);
}
