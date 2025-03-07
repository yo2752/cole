package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CaracteristicasElectricasVO;

public interface CaracteristicasElectricasDao extends GenericDao<CaracteristicasElectricasVO>, Serializable {

	List<CaracteristicasElectricasVO> getCaracteristicasElectricas(String codigoMarca, String modelo, String tipoItv, String version, String variante, String bastidor, String categoriaElectrica,
			BigDecimal consumo, BigDecimal autonomiaElectrica);
}
