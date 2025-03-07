package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.TipoVehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.TipoVehiculoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoVehiculoImpl implements ServicioTipoVehiculo {

	private static final long serialVersionUID = -123630876161548361L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoVehiculoImpl.class);

	@Autowired
	private TipoVehiculoDao tipoVehiculoDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public TipoVehiculoVO getTipoVehiculo(String tipoVehiculo) {
		try {
			TipoVehiculoVO tipoVehiculoVO = tipoVehiculoDao.getTipoVehiculo(tipoVehiculo);
			if (tipoVehiculoVO != null) {
				return tipoVehiculoVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de vehículo", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoVehiculoDto> getListaTiposVehiculos() {
		List<TipoVehiculoVO> listaTiposVehiculos = tipoVehiculoDao.getListaTipoVehiculos();
		if (listaTiposVehiculos != null && !listaTiposVehiculos.isEmpty()) {
			return conversor.transform(listaTiposVehiculos, TipoVehiculoDto.class);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public List<TipoVehiculoVO> getTipoVehiculosPorTipoTramite(String tipoTramite) {
		return tipoVehiculoDao.getTipoVehiculosPorTipoTramite(tipoTramite);
	}

	public TipoVehiculoDao getTipoVehiculoDao() {
		return tipoVehiculoDao;
	}

	public void setTipoVehiculoDao(TipoVehiculoDao tipoVehiculoDao) {
		this.tipoVehiculoDao = tipoVehiculoDao;
	}
}