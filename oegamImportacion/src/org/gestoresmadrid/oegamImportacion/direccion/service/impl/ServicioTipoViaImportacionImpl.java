package org.gestoresmadrid.oegamImportacion.direccion.service.impl;

import org.gestoresmadrid.core.direccion.model.dao.TipoViaDao;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioTipoViaImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioViaImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoViaImportacionImpl implements ServicioTipoViaImportacion {

	private static final long serialVersionUID = 8659895994195033153L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoViaImportacionImpl.class);

	@Autowired
	ServicioViaImportacion servicioVia;

	@Autowired
	TipoViaDao tipoViaDao;

	@Transactional
	@Override
	public TipoViaVO getTipoVia(String idTipoVia) {
		try {
			TipoViaVO tipoViaVO = tipoViaDao.getTipoVia(idTipoVia);
			if (tipoViaVO != null) {
				return tipoViaVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de via", e);
		}
		return null;
	}

	@Transactional
	@Override
	public TipoViaVO getTipoViaDgt(String idTipoViaDgt) {
		try {
			TipoViaVO tipoViaVO = tipoViaDao.getTipoViaDgt(idTipoViaDgt);
			if (tipoViaVO != null) {
				return tipoViaVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de via", e);
		}
		return null;
	}

	@Transactional
	@Override
	public TipoViaVO getIdTipoViaPorDesc(String descTipoVia) {
		try {
			TipoViaVO tipoViaVO = tipoViaDao.getIdTipoVia(descTipoVia);
			if (tipoViaVO != null) {
				return tipoViaVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de via", e);
		}
		return null;
	}

}
