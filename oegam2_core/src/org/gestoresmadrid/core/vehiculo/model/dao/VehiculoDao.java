package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;

public interface VehiculoDao extends GenericDao<VehiculoVO>, Serializable {

	public VehiculoVO getVehiculo(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive, EstadoVehiculo estadoVehiculo);

	public List<Object[]> getBastidor(String bastidor, String colegiado);

	public String obtenerMatriculaPorBastidor(String bastidor);

}
