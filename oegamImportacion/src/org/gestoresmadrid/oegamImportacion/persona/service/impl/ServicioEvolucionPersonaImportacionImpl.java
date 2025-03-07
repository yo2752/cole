package org.gestoresmadrid.oegamImportacion.persona.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.personas.model.dao.EvolucionPersonaDao;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaPK;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioEvolucionPersonaImportacion;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionPersonaImportacionImpl implements ServicioEvolucionPersonaImportacion {

	private static final long serialVersionUID = 163221922760433047L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionPersonaImportacionImpl.class);

	@Autowired
	EvolucionPersonaDao evolucionDao;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public void guardarEvolucionVO(EvolucionPersonaVO evolucionPersona, Long idUsuario) {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario);
		evolucionPersona.setUsuario(usuario);
		evolucionDao.guardar(evolucionPersona);
	}

	@Override
	public void guardarEvolucionPersonaDireccion(String nif, BigDecimal numExpediente, String tipoTramite, String numColegiado, Long idUsuario) {
		EvolucionPersonaVO evolucionPersonaVO = new EvolucionPersonaVO();
		EvolucionPersonaPK id = new EvolucionPersonaPK();
		id.setNumColegiado(numColegiado);
		id.setNif(nif);
		id.setFechaHora(utilesFecha.getFechaActualDesfaseBBDD());
		evolucionPersonaVO.setId(id);
		evolucionPersonaVO.setNumExpediente(numExpediente);
		evolucionPersonaVO.setTipoTramite(tipoTramite);
		evolucionPersonaVO.setOtros("Nueva Dirección");
		evolucionPersonaVO.setTipoActuacion(TipoActualizacion.MOD.getNombreEnum());
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario);
		evolucionPersonaVO.setUsuario(usuario);
		evolucionDao.guardar(evolucionPersonaVO);
	}

	/***********************************************************************************************************************************/

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<EvolucionPersonaVO> getEvolucionPersona(String nif, String numColegiado, ArrayList<String> tipoActualizacion) {
		try {
			List<EvolucionPersonaVO> lista = evolucionDao.getEvolucionPersona(nif, numColegiado, tipoActualizacion);
			if (lista != null && lista.size() > 0) {
				return lista;
			}
		} catch (Exception e) {
			log.error("Error el obtener la persona");
		}
		return null;
	}
}
