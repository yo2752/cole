package org.gestoresmadrid.oegamInterga.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.personas.model.dao.PersonaDao;
import org.gestoresmadrid.core.personas.model.dao.PersonaDireccionDao;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.interga.model.dao.IntervinienteIntergaDao;
import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamInterga.service.ServicioIntervinienteInterga;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioIntervinienteIntergaImpl implements ServicioIntervinienteInterga {

	private static final long serialVersionUID = 2448070758028524376L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioIntervinienteIntergaImpl.class);

	@Autowired
	IntervinienteIntergaDao intervinientePermInterDao;

	@Autowired
	Conversor conversor;

	@Autowired
	PersonaDao personaDao;

	@Autowired
	PersonaDireccionDao personaDireccionDao;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Transactional
	@Override
	public ResultadoBean guardarActualizar(IntervinienteIntergaVO intervinientePantalla, String tipoTramiteInterga) {
		ResultadoBean respuesta = new ResultadoBean(Boolean.FALSE);
		IntervinienteIntergaVO intervinienteBBDD = null;
		try {
			if (intervinientePantalla.getNumExpediente() != null) {
				intervinienteBBDD = getIntervinienteTrafico(intervinientePantalla.getNumExpediente(), null, intervinientePantalla.getNumColegiado(), tipoTramiteInterga);
				if (intervinienteBBDD != null) {
					if (intervinientePantalla.getNif().equals(intervinienteBBDD.getNif())) {
						boolean actualiza = esModificada(intervinientePantalla, intervinienteBBDD);
						if (actualiza) {
							intervinientePermInterDao.guardarOActualizar(intervinienteBBDD);
						}
						respuesta.setInterviniente(intervinienteBBDD);
					} else {
						eliminar(intervinienteBBDD, tipoTramiteInterga);
						intervinientePermInterDao.guardar(intervinientePantalla);
						respuesta.setInterviniente(intervinientePantalla);
					}
				} else {
					intervinientePermInterDao.guardar(intervinientePantalla);
					respuesta.setInterviniente(intervinientePantalla);
				}
			} else {
				log.error("Error guardado el interviniente. No tiene expediente asociado");
			}
		} catch (Exception e) {
			log.error("Error al guardarActualizar un interviniente", e);
		}
		return respuesta;
	}

	@Override
	@Transactional
	public IntervinienteIntergaVO crearIntervinienteNif(String nif, String numColegiado) {
		try {
			PersonaVO persona = personaDao.getPersona(nif, numColegiado);
			if (persona != null) {
				IntervinienteIntergaVO interviniente = new IntervinienteIntergaVO();
				interviniente.setPersona(persona);
				List<PersonaDireccionVO> lpd = personaDireccionDao.getPersonaDireccionPorNif(numColegiado, nif, null, true);

				if (lpd != null && !lpd.isEmpty()) {
					interviniente.setDireccion(lpd.get(0).getDireccion());
				}
				return interviniente;
			}
		} catch (Exception e) {
			log.error("Error al crear un interviniente", e);
		}

		return null;
	}

	@Override
	@Transactional
	public void eliminar(IntervinienteIntergaVO interviniente, String tipoTramiteInterga) {
		try {
			interviniente = getIntervinienteTrafico(interviniente.getNumExpediente(), interviniente.getNif(), interviniente.getNumColegiado(), tipoTramiteInterga);
			if (interviniente != null) {
				intervinientePermInterDao.borrar(interviniente);
			}
		} catch (Exception e) {
			log.error("Error al eliminar un interviniente", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public IntervinienteIntergaVO getIntervinienteTramite(BigDecimal numExpediente, String tipoTramiteInterga) {
		return intervinientePermInterDao.getIntervinienteTraficoTipo(numExpediente, tipoTramiteInterga);
	}

	@Override
	@Transactional(readOnly = true)
	public IntervinienteIntergaVO getIntervinienteTrafico(BigDecimal numExpediente, String nif, String numColegiado, String tipoTramiteInterga) {
		return intervinientePermInterDao.getIntervinienteTrafico(numExpediente, nif, numColegiado, tipoTramiteInterga);
	}

	private boolean esModificada(IntervinienteIntergaVO intervinienteVO, IntervinienteIntergaVO intervinienteBBDD) {
		boolean actualiza = false;
		try {
			if (!utiles.sonIgualesString(intervinienteVO.getNif(), intervinienteBBDD.getNif())) {
				actualiza = true;
				intervinienteBBDD.setNif(intervinienteVO.getNif());
			}
			if (!utiles.sonIgualesObjetos(intervinienteVO.getIdDireccion(), intervinienteBBDD.getIdDireccion())) {
				actualiza = true;
				intervinienteBBDD.setIdDireccion(intervinienteVO.getIdDireccion());
			}
		} catch (Exception e) {
			log.error("Error al comparar el interviniente de pantalla con el interviniente de la bbdd", e);
		} finally {
			intervinientePermInterDao.evict(intervinienteVO);
		}
		return actualiza;
	}
}
