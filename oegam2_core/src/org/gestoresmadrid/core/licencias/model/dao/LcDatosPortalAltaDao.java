package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.vo.LcDatosPortalAltaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcDatosPortalAltaDao extends GenericDao<LcDatosPortalAltaVO>, Serializable {

	LcDatosPortalAltaVO getDatosPortalAlta(long id);

	List<LcDatosPortalAltaVO> getPortalesAlta(long id);

}
