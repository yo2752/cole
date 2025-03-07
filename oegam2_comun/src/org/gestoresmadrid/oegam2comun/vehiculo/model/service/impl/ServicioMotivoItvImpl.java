package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.MotivoItvDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MotivoItvVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMotivoItv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMotivoItvImpl implements ServicioMotivoItv {

	private static final long serialVersionUID = 1313573034060975933L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMotivoItvImpl.class);

	@Autowired
	MotivoItvDao motivoItvDao;

	@Override
	@Transactional
	public MotivoItvVO getMotivoItv(String idMotivoItv) {
		try {
			return motivoItvDao.getMotivoItv(idMotivoItv);
		} catch (Exception e) {
			log.error("Error al recuperar el motivo ITV", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<MotivoItvVO> getListaMotivoItv() {
		return motivoItvDao.getListaMotivoItv();
	}
}