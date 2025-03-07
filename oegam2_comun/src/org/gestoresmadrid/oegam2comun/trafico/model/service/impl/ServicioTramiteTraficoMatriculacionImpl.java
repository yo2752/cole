package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.docPermDistItv.model.dao.DocPermDistItvDao;
import org.gestoresmadrid.core.docPermDistItv.model.dao.VehNoMatOegamDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTarjetaITV;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.materiales.model.values.ConsumoMaterialValue;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoProcedureDao;
import org.gestoresmadrid.core.trafico.model.procedure.bean.ValidacionTramite;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.service.ServicioEvolucionDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionMatriculacion;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ConsultaEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.model.service.ServicioEmpresaTelematica;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.dto.EmpresaTelematicaDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.tramitar.build.BuildMatriculacionSega;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatriculacionBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCardMatwDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.EvolucionTramiteTraficoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioEvolucionVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioFabricante;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.am.service.ServicioWebServiceAm;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.DecisionTrafico;
import general.utiles.Anagrama;
import trafico.beans.jaxb.matriculacion.TipoServicio;
import trafico.utiles.ComprobadorDatosSensibles;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioTramiteTraficoMatriculacionImpl implements ServicioTramiteTraficoMatriculacion {

	private static final long serialVersionUID = -5556313291116051153L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoMatriculacionImpl.class);
	private static final String ERROR_GUARDAR_TRAMITE_MATRICULACION = "Error al guardar el trámite de matriculación: ";
	private static final String ERROR_RECUPERAR_JUSTIFICANTE_IVTM = "Ha sucedido un error a la hora de recuperar el justificante IVTM ";

	private static final String NO_ENCONTRADO_JUSTIFICANTE_PAGO_IVTM = "No se ha encontrado el justificante de pago IVTM";
	private static final String FORMATO_DE_FECHA_DE_NACIMIENTO_DEL = "El formato de la fecha de nacimiento del ";

	private static final String ERROR_AL_ACTUALIZAR_ESTADOS_TRAMITES = "Ha sucedido un error a la hora de actualizar los estados de los trámites.";

	@Autowired
	TramiteTraficoMatrDao tramiteTraficoMatrDao;

	@Autowired
	VehNoMatOegamDao vehNoMatOegamDao;

	@Autowired
	TramiteTraficoProcedureDao validacionTramiteDao;

	@Autowired
	ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioUsuario servicioUsuario;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioEvolucionVehiculo servicioEvolucionVehiculo;

	@Autowired
	ServicioEvolucionTramite servicioEvolucionTramite;

	@Autowired
	ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	ServicioTasa servicioTasa;

	@Autowired
	ServicioEEFFNuevo servicioEEFF;

	@Autowired
	ServicioIvtmMatriculacion servicioIvtmMatriculacion;

	@Autowired
	ServicioFabricante servicioFabricante;

	@Autowired
	ServicioCredito servicioCredito;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioDatosSensibles servicioDatosSensibles;

	@Autowired
	BuildMatriculacionSega buildMatriculacionSega;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	ServicioImpresionMatriculacion servicioImpresionMatriculacion;

	@Autowired
	DocPermDistItvDao permDistiItvDao;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioPermisos servicioPermisos;

	@Autowired
	ServicioEmpresaTelematica servicioEmpresaTelematica;

	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrmDstvFicha;

	@Autowired
	ServicioEvolucionDocPrmDstvFicha servicioEvolucionDocPrmDstvFicha;

	@Autowired
	ServicioDistintivoVehNoMat servicioDistintivoVehNoMat;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioWebServiceAm servicioWebServiceAm;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional(readOnly = true)
	public List<ConsumoMaterialValue> getListaConsumoDstvJefaturaPorDia(String jefatura, Date fecha) {
		return tramiteTraficoMatrDao.getListaConsumoDstvJefaturaPorDia(jefatura, fecha);
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoDistintivoDgtBean existeTramiteDstvDuplicado(String matricula, Long idContrato) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			List<TramiteTrafMatrVO> lista = tramiteTraficoMatrDao.getTramitePorMatriculaContrato(matricula, idContrato);
			if (lista != null && !lista.isEmpty()) {
				if (lista.size() > 1) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Existen dos trámites para la misma matrícula. Por favor, verifique cuál es el correcto");
				} else if (!EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(lista.get(0).getEstadoImpDstv())) {
					resultado.setExisteTramiteDup(Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si existe algún trámite de matriculación para el distintivo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar si existe algún trámite de matriculación para el distintivo.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultBean guardarTramite(TramiteTrafMatrDto tramiteDto, IvtmMatriculacionDto ivtmMatriculacionDto,
			BigDecimal idUsuario, boolean permisoAdmin, boolean permisoLiberacion, boolean desdeValidar)
			throws OegamExcepcion {
		ResultBean respuesta = new ResultBean();
		TramiteTrafMatrVO tramiteMatriculacion = new TramiteTrafMatrVO();
		try {
			String validacionFecha = validarFechasMatriculacion(tramiteDto);
			if (!"OK".equals(validacionFecha)) {
				respuesta.addMensajeALista(validacionFecha);
				respuesta.setError(true);
			}
			respuesta = servicioEEFF.comprobarDatosModificadosLiberacion(tramiteDto);
			if (respuesta != null && respuesta.getError()) {
				return respuesta;
			}

			tramiteDto = cargaNumColegiado(tramiteDto);
			if ((tramiteDto.getJefaturaTraficoDto() == null
					|| tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial() == null
					|| "-1".equals(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial()))
					&& tramiteDto.getIdContrato() != null) {
				ContratoDto contratoDto = servicioContrato.getContratoDto(tramiteDto.getIdContrato());
				tramiteDto.setJefaturaTraficoDto(contratoDto.getJefaturaTraficoDto());
			}

			if (!respuesta.getError()) {
				if (tramiteDto.getTasa() != null) {
					ResultBean respuestaDesTasaExpediente = servicioTasa.desasignarTasaExpediente(tramiteDto.getTasa().getCodigoTasa(), tramiteDto.getNumExpediente(), null);
					respuesta.addListaMensajes(respuestaDesTasaExpediente.getListaMensajes());
				}

				UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
				tramiteDto.setUsuarioDto(usuario);
				
				if(tramiteDto.getNumExpediente() != null) {
					comprobarAutorizado(tramiteDto);
				}
				// Datos del VO
				tramiteMatriculacion = convertirDTOVO(tramiteDto);

				// Guardamos vehículo
				ResultBean respuestaVehiculo = guardarVehiculo(tramiteDto, tramiteMatriculacion, usuario, permisoAdmin);
				respuesta.addListaMensajes(respuestaVehiculo.getListaMensajes());

				ResultBean respuestaTramite = guardarTramite(tramiteDto, tramiteMatriculacion, desdeValidar, idUsuario);
				if (respuestaTramite != null && !respuestaTramite.getError()) {
					respuesta.addAttachment(NUMEXPEDIENTE, tramiteMatriculacion.getNumExpediente());

					// Guardamos interviniente
					ResultBean respuestaInterviniente = guardarIntervinientes(tramiteDto, tramiteMatriculacion, usuario);
					respuesta.addListaMensajes(respuestaInterviniente.getListaMensajes());

					if (tramiteDto.getTasa() != null && tramiteDto.getTasa().getCodigoTasa() != null
							&& !tramiteDto.getTasa().getCodigoTasa().isEmpty()
							&& !"-1".equals(tramiteDto.getTasa().getCodigoTasa())) {
						ResultBean respuestaTasaExpediente = servicioTasa.asignarTasa(tramiteDto.getTasa().getCodigoTasa(), tramiteMatriculacion.getNumExpediente());
						respuesta.addListaMensajes(respuestaTasaExpediente.getListaMensajes());
					}

					ResultBean respuestaLiberacion = guardarLiberacion(tramiteDto, tramiteMatriculacion.getNumExpediente(), permisoLiberacion, permisoAdmin);
					respuesta.addListaMensajes(respuestaLiberacion.getListaMensajes());

					guardarIvtm(tramiteDto, ivtmMatriculacionDto);

					ResultBean respuestaCaducidadNif = comprobarCaducidadNifs(tramiteDto, false);
					respuesta.addListaMensajes(respuestaCaducidadNif.getListaMensajes());
				} else {
					log.error(ERROR_GUARDAR_TRAMITE_MATRICULACION + tramiteMatriculacion.getNumExpediente() + ". Mensaje: " + respuestaTramite.getMensaje());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					respuesta.setError(true);
					respuesta.addMensajeALista(respuestaTramite.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error(ERROR_GUARDAR_TRAMITE_MATRICULACION + tramiteMatriculacion.getNumExpediente() + ". Mensaje: " + e.getMessage(), e, tramiteDto.getNumExpediente().toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.setError(true);
			respuesta.addMensajeALista(e.getMessage());
		}
		return respuesta;
	}

	private void comprobarAutorizado(TramiteTrafMatrDto tramiteDto) {
		TramiteTrafMatrVO trafMatrVO = getTramiteMatriculacionVO(tramiteDto.getNumExpediente(), Boolean.FALSE, Boolean.FALSE);
		if(trafMatrVO != null) {
			if(trafMatrVO.getAutorizadoFichaA() != null) {
				tramiteDto.setAutorizadoFichaA(trafMatrVO.getAutorizadoFichaA());
			}
			if(trafMatrVO.getAutorizadoExentoCtr() != null) {
				tramiteDto.setAutorizadoExentoCtr(trafMatrVO.getAutorizadoExentoCtr());
			}
		}
	}

	private ResultBean guardarVehiculo(TramiteTrafMatrDto tramiteDto, TramiteTrafMatrVO tramiteMatriculacion, UsuarioDto usuario, boolean admin) throws ParseException {
		ResultBean respuesta = new ResultBean();
		// Guardado Vehículo
		if ((tramiteDto.getVehiculoDto().getBastidor() == null || tramiteDto.getVehiculoDto().getBastidor().isEmpty())
				&& (tramiteDto.getVehiculoDto().getCodItv() == null
						|| tramiteDto.getVehiculoDto().getCodItv().isEmpty())) {
			respuesta.setMensaje("Los datos del Vehículo no se guardarán si no tienen Bastidor o Código ITV.");
			tramiteMatriculacion.setVehiculo(null);
		} else {
			tramiteDto = convertirDatosVehiculos(tramiteDto);
			Date fechaPresentacion = null;
			if (tramiteDto.getFechaPresentacion() != null) {
				fechaPresentacion = tramiteDto.getFechaPresentacion().getDate();
			}
			ResultBean respuestaVehiculo = servicioVehiculo.guardarVehiculoConPrever(tramiteDto.getVehiculoDto(),
					tramiteDto.getNumExpediente(), tramiteDto.getTipoTramite(), usuario, fechaPresentacion,
					ServicioVehiculo.CONVERSION_VEHICULO_MATRICULACION, tramiteDto.getVehiculoPrever(), admin);

			Object attachment = respuestaVehiculo.getAttachment(ServicioVehiculo.VEHICULO);
			if (attachment instanceof VehiculoVO) {
				tramiteMatriculacion.setVehiculo((VehiculoVO) attachment);
			} else {
				tramiteMatriculacion.setVehiculo(null);
			}
			if (respuestaVehiculo.getListaMensajes() != null && !respuestaVehiculo.getListaMensajes().isEmpty()) {
				for (String mensaje : respuestaVehiculo.getListaMensajes()) {
					respuesta.addMensajeALista(mensaje);
				}
			}
		}
		return respuesta;
	}

	private ResultBean guardarTramite(TramiteTrafMatrDto tramiteDto, TramiteTrafMatrVO tramiteMatriculacion, boolean desdeValidar, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {
			tramiteMatriculacion = convertirDatosTramite(tramiteMatriculacion);
			if (tramiteMatriculacion.getNumExpediente() == null) {
				BigDecimal numExpediente = servicioTramiteTrafico.generarNumExpediente(tramiteMatriculacion.getNumColegiado());
				log.debug("Creación trámite de matriculación: " + numExpediente);
				tramiteMatriculacion.setNumExpediente(numExpediente);
				tramiteDto.setNumExpediente(numExpediente);
				if (tramiteMatriculacion.getEstado() == null || (tramiteMatriculacion.getEstado() != null
						&& !EstadoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteMatriculacion.getEstado()))) {
					tramiteMatriculacion.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
				}
				// Actualizar la evolución vehículo
				if (tramiteMatriculacion.getVehiculo() != null) {
					servicioEvolucionVehiculo.actualizarEvolucionVehiculoCreacionOCopia(tramiteMatriculacion.getVehiculo().getIdVehiculo(), numExpediente);
				}

				Fecha fecha = utilesFecha.getFechaHoraActualLEG();
				tramiteMatriculacion.setFechaUltModif(fecha.getFechaHora());
				tramiteMatriculacion.setFechaAlta(fecha.getFechaHora());
				tramiteMatriculacion.setFechaPresentacion(fecha.getFechaHora());
				tramiteTraficoMatrDao.guardar(tramiteMatriculacion);
				guardarEvolucionTramite(tramiteDto, EstadoTramiteTrafico.Iniciado.getValorEnum(), idUsuario);
			} else {
				log.debug("Actualización trámite de matriculación: " + tramiteMatriculacion.getNumExpediente());
				tramiteMatriculacion.setFechaUltModif(utilesFecha.getFechaHoraActualLEG().getFechaHora());
				if (!desdeValidar) {
					try {
						tramiteMatriculacion.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						guardarEvolucionTramite(tramiteDto, EstadoTramiteTrafico.Iniciado.getValorEnum(), idUsuario);
					} catch (Exception e) {
						log.error("Error al guardar la evolución del trámite de matriculación: " + tramiteMatriculacion.getNumExpediente() + ". Mensaje: " + e.getMessage(), e, tramiteMatriculacion
								.getNumExpediente().toString());
					}
				}
				tramiteDto.setNumExpediente(tramiteMatriculacion.getNumExpediente());
				tramiteMatriculacion = tramiteTraficoMatrDao.actualizar(tramiteMatriculacion);
			}
		} catch (Exception e) {
			log.error(ERROR_GUARDAR_TRAMITE_MATRICULACION + tramiteMatriculacion.getNumExpediente() + ". Mensaje: "
					+ e.getMessage(), e, tramiteMatriculacion.getNumExpediente().toString());
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		if (result.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	private ResultBean guardarIntervinientes(TramiteTrafMatrDto tramiteDto, TramiteTrafMatrVO tramiteMatriculacion, UsuarioDto usuario) {
		ResultBean respuesta = new ResultBean();
		boolean direccionNueva = false;
		// Guardado de Intervinientes
		if (tramiteMatriculacion.getIntervinienteTraficos() != null && !tramiteMatriculacion.getIntervinienteTraficos().isEmpty()) {
			for (IntervinienteTraficoVO interviniente : tramiteMatriculacion.getIntervinienteTraficos()) {
				if (interviniente.getPersona() != null && interviniente.getPersona().getId() != null) {
					if (interviniente.getPersona().getId().getNif() != null && !interviniente.getPersona().getId().getNif().isEmpty()) {
						interviniente.getId().setNif(interviniente.getPersona().getId().getNif().toUpperCase());
						interviniente.getId().setNumExpediente(tramiteMatriculacion.getNumExpediente());
						interviniente.getPersona().getId().setNumColegiado(tramiteMatriculacion.getNumColegiado());
						// Estado para saber que está activo
						interviniente.getPersona().setEstado(Long.valueOf(1));
						// Se crea el anagrama
						String anagrama = Anagrama.obtenerAnagramaFiscal(interviniente.getPersona().getApellido1RazonSocial(), interviniente.getPersona().getId().getNif());
						interviniente.getPersona().setAnagrama(anagrama);

						// Guardar la persona antes de guardar el interviniente
						String conversion = null;

						if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())) {
							// Para titular
							conversion = ServicioPersona.CONVERSION_PERSONA_TITULAR;
						} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Arrendatario.getValorEnum())) {
							// Para arrendatario
							conversion = ServicioPersona.CONVERSION_PERSONA_ARRENDATARIO;
						} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.RepresentanteArrendatario.getValorEnum())) {
							// Para representante arrendatario
							conversion = ServicioPersona.CONVERSION_PERSONA_REPRESENTANTE_ARRENDATARIO;
						} else {
							// Para representanteTitular, ConductorHabitual
							conversion = ServicioPersona.CONVERSION_PERSONA_MATRICULACION;
						}

						// Guardar persona
						ResultBean resultPersona = servicioPersona.guardarActualizar(interviniente.getPersona(),
								tramiteMatriculacion.getNumExpediente(), tramiteMatriculacion.getTipoTramite(), usuario,
								conversion);

						if (!resultPersona.getError()) {
							// Guardar dirección
							if (interviniente.getDireccion() != null && utiles.convertirCombo(interviniente.getDireccion().getIdProvincia()) != null) {
								ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(
										interviniente.getDireccion(), interviniente.getPersona().getId().getNif(),
										interviniente.getPersona().getId().getNumColegiado(),
										tramiteDto.getTipoTramite(), ServicioDireccion.CONVERSION_DIRECCION_CORREOS);
								if (resultDireccion.getError()) {
									respuesta.addMensajeALista("- " + TipoInterviniente.convertirTexto(interviniente.getId().getTipoInterviniente()) + ": " + resultDireccion.getMensaje());
									interviniente.setIdDireccion(null);
								} else {
									DireccionVO direccion = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
									direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
									if (direccion != null) {
										interviniente.setIdDireccion(direccion.getIdDireccion());
									}
									servicioEvolucionPersona.guardarEvolucionPersonaDireccion(
											interviniente.getPersona().getId().getNif(),
											tramiteMatriculacion.getNumExpediente(),
											tramiteMatriculacion.getTipoTramite(),
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
			respuesta.addMensajeALista("No se ha guardado ningún interviniente");
		}
		return respuesta;
	}

	private void guardarEvolucionTramite(TramiteTrafMatrDto tramiteDto, String estadoNuevo, BigDecimal idUsuario) {
		if (!estadoNuevo.equals(tramiteDto.getEstado())) {
			EvolucionTramiteTraficoDto evolucion = new EvolucionTramiteTraficoDto();
			if (tramiteDto.getEstado() != null && !tramiteDto.getEstado().isEmpty()) {
				evolucion.setEstadoAnterior(new BigDecimal(tramiteDto.getEstado()));
			} else {
				evolucion.setEstadoAnterior(BigDecimal.ZERO);
			}
			evolucion.setEstadoNuevo(new BigDecimal(estadoNuevo));
			UsuarioDto usuarioDto = new UsuarioDto();
			usuarioDto.setIdUsuario(idUsuario);
			evolucion.setUsuarioDto(usuarioDto);
			evolucion.setNumExpediente(tramiteDto.getNumExpediente());
			servicioEvolucionTramite.guardar(evolucion);
		}
	}

	private TramiteTrafMatrDto convertirDatosVehiculos(TramiteTrafMatrDto tramiteDto) {
		// CO2 (formato 999.999)
		if (tramiteDto.getVehiculoDto().getCo2() != null && !tramiteDto.getVehiculoDto().getCo2().trim().isEmpty()) {
			BigDecimal co2 = new BigDecimal(tramiteDto.getVehiculoDto().getCo2()).setScale(3, BigDecimal.ROUND_DOWN);
			tramiteDto.getVehiculoDto().setCo2(co2.toString());
		}
		// Cilindrada (formato 999.99)
		if (tramiteDto.getVehiculoDto().getCilindrada() != null
				&& !tramiteDto.getVehiculoDto().getCilindrada().trim().isEmpty()
				&& tramiteDto.getVehiculoDto().getTipoVehiculo() != null
				&& !tramiteDto.getVehiculoDto().getTipoVehiculo().isEmpty()
				&& tramiteDto.getVehiculoDto().getTipoVehiculo().substring(0, 1).equals("9")
				&& tramiteDto.getVehiculoDto().getCilindrada().length() < 4) {
			BigDecimal cilind = new BigDecimal(tramiteDto.getVehiculoDto().getCilindrada()).setScale(2, BigDecimal.ROUND_DOWN);
			tramiteDto.getVehiculoDto().setCilindrada(cilind.toString());
		}
		return tramiteDto;
	}

	private TramiteTrafMatrVO convertirDatosTramite(TramiteTrafMatrVO tramiteMatriculacion) throws Exception {
		tramiteMatriculacion.setnRegIedtm(utiles.convertirCombo(tramiteMatriculacion.getnRegIedtm()));
		tramiteMatriculacion.setModeloIedtm(utiles.convertirCombo(tramiteMatriculacion.getModeloIedtm()));
		tramiteMatriculacion.setIdReduccion05(utiles.convertirCombo(tramiteMatriculacion.getIdReduccion05()));
		tramiteMatriculacion.setIedtm(utiles.convertirCombo(tramiteMatriculacion.getIedtm()));
		tramiteMatriculacion.setIdNoSujeccion06(utiles.convertirCombo(tramiteMatriculacion.getIdNoSujeccion06()));

		if (tramiteMatriculacion.getnRegIedtm() != null && !tramiteMatriculacion.getnRegIedtm().isEmpty()) {
			if (tramiteMatriculacion.getFechaPresentacion() != null) {
				tramiteMatriculacion.setFechaIedtm(tramiteMatriculacion.getFechaPresentacion());
			} else if (tramiteMatriculacion.getFechaIedtm() != null) {
				Fecha fecha = utilesFecha.getFechaHoraActualLEG();
				tramiteMatriculacion.setFechaIedtm(fecha.getFechaHora());
			}
		}
		return tramiteMatriculacion;
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafMatrDto getTramiteMatriculacion(BigDecimal numExpediente, boolean permisoLiberacion, boolean tramiteCompleto) throws OegamExcepcion {
		TramiteTrafMatrDto tramiteTrafMatrDto = null;
		try {
			TramiteTrafMatrVO tramiteTrafMatrVO = getTramiteMatriculacionVO(numExpediente, permisoLiberacion, tramiteCompleto);
			if (tramiteTrafMatrVO != null) {
				tramiteTrafMatrDto = conversor.transform(tramiteTrafMatrVO, TramiteTrafMatrDto.class);

				if (tramiteTrafMatrDto.getRenting() != null && !tramiteTrafMatrDto.getRenting()) {
					tramiteTrafMatrDto.setRenting(DecisionTrafico.Si.getValorEnum().equals(tramiteTrafMatrDto.getRenting()) ? Boolean.TRUE : Boolean.FALSE);
				}

				if (tramiteTrafMatrVO.getIntervinienteTraficos() != null && !tramiteTrafMatrVO.getIntervinienteTraficos().isEmpty()) {
					for (IntervinienteTraficoVO interviniente : tramiteTrafMatrVO.getIntervinienteTraficosAsList()) {
						if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())) {
							tramiteTrafMatrDto.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.RepresentanteTitular.getValorEnum())) {
							tramiteTrafMatrDto.setRepresentanteTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Arrendatario.getValorEnum())) {
							tramiteTrafMatrDto.setArrendatario(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.RepresentanteArrendatario.getValorEnum())) {
							tramiteTrafMatrDto.setRepresentanteArrendatario(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						} else if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.ConductorHabitual.getValorEnum())) {
							tramiteTrafMatrDto.setConductorHabitual(conversor.transform(interviniente, IntervinienteTraficoDto.class));
						}
					}
				}

				if (tramiteTrafMatrVO.getVehiculo() != null && tramiteTrafMatrVO.getVehiculo().getIdVehiculoPrever() != null) {
					tramiteTrafMatrDto.setVehiculoPrever(servicioVehiculo.getVehiculoDto(Long.valueOf(tramiteTrafMatrVO.getVehiculo().getIdVehiculoPrever().longValue()), null, null, null, null,
							null));
				}

				if (tramiteTrafMatrVO.getTramiteFacturacion() != null && !tramiteTrafMatrVO.getTramiteFacturacion().isEmpty() && null != tramiteTrafMatrVO.getFirstElementTramiteFacturacion()) {
					TramiteTrafFacturacionDto tramiteTraf = conversor.transform(tramiteTrafMatrVO.getFirstElementTramiteFacturacion(), TramiteTrafFacturacionDto.class);
					tramiteTrafMatrDto.setTramiteFacturacion(tramiteTraf);
				}

				// Si existe facturación se debe de recuperar la persona y la dirección
				if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getTramiteFacturacion() != null && tramiteTrafMatrDto.getTramiteFacturacion().getNif() != null) {
					ResultBean resultPer = servicioPersona.buscarPersona(tramiteTrafMatrDto.getTramiteFacturacion().getNif(), tramiteTrafMatrDto.getNumColegiado());
					if (resultPer != null && !resultPer.getError()) {
						PersonaDto personaDto = (PersonaDto) resultPer.getAttachment(ServicioPersona.PERSONA);
						tramiteTrafMatrDto.getTramiteFacturacion().setPersona(personaDto);
						ResultBean resultDir = servicioPersonaDireccion.buscarPersonaDireccionDto(tramiteTrafMatrDto.getNumColegiado(), personaDto.getNif());
						if (resultDir != null && !resultDir.getError()) {
							PersonaDireccionDto personaDDto = (PersonaDireccionDto) resultDir.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
							if (personaDDto.getDireccion() != null)
								tramiteTrafMatrDto.getTramiteFacturacion().setDireccion(personaDDto.getDireccion());
						}
					}
				}

				if (permisoLiberacion && tramiteTrafMatrDto.getListaConsultaEEFF() != null && tramiteTrafMatrDto.getListaConsultaEEFF().isEmpty()) {
					ordenarListaConsultaEEFF(tramiteTrafMatrDto.getListaConsultaEEFF());
				}
			}
		} catch (Exception e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		}
		return tramiteTrafMatrDto;
	}

	@Override
	@Transactional
	public TramiteTrafMatrVO getTramiteMatriculacionVO(BigDecimal numExpediente, boolean permisoLiberacion, boolean tramiteCompleto) {
		try {
			return tramiteTraficoMatrDao.getTramiteTrafMatr(numExpediente, tramiteCompleto, permisoLiberacion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el trámite de Matriculación ,error: ", e, numExpediente.toString());
		}
		return null;
	}

	public void ordenarListaConsultaEEFF(List<ConsultaEEFFDto> lista) {
		Collections.sort(lista, new Comparator<ConsultaEEFFDto>() {
			@Override
			public int compare(ConsultaEEFFDto cEEFF1, ConsultaEEFFDto cEEFF2) {
				return cEEFF1.getFechaRealizacion().compareTo(cEEFF2.getFechaRealizacion());
			}
		});
	}

	@Override
	@Transactional
	public ResultBean matriculado(TramiteTrafMatrDto tramiteDto, String matricula, BigDecimal idUsuario, boolean admin) {
		ResultBean respuesta = new ResultBean();
		try {
			tramiteDto.getVehiculoDto().setMatricula(matricula);
			tramiteDto.getVehiculoDto().setFechaMatriculacion(utilesFecha.getFechaHoraActual());
			tramiteDto.getVehiculoDto().setFechaPrimMatri(utilesFecha.getFechaHoraActual());
			tramiteDto.getVehiculoDto().setLugarAdquisicion("1");
			UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
			respuesta = servicioVehiculo.guardarVehiculoConPrever(tramiteDto.getVehiculoDto(),
					tramiteDto.getNumExpediente(), ServicioVehiculo.MATRICULAR, usuario,
					tramiteDto.getFechaPresentacion().getDate(), ServicioVehiculo.CONVERSION_VEHICULO_MATRICULACION,
					tramiteDto.getVehiculoPrever(), admin);
		} catch (Exception e) {
			log.error("Error pendiente envio excel matriculación: " + tramiteDto.getNumExpediente(), e, tramiteDto.getNumExpediente().toString());
			respuesta.setError(true);
			respuesta.setMensaje("Error al modificar la matrícula del vehículo: " + e.getMessage());
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ResultBean tramitacion(BigDecimal numExpediente, BigDecimal idUsuario, String aliasColegiado, String colegio, boolean permisoLiberacion, BigDecimal idContrato) throws Throwable {
		ResultBean respuesta = new ResultBean(Boolean.FALSE);
		try {
			TramiteTrafMatrDto tramiteDto = getTramiteMatriculacion(numExpediente, permisoLiberacion, true);
			respuesta = comprobacionDatosSensibles(tramiteDto, idUsuario);
			if (!respuesta.getError()) {
				String gestionarConAM = gestorPropiedades.valorPropertie("gestionar.conAm");
				if ("2631".equals(tramiteDto.getNumColegiado()) && "SI".equals(gestionarConAM)) {
					try {
						ResultBean resSolcicitud = servicioCola.crearSolicitud(ProcesosAmEnum.MATW.getValorEnum(), null, gestorPropiedades.valorPropertie("nombreHostSolicitud"),
								TipoTramiteTrafico.Matriculacion.getValorEnum(), tramiteDto.getNumExpediente().toString(), idUsuario, null, tramiteDto.getIdContrato());
						if (!resSolcicitud.getError()) {
							log.info("Se agregó correctamente en la cola el tr\341mite de MATW:" + tramiteDto.getNumExpediente());
							respuesta.setMensaje("Solicitud creada correctamente");
							try {
								respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteDto.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Envio, EstadoTramiteTrafico.convertir(
										tramiteDto.getEstado()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
								if (respuesta != null && respuesta.getError()) {
									log.error("Se ha producido un error al actualizar al estado anterior el tramite." + respuesta.getMensaje());
								}
							} catch (Exception e) {
								log.error("Se ha producido un error al actualizar al estado anterior el tramite, ya que no se ha podido crear la solicitud. Error: " + e.getMessage(), e, tramiteDto
										.getNumExpediente().toString());
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
					respuesta = servicioCredito.validarCreditos(TipoTramiteTrafico.Matriculacion.getValorEnum(), tramiteDto.getIdContrato(), new BigDecimal(1));
					if (!respuesta.getError()) {
						respuesta = validarDatosLiberacion(tramiteDto, permisoLiberacion, Boolean.TRUE);
						if (!respuesta.getError()) {
							if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("nuevas.url.sega.matw"))) {
								respuesta = tramitarSega(tramiteDto, idUsuario, aliasColegiado, colegio, idContrato);
							} else {
								respuesta = tramitarSiga(tramiteDto, idUsuario, aliasColegiado, colegio);
							}
						}
					}
				}
			} else {
				respuesta.setError(Boolean.TRUE);
				respuesta.setMensaje(respuesta.getMensaje());
			}
		} catch (Throwable e) {
			log.error("Error al tramitar el trámite con num expediente: " + numExpediente, e, numExpediente.toString());
			respuesta.setError(true);
			respuesta.setMensaje("Error al tramitar: " + e.getMessage());
		}
		return respuesta;
	}

	private ResultBean tramitarSega(TramiteTrafMatrDto tramiteDto, BigDecimal idUsuario, String aliasColegiado, String colegio, BigDecimal idContrato) throws Throwable {
		ResultBean respuesta = new ResultBean(Boolean.FALSE);
		org.gestoresmadrid.oegam2comun.sega.matw.view.xml.SolicitudRegistroEntrada solicitudRegistroEntrada = buildMatriculacionSega.obtenerSolicitudRegistroEntrada(tramiteDto, colegio);
		ResultBean resultadoFirmasBean = buildMatriculacionSega.anhadirFirmasHsm(solicitudRegistroEntrada);
		if (!resultadoFirmasBean.getError()) {
			respuesta = buildMatriculacionSega.completarSolicitud(solicitudRegistroEntrada, resultadoFirmasBean.getMensaje());
			if (respuesta != null && !respuesta.getError()) {
				respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteDto.getNumExpediente(), EstadoTramiteTrafico.convertir(tramiteDto.getEstado()),
						EstadoTramiteTrafico.Pendiente_Envio, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
				if (respuesta != null && !respuesta.getError()) {
					try {
						ResultBean resSolcicitud = servicioCola.crearSolicitud(ProcesosEnum.MATW_SEGA.getNombreEnum(), (new StringBuilder()).append(ProcesosEnum.MATW.getNombreEnum()).append(tramiteDto
								.getNumExpediente()).toString(), gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.Matriculacion.getValorEnum(), tramiteDto.getNumExpediente()
										.toString(), idUsuario, null, tramiteDto.getIdContrato());
						if (!resSolcicitud.getError()) {
							log.info("Se agregó correctamente en la cola el tr\341mite de MATW:" + tramiteDto.getNumExpediente());
							respuesta.setMensaje("Solicitud creada correctamente");
						} else {
							try {
								respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteDto.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Envio, EstadoTramiteTrafico.convertir(
										tramiteDto.getEstado()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
								if (respuesta != null && respuesta.getError()) {
									log.error("Se ha producido un error al actualizar al estado anterior el tramite." + respuesta.getMensaje());
								}
							} catch (Exception e) {
								log.error("Se ha producido un error al actualizar al estado anterior el tramite, ya que no se ha podido crear la solicitud. Error: " + e.getMessage(), e, tramiteDto
										.getNumExpediente().toString());
							}
							respuesta.setError(Boolean.TRUE);
							respuesta.setMensaje(respuesta.getMensaje());
						}
					} catch (OegamExcepcion e) {
						respuesta.setError(Boolean.TRUE);
						respuesta.setMensaje(e.getMensajeError1());
					}
				}
			}
		} else {
			respuesta.setMensaje(resultadoFirmasBean.getMensaje());
			respuesta.setError(Boolean.TRUE);
		}
		return respuesta;
	}

	private ResultBean tramitarSiga(TramiteTrafMatrDto tramiteDto, BigDecimal idUsuario, String aliasColegiado, String colegio) throws Throwable {
		ResultBean respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteDto.getNumExpediente(), EstadoTramiteTrafico.convertir(tramiteDto.getEstado()),
				EstadoTramiteTrafico.Pendiente_Envio, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
		if (respuesta != null && !respuesta.getError()) {
			try {
				ResultBean resSolcicitud = servicioCola.crearSolicitud(ProcesosEnum.MATW.getNombreEnum(), null, gestorPropiedades.valorPropertie("nombreHostSolicitud"),
						TipoTramiteTrafico.Matriculacion.getValorEnum(), tramiteDto.getNumExpediente().toString(), idUsuario, null, tramiteDto.getIdContrato());
				if (!resSolcicitud.getError()) {
					log.info("Se agregó correctamente en la cola el trámite de MATW:" + tramiteDto.getNumExpediente());
					respuesta.setMensaje("Solicitud creada correctamente");
				} else {
					try {
						respuesta = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteDto.getNumExpediente(), EstadoTramiteTrafico.Pendiente_Envio, EstadoTramiteTrafico.convertir(tramiteDto
								.getEstado()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
						if (respuesta != null && respuesta.getError()) {
							log.error("Se ha producido un error al actualizar al estado anterior el tramite." + respuesta.getMensaje());
						}
					} catch (Exception e) {
						log.error("Se ha producido un error al actualizar al estado anterior el tramite, ya que no se ha podido crear la solicitud. Error: " + e.getMessage(), e, tramiteDto
								.getNumExpediente().toString());
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

	private ResultBean comprobacionDatosSensibles(TramiteTrafMatrDto tramiteDto, BigDecimal idUsuario) throws OegamExcepcion {
		ResultBean respuesta = new ResultBean();
		try {
			String[] numExpedientes = new String[] { tramiteDto.getNumExpediente().toString() };
			List<String> expedientesSensibles = null;
			String datosSensiblesAnt = gestorPropiedades.valorPropertie(antiguoDatosSensibles);
			if ("true".equals(datosSensiblesAnt)) {
				expedientesSensibles = new ComprobadorDatosSensibles().isSensibleData(numExpedientes);
			} else {
				expedientesSensibles = servicioDatosSensibles.existenDatosSensiblesPorExpedientes(utiles.convertirStringArrayToBigDecimal(numExpedientes), idUsuario);
			}
			if (expedientesSensibles != null && !expedientesSensibles.isEmpty()) {
				for (int j = 0; j < expedientesSensibles.size(); j++) {
					respuesta.setMensaje("Se ha recibido un error técnico. No intenten presentar el trámite. Les rogamos comuniquen con el Colegio.");
					respuesta.setError(Boolean.TRUE);
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Datos sensibles en el trámite " + expedientesSensibles.get(j));
				}
				return respuesta;
			}
		} catch (Exception e) {}
		return respuesta;
	}

	@Override
	@Transactional
	public void guardarOActualizar(TramiteTrafMatrDto tramiteTrafMatrDto) throws OegamExcepcion {
		try {
			TramiteTrafMatrVO tramiteTrafMatrVO = conversor.transform(tramiteTrafMatrDto, TramiteTrafMatrVO.class);
			guardarOActualizar(tramiteTrafMatrVO);
		} catch (Exception e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		}
	}

	@Override
	@Transactional
	public void guardarOActualizar(TramiteTrafMatrVO tramiteTrafMatrVO) throws OegamExcepcion {
		try {
			tramiteTraficoMatrDao.guardarOActualizar(tramiteTrafMatrVO);
		} catch (Exception e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		}
	}

	private TramiteTrafMatrDto cargaNumColegiado(TramiteTrafMatrDto tramiteDto) {
		if (tramiteDto.getVehiculoDto() != null && (tramiteDto.getVehiculoDto().getNumColegiado() == null
				|| tramiteDto.getVehiculoDto().getNumColegiado().isEmpty())) {
			tramiteDto.getVehiculoDto().setNumColegiado(tramiteDto.getNumColegiado());
		}
		return tramiteDto;
	}

	private TramiteTrafMatrVO convertirDTOVO(TramiteTrafMatrDto tramiteDto) {
		TramiteTrafMatrVO tramiteMatriculacion = conversor.transform(tramiteDto, TramiteTrafMatrVO.class);

		if (tramiteMatriculacion.getContrato() == null) {
			ContratoVO contrato = new ContratoVO();
			contrato.setIdContrato(tramiteDto.getIdContrato().longValue());
			tramiteMatriculacion.setContrato(contrato);
		}

		if (tramiteDto.getTitular() != null && tramiteDto.getTitular().getPersona() != null
				&& tramiteDto.getTitular().getPersona().getNif() != null
				&& !tramiteDto.getTitular().getPersona().getNif().isEmpty()
				&& (tramiteDto.getTitular().getNifInterviniente() == null
						|| tramiteDto.getTitular().getNifInterviniente().isEmpty())) {
			tramiteDto.getTitular().setNifInterviniente(tramiteDto.getTitular().getPersona().getNif());
			tramiteDto.getTitular().setNumColegiado(tramiteDto.getNumColegiado());
		}
		if (tramiteDto.getTitular() != null && tramiteDto.getTitular().getTipoInterviniente() != null
				&& tramiteDto.getTitular().getNifInterviniente() != null
				&& !tramiteDto.getTitular().getTipoInterviniente().isEmpty()
				&& !tramiteDto.getTitular().getNifInterviniente().isEmpty()) {
			if (tramiteMatriculacion.getIntervinienteTraficos() == null) {
				tramiteMatriculacion.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteMatriculacion.getIntervinienteTraficos().add(conversor.transform(tramiteDto.getTitular(), IntervinienteTraficoVO.class));
		}

		if (tramiteDto.getRepresentanteTitular() != null && tramiteDto.getRepresentanteTitular().getPersona() != null
				&& tramiteDto.getRepresentanteTitular().getPersona().getNif() != null
				&& !tramiteDto.getRepresentanteTitular().getPersona().getNif().isEmpty()
				&& (tramiteDto.getRepresentanteTitular().getNifInterviniente() == null
						|| tramiteDto.getRepresentanteTitular().getNifInterviniente().isEmpty())) {
			tramiteDto.getRepresentanteTitular().setNifInterviniente(tramiteDto.getRepresentanteTitular().getPersona().getNif());
			tramiteDto.getRepresentanteTitular().setNumColegiado(tramiteDto.getNumColegiado());
		}
		if (tramiteDto.getRepresentanteTitular() != null
				&& tramiteDto.getRepresentanteTitular().getTipoInterviniente() != null
				&& tramiteDto.getRepresentanteTitular().getNifInterviniente() != null
				&& !tramiteDto.getRepresentanteTitular().getTipoInterviniente().isEmpty()
				&& !tramiteDto.getRepresentanteTitular().getNifInterviniente().isEmpty()) {
			if (tramiteMatriculacion.getIntervinienteTraficos() == null) {
				tramiteMatriculacion.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteMatriculacion.getIntervinienteTraficos().add(conversor.transform(tramiteDto.getRepresentanteTitular(), IntervinienteTraficoVO.class));
		}

		if (tramiteDto.getArrendatario() != null && tramiteDto.getArrendatario().getPersona() != null
				&& tramiteDto.getArrendatario().getPersona().getNif() != null
				&& !tramiteDto.getArrendatario().getPersona().getNif().isEmpty()
				&& (tramiteDto.getArrendatario().getNifInterviniente() == null
						|| tramiteDto.getArrendatario().getNifInterviniente().isEmpty())) {
			tramiteDto.getArrendatario().setNifInterviniente(tramiteDto.getArrendatario().getPersona().getNif());
			tramiteDto.getArrendatario().setNumColegiado(tramiteDto.getNumColegiado());
		}
		if (tramiteDto.getArrendatario() != null && tramiteDto.getArrendatario().getTipoInterviniente() != null
				&& tramiteDto.getArrendatario().getNifInterviniente() != null
				&& !tramiteDto.getArrendatario().getTipoInterviniente().isEmpty()
				&& !tramiteDto.getArrendatario().getNifInterviniente().isEmpty()) {
			if (tramiteMatriculacion.getIntervinienteTraficos() == null) {
				tramiteMatriculacion.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteMatriculacion.getIntervinienteTraficos().add(conversor.transform(tramiteDto.getArrendatario(), IntervinienteTraficoVO.class));
		}

		if (tramiteDto.getRepresentanteArrendatario() != null
				&& tramiteDto.getRepresentanteArrendatario().getPersona() != null
				&& tramiteDto.getRepresentanteArrendatario().getPersona().getNif() != null
				&& !tramiteDto.getRepresentanteArrendatario().getPersona().getNif().isEmpty()
				&& (tramiteDto.getRepresentanteArrendatario().getNifInterviniente() == null
						|| tramiteDto.getRepresentanteArrendatario().getNifInterviniente().isEmpty())) {
			tramiteDto.getRepresentanteArrendatario().setNifInterviniente(tramiteDto.getRepresentanteArrendatario().getPersona().getNif());
			tramiteDto.getRepresentanteArrendatario().setNumColegiado(tramiteDto.getNumColegiado());
		}
		if (tramiteDto.getRepresentanteArrendatario() != null
				&& tramiteDto.getRepresentanteArrendatario().getTipoInterviniente() != null
				&& tramiteDto.getRepresentanteArrendatario().getNifInterviniente() != null
				&& !tramiteDto.getRepresentanteArrendatario().getTipoInterviniente().isEmpty()
				&& !tramiteDto.getRepresentanteArrendatario().getNifInterviniente().isEmpty()) {
			if (tramiteMatriculacion.getIntervinienteTraficos() == null) {
				tramiteMatriculacion.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteMatriculacion.getIntervinienteTraficos().add(conversor.transform(tramiteDto.getRepresentanteArrendatario(), IntervinienteTraficoVO.class));
		}

		if (tramiteDto.getConductorHabitual() != null && tramiteDto.getConductorHabitual().getPersona() != null
				&& tramiteDto.getConductorHabitual().getPersona().getNif() != null
				&& !tramiteDto.getConductorHabitual().getPersona().getNif().isEmpty()
				&& (tramiteDto.getConductorHabitual().getNifInterviniente() == null
						|| tramiteDto.getConductorHabitual().getNifInterviniente().isEmpty())) {
			tramiteDto.getConductorHabitual().setNifInterviniente(tramiteDto.getConductorHabitual().getPersona().getNif());
			tramiteDto.getConductorHabitual().setNumColegiado(tramiteDto.getNumColegiado());
		}
		if (tramiteDto.getConductorHabitual() != null
				&& tramiteDto.getConductorHabitual().getTipoInterviniente() != null
				&& tramiteDto.getConductorHabitual().getNifInterviniente() != null
				&& !tramiteDto.getConductorHabitual().getTipoInterviniente().isEmpty()
				&& !tramiteDto.getConductorHabitual().getNifInterviniente().isEmpty()) {
			if (tramiteMatriculacion.getIntervinienteTraficos() == null) {
				tramiteMatriculacion.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
			}
			tramiteMatriculacion.getIntervinienteTraficos().add(conversor.transform(tramiteDto.getConductorHabitual(), IntervinienteTraficoVO.class));
		}

		if (tramiteMatriculacion.getTasa() == null || !servicioTasa.comprobarTasa(tramiteMatriculacion.getTasa().getCodigoTasa(), tramiteMatriculacion.getNumExpediente())) {
			tramiteMatriculacion.setTasa(null);
		}

		return tramiteMatriculacion;
	}

	@Override
	@Transactional
	public boolean tramiteModificadoParaLiberacion(TramiteTrafMatrDto tramiteDto) throws OegamExcepcion {
		boolean modificado = false;
		if (tramiteDto != null && (titularModificado(tramiteDto.getTitular(), tramiteDto)
				|| vehiculoModificado(tramiteDto.getVehiculoDto(), tramiteDto.getVehiculoDto()))) {
			modificado = true;
		}
		return modificado;
	}

	@Override
	@Transactional
	public ResultBean validarTramite(TramiteTrafMatrDto tramiteDto) throws ParseException {
		log.debug("Validar trámite matriculación: " + tramiteDto.getNumExpediente());
		ResultBean respuesta = new ResultBean();
		String gestionarConAM = gestorPropiedades.valorPropertie("gestionar.conAm");
		if ("2631".equals(tramiteDto.getNumColegiado()) && "SI".equals(gestionarConAM)) {
			ResultadoBean resultVal = servicioWebServiceAm.validarMatw(tramiteDto.getNumExpediente(), tramiteDto.getUsuarioDto().getIdUsuario().longValue());
			if (!resultVal.getError()) {
				if (StringUtils.isNotBlank(resultVal.getEstado())) {
					respuesta.addAttachment(ESTADO_VALIDAR, Long.parseLong(resultVal.getEstado()));
					if (StringUtils.isNotBlank(resultVal.getMensaje())) {
						respuesta.addMensajeALista(resultVal.getMensaje());
					}
				} else {
					respuesta.setError(Boolean.TRUE);
					respuesta.addMensajeALista("No se ha podido recuperar el estado del trámite: " + tramiteDto.getNumExpediente());
				}
			} else {
				respuesta.setError(Boolean.TRUE);
				respuesta.addMensajeALista(resultVal.getMensaje());
			}
		} else {
			respuesta = validacionesBloqueantes(tramiteDto);
			if (!respuesta.getError()) {
				try {
					if (tramiteDto != null && tramiteDto.getNumExpediente() != null) {
						TramiteTrafMatrVO tramiteVO = conversor.transform(tramiteDto, TramiteTrafMatrVO.class);
						ValidacionTramite vTramite = validacionTramiteDao.validarTramiteMatriculacion(tramiteVO);
						if (vTramite != null && vTramite.getSqlerrm().equals("Correcto")) {
							log.debug("Trámite matriculación validado: " + tramiteDto.getNumExpediente());
							try {
								boolean evolucionGuardada = vTramite.getInformacion().contains("ev guardada");

								tramiteVO.setEstado(new BigDecimal(vTramite.getEstado()));
								tramiteTraficoMatrDao.guardarOActualizar(tramiteVO);
								if(utiles.esUsuarioExentoCtr(tramiteVO.getNumColegiado())) {
									if(esExentoCtr(tramiteVO,respuesta)) {
										if(respuesta.isEsValidacionAutomatizada()) {
											tramiteVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()));
											tramiteVO.setAutorizadoExentoCtr("SI");
										}else {
											tramiteVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum()));
										}
										tramiteVO.setExentoCtr("SI");
										tramiteTraficoMatrDao.guardarOActualizar(tramiteVO);
										vTramite.setEstado(tramiteVO.getEstado().longValue());
									}
								}
								List<EvolucionTramiteTraficoVO> evolucion = servicioEvolucionTramite.getTramiteFinalizadoErrorAutorizado(tramiteVO.getNumExpediente());
								if (evolucion != null && !evolucion.isEmpty()) {
									tramiteVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()));
									vTramite.setEstado(tramiteVO.getEstado().longValue());
									tramiteTraficoMatrDao.guardarOActualizar(tramiteVO);
								}
								if (!evolucionGuardada) {
									vTramite.setInformacion(vTramite.getInformacion().replace("ev no guardada", ""));
									guardarEvolucionTramite(tramiteDto, tramiteVO.getEstado().toString(), tramiteDto.getUsuarioDto().getIdUsuario());
								} else {
									vTramite.setInformacion(vTramite.getInformacion().replace("ev guardada", ""));
								}
							} catch (Exception e) {
								log.error("Error al actualizar el estado del trámite de matriculación: " + tramiteDto.getNumExpediente(), e, tramiteDto.getNumExpediente().toString());
								respuesta.setMensaje("Error al actualizar el estado del trámite de matriculación: " + tramiteDto.getNumExpediente());
								respuesta.setError(true);
							}
							respuesta.setError(false);
							if (vTramite.getInformacion() != null) {
								respuesta.addMensajeALista(vTramite.getInformacion());
							}
							respuesta.addAttachment(ESTADO_VALIDAR, vTramite.getEstado());
						} else {
							respuesta.setError(true);
							respuesta.addMensajeALista(vTramite != null ? vTramite.getSqlerrm() : "Error al validar");
						}
					} else {
						respuesta.setError(true);
						respuesta.addMensajeALista("Error en el trámite");
					}
				} catch (Exception e) {
					log.error("Error al validar el trámite de matriculación: " + tramiteDto.getNumExpediente(), e, tramiteDto.getNumExpediente().toString());
					respuesta.addMensajeALista("Error al validar el trámite de matriculación");
					respuesta.setError(true);
				}
			}
		}
		return respuesta;
	}

	private boolean esExentoCtr(TramiteTrafMatrVO tramiteVO, ResultBean respuesta) {
		if(esTransporteBasura(tramiteVO,respuesta) || 
				esTransporteDinero(tramiteVO,respuesta) ||
				esVelMaxAutorizada(tramiteVO,respuesta) ||
				esVehUnidoMaquina(tramiteVO,respuesta) ||
				esEmpresaASC(tramiteVO,respuesta) ||
				esMinistCCEntLocal(tramiteVO,respuesta) ||
				esAutoescuela(tramiteVO,respuesta) ||
				esCompraventaVeh(tramiteVO,respuesta) ||
				esVivienda(tramiteVO,respuesta)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public boolean esTransporteBasura(TramiteTrafMatrVO tramiteVO, ResultBean respuesta) {
		if (TipoServicio.A_11.value().equalsIgnoreCase(tramiteVO.getVehiculo().getIdServicio()) && "55".equalsIgnoreCase(tramiteVO.getVehiculo().getIdCriterioUtilizacion())) {
			respuesta.setEsValidacionAutomatizada(Boolean.TRUE);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public boolean esTransporteDinero(TramiteTrafMatrVO tramiteVO, ResultBean respuesta) {
		if ("22".equalsIgnoreCase(tramiteVO.getVehiculo().getIdCriterioUtilizacion())) {
			respuesta.setEsValidacionAutomatizada(Boolean.TRUE);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public boolean esVelMaxAutorizada(TramiteTrafMatrVO tramiteVO, ResultBean respuesta) {
		int velMax = 40;
		if ("70".equalsIgnoreCase(tramiteVO.getVehiculo().getTipoVehiculo()) && tramiteVO.getVehiculo().getVelocidadMaxima().intValue() <= velMax) {
			respuesta.setEsValidacionAutomatizada(Boolean.TRUE);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	

	public boolean esVehUnidoMaquina(TramiteTrafMatrVO tramiteVO, ResultBean respuesta) {
		String[] codigo = gestorPropiedades.valorPropertie("criterio.utilizacion.maquina").split(",");
		for (String elemento : codigo) {
			if (elemento.replaceAll("\\n","").equalsIgnoreCase(tramiteVO.getVehiculo().getIdCriterioUtilizacion())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public boolean esEmpresaASC(TramiteTrafMatrVO tramiteVO, ResultBean respuesta) {
		for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteVO.getIntervinienteTraficosAsList()) {
			if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente()) && TipoPersona.Juridica.getValorEnum().equalsIgnoreCase(intervinienteTraficoVO.getPersona().getTipoPersona())){
				return(intervinienteTraficoVO.getAutonomo() != null && ("SI".equals(intervinienteTraficoVO.getAutonomo()) || "S".equals(intervinienteTraficoVO.getAutonomo()) || "true".equals(intervinienteTraficoVO.getAutonomo()))
						&& ("854".equalsIgnoreCase(intervinienteTraficoVO.getCodigoIae()) ||"8541".equalsIgnoreCase(intervinienteTraficoVO.getCodigoIae()) || "8542".equalsIgnoreCase(intervinienteTraficoVO.getCodigoIae())));
			}
		}
		return false;
	}
	
	public boolean esMinistCCEntLocal(TramiteTrafMatrVO tramiteVO, ResultBean respuesta) {
		for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteVO.getIntervinienteTraficosAsList()) {
			if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
				if (intervinienteTraficoVO.getId().getNif().toUpperCase().startsWith("P") || intervinienteTraficoVO.getId().getNif().toUpperCase().startsWith("Q") 
						|| intervinienteTraficoVO.getId().getNif().toUpperCase().startsWith("S")) {
					respuesta.setEsValidacionAutomatizada(Boolean.TRUE);
					return Boolean.TRUE;
				}
				String[] codigo = gestorPropiedades.valorPropertie("nif.exentos.ctr").split(",");
				for (String elemento : codigo) {
					if (elemento.replaceAll("\\n","").equalsIgnoreCase(intervinienteTraficoVO.getId().getNif().toUpperCase())) {
						return Boolean.TRUE;
					}
				}
		
			}
		}
		return Boolean.FALSE;
		 
	}
	
	public boolean esAutoescuela(TramiteTrafMatrVO tramiteVO, ResultBean respuesta) {
		for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteVO.getIntervinienteTraficosAsList()) {
			if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
				return(intervinienteTraficoVO.getAutonomo() != null && ("SI".equals(intervinienteTraficoVO.getAutonomo()) || "S".equals(intervinienteTraficoVO.getAutonomo()) || "true".equals(intervinienteTraficoVO.getAutonomo()))
						&& "9331".equalsIgnoreCase(intervinienteTraficoVO.getCodigoIae()));
			}
		}
		return false;
	}
	
	public boolean esCompraventaVeh(TramiteTrafMatrVO tramiteVO, ResultBean respuesta) {
		for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteVO.getIntervinienteTraficosAsList()) {
			if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente()) && TipoPersona.Juridica.getValorEnum().equalsIgnoreCase(intervinienteTraficoVO.getPersona().getTipoPersona())){
				return(intervinienteTraficoVO.getAutonomo() != null && ("SI".equals(intervinienteTraficoVO.getAutonomo()) || "S".equals(intervinienteTraficoVO.getAutonomo()) || "true".equals(intervinienteTraficoVO.getAutonomo()))
						&& ("6151".equalsIgnoreCase(intervinienteTraficoVO.getCodigoIae()) || "6541".equalsIgnoreCase(intervinienteTraficoVO.getCodigoIae())));
			}
		}
		return false;
	}

	public boolean esVivienda(TramiteTrafMatrVO tramiteVO, ResultBean respuesta) {
		if (TipoServicio.B_17.value().equalsIgnoreCase(tramiteVO.getVehiculo().getIdServicio()) && "48".equalsIgnoreCase(tramiteVO.getVehiculo().getIdCriterioUtilizacion())) {
			respuesta.setEsValidacionAutomatizada(Boolean.TRUE);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@Override
	@Transactional
	public DatosCardMatwDto datosCardMatw(BigDecimal numExpediente) {
		log.debug("Datos Card Matw trámite matriculación: " + numExpediente);
		DatosCardMatwDto datosCardMatw = null;
		try {
			TramiteTrafMatrVO tramiteVO = tramiteTraficoMatrDao.datosCardMatw(numExpediente);
			if (tramiteVO != null) {
				datosCardMatw = conversor.transform(tramiteVO, DatosCardMatwDto.class);

				if (tramiteVO.getExento576() != null && "SI".equalsIgnoreCase(tramiteVO.getExento576())) {
					datosCardMatw.setHasForm576(Boolean.FALSE);
				} else {
					datosCardMatw.setHasForm576(Boolean.TRUE);
				}
				if (tramiteVO.getIdReduccion05() == null || "NO".equalsIgnoreCase(tramiteVO.getIdReduccion05())) {
					datosCardMatw.setHasForm05(Boolean.FALSE);
				} else {
					datosCardMatw.setHasForm05(Boolean.TRUE);
				}
				if (tramiteVO.getIdNoSujeccion06() == null || "NO".equalsIgnoreCase(tramiteVO.getIdNoSujeccion06())) {
					datosCardMatw.setHasForm06(Boolean.FALSE);
				} else {
					datosCardMatw.setHasForm06(Boolean.TRUE);
				}

				if (tramiteVO.getVehiculo().getTipoVehiculo() != null && "8".equals(tramiteVO.getVehiculo().getTipoVehiculo().substring(0, 1))) {
					if (tramiteVO.getVehiculo().getFichaTecnicaRd750() != null && tramiteVO.getVehiculo().getFichaTecnicaRd750()) {
						datosCardMatw.setItvCardNew(BigDecimal.ONE);
					} else if (tramiteVO.getVehiculo().getFichaTecnicaRd750() != null && tramiteVO.getVehiculo().getFichaTecnicaRd750()) {
						datosCardMatw.setItvCardNew(BigDecimal.ZERO);
					}
				}

				if (tramiteVO.getVehiculo().getTipoVehiculo() != null
						&& ("R".equals(tramiteVO.getVehiculo().getTipoVehiculo().substring(0, 1))
								|| "S".equals(tramiteVO.getVehiculo().getTipoVehiculo().substring(0, 1))
								|| "5".equals(tramiteVO.getVehiculo().getTipoVehiculo().substring(0, 1))
								|| "7".equals(tramiteVO.getVehiculo().getTipoVehiculo().substring(0, 1))
								|| "8".equals(tramiteVO.getVehiculo().getTipoVehiculo().substring(0, 1))
								|| "9".equals(tramiteVO.getVehiculo().getTipoVehiculo().substring(0, 1)))) {
					if (tramiteVO.getVehiculo().getFichaTecnicaRd750() != null && tramiteVO.getVehiculo().getFichaTecnicaRd750()) {
						datosCardMatw.setItvCardNew(BigDecimal.ONE);
					} else if (tramiteVO.getVehiculo().getFichaTecnicaRd750() != null && tramiteVO.getVehiculo().getFichaTecnicaRd750()) {
						datosCardMatw.setItvCardNew(BigDecimal.ZERO);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar Datos Card Matw trámite matriculación: " + numExpediente, e, numExpediente.toString());
		}
		return datosCardMatw;
	}

	private ResultBean validacionesBloqueantes(TramiteTrafMatrDto tramiteDto) throws ParseException {
		ResultBean respuesta = new ResultBean();
		String colegiado = tramiteDto.getNumColegiado();
		
		//Para los trámites con fichas A, se comprueba que el estado previo es válido para continuar con la validación
		if (utiles.esUsuarioMatw(colegiado) && esUnaFichaA(tramiteDto.getVehiculoDto().getTipoTarjetaITV())
				&& (!tramiteDto.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum())
						&& !tramiteDto.getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())
						&& !tramiteDto.getEstado().equals(EstadoTramiteTrafico.Consultado_EITV.getValorEnum())
						&& !tramiteDto.getEstado().equals(EstadoTramiteTrafico.Error_Consulta_EITV.getValorEnum())
						&& !tramiteDto.getEstado().equals(EstadoTramiteTrafico.TramitadoNRE06.getValorEnum()))) {
			respuesta.setError(true);
			respuesta.addMensajeALista("El trámite, si es de matriculación, debe estar iniciado.");
			return respuesta;
		}

		if (tramiteDto.getVehiculoDto() == null) {
			respuesta.setError(true);
			respuesta.addMensajeALista("Debe introducir un vehículo para poder validar el trámite.");
			return respuesta;
		}

		boolean territorialidadValida = validarTerritorialidad(tramiteDto);
		if (!territorialidadValida) {
			String mensajeTerritorialidad = gestorPropiedades.valorPropertie("territorialidad.ctit.exclusiones.mensaje");
			respuesta.setError(true);
			respuesta.addMensajeALista(mensajeTerritorialidad);
			return respuesta;
		}

		List<String> erroresIVTM = new ArrayList<>();
		if (tramiteDto != null && tramiteDto.getNumExpediente() != null) {
			erroresIVTM = servicioIvtmMatriculacion.validarMatriculacion(tramiteDto);
		} else {
			erroresIVTM = new ArrayList<>();
			erroresIVTM.add("Hay errores recuperando datos, y no se puede comprobar la situación del IVTM");
		}
		if (!erroresIVTM.isEmpty()) {
			List<String> listaErrorres = new ArrayList<>();
			for (String error : erroresIVTM) {
				listaErrorres.add(error);
			}
			respuesta.addListaMensajes(listaErrorres);
			respuesta.setError(true);
			respuesta.addMensajeALista("Error de IVTM");
			return respuesta;
		}

		try {
			ResultBean respuestaCaducidadNif = comprobarCaducidadNifs(tramiteDto, true);
			if (respuestaCaducidadNif != null && respuestaCaducidadNif.getError()) {
				respuesta.setError(true);
				respuesta.addListaMensajes(respuestaCaducidadNif.getListaMensajes());
				return respuesta;
			}
			String mensajeValidezFechas = comprobarFormatoFechas(tramiteDto);
			if (mensajeValidezFechas != null && !mensajeValidezFechas.isEmpty()) {
				respuesta.setError(true);
				respuesta.addMensajeALista(mensajeValidezFechas);
				return respuesta;
			}
		} catch (ParseException e) {
			log.error("Al comprobar la fecha de caducidad de un NIF se ha producido el siguiente error: " + e.getMessage(), e);
		}

		boolean validacionFabricante = validarFabricante(tramiteDto);
		if (!validacionFabricante) {
			respuesta.setError(true);
			respuesta.addMensajeALista("Fabricante no verificado");
			return respuesta;
		}

		if (tramiteDto.getCheckJustifFicheroIvtm() != null && tramiteDto.getCheckJustifFicheroIvtm()
				&& !validarSiPagoIVTM(tramiteDto)) {
			respuesta.setError(true);
			respuesta.addMensajeALista(NO_ENCONTRADO_JUSTIFICANTE_PAGO_IVTM);
			return respuesta;
		}
		
	//	String fechaTaco = gestorPropiedades.valorPropertie("fecha.tacografo");
	//	int iguales = 0;
	//	if (tramiteDto.getVehiculoDto().getFechaPrimMatri() != null && tramiteDto.getVehiculoDto().getFechaPrimMatri().getFecha() != null) {
	//		 iguales = utilesFecha.compararFechas(tramiteDto.getVehiculoDto().getFechaPrimMatri(), new Fecha(fechaTaco));
	//	}
		if (utiles.esUsuarioMatw(colegiado) && esUnaFichaA(tramiteDto.getVehiculoDto().getTipoTarjetaITV())
				&& (StringUtils.isBlank(tramiteDto.getVehiculoDto().getEstacionItv()) || "-1".equalsIgnoreCase(tramiteDto.getVehiculoDto().getEstacionItv()))) {
			respuesta.setError(true);
			respuesta.addMensajeALista("Para Matriculaciones con tipo tarjeta ITV A, AT, AR, AL, debe indicar la estación de ITV");
			return respuesta;
		}
		if(StringUtils.isBlank(tramiteDto.getVehiculoDto().getNive())){
			ResultBean resultComprobarDatos = comprobarDatosObligatoriosNuevos(tramiteDto);
			if (resultComprobarDatos.getError()) {
				respuesta.setError(true);
				respuesta.setListaMensajes(resultComprobarDatos.getListaMensajes());
				return respuesta;
			}
		}
	/*	if (!esUnaFichaA(tramiteDto.getVehiculoDto().getTipoTarjetaITV())
				&& tieneFechaDePrimeraMatriculacion(tramiteDto.getVehiculoDto().getFechaPrimMatri())
				&& !tieneRespuestaBloqueadoEnEEFF(tramiteDto.getRespBloqBastidor())) {
			respuesta.setError(true);
			respuesta.addMensajeALista("Para Matriculaciones con tipo tarjeta ITV distinta a A, AT, AR, AL, si no se ha recibido el error:SIMA01014: El bastidor/NIVE aparece bloqueado en EEFF,"
					+ " no se puede enviar fecha de primera matriculación.");
			return respuesta;
		}*/
		/*if (esUnaFichaBoD(tramiteDto.getVehiculoDto().getTipoTarjetaITV()) && tieneRespuestaBloqueadoEnEEFF(tramiteDto.getRespBloqBastidor()) && iguales != 1) {
			respuesta.setError(true);
			respuesta.addMensajeALista("Para Matriculaciones con tipo tarjeta ITV B, BT, BR, BL, D, DT, DR, DL, la fecha de primera matriculación no puede ser distinta a 18/08/2023");
			return respuesta;
		}*/
		/*if (tieneRespuestaBloqueadoEnEEFF(tramiteDto.getRespBloqBastidor())) {
			if (tramiteDto.getVehiculoDto().getFechaPrimMatri() != null && tramiteDto.getVehiculoDto().getFechaPrimMatri().isfechaNula()) {
				respuesta.setError(true);
				respuesta.addMensajeALista("Para el error SIMA01014: El bastidor/NIVE aparece bloqueado en EEFF, la fecha de primera matriculación debe estar informada a fecha 18/08/2023");
				return respuesta;
			} else if (iguales != 1) {
				respuesta.setError(true);
				respuesta.addMensajeALista("Para el error SIMA01014: El bastidor/NIVE aparece bloqueado en EEFF, la fecha de primera matriculación no puede ser distinta a 18/08/2023");
				return respuesta;
			}
		}*/
		return respuesta;
	}

	private ResultBean comprobarDatosObligatoriosNuevos(TramiteTrafMatrDto tramiteDto) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		try {
			if(tramiteDto.getVehiculoDto().getLongitud() == null) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Cuando no hay NIVE el campo Longitud es obligatorio.");
			}
			if(tramiteDto.getVehiculoDto().getAnchura() == null) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Cuando no hay NIVE el campo Anchura es obligatorio.");
			}
			if(tramiteDto.getVehiculoDto().getAltura() == null) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Cuando no hay NIVE el campo Altura es obligatorio.");
			}
			if(tramiteDto.getVehiculoDto().getNumEjes() == null) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Cuando no hay NIVE el campo Num. Ejes es obligatorio.");
			}
		} catch (Exception e) {
			log.error("Error al comprobar datos obligatorios cuando no hay NIVE: " + tramiteDto.getNumExpediente(), e);
		}
		return result;
	}

	public boolean esUnaFichaA(String tipoFicha) {
		if (tipoFicha == null) {
			return false;
		}

		List<String> fichasA = new ArrayList<>();
		fichasA.add(TipoTarjetaITV.A.getValorEnum());
		fichasA.add(TipoTarjetaITV.AT.getValorEnum());
		fichasA.add(TipoTarjetaITV.AR.getValorEnum());
		fichasA.add(TipoTarjetaITV.AL.getValorEnum());

		return fichasA.contains(tipoFicha);
	}

	private boolean tieneFechaDePrimeraMatriculacion(Fecha fecha) {
		return fecha != null;
	}

	private boolean tieneRespuestaBloqueadoEnEEFF(String respuesta) {
		if (respuesta == null) {
			return false;
		}
		return "SIMA01014 - El bastidor/NIVE aparece bloqueado en EEFF. (Recibido de la DGT)".contains(respuesta);
	}

	private boolean esUnaFichaBoD(String tipoFicha) {
		if (tipoFicha == null) {
			return false;
		}

		List<String> fichasByD = new ArrayList<>();
		fichasByD.add(TipoTarjetaITV.B.getValorEnum());
		fichasByD.add(TipoTarjetaITV.BL.getValorEnum());
		fichasByD.add(TipoTarjetaITV.BR.getValorEnum());
		fichasByD.add(TipoTarjetaITV.BT.getValorEnum());
		fichasByD.add(TipoTarjetaITV.D.getValorEnum());
		fichasByD.add(TipoTarjetaITV.DL.getValorEnum());
		fichasByD.add(TipoTarjetaITV.DR.getValorEnum());
		fichasByD.add(TipoTarjetaITV.DT.getValorEnum());

		return fichasByD.contains(tipoFicha);
	}

	private boolean validarSiPagoIVTM(TramiteTrafMatrDto tramiteDto) {
		boolean valido = true;
		try {
			ResultadoMatriculacionBean resultado = obtenerJustificanteIVTM(tramiteDto.getNumExpediente().toString());
			if (resultado.getError()) {
				String numColegiados = gestorPropiedades.valorPropertie("colegiados.sin.pago.justificante.IVTM");
				return (numColegiados != null && numColegiados.contains(tramiteDto.getNumColegiado()));
			}
		} catch (Throwable e) {
			valido = true;
			log.error("Error al validar el pago IVTM del trámite: " + tramiteDto.getNumExpediente(), e, tramiteDto.getNumExpediente().toString());
		}
		return valido;
	}

	private boolean validarTerritorialidad(TramiteTrafMatrDto tramiteDto) {
		boolean valido = true;
		try {
			String activo = gestorPropiedades.valorPropertie("territorialidad.matriculacion.exclusiones.activar");
			if ("S".equals(activo) || "SI".equals(activo)) {
				PersonaDto titular = null;
				DireccionDto direccionTitular = null;
				String provinciaTitular = null;
				String[] arrayExcluidas = gestorPropiedades.valorPropertie("territorialidad.ctit.exclusiones.provincias").split(",");
				if (tramiteDto.getTitular() != null && tramiteDto.getTitular().getPersona() != null) {
					titular = tramiteDto.getTitular().getPersona();
				}
				if (titular != null) {
					direccionTitular = titular.getDireccionDto();
					if (direccionTitular != null && direccionTitular.getIdProvincia() != null) {
						provinciaTitular = servicioDireccion.obtenerNombreProvincia(direccionTitular.getIdProvincia());
					}
				}
				for (String provinciaExcluida : arrayExcluidas) {
					if (provinciaTitular != null && provinciaExcluida.equals(provinciaTitular)) {
						valido = false;
					}
				}
			}
		} catch (Throwable e) {
			valido = true;
			log.error("Error al validar la territorialidad del trámite: " + tramiteDto.getNumExpediente(), e, tramiteDto.getNumExpediente().toString());
		}
		return valido;
	}

	private String comprobarFormatoFechas(TramiteTrafMatrDto tramiteDto) throws ParseException {
		String resultado = "";
		List<TipoInterviniente> listaIntervinientes = new ArrayList<>();
		listaIntervinientes.add(TipoInterviniente.Titular);
		listaIntervinientes.add(TipoInterviniente.ConductorHabitual);
		listaIntervinientes.add(TipoInterviniente.RepresentanteTitular);
		listaIntervinientes.add(TipoInterviniente.Arrendatario);
		PersonaDto persona = null;
		for (TipoInterviniente interviniente : listaIntervinientes) {
			switch (interviniente) {
				case Titular:
					if (tramiteDto != null && tramiteDto.getTitular() != null && tramiteDto.getTitular().getPersona() != null) {
						persona = tramiteDto.getTitular().getPersona();
					}
					break;
				case ConductorHabitual:
					if (tramiteDto != null && tramiteDto.getConductorHabitual() != null && tramiteDto.getConductorHabitual().getPersona() != null) {
						persona = tramiteDto.getConductorHabitual().getPersona();
					}
					break;
				case RepresentanteTitular:
					if (tramiteDto != null && tramiteDto.getRepresentanteTitular() != null && tramiteDto.getRepresentanteTitular().getPersona() != null) {
						persona = tramiteDto.getRepresentanteTitular().getPersona();
					}
					break;
				case Arrendatario:
					if (tramiteDto != null && tramiteDto.getArrendatario() != null && tramiteDto.getArrendatario().getPersona() != null) {
						persona = tramiteDto.getArrendatario().getPersona();
					}
					break;
				default:
					persona = null;
					break;
			}

			String resultadoValidacionFecha = "";
			if (persona != null && persona.getFechaNacimiento() != null && persona.getFechaNacimiento().getDia() != null
					&& persona.getFechaNacimiento().getMes() != null && persona.getFechaNacimiento().getAnio() != null) {
				String dia = persona.getFechaNacimiento().getDia();
				String mes = persona.getFechaNacimiento().getMes();
				String anio = persona.getFechaNacimiento().getAnio();
				int diaNum = 0;
				int mesNum = 0;
				if (dia != null && !dia.isEmpty()) {
					diaNum = Integer.parseInt(dia);
					if (diaNum < 0 || diaNum > 31) {
						resultadoValidacionFecha = FORMATO_DE_FECHA_DE_NACIMIENTO_DEL + interviniente.getNombreEnum() + " no es válida. El día debe estar comprendido entre el 1 y el 31";
					}
				}
				if (mes != null && !mes.isEmpty()) {
					mesNum = Integer.parseInt(mes);
					if (mesNum < 1 || mesNum > 12) {
						resultadoValidacionFecha = FORMATO_DE_FECHA_DE_NACIMIENTO_DEL + interviniente.getNombreEnum() + " no es válida. El mes debe estar comprendido entre el 1 y el 12";
					}
				}
				if (anio != null && !anio.isEmpty() && anio.length() != 4) {
					resultadoValidacionFecha = FORMATO_DE_FECHA_DE_NACIMIENTO_DEL + interviniente.getNombreEnum() + " no es válida. El año debe tener 4 dígitos.";
				}
			}
			if (resultadoValidacionFecha != null && !resultadoValidacionFecha.equals("")) {
				resultado += "\n" + resultadoValidacionFecha;
			}
		}
		return resultado;
	}

	private boolean validarFabricante(TramiteTrafMatrDto tramiteDto) {
		boolean valido = true;
		String codFab;
		try {
			String[] arrayFabricantesInactivos = gestorPropiedades.valorPropertie("Fabricantes.inactivos").split(",");

			if (tramiteDto.getVehiculoDto().getFabricante() != null && !tramiteDto.getVehiculoDto().getFabricante().isEmpty()) {
				FabricanteVO fabricanteVO = servicioFabricante.getFabricante(tramiteDto.getVehiculoDto().getFabricante());

				if (fabricanteVO != null) {
					codFab = fabricanteVO.getCodFabricante().toString();

					for (int i = 0; i < arrayFabricantesInactivos.length; i++)
						if (arrayFabricantesInactivos[i].equals(codFab))
							return false;
				} else {
					return false;
				}
			}
		} catch (Throwable e) {
			log.error("Error al validar el Fabricante del vehiculo (Fabricante no verificado): " + tramiteDto.getNumExpediente(), e);
		}
		return valido;
	}

	private boolean titularModificado(IntervinienteTraficoDto intervinienteTrafico, TramiteTrafMatrDto tramiteDto) {
		boolean modificado = false;
		if (intervinienteTrafico.getCambioDomicilio() != null && !intervinienteTrafico.getCambioDomicilio().equals(tramiteDto.getCambioDomicilio())) {
			modificado = true;
		}
		if (intervinienteTrafico.getNumColegiado() != null && !intervinienteTrafico.getNumColegiado().equals(tramiteDto.getNumColegiado())) {
			modificado = true;
		}
		if (intervinienteTrafico.getNumExpediente() != null && !intervinienteTrafico.getNumExpediente().equals(tramiteDto.getNumExpediente())) {
			modificado = true;
		}
		return modificado;
	}

	//TODO: En esta función únicamente nos interesa saber si algún campo se ha modificado, por lo que en el momento que uno es true debería salir y no seguir comprobando.
	private boolean vehiculoModificado(VehiculoDto vehiculoPantalla, VehiculoDto vehiculo) {
		boolean modificado = false;

		if (vehiculoPantalla.getNive() != null && !vehiculoPantalla.getNive().equals(vehiculo.getNive())) {
			return true;
		}
		if (vehiculoPantalla.getBastidor() != null && !vehiculoPantalla.getBastidor().equals(vehiculo.getBastidor())) {
			return true;
		}
		if (vehiculoPantalla.getImportado() != null && !vehiculoPantalla.getImportado().equals(vehiculo.getImportado())) {
			modificado = true;
		}
		if (vehiculoPantalla.getSubastado() != null && !vehiculoPantalla.getSubastado().equals(vehiculo.getSubastado())) {
			modificado = true;
		}
		if (vehiculoPantalla.getVehiculoAgricola() != null && !vehiculoPantalla.getVehiculoAgricola().equals(vehiculo.getVehiculoAgricola())) {
			modificado = true;
		}
		if (vehiculoPantalla.getVehiculoTransporte() != null && !vehiculoPantalla.getVehiculoTransporte().equals(vehiculo.getVehiculoTransporte())) {
			modificado = true;
		}
		if (vehiculoPantalla.getAnioFabrica() != null && !vehiculoPantalla.getAnioFabrica().equals(vehiculo.getAnioFabrica())) {
			modificado = true;
		}
		if (vehiculoPantalla.getCaracteristicas() != null && !vehiculoPantalla.getCaracteristicas().equals(vehiculo.getCaracteristicas())) {
			modificado = true;
		}
		if (vehiculoPantalla.getCodigoMarca() != null && !vehiculoPantalla.getCodigoMarca().equals(vehiculo.getCodigoMarca())) {
			modificado = true;
		}
		if (vehiculoPantalla.getModelo() != null && !vehiculoPantalla.getModelo().equals(vehiculo.getModelo())) {
			modificado = true;
		}
		if (vehiculoPantalla.getCilindrada() != null && !vehiculoPantalla.getCilindrada().equals(vehiculo.getCilindrada())) {
			modificado = true;
		}
		if (vehiculoPantalla.getClasificacionItv() != null && !vehiculoPantalla.getClasificacionItv().equals(vehiculo.getClasificacionItv())) {
			modificado = true;
		}
		if (vehiculoPantalla.getCo2() != null && !vehiculoPantalla.getCo2().equals(vehiculo.getCo2())) {
			modificado = true;
		}
		if (vehiculoPantalla.getCodigoEco() != null && !vehiculoPantalla.getCodigoEco().equals(vehiculo.getCodigoEco())) {
			modificado = true;
		}
		if (vehiculoPantalla.getCodItv() != null && !vehiculoPantalla.getCodItv().equals(vehiculo.getCodItv())) {
			modificado = true;
		}
		if (vehiculoPantalla.getConceptoItv() != null && !vehiculoPantalla.getConceptoItv().equals(vehiculo.getConceptoItv())) {
			modificado = true;
		}
		if (vehiculoPantalla.getConsumo() != null && !vehiculoPantalla.getConsumo().equals(vehiculo.getConsumo())) {
			modificado = true;
		}
		if (vehiculoPantalla.getDiplomatico() != null && vehiculo.getDiplomatico() != null && !vehiculoPantalla.getDiplomatico().equals(vehiculo.getDiplomatico())) {
			modificado = true;
		}
		if (vehiculoPantalla.getDistanciaEjes() != null && !vehiculoPantalla.getDistanciaEjes().equals(vehiculo.getDistanciaEjes())) {
			modificado = true;
		}
		if (vehiculoPantalla.getEcoInnovacion() != null && !vehiculoPantalla.getEcoInnovacion().equals(vehiculo.getEcoInnovacion())) {
			modificado = true;
		}
		if (vehiculoPantalla.getEstacionItv() != null && !vehiculoPantalla.getEstacionItv().equals(vehiculo.getEstacionItv())) {
			modificado = true;
		}
		if (vehiculoPantalla.getExcesoPeso() != null && vehiculo.getExcesoPeso() != null && !vehiculoPantalla.getExcesoPeso().equals(vehiculo.getExcesoPeso())) {
			modificado = true;
		}
		if (vehiculoPantalla.getFabricante() != null && !vehiculoPantalla.getFabricante().equals(vehiculo.getFabricante())) {
			modificado = true;
		}
		if (vehiculoPantalla.getModelo() != null && !vehiculoPantalla.getModelo().equals(vehiculo.getModelo())) {
			modificado = true;
		}
		if (vehiculoPantalla.getMatricula() != null && vehiculo.getMatricula() != null && !vehiculoPantalla.getMatricula().equals(vehiculo.getMatricula())) {
			modificado = true;
		} else if (vehiculo.getMatricula() == null && vehiculoPantalla.getMatricula() != null && !vehiculoPantalla.getMatricula().equals("")) {
			modificado = true;
		}
		if (vehiculoPantalla.getNivelEmisiones() != null && !vehiculoPantalla.getNivelEmisiones().equals(vehiculo.getNivelEmisiones())) {
			modificado = true;
		}
		if (vehiculoPantalla.getMtmaItv() != null && !vehiculoPantalla.getMtmaItv().equals(vehiculo.getMtmaItv())) {
			modificado = true;
		}
		if (vehiculoPantalla.getNumHomologacion() != null && !vehiculoPantalla.getNumHomologacion().equals(vehiculo.getNumHomologacion())) {
			modificado = true;
		}
		if (vehiculoPantalla.getPesoMma() != null && !vehiculoPantalla.getPesoMma().equals(vehiculo.getPesoMma())) {
			modificado = true;
		}
		if (vehiculoPantalla.getKmUso() != null && !vehiculoPantalla.getKmUso().equals(vehiculo.getKmUso())) {
			modificado = true;
		}
		if (vehiculoPantalla.getLugarPrimeraMatriculacion() != null && !vehiculoPantalla.getLugarPrimeraMatriculacion().equals(vehiculo.getLugarPrimeraMatriculacion())) {
			modificado = true;
		}
		if (vehiculoPantalla.getMatriAyuntamiento() != null && !vehiculoPantalla.getMatriAyuntamiento().equals(vehiculo.getMatriAyuntamiento())) {
			modificado = true;
		}
		if (vehiculoPantalla.getMom() != null && !vehiculoPantalla.getMom().equals(vehiculo.getMom())) {
			modificado = true;
		}
		if (vehiculoPantalla.getNumPlazasPie() != null && !vehiculoPantalla.getNumPlazasPie().equals(vehiculo.getNumPlazasPie())) {
			modificado = true;
		}
		if (vehiculoPantalla.getNumRuedas() != null && !vehiculoPantalla.getNumRuedas().equals(vehiculo.getNumRuedas())) {
			modificado = true;
		}
		if (vehiculoPantalla.getPlazas() != null && !vehiculoPantalla.getPlazas().equals(vehiculo.getPlazas())) {
			modificado = true;
		}
		if (vehiculoPantalla.getPotenciaFiscal() != null && !vehiculoPantalla.getPotenciaFiscal().equals(vehiculo.getPotenciaFiscal())) {
			modificado = true;
		}
		if (vehiculoPantalla.getPotenciaNeta() != null && !vehiculoPantalla.getPotenciaNeta().equals(vehiculo.getPotenciaNeta())) {
			modificado = true;
		}
		if (vehiculoPantalla.getPotenciaPeso() != null && !vehiculoPantalla.getPotenciaPeso().equals(vehiculo.getPotenciaPeso())) {
			modificado = true;
		}
		if (vehiculoPantalla.getProcedencia() != null && !vehiculoPantalla.getProcedencia().equals(vehiculo.getProcedencia())) {
			modificado = true;
		}
		if (vehiculoPantalla.getReduccionEco() != null && !vehiculoPantalla.getReduccionEco().equals(vehiculo.getReduccionEco())) {
			modificado = true;
		}
		if (vehiculoPantalla.getTara() != null && !vehiculoPantalla.getTara().equals(vehiculo.getTara())) {
			modificado = true;
		}
		if (vehiculoPantalla.getTipoIndustria() != null && !vehiculoPantalla.getTipoIndustria().equals(vehiculo.getTipoIndustria())) {
			modificado = true;
		}
		if (vehiculoPantalla.getTipoItv() != null && !vehiculoPantalla.getTipoItv().equals(vehiculo.getTipoItv())) {
			modificado = true;
		}
		if (vehiculoPantalla.getTipoVehiculo() != null && !vehiculoPantalla.getTipoVehiculo().equals(vehiculo.getTipoVehiculo())) {
			modificado = true;
		}
		if (vehiculoPantalla.getVariante() != null && !vehiculoPantalla.getVariante().equals(vehiculo.getVariante())) {
			modificado = true;
		}
		if (vehiculoPantalla.getVehiUsado() != null && !vehiculoPantalla.getVehiUsado().equals(vehiculo.getVehiUsado())) {
			modificado = true;
		}
		if (vehiculoPantalla.getVersion() != null && !vehiculoPantalla.getVersion().equals(vehiculo.getVersion())) {
			modificado = true;
		}
		if (vehiculoPantalla.getViaAnterior() != null && !vehiculoPantalla.getViaAnterior().equals(vehiculo.getViaAnterior())) {
			modificado = true;
		}
		if (vehiculoPantalla.getViaPosterior() != null && !vehiculoPantalla.getViaPosterior().equals(vehiculo.getViaPosterior())) {
			modificado = true;
		}
		if (vehiculoPantalla.getVelocidadMaxima() != null && !vehiculoPantalla.getVelocidadMaxima().equals(vehiculo.getVelocidadMaxima())) {
			modificado = true;
		}

		return modificado;
	}

	@Override
	@Transactional
	public TramiteTrafMatrVO actualizarRespuestaMateEitv(String respuesta, BigDecimal numExpediente) {
		TramiteTrafMatrVO tramite = null;
		try {
			if (respuesta != null && numExpediente != null) {
				tramite = tramiteTraficoMatrDao.getTramiteTrafMatr(numExpediente, Boolean.FALSE, Boolean.FALSE);
				if (tramite != null) {
					tramite.setRespuestaEitv(respuesta);
					tramiteTraficoMatrDao.actualizar(tramite);
				}
			}
		} catch (Exception e) {
			log.error("Error al actualizar la respuesta del tramite: " + numExpediente, e, numExpediente.toString());
		}
		return tramite;
	}

	private String validarFechasMatriculacion(TramiteTrafMatrDto tramiteTrafMatrDto) {
		String resultadoValidacion = "OK";
		if (tramiteTrafMatrDto.getFechaPresentacion() != null && !utilesFecha.validarFormatoFecha(tramiteTrafMatrDto.getFechaPresentacion())) {
			resultadoValidacion = "La fecha de presentaci\363n del tr\341mite que ha introducido no es válida";
		} else if (tramiteTrafMatrDto.getTitular() != null && tramiteTrafMatrDto.getTitular().getPersona() != null
				&& tramiteTrafMatrDto.getTitular().getPersona().getFechaCaducidadNif() != null && !utilesFecha
						.validarFormatoFecha(tramiteTrafMatrDto.getTitular().getPersona().getFechaCaducidadNif())) {
			resultadoValidacion = "La fecha de caducidad del NIF que ha introducido para el titular no es valida";
		} else if (tramiteTrafMatrDto.getRepresentanteTitular() != null
				&& tramiteTrafMatrDto.getRepresentanteTitular().getPersona() != null
				&& tramiteTrafMatrDto.getRepresentanteTitular().getPersona().getFechaCaducidadNif() != null
				&& !utilesFecha.validarFormatoFecha(
						tramiteTrafMatrDto.getRepresentanteTitular().getPersona().getFechaCaducidadNif())) {
			resultadoValidacion = "La fecha de caducidad del NIF que ha introducido para el representante del titular no es válida";
		} else if (tramiteTrafMatrDto.getVehiculoDto() != null
				&& tramiteTrafMatrDto.getVehiculoDto().getFechaItv() != null
				&& !utilesFecha.validarFormatoFecha(tramiteTrafMatrDto.getVehiculoDto().getFechaItv())) {
			resultadoValidacion = "La fecha de caducidad ITV del vehículo no es válida";
		} else if (tramiteTrafMatrDto.getFechaIedtm() != null && !utilesFecha.validarFormatoFecha(tramiteTrafMatrDto.getFechaIedtm())) {
			resultadoValidacion = "La fecha de inicio de la exención no es válida";
		} else if (tramiteTrafMatrDto.getFechaPago576() != null && !utilesFecha.validarFormatoFecha(tramiteTrafMatrDto.getFechaPago576())) {
			resultadoValidacion = "La fecha de pago no es válida";
		}
		return resultadoValidacion;
	}

	private ResultBean guardarLiberacion(TramiteTrafMatrDto tramiteDto, BigDecimal numExpediente, boolean permisoLiberacion, boolean permisoAdmin) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		if (permisoLiberacion || permisoAdmin) {
			result = servicioEEFF.guardarDatosLiberacion(tramiteDto, numExpediente);
		}
		return result;
	}

	private ResultBean guardarIvtm(TramiteTrafMatrDto tramiteDto, IvtmMatriculacionDto ivtmMatriculacionDto) throws ParseException {
		ResultBean respuesta = new ResultBean();
		if (ivtmMatriculacionDto != null && !servicioIvtmMatriculacion.puedeGenerarAutoliquidacion(tramiteDto.getNumExpediente()).getError()) {
			ivtmMatriculacionDto.setNumExpediente(tramiteDto.getNumExpediente());
			// 19_01_2017 No está guardando Bastidor en la tabla
			ivtmMatriculacionDto.setBastidor(tramiteDto.getVehiculoDto().getBastidor());
			respuesta = servicioIvtmMatriculacion.guardarIvtm(ivtmMatriculacionDto);
		}
		return respuesta;
	}

	private ResultBean comprobarCaducidadNifs(TramiteTrafMatrDto tramiteDto, boolean validacion) throws ParseException {
		ResultBean result = new ResultBean(false);
		Fecha fechaActual = utilesFecha.getFechaActual();
		Date fechaAdvertencia = utilesFecha.getDate((utilesFecha.sumaDias(fechaActual, "-30")));
		List<TipoInterviniente> listaIntervinientes = new ArrayList<>();
		listaIntervinientes.add(TipoInterviniente.Titular);
		listaIntervinientes.add(TipoInterviniente.ConductorHabitual);
		listaIntervinientes.add(TipoInterviniente.RepresentanteTitular);
		PersonaDto persona = null;

		for (TipoInterviniente interviniente : listaIntervinientes) {
			persona = null;
			switch (interviniente) {
				case Titular:
					if (tramiteDto != null && tramiteDto.getTitular() != null && tramiteDto.getTitular().getPersona() != null) {
						persona = tramiteDto.getTitular().getPersona();
					}
					break;
				case ConductorHabitual:
					if (tramiteDto != null && tramiteDto.getConductorHabitual() != null && tramiteDto.getConductorHabitual().getPersona() != null) {
						persona = tramiteDto.getConductorHabitual().getPersona();
					}
					break;
				case RepresentanteTitular:
					if (tramiteDto != null && tramiteDto.getRepresentanteTitular() != null && tramiteDto.getRepresentanteTitular().getPersona() != null) {
						persona = tramiteDto.getRepresentanteTitular().getPersona();
					}
					break;
				default:
					break;
			}

			String resultadoCaducidadNIF = "";
			if (persona != null && persona.getFechaCaducidadNif() != null && persona.getFechaCaducidadNif().getDate() != null) {
				if (!validacion && persona.getFechaCaducidadNif().getDate().after(fechaActual.getDate()) && persona.getFechaCaducidadNif().getDate().before(fechaAdvertencia)) {
					resultadoCaducidadNIF = "NIF del " + interviniente.getNombreEnum() + " próximo a caducar";
					if (persona.getCodigoMandato() != null) {
						resultadoCaducidadNIF = "Recuerde que esta persona tiene asociado el código de mandato: " + persona.getCodigoMandato();
					}
				} else if (persona.getFechaCaducidadNif().getDate().before(fechaActual.getDate())) {
					resultadoCaducidadNIF = "NIF del " + interviniente.getNombreEnum() + " caducado. ";
					result.setError(true);
				}
			}

			if (persona != null && result.getError() && persona.isOtroDocumentoIdentidad()) {
				resultadoCaducidadNIF += "Pero existe un documento alternativo";
				result.setError(false);
			}

			if (!resultadoCaducidadNIF.isEmpty()) {
				result.addMensajeALista(resultadoCaducidadNIF);
			}

		}
		return result;
	}

	@Override
	@Transactional
	public void actualizarPegatina(BigDecimal numExpediente) throws OegamExcepcion {
		TramiteTrafMatrVO tramiteMatriculacion = tramiteTraficoMatrDao.getTramiteTrafMatr(numExpediente, Boolean.FALSE, Boolean.FALSE);
		tramiteMatriculacion.setPegatina("SI");
		tramiteTraficoMatrDao.guardarOActualizar(tramiteMatriculacion);
	}

	@Override
	@Transactional
	public void actualizarRespuestaMatriculacion(BigDecimal numExpediente, String respuesta) {
		TramiteTrafMatrVO tramiteMatriculacion = tramiteTraficoMatrDao.getTramiteTrafMatr(numExpediente, Boolean.FALSE, Boolean.FALSE);
		tramiteMatriculacion.setRespuesta(respuesta);
		tramiteTraficoMatrDao.guardarOActualizar(tramiteMatriculacion);
	}

	@Override
	@Transactional
	public void actualizarRespuesta576Matriculacion(BigDecimal numExpediente, String respuesta) {
		TramiteTrafMatrVO tramiteMatriculacion = tramiteTraficoMatrDao.getTramiteTrafMatr(numExpediente, Boolean.FALSE, Boolean.FALSE);
		tramiteMatriculacion.setRespuesta576(respuesta);
		tramiteTraficoMatrDao.guardarOActualizar(tramiteMatriculacion);
	}

	@Override
	@Transactional
	public ResultBean liberarTramiteMatriculacion(TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			resultado = validarDatosLiberacion(tramiteTrafMatrDto, Boolean.TRUE, Boolean.FALSE);
			if (!resultado.getError()) {
				resultado = servicioEEFF.liberar(tramiteTrafMatrDto.getLiberacionEEFF(), tramiteTrafMatrDto.getIdContrato(), idUsuario);
				if (!resultado.getError()) {
					TramiteTrafMatrVO tramiteTrafMatrVO = tramiteTraficoMatrDao.getTramiteTrafMatr(tramiteTrafMatrDto.getNumExpediente(), Boolean.FALSE, Boolean.FALSE);
					tramiteTrafMatrVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Liberar.getValorEnum()));
					tramiteTraficoMatrDao.actualizar(tramiteTrafMatrVO);
					guardarEvolucionTramite(tramiteTrafMatrDto, EstadoTramiteTrafico.Pendiente_Liberar.getValorEnum(), idUsuario);
					resultado.setMensaje("La solicitud de liberación se ha encolado. Espere la respuesta en las notificaciones.");
				}
			}
		} catch (Exception e) {
			if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getNumExpediente() != null) {
				log.error("Ha sucedido un error a la hora de realizar la liberación, error: ", e, tramiteTrafMatrDto.getNumExpediente().toString());
			} else {
				log.error("Ha sucedido un error a la hora de realizar la liberación, error: ", e, "Sin num. expediente");
			}
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la liberación.");
		}
		if (resultado.getError()) {
			String nombreXml = (String) resultado.getAttachment(ServicioEEFFNuevo.NOMBRE_XML);
			if (nombreXml != null && !nombreXml.isEmpty()) {
				try {
					gestorDocumentos.borraFicheroSiExisteConExtension(ConstantesGestorFicheros.EEFF,
							ConstantesGestorFicheros.EEFFLIBERACION,
							Utilidades.transformExpedienteFecha(tramiteTrafMatrDto.getNumExpediente()), nombreXml,
							ConstantesGestorFicheros.EXTENSION_XML);
				} catch (OegamExcepcion e) {
					log.error("Ha sucedido un error a la hora de borrar el XML de liberacion, error: ", e, tramiteTrafMatrDto.getNumExpediente().toString());
				}
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean validarDatosLiberacion(TramiteTrafMatrDto tramiteTrafMatrDto, boolean permisoLiberacion, Boolean esTramitar) {
		ResultBean resultado = new ResultBean(false);
		if (permisoLiberacion) {
			if (esTramitar) {
				if (tramiteTrafMatrDto.getLiberacionEEFF() == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("- No existen datos de la liberación para el trámite.");
				} else if (!tramiteTrafMatrDto.getLiberacionEEFF().getExento() && !tramiteTrafMatrDto.getLiberacionEEFF().getRealizado()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("- El vehículo no se ha liberado, y no se puede matricular.");
				}
			} else {
				if (tramiteTrafMatrDto == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("- No existen datos del trámite para ese expediente.");
				} else if (!EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTrafMatrDto.getEstado()) &&
						!EstadoTramiteTrafico.Autorizado.getValorEnum().equals(tramiteTrafMatrDto.getEstado())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("- Para poder liberar un vehículo, el trámite debe estar en estado Validado Telemáticamente.");
				} else if (tramiteTrafMatrDto.getVehiculoDto() == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("- El trámite no tiene asociados datos del vehículo.");
				} else if (tramiteTrafMatrDto.getTitular() == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("- El trámite no tiene titular.");
				} else if (tramiteTrafMatrDto.getTitular().getPersona() == null
						|| tramiteTrafMatrDto.getTitular().getPersona().getNif() == null
						|| tramiteTrafMatrDto.getTitular().getPersona().getNif().isEmpty()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("- El trámite no tiene asociados datos del titular.");
				} else if (tramiteTrafMatrDto.getTitular().getDireccion() == null || tramiteTrafMatrDto.getTitular().getDireccion().getIdDireccion() == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("- El trámite no tiene asociados datos de la dirección del titular.");
				}
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean crearSolcitudMatriculacionConCambioEstado(String numExpediente, BigDecimal idUsuario, BigDecimal idContrato, EstadoTramiteTrafico estadoNuevo, EstadoTramiteTrafico estadoAnt) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			resultado = servicioTramiteTrafico.cambiarEstadoConEvolucion(new BigDecimal(numExpediente), estadoAnt, estadoNuevo, Boolean.FALSE, null, idUsuario);
			if (!resultado.getError()) {
				try {
					resultado = servicioCola.crearSolicitud(ProcesosEnum.MATW_SEGA.getNombreEnum(), ProcesosEnum.MATW.getNombreEnum() + numExpediente, gestorPropiedades.valorPropertie(
							NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.Matriculacion.getValorEnum(), numExpediente, idUsuario, null, idContrato);
				} catch (OegamExcepcion e) {
					log.error("Ha sucedido un error a la hora de encolar la peticion de matriculacion para el proceso de MATW_SEGA para el tramite: " + numExpediente + " , error: ", e, numExpediente);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de encolar la petición de matriculación para el proceso de MATW_SEGA para el trámite: " + numExpediente);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de crear la solicitud para la cola de matriculacion, error: ", e, numExpediente);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de crear la solicitud para la cola de matriculación.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} else {
			resultado.setMensaje("Solicitud pendiente de respuesta de DGT.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean actualizarEstadoFinalizadoConError(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			TramiteTrafMatrVO tramite = this.getTramiteMatriculacionVO(numExpediente, false, false);
			if (tramite != null) {
				tramite.setEstado(new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()));
				tramiteTraficoMatrDao.actualizar(tramite);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No hay ningún tramite con el numero de expediente " + numExpediente.toString());
			}
		} catch (Exception e) {
			log.error("Error al actualizar el estado del tramite a FINALIZADO CON ERROR por error servicio: " + numExpediente.toString(), e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al actualizar el estado del tramite a FINALIZADO CON ERROR por error servicio: " + numExpediente.toString());

		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean generarDistintivo(List<TramiteTrafMatrVO> listaTramites, BigDecimal idUsuario, Long idDocPermDstvEitv, Date fecha, Long idContrato,
			TipoDistintivo tipoDistintivo, String docId) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTrafMatrVO tramiteTrafMatrBBDD : listaTramites) {
					tramiteTrafMatrBBDD.setDocDistintivo(idDocPermDstvEitv);
					tramiteTrafMatrBBDD.setEstadoImpDstv(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
					tramiteTraficoMatrDao.actualizar(tramiteTrafMatrBBDD);
				}
				resultado = servicioImpresionMatriculacion.generarInformeDocImpresoGestoria(listaTramites, TipoDocumentoImprimirEnum.DISTINTIVO, tipoDistintivo, docId, Boolean.FALSE);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido recuperar el trámite.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el distintivo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el distintivo.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesContratoDistintivos(Long idContrato) {
		try {
			return tramiteTraficoMatrDao.getListaTramitesContratoDistintivos(idContrato);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de tramites con distintivos pendientes de generar para el idContrato: " + idContrato + ", error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean indicarDocIdTramites(List<BigDecimal> listaTramites, Long idDocPermDstvEitv, BigDecimal idUsuario, Date fecha, String docId, String ipConexion) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			for (BigDecimal numExpediente : listaTramites) {
				TramiteTrafMatrVO tramiteTrafMatrVO = getTramiteMatriculacionVO(numExpediente, Boolean.FALSE, Boolean.TRUE);
				tramiteTrafMatrVO.setDocDistintivo(idDocPermDstvEitv);
				String estadoAnt = tramiteTrafMatrVO.getEstadoImpDstv();
				tramiteTrafMatrVO.setEstadoImpDstv(EstadoPermisoDistintivoItv.ORDENADO_DOC.getValorEnum());
				tramiteTraficoMatrDao.actualizar(tramiteTrafMatrVO);
				servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(), idUsuario,
						TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.MODIFICACION, fecha, estadoAnt,
						EstadoPermisoDistintivoItv.ORDENADO_DOC.getValorEnum(), docId, ipConexion);
			}
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de indicar el doId a los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de indicar el doId a los trámites.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public void actualizarEstadoImpresionDistintivo(BigDecimal numExpediente, String estadoAnt, String estadoNuevo, BigDecimal idUsuario, Date fecha, String docId, String ipConexion) {
		TramiteTrafMatrVO trafMatrVO = getTramiteMatriculacionVO(numExpediente, Boolean.FALSE, Boolean.TRUE);
		if (trafMatrVO != null) {
			trafMatrVO.setEstadoImpDstv(estadoNuevo);
			tramiteTraficoMatrDao.actualizar(trafMatrVO);
			servicioEvolucionPrmDstvFicha.guardarEvolucion(numExpediente, idUsuario,
					TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.MODIFICACION, fecha, estadoAnt,
					estadoNuevo, docId, ipConexion);
		}
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarEstadoImpresionDocDistintivos(Long idDocDstv,
			EstadoPermisoDistintivoItv estadoNuevo, BigDecimal idUsuario, String docId, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTrafMatrVO> listaTramites = tramiteTraficoMatrDao.getListaTramitesPorDocId(idDocDstv);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTrafMatrVO tramiteTrafMatrVO : listaTramites) {
					String estadoAnt = tramiteTrafMatrVO.getEstadoImpDstv();
					tramiteTrafMatrVO.setEstadoImpDstv(estadoNuevo.getValorEnum());
					tramiteTraficoMatrDao.actualizar(tramiteTrafMatrVO);
					servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(), idUsuario,
							TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.IMPRESION, new Date(),
							estadoAnt, estadoNuevo.getValorEnum(), docId, ipConexion);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los estados de los trámites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(ERROR_AL_ACTUALIZAR_ESTADOS_TRAMITES);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesMatriculacion(BigDecimal[] listaNumsExpedientes, Boolean tramiteCompleto) {
		try {
			if (listaNumsExpedientes != null) {
				List<TramiteTrafMatrVO> listaTramitesBBDD = tramiteTraficoMatrDao.getListaTramitesPorExpediente(listaNumsExpedientes, tramiteCompleto);
				if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
					return listaTramitesBBDD;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de traer la lista de expedientes, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public ContratoDto getContratoTramite(String numExpediente) {
		try {
			if (numExpediente != null && !numExpediente.isEmpty()) {
				TramiteTrafMatrVO trafMatrVO = tramiteTraficoMatrDao.getTramiteTrafMatr(new BigDecimal(numExpediente), Boolean.TRUE, Boolean.FALSE);
				if (trafMatrVO != null && trafMatrVO.getContrato() != null && trafMatrVO.getContrato().getIdContrato() != null) {
					return conversor.transform(trafMatrVO.getContrato(), ContratoDto.class);
				} else {
					log.error("No existen datos para el expediente: " + numExpediente);
				}
			} else {
				log.error("No se puede obtener el contrato del expediente porque este es nulo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el contrato del expediente, error: ", e, numExpediente);
		}
		return null;
	}

	@Override
	public ResultadoMatriculacionBean obtenerFicheroPresentacion576(String numExpediente) {
		ResultadoMatriculacionBean resultado = new ResultadoMatriculacionBean(Boolean.FALSE);
		try {
			String nombreFichero = numExpediente + "_576_ENV";
			FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.TIPO576ENVTXT,
					null, Utilidades.transformExpedienteFecha(numExpediente), nombreFichero,
					ConstantesGestorFicheros.EXTENSION_TXT);
			if (fichero != null && fichero.getFile() != null) {
				resultado.setFichero(fichero.getFile());
				resultado.setNombreFichero(nombreFichero + ConstantesGestorFicheros.EXTENSION_TXT);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha encontrado ningún fichero de presentacion del 576 para el expediente: " + numExpediente);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el fichero de presentacion del 576 para el expediente: " + numExpediente + ", error: ", e, numExpediente);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el fichero de presentación del 576 para el expediente: " + numExpediente);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean actualizarTramiteMatwDstv(TramiteTrafMatrVO tramiteTrafMatrVO,
			Boolean tieneDistintivo, String tipoDistintivo, BigDecimal idUsuario, String ipConexion) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (tramiteTrafMatrVO != null) {
				if (tramiteTrafMatrVO.getDistintivo() == null || "N".equals(tramiteTrafMatrVO.getDistintivo())) {
					tramiteTrafMatrVO.setDistintivo(tieneDistintivo ? "S" : "N");
				}
				String estadoAnt = tramiteTrafMatrVO.getEstadoPetPermDstv();
				String estadoNuevo = null;
				if (tieneDistintivo) {
					tramiteTrafMatrVO.setEstadoPetPermDstv(EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum());
					estadoNuevo = EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum();
				} else {
					tramiteTrafMatrVO.setEstadoPetPermDstv(EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum());
					estadoNuevo = EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum();
				}
				tramiteTrafMatrVO.setTipoDistintivo(tipoDistintivo);
				tramiteTraficoMatrDao.guardarOActualizar(tramiteTrafMatrVO);
				servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(), idUsuario,
						TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.DOC_RECIBIDA, new Date(), estadoAnt,
						estadoNuevo, null, ipConexion);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe datos del trámite para poder actualizar.");
			}
		} catch (Throwable e) {
			log.error(
					"Ha sucedido un error a la hora de actualizar la el tramite de matriculacion con los datos de permiso, distintivo y eITV, error: ",
					e, tramiteTrafMatrVO.getNumExpediente().toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar el trámite de matriculación con los datos de permiso, distintivo y eITV.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContratoDto> getListaIdsContratosConDistintivos() {
		try {
			List<Long> listaIdsContratos = tramiteTraficoMatrDao.getListaIdsContratosConDistintivos();
			if (listaIdsContratos != null && !listaIdsContratos.isEmpty()) {
				List<Long> listaContratosDupDstv = servicioDistintivoVehNoMat.getListaIdsContratosConDistintivosDuplicados();
				Boolean existeContrato = Boolean.FALSE;
				for (Long idContrato : listaContratosDupDstv) {
					existeContrato = Boolean.FALSE;
					for (Long idContratoTram : listaIdsContratos) {
						if (idContrato.equals(idContratoTram)) {
							existeContrato = Boolean.TRUE;
							break;
						}
					}
					if (!existeContrato) {
						listaIdsContratos.add(idContrato);
					}
				}
				return servicioContrato.getListaContratosPorId(listaIdsContratos);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un erro a la hora de obtener la lista con los contratos, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesPendientesImprimirDstvConFechaAnterior(Date fechaPresentacion) {
		return tramiteTraficoMatrDao.getDistintivosPendientesImpresionSinSolicitar(fechaPresentacion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContratoDto> getListaIdsContratosSinDstvFinalizados(Date fechaPresentacion) {
		try {
			List<Long> listaIdsContratosBBDD = tramiteTraficoMatrDao.getListaIdsContratosSinDstvFinalizados(fechaPresentacion);
			if (listaIdsContratosBBDD != null && !listaIdsContratosBBDD.isEmpty()) {
				return servicioContrato.getListaContratosPorId(listaIdsContratosBBDD);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un erro a la hora de obtener la lista con los contratos, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoPermisoDistintivoItvBean tieneTramitesFinalizadosTelematicamentePorContrato(ContratoDto contrato, Date fecha, String tipoTramiteSolicitud) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Integer numTramitesST = tramiteTraficoMatrDao.comprobarTramitesFinalizadosTelematicamente(contrato.getIdContrato().longValue(), fecha, tipoTramiteSolicitud);
			if (numTramitesST > 0) {
				resultado.setTieneTramitesST(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si el contrato: " + contrato.getColegiadoDto().getNumColegiado() + " - " + contrato.getVia()
					+ " tiene tramites de Matw finalizados telematicamente para IMPR, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar si el contrato: " + contrato.getColegiadoDto().getNumColegiado() + " - " + contrato.getVia()
					+ " tiene tramites de Matw finalizados telematicamente para IMPR.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoPermisoDistintivoItvBean listaTramitesFinalizadosTelmPorContrato(ContratoDto contrato, Date fecha, String tipoSolicitud, Boolean esMoto) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTrafMatrVO> listaTramitesBBDD = tramiteTraficoMatrDao.listaTramitesFinalizadosTelemPorContrato(contrato.getIdContrato().longValue(), fecha, tipoSolicitud, esMoto);
			List<TramiteTrafMatrVO> listaTramitesNormal = new ArrayList<>();
			List<TramiteTrafMatrVO> listaTramitesA = new ArrayList<>();
			if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
				for (TramiteTrafMatrVO tramiteTrafMatrVO : listaTramitesBBDD) {
					if (esUnaFichaA(tramiteTrafMatrVO.getVehiculo().getIdTipoTarjetaItv())) {
						listaTramitesA.add(tramiteTrafMatrVO);
						resultado.setListaTramitesA(listaTramitesA);
					} else {
						listaTramitesNormal.add(tramiteTrafMatrVO);
						resultado.setListaTramitesNormal(listaTramitesNormal);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No tiene tramites finalizados telematicamente");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si el contrato: " + contrato.getColegiadoDto().getNumColegiado() + " - " + contrato.getVia()
					+ " tiene tramites de Matw finalizados telematicamente para IMPR, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar si el contrato: " + contrato.getColegiadoDto().getNumColegiado() + " - " + contrato.getVia()
					+ " tiene tramites de Matw finalizados telematicamente para IMPR.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoPermisoDistintivoItvBean tieneTramitesValidosIMPRPorContrato(ContratoDto contratoDto, Date fecha, String tipoTramiteSolicitud) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<EmpresaTelematicaDto> listaEmpresasSTContrato = servicioEmpresaTelematica.getListaEmpresasSTContrato(contratoDto.getIdContrato().longValue());
			if (listaEmpresasSTContrato != null && !listaEmpresasSTContrato.isEmpty()) {
				for (EmpresaTelematicaDto empresaTelematicaDto : listaEmpresasSTContrato) {
					Integer numTramitesEmpresaST = servicioIntervinienteTrafico.comprobarTramitesEmpresaST(
							contratoDto.getIdContrato().longValue(), fecha, empresaTelematicaDto.getCifEmpresa(),
							empresaTelematicaDto.getCodigoPostal(), empresaTelematicaDto.getMunicipio(),
							empresaTelematicaDto.getProvincia(), tipoTramiteSolicitud);
					if (numTramitesEmpresaST > 0) {
						resultado.setTieneTramitesST(Boolean.TRUE);
						break;
					}
				}
			}
			if (!resultado.getError() && !resultado.getTieneTramitesST()) {
				Integer numTramitesST = tramiteTraficoMatrDao.comprobarTramitesST(contratoDto.getIdContrato().longValue(), fecha, tipoTramiteSolicitud);
				if (numTramitesST > 0) {
					resultado.setTieneTramitesST(Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si el contrato: " + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia()
					+ " tiene tramites de Matw supertelematicos para IMPR, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar si el contrato: " + contratoDto.getColegiadoDto().getNumColegiado() + " - " + contratoDto.getVia()
					+ " tiene tramites de Matw supertelematicos para IMPR.");
		}
		return resultado;
	}

	@Override
	public ResultadoMatriculacionBean obtenerJustificanteIVTM(String numExpediente) {
		ResultadoMatriculacionBean salida = new ResultadoMatriculacionBean(Boolean.FALSE);
		FileResultBean resultado = null;
		try {
			resultado = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE,
					ConstantesGestorFicheros.SUBTIPO_JUSTIFICANTE_IVTM,
					Utilidades.transformExpedienteFecha(numExpediente),
					ConstantesGestorFicheros.NOMBRE_JUSTIFICANTE_IVTM + numExpediente,
					ConstantesGestorFicheros.EXTENSION_PDF);
			if (resultado != null && resultado.getFile() != null) {
				salida.setFichero(resultado.getFile());
			} else {
				salida.setError(Boolean.TRUE);
				salida.setMensaje(NO_ENCONTRADO_JUSTIFICANTE_PAGO_IVTM);
			}
		} catch (Exception | OegamExcepcion e) {
			salida.setError(Boolean.TRUE);
			salida.setMensaje(ERROR_RECUPERAR_JUSTIFICANTE_IVTM + e);
		}
		return salida;
	}

	@Override
	@Transactional
	public ResultadoMatriculacionBean subirJustificanteIVTM(File fichero, String numExpediente) {
		ResultadoMatriculacionBean salida = new ResultadoMatriculacionBean(Boolean.FALSE);
		File resultado = null;
		try {
			gestorDocumentos.borraFicheroSiExisteConExtension(ConstantesGestorFicheros.MATE,
					ConstantesGestorFicheros.SUBTIPO_JUSTIFICANTE_IVTM, null,
					ConstantesGestorFicheros.NOMBRE_JUSTIFICANTE_IVTM + numExpediente,
					ConstantesGestorFicheros.EXTENSION_PDF);
			FicheroBean fichero2 = new FicheroBean(ConstantesGestorFicheros.MATE,
					Utilidades.transformExpedienteFecha(numExpediente),
					ConstantesGestorFicheros.SUBTIPO_JUSTIFICANTE_IVTM,
					ConstantesGestorFicheros.NOMBRE_JUSTIFICANTE_IVTM + numExpediente,
					ConstantesGestorFicheros.EXTENSION_PDF, fichero, null, null, null, null, null);
			resultado = gestorDocumentos.guardarFichero(fichero2);
			if (resultado != null) {
				TramiteTrafMatrVO tramite = getTramiteMatriculacionVO(new BigDecimal(numExpediente), true, true);
				if (tramite != null) {
					tramite.setCheckJustifFicheroIvtm("S");
					tramite.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
					guardarOActualizar(tramite);
				}
				salida.setFichero(resultado);
			} else {
				salida.setError(Boolean.TRUE);
				salida.setMensaje(NO_ENCONTRADO_JUSTIFICANTE_PAGO_IVTM);
			}

		} catch (Exception | OegamExcepcion e) {
			salida.setError(Boolean.TRUE);
			salida.setMensaje(ERROR_RECUPERAR_JUSTIFICANTE_IVTM + e);
		}
		return salida;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoPermisoDistintivoItvBean comprobarSuperTelematicoMate(List<IntervinienteTraficoVO> listaIntervinientes, BigDecimal numExpediente, Long idContrato) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		IntervinienteTraficoVO titular = null;
		for (IntervinienteTraficoVO intervinienteTraficoVO : listaIntervinientes) {
			if (TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getTipoIntervinienteVO().getTipoInterviniente())) {
				titular = intervinienteTraficoVO;
				break;
			}
		}
		if (titular != null) {
			Boolean esEmpresaTelem = servicioEmpresaTelematica.esEmpresaTelematica(null,
					titular.getPersona().getId().getNif(), titular.getDireccion().getCodPostalCorreos(),
					titular.getId().getNumColegiado(), idContrato, titular.getDireccion().getIdMunicipio(),
					titular.getDireccion().getIdProvincia());
			if (!esEmpresaTelem) {
				TramiteTrafMatrVO trafMatrVO = getTramiteMatriculacionVO(numExpediente, Boolean.FALSE, Boolean.FALSE);
				if (trafMatrVO != null) {
					tramiteTraficoMatrDao.evict(trafMatrVO);
					if (!"S".equals(trafMatrVO.getCheckJustifFicheroIvtm())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se puede solicitar los documentos para el expediente:" + numExpediente + " porque el trámite no es super telemático.");
					}
				} else {
					log.error("No existen datos en BBDD para el expediente: " + numExpediente + " para comprobar si es superTelematico.");
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado datos del titular para el expediente: " + numExpediente);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean esSupertelematico(BigDecimal numExpediente, TramiteTrafMatrVO tramiteTrafMatrVO, Boolean esListadoBastidores) {
		if (tramiteTrafMatrVO == null) {
			tramiteTrafMatrVO = getTramiteMatriculacionVO(numExpediente, false, true);
		}
		String deshabilitarSuperTelematico = gestorPropiedades.valorPropertie("deshabilitar.super.telematico");
		if ("NO".equals(deshabilitarSuperTelematico) &&
				!new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()).equals(tramiteTrafMatrVO.getEstado())) {
			if ("S".equals(tramiteTrafMatrVO.getCheckJustifFicheroIvtm())) {
				return true;
			} else {
				if (tramiteTrafMatrVO.getVehiculo() != null && (tramiteTrafMatrVO.getVehiculo().getNive() == null
						|| tramiteTrafMatrVO.getVehiculo().getNive().isEmpty()) && esListadoBastidores) {
					return false;
				}
				if ("SI".equals(gestorPropiedades.valorPropertie("impresion.todos.impr.Matw.jefaturas"))) {
					String sJefaturasAuto = gestorPropiedades.valorPropertie("jefauras.imprimir.todos.impr.matr");
					if (sJefaturasAuto != null && !sJefaturasAuto.isEmpty()) {
						String[] jefaturasAuto = sJefaturasAuto.split(",");
						for (String jefaturoProv : jefaturasAuto) {
							if (jefaturoProv.equals(tramiteTrafMatrVO.getContrato().getJefaturaTrafico().getJefaturaProvincial())) {
								return true;
							}
						}
					}
				}
				IntervinienteTraficoVO titular = null;
				for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteTrafMatrVO.getIntervinienteTraficosAsList()) {
					if (TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getTipoIntervinienteVO().getTipoInterviniente())) {
						titular = intervinienteTraficoVO;
						break;
					}
				}
				Boolean esEmpresaTelem = servicioEmpresaTelematica.esEmpresaTelematica(null,
						titular.getPersona().getId().getNif(), titular.getDireccion().getCodPostalCorreos(),
						titular.getId().getNumColegiado(), tramiteTrafMatrVO.getContrato().getIdContrato(),
						titular.getDireccion().getIdMunicipio(), titular.getDireccion().getIdProvincia());
				if (esEmpresaTelem) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesDistintivosPorDocId(Long idDocPermDistItv) {
		try {
			return tramiteTraficoMatrDao.getListaTramitesPorDocId(idDocPermDistItv);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites de los distintivos, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountNumTramitesDstv(Long idDocPermDistItv) {
		try {
			return tramiteTraficoMatrDao.getCountNumTramitesDstv(idDocPermDistItv);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el numero de tramites de distintivos, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarEstadoImpresionDistintivos(Set<TramiteTrafMatrVO> listaTramites, EstadoPermisoDistintivoItv estadoNuevo) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTrafMatrVO tramiteTrafMatrVO : listaTramites) {
					tramiteTrafMatrVO.setEstadoImpDstv(estadoNuevo.getValorEnum());
					tramiteTraficoMatrDao.actualizar(tramiteTrafMatrVO);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de los trámites para actualizar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los estados de los trámites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(ERROR_AL_ACTUALIZAR_ESTADOS_TRAMITES);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean actualizarTramitesAnuladosDistintivos(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo, BigDecimal idUsuario, String docId,
			String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			List<TramiteTrafMatrVO> listaTramites = tramiteTraficoMatrDao.getListaTramitesPorDocId(idDocPermDistItv);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTrafMatrVO tramiteTraficoMatrVO : listaTramites) {
					String estadoAnt = tramiteTraficoMatrVO.getEstadoImpDstv();
					tramiteTraficoMatrVO.setEstadoImpDstv(estadoNuevo.getValorEnum());
					tramiteTraficoMatrVO.setDocDistintivo(null);
					tramiteTraficoMatrDao.actualizar(tramiteTraficoMatrVO);
					servicioEvolucionPrmDstvFicha.guardarEvolucion(tramiteTraficoMatrVO.getNumExpediente(), idUsuario,
							TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.ANULAR, new Date(), estadoAnt,
							estadoNuevo.getValorEnum(), docId, ipConexion);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los estados de los trámites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(ERROR_AL_ACTUALIZAR_ESTADOS_TRAMITES);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesFinalizadosTelematicamenteSinImpr(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud) {
		try {
			return tramiteTraficoMatrDao.getListaTramiteFinalizadosTelematicamenteSinImpr(idContrato, fechaPresentacion, tipoTramiteSolicitud);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de trámites finalizados telemáticamente, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesSTPorFechaContrato(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud) {
		try {
			return tramiteTraficoMatrDao.getlistaTramiteSTPorContratoYFecha(idContrato, fechaPresentacion, tipoTramiteSolicitud);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de trámites super telemáticos, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getTramitePorMatriculaContrato(String matricula, Long idContrato) {
		return tramiteTraficoMatrDao.getTramitePorMatriculaContrato(matricula, idContrato);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesPorMatricula(String matricula) {
		return tramiteTraficoMatrDao.getListaTramitesPorMatricula(matricula);
	}

	// Estadísticas MATE

	@Override
	@Transactional(readOnly = true)
	public Integer obtenerTotalesTramitesMATE(boolean esTelematico, String delegacion, Fecha fechaDesde, Fecha fechaHasta) throws ParseException {
		return tramiteTraficoMatrDao.obtenerTotalesTramitesMATE(esTelematico, delegacion, fechaDesde.getDate(), fechaHasta.getDate());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerTramitesMATETelJefatura(String provincia, Fecha fechaDesde, Fecha fechaHasta) throws ParseException {
		return tramiteTraficoMatrDao.obtenerTramitesMATETelJefatura(provincia, fechaDesde.getDate(), fechaHasta.getDate());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerTramitesMATETelTipoVehiculo(String provincia, Fecha fechaDesde, Fecha fechaHasta) throws ParseException {
		return tramiteTraficoMatrDao.obtenerTramitesMATETelTipoVehiculo(provincia, fechaDesde.getDate(), fechaHasta.getDate());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerTramitesMATETelCarburante(String provincia, Fecha fechaDesde, Fecha fechaHasta) throws ParseException {
		return tramiteTraficoMatrDao.obtenerTramitesMATETelCarburante(provincia, fechaDesde.getDate(), fechaHasta.getDate());
	}

	@Override
	@Transactional(readOnly = true)
	public Integer obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica(String provincia, String jefatura, Fecha fechaDesde, Fecha fechaHasta) throws ParseException {
		return tramiteTraficoMatrDao.obtenerTramitesMATEFinalizadoPdfVehiculosSiFichaTecnica(provincia, jefatura, fechaDesde.getDate(), fechaHasta.getDate(), true);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica(String provincia, String jefatura, Fecha fechaDesde, Fecha fechaHasta) throws ParseException {
		return tramiteTraficoMatrDao.obtenerTramitesMATEFinalizadoPdfVehiculosSiFichaTecnica(provincia, jefatura, fechaDesde.getDate(), fechaHasta.getDate(), false);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos(String provincia, String jefatura, Fecha fechaDesde, Fecha fechaHasta) throws ParseException {
		return tramiteTraficoMatrDao.obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos(provincia, jefatura, fechaDesde.getDate(), fechaHasta.getDate());
	}
	
	@Override
	@Transactional
	public void actualizarCampoAutorizacion(TramiteTraficoVO tramiteTrafico) {
		try {
			TramiteTrafMatrVO tramiteTrafMatrVO = getTramiteMatriculacionVO(tramiteTrafico.getNumExpediente(), Boolean.FALSE, Boolean.FALSE);
			if(tramiteTrafMatrVO != null) {
				if("SI".equalsIgnoreCase(tramiteTrafMatrVO.getAutorizadoExentoCtr())){
					tramiteTrafMatrVO.setAutorizadoExentoCtr("NO");
				}
				if("SI".equalsIgnoreCase(tramiteTrafMatrVO.getAutorizadoFichaA())){
					tramiteTrafMatrVO.setAutorizadoFichaA("NO");
				}
				tramiteTraficoMatrDao.actualizar(tramiteTrafMatrVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el campo de autorización, error: ", e);
		}
		
	}
}