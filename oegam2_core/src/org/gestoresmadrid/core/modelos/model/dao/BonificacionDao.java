package org.gestoresmadrid.core.modelos.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelos.model.vo.BonificacionVO;

public interface BonificacionDao extends GenericDao<BonificacionVO>,Serializable{

	BonificacionVO getBonificacionPorId(String bonificacion);

}
