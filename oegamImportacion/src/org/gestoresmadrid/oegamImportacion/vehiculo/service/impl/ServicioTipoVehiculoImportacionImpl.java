package org.gestoresmadrid.oegamImportacion.vehiculo.service.impl;

import org.gestoresmadrid.core.vehiculo.model.dao.TipoVehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioTipoVehiculoImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoVehiculoImportacionImpl implements ServicioTipoVehiculoImportacion {

	private static final long serialVersionUID = -8835527669455757208L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoVehiculoImportacionImpl.class);

	@Autowired
	TipoVehiculoDao tipoVehiculoDao;

	@Override
	@Transactional(readOnly = true)
	public TipoVehiculoVO getTipoVehiculo(String tipoVehiculo) {
		try {
			TipoVehiculoVO tipoVehiculoVO = tipoVehiculoDao.getTipoVehiculo(tipoVehiculo);
			if (tipoVehiculoVO != null) {
				return tipoVehiculoVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo vehiculo", e);
		}
		return null;
	}
}
