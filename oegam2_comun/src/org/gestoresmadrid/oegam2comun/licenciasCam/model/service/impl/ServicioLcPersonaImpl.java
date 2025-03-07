package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcPersonaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcPersonaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcPersona;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcPersonaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcPersonaImpl implements ServicioLcPersona {

	private static final long serialVersionUID = 4886095992948605707L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcPersonaImpl.class);

	@Autowired
	LcPersonaDao lcPersonaDao;

	@Autowired
	Conversor conversor;

	@Override
	@Transactional(readOnly = true)
	public LcPersonaVO getLcPersona(String nif, String numColegiado) {
		LcPersonaVO lcPersona = null;
		try {
			lcPersona = lcPersonaDao.getLcPersona(nif, numColegiado);
		} catch (Exception e) {
			log.error("Error al obtener la persona de licencias", e);
		} finally {
			lcPersonaDao.evict(lcPersona);
		}
		return lcPersona;
	}

	@Override
	@Transactional(readOnly = true)
	public LcPersonaDto getLcPersonaDto(String nif, String numColegiado) {
		LcPersonaDto lcPersonaDto = null;
		try {
			LcPersonaVO lcPersona = lcPersonaDao.getLcPersona(nif, numColegiado);
			if (lcPersona != null) {
				lcPersonaDto = conversor.transform(lcPersona, LcPersonaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener la persona de licencias", e);
		}
		return lcPersonaDto;
	}

	@Override
	@Transactional
	public ResultBean guardarActualizar(LcPersonaDto persona) {
		ResultBean resultado = new ResultBean();
		try {
			LcPersonaVO lcPersonaBBDD = getLcPersona(persona.getNif(), persona.getNumColegiado());
			if (lcPersonaBBDD != null) {
				persona.setIdPersona(lcPersonaBBDD.getIdPersona());
			}
			cambiarMayusculas(persona);
			LcPersonaVO lcPersonaVO = conversor.transform(persona, LcPersonaVO.class);
			lcPersonaVO = lcPersonaDao.guardarOActualizar(lcPersonaVO);
			resultado.addAttachment(PERSONA, lcPersonaVO);
		} catch (Exception e) {
			resultado.setError(true);
			resultado.addMensajeALista("Error al guardarActualizar la persona");
			log.error("Error al guardarActualizar la persona", e);
		}
		return resultado;
	}

	private void cambiarMayusculas(LcPersonaDto persona) {
		if (StringUtils.isNotBlank(persona.getNombre())) {
			persona.setNombre(persona.getNombre().toUpperCase());
		}

		if (StringUtils.isNotBlank(persona.getApellido1RazonSocial())) {
			persona.setApellido1RazonSocial(persona.getApellido1RazonSocial().toUpperCase());
		}

		if (StringUtils.isNotBlank(persona.getApellido2())) {
			persona.setApellido2(persona.getApellido2().toUpperCase());
		}

		if (StringUtils.isNotBlank(persona.getNif())) {
			persona.setNif(persona.getNif().toUpperCase());
		}
	}
}
