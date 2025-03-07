package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoCambioServicioDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafCambioServicioVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoCambioServicio;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCambioServicioBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.EvolucionTramiteTraficoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafCambioServicioDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioEvolucionVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import general.utiles.Anagrama;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTramiteTraficoCambioServicioImpl implements ServicioTramiteTraficoCambioServicio {

	private static final long serialVersionUID = 4922950368084169509L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoCambioServicioImpl.class);

	@Autowired
	private TramiteTraficoCambioServicioDao tramiteTraficoCambioServicioDao;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioEvolucionTramite servicioEvolucionTramite;

	@Autowired
	private ServicioMunicipio servicioMunicipio;

	@Autowired
	private ServicioTasa servicioTasa;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioEvolucionVehiculo servicioEvolucionVehiculo;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafCambioServicioDto getTramiteCambServ(BigDecimal numExpediente, boolean tramiteCompleto) {
		log.debug("Recuperar el trámite cambio de servicio: " + numExpediente);
		TramiteTrafCambioServicioDto result = null;
		try {
			TramiteTrafCambioServicioVO tramite = tramiteTraficoCambioServicioDao.getTramiteCambServ(numExpediente,
					tramiteCompleto);

			if (tramite != null) {
				result = conversor.transform(tramite, TramiteTrafCambioServicioDto.class);

				if (tramite.getIntervinienteTraficos() != null) {
					for (IntervinienteTraficoVO interviniente : tramite.getIntervinienteTraficosAsList()) {
						if (interviniente.getId().getTipoInterviniente()
								.equals(TipoInterviniente.Titular.getValorEnum())) {
							result.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (interviniente.getId().getTipoInterviniente()
								.equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
							result.setRepresentanteTitular(
									conversor.transform(interviniente, IntervinienteTraficoDto.class));
						}
					}
				}

			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de cambio de servicio: " + numExpediente, e,
					numExpediente.toString());
		}
		return result;
	}

	@Override
	@Transactional
	public ResultadoCambioServicioBean consultarPersona(TramiteTrafCambioServicioDto tramiteTraficoCambServ,
			String tipoIntervinienteBuscar) {
		ResultadoCambioServicioBean resultado = new ResultadoCambioServicioBean(Boolean.FALSE);
		try {
			if (TipoInterviniente.Titular.getNombreEnum().equals(tipoIntervinienteBuscar)) {
				IntervinienteTraficoDto interviniente = servicioIntervinienteTrafico.crearIntervinienteNif(
						tramiteTraficoCambServ.getTitular().getPersona().getNif(),
						tramiteTraficoCambServ.getNumColegiado());
				if (interviniente != null) {
					tramiteTraficoCambServ.setTitular(interviniente);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No se ha podido recuperar el interviniente");
				}

			} else if (TipoInterviniente.RepresentanteTitular.getNombreEnum().equals(tipoIntervinienteBuscar)) {
				IntervinienteTraficoDto interviniente = servicioIntervinienteTrafico.crearIntervinienteNif(
						tramiteTraficoCambServ.getRepresentanteTitular().getPersona().getNif(),
						tramiteTraficoCambServ.getNumColegiado());
				if (interviniente != null) {
					tramiteTraficoCambServ.setRepresentanteTitular(interviniente);
					;
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No se ha podido recuperar el interviniente");
				}

			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de crear el interviniente , error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No se ha podido recuperar el interviniente");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoCambioServicioBean consultaVehiculo(TramiteTrafCambioServicioDto tramiteTraficoCambServ,
			String matriculaBusqueda) {
		ResultadoCambioServicioBean resultado = new ResultadoCambioServicioBean(Boolean.FALSE);
		VehiculoDto vehiculoDto = null;
		if (matriculaBusqueda != null && !matriculaBusqueda.equals("")) {
			vehiculoDto = servicioVehiculo.getVehiculoDto(null, tramiteTraficoCambServ.getNumColegiado(),
					matriculaBusqueda.toUpperCase(), null, null, EstadoVehiculo.Activo);
		}
		if (vehiculoDto == null) {
			vehiculoDto = new VehiculoDto();
			vehiculoDto.setMatricula(matriculaBusqueda);
		}
		tramiteTraficoCambServ.setVehiculoDto(vehiculoDto);

		return resultado;
	}

	@Override
	@Transactional
	public ResultadoCambioServicioBean guardarTramite(TramiteTrafCambioServicioDto tramiteTraficoCambServ,
			BigDecimal idUsuario, boolean desdeValidar, boolean admin) {
		ResultadoCambioServicioBean resultado = new ResultadoCambioServicioBean(Boolean.FALSE);
		TramiteTrafCambioServicioVO tramiteCambioServicioVO = new TramiteTrafCambioServicioVO();
		try {
			if (tramiteTraficoCambServ.getJefaturaTraficoDto() == null
					|| tramiteTraficoCambServ.getJefaturaTraficoDto().getJefaturaProvincial() == null
					|| "-1".equals(tramiteTraficoCambServ.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (tramiteTraficoCambServ.getIdContrato() != null) {
					ContratoDto contratoDto = servicioContrato.getContratoDto(tramiteTraficoCambServ.getIdContrato());
					tramiteTraficoCambServ.setJefaturaTraficoDto(contratoDto.getJefaturaTraficoDto());
				}
			}

			if (tramiteTraficoCambServ.getRefPropia() != null && !tramiteTraficoCambServ.getRefPropia().isEmpty()) {
				tramiteCambioServicioVO.setRefPropia(tramiteTraficoCambServ.getRefPropia());
			}
			if (tramiteTraficoCambServ.getImprPermisoCirculacion() != null
					&& "true".equals(tramiteTraficoCambServ.getImprPermisoCirculacion().toString())) {
				tramiteCambioServicioVO.setImprPermisoCirculacion("SI");
			}
			tramiteCambioServicioVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
			UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
			tramiteTraficoCambServ.setUsuarioDto(usuario);
			tramiteCambioServicioVO = convertirDTOVO(tramiteTraficoCambServ);

			ResultadoCambioServicioBean respuestaVehiculo = guardarVehiculo(tramiteTraficoCambServ,
					tramiteCambioServicioVO, usuario, ServicioVehiculo.CONVERSION_VEHICULO_CAMBIO_SERVICIO, admin);
			if (respuestaVehiculo.getListaMensajes() != null) {
				resultado.addListaMensajes(respuestaVehiculo.getListaMensajes());
			}
			if(tramiteTraficoCambServ.getNumExpediente() != null) {
				TasaDto codTasa = servicioTasa.getTasaExpediente(tramiteTraficoCambServ.getTasa().getCodigoTasa(),tramiteTraficoCambServ.getNumExpediente(), TipoTasa.CuatroCuatro.getValorEnum());
				if(codTasa != null && !codTasa.getCodigoTasa().equalsIgnoreCase(tramiteTraficoCambServ.getTasa().getCodigoTasa())) {
					servicioTasa.desasignarTasa(codTasa);
					codTasa = null;
				}
				if (codTasa == null && tramiteTraficoCambServ.getTasa() != null && tramiteTraficoCambServ.getTasa().getCodigoTasa() != null
						&& !"-1".equals(tramiteTraficoCambServ.getTasa().getCodigoTasa())) {
					ResultBean respuestaTasaExpediente = servicioTasa.asignarTasa(
							tramiteTraficoCambServ.getTasa().getCodigoTasa(),
							tramiteCambioServicioVO.getNumExpediente());
					resultado.addListaMensajes(respuestaTasaExpediente.getListaMensajes());
				}
			}
			ResultBean respuestaTramite = guardarTramite(tramiteTraficoCambServ, tramiteCambioServicioVO);
			if (respuestaTramite != null && !respuestaTramite.getError()) {
				//resultado.addAttachment(NUMEXPEDIENTE, tramiteCambioServicioVO.getNumExpediente());

				ResultadoCambioServicioBean respuestaInterviniente = guardarIntervinientes(tramiteTraficoCambServ,
						tramiteCambioServicioVO, usuario);
				if (respuestaInterviniente.getListaMensajes() != null) {
					resultado.addListaMensajes(respuestaInterviniente.getListaMensajes());
				}
			} else {
				log.error("Error al guardar el trámite de cambio de servicio: "
						+ tramiteCambioServicioVO.getNumExpediente() + ". Mensaje: " + respuestaTramite.getMensaje());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultado.setError(true);
				resultado.addMensajeALista(respuestaTramite.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el cambio de servicio, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de guardar el cambio de servicio.");
		}
		if (resultado != null && resultado.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean guardarTramite(TramiteTrafCambioServicioDto tramiteTraficoCambServ,
			TramiteTrafCambioServicioVO tramiteCambioServicioVO) {

		ResultBean result = new ResultBean();
		try {
			if (tramiteCambioServicioVO.getNumExpediente() == null) {
				BigDecimal numExpediente = servicioTramiteTrafico
						.generarNumExpediente(tramiteCambioServicioVO.getNumColegiado());
				log.debug("Creación trámite de cambio de servicio: " + numExpediente);
				tramiteTraficoCambServ.setNumExpediente(numExpediente);
				tramiteCambioServicioVO.setNumExpediente(tramiteTraficoCambServ.getNumExpediente());
				if (tramiteTraficoCambServ.getTasa() != null && tramiteTraficoCambServ.getTasa().getCodigoTasa() != null
						&& !"-1".equals(tramiteTraficoCambServ.getTasa().getCodigoTasa())) {
					ResultBean respuestaTasaExpediente = servicioTasa.asignarTasa(
							tramiteTraficoCambServ.getTasa().getCodigoTasa(),
							tramiteCambioServicioVO.getNumExpediente());
					result.addListaMensajes(respuestaTasaExpediente.getListaMensajes());
				}
				if (tramiteCambioServicioVO.getEstado() == null
						|| (tramiteCambioServicioVO.getEstado() != null && !EstadoTramiteTrafico.Duplicado
								.getValorEnum().equals(tramiteTraficoCambServ.getEstado()))) {
					tramiteCambioServicioVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
				}
				// Actualizar la evolucion vehiculo
				if (tramiteCambioServicioVO.getVehiculo() != null) {
					servicioEvolucionVehiculo.actualizarEvolucionVehiculoCreacionOCopia(
							tramiteCambioServicioVO.getVehiculo().getIdVehiculo(), numExpediente);
				}
				Fecha fecha = utilesFecha.getFechaHoraActualLEG();
				tramiteCambioServicioVO.setFechaUltModif(fecha.getFechaHora());
				tramiteCambioServicioVO.setFechaAlta(fecha.getFechaHora());
				String estadoAnterior = tramiteTraficoCambServ.getEstado();
				tramiteTraficoCambioServicioDao.guardarOActualizar(tramiteCambioServicioVO);
				guardarEvolucionTramite(tramiteTraficoCambServ, estadoAnterior,
						EstadoTramiteTrafico.Iniciado.getValorEnum());
			} else {
				log.debug("Actualización trámite de cambio de servicio: " + tramiteCambioServicioVO.getNumExpediente());
				tramiteCambioServicioVO.setFechaUltModif(utilesFecha.getFechaHoraActualLEG().getFechaHora());
				try {
					String estadoAnterior = tramiteTraficoCambServ.getEstado();
					tramiteCambioServicioVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
					guardarEvolucionTramite(tramiteTraficoCambServ, estadoAnterior,
							EstadoTramiteTrafico.Iniciado.getValorEnum());
				} catch (Exception e) {
					log.error(
							"Error al guardar la evolución del trámite de cambio de servicio: "
									+ tramiteCambioServicioVO.getNumExpediente() + ". Mensaje: " + e.getMessage(),
							e, tramiteCambioServicioVO.getNumExpediente().toString());
				}
				tramiteCambioServicioVO.setNumExpediente(tramiteTraficoCambServ.getNumExpediente());
				tramiteCambioServicioVO = tramiteTraficoCambioServicioDao.actualizar(tramiteCambioServicioVO);
			}
		} catch (Exception e) {
			log.error("Error al guardar el trámite de cambio de servicio: " + tramiteCambioServicioVO.getNumExpediente()
					+ ". Mensaje: " + e.getMessage(), e, tramiteCambioServicioVO.getNumExpediente().toString());
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		if (result != null && result.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean pendientesEnvioExcel(TramiteTrafCambioServicioDto tramiteTraficoCambServ, BigDecimal idUsuario) {
		log.debug("Pendiente de Envío a Excel del trámite duplicado: " + tramiteTraficoCambServ.getNumExpediente());
		ResultBean respuesta = null;
		try {
			if (EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteTraficoCambServ.getEstado())) {
				respuesta = comprobarJefaturaEnvio(tramiteTraficoCambServ);
				if (respuesta == null) {
					respuesta = servicioCredito.validarCreditos(TipoTramiteTrafico.Duplicado.getValorEnum(),
							tramiteTraficoCambServ.getIdContrato(), new BigDecimal(1));
					if (!respuesta.getError()) {
						respuesta = servicioCredito.descontarCreditos(TipoTramiteTrafico.Duplicado.getValorEnum(),
								tramiteTraficoCambServ.getIdContrato(), new BigDecimal(1), idUsuario,
								ConceptoCreditoFacturado.EBD, tramiteTraficoCambServ.getNumExpediente().toString());
						if (!respuesta.getError()) {
							respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(
									tramiteTraficoCambServ.getNumExpediente(),
									EstadoTramiteTrafico.convertir(tramiteTraficoCambServ.getEstado()),
									EstadoTramiteTrafico.Pendiente_Envio_Excel, true,
									TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
							if (respuesta.getError()) {
								servicioCredito.devolverCreditos(TipoTramiteTrafico.Duplicado.getValorEnum(),
										tramiteTraficoCambServ.getIdContrato(), 1, idUsuario,
										ConceptoCreditoFacturado.DEBD,
										tramiteTraficoCambServ.getNumExpediente().toString());
							}
						}
					}
				}
			} else {
				respuesta = new ResultBean(true, "El trámite debe de estar en Validado PDF");
			}
		} catch (Exception e) {
			log.error("Error pendiente envio excel duplicado: " + tramiteTraficoCambServ.getNumExpediente(), e,
					tramiteTraficoCambServ.getNumExpediente().toString());
		}
		return respuesta;
	}

	private ResultBean comprobarJefaturaEnvio(TramiteTrafCambioServicioDto tramiteTraficoCambServ) {
		ResultBean resultado = null;
		if (!JefaturasJPTEnum.MADRID.getJefatura()
				.equals(tramiteTraficoCambServ.getJefaturaTraficoDto().getJefaturaProvincial())
				&& !JefaturasJPTEnum.ALCORCON.getJefatura()
						.equals(tramiteTraficoCambServ.getJefaturaTraficoDto().getJefaturaProvincial())
				&& !JefaturasJPTEnum.ALCALADEHENARES.getJefatura()
						.equals(tramiteTraficoCambServ.getJefaturaTraficoDto().getJefaturaProvincial())) {
			if (JefaturasJPTEnum.AVILA.getJefatura()
					.equals(tramiteTraficoCambServ.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))) {
					resultado = new ResultBean(true,
							"El proceso de envio de Cambio de Servicio para Avila no esta habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura()
					.equals(tramiteTraficoCambServ.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))) {
					resultado = new ResultBean(true,
							"El proceso de envio de Cambio de Servicio para Ciudad Real no esta habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.CUENCA.getJefatura()
					.equals(tramiteTraficoCambServ.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))) {
					resultado = new ResultBean(true,
							"El proceso de envio de Cambio de Servicio para Cuenca no esta habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.GUADALAJARA.getJefatura()
					.equals(tramiteTraficoCambServ.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))) {
					resultado = new ResultBean(true,
							"El proceso de envio de Cambio de Servicio para Guadalajara no esta habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.SEGOVIA.getJefatura()
					.equals(tramiteTraficoCambServ.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))) {
					resultado = new ResultBean(true,
							"El proceso de envio de Cambio de Servicio para Segovia no esta habilitado en este momento");
				}
			} else {
				resultado = new ResultBean(true,
						"El proceso de envio de Cambio de Servicio no está habilitado para la jefatura del tramite");
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoCambioServicioBean validarTramite(TramiteTrafCambioServicioDto tramiteTraficoCambServ,
			BigDecimal idUsuario) {
		ResultadoCambioServicioBean resultado = new ResultadoCambioServicioBean(Boolean.FALSE);
		try {
			TramiteTrafCambioServicioVO trafCambioServicioVO = servicioTramiteTrafico
					.getTramiteCambioServicio(tramiteTraficoCambServ.getNumExpediente(), Boolean.TRUE);
			resultado = validarDatosCambServ(trafCambioServicioVO);
			if (!resultado.isError()) {
				String estadoAnterior = trafCambioServicioVO.getEstado().toString();
				trafCambioServicioVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Validado_PDF.getValorEnum()));
				tramiteTraficoCambioServicioDao.guardar(trafCambioServicioVO);
				guardarEvolucionTramite(tramiteTraficoCambServ, estadoAnterior,
						EstadoTramiteTrafico.Validado_PDF.getValorEnum());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el cambio de servicio, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de guardar el cambio de servicio.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoCambioServicioBean validarDatosCambServ(TramiteTrafCambioServicioVO trafCambioServicioVO) {
		ResultadoCambioServicioBean resultado = new ResultadoCambioServicioBean(Boolean.FALSE);
		if (trafCambioServicioVO.getNumExpediente() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("No existe el trámite. ");
		}
		if (trafCambioServicioVO.getVehiculo() == null || trafCambioServicioVO.getVehiculo().getMatricula() == null
				|| trafCambioServicioVO.getVehiculo().getMatricula().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Debe de rellenar la matricula para poder realizar el trámite. ");
		}
		if (trafCambioServicioVO.getVehiculo() == null || trafCambioServicioVO.getVehiculo().getIdServicio() == null
				|| trafCambioServicioVO.getVehiculo().getIdServicio().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Debe de indicar el servicio nuevo para poder realizar el trámite. ");
		}
		if (trafCambioServicioVO.getTasa() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Debe de indicar una tasa para poder realizar el trámite. ");
		}
		if ("NO".equals(trafCambioServicioVO.getImprPermisoCirculacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(
					"Tiene que marcar el check de impresión de permiso para solicitar el cambio de servicio. ");
		}
		if (trafCambioServicioVO.getJefaturaTrafico().getJefaturaProvincial() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("La jefatura es obligatoria. ");
		}
		return resultado;
	}

	private TramiteTrafCambioServicioVO convertirDTOVO(TramiteTrafCambioServicioDto tramiteTraficoCambServ) {
		TramiteTrafCambioServicioVO tramiteVO = conversor.transform(tramiteTraficoCambServ,
				TramiteTrafCambioServicioVO.class);

		if (tramiteTraficoCambServ.getContrato() == null) {
			ContratoVO contrato = new ContratoVO();
			contrato.setIdContrato(tramiteTraficoCambServ.getIdContrato().longValue());
			tramiteVO.setContrato(contrato);
		}

		if (tramiteTraficoCambServ.getTitular() != null && tramiteTraficoCambServ.getTitular().getPersona() != null
				&& tramiteTraficoCambServ.getTitular().getPersona().getNif() != null
				&& !tramiteTraficoCambServ.getTitular().getPersona().getNif().isEmpty()
				&& (tramiteTraficoCambServ.getTitular().getNifInterviniente() == null
						|| tramiteTraficoCambServ.getTitular().getNifInterviniente().isEmpty())) {
			tramiteTraficoCambServ.getTitular()
					.setNifInterviniente(tramiteTraficoCambServ.getTitular().getPersona().getNif());
		}
		if (tramiteTraficoCambServ.getTitular() != null
				&& tramiteTraficoCambServ.getTitular().getTipoInterviniente() != null
				&& tramiteTraficoCambServ.getTitular().getNifInterviniente() != null
				&& !tramiteTraficoCambServ.getTitular().getTipoInterviniente().isEmpty()
				&& !tramiteTraficoCambServ.getTitular().getNifInterviniente().isEmpty()) {
			if (tramiteVO.getIntervinienteTraficos() == null) {
				tramiteVO.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteTraficoCambServ.getTitular().setNumColegiado(tramiteTraficoCambServ.getNumColegiado());
			IntervinienteTraficoVO titularVO = conversor.transform(tramiteTraficoCambServ.getTitular(),
					IntervinienteTraficoVO.class);
			if (titularVO != null && titularVO.getDireccion() != null
					&& titularVO.getDireccion().getNombreMunicipio() != null
					&& !titularVO.getDireccion().getNombreMunicipio().isEmpty()) {
				MunicipioDto municipioDto = servicioMunicipio.getMunicipioPorNombre(
						titularVO.getDireccion().getNombreMunicipio(), titularVO.getDireccion().getIdProvincia());
				if (municipioDto != null) {
					titularVO.getDireccion().setIdMunicipio(municipioDto.getIdMunicipio());
				}
			}
			tramiteVO.getIntervinienteTraficos().add(titularVO);
		}

		if (tramiteTraficoCambServ.getRepresentanteTitular() != null
				&& tramiteTraficoCambServ.getRepresentanteTitular().getPersona() != null
				&& tramiteTraficoCambServ.getRepresentanteTitular().getPersona().getNif() != null
				&& !tramiteTraficoCambServ.getRepresentanteTitular().getPersona().getNif().isEmpty()
				&& (tramiteTraficoCambServ.getRepresentanteTitular().getNifInterviniente() == null
						|| tramiteTraficoCambServ.getRepresentanteTitular().getNifInterviniente().isEmpty())) {
			tramiteTraficoCambServ.getRepresentanteTitular()
					.setNifInterviniente(tramiteTraficoCambServ.getRepresentanteTitular().getPersona().getNif());
		}
		if (tramiteTraficoCambServ.getRepresentanteTitular() != null
				&& tramiteTraficoCambServ.getRepresentanteTitular().getTipoInterviniente() != null
				&& tramiteTraficoCambServ.getRepresentanteTitular().getNifInterviniente() != null
				&& !tramiteTraficoCambServ.getRepresentanteTitular().getTipoInterviniente().isEmpty()
				&& !tramiteTraficoCambServ.getRepresentanteTitular().getNifInterviniente().isEmpty()) {
			if (tramiteVO.getIntervinienteTraficos() == null) {
				tramiteVO.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteTraficoCambServ.getRepresentanteTitular().setNumColegiado(tramiteTraficoCambServ.getNumColegiado());
			IntervinienteTraficoVO repreTitularVO = conversor
					.transform(tramiteTraficoCambServ.getRepresentanteTitular(), IntervinienteTraficoVO.class);
			if (repreTitularVO != null && repreTitularVO.getDireccion() != null
					&& repreTitularVO.getDireccion().getNombreMunicipio() != null
					&& !repreTitularVO.getDireccion().getNombreMunicipio().isEmpty()) {
				MunicipioDto municipioDto = servicioMunicipio.getMunicipioPorNombre(
						repreTitularVO.getDireccion().getNombreMunicipio(),
						repreTitularVO.getDireccion().getIdProvincia());
				if (municipioDto != null) {
					repreTitularVO.getDireccion().setIdMunicipio(municipioDto.getIdMunicipio());
				}
			}
			tramiteVO.getIntervinienteTraficos().add(repreTitularVO);
		}

		if (tramiteTraficoCambServ.getTasa() == null
				|| !servicioTasa.comprobarTasa(tramiteTraficoCambServ.getTasa().getCodigoTasa(),
						tramiteTraficoCambServ.getNumExpediente())) {
			tramiteVO.setTasa(null);
		}

		return tramiteVO;
	}

	private ResultadoCambioServicioBean guardarVehiculo(TramiteTrafCambioServicioDto tramiteTraficoCambServ,
			TramiteTrafCambioServicioVO tramiteCambioServicioVO, UsuarioDto usuario, String conversion, boolean admin)
			throws ParseException {

		ResultadoCambioServicioBean respuesta = new ResultadoCambioServicioBean(Boolean.FALSE);

		if (tramiteTraficoCambServ.getVehiculoDto().getMatricula() != null
				&& !tramiteTraficoCambServ.getVehiculoDto().getMatricula().isEmpty()) {
			VehiculoVO vehiculoBBDD = conversor.transform(tramiteTraficoCambServ.getVehiculoDto(), VehiculoVO.class);
			Date fechaPresentacion = null;
			if (tramiteTraficoCambServ.getFechaPresentacion() != null) {
				fechaPresentacion = tramiteTraficoCambServ.getFechaPresentacion().getDate();
			}
			vehiculoBBDD.setNumColegiado(tramiteCambioServicioVO.getNumColegiado());
			VehiculoVO vehBBDD = servicioVehiculo.getVehiculoVO(null, vehiculoBBDD.getNumColegiado(),
					vehiculoBBDD.getMatricula(), null, null, EstadoVehiculo.Activo);
			if (vehBBDD != null) {
				if (vehBBDD.getIdServicioAnterior() != null) {
					vehiculoBBDD.setIdServicioAnterior(vehBBDD.getIdServicioAnterior());
				} else {
					vehiculoBBDD.setIdServicioAnterior(vehBBDD.getIdServicio());
				}
			}
			ResultBean respuestaVehiculo = servicioVehiculo.guardarVehiculo(vehiculoBBDD,
					tramiteTraficoCambServ.getNumExpediente(), tramiteTraficoCambServ.getTipoTramite(), usuario,
					fechaPresentacion, conversion, false, admin);
			tramiteCambioServicioVO.getVehiculo().setIdVehiculo(vehiculoBBDD.getIdVehiculo());
			if (respuestaVehiculo.getListaMensajes() != null && !respuestaVehiculo.getListaMensajes().isEmpty()) {
				for (String mensaje : respuestaVehiculo.getListaMensajes()) {
					respuesta.addMensajeALista(mensaje);
				}
			}
		} else {
			tramiteCambioServicioVO.setVehiculo(null);
		}

		return respuesta;
	}

	private ResultadoCambioServicioBean guardarIntervinientes(TramiteTrafCambioServicioDto tramiteTraficoCambServ,
			TramiteTrafCambioServicioVO tramiteCambioServicioVO, UsuarioDto usuario) {
		ResultadoCambioServicioBean respuesta = new ResultadoCambioServicioBean(Boolean.FALSE);
		boolean direccionNueva = false;
		// Guardado de Intervinientes
		if (tramiteCambioServicioVO.getIntervinienteTraficos() != null
				&& tramiteCambioServicioVO.getIntervinienteTraficos().size() > 0) {
			for (IntervinienteTraficoVO interviniente : tramiteCambioServicioVO.getIntervinienteTraficos()) {
				if (interviniente.getPersona() != null && interviniente.getPersona().getId() != null) {
					if (interviniente.getPersona().getId().getNif() != null
							&& !interviniente.getPersona().getId().getNif().isEmpty()) {
						interviniente.getId().setNif(interviniente.getPersona().getId().getNif().toUpperCase());
						interviniente.getId().setNumExpediente(tramiteCambioServicioVO.getNumExpediente());
						interviniente.getPersona().getId().setNumColegiado(tramiteCambioServicioVO.getNumColegiado());
						// Estado para saber que esta activo
						interviniente.getPersona().setEstado(new Long(1));
						// Se crea el anagrama
						String anagrama = Anagrama.obtenerAnagramaFiscal(
								interviniente.getPersona().getApellido1RazonSocial(),
								interviniente.getPersona().getId().getNif());
						interviniente.getPersona().setAnagrama(anagrama);
						// Guardar la persona antes de guardar el interviniente
						String conversion = null;
						if (interviniente.getId().getTipoInterviniente()
								.equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
							conversion = ServicioPersona.CONVERSION_PERSONA_REPRESENTANTE;
						} else {
							conversion = ServicioPersona.CONVERSION_PERSONA_TITULAR;
						}

						// Guardar persona
						ResultBean resultPersona = servicioPersona.guardarActualizar(interviniente.getPersona(),
								tramiteCambioServicioVO.getNumExpediente(), tramiteCambioServicioVO.getTipoTramite(),
								usuario, conversion);

						if (!resultPersona.getError()) {
							// Guardar direccion
							if (interviniente.getDireccion() != null
									&& utiles.convertirCombo(interviniente.getDireccion().getIdProvincia()) != null) {
								ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(
										interviniente.getDireccion(), interviniente.getPersona().getId().getNif(),
										interviniente.getPersona().getId().getNumColegiado(),
										tramiteTraficoCambServ.getTipoTramite(),
										ServicioDireccion.CONVERSION_DIRECCION_INE);
								if (resultDireccion.getError()) {
									respuesta.addMensajeALista("- "
											+ TipoInterviniente
													.convertirTexto(interviniente.getId().getTipoInterviniente())
											+ ": " + resultDireccion.getMensaje());
									interviniente.setIdDireccion(null);
								} else {
									DireccionVO direccion = (DireccionVO) resultDireccion
											.getAttachment(ServicioDireccion.DIRECCION);
									direccionNueva = (Boolean) resultDireccion
											.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
									if (direccion != null) {
										interviniente.setIdDireccion(direccion.getIdDireccion());
									}
									servicioEvolucionPersona.guardarEvolucionPersonaDireccion(
											interviniente.getPersona().getId().getNif(),
											tramiteCambioServicioVO.getNumExpediente(),
											tramiteCambioServicioVO.getTipoTramite(),
											interviniente.getPersona().getId().getNumColegiado(), usuario,
											direccionNueva);
								}
							} else {
								interviniente.setIdDireccion(null);
							}
							// Guardar interviniente
							ResultBean result = servicioIntervinienteTrafico.guardarActualizar(interviniente, null);
							for (String mensaje : result.getListaMensajes()) {
								respuesta.addMensajeALista(mensaje);
							}
						} else {
							respuesta.addMensajeALista(resultPersona.getMensaje());
						}
					} else {
						servicioIntervinienteTrafico.eliminar(interviniente);
					}
				}
			}
		} else {
			respuesta.setError(Boolean.TRUE);
			respuesta.addMensajeALista("No se ha guardado ningún interviniente");
		}
		return respuesta;
	}

	private void guardarEvolucionTramite(TramiteTrafCambioServicioDto tramiteTraficoCambServ, String estadoAnterior,
			String estadoNuevo) {
		if (!estadoAnterior.equals(estadoNuevo)) {
			EvolucionTramiteTraficoDto evolucion = new EvolucionTramiteTraficoDto();
			if (tramiteTraficoCambServ.getEstado() != null && !tramiteTraficoCambServ.getEstado().isEmpty()) {
				evolucion.setEstadoAnterior(new BigDecimal(estadoAnterior));
			} else {
				evolucion.setEstadoAnterior(BigDecimal.ZERO);
			}
			evolucion.setEstadoNuevo(new BigDecimal(estadoNuevo));
			evolucion.setUsuarioDto(tramiteTraficoCambServ.getUsuarioDto());
			evolucion.setNumExpediente(tramiteTraficoCambServ.getNumExpediente());
			servicioEvolucionTramite.guardar(evolucion);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafCambioServicioDto> cambioServicioExcel(String jefatura) {
		List<TramiteTrafCambioServicioDto> listaDtos = new ArrayList<TramiteTrafCambioServicioDto>();
		try {
			List<TramiteTrafCambioServicioVO> listaduplicados = servicioTramiteTrafico.cambioServicioExcel(jefatura);
			if (listaduplicados != null && !listaduplicados.isEmpty()) {
				for (TramiteTrafCambioServicioVO vo : listaduplicados) {
					TramiteTrafCambioServicioDto dto = conversor.transform(vo, TramiteTrafCambioServicioDto.class);
					if (vo.getIntervinienteTraficos() != null) {
						for (IntervinienteTraficoVO interviniente : vo.getIntervinienteTraficosAsList()) {
							if (interviniente.getId().getTipoInterviniente()
									.equals(TipoInterviniente.Titular.getValorEnum())) {
								dto.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
							} else if (interviniente.getId().getTipoInterviniente()
									.equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
								dto.setRepresentanteTitular(
										conversor.transform(interviniente, IntervinienteTraficoDto.class));
							}
						}
						listaDtos.add(dto);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar los cambios de servicio excel", e);
		}
		return listaDtos;
	}

	public ILoggerOegam getLog() {
		return log;
	}

	public void setLog(ILoggerOegam log) {
		this.log = log;
	}

	public TramiteTraficoCambioServicioDao getTramiteTraficoCambioServicioDao() {
		return tramiteTraficoCambioServicioDao;
	}

	public void setTramiteTraficoCambioServicioDao(TramiteTraficoCambioServicioDao tramiteTraficoCambioServicioDao) {
		this.tramiteTraficoCambioServicioDao = tramiteTraficoCambioServicioDao;
	}

	public Conversor getConversor() {
		return conversor;
	}

	public void setConversor(Conversor conversor) {
		this.conversor = conversor;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ServicioEvolucionTramite getServicioEvolucionTramite() {
		return servicioEvolucionTramite;
	}

	public void setServicioEvolucionTramite(ServicioEvolucionTramite servicioEvolucionTramite) {
		this.servicioEvolucionTramite = servicioEvolucionTramite;
	}

	public ServicioMunicipio getServicioMunicipio() {
		return servicioMunicipio;
	}

	public void setServicioMunicipio(ServicioMunicipio servicioMunicipio) {
		this.servicioMunicipio = servicioMunicipio;
	}

	public ServicioTasa getServicioTasa() {
		return servicioTasa;
	}

	public void setServicioTasa(ServicioTasa servicioTasa) {
		this.servicioTasa = servicioTasa;
	}

	public ServicioDireccion getServicioDireccion() {
		return servicioDireccion;
	}

	public void setServicioDireccion(ServicioDireccion servicioDireccion) {
		this.servicioDireccion = servicioDireccion;
	}

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}

	public ServicioPersona getServicioPersona() {
		return servicioPersona;
	}

	public void setServicioPersona(ServicioPersona servicioPersona) {
		this.servicioPersona = servicioPersona;
	}

	public ServicioEvolucionPersona getServicioEvolucionPersona() {
		return servicioEvolucionPersona;
	}

	public void setServicioEvolucionPersona(ServicioEvolucionPersona servicioEvolucionPersona) {
		this.servicioEvolucionPersona = servicioEvolucionPersona;
	}

	public ServicioVehiculo getServicioVehiculo() {
		return servicioVehiculo;
	}

	public void setServicioVehiculo(ServicioVehiculo servicioVehiculo) {
		this.servicioVehiculo = servicioVehiculo;
	}

	public ServicioEvolucionVehiculo getServicioEvolucionVehiculo() {
		return servicioEvolucionVehiculo;
	}

	public void setServicioEvolucionVehiculo(ServicioEvolucionVehiculo servicioEvolucionVehiculo) {
		this.servicioEvolucionVehiculo = servicioEvolucionVehiculo;
	}

}
