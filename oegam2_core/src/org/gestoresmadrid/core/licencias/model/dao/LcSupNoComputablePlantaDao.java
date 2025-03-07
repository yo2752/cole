package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.vo.LcSupNoComputablePlantaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcSupNoComputablePlantaDao extends GenericDao<LcSupNoComputablePlantaVO>, Serializable {

	LcSupNoComputablePlantaVO getSupNoComputablePlanta(long id);

	List<LcSupNoComputablePlantaVO> getSupNoComputablesPlanta(long id);

}
