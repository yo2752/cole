package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoTramiteTraficoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoTramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculoTramiteTrafico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioVehiculoTramiteTraficoImpl implements ServicioVehiculoTramiteTrafico {

	private static final long serialVersionUID = -1903569228203800846L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioVehiculoTramiteTraficoImpl.class);

	@Autowired
	VehiculoTramiteTraficoDao vehiculoTramiteTraficoDao;

	@Override
	@Transactional(readOnly = true)
	public VehiculoTramiteTraficoVO getVehiculoTramite(BigDecimal numExpediente, Long idVehiculo) {
		try {
			VehiculoTramiteTraficoVO vehiculoTramiteTraficoVO = vehiculoTramiteTraficoDao.getVehiculoTramite(numExpediente, idVehiculo);
			if (vehiculoTramiteTraficoVO != null) {
				return vehiculoTramiteTraficoVO;
			}
		} catch (Exception e) {
			log.error("Error el obtener el vehiculo tramite trafico", e);
		}
		return null;
	}
}
