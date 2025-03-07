package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.PropiedadIndustrialRegistroVO;




public interface PropiedadIndustrialDao extends GenericDao<PropiedadIndustrialRegistroVO>, Serializable {

	public PropiedadIndustrialRegistroVO getPropiedadIndustrialRegistro(String id);
	public PropiedadIndustrialRegistroVO getPropiedadIndustrialPorPropiedad(BigDecimal id);

}
