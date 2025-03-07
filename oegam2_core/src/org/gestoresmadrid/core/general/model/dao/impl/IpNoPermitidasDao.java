package org.gestoresmadrid.core.general.model.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.IpNoPermitidasVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface IpNoPermitidasDao extends GenericDao<IpNoPermitidasVO>, Serializable {

	List<IpNoPermitidasVO> getListadoIpNoPermitidas();
}
