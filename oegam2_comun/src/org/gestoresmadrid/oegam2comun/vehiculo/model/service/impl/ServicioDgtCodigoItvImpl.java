package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import org.gestoresmadrid.core.vehiculo.model.dao.DgtCodigoItvDao;
import org.gestoresmadrid.core.vehiculo.model.vo.DgtCodigoItvVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioDgtCodigoItv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDgtCodigoItvImpl implements ServicioDgtCodigoItv {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDgtCodigoItvImpl.class);

	@Autowired
	private DgtCodigoItvDao dgtCodigoItvDao;

	@Override
	@Transactional
	public DgtCodigoItvVO obtenerDatosItv(String codigoItv) {
		DgtCodigoItvVO dgtCodigoItvVO = new DgtCodigoItvVO();
		try {
			return dgtCodigoItvVO = dgtCodigoItvDao.obtenerDatosItv(codigoItv);
		} catch (Exception e) {
			log.error("Error obteniendo los datos ITV");
		}
		return dgtCodigoItvVO;
	}
}
