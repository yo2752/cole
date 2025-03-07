package org.gestoresmadrid.core.bien.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.bien.model.vo.BienRusticoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface BienRusticoDao extends Serializable, GenericDao<BienRusticoVO>{

	BienRusticoVO getBienRusticoPorId(Long idBien);

}
