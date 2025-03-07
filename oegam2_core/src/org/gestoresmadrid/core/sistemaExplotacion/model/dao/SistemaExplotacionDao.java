package org.gestoresmadrid.core.sistemaExplotacion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.sistemaExplotacion.model.vo.SistemaExplotacionVO;

public interface SistemaExplotacionDao extends Serializable, GenericDao<SistemaExplotacionVO>{

	List<SistemaExplotacionVO> getListaSistemaExplotacion();

}
