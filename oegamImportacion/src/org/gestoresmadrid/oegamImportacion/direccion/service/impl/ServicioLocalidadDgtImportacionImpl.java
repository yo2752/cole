package org.gestoresmadrid.oegamImportacion.direccion.service.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.LocalidadDgtDao;
import org.gestoresmadrid.core.direccion.model.vo.LocalidadDgtVO;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioLocalidadDgtImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLocalidadDgtImportacionImpl implements ServicioLocalidadDgtImportacion {

	private static final long serialVersionUID = -3083139473056683420L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLocalidadDgtImportacionImpl.class);

	@Autowired
	private LocalidadDgtDao localidadDgtDao;

	@Override
	@Transactional
	public List<LocalidadDgtVO> getLocalidades(String localidad, String municipio) {
		try {
			return localidadDgtDao.getLocalidades(localidad, municipio);
		} catch (Exception e) {
			log.error("Error al recuperar las localidades (DGT)", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<LocalidadDgtVO> getLocalidadesPorCodigoPostal(String codigoPostal, String localidad) {
		try {
			return localidadDgtDao.getLocalidadesPorCodigoPostal(codigoPostal, localidad);
		} catch (Exception e) {
			log.error("Error al recuperar las localidades (DGT)", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<String> listaLocalidades(String codigoIne) {
		try {
			return localidadDgtDao.listaLocalidades(codigoIne);
		} catch (Exception e) {
			log.error("Error al recuperar las localidades (DGT)", e);
		}
		return null;
	}
}
