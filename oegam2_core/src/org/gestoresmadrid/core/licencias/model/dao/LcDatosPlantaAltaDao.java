package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.vo.LcDatosPlantaAltaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcDatosPlantaAltaDao extends GenericDao<LcDatosPlantaAltaVO>, Serializable {

	LcDatosPlantaAltaVO getDatosPlantaAlta(long id);

	List<LcDatosPlantaAltaVO> getPlantasAlta(long id);

}
