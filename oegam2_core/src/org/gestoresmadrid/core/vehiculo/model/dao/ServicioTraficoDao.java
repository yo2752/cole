package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.ServicioTraficoVO;

public interface ServicioTraficoDao extends GenericDao<ServicioTraficoVO>, Serializable {

	ServicioTraficoVO getServicioTrafico(String idServicio);

	List<ServicioTraficoVO> getServicioTraficoPorTipoTramite(String tipoTramite);
}
