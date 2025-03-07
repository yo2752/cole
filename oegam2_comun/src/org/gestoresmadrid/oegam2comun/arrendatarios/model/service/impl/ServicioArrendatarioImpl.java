package org.gestoresmadrid.oegam2comun.arrendatarios.model.service.impl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.gestoresmadrid.core.arrendatarios.model.dao.ArrendatarioDao;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.core.arrendatarios.model.vo.ArrendatarioVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegam2comun.arrendatarios.model.service.ServicioArrendatario;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.dto.ArrendatarioDto;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
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

@Service
public class ServicioArrendatarioImpl implements ServicioArrendatario {

	private static final long serialVersionUID = 7752268719631460462L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioArrendatarioImpl.class);
	@Autowired
	Conversor conversor;
	@Autowired
	ServicioContrato servicioContrato;
	@Autowired
	ArrendatarioDao arrendatarioDao;
	@Autowired
	ServicioPersona servicioPersona;
	@Autowired
	ServicioUsuario servicioUsuario;
	@Autowired
	ServicioDireccion servicioDireccion;
	@Autowired
	ServicioEvolucionPersona servicioEvolucionPersona;
	@Autowired
	ServicioCola servicioCola;
	@Autowired
	ServicioEvolucionCayc servicioEvolucionCayc;
	@Autowired
	private GestorDocumentos gestorDocumentos;
	@Autowired
	BuildArrendatario buildArrendatario;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	@Transactional(readOnly = true)
	public ResultConsultaArrendatarioBean getArrendatarioDto(BigDecimal numExpediente) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ArrendatarioVO arrendatarioVO = getArrendatarioPorExpVO(numExpediente, true);
				if (arrendatarioVO != null) {
					ArrendatarioDto arrendatarioDto = conversor.transform(arrendatarioVO, ArrendatarioDto.class);
					if (arrendatarioVO.getDireccion() != null) {
						arrendatarioDto.getPersona().setDireccionDto(
								conversor.transform(arrendatarioVO.getDireccion(), DireccionDto.class));
					}
					resultado.setNumExpediente(numExpediente);
					resultado.setArrendatarioDto(arrendatarioDto);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(
							"Ha sucedido un error a la hora de obtener el arrendatarioDto con numero de expediente: "
									+ numExpediente);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(
						"Ha sucedido un error a la hora de obtener el arrendatarioDto con numero de expediente: "
								+ numExpediente);
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de obtener el arrendatarioDto con numero de expediente: "
							+ numExpediente);
			log.error("Ha sucedido un error a la hora de obtener el arrendatarioDto con numero de expediente: "
					+ numExpediente + " , error: ", e);
		}
		return resultado;
	}

	@Override
	@Transactional
	public void cambiarEstadoProceso(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo,
			String respuesta, String numRegistro) {

		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ArrendatarioVO arrendatarioVO = getArrendatarioPorExpVO(numExpediente, Boolean.TRUE);
				if (arrendatarioVO != null) {
					BigDecimal estadoAnt = arrendatarioVO.getEstado();
					arrendatarioVO.setEstado(estadoNuevo);
					arrendatarioVO.setRespuesta(respuesta);
					arrendatarioVO.setNumRegistro(numRegistro);
					if (estadoNuevo != null
							&& EstadoCaycEnum.Finalizado.getValorEnum().equals(estadoNuevo.toString())) {
						arrendatarioVO.setFechaPresentacion(new Date());
					}
					arrendatarioDao.actualizar(arrendatarioVO);
					servicioEvolucionCayc.guardarEvolucion(arrendatarioVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, estadoNuevo, TipoActualizacion.MOD.getValorEnum());
				} else {
					log.error(
							"No se puede cambiar el estado porque no existen datos en la BBDD para el arrendatario con numExpediente: "
									+ numExpediente.toString());
					resultado.setError(Boolean.TRUE);
				}
			} else {
				log.error("No se puede cambiar el estado porque el id de la consulta de arrendatario esta vacio.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del arrendatario con numExpediente:   "
					+ numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ArrendatarioVO getArrendatarioPorExpVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			if (numExpediente != null) {
				return arrendatarioDao.getArrendatarioPorExpediente(numExpediente, tramiteCompleto);
			} else {
				log.error("No se puede realizar una consulta a la BBDD con el id a null para los arrendatarios.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el arrendatario con numExpediente: " + numExpediente
					+ " , error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultConsultaArrendatarioBean guardarArrendatario(ArrendatarioDto arrendatarioDto, BigDecimal idUsuario) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			resultado = validarDatosArrendatarioOegam(arrendatarioDto);
			if (!resultado.getError()) {
				Date fecha = new Date();
				BigDecimal estadoAnt = null;
				TipoActualizacion tipoActualizacion = null;
				ArrendatarioVO arrendatarioVO = conversor.transform(arrendatarioDto, ArrendatarioVO.class);
				rellenarDatosGuardadoInicial(arrendatarioDto, arrendatarioVO, fecha);
				if (arrendatarioDto.getEstado() != null && !arrendatarioDto.getEstado().isEmpty()) {
					estadoAnt = new BigDecimal(arrendatarioDto.getEstado());
				}
				if (arrendatarioDto.getIdArrendatario() != null) {
					tipoActualizacion = TipoActualizacion.MOD;
				} else {
					tipoActualizacion = TipoActualizacion.CRE;
				}
				modificarDatos(arrendatarioVO);
				// Guardamos
				resultado = guardar(arrendatarioDto, arrendatarioVO, idUsuario);
				if (!resultado.getError()) {
					servicioEvolucionCayc.guardarEvolucion(arrendatarioVO.getNumExpediente(), idUsuario.longValue(),
							fecha, estadoAnt, arrendatarioVO.getEstado(), tipoActualizacion.getValorEnum());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el arrendatario , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el arrendatario.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultConsultaArrendatarioBean validarArrendatario(ArrendatarioDto arrendatarioDto, BigDecimal idUsuario) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			resultado = validarDatosArrendatario(arrendatarioDto);
			if (!resultado.getError()) {
				ArrendatarioVO arrendatarioVO = conversor.transform(arrendatarioDto, ArrendatarioVO.class);
				BigDecimal estadoAnt = arrendatarioVO.getEstado();
				arrendatarioVO.setEstado(new BigDecimal(EstadoCaycEnum.Validado.getValorEnum()));
				resultado = guardar(arrendatarioDto, arrendatarioVO, idUsuario);
				if (!resultado.getError()) {
					servicioEvolucionCayc.guardarEvolucion(arrendatarioVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, arrendatarioVO.getEstado(), TipoActualizacion.MOD.getValorEnum());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el arrendatario , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el arrendatario.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultConsultaArrendatarioBean guardar(ArrendatarioDto arrendatarioDto, ArrendatarioVO arrendatarioVO,
			BigDecimal idUsuario) {

		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {

			// no se pude comparar con null por que el objeto existe realmente
			if (arrendatarioDto.getNumExpediente() != null)
				if (arrendatarioDto.getNumExpediente().intValue() == -1) {
					BigDecimal temporal = new BigDecimal(0);
					String Tipo = null;
					if (arrendatarioDto.getOperacion().compareTo("AA") == 0) {
						Tipo = "1";
					}
					if (arrendatarioDto.getOperacion().compareTo("MA") == 0) {
						Tipo = "2";
					}
					temporal = arrendatarioDao.generarNumExpediente(arrendatarioVO.getNumColegiado(), Tipo);
					arrendatarioVO.setNumExpediente(temporal);
					arrendatarioDto.setNumExpediente(temporal);
				}

			// resultado.setNumExpediente(arrendatarioVO.getNumExpediente());
			if (arrendatarioDto.getPersona() != null && arrendatarioDto.getPersona().getNif() != null
					&& !arrendatarioDto.getPersona().getNif().isEmpty()) {
				arrendatarioDto.getPersona().setNumColegiado(arrendatarioVO.getNumColegiado());
				UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
				boolean direccionNueva = false;
				ResultBean resultPersona = servicioPersona.guardarActualizar(
						conversor.transform(arrendatarioDto.getPersona(), PersonaVO.class), null, null, usuario, null);
				if (!resultPersona.getError()) {
					DireccionVO direccion = conversor.transform(arrendatarioDto.getPersona().getDireccionDto(),
							DireccionVO.class);
					ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(direccion,
							arrendatarioVO.getNif(), arrendatarioVO.getNumColegiado(), null, null);
					if (resultDireccion != null && !resultDireccion.getError()) {
						direccion = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
						direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
						servicioEvolucionPersona.guardarEvolucionPersonaDireccion(arrendatarioVO.getNif(), null, null,
								arrendatarioVO.getNumColegiado(), usuario, direccionNueva);
						arrendatarioVO.setIdDireccion(direccion.getIdDireccion());
					} else {
						resultado.setError(true);
						if (null != resultDireccion && null != resultDireccion.getListaMensajes()
								&& !resultDireccion.getListaMensajes().isEmpty()) {
							resultado.setMensaje(resultDireccion.getListaMensajes().get(0));
						} else {
							resultado.setMensaje("Error al guardar la dirección para un arrendatario.");
						}
						return resultado;
					}
				} else {
					resultado.setError(true);
					resultado.setMensaje("Faltan datos de la persona para guardar el arrendatario.");
					return resultado;
				}
			}
			// Guardo el arrendatario
			resultado.setNumExpediente(arrendatarioVO.getNumExpediente());
			arrendatarioDao.guardarOActualizar(arrendatarioVO);
		} catch (Exception e) {
			resultado.setError(true);
			resultado.setMensaje("Error de BD al dar de alta arrendatario:" + e.getMessage());
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultConsultaArrendatarioBean consultarArrendatario(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ArrendatarioVO arrendatarioVO = getArrendatarioPorExpVO(numExpediente, Boolean.TRUE);
				BigDecimal estadoAnt = arrendatarioVO.getEstado();
				resultado = validarArrenadatarioConsulta(arrendatarioVO);
				if (!resultado.getError()) {
					modificarDatos(arrendatarioVO);
					resultado = realizarConsulta(arrendatarioVO, idUsuario);
					if (!resultado.getError()) {
						servicioEvolucionCayc.guardarEvolucion(arrendatarioVO.getNumExpediente(), idUsuario.longValue(),
								new Date(), estadoAnt, arrendatarioVO.getEstado(),
								TipoActualizacion.MOD.getValorEnum());
					}
					resultado.setNumExpediente(numExpediente);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de generar la consulta de arrendatario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la consulta de arrendatario , error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la consulta de arrendatario.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de generar la cola para la consulta de arrendatario, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la cola para la consulta de arrendatario.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultConsultaArrendatarioBean validarArrenadatarioConsulta(ArrendatarioVO arrendatarioVO) {
		ResultConsultaArrendatarioBean result = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		if (arrendatarioVO == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("No se encuentran datos para el Arrendatario");
		} else if (!EstadoCaycEnum.Validado.getValorEnum().equals(arrendatarioVO.getEstado().toString())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El tramite no está en estado validado con numero de expediente"
					+ arrendatarioVO.getNumExpediente().toString());
		}
		return result;
	}

	private ResultConsultaArrendatarioBean realizarConsulta(ArrendatarioVO arrendatarioVO, BigDecimal idUsuario)
			throws OegamExcepcion {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		arrendatarioVO.setEstado(new BigDecimal(EstadoCaycEnum.Pdte_Respuesta_DGT.getValorEnum()));
		arrendatarioDao.guardarOActualizar(arrendatarioVO);
		//resultado = buildArrendatario.generarXml(arrendatarioVO);
//		if (!resultado.getError()) {
//			SolicitudRegistroEntrada solicitudRegistroEntrada = resultado.getSolicitudRegistroEntrada();
//			// resultado = buildArrendatario.validarXml(solicitudRegistroEntrada);
//			if (!resultado.getError()) {
//				resultado = buildArrendatario.firmarXml(solicitudRegistroEntrada,
//						arrendatarioVO.getContrato().getColegiado().getAlias());
//				if (!resultado.getError()) {
//					String strXml = new String(resultado.getXml(), StandardCharsets.UTF_8);
//					ResultConsultaArrendatarioBean resultado1 = buildArrendatario.validarXml(strXml);
//					if (resultado1.getError()) { // si hay error
//						resultado = resultado1;
//					}
//				}
//			}
//		}
//		if (!resultado.getError()) {
			String nombreFichero = null;
			String subTipo = null;
			if (TipoOperacionCaycEnum.Alta_Arrendatario.getValorEnum().equals(arrendatarioVO.getOperacion())) {
//				nombreFichero = ConstantesGestorFicheros.XML_ALTA_ARRENDATARIO + "_"
//						+ arrendatarioVO.getNumExpediente();
				subTipo = ConstantesGestorFicheros.XML_ALTA_ARRENDATARIO;
			} else {
//				nombreFichero = ConstantesGestorFicheros.XML_MOD_ARRENDATARIO + "_" + arrendatarioVO.getNumExpediente();
				subTipo = ConstantesGestorFicheros.XML_MOD_ARRENDATARIO;
			}
			ResultBean resultadoCola = servicioCola.crearSolicitud(ProcesosEnum.ARRENDATARIOS.getNombreEnum(),
					nombreFichero, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD_NODO_2), "T11",
					arrendatarioVO.getNumExpediente().toString(), idUsuario, null,
					new BigDecimal(arrendatarioVO.getContrato().getIdContrato()));
			if (resultadoCola.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultado.getMensaje());
			} else {
				resultado.setError(Boolean.FALSE);
				resultado.setMensaje("Solicitud generada correctamente");
				//resultado = guardarXml(resultado.getXml(), subTipo, nombreFichero, arrendatarioVO.getNumExpediente());
			}
//		}
		return resultado;
	}

	/**
	 * Valida los datos obligatorios para nuestra plataforma.
	 * 
	 * @param arrendatarioDto
	 * @return
	 */
	private ResultConsultaArrendatarioBean validarDatosArrendatarioOegam(ArrendatarioDto arrendatarioDto) {
		ResultConsultaArrendatarioBean result = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		if (arrendatarioDto == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Debe de rellenar los datos obligatorios para poder realizar una alta de arrendatario.");
		} else if (arrendatarioDto.getContrato() == null || arrendatarioDto.getContrato().getIdContrato() == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Debe de seleccionar un contrato para poder realizar realizar una alta de arrendatario.");
		}

		return result;
	}

	/**
	 * Valida los datos una vez el tramite está guardado.
	 * 
	 * @param arrendatarioDto
	 * @return
	 */
	private ResultConsultaArrendatarioBean validarDatosArrendatario(ArrendatarioDto arrendatarioDto) {
		ResultConsultaArrendatarioBean result = new ResultConsultaArrendatarioBean(Boolean.FALSE);

		if (!EstadoCaycEnum.Iniciado.getValorEnum().equals(arrendatarioDto.getEstado().toString())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El tramite " + arrendatarioDto.getNumExpediente()
					+ " tiene que estar en estado iniciado para poder ser validado.");
		} else if (arrendatarioDto.getPersona() == null || arrendatarioDto.getPersona().getNif() == null
				|| arrendatarioDto.getPersona().getNif().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El campo nif es obligatorio.");
		} else if (null == arrendatarioDto.getPersona().getTipoPersona()
				|| arrendatarioDto.getPersona().getTipoPersona().equals("-1")) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Para persona es obligatorio el Tipo de persona.");
		} else if (TipoPersona.Fisica.getValorEnum().equals(arrendatarioDto.getPersona().getTipoPersona())
				&& (arrendatarioDto.getPersona().getFechaNacimiento() == null
						|| arrendatarioDto.getPersona().getFechaNacimiento().isfechaNula())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Para personas fisicas es obligatorio la fecha de nacimiento.");
		} else if (arrendatarioDto.getPersona().getDireccionDto() == null
				|| arrendatarioDto.getPersona().getDireccionDto().getIdTipoVia() == null
				|| arrendatarioDto.getPersona().getDireccionDto().getIdTipoVia().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El tipo de via es obligatorio.");
		} else if (arrendatarioDto.getPersona().getDireccionDto().getNombreVia() == null
				|| arrendatarioDto.getPersona().getDireccionDto().getNombreVia().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El nombre de via es obligatorio.");
		} else if (arrendatarioDto.getPersona().getDireccionDto().getNumero() == null
				|| arrendatarioDto.getPersona().getDireccionDto().getNumero().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El numero de via es obligatorio.");
		} else if (arrendatarioDto.getMatricula() == null || arrendatarioDto.getMatricula().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("La matrícula es obligatorio.");
		} else if (!UtilesValidaciones.validarMatricula(arrendatarioDto.getMatricula())) {
			result.setError(Boolean.TRUE);
			result.setMensaje(" La matrícula no tiene el formato correcto.");
		} else if (arrendatarioDto.getBastidor() == null || arrendatarioDto.getBastidor().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El bastidor es obligatorio.");
		} else if (!UtilesValidaciones.validarBastidor(arrendatarioDto.getBastidor())) {
			result.setError(Boolean.TRUE);
			result.setMensaje(" El bastidor no tiene el formato correcto.");
		} else if (arrendatarioDto.getFechaIni() == null || arrendatarioDto.getFechaIni().isfechaNula()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El tramite " + arrendatarioDto.getNumExpediente() + " Tiene que tener fecha de inicio.");
		}
		return result;
	}

	public void rellenarDatosGuardadoInicial(ArrendatarioDto arrendatarioDto, ArrendatarioVO arrendatarioVO, Date fecha)
			throws Exception {

		if (arrendatarioVO.getFechaAlta() == null) {
			arrendatarioVO.setFechaAlta(fecha);
		}
		if (arrendatarioDto.getNumExpediente() == null) {
			if (arrendatarioDto.getNumColegiado() != null && !arrendatarioDto.getNumColegiado().isEmpty()) {
				arrendatarioVO.setNumColegiado(arrendatarioDto.getNumColegiado());
			} else {
				ContratoDto contratoDto = servicioContrato
						.getContratoDto(arrendatarioDto.getContrato().getIdContrato());
				arrendatarioVO.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
			}
			if (TipoOperacionCaycEnum.Alta_Arrendatario.getValorEnum().equals(arrendatarioDto.getOperacion())) {
				arrendatarioVO.setNumExpediente(generarNumExpediente(arrendatarioVO.getNumColegiado(),
						TipoOperacionCaycEnum.Alta_Arrendatario.getTipo()));
			} else {
				arrendatarioVO.setNumExpediente(generarNumExpediente(arrendatarioVO.getNumColegiado(),
						TipoOperacionCaycEnum.Modif_Arrendatario.getTipo()));
			}
		}
		arrendatarioVO.setEstado(new BigDecimal(EstadoCaycEnum.Iniciado.getValorEnum()));
	}

	private BigDecimal generarNumExpediente(String numColegiado, String tipoOperacion) throws Exception {
		return arrendatarioDao.generarNumExpediente(numColegiado, tipoOperacion);
	}

	private void modificarDatos(ArrendatarioVO arrendatarioVO) {
		if (arrendatarioVO.getMatricula() != null && !arrendatarioVO.getMatricula().isEmpty()) {
			arrendatarioVO.setMatricula(arrendatarioVO.getMatricula().toUpperCase());
		}
		if (arrendatarioVO.getBastidor() != null && !arrendatarioVO.getBastidor().isEmpty()) {
			arrendatarioVO.setBastidor(arrendatarioVO.getBastidor().toUpperCase());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ArrendatarioDto getArrendatarioDtoPorIdDto(BigDecimal idArrendatario, Boolean tramiteCompleto) {
		try {
			if (idArrendatario != null) {
				ArrendatarioVO arrendatarioVO = getArrendatarioPorIdConsulta(idArrendatario, tramiteCompleto);
				if (arrendatarioVO != null) {
					ArrendatarioDto arrendatarioDto = conversor.transform(arrendatarioVO, ArrendatarioDto.class);
					arrendatarioDto.getPersona()
							.setDireccionDto(conversor.transform(arrendatarioVO.getDireccion(), DireccionDto.class));
					return arrendatarioDto;
				} else {
					log.error("No se han encontrado datos de arrendatario para el id: " + idArrendatario);
				}
			} else {
				log.error(
						"No se puede realizar una consulta a la BBDD con el id a null para las consultas de arrendatario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta de arrendatario con idArrendatario: "
					+ idArrendatario + " , error: ", e);
		}
		return null;
	}

	@Override
	public ArrendatarioVO getArrendatarioPorIdConsulta(BigDecimal idArrendatario, Boolean tramiteCompleto) {

		try {
			if (idArrendatario != null) {
				return arrendatarioDao.getArrendatarioPorId(idArrendatario.longValue(), tramiteCompleto);
			} else {
				log.error(
						"No se puede realizar una consulta a la BBDD con el id a null para las consultas de arrendatario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el arrendatario con idArrendatario: " + idArrendatario
					+ " , error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultConsultaArrendatarioBean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario,
			BigDecimal estadoNuevo, Boolean accionesAsociadas) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ArrendatarioVO arrendatarioVO = arrendatarioDao.getArrendatarioPorExpediente(numExpediente, false);
				if (arrendatarioVO != null) {
					BigDecimal estadoAnt = arrendatarioVO.getEstado();
					arrendatarioVO.setEstado(estadoNuevo);
					arrendatarioDao.actualizar(arrendatarioVO);
					// Actualizo evolucion
					servicioEvolucionCayc.guardarEvolucion(arrendatarioVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, estadoNuevo, TipoActualizacion.MOD.getValorEnum());
					if (accionesAsociadas) {
						resultado = accionesCambiarEstadoSinValidacion(arrendatarioVO, estadoAnt, estadoNuevo,
								idUsuario);
					}
					if (!resultado.getError()) {
						resultado.setMensaje("La consulta de Arrendatario con número de expediente: " + numExpediente
								+ ", se ha actualizado.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La consulta de Arrendatario con número de expediente: " + numExpediente
							+ ", no se ha podido cambiar el estado porque no se encuentra disponible.");
				}
			}
		} catch (Exception e) {
			log.error(
					"Ha sucedido un error a la hora de cambiar el estado de la consulta de Arrendatarios con numero de expediente: "
							+ numExpediente + ", error: ",
					e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de cambi	ar el estado de la consulta de Arrendatarios");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultConsultaArrendatarioBean accionesCambiarEstadoSinValidacion(ArrendatarioVO arrendatarioVO,
			BigDecimal estadoAnt, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			if (EstadoCaycEnum.Pdte_Respuesta_DGT.getValorEnum().equals(estadoAnt.toString())) {
				servicioCola.eliminar(new BigDecimal(arrendatarioVO.getIdArrendatario()),
						ProcesosEnum.ARRENDATARIOS.toString());
			}
		} catch (Exception e) {
			log.error(
					"Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de arrendatario con num.Expediente: "
							+ arrendatarioVO.getNumExpediente() + ", error: ",
					e);
			resultado.setError(true);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de arrendatario con num.Expediente: "
							+ arrendatarioVO.getNumExpediente() + ".");
		}
		return resultado;
	}

	private ResultConsultaArrendatarioBean guardarXml(byte[] xml, String subTipo, String nombreFichero,
			BigDecimal numExpediente) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
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
			log.error("Ha sucedido un error a la hora de guardar el xml para la solicitud de arrendatario, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el xml para la solicitud de arrendatario.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultConsultaArrendatarioBean guardarArrendatarioProceso(BigDecimal numExpediente, BigDecimal idUsuario,
			BigDecimal estadoNuevo, String respuesta, String numRegistro) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ArrendatarioVO arrendatarioVO = getArrendatarioPorExpVO(numExpediente, Boolean.TRUE);
				if (arrendatarioVO != null) {
					BigDecimal estadoAnt = arrendatarioVO.getEstado();
					arrendatarioVO.setEstado(estadoNuevo);
					arrendatarioVO.setRespuesta(respuesta);
					arrendatarioVO.setNumRegistro(numRegistro);
					arrendatarioDao.actualizar(arrendatarioVO);
					servicioEvolucionCayc.guardarEvolucion(arrendatarioVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, estadoNuevo, TipoActualizacion.MOD.getValorEnum());
				} else {
					log.error(
							"No se puede cambiar el estado porque no existen datos en la BBDD para el arrendatario con id: "
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

	@Override
	@Transactional
	public ResultConsultaArrendatarioBean modificarArrendatario(BigDecimal numExpediente, BigDecimal idUsuario,
			Date dateIni, Date dateFin) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			if (numExpediente != null && dateIni != null && dateFin != null) {
				ArrendatarioVO arrendatarioBBDD = getArrendatarioPorExpVO(numExpediente, Boolean.TRUE);
				if (arrendatarioBBDD != null) {
					if (EstadoCaycEnum.Finalizado.getValorEnum().equals(arrendatarioBBDD.getEstado().toString())) {
						ArrendatarioVO arrendatarioVO = construirArrendatarioModificacion(arrendatarioBBDD, dateIni,
								dateFin);
						resultado = realizarConsulta(arrendatarioVO, idUsuario);
						resultado.setNumExpediente(arrendatarioVO.getNumExpediente());
						// Meter Evolución
						servicioEvolucionCayc.guardarEvolucion(arrendatarioVO.getNumExpediente(), idUsuario.longValue(),
								new Date(), numExpediente, arrendatarioVO.getEstado(),
								TipoActualizacion.MFE.getValorEnum());
					} else {
						resultado.setError(true);
						resultado.setMensaje("El trámite con numero de expediente: " + numExpediente
								+ " debe estar en estado Finalizado para poder ser modificado.");
					}
				} else {
					resultado.setError(true);
					resultado.setMensaje("No se puede modificar el arrendatario porque no se encuentran datos"
							+ "para el tramite con numero de expediente: " + numExpediente.toString());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la consulta de arrendatario , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la consulta de arrendatario.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de generar la cola para la consulta de arrendatario, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la cola para la consulta de arrendatario.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ArrendatarioVO construirArrendatarioModificacion(ArrendatarioVO arrendatarioBBDD, Date dateIni,
			Date dateFin) throws Exception {

		ArrendatarioVO arrendatarioVO = conversor.transform(arrendatarioBBDD, ArrendatarioVO.class);
		// El numero de expediente será nuevo, las fechas y estado.
		arrendatarioVO.setNumExpediente(generarNumExpediente(arrendatarioVO.getNumColegiado(),
				TipoOperacionCaycEnum.Modif_Arrendatario.getTipo()));
		arrendatarioVO.setIdArrendatario(null);
		arrendatarioVO.setNumRegistro(null);
		arrendatarioVO.setRespuesta(null);
		arrendatarioVO.setFechaAlta(new Date());
		arrendatarioVO.setFechaIni(dateIni);
		arrendatarioVO.setFechaFin(dateFin);
		arrendatarioVO.setOperacion(TipoOperacionCaycEnum.Modif_Arrendatario.getValorEnum());
		return arrendatarioVO;
	}

}
