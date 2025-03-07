package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;

public interface TipoVehiculoDao extends GenericDao<TipoVehiculoVO>, Serializable {

	TipoVehiculoVO getTipoVehiculo(String tipoVehiculo);

	List<TipoVehiculoVO> getListaTipoVehiculos();

	List<TipoVehiculoVO> getTipoVehiculosPorTipoTramite(String tipoTramite);
}
