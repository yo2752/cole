package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.licencias.model.dao.LcPersonaDireccionDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDireccionVO;
import org.gestoresmadrid.core.licencias.model.vo.LcPersonaDireccionVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcPersonaDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDireccionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcPersonaDireccionImpl implements ServicioLcPersonaDireccion {

	private static final long serialVersionUID = 4852586549011140770L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcPersonaDireccionImpl.class);

	@Autowired
	LcPersonaDireccionDao lcPersonaDireccionDao;

	@Autowired
	Conversor conversor;

	@Override
	@Transactional(readOnly = true)
	public LcPersonaDireccionVO buscarDireccionExistente(LcDireccionVO direccion, String numColegiado, String numDocumento) {
		return lcPersonaDireccionDao.buscarDireccionExistente(direccion, numColegiado, numDocumento);
	}

	@Override
	@Transactional
	public void guardarActualizar(LcPersonaDireccionVO lcPersonaDireccionVO) {
		try {
			lcPersonaDireccionDao.guardarOActualizar(lcPersonaDireccionVO);
		} catch (Exception e) {
			log.error("Error al guardar la personaDireccion de licencias", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public LcPersonaDireccionVO buscarPersonaDireccion(String numColegiado, String nif) {
		try {
			List<LcPersonaDireccionVO> lpd = lcPersonaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif);
			if (lpd != null && !lpd.isEmpty()) {
				return lpd.get(0);
			}
		} catch (Exception e) {
			log.error("Error al buscar por personaDireccion de licencias", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public LcDireccionDto obtenerDireccionActiva(String numColegiado, String nif) {
		try {
			List<LcPersonaDireccionVO> lpd = lcPersonaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif);
			if (lpd != null && !lpd.isEmpty()) {
				LcPersonaDireccionVO personaDireccion = lpd.get(0);
				if (personaDireccion.getLcDireccion() != null) {
					return conversor.transform(personaDireccion.getLcDireccion(), LcDireccionDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Error al buscar por personaDireccion de licencias", e);
		}
		return null;
	}
}
