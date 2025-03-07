package org.gestoresmadrid.oegamImportacion.persona.service.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.dao.PersonaDireccionDao;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaDireccionImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersonaDireccionImportacionImpl implements ServicioPersonaDireccionImportacion {

	private static final long serialVersionUID = -3321579692509882475L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersonaDireccionImportacionImpl.class);

	@Autowired
	PersonaDireccionDao personaDireccionDao;

	@Override
	@Transactional
	public PersonaDireccionVO buscarDireccionExistente(DireccionVO direccionVO, String numColegiado, String nif, String tipoTramite) {
		return personaDireccionDao.buscarDireccionExistente(direccionVO, numColegiado, nif, tipoTramite);
	}

	/**
	 * Devuelve un VO. Solo se utilzia en los servicios
	 */
	@Override
	@Transactional
	public ResultadoBean buscarPersonaDireccionVO(String numColegiado, String nif) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			List<PersonaDireccionVO> lpd = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, null, true);
			if (lpd != null && lpd.size() > 0) {
				resultado.setPersonaDir(lpd.get(0));
			} else {
				resultado.setError(true);
			}
		} catch (Exception e) {
			log.error("Error al buscar por personaDireccion", e);
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public void guardarActualizar(PersonaDireccionVO persona) {
		try {
			persona = personaDireccionDao.guardarOActualizar(persona);
			//resultado.addAttachment(PERSONADIRECCION, persona);
		} catch (Exception e) {
			//resultado.addMensajeALista("No se ha guardado las persona direccion");
			log.error("Error al guardar la personaDireccion", e);
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public PersonaDireccionVO buscarPersonaDireccionActivaVO(String numColegiado, String nif) {
		try {
			PersonaDireccionVO personaDirVO = personaDireccionDao.buscarPersonaDireccionActiva(numColegiado, nif);
			if(personaDirVO != null) {
				return personaDirVO;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la persona/direccion de la persona con NIF: " + nif + " del colegiado: " + numColegiado + "error: ",e);
		}
		return null;
	}	
	
}