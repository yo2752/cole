package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.registradores.model.dao.TipoCargoDao;
import org.gestoresmadrid.core.registradores.model.vo.TipoCargoVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTipoCargo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TipoCargoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoCargoImpl implements ServicioTipoCargo {

	private static final long serialVersionUID = 6496800161290226358L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoCargoImpl.class);

	@Autowired
	private TipoCargoDao tipoCargoDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public List<TipoCargoDto> getTipoCargo() {
		try {
			List<TipoCargoVO> listaVos = tipoCargoDao.getTipoCargo();
			if (listaVos != null && listaVos.size() > 0) {
				return conversor.transform(listaVos, TipoCargoDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar los tipos de cargos", e);
		}
		return null;
	}
}
