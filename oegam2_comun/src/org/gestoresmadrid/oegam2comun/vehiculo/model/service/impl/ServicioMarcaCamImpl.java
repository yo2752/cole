package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.MarcaCamDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaCamVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaCam;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMarcaCamImpl implements ServicioMarcaCam {

	private static final long serialVersionUID = 535402364156065255L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMarcaCamImpl.class);

	@Autowired
	private MarcaCamDao marcaCamDao;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public List<MarcaCamVO> listaMarcaCam(String tipoVehiculo, String codigoMarca, String fechaDesde) {
		try {
			return marcaCamDao.listaMarcaCam(tipoVehiculo, codigoMarca, utilesFecha.getFechaDate(fechaDesde));
		} catch (Exception e) {
			log.error("Error al recuperar el listado de marcas CAM", e);
		}
		return null;
	}

	public MarcaCamDao getMarcaCamDao() {
		return marcaCamDao;
	}

	public void setMarcaCamDao(MarcaCamDao marcaCamDao) {
		this.marcaCamDao = marcaCamDao;
	}
}