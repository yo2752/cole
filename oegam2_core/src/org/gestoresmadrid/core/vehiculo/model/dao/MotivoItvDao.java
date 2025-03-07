package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MotivoItvVO;

public interface MotivoItvDao extends GenericDao<MotivoItvVO>, Serializable {

	MotivoItvVO getMotivoItv(String idMotivoItv);

	List<MotivoItvVO> getListaMotivoItv();
}
