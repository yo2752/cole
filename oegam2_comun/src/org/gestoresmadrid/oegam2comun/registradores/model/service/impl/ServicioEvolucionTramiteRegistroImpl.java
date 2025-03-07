package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.registradores.model.dao.EvolucionTramiteRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.EvolucionTramiteRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioEvolucionTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.EvolucionTramiteRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionTramiteRegistroImpl implements ServicioEvolucionTramiteRegistro {

	private static final long serialVersionUID = -4163729566865691199L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionTramiteRegistroImpl.class);

	@Autowired
	private EvolucionTramiteRegistroDao evolucionTramiteRegistroDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public void guardar(EvolucionTramiteRegistroDto evolucion) {
		try {
			EvolucionTramiteRegistroVO evolucionVO = conversor.transform(evolucion, EvolucionTramiteRegistroVO.class);
			evolucionVO.getId().setFechaCambio(utilesFecha.getFechaActualDesfaseBBDD());
			evolucionTramiteRegistroDao.guardarOActualizar(evolucionVO);
		} catch (Exception e) {
			log.error("Error al guardar la evolución del trámite de registro: " + evolucion.getIdTramiteRegistro(), e, evolucion.getIdTramiteRegistro().toString());
		}
	}

	@Override
	@Transactional
	public void guardarEvolucion(BigDecimal idTramiteRegistro, BigDecimal estadoAnterior, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		EvolucionTramiteRegistroDto evolucion = new EvolucionTramiteRegistroDto();
		evolucion.setIdTramiteRegistro(idTramiteRegistro);
		if (estadoAnterior != null) {
			evolucion.setEstadoAnterior(estadoAnterior);
		} else {
			evolucion.setEstadoAnterior(BigDecimal.ZERO);
		}

		evolucion.setEstadoNuevo(estadoNuevo);
		if (idUsuario != null) {
			UsuarioDto usuario = new UsuarioDto();
			usuario.setIdUsuario(idUsuario);
			evolucion.setUsuario(usuario);
		}
		guardar(evolucion);
	}

	public EvolucionTramiteRegistroDao getEvolucionTramiteRegistroDao() {
		return evolucionTramiteRegistroDao;
	}

	public void setEvolucionTramiteRegistroDao(EvolucionTramiteRegistroDao evolucionTramiteRegistroDao) {
		this.evolucionTramiteRegistroDao = evolucionTramiteRegistroDao;
	}
}
