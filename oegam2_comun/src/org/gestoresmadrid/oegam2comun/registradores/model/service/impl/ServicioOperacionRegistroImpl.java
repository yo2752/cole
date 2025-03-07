package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.registradores.model.dao.OperacionRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.OperacionRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioOperacionRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.OperacionRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioOperacionRegistroImpl implements ServicioOperacionRegistro {

	private static final long serialVersionUID = -2972772073389617053L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioOperacionRegistroImpl.class);

	@Autowired
	private OperacionRegistroDao operacionRegistroDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public OperacionRegistroDto getOperacionRegistro(String codigo, String tipoTramite) {
		try {
			OperacionRegistroVO vo = operacionRegistroDao.getOperacionRegistro(codigo, tipoTramite);
			if (vo != null) {
				return conversor.transform(vo, OperacionRegistroDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la operación del registro", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<OperacionRegistroDto> getOperacionesRegistro() {
		try {
			List<OperacionRegistroVO> listaVos = operacionRegistroDao.getOperacionesRegistro();
			if (listaVos != null && !listaVos.isEmpty()) {
				return conversor.transform(listaVos, OperacionRegistroDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la operación del registro", e);
		}
		return null;
	}
}
