package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.LocalidadDgtDao;
import org.gestoresmadrid.core.direccion.model.dao.UnidadPoblacionalDao;
import org.gestoresmadrid.core.direccion.model.vo.LocalidadDgtVO;
import org.gestoresmadrid.core.direccion.model.vo.UnidadPoblacionalVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioLocalidadDgt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLocalidadDgtImpl implements ServicioLocalidadDgt {

	private static final long serialVersionUID = -8401332155723248234L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLocalidadDgtImpl.class);
	private static final String ERROR_LOCALIDADES = "Error al recuperar las localidades (DGT)";

	@Autowired
	private LocalidadDgtDao localidadDgtDao;

	@Autowired
	private UnidadPoblacionalDao unidadPoblacionalDao;

	@Override
	@Transactional
	public List<LocalidadDgtVO> getLocalidades(String localidad, String municipio) {
		try {
			return localidadDgtDao.getLocalidades(localidad, municipio);
		} catch (Exception e) {
			log.error(ERROR_LOCALIDADES, e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<LocalidadDgtVO> getLocalidadesPorCodigoPostal(String codigoPostal, String localidad) {
		try {
			return localidadDgtDao.getLocalidadesPorCodigoPostal(codigoPostal, localidad);
		} catch (Exception e) {
			log.error(ERROR_LOCALIDADES, e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<String> listaLocalidades(String codigoIne) {
		try {
			return localidadDgtDao.listaLocalidades(codigoIne);
		} catch (Exception e) {
			log.error(ERROR_LOCALIDADES, e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean actualizar(LocalidadDgtVO localidadDgtVO) {
		ResultBean result = new ResultBean();
		try {
			localidadDgtDao.actualizar(localidadDgtVO);
		} catch (Exception e) {
			log.error("Error al guardar/Actualizar la localidad DGT", e);
		}
		return result;
	}

	@Override
	@Transactional
	public List<UnidadPoblacionalVO> getUnidadesPoblacionales(String idMunicipio, String idProvincia) {
		try {
			List<UnidadPoblacionalVO> unidadPoblacionalVO = unidadPoblacionalDao.getUnidadPoblacional(idMunicipio, idProvincia);
			if (unidadPoblacionalVO != null) {
				return unidadPoblacionalVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio", e);
		}
		return null;
	}
}