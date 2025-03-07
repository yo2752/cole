package org.gestoresmadrid.core.modelos.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;

public interface ModeloDao extends GenericDao<ModeloVO>, Serializable{

	ModeloVO getModeloPorId(String modelo, String version);

}
