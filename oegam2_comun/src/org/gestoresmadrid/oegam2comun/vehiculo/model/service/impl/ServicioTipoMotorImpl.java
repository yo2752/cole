package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.TipoMotorDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoMotorVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoMotor;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoMotorImpl implements ServicioTipoMotor {

	private static final long serialVersionUID = -1343993036404696975L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoMotorImpl.class);

	@Autowired
	private TipoMotorDao tipoMotorDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public TipoMotorVO getTipoMotor(String tipoMotor) {
		try {
			TipoMotorVO tipoMotorVO = tipoMotorDao.getTipoMotor(tipoMotor);
			if (tipoMotorVO != null) {
				return tipoMotorVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de motor", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoMotorVO> getListaTiposMotores() {
		List<TipoMotorVO> listaTiposMotores = tipoMotorDao.getListaTipoMotores();
		if (listaTiposMotores != null && !listaTiposMotores.isEmpty()) {
			return conversor.transform(listaTiposMotores, TipoMotorVO.class);
		}
		return Collections.emptyList();
	}

}