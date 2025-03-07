package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.vo.CaracteristicasElectricasVO;

public interface ServicioCaracteristicasElectricas extends Serializable {

	List<CaracteristicasElectricasVO> getCaracteristicasElectricas(String codigoMarca, String modelo, String tipoItv, String version, String variante, String bastidor, String categoriaElectrica,
			BigDecimal consumo, BigDecimal autonomiaElectrica);

	int numeroCoincidentes(String codigoMarca, String modelo, String tipoItv, String version, String variante, String bastidor, String categoriaElectrica, BigDecimal consumo,
			BigDecimal autonomiaElectrica);
}
