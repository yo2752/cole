package org.gestoresmadrid.core.situacion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.situacion.model.vo.SituacionVO;

public interface SituacionDao extends Serializable, GenericDao<SituacionVO>{

	List<SituacionVO> getListaSituacion();

}
