package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.CriterioUtilizacionDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CriterioUtilizacionVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioCriterioUtilizacion;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoVehiculoCriterios;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.CriterioUtilizacionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioCriterioUtilizacionImpl implements ServicioCriterioUtilizacion {

	private static final long serialVersionUID = -123630876161548361L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioCriterioUtilizacionImpl.class);

	@Autowired
	private ServicioTipoVehiculoCriterios servicioTipoVehiculoCriterios;

	@Autowired
	private CriterioUtilizacionDao criterioUtilizacionDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public CriterioUtilizacionDto getCriterioUtilizacion(String criterioUtilizacion) {
		try {
			List<CriterioUtilizacionVO> listadoCriterioUtilizacion = criterioUtilizacionDao.getCriterioUtilizacion(criterioUtilizacion);
			if (listadoCriterioUtilizacion != null && !listadoCriterioUtilizacion.isEmpty()) {
				return conversor.transform(listadoCriterioUtilizacion.get(0), CriterioUtilizacionDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el criterio de utilización", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<CriterioUtilizacionDto> getListadoCriterioUtilizacion(String[] criteriosUtilizacion) {
		try {
			List<CriterioUtilizacionVO> listadoCriterioUtilizacion = criterioUtilizacionDao.getCriterioUtilizacion(criteriosUtilizacion);
			if (listadoCriterioUtilizacion != null) {
				return conversor.transform(listadoCriterioUtilizacion, CriterioUtilizacionDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar los criterios de utilización", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<CriterioUtilizacionDto> listadoCriterioUtilizacion(String tipoVehiculo) {
		try {
			List<String> listadoCriterioUtilizacion = servicioTipoVehiculoCriterios.listaCriteriosUtilizacion(tipoVehiculo);
			if (listadoCriterioUtilizacion != null && !listadoCriterioUtilizacion.isEmpty()) {
				String[] criteriosUtilizacion = new String[listadoCriterioUtilizacion.size()];
				int i = 0;
				for (String criterioUtilizacion : listadoCriterioUtilizacion) {
					if (TODOS.equals(criterioUtilizacion)) {
						return getListadoCriterioUtilizacion(null);
					} else {
						criteriosUtilizacion[i] = criterioUtilizacion;
					}
					i++;
				}
				return getListadoCriterioUtilizacion(criteriosUtilizacion);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado de criterios de utilización", e);
		}
		return null;
	}

	public ServicioTipoVehiculoCriterios getServicioTipoVehiculoCriterios() {
		return servicioTipoVehiculoCriterios;
	}

	public void setServicioTipoVehiculoCriterios(ServicioTipoVehiculoCriterios servicioTipoVehiculoCriterios) {
		this.servicioTipoVehiculoCriterios = servicioTipoVehiculoCriterios;
	}

	public CriterioUtilizacionDao getCriterioUtilizacionDao() {
		return criterioUtilizacionDao;
	}

	public void setCriterioUtilizacionDao(CriterioUtilizacionDao criterioUtilizacionDao) {
		this.criterioUtilizacionDao = criterioUtilizacionDao;
	}
}