package org.gestoresmadrid.oegamImportacion.vehiculo.service.impl;

import org.gestoresmadrid.core.vehiculo.model.dao.MarcaDgtDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioMarcaDgtImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMarcaDgtImportacionImpl implements ServicioMarcaDgtImportacion {

	private static final long serialVersionUID = 399061843888158281L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMarcaDgtImportacionImpl.class);

	@Autowired
	MarcaDgtDao marcaDgtDao;

	@Override
	@Transactional(readOnly = true)
	public MarcaDgtVO getMarcaDgt(String codigoMarca, String marca, Boolean versionMatw) {
		try {
			MarcaDgtVO marcaDgt = marcaDgtDao.getMarcaDgt(codigoMarca, marca, versionMatw);
			if (marcaDgt != null) {
				return marcaDgt;
			}
		} catch (Exception e) {
			log.error("Error al recuperar la marca", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public String getCodMarcaPorMarcaSinEditar(String marcaSinEditar, Boolean versionMatw) {
		try {
			MarcaDgtVO marcaDgt = marcaDgtDao.getMarcaDgt(null, marcaSinEditar, versionMatw);
			if (marcaDgt != null) {
				return marcaDgt.getCodigoMarca().toString();
			}
		} catch (Exception e) {
			log.error("Error al recuperar la marca", e);
		}
		return null;
	}
}
