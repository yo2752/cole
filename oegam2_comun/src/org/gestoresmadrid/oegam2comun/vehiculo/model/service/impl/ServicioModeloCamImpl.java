package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.ModeloCamDao;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioModeloCam;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.ModeloCamDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioModeloCamImpl implements ServicioModeloCam {

	private static final long serialVersionUID = -1071483061595517648L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioModeloCamImpl.class);

	@Autowired
	private ModeloCamDao modeloCamDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public List<ModeloCamDto> listaModeloCam(String tipoVehiculo, String fechaDesde, String codigoMarca, String codigoModelo) {
		try {
			return conversor.transform(modeloCamDao.listaModeloCam(tipoVehiculo, utilesFecha.getFechaDate(fechaDesde), codigoMarca, codigoModelo), ModeloCamDto.class);
		} catch (Exception e) {
			log.error("Error al recuperar el listado de modelos CAM", e);
		}
		return null;
	}

	public ModeloCamDao getModeloCamDao() {
		return modeloCamDao;
	}

	public void setModeloCamDao(ModeloCamDao modeloCamDao) {
		this.modeloCamDao = modeloCamDao;
	}
}