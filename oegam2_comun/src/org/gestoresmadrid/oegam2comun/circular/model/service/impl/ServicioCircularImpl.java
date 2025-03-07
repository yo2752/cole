package org.gestoresmadrid.oegam2comun.circular.model.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.circular.model.dao.CircularContratoDao;
import org.gestoresmadrid.core.circular.model.dao.CircularDao;
import org.gestoresmadrid.core.circular.model.enumerados.EstadoCircular;
import org.gestoresmadrid.core.circular.model.enumerados.OperacionCirculares;
import org.gestoresmadrid.core.circular.model.vo.CircularContratoVO;
import org.gestoresmadrid.core.circular.model.vo.CircularVO;
import org.gestoresmadrid.oegam2comun.circular.model.service.ServicioCircular;
import org.gestoresmadrid.oegam2comun.circular.model.service.ServicioEvolucionCircular;
import org.gestoresmadrid.oegam2comun.circular.view.bean.ResultadoCircularBean;
import org.gestoresmadrid.oegam2comun.circular.view.dto.CircularDto;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.properties.model.service.ServicioProperties;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import hibernate.entities.general.Property;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioCircularImpl implements ServicioCircular {

	private static final long serialVersionUID = -5367428033446157793L;
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioCircularImpl.class);

	private Property property;

	@Autowired
	Conversor conversor;

	@Autowired
	CircularDao circularDao;

	@Autowired
	CircularContratoDao circularContratoDao;

	@Autowired
	ServicioEvolucionCircular servicioEvolucionCircular;

	@Autowired
	ServicioProperties servicioProperties;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultadoCircularBean gestionarCirculares() {
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		try {
			Date fecha = new Date();
			gestionarCircularesCaducadas(fecha, resultado);
			gestionarRepeticionCirculares(fecha, resultado);
			actualizarPropertie(resultado.getValorPropertie());
		} catch (Throwable e) {
			LOG.error("Ha sucedido un error a la hora de gestionar las circulares en el proceso,error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de gestionar las circulares en el proceso.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private void actualizarPropertie(String valorPropertie) {
		servicioProperties.actualizarProperties("comprobar.circulares", valorPropertie, "FRONTAL");
	}

	private void gestionarRepeticionCirculares(Date fecha, ResultadoCircularBean resultado) throws ParseException {
		List<CircularVO> listaCirculares = circularDao.getListaCircularesRepeticion();
		if (listaCirculares != null && !listaCirculares.isEmpty()) {
			int numCaducadas = 0;
			for (CircularVO circularVO : listaCirculares) {
				if ((BigDecimal.ONE.compareTo(circularVO.getRepeticiones()) == 0)) {
					Date fechaFinal = utilesFecha.sumaDiasAFecha(new Fecha(circularVO.getFechaInicio()),
							circularVO.getDias());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(fechaFinal);
					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
					circularVO.setFechaFin(calendar.getTime());
					if (circularVO.getDias() != null
							&& (utilesFecha.compararFechaDate(fecha, circularVO.getFechaFin()) == 1)) {
						String estadoAnt = circularVO.getEstado();
						if (circularVO.getEstado() != null) {
							circularVO.setEstado(EstadoCircular.Finalizado.getValorEnum());
						}
						circularDao.actualizar(circularVO);
						servicioEvolucionCircular.guardarEvolucion(circularVO.getIdCircular(), BigDecimal.ZERO,
								new Date(), estadoAnt, circularVO.getEstado(), OperacionCirculares.CAMBIO_ESTADO);
						numCaducadas++;
					}
				}
			}
			resultado.setValorPropertie(numCaducadas == listaCirculares.size() ? "NO" : "SI");
		}
	}

	private void gestionarCircularesCaducadas(Date fecha, ResultadoCircularBean resultado) throws ParseException {
		List<CircularVO> listaCirculares = circularDao.getListaCircularesActivasFecha();
		if (listaCirculares != null && !listaCirculares.isEmpty()) {
			int numCaducadas = 0;
			for (CircularVO circularVO : listaCirculares) {
				if ((BigDecimal.ONE.compareTo(circularVO.getFecha()) == 0
						&& utilesFecha.compararFechaDate(fecha, circularVO.getFechaFin()) == 1)) {
					String estadoAnt = circularVO.getEstado();
					if (circularVO.getEstado() != null) {
						circularVO.setEstado(EstadoCircular.Finalizado.getValorEnum());
					}
					circularDao.actualizar(circularVO);
					servicioEvolucionCircular.guardarEvolucion(circularVO.getIdCircular(), new BigDecimal("000"),
							new Date(), estadoAnt, circularVO.getEstado(), OperacionCirculares.CAMBIO_ESTADO);
					numCaducadas++;
				}
			}
			resultado.setValorPropertie(numCaducadas == listaCirculares.size() ? "NO" : "SI");
		}else {
			resultado.setValorPropertie("NO");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoCircularBean gestionarCircularesContrato(Long idContrato, Boolean esInterceptor) {
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		try {
			List<CircularVO> listaCirculares = circularDao.getListaCircularesActivas();
			if (listaCirculares != null && !listaCirculares.isEmpty()) {
				for (CircularVO circular : listaCirculares) {
					CircularContratoVO circularContratoVO = circularContratoDao
							.getCircularContrato(circular.getIdCircular(), idContrato);
					if (circularContratoVO == null) {
						resultado.setCircular(conversor.transform(circular, CircularDto.class));
						resultado.setError(Boolean.FALSE);
						break;
					} else if (esInterceptor) {
						resultado.setError(Boolean.TRUE);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen circulares activas ");
			}

		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de gestionar las circulares para el contrato: " + idContrato
					+ ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de gestionar las circulares para el contrato: " + idContrato);
		}
		return resultado;
	}

	@Override
	public List<CircularDto> convertirListaEnBeanPantallaConsulta(List<CircularVO> listaBBDD) {
		List<CircularDto> listaCircular = new ArrayList<>();
		for (CircularVO circularVO : listaBBDD) {
			CircularDto circularDto = new CircularDto();
			circularDto.setIdCircular(circularVO.getIdCircular());
			circularDto.setNumCircular(circularVO.getNumCircular());
			circularDto.setEstado(EstadoCircular.convertirTexto(circularVO.getEstado()));
			circularDto.setFechaInicio(utilesFecha.getFechaFracionada(circularVO.getFechaInicio()));
			circularDto.setFechaFin(utilesFecha.getFechaFracionada(circularVO.getFechaFin()));
			circularDto.setDias(circularVO.getDias());
			circularDto.setTexto(circularVO.getTexto());
			listaCircular.add(circularDto);
		}
		return listaCircular;
	}

	@Override
	@Transactional
	public ResultadoCircularBean guardarCircular(CircularDto circularDto, BigDecimal idUsuario) {
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		try {
			CircularVO circularVO = conversor.transform(circularDto, CircularVO.class);
			circularVO.setEstado(EstadoCircular.Iniciado.getValorEnum());

			Calendar calendar = Calendar.getInstance();
			if (circularDto.getFechaInicio() != null && circularDto.getFechaInicio().getDate() != null) {
				calendar.setTime(circularDto.getFechaInicio().getDate());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La fecha de inicio no puede ir vacío.");
			}
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			circularVO.setFechaInicio(calendar.getTime());
			if (circularDto.getFechaFin() != null) {
				calendar.setTime(circularDto.getFechaFin().getDate());
			}

			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
			if (circularDto.getRepeticiones()) {
				if (circularDto.getDias() != null && !circularDto.getDias().isEmpty()) {
					circularVO.setDias(circularDto.getDias());
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El número de días no puede ir vacío si está marcado el check de repeticiones.");
				}

				circularVO.setRepeticiones(new BigDecimal("1"));
				circularVO.setFecha(new BigDecimal("0"));
			}
			if (circularDto.getFecha()) {
				circularVO.setFecha(new BigDecimal("1"));
				circularVO.setRepeticiones(new BigDecimal("0"));
				circularVO.setFechaFin(calendar.getTime());
			}
			if (circularDto.getNumCircular() != null && !circularDto.getNumCircular().isEmpty()) {
				circularVO.setNumCircular(circularDto.getNumCircular());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El número de circular no puede ir vacío.");
			}
			if (circularDto.getTexto() != null && !circularDto.getTexto().isEmpty()) {
				circularVO.setTexto(circularDto.getTexto());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El campo de texto no puede ir vacío.");
			}

			String estadoAnt = circularVO.getEstado();
			String estadoNuevo = null;
			if (estadoAnt != null) {
				estadoNuevo = null;
			} else if (EstadoCircular.Iniciado.getValorEnum().equals(estadoAnt)) {
				estadoNuevo = EstadoCircular.Finalizado.getValorEnum();
			}
			circularDao.guardarOActualizar(circularVO);
			servicioEvolucionCircular.guardarEvolucion(circularVO.getIdCircular(), idUsuario, new Date(), estadoAnt,
					estadoNuevo, OperacionCirculares.ALTA);

		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de guardar la circular de OEGAM, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la circular de OEGAM.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoCircularBean cambiarEstados(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario) {
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idsCirculares = codSeleccionados.split("-");
				for (String idCircular : idsCirculares) {
					try {
						ResultadoCircularBean resultCambEstado = cambiarEstadoCircular(new Long(idCircular), estadoNuevo,
								idUsuario);
						if (resultCambEstado.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultCambEstado.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk(resultCambEstado.getMensaje());
						}
					} catch (Exception e) {
						LOG.error("Ha sucedido un error a la hora de cambiar el estado a la circular con id: "
								+ idCircular + ", error: ", e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError(
								"Ha sucedido un error a la hora de cambiar el estado a la circular con id: "
										+ idCircular);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar alguna circular para poder cambiar su estado.");
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de cambiar el estado de las circular, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de las circular.");
		}
		return resultado;
	}

	private ResultadoCircularBean cambiarEstadoCircular(Long idCircular, String estadoNuevo, BigDecimal idUsuario) {
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		try {
			CircularVO circular = getCircularVO(idCircular);
			if (circular != null) {
				Date fecha = new Date();
				String estadoAnt = circular.getEstado();
				circular.setEstado(estadoNuevo);
				circular.setFechaFin(fecha);
				circularDao.actualizar(circular);
				resultado = servicioEvolucionCircular.guardarEvolucion(idCircular, idUsuario, new Date(),
						estadoAnt, estadoNuevo, OperacionCirculares.CAMBIO_ESTADO);
				if (!resultado.getError()) {
					resultado.setMensaje(
							"La circular con id: " + idCircular + " se ha cambiado el estado correctamente.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se encuentran datos de la circular para cambiar el estado.");
			}
		} catch (Throwable e) {
			LOG.error("Ha sucedido un error a la hora de cambiar el estado de la circular, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de la circular.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	public CircularVO getCircularVO(Long id) {
		if (id != null) {
			return circularDao.getCircular(id);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoCircularBean revertir(String[] codSeleccionados, BigDecimal idUsuario) {
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && codSeleccionados.length > 0) {
				Date fechaSol = new Date();
				for (String ids : codSeleccionados) {
					try {
						ResultadoCircularBean resultSolicitar = revertirCircular(new Long(ids), idUsuario);
						if (resultSolicitar.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen()
									.addListaMensajeOk("La circular con id " + ids + " se ha revertido correctamente.");
						}
					} catch (Throwable e) {
						LOG.error("No se ha podido revertir la circular con id: " + ids
								+ ", porque ha sucedido el siguiente error: ", e);
						resultado.getResumen().addNumError();
						resultado.getResumen()
								.addListaMensajeError("No se ha podido revertir la circular con id: " + ids);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder revertir la circular.");
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de revertir las circulares en bloque, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir las circulares en bloque.");
		}
		return resultado;
	}

	private ResultadoCircularBean revertirCircular(Long id, BigDecimal idUsuario)
			throws OegamExcepcion {
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		try {
			CircularVO circularVO = getCircularVO(id);
			resultado = validarCircularRevertir(circularVO);
			Date fechaDia = new Date();
			if (!resultado.getError()) {
				String estadoAnt = circularVO.getEstado();
				circularVO.setEstado(EstadoCircular.Iniciado.getValorEnum());
				circularVO.setFechaInicio(fechaDia);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaDia);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23,59,00);
				circularVO.setFechaFin(calendar.getTime());
				guardarOActualizar(circularVO);
				ResultadoCircularBean resultEstado = servicioEvolucionCircular.guardarEvolucion(id, idUsuario,
						new Date(), estadoAnt, null, OperacionCirculares.REVERTIR);
				if (resultEstado.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultEstado.getMensaje());
				}
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de revertir la circular con id " + id + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir la circular con id: " + id);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private void guardarOActualizar(CircularVO circularVO) {
		try {
			circularDao.guardarOActualizar(circularVO);
		} catch (Exception e) {
			LOG.error(EnumError.error_00001, e.getMessage());
		}
	}

	private ResultadoCircularBean validarCircularRevertir(CircularVO circularVO) {
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		if (circularVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos de la circular.");
		} else if (!EstadoCircular.Finalizado.getValorEnum().equals(circularVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir la circular" + circularVO.getIdCircular()
					+ ", porque no se encuentra finalizada.");
		}
		return resultado;
	}

	@Override
	public ResultadoCircularBean enviarMail(String mensaje) {
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		try {
			String subject = gestorPropiedades.valorPropertie("subject.resumen.circulares");
			String direcciones = gestorPropiedades.valorPropertie("direccion.resumen.circulares");

			ResultBean resultEnvio = servicioCorreo.enviarCorreo(mensaje, Boolean.FALSE, null, subject, direcciones, null, null, null);
		} catch (Exception | OegamExcepcion e) {
			LOG.error("Ha sucedido un error a la hora de enviar el correo en el proceso de circulares, error: ", e);
			resultado.addListaMensaje("Ha sucedido un error a la hora de enviar el correo en el proceso de circulares.");
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public CircularDao getCircularDao() {
		return circularDao;
	}

	public void setCircularDao(CircularDao circularDao) {
		this.circularDao = circularDao;
	}

	public CircularContratoDao getCircularContratoDao() {
		return circularContratoDao;
	}

	public void setCircularContratoDao(CircularContratoDao circularContratoDao) {
		this.circularContratoDao = circularContratoDao;
	}

	public ServicioEvolucionCircular getServicioEvolucionCircular() {
		return servicioEvolucionCircular;
	}

	public void setServicioEvolucionCircular(ServicioEvolucionCircular servicioEvolucionCircular) {
		this.servicioEvolucionCircular = servicioEvolucionCircular;
	}

	@Override
	@Transactional
	public ResultadoCircularBean aceptarCircular(CircularDto circularDto, Long idContrato, Long idUsuario){
		ResultadoCircularBean resultado = new ResultadoCircularBean(Boolean.FALSE);
		try {
			if (circularDto != null && circularDto.getIdCircular() != null) {
				CircularVO circularVO = circularDao.getCircular(circularDto.getIdCircular());
				if (circularVO != null) {
					CircularContratoVO circularContratoVO = circularContratoDao.getCircularContrato(circularVO.getIdCircular(), idContrato);
					if(circularContratoVO == null) {
						circularContratoVO = new CircularContratoVO();
						circularContratoVO.setIdCircular(circularVO.getIdCircular());
						circularContratoVO.setIdContrato(idContrato);
						circularContratoVO.setIdUsuario(idUsuario);
						circularContratoVO.setFecha(new Date());
						circularContratoDao.guardar(circularContratoVO);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar el id de la circular para poder aceptarla.");
			}
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de aceptar la circular, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de aceptar la circular.");
		}
		if(resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

}