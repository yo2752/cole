package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.TipoVehiculoCriteriosDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoCriteriosVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoVehiculoCriterios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoVehiculoCriteriosImpl implements ServicioTipoVehiculoCriterios {

	private static final long serialVersionUID = -2999022551137923712L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoVehiculoCriteriosImpl.class);

	@Autowired
	private TipoVehiculoCriteriosDao tipoVehiculoCriteriosDao;

	@Override
	@Transactional
	public List<TipoVehiculoCriteriosVO> listaTipoVehiculoCriterios(String tipoVehiculo) {
		try {
			return tipoVehiculoCriteriosDao.listaTipoVehiculoCriterios(tipoVehiculo);
		} catch (Exception e) {
			log.error("Error al recuperar el listado de criterios por tipo de vehículo", e);
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<String> listaCriteriosConstruccion(String tipoVehiculo) {
		try {
			return tipoVehiculoCriteriosDao.listaCriteriosConstruccion(tipoVehiculo);
		} catch (Exception e) {
			log.error("Error al recuperar el listado de criterios de construccion por tipo de vehículo", e);
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<String> listaCriteriosUtilizacion(String tipoVehiculo) {
		try {
			return tipoVehiculoCriteriosDao.listaCriteriosUtilizacion(tipoVehiculo);
		} catch (Exception e) {
			log.error("Error al recuperar el listado de criterios de utilización por tipo de vehículo", e);
		}
		return null;
	}

	public TipoVehiculoCriteriosDao getTipoVehiculoCriteriosDao() {
		return tipoVehiculoCriteriosDao;
	}

	public void setTipoVehiculoCriteriosDao(TipoVehiculoCriteriosDao tipoVehiculoCriteriosDao) {
		this.tipoVehiculoCriteriosDao = tipoVehiculoCriteriosDao;
	}
}
