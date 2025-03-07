package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoCriteriosVO;

public interface TipoVehiculoCriteriosDao extends GenericDao<TipoVehiculoCriteriosVO>, Serializable {

	List<TipoVehiculoCriteriosVO> listaTipoVehiculoCriterios(String tipoVehiculo);

	List<String> listaCriteriosConstruccion(String tipoVehiculo);

	List<String> listaCriteriosUtilizacion(String tipoVehiculo);
}
