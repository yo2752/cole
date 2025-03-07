package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.ReconocimientoDeudaVO;

public interface ReconocimientoDeudaDao extends GenericDao<ReconocimientoDeudaVO>, Serializable {

	public ReconocimientoDeudaVO getReconocimientoDeuda(String id);

}
