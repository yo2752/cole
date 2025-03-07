package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.PropiedadIntelectualRegistroVO;




public interface PropiedadIntelectualDao extends GenericDao<PropiedadIntelectualRegistroVO>, Serializable {

	public PropiedadIntelectualRegistroVO getPropiedadIntelectualRegistro(String id);
	public PropiedadIntelectualRegistroVO getPropiedadIntelectualPorPropiedad(BigDecimal id);

}
