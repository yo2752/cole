package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoPresentacionJPT;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoCreacion;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.paises.model.vo.PaisVO;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoBajaDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoProcedureDao;
import org.gestoresmadrid.core.trafico.model.procedure.bean.ValidacionTramite;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.btv.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.colegio.model.service.ServicioColegio;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.EvolucionTramiteTraficoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.utiles.XmlBTVFactory;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioEvolucionVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
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
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioTramiteTraficoBajaImpl implements ServicioTramiteTraficoBaja {

	private static final long serialVersionUID = 2944401905740923994L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoBajaImpl.class);

	@Autowired
	private TramiteTraficoBajaDao tramiteTraficoBajaDao;

	@Autowired
	private TramiteTraficoProcedureDao validacionTramiteDao;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioTasa servicioTasa;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioEvolucionTramite servicioEvolucionTramite;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	private ServicioEvolucionVehiculo servicioEvolucionVehiculo;

	@Autowired
	private ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioColegiado servicioColegiado;

	@Autowired
	private ServicioColegio servicioColegio;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	private ObjectFactory objectFactory;

	private XmlBTVFactory xmlBTVFactory;

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultBean guardarTramite(TramiteTrafBajaDto tramiteDto, BigDecimal idUsuario, boolean desdeValidar, boolean admin, Boolean tienePermisosBtv) {
		Boolean hayTasa = false;
		ResultBean respuesta = new ResultBean();
		TramiteTrafBajaVO tramiteBaja = new TramiteTrafBajaVO();
		try {
			if (tramiteDto != null && tramiteDto.getTasa() != null && tramiteDto.getTasa().getCodigoTasa() != null && !tramiteDto.getTasa().getCodigoTasa().isEmpty() && !"-1".equals(tramiteDto
					.getTasa().getCodigoTasa())) {
				hayTasa = true;
			}
			tramiteDto = cargaNumColegiado(tramiteDto);
			if (tramiteDto.getJefaturaTraficoDto() == null || tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial() == null || "-1".equals(tramiteDto.getJefaturaTraficoDto()
					.getJefaturaProvincial())) {
				if (tramiteDto.getIdContrato() != null) {
					ContratoDto contratoDto = servicioContrato.getContratoDto(tramiteDto.getIdContrato());
					tramiteDto.setJefaturaTraficoDto(contratoDto.getJefaturaTraficoDto());
				}
			}

			// Motivos
			if (tramiteDto.getMotivoBaja() != null) {
				respuesta = procesarMotivoBaja(tramiteDto, hayTasa, desdeValidar, tienePermisosBtv);
			}

			if (!respuesta.getError()) {
				if (tramiteDto.getTasa() != null) {
					ResultBean respuestaDesTasaExpediente = servicioTasa.desasignarTasaExpediente(tramiteDto.getTasa().getCodigoTasa(), tramiteDto.getNumExpediente(), TipoTasa.CuatroUno
							.getValorEnum());
					respuesta.addListaMensajes(respuestaDesTasaExpediente.getListaMensajes());
				}

				ResultBean respuestaDesTasaDuplicado = servicioTasa.desasignarTasaExpediente(tramiteDto.getTasaDuplicado(), tramiteDto.getNumExpediente(), TipoTasa.CuatroCuatro.getValorEnum());
				respuesta.addListaMensajes(respuestaDesTasaDuplicado.getListaMensajes());

				UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
				tramiteDto.setUsuarioDto(usuario);

				// Datos del VO
				tramiteBaja = convertirDTOVO(tramiteDto);

				// Guardamos vehiculo
				ResultBean respuestaVehiculo = guardarVehiculo(tramiteDto, tramiteBaja, usuario, admin);
				respuesta.addListaMensajes(respuestaVehiculo.getListaMensajes());

				ResultBean respuestaTramite = guardarTramite(tramiteDto, tramiteBaja, desdeValidar);
				if (respuestaTramite != null && !respuestaTramite.getError()) {
					respuesta.addAttachment(NUMEXPEDIENTE, tramiteBaja.getNumExpediente());

					// Guardamos interviniente
					ResultBean respuestaInterviniente = guardarIntervinientes(tramiteDto, tramiteBaja, usuario);
					respuesta.addListaMensajes(respuestaInterviniente.getListaMensajes());

					if (tramiteDto.getTasa() != null && tramiteDto.getTasa().getCodigoTasa() != null && !tramiteDto.getTasa().getCodigoTasa().isEmpty() && !"-1".equals(tramiteDto.getTasa()
							.getCodigoTasa())) {
						ResultBean respuestaTasaExpediente = servicioTasa.asignarTasa(tramiteDto.getTasa().getCodigoTasa(), tramiteBaja.getNumExpediente());
						respuesta.addListaMensajes(respuestaTasaExpediente.getListaMensajes());
					}

					if (tramiteDto.getTasaDuplicado() != null && !tramiteDto.getTasaDuplicado().isEmpty() && !"-1".equals(tramiteDto.getTasaDuplicado())) {
						ResultBean respuestaTasaDuplicado = servicioTasa.asignarTasa(tramiteDto.getTasaDuplicado(), tramiteBaja.getNumExpediente());
						respuesta.addListaMensajes(respuestaTasaDuplicado.getListaMensajes());
					}
				} else {
					log.error("Error al guardar el trámite de baja: " + tramiteBaja.getNumExpediente() + ". Mensaje: " + respuestaTramite.getMensaje());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					respuesta.setError(true);
					respuesta.addMensajeALista(respuestaTramite.getMensaje());
				}
			} else {
				log.error("Error al guardar el trámite de baja: " + tramiteBaja.getNumExpediente() + ". Mensaje: " + respuesta.getMensaje());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} catch (Exception e) {
			log.error("Error al guardar el trámite de baja: " + tramiteBaja.getNumExpediente() + ". Mensaje: " + e.getMessage(), e, tramiteDto.getNumExpediente().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.setError(true);
			respuesta.addMensajeALista(e.getMessage());
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ResultBean guardarRespuestaBTV(BigDecimal numExpediente, String respuesta, Boolean isCheck, EstadoTramiteTrafico estado) {
		ResultBean resultado = new ResultBean(false);
		try {
			TramiteTrafBajaVO tramiteTrafBajaVO = getTramiteBajaVO(numExpediente, false);
			if (tramiteTrafBajaVO != null) {
				if (isCheck) {
					tramiteTrafBajaVO.setResCheckBtv(respuesta);
				} else {
					if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(estado.getValorEnum())) {
						tramiteTrafBajaVO.setFechaPresentacion(new Date());
					}
					tramiteTrafBajaVO.setRespuesta(respuesta);
				}
				tramiteTraficoBajaDao.guardarOActualizar(tramiteTrafBajaVO);
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("No se puede guardar la respuesta del Btv porque no existen datos para ese número de expediente.");
			}
		} catch (Exception e) {
			log.error("Error al guardar la respuesta del checkbtv: " + numExpediente + ". Mensaje: ", e, numExpediente.toString());
			resultado.setError(true);
			resultado.addMensajeALista(e.getMessage());
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafBajaVO getTramiteBajaVO(BigDecimal numExpediente, boolean tramiteCompleto) {
		TramiteTrafBajaVO tramiteBaTrafBajaVO = null;
		try {
			tramiteBaTrafBajaVO = servicioTramiteTrafico.getTramiteBaja(numExpediente, tramiteCompleto);
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de baja: " + numExpediente, e, numExpediente.toString());
		}
		return tramiteBaTrafBajaVO;
	}

	private ResultBean guardarVehiculo(TramiteTrafBajaDto tramiteDto, TramiteTrafBajaVO tramiteBaja, UsuarioDto usuario, boolean admin) throws ParseException {
		ResultBean respuesta = new ResultBean();
		// Guardado Vehiculo
		if (tramiteDto.getVehiculoDto().getMatricula() != null && !tramiteDto.getVehiculoDto().getMatricula().isEmpty()) {
			VehiculoVO vehiculoPantalla = conversor.transform(tramiteDto.getVehiculoDto(), VehiculoVO.class);
			Date fechaPresentacion = null;
			if (tramiteDto.getFechaPresentacion() != null) {
				fechaPresentacion = tramiteDto.getFechaPresentacion().getDate();
			}
			ResultBean respuestaVehiculo = servicioVehiculo.guardarVehiculo(vehiculoPantalla, tramiteDto.getNumExpediente(), tramiteDto.getTipoTramite(), usuario, fechaPresentacion,
					ServicioVehiculo.CONVERSION_VEHICULO_BAJAS, false, admin);

			Object attachment = respuestaVehiculo.getAttachment(ServicioVehiculo.VEHICULO);
			if (attachment != null && attachment instanceof VehiculoVO) {
				tramiteBaja.setVehiculo((VehiculoVO) attachment);
			}
			if (respuestaVehiculo.getListaMensajes() != null && !respuestaVehiculo.getListaMensajes().isEmpty()) {
				for (String mensaje : respuestaVehiculo.getListaMensajes()) {
					respuesta.addMensajeALista(mensaje);
				}
			}
		} else {
			tramiteBaja.setVehiculo(null);
		}
		return respuesta;
	}

	private ResultBean guardarTramite(TramiteTrafBajaDto tramiteDto, TramiteTrafBajaVO tramiteBaja, boolean desdeValidar) {
		ResultBean result = new ResultBean();
		try {
			if (tramiteBaja.getNumExpediente() == null) {
				BigDecimal numExpediente = servicioTramiteTrafico.generarNumExpediente(tramiteBaja.getNumColegiado());
				tramiteBaja.setNumExpediente(numExpediente);
				tramiteDto.setNumExpediente(numExpediente);
				if (tramiteBaja.getEstado() == null || (tramiteBaja.getEstado() != null && !EstadoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteBaja.getEstado()))) {
					tramiteBaja.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
				}
				// Actualizar la evolucion vehiculo
				if (tramiteBaja.getVehiculo() != null) {
					servicioEvolucionVehiculo.actualizarEvolucionVehiculoCreacionOCopia(tramiteBaja.getVehiculo().getIdVehiculo(), numExpediente);
				}

				Fecha fecha = utilesFecha.getFechaHoraActualLEG();
				tramiteBaja.setFechaUltModif(fecha.getFechaHora());
				tramiteBaja.setFechaAlta(fecha.getFechaHora());
				tramiteTraficoBajaDao.guardar(tramiteBaja);
				guardarEvolucionTramite(tramiteDto, EstadoTramiteTrafico.Iniciado.getValorEnum());
			} else {
				tramiteBaja.setFechaUltModif(utilesFecha.getFechaHoraActualLEG().getFechaHora());
				if (!desdeValidar) {
					try {
						tramiteBaja.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						guardarEvolucionTramite(tramiteDto, EstadoTramiteTrafico.Iniciado.getValorEnum());
					} catch (Exception e) {
						log.error("Error al guardar la evolución del trámite de baja: " + tramiteBaja.getNumExpediente() + ". Mensaje: " + e.getMessage(), e, tramiteBaja.getNumExpediente()
								.toString());
					}
				}
				tramiteDto.setNumExpediente(tramiteBaja.getNumExpediente());
				tramiteBaja = tramiteTraficoBajaDao.actualizar(tramiteBaja);
			}
		} catch (Exception e) {
			log.error("Error al guardar el trámite de baja: " + tramiteBaja.getNumExpediente() + ". Mensaje: " + e.getMessage(), e, tramiteBaja.getNumExpediente().toString());
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		if (result != null && result.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	private ResultBean guardarIntervinientes(TramiteTrafBajaDto tramiteDto, TramiteTrafBajaVO tramiteBaja, UsuarioDto usuario) {
		ResultBean respuesta = new ResultBean();
		boolean direccionNueva = false;
		// Guardado de Intervinientes
		if (tramiteBaja.getIntervinienteTraficos() != null && tramiteBaja.getIntervinienteTraficos().size() > 0) {
			for (IntervinienteTraficoVO interviniente : tramiteBaja.getIntervinienteTraficos()) {
				if (interviniente.getPersona() != null && interviniente.getPersona().getId() != null) {
					if (interviniente.getPersona().getId().getNif() != null && !interviniente.getPersona().getId().getNif().isEmpty()) {
						interviniente.getId().setNif(interviniente.getPersona().getId().getNif().toUpperCase());
						interviniente.getId().setNumExpediente(tramiteBaja.getNumExpediente());
						interviniente.getPersona().getId().setNumColegiado(tramiteBaja.getNumColegiado());
						// Estado para saber que está activo
						interviniente.getPersona().setEstado(new Long(1));
						// Se crea el anagrama
						String anagrama = Anagrama.obtenerAnagramaFiscal(interviniente.getPersona().getApellido1RazonSocial(), interviniente.getPersona().getId().getNif());
						interviniente.getPersona().setAnagrama(anagrama);

						// Guardar la persona antes de guardar el interviniente
						String conversion = null;
						if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.RepresentanteTitular.getValorEnum()) || interviniente.getId().getTipoInterviniente().equals(
								TipoInterviniente.RepresentanteAdquiriente.getValorEnum())) {
							conversion = ServicioPersona.CONVERSION_PERSONA_REPRESENTANTE;
						} else {
							conversion = ServicioPersona.CONVERSION_PERSONA_BAJA;
						}

						// Guardar persona
						ResultBean resultPersona = servicioPersona.guardarActualizar(interviniente.getPersona(), tramiteBaja.getNumExpediente(), tramiteBaja.getTipoTramite(), usuario, conversion);

						if (!resultPersona.getError()) {
							// Guardar direccion
							if (interviniente.getDireccion() != null && utiles.convertirCombo(interviniente.getDireccion().getIdProvincia()) != null) {
								ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(interviniente.getDireccion(), interviniente.getPersona().getId().getNif(), interviniente
										.getPersona().getId().getNumColegiado(), tramiteDto.getTipoTramite(), ServicioDireccion.CONVERSION_DIRECCION_INE);
								if (resultDireccion.getError()) {
									respuesta.addMensajeALista("- " + TipoInterviniente.convertirTexto(interviniente.getId().getTipoInterviniente()) + ": " + resultDireccion.getMensaje());
									interviniente.setIdDireccion(null);
								} else {
									DireccionVO direccion = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
									direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
									if (direccion != null) {
										interviniente.setIdDireccion(direccion.getIdDireccion());
									}
									servicioEvolucionPersona.guardarEvolucionPersonaDireccion(interviniente.getPersona().getId().getNif(), tramiteBaja.getNumExpediente(), tramiteBaja.getTipoTramite(),
											interviniente.getPersona().getId().getNumColegiado(), usuario, direccionNueva);
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
			respuesta.addMensajeALista("No se ha guardado ningún interviniente");
		}
		return respuesta;
	}

	private void guardarEvolucionTramite(TramiteTrafBajaDto tramiteDto, String estadoNuevo) {
		if (!estadoNuevo.equals(tramiteDto.getEstado())) {
			EvolucionTramiteTraficoDto evolucion = new EvolucionTramiteTraficoDto();
			if (tramiteDto.getEstado() != null && !tramiteDto.getEstado().isEmpty()) {
				evolucion.setEstadoAnterior(new BigDecimal(tramiteDto.getEstado()));
			} else {
				evolucion.setEstadoAnterior(BigDecimal.ZERO);
			}
			evolucion.setEstadoNuevo(new BigDecimal(estadoNuevo));
			evolucion.setUsuarioDto(tramiteDto.getUsuarioDto());
			evolucion.setNumExpediente(tramiteDto.getNumExpediente());
			servicioEvolucionTramite.guardar(evolucion);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafBajaDto getTramiteBaja(BigDecimal numExpediente, boolean tramiteCompleto) {
		log.debug("Recuperar el trámite baja: " + numExpediente);
		TramiteTrafBajaDto result = null;
		try {
			TramiteTrafBajaVO tramite = getTramiteBajaVO(numExpediente, tramiteCompleto);

			if (tramite != null) {
				result = conversor.transform(tramite, TramiteTrafBajaDto.class);
				if (tramite.getIntervinienteTraficos() != null) {
					for (IntervinienteTraficoVO interviniente : tramite.getIntervinienteTraficosAsList()) {
						if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())) {
							result.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Adquiriente.getValorEnum())) {
							result.setAdquiriente(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
							result.setRepresentanteTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.RepresentanteAdquiriente.getValorEnum())) {
							result.setRepresentanteAdquiriente(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						}
					}
				}

				if (tramite.getTramiteFacturacion() != null && !tramite.getTramiteFacturacion().isEmpty() && null != tramite.getFirstElementTramiteFacturacion()) {
					TramiteTrafFacturacionDto tramiteTraf = conversor.transform(tramite.getFirstElementTramiteFacturacion(), TramiteTrafFacturacionDto.class);
					result.setTramiteFacturacion(tramiteTraf);
				}
				// Si existe facturación se debe de recuperar la persona y la direccion
				if (result != null && result.getTramiteFacturacion() != null && result.getTramiteFacturacion().getNif() != null) {
					ResultBean resultPer = servicioPersona.buscarPersona(result.getTramiteFacturacion().getNif(), result.getNumColegiado());
					if (resultPer != null && !resultPer.getError()) {
						PersonaDto personaDto = (PersonaDto) resultPer.getAttachment(ServicioPersona.PERSONA);
						result.getTramiteFacturacion().setPersona(personaDto);
						ResultBean resultDir = servicioPersonaDireccion.buscarPersonaDireccionDto(result.getNumColegiado(), personaDto.getNif());
						if (resultDir != null && !resultDir.getError()) {
							PersonaDireccionDto personaDDto = (PersonaDireccionDto) resultDir.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
							if (personaDDto.getDireccion() != null)
								result.getTramiteFacturacion().setDireccion(personaDDto.getDireccion());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de baja: " + numExpediente, e, numExpediente.toString());
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean validarTramite(BigDecimal numExpediente, Boolean tienePermisoBTV) {
		log.debug("Validar trámite baja: " + numExpediente);
		ResultBean respuesta = new ResultBean();
		try {
			TramiteTrafBajaVO tramiteTrafBajaVO = getTramiteBajaVO(numExpediente, true);
			if (tramiteTrafBajaVO != null) {
				ResultBean resultValFecMatr = validarFechaMatriculacion(tramiteTrafBajaVO);
				if (!resultValFecMatr.getError()) {
					ValidacionTramite vTramite = null;
					// Bajas checkBTV

					if (MotivoBaja.DefE.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja()) || MotivoBaja.DefTC.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja())) {
						vTramite = validacionTramiteDao.validarTramiteTelematicamenteBaja(tramiteTrafBajaVO);
					} else {
						vTramite = validacionTramiteDao.validarTramiteBaja(tramiteTrafBajaVO);
					}

					if (vTramite != null && vTramite.getSqlerrm().equals("Correcto")) {
						log.debug("Trámite baja validado: " + numExpediente);
						respuesta.addAttachment(ESTADO_VALIDAR, vTramite.getEstado());
						respuesta.addAttachment(MOTIVO_BAJA, tramiteTrafBajaVO.getMotivoBaja());
						respuesta.setError(false);
					} else {
						respuesta.setError(true);
						if (vTramite != null) {
							respuesta.addMensajeALista(vTramite.getSqlerrm());
						} else {
							respuesta.addMensajeALista("Error al validar");
						}
					}
				} else {
					return resultValFecMatr;
				}
			} else {
				respuesta.setError(true);
				respuesta.addMensajeALista("No existen datos para el expediente: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Error al validar el trámite de baja: " + numExpediente, e, numExpediente.toString());
			respuesta.addMensajeALista("Error al validar el trámite de baja");
			respuesta.setError(true);
		}
		return respuesta;
	}

	private ResultBean validarFechaMatriculacion(TramiteTrafBajaVO tramiteTrafBajaVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (tramiteTrafBajaVO.getVehiculo() != null && tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion() != null) {
				int resCom;
				resCom = utilesFecha.compararFechaMayor(utilesFecha.getFechaConDate(tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion()), utilesFecha.getFechaActual());
				if (resCom == 1) {
					// Si la fecha de matriculación es superior a la decha de presentación mostramos el aviso
					resultado.setError(true);
					resultado.addMensajeALista("La fecha de matriculación del vehículo no puede ser posterior a la fecha del día de hoy.");
				} else if (resCom == -1 && tramiteTrafBajaVO.getFechaPresentacion() != null) {
					resCom = utilesFecha.compararFechaMayor(utilesFecha.getFechaConDate(tramiteTrafBajaVO.getVehiculo().getFechaPrimMatri()), utilesFecha.getFechaConDate(tramiteTrafBajaVO
							.getFechaPresentacion()));
					if (resCom == 1) {
						// Si la fecha de matriculación es superior a la decha de presentación mostramos el aviso
						resultado.setError(true);
						resultado.addMensajeALista("La fecha de matriculación del vehículo no puede ser posterior a la fecha de presentación del tramite.");
					}
				}
			}
		} catch (ParseException e) {
			resultado.setError(true);
			resultado.addMensajeALista("Existen errores en las fechas.");
		}
		return resultado;
	}

	public boolean vehiculoMayorDe15Anios(VehiculoDto vehiculo) {
		try {
			boolean esMayor = false;
			Calendar fechaVehiculo = new GregorianCalendar((new Integer(vehiculo.getFechaMatriculacion().getAnio()) + 15), (new Integer(vehiculo.getFechaMatriculacion().getMes()) - 1), (new Integer(
					vehiculo.getFechaMatriculacion().getDia())));
			Calendar fechaHoy = new GregorianCalendar();
			if (fechaVehiculo.after(fechaHoy)) {
				esMayor = false;
			} else {
				esMayor = true;
			}
			return esMayor;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public ResultBean pendientesEnvioExcel(TramiteTrafBajaDto tramiteDto, BigDecimal idUsuario) {
		log.debug("Pendiente de Envío a Excel del trámite baja: " + tramiteDto.getNumExpediente());
		ResultBean respuesta = null;
		try {
			if (EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteDto.getEstado()) && (!MotivoBaja.TempS.getValorEnum().equals(tramiteDto.getMotivoBaja()) && !MotivoBaja.TempFCA
					.getValorEnum().equals(tramiteDto.getMotivoBaja()) && !MotivoBaja.TempT.getValorEnum().equals(tramiteDto.getMotivoBaja()) && !MotivoBaja.DefRP.getValorEnum().equals(tramiteDto
							.getMotivoBaja()) && !MotivoBaja.DefTC.getValorEnum().equals(tramiteDto.getMotivoBaja()) && !MotivoBaja.DefE.getValorEnum().equals(tramiteDto.getMotivoBaja()))) {

				respuesta = comprobarJefaturaEnvio(tramiteDto);
				if (respuesta == null) {
					respuesta = servicioCredito.validarCreditos(TipoTramiteTrafico.Baja.getValorEnum(), tramiteDto.getIdContrato(), new BigDecimal(1));
					if (!respuesta.getError()) {
						respuesta = servicioCredito.descontarCreditos(TipoTramiteTrafico.Baja.getValorEnum(), tramiteDto.getIdContrato(), new BigDecimal(1), idUsuario, ConceptoCreditoFacturado.EBB,
								tramiteDto.getNumExpediente().toString());
						if (!respuesta.getError()) {
							respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteDto.getNumExpediente(), EstadoTramiteTrafico.convertir(tramiteDto.getEstado()),
									EstadoTramiteTrafico.Pendiente_Envio_Excel, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
							if (respuesta.getError()) {
								servicioCredito.devolverCreditos(TipoTramiteTrafico.Baja.getValorEnum(), tramiteDto.getIdContrato(), 1, idUsuario, ConceptoCreditoFacturado.DEBB, tramiteDto
										.getNumExpediente().toString());
							}
						}
					}
				}
			} else {
				respuesta = new ResultBean(true, "Para trámites en estado Validado PDF y con el motivo de baja indicado no se puede enviar excel y hay que imprimir PDF");
			}

		} catch (Exception e) {
			log.error("Error pendiente envio excel baja: " + tramiteDto.getNumExpediente(), e, tramiteDto.getNumExpediente().toString());
		}
		return respuesta;
	}

	private ResultBean comprobarJefaturaEnvio(TramiteTrafBajaDto tramiteDto) {
		ResultBean resultado = null;
		if (!JefaturasJPTEnum.MADRID.getJefatura().equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial()) && !JefaturasJPTEnum.ALCORCON.getJefatura().equals(tramiteDto
				.getJefaturaTraficoDto().getJefaturaProvincial())) {
			if (JefaturasJPTEnum.AVILA.getJefatura().equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))) {
					resultado = new ResultBean(true, "El proceso de envío de Bajas para Avila no está habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))) {
					resultado = new ResultBean(true, "El proceso de envío de Bajas para Ciudad Real no está habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.CUENCA.getJefatura().equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))) {
					resultado = new ResultBean(true, "El proceso de envío de Bajas para Cuenca no está habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))) {
					resultado = new ResultBean(true, "El proceso de envío de Bajas para Guadalajara no está habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.SEGOVIA.getJefatura().equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))) {
					resultado = new ResultBean(true, "El proceso de envío de Bajas para Segovia no está habilitado en este momento");
				}
			} else {
				resultado = new ResultBean(true, "El proceso de envío de Bajas no está habilitado para la jefatura del trámite");
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean tramitarBajaTelematicamente(BigDecimal numExpediente, BigDecimal idUsuario, String colegio) {
		ResultBean resultado = null;
		try {
			TramiteTrafBajaDto tramiteTraficoBajaDto = getTramiteBaja(numExpediente, true);
			if (tramiteTraficoBajaDto != null) {
				if (EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTraficoBajaDto.getEstado())) {
					if (MotivoBaja.DefE.getValorEnum().equals(tramiteTraficoBajaDto.getMotivoBaja()) || MotivoBaja.DefTC.getValorEnum().equals(tramiteTraficoBajaDto.getMotivoBaja())) {
						resultado = tratarCobrarCreditos(tramiteTraficoBajaDto, idUsuario);
						if (!resultado.getError()) {
							resultado = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteTraficoBajaDto.getNumExpediente(), EstadoTramiteTrafico.convertir(tramiteTraficoBajaDto.getEstado()),
									EstadoTramiteTrafico.Pendiente_Envio, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
							if (resultado == null || !resultado.getError()) {
								String tramitarBtv = gestorPropiedades.valorPropertie("nuevas.url.sega.btv");
								if ("SI".equals(tramitarBtv)) {
									resultado = servicioCola.crearSolicitud(ProcesosEnum.BTV_SEGA.toString(), null, gestorPropiedades.valorPropertie(
											ServicioTramiteTraficoBaja.NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.Baja.getValorEnum(), numExpediente.toString(), idUsuario, null, tramiteTraficoBajaDto
													.getIdContrato());
								} else {
									resultado = servicioCola.crearSolicitud(ProcesosEnum.BTV.toString(), null, gestorPropiedades.valorPropertie(
											ServicioTramiteTraficoBaja.NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.Baja.getValorEnum(), numExpediente.toString(), idUsuario, null, tramiteTraficoBajaDto
													.getIdContrato());
								}
							}
						} else {
							resultado = new ResultBean(true, "No tiene creditos disponibles para poder realizar la tramitación.");
						}
					} else {
						resultado = new ResultBean(true, "Solo se pueden tramitar telemáticamente las bajas definitivas de exportación y transito comunitario.");
					}
				} else {
					resultado = new ResultBean(true, "El expediente de baja no se encuentra en Validado telemáticamente para poder realizar la tramitación.");
				}
			} else {
				resultado = new ResultBean(true, "No existen datos en la base de datos para ese expediente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la tramitacion, error: ", e, numExpediente.toString());
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de realizar la tramitación");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de realizar la tramitacion, error: ", e, numExpediente.toString());
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de realizar la tramitación");
		}

		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} else {
			resultado.addAttachment(ServicioTramiteTraficoBaja.NUMEXPEDIENTE, numExpediente);
		}
		return resultado;
	}

	private ResultBean tratarCobrarCreditos(TramiteTrafBajaDto tramiteTrafBajaDto, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			resultado = servicioCredito.validarCreditos(TipoTramiteTrafico.Baja.getValorEnum(), tramiteTrafBajaDto.getIdContrato(), BigDecimal.ONE);
			if (!resultado.getError()) {
				// Descontar créditos
				resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Baja.getValorEnum(), tramiteTrafBajaDto.getIdContrato(), BigDecimal.ONE, idUsuario, ConceptoCreditoFacturado.TBT,
						tramiteTrafBajaDto.getNumExpediente().toString());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cobrar los créditos de la tramitación telematica de bajas, error: ", e, tramiteTrafBajaDto.getNumExpediente().toString());
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de cobrar los créditos.");
		}
		return resultado;
	}

	private ResultBean procesarMotivoBaja(TramiteTrafBajaDto tramiteDto, boolean hayTasa, boolean esImportacion, Boolean tienePermisosBtv) {
		ResultBean respuesta = new ResultBean();

		if (tramiteDto.getMotivoBaja().equals(MotivoBaja.DefE.getValorEnum())) {
			if (tramiteDto.getDeclaracionResiduo() != null && Boolean.FALSE.equals(tramiteDto.getDeclaracionResiduo())) {
				if (hayTasa) {
					tramiteDto.getTasa().setCodigoTasa(null);
				}
				respuesta.addMensajeALista("Bajas por exportación es necesario declaración de NO es residuo sólido");
				if (!TipoCreacion.SIGA.getValorEnum().equals(tramiteDto.getIdTipoCreacion())) {
					respuesta.setError(true);
				}
			} else if (vehiculoMayorDe15Anios(tramiteDto.getVehiculoDto()) && hayTasa) {
				respuesta.addMensajeALista("Bajas por " + MotivoBaja.convertirTexto(tramiteDto.getMotivoBaja()) + " y vehiculo >=15 años no es necesaria tasa .");
				tramiteDto.setTasa(null);
			}
			if (MotivoBaja.DefE.getValorEnum().equals(tramiteDto.getMotivoBaja())) {
				if (esImportacion && tramiteDto.getPais() == null && tramiteDto.getPais().getIdPais() == null && (tramiteDto.getPais() != null && tramiteDto.getPais().getIdPais() != null && tramiteDto
						.getPais().getIdPais().equals("-1"))) {
					if (!TipoCreacion.SIGA.getValorEnum().equals(tramiteDto.getIdTipoCreacion())) {
						respuesta.setError(true);
					}
					respuesta.addMensajeALista("Bajas por " + MotivoBaja.convertirTexto(tramiteDto.getMotivoBaja()) + " es necesario seleccionar pais de la baja");
				}
			}
		} else if (tramiteDto.getMotivoBaja().equals(MotivoBaja.DefCT.getValorEnum())) {
			respuesta.setError(true);
			respuesta.addMensajeALista("El procedimiento de Bajas definitivas por carta de la DGT ya no está operativo. Ese tipo de bajas habrá que solicitarlas como BAJAS DEFINITIVAS VOLUNTARIAS");
		} else if (tramiteDto.getMotivoBaja().equals(MotivoBaja.DefV.getValorEnum())) {
			if (tramiteDto.getCertificadoMedioAmbiental() != null && Boolean.TRUE.equals(tramiteDto.getCertificadoMedioAmbiental()) && hayTasa) {
				respuesta.addMensajeALista("Bajas por " + MotivoBaja.convertirTexto(tramiteDto.getMotivoBaja()) + " no es necesaria tasa si aporta Certificado Medioambiental.");
				tramiteDto.setTasa(null);
			} else if (vehiculoMayorDe15Anios(tramiteDto.getVehiculoDto()) && hayTasa) {
				respuesta.addMensajeALista("Bajas por " + MotivoBaja.convertirTexto(tramiteDto.getMotivoBaja()) + " no es necesaria tasa si el vehículo tiene más de 15 años.");
				tramiteDto.setTasa(null);
			}
		} else if (tramiteDto.getMotivoBaja().equals(MotivoBaja.DefTC.getValorEnum())) {
			if (vehiculoMayorDe15Anios(tramiteDto.getVehiculoDto()) && hayTasa) {
				respuesta.addMensajeALista("Bajas por " + MotivoBaja.convertirTexto(tramiteDto.getMotivoBaja()) + " y vehiculo >=15 años no es necesaria tasa .");
				tramiteDto.setTasa(null);
			}

			if (MotivoBaja.DefTC.getValorEnum().equals(tramiteDto.getMotivoBaja())) {
				if (esImportacion && tramiteDto.getPais() == null && (tramiteDto.getPais() != null && tramiteDto.getPais().getIdPais() != null && tramiteDto.getPais().getIdPais().equals("-1"))) {
					respuesta.setError(true);
					respuesta.addMensajeALista("Bajas por " + MotivoBaja.convertirTexto(tramiteDto.getMotivoBaja()) + " es necesario seleccionar país de la baja");
				}
			}
		} else if (tramiteDto.getMotivoBaja().equals(MotivoBaja.TranCTIT.getValorEnum()) || tramiteDto.getMotivoBaja().equals(MotivoBaja.ExpCTIT.getValorEnum())) {
			boolean resultadoComprobar = servicioTramiteTraficoTransmision.sitexComprobarCTITPrevio(tramiteDto.getVehiculoDto().getMatricula());
			if (resultadoComprobar == false) {
				respuesta.setError(true);
				respuesta.addMensajeALista(TRANSITO_EXPORTACION_CTIT_REQUIEREN_UN_CTIT_PREVIO);
			} else if (tramiteDto.getTasaDuplicado() != null && !"".equals(tramiteDto.getTasaDuplicado())) {
				tramiteDto.setTasaDuplicado(null);
				respuesta.addMensajeALista("Procede  de un cambio de titularidad telemático y por ello no requiere de una tasa de duplicado para expedir el permiso de circulación");
			}
		} else if (tramiteDto.getMotivoBaja().equals(MotivoBaja.TempT.getValorEnum())) {
			respuesta.setError(true);
			respuesta.addMensajeALista("Este tipo de trámite se debe realizar a través del servicio CTIT - Entrega CompraVenta");
		} else if ("-1".equals(tramiteDto.getMotivoBaja())) {
			tramiteDto.setMotivoBaja(null);
		}
		return respuesta;
	}

	private TramiteTrafBajaDto cargaNumColegiado(TramiteTrafBajaDto tramiteDto) {
		if (tramiteDto.getVehiculoDto() != null && (tramiteDto.getVehiculoDto().getNumColegiado() == null || tramiteDto.getVehiculoDto().getNumColegiado().isEmpty())) {
			tramiteDto.getVehiculoDto().setNumColegiado(tramiteDto.getNumColegiado());
		}
		return tramiteDto;
	}

	private TramiteTrafBajaVO convertirDTOVO(TramiteTrafBajaDto tramiteDto) {
		TramiteTrafBajaVO tramiteBaja = conversor.transform(tramiteDto, TramiteTrafBajaVO.class);

		if (tramiteBaja.getContrato() == null) {
			ContratoVO contrato = new ContratoVO();
			contrato.setIdContrato(tramiteDto.getIdContrato().longValue());
			tramiteBaja.setContrato(contrato);
		}

		if (tramiteDto.getTitular() != null && tramiteDto.getTitular().getPersona() != null && tramiteDto.getTitular().getPersona().getNif() != null && !tramiteDto.getTitular().getPersona().getNif()
				.isEmpty() && (tramiteDto.getTitular().getNifInterviniente() == null || tramiteDto.getTitular().getNifInterviniente().isEmpty())) {
			tramiteDto.getTitular().setNifInterviniente(tramiteDto.getTitular().getPersona().getNif());
			tramiteDto.getTitular().setNumColegiado(tramiteDto.getNumColegiado());
		}
		if (tramiteDto.getTitular() != null && tramiteDto.getTitular().getTipoInterviniente() != null && tramiteDto.getTitular().getNifInterviniente() != null && !tramiteDto.getTitular()
				.getTipoInterviniente().isEmpty() && !tramiteDto.getTitular().getNifInterviniente().isEmpty()) {
			if (tramiteBaja.getIntervinienteTraficos() == null) {
				tramiteBaja.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteBaja.getIntervinienteTraficos().add(conversor.transform(tramiteDto.getTitular(), IntervinienteTraficoVO.class));
		}

		if (tramiteDto.getRepresentanteTitular() != null && tramiteDto.getRepresentanteTitular().getPersona() != null && tramiteDto.getRepresentanteTitular().getPersona().getNif() != null
				&& !tramiteDto.getRepresentanteTitular().getPersona().getNif().isEmpty() && (tramiteDto.getRepresentanteTitular().getNifInterviniente() == null || tramiteDto.getRepresentanteTitular()
						.getNifInterviniente().isEmpty())) {
			tramiteDto.getRepresentanteTitular().setNifInterviniente(tramiteDto.getRepresentanteTitular().getPersona().getNif());
			tramiteDto.getRepresentanteTitular().setNumColegiado(tramiteDto.getNumColegiado());
		}
		if (tramiteDto.getRepresentanteTitular() != null && tramiteDto.getRepresentanteTitular().getTipoInterviniente() != null && tramiteDto.getRepresentanteTitular().getNifInterviniente() != null
				&& !tramiteDto.getRepresentanteTitular().getTipoInterviniente().isEmpty() && !tramiteDto.getRepresentanteTitular().getNifInterviniente().isEmpty()) {
			if (tramiteBaja.getIntervinienteTraficos() == null) {
				tramiteBaja.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteBaja.getIntervinienteTraficos().add(conversor.transform(tramiteDto.getRepresentanteTitular(), IntervinienteTraficoVO.class));
		}

		if (tramiteDto.getAdquiriente() != null && tramiteDto.getAdquiriente().getPersona() != null && tramiteDto.getAdquiriente().getPersona().getNif() != null && !tramiteDto.getAdquiriente()
				.getPersona().getNif().isEmpty() && (tramiteDto.getAdquiriente().getNifInterviniente() == null || tramiteDto.getAdquiriente().getNifInterviniente().isEmpty())) {
			tramiteDto.getAdquiriente().setNifInterviniente(tramiteDto.getAdquiriente().getPersona().getNif());
			tramiteDto.getAdquiriente().setNumColegiado(tramiteDto.getNumColegiado());
		}
		if (tramiteDto.getAdquiriente() != null && tramiteDto.getAdquiriente().getTipoInterviniente() != null && tramiteDto.getAdquiriente().getNifInterviniente() != null && !tramiteDto
				.getAdquiriente().getTipoInterviniente().equals("") && !tramiteDto.getAdquiriente().getNifInterviniente().isEmpty()) {
			if (tramiteBaja.getIntervinienteTraficos() == null) {
				tramiteBaja.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteBaja.getIntervinienteTraficos().add(conversor.transform(tramiteDto.getAdquiriente(), IntervinienteTraficoVO.class));
		}

		if (tramiteDto.getRepresentanteAdquiriente() != null && tramiteDto.getRepresentanteAdquiriente().getPersona() != null && tramiteDto.getRepresentanteAdquiriente().getPersona().getNif() != null
				&& !tramiteDto.getRepresentanteAdquiriente().getPersona().getNif().isEmpty() && (tramiteDto.getRepresentanteAdquiriente().getNifInterviniente() == null || tramiteDto
						.getRepresentanteAdquiriente().getNifInterviniente().isEmpty())) {
			tramiteDto.getRepresentanteAdquiriente().setNifInterviniente(tramiteDto.getRepresentanteAdquiriente().getPersona().getNif());
			tramiteDto.getRepresentanteAdquiriente().setNumColegiado(tramiteDto.getNumColegiado());
		}
		if (tramiteDto.getRepresentanteAdquiriente() != null && tramiteDto.getRepresentanteAdquiriente().getTipoInterviniente() != null && tramiteDto.getRepresentanteAdquiriente()
				.getNifInterviniente() != null && !tramiteDto.getRepresentanteAdquiriente().getTipoInterviniente().isEmpty() && tramiteDto.getRepresentanteAdquiriente().getNifInterviniente() != null
				&& !tramiteDto.getRepresentanteAdquiriente().getNifInterviniente().isEmpty()) {
			if (tramiteBaja.getIntervinienteTraficos() == null) {
				tramiteBaja.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteBaja.getIntervinienteTraficos().add(conversor.transform(tramiteDto.getRepresentanteAdquiriente(), IntervinienteTraficoVO.class));
		}

		if (tramiteDto.getPais() != null && tramiteDto.getPais().getIdPais() != null && tramiteDto.getPais().getIdPais() != -1) {
			tramiteBaja.setPais(conversor.transform(tramiteDto.getPais(), PaisVO.class));
		} else {
			tramiteBaja.setPais(null);
		}

		if (tramiteBaja.getTasa() == null || !servicioTasa.comprobarTasa(tramiteBaja.getTasa().getCodigoTasa(), tramiteBaja.getNumExpediente())) {
			tramiteBaja.setTasa(null);
		}

		return tramiteBaja;
	}

	@Override
	@Transactional
	public ResultBean comprobarConsultaBTV(TramiteTrafBajaDto tramiteTraficoBaja, BigDecimal idUsuario) {
		ResultBean resultado = null;
		//String nombreXml = null;
		try {
			// Se comprueban los datos minimos para hacer el checkBTV
			tramiteTraficoBaja = getTramiteBaja(tramiteTraficoBaja.getNumExpediente(), true);
			resultado = comprobarDatosMinimosCheckBTV(tramiteTraficoBaja);
			if (!resultado.getError()) {
				resultado = comprobarCheckFichaPermiso(tramiteTraficoBaja);
				if (!resultado.getError()) {
					// Se comprueba localmente los datos minimos para poder realizar la baja electrónicamemente
					resultado = comprobarLocalmenteTramitabilidad(tramiteTraficoBaja);
					if (!resultado.getError()) {
						resultado = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteTraficoBaja.getNumExpediente(), EstadoTramiteTrafico.Iniciado, EstadoTramiteTrafico.Pendiente_Consulta_BTV,
								true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
						if (resultado == null || !resultado.getError()) {
							String consultaCheckBtv = gestorPropiedades.valorPropertie("nuevas.url.sega.checkBtv");
							if ("SI".equals(consultaCheckBtv)) {
								resultado = servicioCola.crearSolicitud(ProcesosEnum.CONSULTABTV_SEGA.toString(), null, gestorPropiedades.valorPropertie(
										ServicioTramiteTraficoBaja.NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.Baja.getValorEnum(), tramiteTraficoBaja.getNumExpediente().toString(), idUsuario, null,
										tramiteTraficoBaja.getIdContrato());
							} else {
								resultado = servicioCola.crearSolicitud(ProcesosEnum.CONSULTABTV.toString(), null, gestorPropiedades.valorPropertie(ServicioTramiteTraficoBaja.NOMBRE_HOST_SOLICITUD),
										TipoTramiteTrafico.Baja.getValorEnum(), tramiteTraficoBaja.getNumExpediente().toString(), idUsuario, null, tramiteTraficoBaja.getIdContrato());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar las bajas por consultaBTV, error:", e, tramiteTraficoBaja.getNumExpediente().toString()); // TODO: Aquí peude dar NullPointerException
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de comprobar las bajas por consultaBTV");
		} catch (OegamExcepcion e) {
			if (EnumError.error_00001.equals(e.getCodigoError())) {
				resultado = new ResultBean(true, e.getMensajeError1());
			} else if (EnumError.error_00002.equals(e.getCodigoError())) {
				resultado = new ResultBean(true, e.getMensajeError1());
			} else {
				log.error("Ha sucedido un error a la hora de encolar la solicitud de consultaBTV, error:", e, tramiteTraficoBaja.getNumExpediente().toString());
				resultado = new ResultBean(true, "Ha sucedido un error a la hora de encolar la solicitud de consultaBTV");
			}
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} else {
			resultado.addAttachment(ServicioTramiteTraficoBaja.NUMEXPEDIENTE, tramiteTraficoBaja.getNumExpediente());
		}
		return resultado;
	}

	private ResultBean comprobarLocalmenteTramitabilidad(TramiteTrafBajaDto tramiteTraficoBaja) {
		ResultBean resultado = new ResultBean(false);
		if (tramiteTraficoBaja.getTasa() != null && !"-1".equals(tramiteTraficoBaja.getTasa().getCodigoTasa())) {
			if (!vehiculoMayorDe15Anios(tramiteTraficoBaja.getVehiculoDto())) {
				TasaDto tasa = servicioTasa.getTasaCodigoTasa(tramiteTraficoBaja.getTasa().getCodigoTasa());
				if (tasa == null || !TipoTasa.CuatroUno.getValorEnum().equals(tasa.getTipoTasa())) {
					resultado.setError(true);
					resultado.setMensaje("La tasa con la que se intenta tramitar la baja no es valida para la tramitación telemática, por favor seleccione la 4.1.");
					return resultado;
				} else if (vehiculoMayorDe15Anios(tramiteTraficoBaja.getVehiculoDto())) {
					resultado.setError(true);
					resultado.addMensajeALista("Bajas por " + MotivoBaja.convertirTexto(tramiteTraficoBaja.getMotivoBaja()) + " y vehiculo >=15 años no es necesaria tasa .");
				}
			}
		}

		if (tramiteTraficoBaja.getPais() == null || tramiteTraficoBaja.getPais().getIdPais() == null) {
			resultado.setError(true);
			resultado.setMensaje("Debe seleccionar un país de destino para la baja.");
			return resultado;
		}

		// Validación para que los vehículos de Melilla y Ceuta no se puedan realizar telemáticamente la baja
		if (tramiteTraficoBaja.getVehiculoDto().getDireccion() != null && ("51".equals(tramiteTraficoBaja.getVehiculoDto().getDireccion().getIdProvincia()) || "52".equals(tramiteTraficoBaja
				.getVehiculoDto().getDireccion().getIdProvincia()))) {
			String prov = "";
			if ("51".equals(tramiteTraficoBaja.getVehiculoDto().getDireccion().getIdProvincia())) {
				prov = "Ceuta";
			} else if ("52".equals(tramiteTraficoBaja.getVehiculoDto().getDireccion().getIdProvincia())) {
				prov = "Melilla";
			}
			resultado.setError(true);
			resultado.setMensaje("El vehículo con domicilio en la provincia de " + prov + " no se puede tramitar telemáticamente.");
			return resultado;
		}
		// Validación para que los titulares de Melilla y Ceuta no puedan realizar telemáticamente la baja
		if (tramiteTraficoBaja.getTitular().getDireccion() != null && ("51".equals(tramiteTraficoBaja.getTitular().getDireccion().getIdProvincia()) || "52".equals(tramiteTraficoBaja.getTitular()
				.getDireccion().getIdProvincia()))) {
			String prov = "";
			if ("51".equals(tramiteTraficoBaja.getTitular().getDireccion().getIdProvincia())) {
				prov = "Ceuta";
			} else if ("52".equals(tramiteTraficoBaja.getTitular().getDireccion().getIdProvincia())) {
				prov = "Melilla";
			}
			resultado.setError(true);
			resultado.setMensaje("El titular con domicilio en la provincia de " + prov + " no se puede tramitar telemáticamente.");
			return resultado;
		}
		return resultado;
	}

	private ResultBean comprobarCheckFichaPermiso(TramiteTrafBajaDto tramiteTraficoBaja) {
		ResultBean resultado = new ResultBean(false);
		if (MotivoBaja.DefE.getValorEnum().equals(tramiteTraficoBaja.getMotivoBaja()) || MotivoBaja.DefTC.getValorEnum().equals(tramiteTraficoBaja.getMotivoBaja())) {
			if ((Boolean.FALSE.equals(tramiteTraficoBaja.getTarjetaInspeccion()) || Boolean.FALSE.equals(tramiteTraficoBaja.getPermisoCircu())) && Boolean.FALSE.equals(tramiteTraficoBaja
					.getDeclaracionJuradaExtravio())) {
				resultado.setError(true);
				resultado.setMensaje("Para este motivo de baja debe ir seleccionado los checks de permiso de circulación y tarjeta de inspección técnica, o en su defecto la declaración jurada");
			}
		}
		return resultado;
	}

	private ResultBean comprobarDatosMinimosCheckBTV(TramiteTrafBajaDto tramiteTraficoBaja) {
		ResultBean resultado = new ResultBean(false);

		if (tramiteTraficoBaja == null) {
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar los campos obligatorios para poder realizar un checkBTV");
		} else if (!EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTraficoBaja.getEstado())) {
			resultado.setError(true);
			resultado.setMensaje("El tramite tiene que estar en estado Iniciado para poder realizar el CheckBTV.");
		} else if (tramiteTraficoBaja.getMotivoBaja() == null && (!MotivoBaja.DefE.getValorEnum().equals(tramiteTraficoBaja.getMotivoBaja()) || !MotivoBaja.DefTC.getValorEnum().equals(
				tramiteTraficoBaja.getMotivoBaja()))) {
			resultado.setError(true);
			resultado.setMensaje("La baja no se puede comprobar porque no es del motivo adecuado, debe validarla directamente.");
		} else if (tramiteTraficoBaja.getTasa() == null || "-1".equals(tramiteTraficoBaja.getTasa().getCodigoTasa())) {
			if (tramiteTraficoBaja.getVehiculoDto() == null || (tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion() != null)) {
				if (!vehiculoMayorDe15Anios(tramiteTraficoBaja.getVehiculoDto())) {
					resultado.setError(true);
					resultado.setMensaje("Debe seleccionar una Tasa para poder realizar el checkBTV.");
				}
			}
		} else if (tramiteTraficoBaja.getVehiculoDto() == null || tramiteTraficoBaja.getVehiculoDto().getMatricula() == null || tramiteTraficoBaja.getVehiculoDto().getMatricula().isEmpty()) {
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar la matricula del vehículo para poder realizar el checkBTV.");
		} else if (tramiteTraficoBaja.getVehiculoDto() == null || tramiteTraficoBaja.getVehiculoDto().getBastidor() == null || tramiteTraficoBaja.getVehiculoDto().getBastidor().isEmpty()) {
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar el bastidor del vehículo para poder realizar el checkBTV.");
		} else if (tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion() == null || tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion().isfechaNula()) {
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar la fecha de matriculación del vehículo para poder realizar el checkBTV.");
		} else if (tramiteTraficoBaja.getTitular() == null || tramiteTraficoBaja.getTitular().getNifInterviniente() == null || tramiteTraficoBaja.getTitular().getNifInterviniente().isEmpty()) {
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar el NIF del Titular para poder realizar el checkBTV.");
		} else if (tramiteTraficoBaja.getPais() == null || tramiteTraficoBaja.getPais().getIdPais() == null || tramiteTraficoBaja.getPais().getNombre().isEmpty()) {
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar el país de la baja para poder realizar el checkBTV.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean esMotivoCorrectoBajasTelematicas(String[] numsExpedientes) {
		ResultBean resultBean = null;
		if (numsExpedientes != null) {
			boolean error = false;
			List<String> listaMensajes = new ArrayList<String>();
			for (String numExpediente : numsExpedientes) {
				TramiteTrafBajaDto trafBajaDto = getTramiteBaja(new BigDecimal(numExpediente), false);
				if (!MotivoBaja.DefE.getValorEnum().equals(trafBajaDto.getMotivoBaja()) && !MotivoBaja.DefTC.getValorEnum().toString().equals(trafBajaDto.getMotivoBaja())) {
					error = true;
					listaMensajes.add("El tramite con numero: " + numExpediente + "no corresponde a una baja telemática");
				}
			}
			if (error) {
				resultBean = new ResultBean(true);
				resultBean.addListaMensajes(listaMensajes);
			}
		} else {
			resultBean = new ResultBean(true, "Debe seleccionar algun expediente de baja.");
		}
		return resultBean;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean estanAutorizadosExpImpresionInformeBTV(String[] numsExpedientes) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			if (numsExpedientes != null) {
				for (String numExpediente : numsExpedientes) {
					TramiteTrafBajaDto trafBajaDto = getTramiteBaja(new BigDecimal(numExpediente), false);
					if (trafBajaDto.getAutoInformeBtv() == null || !trafBajaDto.getAutoInformeBtv()) {
						resultBean.setError(Boolean.TRUE);
						resultBean.addMensajeALista("El tramite con número: " + numExpediente
								+ " no tiene autorizado la impresión del informe telemático. Debe de ir a su jefatura correspondiente y presentar la documentación.");
					}
				}
			} else {
				resultBean.setError(Boolean.TRUE);
				resultBean.addMensajeALista("Debe seleccionar algun expediente de baja.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar la autorizacion de la impresion del informe de BTV, error: ", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.addMensajeALista("Ha sucedido un error a la hora de validar la autorizacion de la impresion del informe de BTV.");
		}
		return resultBean;
	}

	@Override
	@Transactional
	public ResultBean cambiarEstadoTramitesImprimir(String[] numsExpedientes, BigDecimal idUsuario) {
		ResultBean resultado = null;
		if (numsExpedientes != null && numsExpedientes.length > 0) {
			for (String numExpediente : numsExpedientes) {
				TramiteTrafBajaDto tramiteTrafBajaDto = getTramiteBaja(new BigDecimal(numExpediente), false);
				if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafBajaDto.getEstado())) {
					resultado = servicioTramiteTrafico.cambiarEstadoConEvolucion(new BigDecimal(numExpediente), EstadoTramiteTrafico.Finalizado_Telematicamente,
							EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso, false, TextoNotificacion.Cambio_Estado.getValorEnum(), idUsuario);
					if (resultado.getError()) {
						break;
					}
				}
			}
			if (resultado != null && resultado.getError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} else {
			resultado = new ResultBean(true, "Debe de seleccionar algun expediente para poder cambiar los estados.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafBajaDto> bajasExcel(String jefatura) {
		List<TramiteTrafBajaDto> listaDtos = new ArrayList<TramiteTrafBajaDto>();
		try {
			List<TramiteTrafBajaVO> listaBajas = servicioTramiteTrafico.bajasExcel(jefatura);
			if (listaBajas != null && !listaBajas.isEmpty()) {
				for (TramiteTrafBajaVO vo : listaBajas) {
					TramiteTrafBajaDto dto = conversor.transform(vo, TramiteTrafBajaDto.class);
					if (vo.getIntervinienteTraficos() != null) {
						for (IntervinienteTraficoVO interviniente : vo.getIntervinienteTraficosAsList()) {
							if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())) {
								dto.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
							} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Adquiriente.getValorEnum())) {
								dto.setAdquiriente(conversor.transform(interviniente, IntervinienteTraficoDto.class));
							} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
								dto.setRepresentanteTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
							} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.RepresentanteAdquiriente.getValorEnum())) {
								dto.setRepresentanteAdquiriente(conversor.transform(interviniente, IntervinienteTraficoDto.class));
							}
						}
					}
					listaDtos.add(dto);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar las bajas excel", e);
		}
		return listaDtos;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean comprobarExpedientesImpresionMasiva(String[] numsExpedientes, String tipoImpreso) {
		ResultBean resultado = new ResultBean(false);
		boolean error = false;
		List<String> listaErrores = new ArrayList<String>();
		try {
			if (numsExpedientes != null && numsExpedientes.length > 0) {
				for (String numExp : numsExpedientes) {
					TramiteTrafBajaVO tramiteTrafBajaVO = getTramiteBajaVO(new BigDecimal(numExp), false);
					if (tramiteTrafBajaVO == null) {
						error = true;
						listaErrores.add("Para el tramite con número de expediente: " + numExp + ", no existen datos para su impresión.");
					} else if (TipoImpreso.BajasTelematicas.toString().equals(tipoImpreso)) {
						if (!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafBajaVO.getEstado().toString()) && !EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
								.getValorEnum().equals(tramiteTrafBajaVO.getEstado().toString())) {
							error = true;
							listaErrores.add("El expediente: " + numExp + ", no se encuentra en estado valido para su impresión.");
						}
					}
				}
				if (error) {
					resultado.setError(true);
					resultado.setListaMensajes(listaErrores);
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Debe de indicar algún expediente para su impresión.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar loos tramites de bajas para su impresion, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar loos tramites de bajas para su impresión.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public void cambiarEstadoTramite(BigDecimal numExpediente, EstadoTramiteTrafico estadoNuevo, EstadoTramiteTrafico estadoAnt, boolean notificar, BigDecimal idUsuario, String respuesta,
			boolean esCheckBtv, boolean esBtv) {
		ResultBean resultado = servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, estadoAnt, estadoNuevo, notificar, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
		if (resultado != null && resultado.getError()) {
			if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
				for (String mensaje : resultado.getListaMensajes()) {
					log.error(mensaje);
				}
			} else {
				log.error(resultado.getMensaje());
			}
		}
		ResultBean resultBean = null;
		if (esCheckBtv) {
			resultBean = guardarRespuestaBTV(numExpediente, respuesta, Boolean.TRUE, estadoNuevo);
		} else if (esBtv) {
			resultBean = guardarRespuestaBTV(numExpediente, respuesta, Boolean.FALSE, estadoNuevo);
		}
		if (resultBean != null && resultBean.getError()) {
			if (resultBean.getListaMensajes() != null && !resultBean.getListaMensajes().isEmpty()) {
				for (String mensaje : resultBean.getListaMensajes()) {
					log.error(mensaje);
				}
			} else {
				log.error(resultado.getMensaje());
			}
		}
	}

	@Override
	@Transactional
	public void tratarDevolverCreditos(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato, ConceptoCreditoFacturado conceptoCreditoFacturado) {
		try {
			servicioCredito.devolverCreditos(TipoTramiteTrafico.Baja.getValorEnum(), idContrato, BigDecimal.ONE.intValue(), idUsuario, conceptoCreditoFacturado, numExpediente.toString());
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de devolver los creditos de la tramitación BTV, error: ", e, numExpediente.toString());
		}
	}

	@Override
	@Transactional
	public void tratarDevolverCreditosAM(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato, ConceptoCreditoFacturado conceptoCreditoFacturado) {
		try {
			servicioCredito.descontarCreditosAM(TipoTramiteTrafico.BAJA_AM.getValorEnum(), idContrato, BigDecimal.ONE, idUsuario, conceptoCreditoFacturado, numExpediente.toString());
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de devolver los creditos de la tramitación BTV, error: ", e, numExpediente.toString());
		}
	}

	@Override
	@Transactional
	public ResultBean autorizarImpresionInformeBtv(BigDecimal numExpediente, Boolean tienePermisoAdmin) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			TramiteTrafBajaVO tramiteBaja = getTramiteBajaVO(numExpediente, Boolean.FALSE);
			resultado = validarAutoImpresionInformeBtv(tramiteBaja, numExpediente, tienePermisoAdmin);
			if (!resultado.getError()) {
				tramiteBaja.setAutoInformeBtv("SI");
				tramiteBaja.setPresentadoJpt(EstadoPresentacionJPT.Presentado.getValorEnum());
				tramiteTraficoBajaDao.guardarOActualizar(tramiteBaja);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de autorizar el expediente: " + numExpediente + ", error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de autorizar el expediente: " + numExpediente);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean validarAutoImpresionInformeBtv(TramiteTrafBajaVO tramiteBaja, BigDecimal numExpediente, Boolean tienePermisoAdmin) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if (tramiteBaja == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite: " + numExpediente + " no existe o no es del tipo indicado para poder autorizar la impresión de su informe de baja.");
		} else if (!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteBaja.getEstado().toString()) && (!EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
				.getValorEnum().equals(tramiteBaja.getEstado().toString()))) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite: " + numExpediente + " debe de estar en estado Finalizado Telematicamente o Impreso para poder autorizar la impresión de su informe de baja.");
		} else if (!MotivoBaja.DefE.getValorEnum().equals(tramiteBaja.getMotivoBaja()) && !MotivoBaja.DefTC.getValorEnum().equals(tramiteBaja.getMotivoBaja())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite: " + numExpediente + " no es de un motivo valido para poder autorizar la impresión de su informe de baja.");
		} else if ((tramiteBaja.getTarjetaInspeccion() == null || tramiteBaja.getTarjetaInspeccion().isEmpty() || "NO".equals(tramiteBaja.getTarjetaInspeccion()) || tramiteBaja
				.getPermisoCircu() == null || tramiteBaja.getPermisoCircu().isEmpty() || "NO".equals(tramiteBaja.getPermisoCircu())) && (tramiteBaja.getDeclaracionJuradaExtravio() == null
						|| tramiteBaja.getDeclaracionJuradaExtravio().isEmpty() || "NO".equals(tramiteBaja.getDeclaracionJuradaExtravio()))) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite: " + numExpediente
					+ " no se puede autorizar para la impresión de su informe de baja porque debe de tener seleccionado como documentacion a aportar el permiso y la ficha o la declaracion jurada de extravio.");
		} else if (!tienePermisoAdmin) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene permisos para poder solicitar la autorizacion de la impresion del informe de baja para el trámite: " + numExpediente);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafBajaDto> getListaTramitesFinalizadosIncidenciaBtv(String jefatura, Date fecha) {
		try {
			if (jefatura != null && !jefatura.isEmpty() && fecha != null) {
				List<TramiteTrafBajaVO> listaBBDD = tramiteTraficoBajaDao.getListaTramitesFinalizadosIncidenciasBtv(jefatura, fecha);
				if (listaBBDD != null && !listaBBDD.isEmpty()) {
					return conversor.transform(listaBBDD, TramiteTrafBajaDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites finalizados con Incidencia del dia: " + fecha + ", error: ", e);
		}
		return Collections.emptyList();
	}

	public TramiteTraficoBajaDao getTramiteTraficoBajaDao() {
		return tramiteTraficoBajaDao;
	}

	public void setTramiteTraficoBajaDao(TramiteTraficoBajaDao tramiteTraficoBajaDao) {
		this.tramiteTraficoBajaDao = tramiteTraficoBajaDao;
	}

	public TramiteTraficoProcedureDao getValidacionTramiteDao() {
		return validacionTramiteDao;
	}

	public void setValidacionTramiteDao(TramiteTraficoProcedureDao validacionTramiteDao) {
		this.validacionTramiteDao = validacionTramiteDao;
	}

	public ServicioTasa getServicioTasa() {
		return servicioTasa;
	}

	public void setServicioTasa(ServicioTasa servicioTasa) {
		this.servicioTasa = servicioTasa;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}

	public ServicioVehiculo getServicioVehiculo() {
		return servicioVehiculo;
	}

	public void setServicioVehiculo(ServicioVehiculo servicioVehiculo) {
		this.servicioVehiculo = servicioVehiculo;
	}

	public ServicioEvolucionTramite getServicioEvolucionTramite() {
		return servicioEvolucionTramite;
	}

	public void setServicioEvolucionTramite(ServicioEvolucionTramite servicioEvolucionTramite) {
		this.servicioEvolucionTramite = servicioEvolucionTramite;
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

	public ServicioCredito getServicioCredito() {
		return servicioCredito;
	}

	public void setServicioCredito(ServicioCredito servicioCredito) {
		this.servicioCredito = servicioCredito;
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ServicioPersonaDireccion getServicioPersonaDireccion() {
		return servicioPersonaDireccion;
	}

	public void setServicioPersonaDireccion(ServicioPersonaDireccion servicioPersonaDireccion) {
		this.servicioPersonaDireccion = servicioPersonaDireccion;
	}

	public ServicioEvolucionVehiculo getServicioEvolucionVehiculo() {
		return servicioEvolucionVehiculo;
	}

	public void setServicioEvolucionVehiculo(ServicioEvolucionVehiculo servicioEvolucionVehiculo) {
		this.servicioEvolucionVehiculo = servicioEvolucionVehiculo;
	}

	public ServicioTramiteTraficoTransmision getServicioTramiteTraficoTransmision() {
		return servicioTramiteTraficoTransmision;
	}

	public void setServicioTramiteTraficoTransmision(ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision) {
		this.servicioTramiteTraficoTransmision = servicioTramiteTraficoTransmision;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}

	public ServicioEvolucionPersona getServicioEvolucionPersona() {
		return servicioEvolucionPersona;
	}

	public void setServicioEvolucionPersona(ServicioEvolucionPersona servicioEvolucionPersona) {
		this.servicioEvolucionPersona = servicioEvolucionPersona;
	}

	public ServicioCola getServicioCola() {
		return servicioCola;
	}

	public void setServicioCola(ServicioCola servicioCola) {
		this.servicioCola = servicioCola;
	}

	public Conversor getConversor() {
		return conversor;
	}

	public void setConversor(Conversor conversor) {
		this.conversor = conversor;
	}

	public ServicioColegiado getServicioColegiado() {
		return servicioColegiado;
	}

	public void setServicioColegiado(ServicioColegiado servicioColegiado) {
		this.servicioColegiado = servicioColegiado;
	}

	public ServicioColegio getServicioColegio() {
		return servicioColegio;
	}

	public void setServicioColegio(ServicioColegio servicioColegio) {
		this.servicioColegio = servicioColegio;
	}

	public ObjectFactory getObjectFactory() {
		if (objectFactory == null) {
			objectFactory = new ObjectFactory();
		}
		return objectFactory;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public XmlBTVFactory getXmlBTVFactory() {
		if (xmlBTVFactory == null) {
			xmlBTVFactory = new XmlBTVFactory();
		}
		return xmlBTVFactory;
	}

	public void setXmlBTVFactory(XmlBTVFactory xmlBTVFactory) {
		this.xmlBTVFactory = xmlBTVFactory;
	}


}