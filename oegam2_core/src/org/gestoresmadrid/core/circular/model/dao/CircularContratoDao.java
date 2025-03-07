package org.gestoresmadrid.core.circular.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.circular.model.vo.CircularContratoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface CircularContratoDao extends GenericDao<CircularContratoVO>, Serializable{

	CircularContratoVO getCircularContrato(Long idCircular, Long idContrato);

}
