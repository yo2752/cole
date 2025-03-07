package org.gestoresmadrid.oegamComun.colegiado.services.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.general.model.dao.ColegiadoDao;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioColegiadoImpl implements ServicioColegiado {

	private static final long serialVersionUID = -3166432429891288991L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioColegiadoImpl.class);

	@Autowired
	private Conversor conversor;

	@Autowired
	private ColegiadoDao colegiadoDao;

	@Override
	@Transactional(readOnly = true)
	public ColegiadoVO getColegiado(String numColegiado) {
		ColegiadoVO colegiado = null;
		try {
			colegiado = colegiadoDao.getColegiado(numColegiado);
		} catch (Exception e) {
			log.error("Error al obtener el colegiado: " + numColegiado, e);
		} finally {
			colegiadoDao.evict(colegiado);
		}
		return colegiado;
	}

	@Override
	@Transactional(readOnly = true)
	public ColegiadoDto getColegiadoDto(String numColegiado) {
		ColegiadoDto colegiado = null;
		try {
			ColegiadoVO colegiadoVO = getColegiado(numColegiado);
			if (colegiadoVO != null) {
				colegiado = conversor.transform(colegiadoVO, ColegiadoDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener el colegiado: " + numColegiado, e);
		}
		return colegiado;
	}

	@Override
	@Transactional(readOnly = true)
	public Long getIdColegiado(String numColegiado) {
		Long id = null;
		try {
			ColegiadoVO colegiado = getColegiado(numColegiado);
			id = colegiado.getIdUsuario() != null ? colegiado.getIdUsuario() : null;
		} catch (Exception e) {
			log.error("Error al obtener el colegiado: " + numColegiado, e);
		}
		return id;
	}

	@Override
	@Transactional(readOnly = true)
	public ColegiadoVO getColegiadoPorIdUsuario(BigDecimal idUsuario) {
		ColegiadoVO colegiado = null;
		try {
			colegiado = colegiadoDao.getColegiadoPorUsuario(idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el colegiado por el usuario, error: ", e);
		}
		return colegiado;
	}

}
