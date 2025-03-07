package org.gestoresmadrid.oegam2comun.consultasTGate.model.service.impl;

import org.gestoresmadrid.core.consultasTGate.model.dao.ConsultasTGateDao;
import org.gestoresmadrid.core.consultasTGate.model.vo.ConsultasTGateVO;
import org.gestoresmadrid.oegam2comun.consultasTGate.model.service.ServicioConsultasTGate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultasTGateImpl implements ServicioConsultasTGate {

	private static final long serialVersionUID = 3687894648027437990L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultasTGateImpl.class);

	@Autowired
	private ConsultasTGateDao consultasTGateDao;

	@Override
	@Transactional
	public void guardar(ConsultasTGateVO consultasTGateVO) {
		consultasTGateDao.guardar(consultasTGateVO);
	}

	public ConsultasTGateDao getConsultasTGateDao() {
		return consultasTGateDao;
	}

	public void setConsultasTGateDao(ConsultasTGateDao consultasTGateDao) {
		this.consultasTGateDao = consultasTGateDao;
	}
}
