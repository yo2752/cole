package org.gestoresmadrid.oegam2comun.conductor.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.core.conductor.model.dao.ConductorDao;
import org.gestoresmadrid.core.conductor.model.vo.ConductorVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.conductor.model.service.ServicioConductor;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ResultConsultaConductorBean;
import org.gestoresmadrid.oegam2comun.conductor.view.dto.ConductorDto;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.DatosEspecificos;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.DatosFirmados;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.DatosGenericos;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.InformacionAsunto;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.InformacionDestino;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.InformacionPersona;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.InformacionPersona.DocumentoIdentificacion;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.evolucionCayc.model.service.ServicioEvolucionCayc;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioConductorImpl implements ServicioConductor {

	private static final long serialVersionUID = -4112503218921839653L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConductorImpl.class);
	@Autowired
	Conversor conversor;
	@Autowired
	ServicioContrato servicioContrato;
	@Autowired
	ServicioPersona servicioPersona;
	@Autowired
	ServicioUsuario servicioUsuario;
	@Autowired
	ServicioDireccion servicioDireccion;
	@Autowired
	ServicioEvolucionPersona servicioEvolucionPersona;
	@Autowired
	ConductorDao conductorDao;
	@Autowired
	ServicioCola servicioCola;
	@Autowired
	ServicioEvolucionCayc servicioEvolucionCayc;
	@Autowired
	private GestorDocumentos gestorDocumentos;
	@Autowired
	BuildConductorHabitual buildConductor;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	@Transactional
	public ResultConsultaConductorBean guardarConductor(ConductorDto conductorDto, BigDecimal idUsuario) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			resultado = validarDatosConductorOegam(conductorDto);
			if (!resultado.getError()) {
				Date fecha = new Date();
				BigDecimal estadoAnt = null;
				TipoActualizacion tipoActualizacion = null;
				ConductorVO conductorVO = conversor.transform(conductorDto, ConductorVO.class);
				rellenarDatosGuardadoInicial(conductorDto, conductorVO, fecha);
				if (conductorDto.getEstado() != null && !conductorDto.getEstado().isEmpty()) {
					estadoAnt = new BigDecimal(conductorDto.getEstado());
				}

				tipoActualizacion = conductorDto.getIdConductor() != null ? TipoActualizacion.MOD : TipoActualizacion.CRE;

				// Guardamos
				resultado = guardar(conductorDto, conductorVO, idUsuario);
				if (!resultado.getError()) {
					servicioEvolucionCayc.guardarEvolucion(conductorVO.getNumExpediente(), idUsuario.longValue(), fecha,
							estadoAnt, conductorVO.getEstado(), tipoActualizacion.getValorEnum());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el conductor , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el conductor.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultConsultaConductorBean guardar(ConductorDto conductorDto, ConductorVO conductorVO,
			BigDecimal idUsuario) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			if (conductorDto.getNumExpediente() != null
					&& conductorDto.getNumExpediente().intValue() == -1) {
				String tipo = null;
				if (conductorDto.getOperacion().compareTo("AC") == 0) {
					tipo = "3";
				}
				if (conductorDto.getOperacion().compareTo("MC") == 0) {
					tipo = "4";
				}
				BigDecimal temporal = conductorDao.generarNumExpediente(conductorDto.getNumColegiado(), tipo);
				conductorVO.setNumExpediente(temporal);
			}

			// Guardamos primero la persona
			if (conductorDto.getPersona() != null && conductorDto.getPersona().getNif() != null
					&& !conductorDto.getPersona().getNif().isEmpty()) {
				conductorDto.getPersona().setNumColegiado(conductorVO.getNumColegiado());
				UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
				boolean direccionNueva = false;
				ResultBean resultPersona = servicioPersona.guardarActualizar(
						conversor.transform(conductorDto.getPersona(), PersonaVO.class), null, null, usuario, null);
				if (!resultPersona.getError()) {
					DireccionVO direccion = conversor.transform(conductorDto.getPersona().getDireccionDto(),
							DireccionVO.class);
					ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(direccion,
							conductorVO.getNif(), conductorVO.getNumColegiado(), null, null);
					if (resultDireccion != null && !resultDireccion.getError()) {
						direccion = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
						direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
						servicioEvolucionPersona.guardarEvolucionPersonaDireccion(conductorVO.getNif(), null, null,
								conductorVO.getNumColegiado(), usuario, direccionNueva);
						conductorVO.setIdDireccion(direccion.getIdDireccion());
						// Guardo el conductor
					} else {
						resultado.setError(true);
						if (null != resultDireccion && null != resultDireccion.getListaMensajes()
								&& !resultDireccion.getListaMensajes().isEmpty()) {
							resultado.setMensaje(resultDireccion.getListaMensajes().get(0));
						} else {
							resultado.setMensaje("Error al guardar la dirección para un conductor.");
						}
						return resultado;
					}
				} else {
					resultado.setError(true);
					resultado.setMensaje("Faltan datos de la persona para guardar el conductor.");
					return resultado;
				}
				// Añadir actualización de evolución
			}
			// Guardo el conductor
			resultado.setNumExpediente(conductorVO.getNumExpediente());
			conductorDao.guardarOActualizar(conductorVO);
		} catch (Exception e) {
			resultado.setError(true);
			resultado.setMensaje("Error de BD al dar de alta conductor:" + e.getMessage());
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultConsultaConductorBean validarConductor(ConductorDto conductorDto, BigDecimal idUsuario) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			resultado = validarDatosConductor(conductorDto);
			if (!resultado.getError()) {
				ConductorVO conductorVO = conversor.transform(conductorDto, ConductorVO.class);
				BigDecimal estadoAnt = conductorVO.getEstado();
				conductorVO.setEstado(new BigDecimal(EstadoCaycEnum.Validado.getValorEnum()));
				resultado = guardar(conductorDto, conductorVO, idUsuario);
				if (!resultado.getError()) {
					servicioEvolucionCayc.guardarEvolucion(conductorVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, conductorVO.getEstado(), TipoActualizacion.MOD.getValorEnum());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el conductor , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el conductor.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultConsultaConductorBean getConductorDto(BigDecimal numExpediente) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConductorVO conductorVO = getConductorPorExpVO(numExpediente, true);
				if (conductorVO != null) {
					ConductorDto conductorDto = conversor.transform(conductorVO, ConductorDto.class);
					if (conductorVO.getDireccion() != null) {
						conductorDto.getPersona()
								.setDireccionDto(conversor.transform(conductorVO.getDireccion(), DireccionDto.class));
					}
					resultado.setNumExpediente(numExpediente);
					resultado.setConsultaConductor(conductorDto);
				} else {
					resultado.setError(true);
					resultado.setMensaje(
							"Ha sucedido un error a la hora de obtener el conductorDto con numero de expediente: "
									+ numExpediente);
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje(
						"Ha sucedido un error a la hora de obtener el conductorDto con numero de expediente: "
								+ numExpediente);
			}
		} catch (Exception e) {
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el conductorDto con numero de expediente: "
					+ numExpediente);
			log.error("Ha sucedido un error a la hora de obtener el conductorDto con numero de expediente: "
					+ numExpediente + " , error: ", e);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ConductorVO getConductorPorExpVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			if (numExpediente != null) {
				return conductorDao.getConductorPorExpediente(numExpediente, tramiteCompleto);
			} else {
				log.error("No se puede realizar una consulta a la BBDD con el id a null para los conductores.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el conductor con numExpediente: " + numExpediente
					+ " , error: ", e);
		}
		return null;
	}

	private ResultConsultaConductorBean validarDatosConductorOegam(ConductorDto conductorDto) {
		ResultConsultaConductorBean result = new ResultConsultaConductorBean(Boolean.FALSE);
		if (conductorDto == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Debe de rellenar los datos obligatorios para poder realizar una alta de conductor.");
		} else if (conductorDto.getContrato() == null || conductorDto.getContrato().getIdContrato() == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Debe de seleccionar un contrato para poder realizar realizar una alta de conductor.");
		}
		return result;
	}

	private ResultConsultaConductorBean validarDatosConductor(ConductorDto conductorDto) {

		ResultConsultaConductorBean result = new ResultConsultaConductorBean(Boolean.FALSE);
		if (conductorDto.getPersona() == null || conductorDto.getPersona().getNif() == null
				|| conductorDto.getPersona().getNif().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El campo nif es obligatorio.");
		} else if (conductorDto.getPersona() == null || conductorDto.getPersona().getTipoPersona() == null
				|| (TipoPersona.Fisica.getValorEnum().equals(conductorDto.getPersona().getTipoPersona())
						&& (conductorDto.getPersona().getFechaNacimiento() == null || conductorDto.getPersona().getFechaNacimiento().isfechaNula()))) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Para personas fisicas es obligatorio es la fecha de nacimiento.");
		} else if (conductorDto.getPersona() == null || conductorDto.getPersona().getDireccionDto() == null
				|| conductorDto.getPersona().getDireccionDto().getIdTipoVia() == null
				|| conductorDto.getPersona().getDireccionDto().getIdTipoVia().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El tipo de via es obligatorio.");
		} else if (conductorDto.getPersona() == null || conductorDto.getPersona().getDireccionDto() == null
				|| conductorDto.getPersona().getDireccionDto().getNombreVia() == null
				|| conductorDto.getPersona().getDireccionDto().getNombreVia().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El nombre de via es obligatorio.");
		} else if (conductorDto.getPersona() == null || conductorDto.getPersona().getDireccionDto() == null
				|| conductorDto.getPersona().getDireccionDto().getNumero() == null
				|| conductorDto.getPersona().getDireccionDto().getNumero().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El numero de via es obligatorio.");
		} else if (conductorDto.getMatricula() == null || conductorDto.getMatricula().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("La matrícula es obligatorio.");
		} else if (!UtilesValidaciones.validarMatricula(conductorDto.getMatricula())) {
			result.setError(Boolean.TRUE);
			result.setMensaje(" La matrícula no tiene el formato correcto.");
		} else if (conductorDto.getBastidor() == null || conductorDto.getBastidor().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El bastidor es obligatorio.");
		} else if (!UtilesValidaciones.validarBastidor(conductorDto.getBastidor())) {
			result.setError(Boolean.TRUE);
			result.setMensaje(" El bastidor no tiene el formato correcto.");
		} else if (conductorDto.getConsentimiento() == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El consentimiento es obligatorio.");
		} else if (!EstadoCaycEnum.Iniciado.getValorEnum().equals(conductorDto.getEstado())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El trámite " + conductorDto.getNumExpediente() + " debe estar en estado iniciado.");
		} else if (conductorDto.getFechaIni().isfechaNula()) {
			result.setError(Boolean.TRUE);
			result.setMensaje(
					"El trámite " + conductorDto.getNumExpediente() + " tiene que tener fecha de inicio.");
		}
		return result;
	}

	public void rellenarDatosGuardadoInicial(ConductorDto conductorDto, ConductorVO conductorVO, Date fecha)
			throws Exception {

		if (conductorVO.getFechaAlta() == null) {
			conductorVO.setFechaAlta(fecha);
		}
		if (conductorDto.getNumExpediente() == null) {
			if (conductorDto.getNumColegiado() != null && !conductorDto.getNumColegiado().isEmpty()) {
				conductorVO.setNumColegiado(conductorDto.getNumColegiado());
			} else {
				ContratoDto contratoDto = servicioContrato.getContratoDto(conductorDto.getContrato().getIdContrato());
				conductorVO.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
			}
			if (TipoOperacionCaycEnum.Alta_Conductor.getValorEnum().equals(conductorDto.getOperacion())) {
				conductorVO.setNumExpediente(generarNumExpediente(conductorVO.getNumColegiado(),
						TipoOperacionCaycEnum.Alta_Conductor.getTipo()));
			} else {
				conductorVO.setNumExpediente(generarNumExpediente(conductorVO.getNumColegiado(),
						TipoOperacionCaycEnum.Modif_Conductor.getTipo()));
			}
		}
		conductorVO.setEstado(new BigDecimal(EstadoCaycEnum.Iniciado.getValorEnum()));
		// conductorVO.setOperacion(TipoOperacionCaycEnum.Alta_Conductor.getValorEnum());
	}

	private BigDecimal generarNumExpediente(String numColegiado, String tipoOperacion) throws Exception {
		return conductorDao.generarNumExpediente(numColegiado, tipoOperacion);
	}

	private void modificarDatos(ConductorVO conductorVO) {
		if (conductorVO.getMatricula() != null && !conductorVO.getMatricula().isEmpty()) {
			conductorVO.setMatricula(conductorVO.getMatricula().toUpperCase());
		}
		if (conductorVO.getBastidor() != null && !conductorVO.getBastidor().isEmpty()) {
			conductorVO.setBastidor(conductorVO.getBastidor().toUpperCase());
		}
	}

	@Override
	@Transactional
	public ResultConsultaConductorBean consultarConductor(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConductorVO conductorVO = getConductorPorExpVO(numExpediente, Boolean.TRUE);
				BigDecimal estadoAnt = conductorVO.getEstado();
				resultado = validarConductorConsulta(conductorVO);
				if (!resultado.getError()) {
					modificarDatos(conductorVO);
					resultado = realizarConsulta(conductorVO, idUsuario);
					resultado.setNumExpediente(conductorVO.getNumExpediente());
					// Meter Evolución
					servicioEvolucionCayc.guardarEvolucion(conductorVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, conductorVO.getEstado(), TipoActualizacion.MOD.getValorEnum());
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Ha sucedido un error a la hora de generar la consulta de conductor.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la consulta de conductor , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la consulta de conductor.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de generar la cola para la consulta de conductor, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la cola para la consulta de conductor.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	/**
	 * Valida los datos antes de encolar.
	 * 
	 * @param conductorVO
	 * @return
	 */
	private ResultConsultaConductorBean validarConductorConsulta(ConductorVO conductorVO) {
		ResultConsultaConductorBean result = new ResultConsultaConductorBean(Boolean.FALSE);
		if (conductorVO == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("No se encuentran datos para el Conductor");
		} else if (!EstadoCaycEnum.Validado.getValorEnum().equals(conductorVO.getEstado().toString())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El tramite no está en estado validado con numero de expediente: "
					+ conductorVO.getNumExpediente().toString());
		}
		return result;
	}

	private ResultConsultaConductorBean realizarConsulta(ConductorVO conductorVO, BigDecimal idUsuario)
			throws OegamExcepcion {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		conductorVO.setEstado(new BigDecimal(EstadoCaycEnum.Pdte_Respuesta_DGT.getValorEnum()));
		conductorDao.guardarOActualizar(conductorVO);
		resultado = buildConductor.generarXml(conductorVO);
		if (!resultado.getError()) {
			org.gestoresmadrid.oegam2comun.conductor.view.xml.SolicitudRegistroEntrada solicitudRegistroEntrada = resultado
					.getSolicitudRegistroEntrada();
			resultado = buildConductor.validarXml(solicitudRegistroEntrada);
			if (!resultado.getError()) {
				resultado = buildConductor.firmarXml(solicitudRegistroEntrada,
						conductorVO.getContrato().getColegiado().getAlias());
			}
		}
		if (!resultado.getError()) {
			String nombreFichero = null;
			String subTipo = null;
			if (TipoOperacionCaycEnum.Alta_Conductor.getValorEnum().equals(conductorVO.getOperacion())) {
				nombreFichero = ConstantesGestorFicheros.XML_ALTA_CONDUCTOR_HABITUAL + "_"
						+ conductorVO.getNumExpediente();
				subTipo = ConstantesGestorFicheros.XML_ALTA_CONDUCTOR_HABITUAL;
			} else {
				nombreFichero = ConstantesGestorFicheros.XML_MOD_CONDUCTOR_HABITUAL + "_"
						+ conductorVO.getNumExpediente();
				subTipo = ConstantesGestorFicheros.XML_MOD_CONDUCTOR_HABITUAL;
			}
			ResultBean resultadoCola = servicioCola.crearSolicitud(ProcesosEnum.CONDUCTOR_HABITUAL.getNombreEnum(),
					nombreFichero, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD_NODO_2), null,
					conductorVO.getNumExpediente().toString(), idUsuario, null,
					new BigDecimal(conductorVO.getContrato().getIdContrato()));
			if (resultadoCola.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultado.getMensaje());
			} else {
				resultado = guardarXml(resultado.getXml(), subTipo, nombreFichero, conductorVO.getNumExpediente());
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultConsultaConductorBean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario,
			BigDecimal estadoNuevo, Boolean accionesAsociadas) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConductorVO conductorVO = conductorDao.getConductorPorExpediente(numExpediente, false);
				if (conductorVO != null) {
					BigDecimal estadoAnt = conductorVO.getEstado();
					conductorVO.setEstado(estadoNuevo);
					conductorDao.actualizar(conductorVO);
					// Actualizo Evolución
					servicioEvolucionCayc.guardarEvolucion(conductorVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, estadoNuevo, TipoActualizacion.MOD.getValorEnum());
					if (accionesAsociadas) {
						resultado = accionesCambiarEstadoSinValidacion(conductorVO, estadoAnt);
					}
					if (!resultado.getError()) {
						resultado.setMensaje("La consulta de Conductor con número de expediente: " + numExpediente
								+ ", se ha actualizado.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La consulta de Conductor con número de expediente: " + numExpediente
							+ ", no se ha podido cambiar el estado porque no se encuentra disponible.");
				}
			}
		} catch (Exception e) {
			log.error(
					"Ha sucedido un error a la hora de cambiar el estado de la consulta de Conductor con numero de expediente: "
							+ numExpediente + ", error: ",
					e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de la consulta de Conductor");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultConsultaConductorBean accionesCambiarEstadoSinValidacion(ConductorVO conductorVO,
			BigDecimal estadoAnt) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			if (EstadoCaycEnum.Pdte_Respuesta_DGT.getValorEnum().equals(estadoAnt.toString())) {
				servicioCola.eliminar(new BigDecimal(conductorVO.getIdConductor()),
						ProcesosEnum.CONDUCTOR_HABITUAL.toString());
			}
		} catch (Exception e) {
			log.error(
					"Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de conductor habitual con num.Expediente: "
							+ conductorVO.getNumExpediente() + ", error: ",
					e);
			resultado.setError(true);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de conductor habitual con num.Expediente: "
							+ conductorVO.getNumExpediente() + ".");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ConductorDto getConductorDtoPorIdDto(BigDecimal idConductor, Boolean tramiteCompleto) {
		try {
			if (idConductor != null) {
				ConductorVO conductorVO = getConductorPorIdConsulta(idConductor, tramiteCompleto);
				if (conductorVO != null) {
					ConductorDto conductorDto = conversor.transform(conductorVO, ConductorDto.class);
					conductorDto.getPersona()
							.setDireccionDto(conversor.transform(conductorVO.getDireccion(), DireccionDto.class));
					return conductorDto;
				} else {
					log.error("No se han encontrado datos de conductor para el id: " + idConductor);
				}
			} else {
				log.error(
						"No se puede realizar una consulta a la BBDD con el id a null para las consultas de conductor.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta de conductor con idConductor: "
					+ idConductor + " , error: ", e);
		}
		return null;
	}

	@Override
	public ConductorVO getConductorPorIdConsulta(BigDecimal idConductor, Boolean tramiteCompleto) {
		try {
			if (idConductor != null) {
				return conductorDao.getConductorPorId(idConductor.longValue(), tramiteCompleto);
			} else {
				log.error(
						"No se puede realizar una consulta a la BBDD con el id a null para las consultas de conductor.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el conductor con idConductor: " + idConductor
					+ " , error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultConsultaConductorBean generarXmlApp(BigDecimal numExpediente) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		if (numExpediente != null) {
			resultado = getConductorDto(numExpediente);
			if (!resultado.getError()) {
				ConductorDto conductorDto = resultado.getConductorDto();
				resultado = validarDatosGeneracionXML(conductorDto);
				if (!resultado.getError()) {
					String xmlString = buildXml(conductorDto);
					if (xmlString != null && !xmlString.isEmpty()) {
						try {
							File file = null;
							if (TipoOperacionCaycEnum.Alta_Conductor.getValorEnum()
									.equals(conductorDto.getOperacion())) {
								file = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.CAYC,
										ConstantesGestorFicheros.XML_ALTA_CONDUCTOR_HABITUAL,
										Utilidades.transformExpedienteFecha(conductorDto.getNumExpediente()),
										ConstantesGestorFicheros.XML_ALTA_CONDUCTOR_HABITUAL + "_"
												+ conductorDto.getNumExpediente(),
										ConstantesGestorFicheros.EXTENSION_XML, xmlString.getBytes(StandardCharsets.UTF_8));
							} else {// Operación de Modificación
								file = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.CAYC,
										ConstantesGestorFicheros.XML_MOD_CONDUCTOR_HABITUAL,
										Utilidades.transformExpedienteFecha(conductorDto.getNumExpediente()),
										ConstantesGestorFicheros.XML_MOD_CONDUCTOR_HABITUAL + "_"
												+ conductorDto.getNumExpediente(),
										ConstantesGestorFicheros.EXTENSION_XML, xmlString.getBytes(StandardCharsets.UTF_8));
							}
							resultado.setFicheroXml(file);
							resultado.setNombreXml(resultado.getFicheroXml().getName());
						} catch (OegamExcepcion e) {
							log.error("Ha sucedido un error a la hora de guardar el xml generado, error: ", e);
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Ha sucedido un error a la hora de guardar el xml generado.");
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Ha sucedido un error a la hora de generar el xml para la aplicación.");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(
						"No se puede generar el xml porque no se pueden recuperar datos del tramite con el numExpediente: "
								+ numExpediente);
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(
					"No se puede generar el xml porque no se encuentran datos del tramite para numExpediente: "
							+ numExpediente);
		}
		return resultado;
	}

	private String buildXml(ConductorDto conductorDto) {

		SolicitudRegistroEntrada solicitud = new SolicitudRegistroEntrada();
		DatosFirmados datosFirmados = new DatosFirmados();
		DatosGenericos datosGenericos = new DatosGenericos();
		DatosEspecificos datosEspecificos = new DatosEspecificos();
		InformacionPersona remitente = new InformacionPersona();
		remitente.setNombre("Usuario Prueba DGT");
		remitente.setApellidos(null);
		DocumentoIdentificacion documentoIdentificacion = new DocumentoIdentificacion();
		documentoIdentificacion.setNumero("A08111114");
		remitente.setDocumentoIdentificacion(documentoIdentificacion);
		remitente.setCorreoElectronico(null);
		InformacionAsunto asunto = new InformacionAsunto();
		asunto.setCodigo("SAC");
		asunto.setDescripcion("Solicitud de Anotacion de Conductor Habitual");
		InformacionDestino destino = new InformacionDestino();
		destino.setCodigo("101001");
		destino.setDescripcion("DGT - Vehiculos");
		datosGenericos.setInteresados(null);
		datosGenericos.setRemitente(remitente);
		datosGenericos.setAsunto(asunto);
		datosGenericos.setDestino(destino);
		datosEspecificos.setBastidor(conductorDto.getBastidor().substring(conductorDto.getBastidor().length() - 6,
				conductorDto.getBastidor().length())); // 123456
		datosEspecificos.setMatricula(conductorDto.getMatricula()); // 1143GJC
		datosFirmados.setDatosGenericos(datosGenericos);
		datosFirmados.setDatosEspecificos(datosEspecificos);
		// Se debe firmar el objecto Datos firmados
		// firmarDatos(DatosFirmados datosFirmados);
		solicitud.setDatosFirmados(datosFirmados);

		// Generamos XML
		String xmlString = null;
		try {
			StringWriter writer = new StringWriter();
			Marshaller m = JAXBContext
					.newInstance("es.trafico.servicios.vehiculos.conductor.comunicaciones.webservices.solicitud")
					.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			m.marshal(solicitud, writer);
			writer.flush();
			xmlString = writer.toString();
		} catch (Exception e) {
			log.error("Error al generar XML del objeto SolicitudRegistroEntrada");
			log.error(e);
		}
		return firmarXML(xmlString, conductorDto.getContrato().getColegiadoDto().getAlias());
	}

	private ResultConsultaConductorBean validarDatosGeneracionXML(ConductorDto conductorDto) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		// Se validan de momento los campos especificos del XML de solicitud
		if (conductorDto == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos del tramite para generar el XML de solicitud");
		} else if (conductorDto.getMatricula() == null || conductorDto.getMatricula().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La matrícula es obligatoria en la generación del XML de solicitud");
		} else if (conductorDto.getBastidor() == null || conductorDto.getBastidor().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El bastidor es obligatoria en la generación del XML de solicitud");
		}
		return resultado;
	}

	private String firmarXML(String xml, String alias) {
		byte[] xmlSinFirma;
		String cadena = null;
		try {
			xmlSinFirma = IOUtils.toByteArray(xml);
			UtilesViafirma utilesViaFirma = new UtilesViafirma();
			byte[] firma = utilesViaFirma.firmarXmlPorAlias(xmlSinFirma, alias);
			cadena = new String(firma, StandardCharsets.UTF_8);
			// String certificado =
			// xml.substring(0,xml.indexOf(INICIO_TAG_CERTIFICADO));
			// certificado = certificado + cadena + FIN_TAG_CERTIFICADO;
			// certificado = certificado.replace("\n", "").replace("\r", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cadena;
	}

	@Override
	@Transactional
	public void cambiarEstadoProceso(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo,
			String respuesta, String numRegistro) {

		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConductorVO conductorVO = getConductorPorExpVO(numExpediente, Boolean.TRUE);
				if (conductorVO != null) {
					BigDecimal estadoAnt = conductorVO.getEstado();
					conductorVO.setEstado(estadoNuevo);
					conductorVO.setRespuesta(respuesta);
					conductorVO.setNumRegistro(numRegistro);
					if (estadoNuevo != null && EstadoCaycEnum.Finalizado.getValorEnum().equals(estadoNuevo.toString())) {
						conductorVO.setFechaPresentacion(new Date());
					}
					conductorDao.actualizar(conductorVO);
					servicioEvolucionCayc.guardarEvolucion(conductorVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, estadoNuevo, TipoActualizacion.MOD.getValorEnum());
				} else {
					log.error(
							"No se puede cambiar el estado porque no existen datos en la BBDD para el conductor con numExpediente: "
									+ numExpediente.toString());
					resultado.setError(Boolean.TRUE);
				}
			} else {
				log.error("No se puede cambiar el estado porque el id de la consulta de conductor esta vacio.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del conductor con numExpediente:   "
					+ numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
		}
	}

	@Override
	@Transactional
	public ResultConsultaConductorBean modificarConductor(BigDecimal numExpediente, BigDecimal idUsuario,
			java.sql.Date dateIni, java.sql.Date dateFin) {

		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			if (numExpediente != null && dateIni != null && dateFin != null) {
				ConductorVO conductorBBDD = getConductorPorExpVO(numExpediente, Boolean.TRUE);
				if (conductorBBDD != null) {
					if (EstadoCaycEnum.Finalizado.getValorEnum().equals(conductorBBDD.getEstado().toString())) {
						ConductorVO conductorVO = construirconductorModificacion(conductorBBDD, dateIni, dateFin);
						resultado = realizarConsulta(conductorVO, idUsuario);
						resultado.setNumExpediente(conductorVO.getNumExpediente());
						servicioEvolucionCayc.guardarEvolucion(conductorVO.getNumExpediente(), idUsuario.longValue(),
								new Date(), numExpediente, conductorVO.getEstado(),
								TipoActualizacion.MFE.getValorEnum());
					} else {
						resultado.setError(true);
						resultado.setMensaje("El trámite con numero de expediente: " + numExpediente
								+ " debe estar en estado Finalizado para poder ser modificado.");
					}
				} else {
					resultado.setError(true);
					resultado.setMensaje("No se puede modificar el conductor habitual porque no se encuentran datos"
							+ "para el tramite con numero de expediente: " + numExpediente.toString());
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Faltan datos para dar crear una modificación de conductor habitual");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la consulta de modificacion de conductor , error: ",
					e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la consulta de modificacion de conductor.");
		} catch (OegamExcepcion e) {
			log.error(
					"Ha sucedido un error a la hora de generar la cola para la consulta de modificacion de conductor, error: ",
					e);
			resultado.setError(true);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de generar la cola de modificacion para la consulta de conductor.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ConductorVO construirconductorModificacion(ConductorVO conductorBBDD, Date dateIni, Date dateFin)
			throws Exception {

		ConductorVO conductorVO = conversor.transform(conductorBBDD, ConductorVO.class);
		// El número de expediente será nuevo, las fechas y estado.
		conductorVO.setNumExpediente(
				generarNumExpediente(conductorVO.getNumColegiado(), TipoOperacionCaycEnum.Modif_Conductor.getTipo()));
		conductorVO.setIdConductor(null);
		conductorVO.setNumRegistro(null);
		conductorVO.setRespuesta(null);
		conductorVO.setFechaAlta(new Date());
		conductorVO.setFechaIni(dateIni);
		conductorVO.setFechaFin(dateFin);
		conductorVO.setOperacion(TipoOperacionCaycEnum.Modif_Conductor.getValorEnum());
		return conductorVO;
	}

	@Override
	@Transactional
	public ResultConsultaArrendatarioBean guardarConductorProceso(BigDecimal numExpediente, BigDecimal idUsuario,
			BigDecimal estadoNuevo, String respuesta, String numRegistro) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConductorVO coductorVO = getConductorPorExpVO(numExpediente, Boolean.TRUE);
				if (coductorVO != null) {
					BigDecimal estadoAnt = coductorVO.getEstado();
					coductorVO.setEstado(estadoNuevo);
					coductorVO.setRespuesta(respuesta);
					coductorVO.setNumRegistro(numRegistro);
					conductorDao.actualizar(coductorVO);
					servicioEvolucionCayc.guardarEvolucion(coductorVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, estadoNuevo, TipoActualizacion.MOD.getValorEnum());
				} else {
					log.error(
							"No se puede cambiar el estado porque no existen datos en la BBDD para el conductor con id: "
									+ numExpediente);
					resultado.setError(Boolean.TRUE);
				}
			} else {
				log.error("No se puede cambiar el estado porque el id de la consulta de arrendatario esta vacio.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del arrendatario con id:   " + numExpediente
					+ ", error: ", e);
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private ResultConsultaConductorBean guardarXml(byte[] xml, String subTipo, String nombreFichero,
			BigDecimal numExpediente) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.CAYC);
		fichero.setSubTipo(subTipo);
		fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		fichero.setFicheroByte(xml);
		fichero.setNombreDocumento(nombreFichero);
		try {
			gestorDocumentos.guardarByte(fichero);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de guardar el xml para la solicitud de conductor, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el xml para la solicitud de conductor.");
		}
		return resultado;
	}
}