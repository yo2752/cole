package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.vo.LcInfoEdificioAltaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcInfoEdificioAltaDao extends GenericDao<LcInfoEdificioAltaVO>, Serializable {

	LcInfoEdificioAltaVO getInfoEdificioAlta(long id);

	List<LcInfoEdificioAltaVO> getInfoEdificiosAlta(long id);

}
