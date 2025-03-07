package org.gestoresmadrid.oegam2comun.personas.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.personas.model.dao.EvolucionPersonaDao;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaPK;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.view.dto.EvolucionPersonaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionPersonaImpl implements ServicioEvolucionPersona {

	private static final long serialVersionUID = -6675434227442834777L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionPersonaImpl.class);

	@Autowired
	private EvolucionPersonaDao evolucionDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<EvolucionPersonaVO> getEvolucionPersona(String nif, String numColegiado, ArrayList<String> tipoActualizacion) {
		try {
			List<EvolucionPersonaVO> lista = evolucionDao.getEvolucionPersona(nif, numColegiado, tipoActualizacion);
			if (lista != null && !lista.isEmpty()) {
				return lista;
			}
		} catch (Exception e) {
			log.error("Error el obtener la persona");
		}
		return null;
	}

	@Transactional
	@Override
	public ResultBean guardarEvolucion(EvolucionPersonaDto evolucion) {
		ResultBean resultado = new ResultBean();
		EvolucionPersonaVO evolucionVO = conversor.transform(evolucion, EvolucionPersonaVO.class);
		evolucionVO.getId().setFechaHora(utilesFecha.getFechaActualDesfaseBBDD());
		EvolucionPersonaPK evolucionPK = (EvolucionPersonaPK) evolucionDao.guardar(evolucionVO);
		resultado.setError(false);

		if (evolucionPK == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Error al guardar la evolución de la persona");
			log.error("Error al guardar la evolución de la persona");
		}
		return resultado;
	}

	@Transactional
	@Override
	public void guardarEvolucionPersonaDireccion(String nif, BigDecimal numExpediente, String tipoTramite, String numColegiado, UsuarioDto usuario, boolean direccionNueva) {
		if (direccionNueva) {
			EvolucionPersonaDto evolucionDto = new EvolucionPersonaDto();
			evolucionDto.setNumColegiado(numColegiado);
			evolucionDto.setNumExpediente(numExpediente);
			evolucionDto.setTipoTramite(tipoTramite);
			evolucionDto.setNif(nif);
			evolucionDto.setOtros("Nueva Dirección");
			evolucionDto.setTipoActuacion(TipoActualizacion.MOD.getNombreEnum());
			evolucionDto.setUsuario(usuario);

			guardarEvolucion(evolucionDto);
		}
	}
}