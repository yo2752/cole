package org.gestoresmadrid.core.presentacion.jpt.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.presentacion.jpt.model.vo.TipoEstadisticasJPTVO;

public interface TipoEstadisticasJPTDao extends GenericDao<TipoEstadisticasJPTVO>, Serializable {

	List<TipoEstadisticasJPTVO> getListaTipoEstadisticas(boolean visible);

	List<TipoEstadisticasJPTVO> getListaIncidenciasEstadisticasJpt();

	List<TipoEstadisticasJPTVO> getListaTipoEstadisticasFichero();
}
