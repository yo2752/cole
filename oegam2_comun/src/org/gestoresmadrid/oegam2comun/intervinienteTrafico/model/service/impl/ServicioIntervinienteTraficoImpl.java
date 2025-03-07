package org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.intervinienteTrafico.model.dao.IntervinienteTraficoDao;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioIntervinienteTraficoImpl implements ServicioIntervinienteTrafico {

	private static final long serialVersionUID = -6844598577092710882L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioIntervinienteTraficoImpl.class);

	@Autowired
	private IntervinienteTraficoDao intervinienteTraficoDao;

	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional(readOnly = true)
	public IntervinienteTraficoVO getIntervinienteTrafico(BigDecimal numExpediente, String tipoInterviniente, String nif, String numColegiado) {
		try {
			return intervinienteTraficoDao.getIntervinienteTrafico(numExpediente, tipoInterviniente, nif, numColegiado);
		} catch (Exception e) {
			log.error("Error al obtener el interviniente", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public Integer comprobarTramitesEmpresaST(Long idContrato, Date fecha, String nifInterviniente, String codigoPostal,
			String idMunicipio, String idProvincia, String tipoTramiteSolicitud) {
		return intervinienteTraficoDao.comprobarcomprobarTramitesEmpresaST(idContrato,fecha,nifInterviniente,codigoPostal, idMunicipio, idProvincia, tipoTramiteSolicitud);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TramiteTraficoVO> getListaTramitesPorNifTipoIntervienteYFecha(String nif, BigDecimal idContrato,String tipoInterviniente, Date fecha, String tipoTramite, String codigoPostal, String idMunicipio, String idProvincia, String tipoTramiteSolicitud) {
		try {
			List<IntervinienteTraficoVO> listaTramitesInterv = intervinienteTraficoDao.getListaTramitesPorIntervieniente(nif, idContrato, tipoInterviniente, fecha, tipoTramite, codigoPostal, idMunicipio, idProvincia, tipoTramiteSolicitud);
			if (listaTramitesInterv!= null && !listaTramitesInterv.isEmpty()) {
				List<TramiteTraficoVO> listaTramites = new ArrayList<>();
				for (IntervinienteTraficoVO interviniente : listaTramitesInterv) {
					listaTramites.add(interviniente.getTramiteTrafico());
				}
				return listaTramites;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites, error: ",e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public IntervinienteTraficoDto getIntervinienteTraficoDto(BigDecimal numExpediente, String tipoInterviniente, String nif, String numColegiado) {
		try {
			IntervinienteTraficoVO interviniente = getIntervinienteTrafico(numExpediente, tipoInterviniente, nif, numColegiado);
			if (interviniente != null) {
				return conversor.transform(interviniente, IntervinienteTraficoDto.class);
			}
		} catch (Exception e) {
			log.error("Error el obtener el interviniente");
		}
		return null;
	}

	@Transactional
	@Override
	public ResultBean guardarActualizar(IntervinienteTraficoVO intervinientePantalla, String conversion) {
		ResultBean respuesta = new ResultBean();
		IntervinienteTraficoVO intervinienteBBDD = null;
		try {
			if (intervinientePantalla.getId().getNumExpediente() != null) {
				intervinienteBBDD = getIntervinienteTrafico(intervinientePantalla.getId().getNumExpediente(), intervinientePantalla.getId().getTipoInterviniente(), null, intervinientePantalla.getId()
						.getNumColegiado());
				if (intervinienteBBDD != null) {
					if (intervinientePantalla.getId().getNif().equals(intervinienteBBDD.getId().getNif())) {
						boolean actualiza = false;
						if (conversion != null && !conversion.isEmpty()) {
							IntervinienteTraficoVO intervinienteCompleto = (IntervinienteTraficoVO) intervinienteBBDD.clone();
							conversor.transform(intervinientePantalla, intervinienteBBDD, conversion);
							actualiza = esModificada(intervinienteBBDD, intervinienteCompleto);
						} else {
							actualiza = esModificada(intervinientePantalla, intervinienteBBDD);
						}
						if (actualiza) {
							modificarDatosCombos(intervinienteBBDD);
							intervinienteTraficoDao.guardarOActualizar(intervinienteBBDD);
						}
						respuesta.addAttachment(INTERVINIENTE, intervinienteBBDD);
					} else {
						eliminar(intervinienteBBDD);
						intervinienteTraficoDao.guardar(intervinientePantalla);
						respuesta.addAttachment(INTERVINIENTE, intervinientePantalla);
					}
				} else {
					modificarDatosCombos(intervinientePantalla);
					intervinienteTraficoDao.guardar(intervinientePantalla);
					respuesta.addAttachment(INTERVINIENTE, intervinientePantalla);
				}
			} else {
				log.error("Error guardado el interviniente. No tiene expediente asociado");
			}
		} catch (Exception e) {
			log.error("Error al guardarActualizar un interviniente", e);
		}
		return respuesta;
	}

	@Transactional
	@Override
	public ResultBean guardarMantenimientoInterviniente(IntervinienteTraficoDto intervinienteTraficoDto) {
		IntervinienteTraficoVO intervinienteTraficoVO = conversor.transform(intervinienteTraficoDto, IntervinienteTraficoVO.class);
		return guardarActualizar(intervinienteTraficoVO, CONVERSION_CONSULTA_INTERVINIENTE);
	}

	@Override
	@Transactional
	public boolean existenIntervinientesPorIdDireccion(String nif, String numColegiado, Long idDireccion) {
		try {
			List<IntervinienteTraficoVO> lista = intervinienteTraficoDao.getExpedientesDireccion(nif, numColegiado, idDireccion);
			if (lista != null && !lista.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			log.error("Error al obtener el interviniente");
		}
		return false;
	}

	@Transactional
	@Override
	public IntervinienteTraficoDto crearIntervinienteNif(String nif, String numColegiado) {
		try {
			ResultBean resultado = servicioPersona.buscarPersona(nif, numColegiado);
			PersonaDto personaDto = (PersonaDto) resultado.getAttachment(ServicioPersona.PERSONA);

			if (personaDto != null) {
				IntervinienteTraficoDto interviniente = new IntervinienteTraficoDto();
				interviniente.setPersona(personaDto);
				interviniente.setNifInterviniente(personaDto.getNif());
				if (numColegiado != null && !numColegiado.isEmpty()) {
					interviniente.setNumColegiado(numColegiado);
				}
				// Traemos la dirección de la persona
				ResultBean resultPD = servicioPersonaDireccion.buscarPersonaDireccionDto(numColegiado, personaDto.getNif());

				PersonaDireccionDto pdDto = (PersonaDireccionDto) resultPD.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
				if (pdDto != null) {
					DireccionDto direcionDto = conversor.transform(pdDto.getDireccion(), DireccionDto.class);
					interviniente.setDireccion(direcionDto);
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
	public void eliminar(IntervinienteTraficoVO interviniente) {
		try {
			interviniente = getIntervinienteTrafico(interviniente.getId().getNumExpediente(), interviniente.getId().getTipoInterviniente(), interviniente.getId().getNif(), interviniente.getId()
					.getNumColegiado());
			if (interviniente != null) {
				intervinienteTraficoDao.borrar(interviniente);
			}
		} catch (Exception e) {
			log.error("Error al eliminar un interviniente", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, isolation = Isolation.REPEATABLE_READ)
	@Override
	public boolean esModificada(IntervinienteTraficoVO intervinienteVO, IntervinienteTraficoVO intervinienteBBDD) {
		boolean actualiza = false;
		try {
			if (!utiles.sonIgualesObjetos(intervinienteVO.getId(), intervinienteBBDD.getId())
					|| (intervinienteVO.getId() != null && intervinienteBBDD.getId() != null && !utiles.sonIgualesString(intervinienteVO.getId().getNif(), intervinienteBBDD.getId().getNif()))) {
				actualiza = true;
				intervinienteBBDD.getId().setNif(intervinienteVO.getId().getNif());
			}
			if (!utiles.sonIgualesObjetos(intervinienteVO.getIdDireccion(), intervinienteBBDD.getIdDireccion())) {
				actualiza = true;
				intervinienteBBDD.setIdDireccion(intervinienteVO.getIdDireccion());
			}
			if (!utiles.sonIgualesString(intervinienteVO.getCambioDomicilio(), intervinienteBBDD.getCambioDomicilio())) {
				actualiza = true;
				intervinienteBBDD.setCambioDomicilio(intervinienteVO.getCambioDomicilio());
			}
			if (!utiles.sonIgualesString(intervinienteVO.getAutonomo(), intervinienteBBDD.getAutonomo())) {
				actualiza = true;
				intervinienteBBDD.setAutonomo(intervinienteVO.getAutonomo());
			}
			if (!utiles.sonIgualesCombo(intervinienteVO.getCodigoIae(), intervinienteBBDD.getCodigoIae())) {
				actualiza = true;
				intervinienteBBDD.setCodigoIae(utiles.convertirCombo(intervinienteVO.getCodigoIae()));
			}
			if (utilesFecha.compararFechaDate(intervinienteVO.getFechaInicio(), intervinienteBBDD.getFechaInicio()) != 0) {
				actualiza = true;
				intervinienteBBDD.setFechaInicio(intervinienteVO.getFechaInicio());
			}
			if (utilesFecha.compararFechaDate(intervinienteVO.getFechaFin(), intervinienteBBDD.getFechaFin()) != 0) {
				actualiza = true;
				intervinienteBBDD.setFechaFin(intervinienteVO.getFechaFin());
			}
			if (!utiles.sonIgualesCombo(intervinienteVO.getConceptoRepre(), intervinienteBBDD.getConceptoRepre())) {
				actualiza = true;
				intervinienteBBDD.setConceptoRepre(utiles.convertirCombo(intervinienteVO.getConceptoRepre()));
			}
			if (!utiles.sonIgualesString(intervinienteVO.getIdMotivoTutela(), intervinienteBBDD.getIdMotivoTutela())) {
				actualiza = true;
				intervinienteBBDD.setIdMotivoTutela(intervinienteVO.getIdMotivoTutela());
			}
			if (!utiles.sonIgualesString(intervinienteVO.getHoraInicio(), intervinienteBBDD.getHoraInicio())) {
				actualiza = true;
				intervinienteBBDD.setHoraInicio(intervinienteVO.getHoraInicio());
			}
			if (!utiles.sonIgualesString(intervinienteVO.getHoraFin(), intervinienteBBDD.getHoraFin())) {
				actualiza = true;
				intervinienteBBDD.setHoraFin(intervinienteVO.getHoraFin());
			}
			if (!utiles.sonIgualesString(intervinienteVO.getDatosDocumento(), intervinienteBBDD.getDatosDocumento())) {
				actualiza = true;
				intervinienteBBDD.setDatosDocumento(intervinienteVO.getDatosDocumento());
			}
		} catch (Exception e) {
			log.error("Error al comparar el interviniente de pantalla con el interviniente de la bbdd", e);
		} finally {
			intervinienteTraficoDao.evict(intervinienteVO);
		}
		return actualiza;
	}

	@Override
	@Transactional
	public List<IntervinienteTraficoDto> getExpedientesDireccion(String nif, String numColegiado, Long idDireccion) {
		List<IntervinienteTraficoDto> listaExpedientesDireccion = null;
		try {
			List<IntervinienteTraficoVO> lista = intervinienteTraficoDao.getExpedientesDireccion(nif, numColegiado, idDireccion);
			if (lista != null && !lista.isEmpty()) {
				listaExpedientesDireccion = new ArrayList<>();
				for (IntervinienteTraficoVO interTraficoVO : lista) {
					IntervinienteTraficoDto intervinienteDto = conversor.transform(interTraficoVO, IntervinienteTraficoDto.class);
					intervinienteDto.setTipoIntervinienteDes(TipoInterviniente.convertirTexto(intervinienteDto.getTipoInterviniente()));
					listaExpedientesDireccion.add(intervinienteDto);
				}
			}
		} catch (Exception e) {
			log.error("Error al obtener los expedientes por dirección", e);
		}
		return listaExpedientesDireccion;
	}

	@Transactional
	@Override
	public ResultBean actualizarDireccion(String numColegiado, String nif, Long idDireccionPrincipal, Long idDireccionBorrar) {
		ResultBean respuesta = new ResultBean();
		try {
			List<IntervinienteTraficoVO> lista = intervinienteTraficoDao.getExpedientesDireccion(nif, numColegiado, idDireccionBorrar);
			if (lista != null && !lista.isEmpty()) {
				for (IntervinienteTraficoVO interTraficoVO : lista) {
					interTraficoVO.setIdDireccion(idDireccionPrincipal);
					intervinienteTraficoDao.guardarOActualizar(interTraficoVO);
				}
			}
		} catch (Exception e) {
			respuesta.setError(true);
			respuesta.setMensaje("Error al actualizar la dirección de un interviniente: " + e.getMessage());
			log.error("Error al actualizar la dirección de un interviniente", e);
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ResultBean eliminarInterviniete(String nif, String numColegiado, String numExpediente, String tipoInterviniente) {
		ResultBean resultado = new ResultBean();
		try {
			if (nif != null && !nif.isEmpty()
				&& numColegiado != null && !numColegiado.isEmpty()
				&& numExpediente != null && !numExpediente.isEmpty()
				&& tipoInterviniente != null && !tipoInterviniente.isEmpty()) {
				IntervinienteTraficoVO intervinienteTraficoVO = intervinienteTraficoDao.getIntervinientePorNifColegiadoExpedienteYTipo(nif,numColegiado,new BigDecimal(numExpediente),tipoInterviniente);
				if (intervinienteTraficoVO != null) {
					ResultBean resultValEstado = validarEstadoTramitePermitidosBorrado(intervinienteTraficoVO);
					if (!resultValEstado.getError()) {
						intervinienteTraficoDao.borrar(intervinienteTraficoVO);
						resultado.setMensaje("El interviniente con nif: " + nif +", del colegiado: " + numColegiado + ", del tramite: " + numExpediente +
							" y del tipo : " + TipoInterviniente.convertirTexto(tipoInterviniente) + "se han eliminado correctamente.");
					} else {
						resultado = resultValEstado;
					}
				} else {
					resultado.setError(true);
					resultado.setMensaje("Ha sucedido un error a la hora de obtener el interviniente con nif: " + nif +", del colegiado: " + numColegiado +
							", del tramite: " + numExpediente + " y del tipo : " + TipoInterviniente.convertirTexto(tipoInterviniente));
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Debe de indicar el nif o numColegiado para poder eliminar un interviniente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el interviniente con nif: " + nif +", del colegiado: " + numColegiado + 
					", del tramite: " + numExpediente + " y del tipo : " + TipoInterviniente.convertirTexto(tipoInterviniente) + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar el interviniente con nif: " + nif +", del colegiado: " + numColegiado
					+ ", del tramite: " + numExpediente + " y del tipo : " + TipoInterviniente.convertirTexto(tipoInterviniente));
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean validarEstadoTramitePermitidosBorrado(IntervinienteTraficoVO intervinienteTraficoVO) {
		ResultBean resultado = new ResultBean();
		if (intervinienteTraficoVO.getTramiteTrafico() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha podido obtener el tramite relacionado al interviniente con nif: " + intervinienteTraficoVO.getId().getNif() +", del colegiado: " + intervinienteTraficoVO.getId().getNumColegiado()
					+ ", del tramite: " + intervinienteTraficoVO.getId().getNumExpediente() + " y del tipo : " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente()));
		} else if (!EstadoTramiteTrafico.Iniciado.getValorEnum().equals(intervinienteTraficoVO.getTramiteTrafico().getEstado().toString())
				&& !EstadoTramiteTrafico.Tramitable.getValorEnum().equals(intervinienteTraficoVO.getTramiteTrafico().getEstado().toString())
				&& !EstadoTramiteTrafico.No_Tramitable.getValorEnum().equals(intervinienteTraficoVO.getTramiteTrafico().getEstado().toString())
				&& !EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum().equals(intervinienteTraficoVO.getTramiteTrafico().getEstado().toString())
				&& !EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum().equals(intervinienteTraficoVO.getTramiteTrafico().getEstado().toString())
				&& !EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(intervinienteTraficoVO.getTramiteTrafico().getEstado().toString())
				&& !EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(intervinienteTraficoVO.getTramiteTrafico().getEstado().toString())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede eliminar el interviniente con nif: " + intervinienteTraficoVO.getId().getNif() +", del colegiado: " + intervinienteTraficoVO.getId().getNumColegiado()
					+ ", del tramite: " + intervinienteTraficoVO.getId().getNumExpediente() + " y del tipo : " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente()) +
					" porque el tramite se encuentra en estado: " + EstadoTramiteTrafico.convertirTexto(intervinienteTraficoVO.getTramiteTrafico().getEstado().toString()));
		}
		return resultado;
	}

	private void modificarDatosCombos(IntervinienteTraficoVO intervinienteVO) {
		intervinienteVO.setConceptoRepre(utiles.convertirCombo(intervinienteVO.getConceptoRepre()));
		intervinienteVO.setIdMotivoTutela(utiles.convertirCombo(intervinienteVO.getIdMotivoTutela()));
		intervinienteVO.setCodigoIae(utiles.convertirCombo(intervinienteVO.getCodigoIae()));
	}

	public IntervinienteTraficoDao getIntervinienteTraficoDao() {
		return intervinienteTraficoDao;
	}

	public void setIntervinienteTraficoDao(IntervinienteTraficoDao intervinienteTraficoDao) {
		this.intervinienteTraficoDao = intervinienteTraficoDao;
	}

	public ServicioPersonaDireccion getServicioPersonaDireccion() {
		return servicioPersonaDireccion;
	}

	public void setServicioPersonaDireccion(ServicioPersonaDireccion servicioPersonaDireccion) {
		this.servicioPersonaDireccion = servicioPersonaDireccion;
	}

	public ServicioPersona getServicioPersona() {
		return servicioPersona;
	}

	public void setServicioPersona(ServicioPersona servicioPersona) {
		this.servicioPersona = servicioPersona;
	}

	public ServicioDireccion getServicioDireccion() {
		return servicioDireccion;
	}

	public void setServicioDireccion(ServicioDireccion servicioDireccion) {
		this.servicioDireccion = servicioDireccion;
	}
}