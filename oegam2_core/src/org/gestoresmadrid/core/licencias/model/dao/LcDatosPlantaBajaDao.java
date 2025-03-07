package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.vo.LcDatosPlantaBajaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcDatosPlantaBajaDao extends GenericDao<LcDatosPlantaBajaVO>, Serializable {

	LcDatosPlantaBajaVO getDatosPlantaBaja(long id);

	List<LcDatosPlantaBajaVO> getPlantasBaja(long id);

}
