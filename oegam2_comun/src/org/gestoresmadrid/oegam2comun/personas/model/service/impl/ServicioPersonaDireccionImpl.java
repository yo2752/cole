package org.gestoresmadrid.oegam2comun.personas.model.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.dao.PersonaDireccionDao;
import org.gestoresmadrid.core.personas.model.enumerados.EstadoPersonaDireccion;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersonaDireccionImpl implements ServicioPersonaDireccion {

	private static final long serialVersionUID = 5423730496730189787L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersonaDireccionImpl.class);

	@Autowired
	private PersonaDireccionDao personaDireccionDao;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Transactional
	@Override
	public ResultBean guardarActualizar(PersonaDireccionDto personaDDto) {
		ResultBean resultado = new ResultBean();
		try {
			if (personaDDto.getDireccion() != null && personaDDto.getDireccion().getIdProvincia() != null && !personaDDto.getDireccion().getIdProvincia().equals("")
					&& personaDDto.getDireccion().getIdProvincia().length() > 2) {
				personaDDto.getDireccion().setIdProvincia(personaDDto.getDireccion().getIdProvincia().substring(personaDDto.getDireccion().getIdProvincia().length() - 2));
			}

			if (personaDDto.getPersona().getEstado() == null || personaDDto.getPersona().getEstado().equals("")) {
				personaDDto.getPersona().setEstado("1");
			}

			if (personaDDto.getNif() == null || personaDDto.getNif().equals("")) {
				personaDDto.setNif(personaDDto.getPersona().getNif());
			}

			PersonaDireccionVO personaDireccion = conversor.transform(personaDDto, PersonaDireccionVO.class);
			// Guardar la direccion antes de guardar la persona
			if (personaDireccion.getDireccion().getIdDireccion() == null) {
				ResultBean result = servicioDireccion.guardar(personaDireccion.getDireccion());
				DireccionVO direccion = (DireccionVO) result.getAttachment(ServicioDireccion.DIRECCION);
				personaDireccion.setDireccion(direccion);
				personaDireccion.getId().setIdDireccion(direccion.getIdDireccion());
				personaDireccion.setFechaInicio(new Date());
			} else {
				personaDireccion.getId().setIdDireccion(personaDireccion.getDireccion().getIdDireccion());
			}

			try {
				personaDireccionDao.guardarOActualizar(personaDireccion);
			} catch (Exception e) {
				resultado.addMensajeALista("No se ha guardado el interviniente");
				log.error("No se ha guardado el interviniente", e);
			}
			personaDDto = conversor.transform(personaDireccion, PersonaDireccionDto.class);
			resultado.addAttachment(PERSONADIRECCION, personaDDto);
		} catch (Exception e) {
			log.error("Error al guardar la personaDireccion", e);
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultBean buscarPersonaDireccionDto(String numColegiado, String nif) {
		ResultBean resultado = new ResultBean();
		try {
			List<PersonaDireccionVO> lpd = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, null, true);
			if (lpd != null && lpd.size() > 0) {
				PersonaDireccionDto personaDDto = conversor.transform(lpd.get(0), PersonaDireccionDto.class);
				resultado.addAttachment(PERSONADIRECCION, personaDDto);
			}
		} catch (Exception e) {
			log.error("Error al buscar por personaDireccion", e);
		}
		return resultado;
	}

	/**
	 * Devuelve un VO. Solo se utilzia en los servicios
	 */
	@Override
	@Transactional
	public ResultBean buscarPersonaDireccionVO(String numColegiado, String nif) {
		ResultBean resultado = new ResultBean();
		try {
			List<PersonaDireccionVO> lpd = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, null, true);
			if (lpd != null && lpd.size() > 0) {
				resultado.addAttachment(PERSONADIRECCION, lpd.get(0));
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
	public ResultBean guardarActualizar(PersonaDireccionVO persona) {
		ResultBean resultado = new ResultBean();
		try {
			persona = personaDireccionDao.guardarOActualizar(persona);
			resultado.addAttachment(PERSONADIRECCION, persona);
		} catch (Exception e) {
			resultado.addMensajeALista("No se ha guardado las persona direccion");
			log.error("Error al guardar la personaDireccion", e);
		}
		return resultado;
	}

	@Override
	@Transactional
	public PersonaDireccionVO buscarDireccionExistente(DireccionVO direccionVO, String numColegiado, String nif, String tipoTramite) {
		return personaDireccionDao.buscarDireccionExistente(direccionVO, numColegiado, nif, tipoTramite);
	}

	@Override
	@Transactional
	public ResultBean eliminarFusionarDireccion(String numColegiado, String nif, Long idDireccionPrincipal, Long idDireccionBorrar, boolean existenIntervinientes, Short estadoPersonaDireccion) {
		ResultBean result = new ResultBean();
		PersonaDireccionVO personaDireccionPrincipal;
		PersonaDireccionVO personaDireccionBorrar;
		try {
			List<PersonaDireccionVO> listaPersonaDireccionVO = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, idDireccionBorrar, false);
			if (listaPersonaDireccionVO != null && listaPersonaDireccionVO.size() > 0) {
				personaDireccionBorrar = listaPersonaDireccionVO.get(0);
				if (personaDireccionBorrar != null && idDireccionPrincipal != null) {
					listaPersonaDireccionVO = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, idDireccionPrincipal, false);
					if (listaPersonaDireccionVO != null && listaPersonaDireccionVO.size() > 0) {
						personaDireccionPrincipal = listaPersonaDireccionVO.get(0);
						if (personaDireccionPrincipal != null) {
							boolean modificar = false;
							if (!existenIntervinientes) {
								if (utilesFecha.compararFechaDate(personaDireccionPrincipal.getFechaInicio(), personaDireccionBorrar.getFechaInicio()) == 1) {
									personaDireccionPrincipal.setFechaInicio(personaDireccionBorrar.getFechaInicio());
									modificar = true;
								}
								if (utilesFecha.compararFechaDate(personaDireccionPrincipal.getFechaFin(), personaDireccionBorrar.getFechaFin()) == 2) {
									personaDireccionPrincipal.setFechaFin(personaDireccionBorrar.getFechaFin());
									modificar = true;
								}
							}
							if (personaDireccionBorrar.getFechaFin() == null) {
								personaDireccionBorrar.setFechaFin(Calendar.getInstance().getTime());
								personaDireccionPrincipal.setFechaFin(null);
								modificar = true;
							}
							if (!EstadoPersonaDireccion.Activo.getValorEnum().equals(personaDireccionPrincipal.getEstado())) {
								personaDireccionPrincipal.setEstado(EstadoPersonaDireccion.Activo.getValorEnum());
								modificar = true;
							}
							if (modificar) {
								personaDireccionDao.actualizar(personaDireccionPrincipal);
							}
						}
					}
				}
				if (personaDireccionBorrar.getFechaFin() == null) {
					result.setMensaje("No se puede eliminar/fusionar la dirección " + idDireccionBorrar + " al ser principal");
				} else {
					if (existenIntervinientes) {
						personaDireccionBorrar.setEstado(estadoPersonaDireccion);
						personaDireccionDao.actualizar(personaDireccionBorrar);
					} else {
						personaDireccionDao.borrar(personaDireccionBorrar);
					}
				}
			}
		} catch (Exception e) {
			result.setMensaje("Error en persona-direccion en la fusión");
			result.setError(true);
			log.error("Error en persona-direccion en la fusión", e);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean asignarPrincipal(String numColegiado, String nif, Long idDireccionPrincipal) {
		ResultBean result = new ResultBean();
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

	public PersonaDireccionDao getPersonaDireccionDao() {
		return personaDireccionDao;
	}

	public void setPersonaDireccionDao(PersonaDireccionDao personaDireccionDao) {
		this.personaDireccionDao = personaDireccionDao;
	}
}
