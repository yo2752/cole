package org.gestoresmadrid.oegamImportacion.direccion.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.direccion.model.dao.DgtMunicipioDao;
import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioDgtMunicipioImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDgtMunicipioImportacionImpl implements ServicioDgtMunicipioImportacion {

	private static final long serialVersionUID = 1758610221320181552L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDgtMunicipioImportacionImpl.class);

	@Autowired
	private DgtMunicipioDao dgtMunicipioDao;

	@Transactional
	@Override
	public DgtMunicipioVO getDgtMunicipio(String idProvincia, String municipio) {
		try {
			DgtMunicipioVO dgtMunicipioVO = dgtMunicipioDao.getDgtMunicipio(idProvincia, municipio);
			if (dgtMunicipioVO != null) {
				return dgtMunicipioVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio de la DGT", e);
		}
		return null;
	}
	
	@Transactional
	@Override
	public DgtMunicipioVO getDgtMunicipioPorIdDgt(String idProvincia, BigDecimal idDgtMunicipio) {
		try {
			DgtMunicipioVO dgtMunicipioVO = dgtMunicipioDao.getDgtMunicipioPorIdDgt(idProvincia, idDgtMunicipio);
			if (dgtMunicipioVO != null) {
				return dgtMunicipioVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio de la DGT", e);
		}
		return null;
	}

	@Transactional
	@Override
	public DgtMunicipioVO getDgtMunicipioPorCodigoIne(String idProvincia, String codigoIne) {
		try {
			DgtMunicipioVO dgtMunicipioVO = dgtMunicipioDao.getDgtMunicipioPorCodigoIne(idProvincia, codigoIne);
			if (dgtMunicipioVO != null) {
				return dgtMunicipioVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio de la DGT por código INE", e);
		}
		return null;
	}

}
