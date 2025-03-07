package org.gestoresmadrid.core.presentacion.jpt.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.presentacion.jpt.model.vo.EstadisticasJPTVO;

public interface EstadisticasJPTDao extends GenericDao<EstadisticasJPTVO>, Serializable {

	List<EstadisticasJPTVO> getListaEstadisticaJPT(Date fechaEstadistica, Long idTipoEstadistica, String jefaturaJpt);

	Long getCantidadTipoEstadistica(Date fechaEstadistica, Long idTipoEstadistica, String jefaturaJpt);

	int getNumColegiadosDistintos(Date fechaEstadistica, Long idTipoEstadistica, String jefaturaJpt);
}
