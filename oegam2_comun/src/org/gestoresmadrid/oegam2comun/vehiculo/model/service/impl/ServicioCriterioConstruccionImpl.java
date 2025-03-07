package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.vehiculo.model.dao.CriterioConstruccionDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CriterioConstruccionVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioCriterioConstruccion;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoVehiculoCriterios;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.CriterioConstruccionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioCriterioConstruccionImpl implements ServicioCriterioConstruccion {

	private static final long serialVersionUID = 8080551535175054815L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioCriterioConstruccionImpl.class);

	@Autowired
	private ServicioTipoVehiculoCriterios servicioTipoVehiculoCriterios;

	@Autowired
	private CriterioConstruccionDao criterioConstruccionDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public CriterioConstruccionDto getCriterioConstruccion(String criterioConstruccion) {
		try {
			List<CriterioConstruccionVO> listadoCriterioConstruccion = criterioConstruccionDao.getCriterioConstruccion(criterioConstruccion);
			if (listadoCriterioConstruccion != null && !listadoCriterioConstruccion.isEmpty()) {
				return conversor.transform(listadoCriterioConstruccion.get(0), CriterioConstruccionDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el criterio de construcción", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<CriterioConstruccionDto> getListadoCriterioConstruccion(String[] criteriosConstruccion) {
		try {
			List<CriterioConstruccionVO> listadoCriterioConstruccion = criterioConstruccionDao.getCriterioConstruccion(criteriosConstruccion);
			if (listadoCriterioConstruccion != null) {
				return conversor.transform(listadoCriterioConstruccion, CriterioConstruccionDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar los criterios de construcción", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<CriterioConstruccionDto> listadoCriterioConstruccion(String tipoVehiculo) {
		try {
			List<String> listadoCriterioConstruccion = servicioTipoVehiculoCriterios.listaCriteriosConstruccion(tipoVehiculo);
			if (listadoCriterioConstruccion != null && !listadoCriterioConstruccion.isEmpty()) {
				String[] criteriosConstruccion = new String[listadoCriterioConstruccion.size()];
				int i = 0;
				for (String criterioConstruccion : listadoCriterioConstruccion) {
					if (TODOS.equals(criterioConstruccion)) {
						return getListadoCriterioConstruccion(null);
					} else {
						criteriosConstruccion[i] = criterioConstruccion;
					}
					i++;
				}
				return getListadoCriterioConstruccion(criteriosConstruccion);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado de criterios de construcción", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Boolean esVehiculoEspecialCriterioConstruccion(String idCriterioConstruccion) {
		try {
			if (StringUtils.isNotBlank(idCriterioConstruccion)) {
				CriterioConstruccionVO criterioConstruccion = criterioConstruccionDao.getCriteriosConstruccion(idCriterioConstruccion);
				if (criterioConstruccion != null && criterioConstruccion.getClaveMatriculacion() != null && "ESPE".equals(criterioConstruccion.getClaveMatriculacion().getClave())) {
					return Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar la clave de matw para los vehiculos especiales, error: ", e);
		}
		return Boolean.FALSE;
	}

	public ServicioTipoVehiculoCriterios getServicioTipoVehiculoCriterios() {
		return servicioTipoVehiculoCriterios;
	}

	public void setServicioTipoVehiculoCriterios(ServicioTipoVehiculoCriterios servicioTipoVehiculoCriterios) {
		this.servicioTipoVehiculoCriterios = servicioTipoVehiculoCriterios;
	}

	public CriterioConstruccionDao getCriterioConstruccionDao() {
		return criterioConstruccionDao;
	}

	public void setCriterioConstruccionDao(CriterioConstruccionDao criterioConstruccionDao) {
		this.criterioConstruccionDao = criterioConstruccionDao;
	}
}