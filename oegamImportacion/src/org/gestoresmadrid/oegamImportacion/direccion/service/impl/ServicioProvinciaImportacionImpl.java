package org.gestoresmadrid.oegamImportacion.direccion.service.impl;

import org.gestoresmadrid.core.direccion.model.dao.ProvinciaDao;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioProvinciaImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioProvinciaImportacionImpl implements ServicioProvinciaImportacion {

	private static final long serialVersionUID = -2089093823416352290L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioProvinciaImportacionImpl.class);

	@Autowired
	private ProvinciaDao provinciaDao;

	@Transactional
	@Override
	public ProvinciaVO getProvincia(String idProvincia) {
		try {
			ProvinciaVO provinciaVO = provinciaDao.getProvincia(idProvincia);
			if (provinciaVO != null) {
				return provinciaVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar la provincia", e);
		}
		return null;
	}
	
	@Transactional
	@Override
	public ProvinciaVO getProvinciaPorNombre(String nombre) {
		try {
			ProvinciaVO provinciaVO = provinciaDao.getProvinciaPorNombre(nombre);
			if (provinciaVO != null) {
				return provinciaVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar la provincia", e);
		}
		return null;
	}

}