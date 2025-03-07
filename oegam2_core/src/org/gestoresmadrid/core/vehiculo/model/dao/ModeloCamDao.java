package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.ModeloCamVO;

public interface ModeloCamDao extends GenericDao<ModeloCamVO>, Serializable {

	List<ModeloCamVO> listaModeloCam(String tipoVehiculo, Date fechaDesde, String codigoMarca, String codigoModelo);
}
