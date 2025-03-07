package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.CaracteristicasElectricasDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CaracteristicasElectricasVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioCaracteristicasElectricas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioCaracteristicasElectricasImpl implements ServicioCaracteristicasElectricas {

	private static final long serialVersionUID = -4839896124344322676L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioCaracteristicasElectricasImpl.class);

	@Autowired
	private CaracteristicasElectricasDao caracteristicasElectricasDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<CaracteristicasElectricasVO> getCaracteristicasElectricas(String codigoMarca, String modelo, String tipoItv, String version, String variante, String bastidor,
			String categoriaElectrica, BigDecimal consumo, BigDecimal autonomiaElectrica) {
		try {
			List<CaracteristicasElectricasVO> lista = caracteristicasElectricasDao.getCaracteristicasElectricas(codigoMarca, modelo, tipoItv, version, variante, bastidor, categoriaElectrica, consumo,
					autonomiaElectrica);
			if (lista != null && !lista.isEmpty()) {
				return lista;
			}
		} catch (Exception e) {
			log.error("Error al obtener las caracteristicas electricas", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public int numeroCoincidentes(String codigoMarca, String modelo, String tipoItv, String version, String variante, String bastidor, String categoriaElectrica, BigDecimal consumo,
			BigDecimal autonomiaElectrica) {
		try {
			List<CaracteristicasElectricasVO> lista = getCaracteristicasElectricas(codigoMarca, modelo, tipoItv, version, variante, bastidor, categoriaElectrica, consumo, autonomiaElectrica);
			if (lista != null && !lista.isEmpty()) {
				return lista.size();
			}
		} catch (Exception e) {
			log.error("Error al obtener el numero de caracteristicas electricas", e);
		}
		return 0;
	}
}
