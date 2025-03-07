package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import org.gestoresmadrid.core.direccion.model.dao.DgtProvinciaDao;
import org.gestoresmadrid.core.direccion.model.vo.DgtProvinciaVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDgtProvincia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.DgtProvinciaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDgtProvinciaImpl implements ServicioDgtProvincia {

	private static final long serialVersionUID = -7408973298996783497L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDgtProvinciaImpl.class);

	@Autowired
	private DgtProvinciaDao dgtProvinciaDao;

	@Autowired
	private Conversor conversor;

	@Transactional
	@Override
	public DgtProvinciaDto getDgtProvincia(String idProvincia) {
		try {
			DgtProvinciaVO dgtProvinciaVO = dgtProvinciaDao.getDgtProvincia(idProvincia);
			if (dgtProvinciaVO != null) {
				return conversor.transform(dgtProvinciaVO, DgtProvinciaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la provincia de la DGT", e);
		}
		return null;
	}
}
