	package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import org.gestoresmadrid.core.direccion.model.dao.DgtMunicipioDao;
import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDgtMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.DgtMunicipioDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDgtMunicipioImpl implements ServicioDgtMunicipio {

	private static final long serialVersionUID = 3032680640325806308L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDgtMunicipioImpl.class);

	@Autowired
	private DgtMunicipioDao dgtMunicipioDao;

	@Autowired
	private Conversor conversor;

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

	@Transactional
	@Override
	public DgtMunicipioDto getDgtMunicipioPorCodigoIneDto(String idProvincia, String codigoIne) {
		try {
			DgtMunicipioVO dgtMunicipioVO = dgtMunicipioDao.getDgtMunicipioPorCodigoIne(idProvincia, codigoIne);
			if (dgtMunicipioVO != null) {
				return conversor.transform(dgtMunicipioVO, DgtMunicipioDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio de la DGT por código INE", e);
		}
		return null;
	}
}
