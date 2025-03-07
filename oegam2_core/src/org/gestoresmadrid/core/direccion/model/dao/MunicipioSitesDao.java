package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.MunicipioSitesVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface MunicipioSitesDao extends GenericDao<MunicipioSitesVO>, Serializable {

	MunicipioSitesVO getMunicipioSites(String idMunicipio, String idProvincia);
}
