package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.licencias.model.vo.LcInfoEdificioBajaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcInfoEdificioBajaDao extends GenericDao<LcInfoEdificioBajaVO>, Serializable {

	LcInfoEdificioBajaVO getInfoEdificioBaja(long id);

}
