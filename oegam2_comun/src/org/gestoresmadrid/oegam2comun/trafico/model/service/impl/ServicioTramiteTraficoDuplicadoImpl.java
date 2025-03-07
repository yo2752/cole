package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioSitesVO;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.enumerados.TipoDocumentoDuplicado;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoDuplicado;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDuplicadosDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoProcedureDao;
import org.gestoresmadrid.core.trafico.model.procedure.bean.ValidacionTramite;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioSites;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.EvolucionTramiteTraficoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioEvolucionVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.service.build.BuildCheckCaducidadCert;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioPersistenciaEvoTramiteTrafico;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.utiles.Anagrama;
import trafico.utiles.ComprobadorDatosSensibles;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioTramiteTraficoDuplicadoImpl implements ServicioTramiteTraficoDuplicado {

	private static final long serialVersionUID = 4922950368084169509L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoDuplicadoImpl.class);

	@Autowired
	private TramiteTraficoDuplicadosDao tramiteTraficoDuplicadosDao;

	@Autowired
	ServicioPersistenciaEvoTramiteTrafico servicioPersistenciaEvoTramiteTrafico;

	@Autowired
	private TramiteTraficoProcedureDao validacionTramiteDao;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioEvolucionVehiculo servicioEvolucionVehiculo;

	@Autowired
	private ServicioEvolucionTramite servicioEvolucionTramite;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioTasa servicioTasa;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	ServicioMunicipio servicioMunicipio;

	@Autowired
	ServicioMunicipioSites servicioMunicipioSites;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioDatosSensibles servicioDatosSensibles;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	BuildCheckCaducidadCert buildCheckCaducidadCert;

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafDuplicadoDto getTramiteDuplicado(BigDecimal numExpediente, boolean tramiteCompleto) {
		log.debug("Recuperar el trámite duplicado: " + numExpediente);
		TramiteTrafDuplicadoDto result = null;
		try {
			TramiteTrafDuplicadoVO tramite = tramiteTraficoDuplicadosDao.getTramiteDuplicado(numExpediente,
					tramiteCompleto);

			if (tramite != null) {
				result = conversor.transform(tramite, TramiteTrafDuplicadoDto.class);

				if (tramite.getIntervinienteTraficos() != null) {
					for (IntervinienteTraficoVO interviniente : tramite.getIntervinienteTraficosAsList()) {
						if (interviniente.getId().getTipoInterviniente()
								.equals(TipoInterviniente.Titular.getValorEnum())) {
							result.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (interviniente.getId().getTipoInterviniente()
								.equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
							result.setRepresentanteTitular(
									conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (interviniente.getId().getTipoInterviniente()
								.equals(TipoInterviniente.CotitularTransmision.getValorEnum())) {
							result.setCotitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						}
					}
				}

				if (tramite.getTramiteFacturacion() != null && !tramite.getTramiteFacturacion().isEmpty()
						&& null != tramite.getFirstElementTramiteFacturacion()) {
					TramiteTrafFacturacionDto tramiteTraf = conversor
							.transform(tramite.getFirstElementTramiteFacturacion(), TramiteTrafFacturacionDto.class);
					result.setTramiteFacturacion(tramiteTraf);
				}
				// Si existe facturación se debe de recuperar la persona y la direccion
				if (result != null && result.getTramiteFacturacion() != null
						&& result.getTramiteFacturacion().getNif() != null) {
					ResultBean resultPer = servicioPersona.buscarPersona(result.getTramiteFacturacion().getNif(),
							result.getNumColegiado());
					if (resultPer != null && !resultPer.getError()) {
						PersonaDto personaDto = (PersonaDto) resultPer.getAttachment(ServicioPersona.PERSONA);
						result.getTramiteFacturacion().setPersona(personaDto);
						ResultBean resultDir = servicioPersonaDireccion
								.buscarPersonaDireccionDto(result.getNumColegiado(), personaDto.getNif());
						if (resultDir != null && !resultDir.getError()) {
							PersonaDireccionDto personaDDto = (PersonaDireccionDto) resultDir
									.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
							if (personaDDto.getDireccion() != null)
								result.getTramiteFacturacion().setDireccion(personaDDto.getDireccion());
						}
					}
				}
				// Se intenta obtener documentos relacionados con el trámite
				result.setFicherosSubidos(recuperarDocumentos(numExpediente));
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de duplicado: " + numExpediente, e, numExpediente.toString());
		}
		return result;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultBean guardarTramite(TramiteTrafDuplicadoDto tramiteDto, BigDecimal idUsuario, boolean desdeValidar,
			boolean desdeJustificante, boolean admin) {
		ResultBean respuesta = new ResultBean();
		TramiteTrafDuplicadoVO tramiteDupli = new TramiteTrafDuplicadoVO();
		try {
			tramiteDto = cargaNumColegiado(tramiteDto);
			if (tramiteDto.getJefaturaTraficoDto() == null
					|| tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial() == null
					|| "-1".equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (tramiteDto.getIdContrato() != null) {
					ContratoDto contratoDto = servicioContrato.getContratoDto(tramiteDto.getIdContrato());
					tramiteDto.setJefaturaTraficoDto(contratoDto.getJefaturaTraficoDto());
				}
			}
			if (!desdeJustificante) {
				if (null != tramiteDto.getNumExpediente()) {
					TramiteTrafDuplicadoVO tramiteAux =  tramiteTraficoDuplicadosDao.getTramiteDuplicadoSive(tramiteDto.getNumExpediente(), false);
					if(utiles.esUsuarioSive(tramiteDto.getNumColegiado())) {
						if (tramiteAux.getTasaPermiso() != null && !tramiteAux.getTasaPermiso().equals(tramiteDto.getTasaPermiso())) {
							servicioTasa.desasignarTasaExpedienteDuplicados(tramiteDto.getTasaPermiso(), tramiteAux.getTasaPermiso() , tramiteDto.getNumExpediente(), null);
						}
						if (tramiteAux.getTasaFichaTecnica() != null && !tramiteAux.getTasaFichaTecnica().equals(tramiteDto.getTasaFichaTecnica())) {
							servicioTasa.desasignarTasaExpedienteDuplicados(tramiteDto.getTasaFichaTecnica(), tramiteAux.getTasaFichaTecnica(), tramiteDto.getNumExpediente(), null);
						}
					}
					if (tramiteAux.getTasaImportacion() != null && !tramiteAux.getTasaImportacion().equals(tramiteDto.getTasaImportacion())) {
						servicioTasa.desasignarTasaExpedienteDuplicados(tramiteDto.getTasaImportacion(), tramiteAux.getTasaImportacion(), tramiteDto.getNumExpediente(), null);
					}
					if ((tramiteAux.getTasa() != null && tramiteDto.getTasa() != null && !tramiteAux.getTasa().getCodigoTasa().equals(tramiteDto.getTasa().getCodigoTasa()))
							||(tramiteAux.getTasa() != null && tramiteDto.getTasa() == null)
							||(tramiteAux.getTasa() != null && tramiteDto.getTasa().getCodigoTasa().equals("-1"))) {
						servicioTasa.desasignarTasaExpedienteDuplicados(tramiteDto.getTasa() != null ? tramiteDto.getTasa().getCodigoTasa(): null, tramiteAux.getTasa().getCodigoTasa(), tramiteDto.getNumExpediente(), null);
					}
				}
			}

			UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
			tramiteDto.setUsuarioDto(usuario);

			// Datos del VO
			tramiteDupli = convertirDTOVO(tramiteDto);

			// Guardamos vehiculo
			ResultBean respuestaVehiculo = null;
			if (desdeJustificante) {
				respuestaVehiculo = guardarVehiculo(tramiteDto, tramiteDupli, usuario,
						ServicioVehiculo.CONVERSION_VEHICULO_JUSTIFICANTE, admin);
			} else {
				respuestaVehiculo = guardarVehiculo(tramiteDto, tramiteDupli, usuario,
						ServicioVehiculo.CONVERSION_VEHICULO_DUPLICADO, admin);
			}
			respuesta.addListaMensajes(respuestaVehiculo.getListaMensajes());

			ResultBean respuestaTramite = guardarTramite(tramiteDto, tramiteDupli, desdeValidar);
			if (respuestaTramite != null && !respuestaTramite.getError()) {
				respuesta.addAttachment(NUMEXPEDIENTE, tramiteDupli.getNumExpediente());

				// Guardamos interviniente
				ResultBean respuestaInterviniente = guardarIntervinientes(tramiteDto, tramiteDupli, usuario,
						desdeJustificante);
				respuesta.addListaMensajes(respuestaInterviniente.getListaMensajes());

				if (!desdeJustificante) {
					ResultBean respuestaTasaDuplicado = null;
					if(utiles.esUsuarioSive(tramiteDupli.getNumColegiado())) {
						if (StringUtils.isNotBlank(tramiteDto.getTasaPermiso())
								&& !"-1".equals(tramiteDto.getTasaPermiso())) {
							respuestaTasaDuplicado = servicioTasa.asignarTasa(tramiteDto.getTasaPermiso(),
									tramiteDupli.getNumExpediente());
							respuesta.addListaMensajes(respuestaTasaDuplicado.getListaMensajes());
						}
						if (StringUtils.isNotBlank(tramiteDto.getTasaFichaTecnica())
								&& !"-1".equals(tramiteDto.getTasaFichaTecnica())) {
							respuestaTasaDuplicado = servicioTasa.asignarTasa(tramiteDto.getTasaFichaTecnica(),
									tramiteDupli.getNumExpediente());
							respuesta.addListaMensajes(respuestaTasaDuplicado.getListaMensajes());
						}
					}else {
						if (tramiteDto.getTasa() != null && tramiteDto.getTasa().getCodigoTasa() != null
								&& !tramiteDto.getTasa().getCodigoTasa().isEmpty()
								&& !"-1".equals(tramiteDto.getTasa().getCodigoTasa())) {
							ResultBean respuestaTasaExpediente = servicioTasa
									.asignarTasa(tramiteDto.getTasa().getCodigoTasa(), tramiteDupli.getNumExpediente());
						respuesta.addListaMensajes(respuestaTasaExpediente.getListaMensajes());
						}
					}
					if (StringUtils.isNotBlank(tramiteDto.getTasaImportacion())
							&& !"-1".equals(tramiteDto.getTasaImportacion())) {
						respuestaTasaDuplicado = servicioTasa.asignarTasa(tramiteDto.getTasaImportacion(),
								tramiteDupli.getNumExpediente());
						respuesta.addListaMensajes(respuestaTasaDuplicado.getListaMensajes());
					}
				}
			} else {
				log.error("Error al guardar el trámite de duplicado: " + tramiteDupli.getNumExpediente() + ". Mensaje: "
						+ respuestaTramite.getMensaje());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				respuesta.setError(true);
				respuesta.addMensajeALista(respuestaTramite.getMensaje());
			}
		} catch (Exception e) {
			log.error("Error al guardar el trámite de duplicado: " + tramiteDupli.getNumExpediente() + ". Mensaje: "
					+ e.getMessage(), e, tramiteDupli.getNumExpediente().toString());
			respuesta.setError(true);
			respuesta.addMensajeALista("Error al guardar el trámite de duplicado");
		}
		return respuesta;
	}

	private ResultBean guardarTramite(TramiteTrafDuplicadoDto tramiteDto, TramiteTrafDuplicadoVO tramiteDuplicado,
			boolean desdeValidar) {
		ResultBean result = new ResultBean();
		try {
			if (tramiteDuplicado.getNumExpediente() == null) {
				BigDecimal numExpediente = servicioTramiteTrafico
						.generarNumExpediente(tramiteDuplicado.getNumColegiado());
				log.debug("Creación trámite de duplicado: " + numExpediente);
				tramiteDuplicado.setNumExpediente(numExpediente);
				tramiteDto.setNumExpediente(numExpediente);
				if (tramiteDuplicado.getEstado() == null || (tramiteDuplicado.getEstado() != null
						&& !EstadoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteDuplicado.getEstado()))) {
					tramiteDuplicado.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
				}
				// Actualizar la evolucion vehiculo
				if (tramiteDuplicado.getVehiculo() != null) {
					servicioEvolucionVehiculo.actualizarEvolucionVehiculoCreacionOCopia(
							tramiteDuplicado.getVehiculo().getIdVehiculo(), numExpediente);
				}

				Fecha fecha = utilesFecha.getFechaHoraActualLEG();
				tramiteDuplicado.setFechaUltModif(fecha.getFechaHora());
				tramiteDuplicado.setFechaAlta(fecha.getFechaHora());
				tramiteTraficoDuplicadosDao.guardar(tramiteDuplicado);
				guardarEvolucionTramite(tramiteDto, EstadoTramiteTrafico.Iniciado.getValorEnum());
			} else {
				log.debug("Actualización trámite de duplicado: " + tramiteDuplicado.getNumExpediente());
				tramiteDuplicado.setFechaUltModif(utilesFecha.getFechaHoraActualLEG().getFechaHora());
				if (!desdeValidar) {
					try {
						tramiteDuplicado.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						guardarEvolucionTramite(tramiteDto, EstadoTramiteTrafico.Iniciado.getValorEnum());
					} catch (Exception e) {
						log.error(
								"Error al guardar la evolución del trámite de duplicado: "
										+ tramiteDuplicado.getNumExpediente() + ". Mensaje: " + e.getMessage(),
								e, tramiteDuplicado.getNumExpediente().toString());
					}
				}
				tramiteDto.setNumExpediente(tramiteDuplicado.getNumExpediente());
				tramiteDuplicado = tramiteTraficoDuplicadosDao.actualizar(tramiteDuplicado);
			}
		} catch (Exception e) {
			log.error("Error al guardar el trámite de duplicado: " + tramiteDuplicado.getNumExpediente() + ". Mensaje: "
					+ e.getMessage(), e, tramiteDuplicado.getNumExpediente().toString());
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
	public ResultBean validarTramite(TramiteTrafDuplicadoDto tramiteDto) {
		log.debug("Validar trámite duplicado: " + tramiteDto.getNumExpediente());
		ResultBean respuesta = new ResultBean();

			try {
				if (tramiteDto != null && tramiteDto.getNumExpediente() != null) {
					TramiteTrafDuplicadoVO tramiteVO = conversor.transform(tramiteDto, TramiteTrafDuplicadoVO.class);
					

						ValidacionTramite vTramite = null;
						if(utiles.esUsuarioSive(tramiteDto.getNumColegiado()) && (MotivoDuplicado.Deter.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())
								|| MotivoDuplicado.Extrv.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())
								|| MotivoDuplicado.Sustr.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado()))) {
							validacionPreviaSive(tramiteVO,respuesta);
							if(!respuesta.getError()){
								ResultBean resultValidacion = validarTramiteDuplicado(tramiteVO);
								if(!resultValidacion.getError()) {
									vTramite = actualizarEstado(tramiteVO);
									try {
										respuesta.addAttachment(ESTADO_VALIDAR, vTramite.getEstado());
										tramiteVO.setEstado(new BigDecimal(vTramite.getEstado()));
										tramiteTraficoDuplicadosDao.guardarOActualizar(tramiteVO);
										guardarEvolucionTramite(tramiteDto, tramiteVO.getEstado().toString());
									} catch (Exception e) {
										log.error("Error al actualizar el estado del trámite de duplicado: " + tramiteDto.getNumExpediente(), e, tramiteDto.getNumExpediente().toString());
										respuesta.setMensaje("Error al actualizar el estado del trámite de duplicado: " + tramiteDto.getNumExpediente());
										respuesta.setError(true);
									}
								}else {
									respuesta.setError(resultValidacion.getError());
									respuesta.setMensaje(resultValidacion.getMensaje());
								}
							}
						}else {
							vTramite = validacionTramiteDao.validarTramiteDuplicado(tramiteVO);
							if (vTramite != null && vTramite.getSqlerrm() != null && vTramite.getSqlerrm().equals("Correcto")) {
								// Validar en caso de Importación DUA
								if (validadoDUA(tramiteVO)) {
									log.debug("Trámite duplicado validado: " + tramiteDto.getNumExpediente());
									respuesta.addAttachment(ESTADO_VALIDAR, vTramite.getEstado());
									respuesta.setError(false);
								} else {
									respuesta.setError(true);
									respuesta.addMensajeALista("Es necesario adjuntar documentación para este trámite.");
								}
								// Fin de validación en caso de Importación DUA
							} else {
								respuesta.setError(true);
								if (vTramite != null && StringUtils.isNotBlank(vTramite.getSqlerrm())) {
									respuesta.addMensajeALista(vTramite.getSqlerrm());
								} else {
									respuesta.addMensajeALista("Error al validar");
								}
							}
						}		
				} else {
					respuesta.setError(true);
					respuesta.addMensajeALista("Error en el tramite");
				}
			} catch (Exception e) {
				log.error("Error al validar el trámite de duplicado: " + tramiteDto.getNumExpediente(), e,
						tramiteDto.getNumExpediente().toString());
				respuesta.addMensajeALista("Error al validar el trámite de duplicado");
				respuesta.setError(true);
			}

		return respuesta;
	}

	private ResultBean validarTramiteDuplicado(TramiteTrafDuplicadoVO tramiteVO) {
		ResultBean result = new ResultBean(); 
//		if((TipoDocumentoDuplicado.TARJETA_ITV.getValorEnum().equalsIgnoreCase(tramiteVO.getTipoDocumento()) || 
//				TipoDocumentoDuplicado.PERMISO_CIRCULACION_ITV.getValorEnum().equalsIgnoreCase(tramiteVO.getTipoDocumento()))
//				&& StringUtils.isBlank(tramiteVO.getVehiculo().getNive())) {
//				result.setMensaje("El expediente de duplicado " +tramiteVO.getNumExpediente() + ", no tiene Nive guardado. Es necesario si se quiere solicitar el duplicado de ficha técnica para solicitar posteriormente su impresión");
//				result.setError(Boolean.TRUE);
//		}
		if((MotivoDuplicado.Deter.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado()) 
				|| MotivoDuplicado.Extrv.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())
					|| MotivoDuplicado.Sustr.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())) 
						&& TipoDocumentoDuplicado.PERMISO_CIRCULACION.getValorEnum().equalsIgnoreCase(tramiteVO.getTipoDocumento())
						&& StringUtils.isBlank(tramiteVO.getTasaPermiso()))
						 {
			result.setMensaje("El expediente de duplicado " +tramiteVO.getNumExpediente() + ", no tiene tasa asociada para solicitar duplicado de Permiso de Circulacion para los motivos de deterioro,extravío o sustracción.");
			result.setError(Boolean.TRUE);
		}
		if((MotivoDuplicado.Deter.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado()) 
				|| MotivoDuplicado.Extrv.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())
					|| MotivoDuplicado.Sustr.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())) 
						&& TipoDocumentoDuplicado.TARJETA_ITV.getValorEnum().equalsIgnoreCase(tramiteVO.getTipoDocumento())
						&& StringUtils.isBlank(tramiteVO.getTasaFichaTecnica()))
						 {
			result.setMensaje("El expediente de duplicado " +tramiteVO.getNumExpediente() + ", no tiene tasa asociada para solicitar duplicado de la Ficha Técnica para los motivos de deterioro,extravío o sustracción.");
			result.setError(Boolean.TRUE);
		}
		if((MotivoDuplicado.Deter.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado()) 
				|| MotivoDuplicado.Extrv.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())
					|| MotivoDuplicado.Sustr.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())) 
						&& TipoDocumentoDuplicado.PERMISO_CIRCULACION_ITV.getValorEnum().equalsIgnoreCase(tramiteVO.getTipoDocumento())
						&& (StringUtils.isBlank(tramiteVO.getTasaPermiso()) || StringUtils.isBlank(tramiteVO.getTasaFichaTecnica())))
						 {
			result.setMensaje("El expediente de duplicado " +tramiteVO.getNumExpediente() + ", no tiene tasa asociada para solicitar duplicado del Permiso de Circulación y Ficha Técnica para los motivos de deterioro,extravío o sustracción.");
			result.setError(Boolean.TRUE);
		}
		if(StringUtils.isBlank(tramiteVO.getMotivoDuplicado()) || "-1".equalsIgnoreCase(tramiteVO.getMotivoDuplicado())) {
			result.setMensaje("El expediente de duplicado " +tramiteVO.getNumExpediente() + ", no tiene un motivo indicado y es obligatorio.");
			result.setError(Boolean.TRUE);
		}
		if((MotivoDuplicado.Deter.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado()) 
				|| MotivoDuplicado.Extrv.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())
				|| MotivoDuplicado.Sustr.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())) && StringUtils.isBlank(tramiteVO.getTipoDocumento()) || "-1".equalsIgnoreCase(tramiteVO.getTipoDocumento())) {
			result.setMensaje("El expediente de duplicado " +tramiteVO.getNumExpediente() + ", no tiene un Documento a solicitar a DGT y es obligatorio.");
			result.setError(Boolean.TRUE);
		}
		if((TipoDocumentoDuplicado.TARJETA_ITV.getValorEnum().equalsIgnoreCase(tramiteVO.getTipoDocumento()) || 
				TipoDocumentoDuplicado.PERMISO_CIRCULACION_ITV.getValorEnum().equalsIgnoreCase(tramiteVO.getTipoDocumento()))
				&& EstadoTramiteTrafico.No_Tramitable.getValorEnum().equalsIgnoreCase(tramiteVO.getEstado().toString())) {
				result.setMensaje("El expediente de duplicado " +tramiteVO.getNumExpediente() + ", no se puede validar por Excel ya que se está solicitando una ficha ITV.");
				result.setError(Boolean.TRUE);
		}
		if(!EstadoTramiteTrafico.Tramitable.getValorEnum().equalsIgnoreCase(tramiteVO.getEstado().toString()) && !EstadoTramiteTrafico.No_Tramitable.getValorEnum().equalsIgnoreCase(tramiteVO.getEstado().toString())) {
				result.setMensaje("El expediente de duplicado " + tramiteVO.getNumExpediente() + ", debe estar en estado Tramitable o No Tramitable para iniciar su validación.");
				result.setError(Boolean.TRUE);
		}
		return result;
	}

	private ValidacionTramite actualizarEstado(TramiteTrafDuplicadoVO tramiteVO) {
		ValidacionTramite vTramite= new ValidacionTramite();
		if (EstadoTramiteTrafico.Tramitable.getValorEnum().equalsIgnoreCase(tramiteVO.getEstado().toString())) {
			vTramite.setEstado(new Long(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()));
		} else if (EstadoTramiteTrafico.No_Tramitable.getValorEnum().equalsIgnoreCase(tramiteVO.getEstado().toString())) {
			if (TipoDocumentoDuplicado.PERMISO_CIRCULACION.getValorEnum().equalsIgnoreCase(tramiteVO.getTipoDocumento())) {
				vTramite.setEstado(new Long(EstadoTramiteTrafico.Validado_PDF.getValorEnum()));
			} else {
				vTramite.setEstado(new Long(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()));
			}
		} 
		return vTramite;
	}

	private void validacionPreviaSive(TramiteTrafDuplicadoVO tramiteVO, ResultBean respuesta) {
		if(TipoDocumentoDuplicado.PERMISO_CIRCULACION.getValorEnum().equalsIgnoreCase(tramiteVO.getTipoDocumento())){
			if(StringUtils.isBlank(tramiteVO.getTasaPermiso()) 
				&& (MotivoDuplicado.Deter.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())
				|| MotivoDuplicado.Extrv.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado())
				|| MotivoDuplicado.Sustr.getValorEnum().equalsIgnoreCase(tramiteVO.getMotivoDuplicado()))) {
				respuesta.addMensajeALista("El expediente de duplicado " +tramiteVO.getNumExpediente() + ", no tiene tasa asociada para el tipo documento permiso circulación.");
				respuesta.setError(true);
			}
			if (EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equalsIgnoreCase(tramiteVO.getEstado().toString())) {
				respuesta.addMensajeALista("El expediente de duplicado " +tramiteVO.getNumExpediente() + ", ya está validado telemáticamente.");
				respuesta.setError(true);
			}
			if (EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equalsIgnoreCase(tramiteVO.getEstado().toString())) {
				respuesta.addMensajeALista("El expediente de duplicado " +tramiteVO.getNumExpediente() + ", no puede estar Finalizado con error para su validación.");
				respuesta.setError(true);
			}
		}
	}

	@Override
	@Transactional
	public ResultBean comprobarDuplicado(BigDecimal numExpediente, BigDecimal idUsuario) {
		log.debug("Comprobar trámite duplicado: " + numExpediente);
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		String nombreXml = null;
		try {
			TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO = tramiteTraficoDuplicadosDao
					.getTramiteDuplicado(numExpediente, true);
			resultado = comprobarDatosMinimosCheckDuplicado(tramiteTrafDuplicadoVO, numExpediente, resultado);
			if (!resultado.getError()) {
				String gestionarConAM = gestorPropiedades.valorPropertie("gestionar.conAm");
				if ("2631".equals(tramiteTrafDuplicadoVO.getNumColegiado()) && "SI".equals(gestionarConAM)) {
					resultado = realizarCheckDuplicado(tramiteTrafDuplicadoVO,
							ProcesosAmEnum.CHECK_DUPLICADO.toString(), nombreXml, idUsuario,
							gestorPropiedades.valorPropertie("nombreHostSolicitud"),
							tramiteTrafDuplicadoVO.getContrato().getIdContrato());
				} else {
					ResultadoBean resultCheckCert = buildCheckCaducidadCert.comprobarCaducidadCertificado(
							tramiteTrafDuplicadoVO.getContrato().getColegiado().getAlias());
					if (!resultCheckCert.getError()) {
						resultado = realizarCheckDuplicado(tramiteTrafDuplicadoVO,
								ProcesosEnum.CHECK_DUPLICADO.toString(), null, idUsuario,
								gestorPropiedades.valorPropertie("nombreHostSolicitud"),
								tramiteTrafDuplicadoVO.getContrato().getIdContrato());

					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Actualmente el certificado del colegiado se encuentra caducado.");
					}
				}
			}
		} catch (Throwable e) {
			log.error(
					"Ha sucedido un error a la hora de comprobarDuplicado del tramite de duplicado con numero de expediente: "
							+ numExpediente + ", error: ",
					e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de comprobarDuplicado del tramite de duplicado con numero de expediente: "
							+ numExpediente);
		}

		return resultado;
	}

	private ResultBean comprobarDatosMinimosCheckDuplicado(TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO,
			BigDecimal numExpediente, ResultBean resultado) {
		if (tramiteTrafDuplicadoVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado datos para el expediente: " + numExpediente);
		} else if (!EstadoTramiteTrafico.Iniciado.getValorEnum()
				.equals(tramiteTrafDuplicadoVO.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado
					.setMensaje("El tramite tiene que estar en estado Iniciado para poder realizar el CheckDuplicado.");
		} else if (StringUtils.isBlank(tramiteTrafDuplicadoVO.getMotivoDuplicado())
				|| (!MotivoDuplicado.Deter.getValorEnum().equals(tramiteTrafDuplicadoVO.getMotivoDuplicado())
						&& !MotivoDuplicado.Sustr.getValorEnum().equals(tramiteTrafDuplicadoVO.getMotivoDuplicado())
						&& !MotivoDuplicado.Extrv.getValorEnum().equals(tramiteTrafDuplicadoVO.getMotivoDuplicado()))) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(
					"El duplicado no se puede comprobar porque no es del motivo adecuado, debe validarlo directamente.");
		} else if (tramiteTrafDuplicadoVO.getVehiculo() == null) {
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar los datos del vehiculo.");
		}
//		else if (tramiteTrafDuplicadoVO.getVehiculo().getFechaMatriculacion() == null) {
//			resultado.setError(true);
//			resultado.setMensaje("Debe rellenar la fecha de matriculación del vehiculo para poder realizar el checkDuplicado.");
//		} else if (tramiteTrafDuplicadoVO.getTasa() == null || StringUtils.isBlank(tramiteTrafDuplicadoVO.getTasa().getCodigoTasa())) {
//			if (!vehiculoMayorDe15Anios(utilesFecha.getFechaConDate(tramiteTrafDuplicadoVO.getVehiculo().getFechaMatriculacion()))) {
//				resultado.setError(Boolean.TRUE);
//				resultado.setMensaje("Debe seleccionar una Tasa para poder realizar el checkDuplicado.");
//			}
//		} 
		else if (StringUtils.isBlank(tramiteTrafDuplicadoVO.getVehiculo().getMatricula())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe rellenar la matricula del vehiculo para poder realizar el checkDuplicado.");
		} else if (tramiteTrafDuplicadoVO.getIntervinienteTraficosAsList() == null
				|| tramiteTrafDuplicadoVO.getIntervinienteTraficosAsList().isEmpty()) {
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar el titular del duplicado para poder realizar el checkDuplicado.");
		}
//		else if (tramiteTrafDuplicadoVO.getVehiculo().getDireccion() != null && 
//				("51".equals(tramiteTrafDuplicadoVO.getVehiculo().getDireccion().getIdProvincia())
//					|| "52".equals(tramiteTrafDuplicadoVO.getVehiculo().getDireccion().getIdProvincia()))) {
//			String prov = "";
//			if ("51".equals(tramiteTrafDuplicadoVO.getVehiculo().getDireccion().getIdProvincia())) {
//				prov = "Ceuta";
//			} else if ("52".equals(tramiteTrafDuplicadoVO.getVehiculo().getDireccion().getIdProvincia())) {
//				prov = "Melilla";
//			}
//			resultado.setError(Boolean.TRUE);
//			resultado.setMensaje("El vehículo con domicilio en la provincia de " + prov + " no se puede tramitar telemáticamente.");
//		}
		else if (StringUtils.isBlank(tramiteTrafDuplicadoVO.getTipoDocumento())
				|| (!TipoDocumentoDuplicado.PERMISO_CIRCULACION.getValorEnum()
						.equals(tramiteTrafDuplicadoVO.getTipoDocumento())
						&& !TipoDocumentoDuplicado.PERMISO_CIRCULACION_ITV.getValorEnum()
								.equals(tramiteTrafDuplicadoVO.getTipoDocumento())
						&& !TipoDocumentoDuplicado.TARJETA_ITV.getValorEnum()
								.equals(tramiteTrafDuplicadoVO.getTipoDocumento()))) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El duplicado no se puede comprobar indique el tipo de documento.");
		} else {
			Boolean existeTitular = Boolean.FALSE;
			for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteTrafDuplicadoVO
					.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.Titular.getValorEnum()
						.equals(intervinienteTraficoVO.getId().getTipoInterviniente())) {
//					if (intervinienteTraficoVO.getDireccion() != null && 
//							("51".equals(intervinienteTraficoVO.getDireccion().getIdProvincia()) 
//									|| "52".equals(intervinienteTraficoVO.getDireccion().getIdProvincia()))) {
//						String prov = "";
//						if ("51".equals(intervinienteTraficoVO.getDireccion().getIdProvincia())) {
//							prov = "Ceuta";
//						} else if ("52".equals(intervinienteTraficoVO.getDireccion().getIdProvincia())) {
//							prov = "Melilla";
//						}
//						resultado.setError(Boolean.TRUE);
//						resultado.setMensaje("El titular con domicilio en la provincia de " + prov + " no se puede tramitar telemáticamente.");
//					}
					existeTitular = Boolean.TRUE;
					break;
				}
			}
			if (!existeTitular) {
				resultado.setError(true);
				resultado.setMensaje("Debe rellenar el titular del duplicado para poder realizar el checkDuplicado.");
			}
		}
		return resultado;
	}

	private ResultBean realizarCheckDuplicado(TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO, String proceso,
			String nombreXml, BigDecimal idUsuario, String hostProceso, Long idContrato) throws OegamExcepcion {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			BigDecimal estadoAnt = tramiteTrafDuplicadoVO.getEstado();
			tramiteTrafDuplicadoVO
					.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Check_Duplicado.getValorEnum()));
			EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = new EvolucionTramiteTraficoVO();
			EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();
			id.setEstadoAnterior(estadoAnt);
			id.setEstadoNuevo(tramiteTrafDuplicadoVO.getEstado());
			id.setNumExpediente(tramiteTrafDuplicadoVO.getNumExpediente());
			evolucionTramiteTraficoVO.setId(id);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario.longValue());
			evolucionTramiteTraficoVO.setUsuario(usuario);
			Date fecha = new Date();
			tramiteTrafDuplicadoVO.setFechaUltModif(fecha);
			tramiteTraficoDuplicadosDao.actualizar(tramiteTrafDuplicadoVO);
			servicioPersistenciaEvoTramiteTrafico.guardar(evolucionTramiteTraficoVO);
			servicioCola.crearSolicitud(proceso, null, gestorPropiedades.valorPropertie("nombreHostSolicitud"),
					TipoTramiteTrafico.Duplicado.getValorEnum(), tramiteTrafDuplicadoVO.getNumExpediente().toString(),
					idUsuario, null, BigDecimal.valueOf(tramiteTrafDuplicadoVO.getContrato().getIdContrato()));
		} catch (Throwable e) {
			log.error(
					"Ha sucedido un error a la hora de guardar el checkDuplicado del tramite de duplicado con numero de expediente: "
							+ tramiteTrafDuplicadoVO.getNumExpediente() + ", error: ",
					e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de guardar el checkDuplicado del tramite de duplicado con numero de expediente: "
							+ tramiteTrafDuplicadoVO.getNumExpediente());
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean tramitarDuplicado(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultBean respuesta = new ResultBean(Boolean.FALSE);
		try {
			TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO = tramiteTraficoDuplicadosDao
					.getTramiteDuplicado(numExpediente, true);
			TramiteTrafDuplicadoDto tramiteDto = conversor.transform(tramiteTrafDuplicadoVO,
					TramiteTrafDuplicadoDto.class);
			respuesta = comprobacionDatosSensibles(tramiteDto, idUsuario);
			if (respuesta != null && !respuesta.getError()) {
				String gestionarConAM = gestorPropiedades.valorPropertie("gestionar.conAm");
				if ("2631".equals(tramiteDto.getNumColegiado()) && "SI".equals(gestionarConAM)) {
					try {
						ResultBean resSolcicitud = servicioCola.crearSolicitud(
								ProcesosAmEnum.FULL_DUPLICADO.getValorEnum(), null,
								gestorPropiedades.valorPropertie("nombreHostSolicitud"),
								TipoTramiteTrafico.Duplicado.getValorEnum(), tramiteDto.getNumExpediente().toString(),
								idUsuario, null, tramiteDto.getIdContrato());
						if (!resSolcicitud.getError()) {
							log.info("Se agregó correctamente en la cola el tr\341mite de Duplicado:"
									+ tramiteDto.getNumExpediente());
							respuesta.setMensaje("Solicitud creada correctamente");
							try {
								respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(
										tramiteDto.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Envio,
										EstadoTramiteTrafico.convertir(tramiteDto.getEstado()), true,
										TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
								if (respuesta != null && respuesta.getError()) {
									log.error("Se ha producido un error al actualizar al estado anterior el tramite."
											+ respuesta.getMensaje());
								}
							} catch (Exception e) {
								log.error(
										"Se ha producido un error al actualizar al estado anterior el tramite, ya que no se ha podido crear la solicitud. Error: "
												+ e.getMessage(),
										e, tramiteDto.getNumExpediente().toString());
							}
						} else {
							respuesta.setError(Boolean.TRUE);
							respuesta.setMensaje(respuesta.getMensaje());
						}
					} catch (OegamExcepcion e) {
						respuesta.setError(Boolean.TRUE);
						respuesta.setMensaje(e.getMensajeError1());
					}
				} else {
					respuesta = tratarCobrarCreditos(tramiteDto, idUsuario);

					if (!respuesta.getError()) {
						respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteDto.getNumExpediente(),
								EstadoTramiteTrafico.convertir(tramiteDto.getEstado()),
								EstadoTramiteTrafico.Pendiente_Envio, true,
								TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
						if (respuesta != null && !respuesta.getError()) {
							try {
								ResultBean resSolcicitud = servicioCola.crearSolicitud(
										ProcesosEnum.FULL_DUPLICADO.getNombreEnum(), null,
										gestorPropiedades.valorPropertie("nombreHostSolicitud"),
										TipoTramiteTrafico.Duplicado.getValorEnum(),
										tramiteDto.getNumExpediente().toString(), idUsuario, null,
										tramiteDto.getIdContrato());
								if (!resSolcicitud.getError()) {
									log.info("Se agregó correctamente en la cola el trámite de Duplicado:"
											+ tramiteDto.getNumExpediente());
									respuesta.setMensaje("Solicitud creada correctamente");
								} else {
									try {
										respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(
												tramiteDto.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Envio,
												EstadoTramiteTrafico.convertir(tramiteDto.getEstado()), true,
												TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
										if (respuesta != null && respuesta.getError()) {
											log.error(
													"Se ha producido un error al actualizar al estado anterior el tramite."
															+ respuesta.getMensaje());
										}
									} catch (Exception e) {
										log.error(
												"Se ha producido un error al actualizar al estado anterior el tramite, ya que no se ha podido crear la solicitud. Error: "
														+ e.getMessage(),
												e, tramiteDto.getNumExpediente().toString());
									}
									respuesta.setError(Boolean.TRUE);
									respuesta.setMensaje(respuesta.getMensaje());
								}
							} catch (OegamExcepcion e) {
								respuesta.setError(Boolean.TRUE);
								respuesta.setMensaje(e.getMensajeError1());
							}
						}
						return respuesta;
					}
				}
			}
		} catch (Throwable e) {
			log.error("Error al tramitar el trámite con num expediente: " + numExpediente, e, numExpediente.toString());
			respuesta.setError(true);
			respuesta.setMensaje("Error al tramitar: " + e.getMessage());
		}
		return respuesta;
	}

	private ResultBean tratarCobrarCreditos(TramiteTrafDuplicadoDto tramiteDto, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			resultado = servicioCredito.validarCreditos(TipoTramiteTrafico.Duplicado.getValorEnum(), tramiteDto.getIdContrato(), BigDecimal.ONE);
			if (!resultado.getError()) {
				// Descontar créditos
				resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Duplicado.getValorEnum(), tramiteDto.getIdContrato(), BigDecimal.ONE, idUsuario, ConceptoCreditoFacturado.TDT,
						tramiteDto.getNumExpediente().toString());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cobrar los créditos de la tramitación telematica de duplicado, error: ", e, tramiteDto.getNumExpediente().toString());
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de cobrar los créditos.");
		}
		return resultado;
	}

	private static final String CAMBIO_DOMICILIO = "1";

	private boolean validadoDUA(TramiteTrafDuplicadoVO tramiteVO) {
		if (tramiteVO.getMotivoDuplicado().equals(CAMBIO_DOMICILIO) && tramiteVO.getImportacion().equals("SI")
				&& tramiteVO.getJefaturaTrafico().getJefaturaProvincial().equalsIgnoreCase("M")) {
			ArrayList<FicheroInfo> ficherosSubidos = recuperarDocumentos(tramiteVO.getNumExpediente());
			if (ficherosSubidos != null && ficherosSubidos.size() >= 1) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	private ResultBean guardarIntervinientes(TramiteTrafDuplicadoDto tramiteDto,
			TramiteTrafDuplicadoVO tramiteDuplicado, UsuarioDto usuario, boolean desdeJustificante) {
		ResultBean respuesta = new ResultBean();
		boolean direccionNueva = false;
		// Guardado de Intervinientes
		if (tramiteDuplicado.getIntervinienteTraficos() != null
				&& !tramiteDuplicado.getIntervinienteTraficos().isEmpty()) {
			for (IntervinienteTraficoVO interviniente : tramiteDuplicado.getIntervinienteTraficos()) {
				if (interviniente.getPersona() != null && interviniente.getPersona().getId() != null) {
					if (interviniente.getPersona().getId().getNif() != null
							&& !interviniente.getPersona().getId().getNif().isEmpty()) {
						interviniente.getId().setNif(interviniente.getPersona().getId().getNif().toUpperCase());
						interviniente.getId().setNumExpediente(tramiteDuplicado.getNumExpediente());
						interviniente.getPersona().getId().setNumColegiado(tramiteDuplicado.getNumColegiado());
						// Estado para saber que esta activo
						interviniente.getPersona().setEstado(new Long(1));
						// Se crea el anagrama
						String anagrama = Anagrama.obtenerAnagramaFiscal(
								interviniente.getPersona().getApellido1RazonSocial(),
								interviniente.getPersona().getId().getNif());
						interviniente.getPersona().setAnagrama(anagrama);
						// Guardar la persona antes de guardar el interviniente
						String conversion = null;
						if (desdeJustificante) {
							conversion = ServicioPersona.CONVERSION_PERSONA_JUSTIFICANTE;
						} else if (interviniente.getId().getTipoInterviniente()
								.equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
							conversion = ServicioPersona.CONVERSION_PERSONA_REPRESENTANTE;
						} else {
							conversion = ServicioPersona.CONVERSION_PERSONA_DUPLICADO;
						}

						// Guardar persona
						ResultBean resultPersona = servicioPersona.guardarActualizar(interviniente.getPersona(),
								tramiteDuplicado.getNumExpediente(), tramiteDuplicado.getTipoTramite(), usuario,
								conversion);

						if (!resultPersona.getError()) {
							// Guardar direccion
							if (interviniente.getDireccion() != null
									&& utiles.convertirCombo(interviniente.getDireccion().getIdProvincia()) != null) {

								MunicipioSitesVO municipioSites = servicioMunicipioSites.getMunicipio(
										interviniente.getDireccion().getIdMunicipio(),
										interviniente.getDireccion().getIdProvincia());

								if (municipioSites != null) {

									ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(
											interviniente.getDireccion(), interviniente.getPersona().getId().getNif(),
											interviniente.getPersona().getId().getNumColegiado(),
											tramiteDto.getTipoTramite(), ServicioDireccion.CONVERSION_DIRECCION_INE);
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
												tramiteDuplicado.getNumExpediente(), tramiteDuplicado.getTipoTramite(),
												interviniente.getPersona().getId().getNumColegiado(), usuario,
												direccionNueva);
									}
								} else {
									respuesta.addMensajeALista("- "
											+ TipoInterviniente
													.convertirTexto(interviniente.getId().getTipoInterviniente())
											+ ": El municipio seleccionado no corresponde a un municipio INE válido.");
									interviniente.setIdDireccion(null);
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

	private void guardarEvolucionTramite(TramiteTrafDuplicadoDto tramiteDto, String estadoNuevo) {
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

	private ResultBean guardarVehiculo(TramiteTrafDuplicadoDto tramiteDto, TramiteTrafDuplicadoVO tramiteDupli,
			UsuarioDto usuario, String conversion, boolean admin) throws ParseException {
		ResultBean respuesta = new ResultBean();
		// Guardado Vehículo
		if (tramiteDto.getVehiculoDto().getMatricula() != null
				&& !tramiteDto.getVehiculoDto().getMatricula().isEmpty()) {
			VehiculoVO vehiculoPantalla = conversor.transform(tramiteDto.getVehiculoDto(), VehiculoVO.class);

			Date fechaPresentacion = null;
			if (tramiteDto.getFechaPresentacion() != null) {
				fechaPresentacion = tramiteDto.getFechaPresentacion().getDate();
			}

			ResultBean respuestaVehiculo = servicioVehiculo.guardarVehiculo(vehiculoPantalla,
					tramiteDto.getNumExpediente(), tramiteDto.getTipoTramite(), usuario, fechaPresentacion, conversion,
					false, admin);

			Object attachment = respuestaVehiculo.getAttachment(ServicioVehiculo.VEHICULO);
			if (attachment != null && attachment instanceof VehiculoVO) {
				tramiteDupli.setVehiculo((VehiculoVO) attachment);
			}
			if (respuestaVehiculo.getListaMensajes() != null && !respuestaVehiculo.getListaMensajes().isEmpty()) {
				for (String mensaje : respuestaVehiculo.getListaMensajes()) {
					respuesta.addMensajeALista(mensaje);
				}
			}
		} else {
			tramiteDupli.setVehiculo(null);
		}
		return respuesta;
	}

	private TramiteTrafDuplicadoDto cargaNumColegiado(TramiteTrafDuplicadoDto tramiteDto) {
		if (tramiteDto.getVehiculoDto() != null && (tramiteDto.getVehiculoDto().getNumColegiado() == null
				|| tramiteDto.getVehiculoDto().getNumColegiado().isEmpty())) {
			tramiteDto.getVehiculoDto().setNumColegiado(tramiteDto.getNumColegiado());
		}
		return tramiteDto;
	}

	private TramiteTrafDuplicadoVO convertirDTOVO(TramiteTrafDuplicadoDto tramiteDto) {
		TramiteTrafDuplicadoVO tramiteDupli = conversor.transform(tramiteDto, TramiteTrafDuplicadoVO.class);

		if (tramiteDto.getContrato() == null) {
			ContratoVO contrato = new ContratoVO();
			contrato.setIdContrato(tramiteDto.getIdContrato().longValue());
			tramiteDupli.setContrato(contrato);
		}

		if (tramiteDto.getTitular() != null && tramiteDto.getTitular().getPersona() != null
				&& tramiteDto.getTitular().getPersona().getNif() != null
				&& !tramiteDto.getTitular().getPersona().getNif().isEmpty()
				&& (tramiteDto.getTitular().getNifInterviniente() == null
						|| tramiteDto.getTitular().getNifInterviniente().isEmpty())) {
			tramiteDto.getTitular().setNifInterviniente(tramiteDto.getTitular().getPersona().getNif());
		}
		if (tramiteDto.getTitular() != null && tramiteDto.getTitular().getTipoInterviniente() != null
				&& tramiteDto.getTitular().getNifInterviniente() != null
				&& !tramiteDto.getTitular().getTipoInterviniente().isEmpty()
				&& !tramiteDto.getTitular().getNifInterviniente().isEmpty()) {
			if (tramiteDupli.getIntervinienteTraficos() == null) {
				tramiteDupli.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteDto.getTitular().setNumColegiado(tramiteDto.getNumColegiado());
			IntervinienteTraficoVO titularVO = conversor.transform(tramiteDto.getTitular(),
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
			tramiteDupli.getIntervinienteTraficos().add(titularVO);
		}

		if (tramiteDto.getRepresentanteTitular() != null && tramiteDto.getRepresentanteTitular().getPersona() != null
				&& tramiteDto.getRepresentanteTitular().getPersona().getNif() != null
				&& !tramiteDto.getRepresentanteTitular().getPersona().getNif().isEmpty()
				&& (tramiteDto.getRepresentanteTitular().getNifInterviniente() == null
						|| tramiteDto.getRepresentanteTitular().getNifInterviniente().isEmpty())) {
			tramiteDto.getRepresentanteTitular()
					.setNifInterviniente(tramiteDto.getRepresentanteTitular().getPersona().getNif());
		}
		if (tramiteDto.getRepresentanteTitular() != null
				&& tramiteDto.getRepresentanteTitular().getTipoInterviniente() != null
				&& tramiteDto.getRepresentanteTitular().getNifInterviniente() != null
				&& !tramiteDto.getRepresentanteTitular().getTipoInterviniente().isEmpty()
				&& !tramiteDto.getRepresentanteTitular().getNifInterviniente().isEmpty()) {
			if (tramiteDupli.getIntervinienteTraficos() == null) {
				tramiteDupli.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteDto.getRepresentanteTitular().setNumColegiado(tramiteDto.getNumColegiado());
			IntervinienteTraficoVO repreTitularVO = conversor.transform(tramiteDto.getRepresentanteTitular(),
					IntervinienteTraficoVO.class);
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
			tramiteDupli.getIntervinienteTraficos().add(repreTitularVO);
		}

		if (tramiteDto.getCotitular() != null && tramiteDto.getCotitular().getPersona() != null
				&& tramiteDto.getCotitular().getPersona().getNif() != null
				&& !tramiteDto.getCotitular().getPersona().getNif().isEmpty()
				&& (tramiteDto.getCotitular().getNifInterviniente() == null
						|| tramiteDto.getCotitular().getNifInterviniente().isEmpty())) {
			tramiteDto.getCotitular().setNifInterviniente(tramiteDto.getCotitular().getPersona().getNif());
		}
		if (tramiteDto.getCotitular() != null && tramiteDto.getCotitular().getTipoInterviniente() != null
				&& tramiteDto.getCotitular().getNifInterviniente() != null
				&& !tramiteDto.getCotitular().getTipoInterviniente().isEmpty()
				&& !tramiteDto.getCotitular().getNifInterviniente().isEmpty()) {
			if (tramiteDupli.getIntervinienteTraficos() == null) {
				tramiteDupli.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteDto.getCotitular().setNumColegiado(tramiteDto.getNumColegiado());
			IntervinienteTraficoVO coTitularVO = conversor.transform(tramiteDto.getCotitular(),
					IntervinienteTraficoVO.class);
			if (coTitularVO != null && coTitularVO.getDireccion() != null
					&& coTitularVO.getDireccion().getNombreMunicipio() != null
					&& !coTitularVO.getDireccion().getNombreMunicipio().isEmpty()) {
				MunicipioDto municipioDto = servicioMunicipio.getMunicipioPorNombre(
						coTitularVO.getDireccion().getNombreMunicipio(), coTitularVO.getDireccion().getIdProvincia());
				if (municipioDto != null) {
					coTitularVO.getDireccion().setIdMunicipio(municipioDto.getIdMunicipio());
				}
			}
			tramiteDupli.getIntervinienteTraficos().add(coTitularVO);
		}

		if (tramiteDto.getTasa() == null
				|| !servicioTasa.comprobarTasa(tramiteDto.getTasa().getCodigoTasa(), tramiteDto.getNumExpediente())) {
			tramiteDupli.setTasa(null);
		}

		return tramiteDupli;
	}

	@Override
	@Transactional
	public ResultBean pendientesEnvioExcel(TramiteTrafDuplicadoDto tramiteDto, BigDecimal idUsuario) {
		log.debug("Pendiente de Envío a Excel del trámite duplicado: " + tramiteDto.getNumExpediente());
		ResultBean respuesta = null;
		try {
			if (EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteDto.getEstado())) {
				respuesta = comprobarJefaturaEnvio(tramiteDto);
				if (respuesta == null) {
					respuesta = servicioCredito.validarCreditos(TipoTramiteTrafico.Duplicado.getValorEnum(),
							tramiteDto.getIdContrato(), new BigDecimal(1));
					if (!respuesta.getError()) {
						respuesta = servicioCredito.descontarCreditos(TipoTramiteTrafico.Duplicado.getValorEnum(),
								tramiteDto.getIdContrato(), new BigDecimal(1), idUsuario, ConceptoCreditoFacturado.EBD,
								tramiteDto.getNumExpediente().toString());
						if (!respuesta.getError()) {
							respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteDto.getNumExpediente(),
									EstadoTramiteTrafico.convertir(tramiteDto.getEstado()),
									EstadoTramiteTrafico.Pendiente_Envio_Excel, true,
									TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
							if (respuesta.getError()) {
								servicioCredito.devolverCreditos(TipoTramiteTrafico.Duplicado.getValorEnum(),
										tramiteDto.getIdContrato(), 1, idUsuario, ConceptoCreditoFacturado.DEBD,
										tramiteDto.getNumExpediente().toString());
							}
							if (tramiteDto.getFechaPresentacion().isfechaNula()) { // Para los casos de los trámites
																					// enviados por Oegam AM WS en
																					// estado Validado PDF que se pasen
																					// a estado Pendiente Envío Excel
																					// desde Oegam 2
								servicioTramiteTrafico.actualizarFechaPresentacion(tramiteDto.getNumExpediente(),
										new Date());
							}
						}
					}
				}
			} else {
				respuesta = new ResultBean(true, "El trámite debe de estar en Validado PDF");
			}
		} catch (Exception e) {
			log.error("Error pendiente envio excel duplicado: " + tramiteDto.getNumExpediente(), e,
					tramiteDto.getNumExpediente().toString());
		}
		return respuesta;
	}

	private ResultBean comprobarJefaturaEnvio(TramiteTrafDuplicadoDto tramiteDto) {
		ResultBean resultado = null;
		if (!JefaturasJPTEnum.MADRID.getJefatura().equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())
				&& !JefaturasJPTEnum.ALCORCON.getJefatura()
						.equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())
				&& !JefaturasJPTEnum.ALCALADEHENARES.getJefatura()
						.equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
			if (JefaturasJPTEnum.AVILA.getJefatura()
					.equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))) {
					resultado = new ResultBean(true,
							"El proceso de envio de Duplicados para Avila no esta habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura()
					.equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))) {
					resultado = new ResultBean(true,
							"El proceso de envio de Duplicados para Ciudad Real no esta habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.CUENCA.getJefatura()
					.equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))) {
					resultado = new ResultBean(true,
							"El proceso de envio de Duplicados para Cuenca no esta habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.GUADALAJARA.getJefatura()
					.equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))) {
					resultado = new ResultBean(true,
							"El proceso de envio de Duplicados para Guadalajara no esta habilitado en este momento");
				}
			} else if (JefaturasJPTEnum.SEGOVIA.getJefatura()
					.equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))) {
					resultado = new ResultBean(true,
							"El proceso de envio de Duplicados para Segovia no esta habilitado en este momento");
				}
			} else {
				resultado = new ResultBean(true,
						"El proceso de envio de Duplicados no está habilitado para la jefatura del tramite");
			}
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafDuplicadoDto> duplicadosExcel(String jefatura) {
		List<TramiteTrafDuplicadoDto> listaDtos = new ArrayList<TramiteTrafDuplicadoDto>();
		try {
			List<TramiteTrafDuplicadoVO> listaduplicados = servicioTramiteTrafico.duplicadosExcel(jefatura);
			if (listaduplicados != null && !listaduplicados.isEmpty()) {
				for (TramiteTrafDuplicadoVO vo : listaduplicados) {
					TramiteTrafDuplicadoDto dto = conversor.transform(vo, TramiteTrafDuplicadoDto.class);
					if (vo.getIntervinienteTraficos() != null) {
						for (IntervinienteTraficoVO interviniente : vo.getIntervinienteTraficosAsList()) {
							if (interviniente.getId().getTipoInterviniente()
									.equals(TipoInterviniente.Titular.getValorEnum())) {
								dto.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
							} else if (interviniente.getId().getTipoInterviniente()
									.equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
								dto.setRepresentanteTitular(
										conversor.transform(interviniente, IntervinienteTraficoDto.class));
							} else if (interviniente.getId().getTipoInterviniente()
									.equals(TipoInterviniente.CotitularTransmision.getValorEnum())) {
								dto.setCotitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
							}
						}
					}
					listaDtos.add(dto);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar los duplicados excel", e);
		}
		return listaDtos;
	}

	private ResultBean comprobacionDatosSensibles(TramiteTrafDuplicadoDto tramiteDto, BigDecimal idUsuario)
			throws OegamExcepcion {
		ResultBean respuesta = new ResultBean();
		try {
			String[] numExpedientes = new String[] { tramiteDto.getNumExpediente().toString() };
			List<String> expedientesSensibles = null;
			String datosSensiblesAnt = gestorPropiedades.valorPropertie("datosSensibles.antiguo");
			if ("true".equals(datosSensiblesAnt)) {
				expedientesSensibles = new ComprobadorDatosSensibles().isSensibleData(numExpedientes);
			} else {
				expedientesSensibles = servicioDatosSensibles.existenDatosSensiblesPorExpedientes(
						utiles.convertirStringArrayToBigDecimal(numExpedientes), idUsuario);
			}
			if (expedientesSensibles != null && !expedientesSensibles.isEmpty()) {
				for (int j = 0; j < expedientesSensibles.size(); j++) {
					respuesta.addMensajeALista(
							"Se ha recibido un error técnico. No intenten presentar el tramite. Les rogamos comuniquen con el Colegio.");
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Datos sensibles en el trámite "
							+ expedientesSensibles.get(j));
				}
				return respuesta;
			}
		} catch (Exception e) {
		}
		return respuesta;
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
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

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}

	public TramiteTraficoDuplicadosDao getTramiteTraficoDuplicadosDao() {
		return tramiteTraficoDuplicadosDao;
	}

	public void setTramiteTraficoDuplicadosDao(TramiteTraficoDuplicadosDao tramiteTraficoDuplicadosDao) {
		this.tramiteTraficoDuplicadosDao = tramiteTraficoDuplicadosDao;
	}

	public ServicioEvolucionPersona getServicioEvolucionPersona() {
		return servicioEvolucionPersona;
	}

	public void setServicioEvolucionPersona(ServicioEvolucionPersona servicioEvolucionPersona) {
		this.servicioEvolucionPersona = servicioEvolucionPersona;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	@Override
	public ArrayList<FicheroInfo> recuperarDocumentos(BigDecimal numExpediente) {
		log.info("TRÁMITE DUPLICADO: Inicio -- recuperarDocumentos. ");
		ArrayList<FicheroInfo> ficherosSubidos = null;
		if (numExpediente != null) {
			try {
				String carpeta = ConstantesGestorFicheros.EXCELS;
				String subcarpeta = ConstantesGestorFicheros.DUPLICADO_PERMISO_CONDUCIR_DUA;
				FileResultBean ficheros = gestorDocumentos.buscarFicheroPorNumExpTipo(carpeta, subcarpeta,
						Utilidades.transformExpedienteFecha(numExpediente), numExpediente.toString());
				if (ficheros.getFiles() != null && !ficheros.getFiles().isEmpty()) {
					ficherosSubidos = new ArrayList<FicheroInfo>();
					for (File fichero : ficheros.getFiles()) {
						FicheroInfo ficheroInfo = new FicheroInfo(fichero, 0);
						ficherosSubidos.add(ficheroInfo);
					}
				}
			} catch (OegamExcepcion e) {
				log.error("Error al recuperar los documentos " + e.getMessage(), e, numExpediente.toString());
			}
		}
		return ficherosSubidos;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoPermisoDistintivoItvBean getTramitesFinalizadosTelematicamentePorContrato(Long idContrato, Date fecha) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTrafDuplicadoVO> listaTramitesBBDD = tramiteTraficoDuplicadosDao.getListaTramitesFinalizadosTelematicamentePorContrato(idContrato, fecha);
			if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
				resultado.setListaTramitesDuplicado(listaTramitesBBDD);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El contrato: " + idContrato + " no tiene trámites telemáticos de duplicado para imprimir sus permisos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites para imprimir sus permisos para el contrato: " + idContrato + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el listado de trámites para imprimir sus permisos para el contrato: " + idContrato);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoPermisoDistintivoItvBean listaTramitesFinalizadosTelmPorContrato(ContratoDto contrato, Date fecha, String tipoSolicitud, Boolean esMotos) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTrafDuplicadoVO> listaTramitesBBDD = tramiteTraficoDuplicadosDao.listaTramitesFinalizadosTelemPorContrato(contrato.getIdContrato().longValue(), fecha, tipoSolicitud, esMotos);
			if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
				resultado.setListaTramitesDuplicado(listaTramitesBBDD);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No tiene tramites finalizados telematicamente");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si el contrato: " + contrato.getColegiadoDto().getNumColegiado() + " - " + contrato.getVia()
					+ " tiene tramites de Matw finalizados telematicamente para IMPR, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar si el contrato: " + contrato.getColegiadoDto().getNumColegiado() + " - " + contrato.getVia()
					+ " tiene tramites de Duplicado finalizados telematicamente para IMPR.");
		}
		return resultado;
	}

}