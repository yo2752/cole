package org.gestoresmadrid.oegamComun.direccion.service.impl;

import java.util.Calendar;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.dao.EvolucionPersonaDao;
import org.gestoresmadrid.core.personas.model.dao.PersonaDireccionDao;
import org.gestoresmadrid.core.personas.model.enumerados.EstadoPersonaDireccion;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunPersonaDireccion;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioComunPersonaDireccionImpl implements ServicioComunPersonaDireccion {

	private static final long serialVersionUID = 5423730496730189787L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunPersonaDireccionImpl.class);

	@Autowired
	PersonaDireccionDao personaDireccionDao;
	
	@Autowired
	EvolucionPersonaDao evolucionPersonaDao;

	@Override
	@Transactional(readOnly=true)
	public ResultadoPersonaBean tratarAsignarPrincipal(String numColegiado, String nif, Long idDireccion) {
		ResultadoPersonaBean resultado = new ResultadoPersonaBean(Boolean.FALSE);
		try {
			List<PersonaDireccionVO> listaPersonaDireccionVO = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, null, true);
			if (listaPersonaDireccionVO != null && !listaPersonaDireccionVO.isEmpty()) {
				PersonaDireccionVO personaDireccion = listaPersonaDireccionVO.get(0);
				personaDireccion.setFechaFin(Calendar.getInstance().getTime());
				personaDireccion.setEstado(EstadoPersonaDireccion.Activo.getValorEnum());
				resultado.setPersonaDirPrincipalAnt(personaDireccion);
				listaPersonaDireccionVO = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, idDireccion, false);
				if (listaPersonaDireccionVO != null && !listaPersonaDireccionVO.isEmpty()) {
					personaDireccion = listaPersonaDireccionVO.get(0);
					personaDireccion.setFechaFin(null);
					personaDireccion.setEstado(EstadoPersonaDireccion.Activo.getValorEnum());
					resultado.setPersonaDirPrincipalAnt(personaDireccion);
				} else {
					resultado.setMensaje("No se ha encontrado la dirección que pasa a ser principal");
					resultado.setError(Boolean.TRUE);
				}
			} else {
				resultado.setMensaje("No se ha encontrado la dirección principal");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar la persona direccion para el nif: " + nif + " e idDireccion: " + idDireccion
				+ ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar la persona direccion para el nif: " + nif);
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ResultadoPersonaBean buscarPersonaDireccionVO(String numColegiado, String nif) {
		ResultadoPersonaBean resultado = new ResultadoPersonaBean(Boolean.FALSE);
		try {
			List<PersonaDireccionVO> lpd = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, null, true);
			if (lpd != null && lpd.size() > 0) {
				resultado.setPersonaDireccion(lpd.get(0));
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
	public ResultadoPersonaBean guardarActualizar(PersonaDireccionVO persona) {
		ResultadoPersonaBean resultado = new ResultadoPersonaBean(Boolean.FALSE);
		try {
			persona = personaDireccionDao.guardarOActualizar(persona);
			resultado.setPersonaDireccion(persona);
		} catch (Exception e) {
			resultado.setMensaje("No se ha guardado las persona direccion");
			log.error("Error al guardar la personaDireccion", e);
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public void guardarActualizarConEvo(PersonaDireccionVO persona, EvolucionPersonaVO evolucion) {
		personaDireccionDao.guardarOActualizar(persona);
		evolucionPersonaDao.guardar(evolucion);
	}

	@Override
	@Transactional
	public PersonaDireccionVO buscarDireccionExistente(DireccionVO direccionVO, String numColegiado, String nif, String tipoTramite) {
		return personaDireccionDao.buscarDireccionExistente(direccionVO, numColegiado, nif, tipoTramite);
	}

	@Override
	@Transactional
	public ResultadoBean asignarPrincipal(String numColegiado, String nif, Long idDireccionPrincipal) {
		ResultadoBean result = new ResultadoBean(Boolean.FALSE);
		try {
			List<PersonaDireccionVO> listaPersonaDireccionVO = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, null, true);
			if (listaPersonaDireccionVO != null && listaPersonaDireccionVO.size() > 0) {
				PersonaDireccionVO personaDireccion = listaPersonaDireccionVO.get(0);
				personaDireccion.setFechaFin(Calendar.getInstance().getTime());
				personaDireccion.setEstado(EstadoPersonaDireccion.Activo.getValorEnum());
				personaDireccionDao.actualizar(personaDireccion);

				listaPersonaDireccionVO = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, idDireccionPrincipal, false);
				if (listaPersonaDireccionVO != null && listaPersonaDireccionVO.size() > 0) {
					personaDireccion = listaPersonaDireccionVO.get(0);
					personaDireccion.setFechaFin(null);
					personaDireccion.setEstado(EstadoPersonaDireccion.Activo.getValorEnum());
					personaDireccionDao.actualizar(personaDireccion);
				} else {
					result.setMensaje("No se ha encontrado la dirección que pasa a ser principal");
					result.setError(true);
				}
			} else {
				result.setMensaje("No se ha encontrado la dirección principal");
				result.setError(true);
			}
		} catch (Exception e) {
			result.setMensaje("Error al asignar como principal una dirección");
			result.setError(true);
			log.error("Error al asignar como principal una dirección", e);
		}
		return result;
	}

}
