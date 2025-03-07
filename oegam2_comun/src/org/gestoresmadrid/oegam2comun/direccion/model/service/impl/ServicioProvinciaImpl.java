package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.ProvinciaDao;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioProvinciaImpl implements ServicioProvincia {

	private static final long serialVersionUID = -3463991735972581837L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioProvinciaImpl.class);
	private static final String ERROR_PROVINCIA = "Error al recuperar la provincia";

	@Autowired
	private ProvinciaDao provinciaDao;

	@Autowired
	private Conversor conversor;

	@Transactional
	@Override
	public ProvinciaVO getProvincia(String idProvincia) {
		try {
			ProvinciaVO provinciaVO = provinciaDao.getProvincia(idProvincia);
			if (provinciaVO != null) {
				return provinciaVO;
			}
		} catch (Exception e) {
			log.error(ERROR_PROVINCIA, e);
		}
		return null;
	}

	@Transactional
	@Override
	public ProvinciaDto getProvinciaDto(String idProvincia) {
		try {
			ProvinciaVO provinciaVO = provinciaDao.getProvincia(idProvincia);
			if (provinciaVO != null) {
				return conversor.transform(provinciaVO, ProvinciaDto.class);
			}
		} catch (Exception e) {
			log.error(ERROR_PROVINCIA, e);
		}
		return null;
	}

	@Transactional
	@Override
	public ProvinciaDto getProvinciaPorNombre(String nombre) {
		try {
			ProvinciaVO provinciaVO = provinciaDao.getProvinciaPorNombre(nombre);
			if (provinciaVO != null) {
				return conversor.transform(provinciaVO, ProvinciaDto.class);
			}
		} catch (Exception e) {
			log.error(ERROR_PROVINCIA, e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<ProvinciaDto> getListaProvincias() {
		try {
			List<ProvinciaVO> lista = provinciaDao.getLista();
			if (lista != null && !lista.isEmpty()) {
				return conversor.transform(lista, ProvinciaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la lista de provincias, error: ", e);
		}
		return Collections.emptyList();
	}
}