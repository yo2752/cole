package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaCamVO;

public interface MarcaCamDao extends GenericDao<MarcaCamVO>, Serializable {

	List<MarcaCamVO> listaMarcaCam(String tipoVehiculo, String codigoMarca, Date fechaDesde);
}
