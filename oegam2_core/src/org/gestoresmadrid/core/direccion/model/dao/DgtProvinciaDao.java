package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.DgtProvinciaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface DgtProvinciaDao extends GenericDao<DgtProvinciaVO>, Serializable {

	DgtProvinciaVO getDgtProvincia(String idProvincia);
}
