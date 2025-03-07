package org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.core.empresaDIRe.model.dao.EmpresaDIReDao;
import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIReVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.ServicioEmpresaDIRe;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ConsultaEmpresaDIReBean;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ResultConsultaEmpresaDIReBean;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.dto.EmpresaDIReDto;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.evolucionCayc.model.service.ServicioEvolucionCayc;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioEmpresaDIReImpl implements ServicioEmpresaDIRe {

	private static final long serialVersionUID = 7752268719631460462L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEmpresaDIReImpl.class);
	@Autowired
	Conversor conversor;
	@Autowired
	ServicioContrato servicioContrato;
	@Autowired
	EmpresaDIReDao empresaDIReDao;
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

	@Override
	@Transactional(readOnly = true)
	public ResultConsultaEmpresaDIReBean getEmpresaDIReDto(BigDecimal numExpediente) {
		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				EmpresaDIReVO empresaDIReVO = getEmpresaDIRePorExpVO(numExpediente, true);
				if (empresaDIReVO != null) {
					EmpresaDIReDto empresaDIReDto = conversor.transform(empresaDIReVO, EmpresaDIReDto.class);
					resultado.setNumExpediente(numExpediente);
					resultado.setEmpresaDIReDto(empresaDIReDto);
				} else {
					resultado.setError(true);
					resultado.setMensaje(
							"Ha sucedido un error a la hora de obtener el empresaDIReDto con numero de expediente: "
									+ numExpediente);
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje(
						"Ha sucedido un error a la hora de obtener el empresaDIReDto con numero de expediente: "
								+ numExpediente);
			}
		} catch (Exception e) {
			resultado.setError(true);
			resultado
					.setMensaje("Ha sucedido un error a la hora de obtener el empresaDIReDto con numero de expediente: "
							+ numExpediente);
			log.error("Ha sucedido un error a la hora de obtener el empresaDIReDto con numero de expediente: "
					+ numExpediente + " , error: ", e);
		}
		return resultado;
	}

	@Override
	@Transactional
	public void cambiarEstadoProceso(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo,
			String respuesta, String numRegistro) {
		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				EmpresaDIReVO empresaDIReVO = getEmpresaDIRePorExpVO(numExpediente, Boolean.TRUE);
				if (empresaDIReVO != null) {
					/*
					 * BigDecimal estadoAnt = empresaDIReVO.getEstado();
					 * empresaDIReVO.setEstado(estadoNuevo);
					 * empresaDIReVO.setRespuesta(respuesta);
					 * empresaDIReVO.setNumRegistro(numRegistro);
					 */
					empresaDIReDao.actualizar(empresaDIReVO);
					/*
					 * servicioEvolucionCayc.guardarEvolucion(empresaDIReVO.
					 * getNumExpediente(), idUsuario.longValue(), new Date(),
					 * estadoAnt, estadoNuevo,
					 * TipoActualizacion.MOD.getValorEnum());
					 */ } else {
					log.error(
							"No se puede cambiar el estado porque no existen datos en la BBDD para el arrendatario con numExpediente: "
									+ numExpediente.toString());
					resultado.setError(Boolean.TRUE);
				}
			} else {
				log.error("No se puede cambiar el estado porque el id de la consulta de EmpresaDIRe esta vacio.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del EmpresaDIRe con numExpediente:   "
					+ numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public EmpresaDIReVO getEmpresaDIRePorExpVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			if (numExpediente != null) {
				tramiteCompleto = false;
				return empresaDIReDao.getEmpresaDIRePorExpediente(numExpediente, tramiteCompleto);
			} else {
				log.error("No se puede realizar una consulta a la BBDD con el id a null para los arrendatarios.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el EmpresaDIRe con numExpediente: " + numExpediente
					+ " , error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultConsultaEmpresaDIReBean guardarEmpresaDIRe(EmpresaDIReDto empresaDIReDto, BigDecimal idUsuario) {
		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		try {
			resultado = validarDatosEmpresaDIRe(empresaDIReDto);
			if (!resultado.getError()) {
				Date fecha = new Date();
				BigDecimal estadoAnt = null;
				TipoActualizacion tipoActualizacion = null;
				EmpresaDIReVO empresaDIReVO = conversor.transform(empresaDIReDto, EmpresaDIReVO.class);
				if (empresaDIReDto.getNumExpediente() == null)
					rellenarDatosGuardadoInicial(empresaDIReDto, empresaDIReVO, fecha);
				if (empresaDIReDto.getEstado() != null && !empresaDIReDto.getEstado().isEmpty()) {
					estadoAnt = new BigDecimal(empresaDIReDto.getEstado());
				}
				tipoActualizacion = empresaDIReDto.getCodigoDire() != null ? TipoActualizacion.MOD : TipoActualizacion.CRE;

				modificarDatos(empresaDIReVO);
				// Guardamos
				resultado = guardar(empresaDIReDto, empresaDIReVO, idUsuario);

				resultado.setNumExpediente(empresaDIReDto.getNumExpediente());

				if (!resultado.getError()) {
					servicioEvolucionCayc.guardarEvolucion(empresaDIReVO.getNumExpediente(), idUsuario.longValue(),
							fecha, estadoAnt, empresaDIReVO.getEstado(), tipoActualizacion.getValorEnum());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el EmpresaDIRe , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el EmpresaDIRe.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultConsultaEmpresaDIReBean validarArrendatario(EmpresaDIReDto empresaDIReDto, BigDecimal idUsuario) {
		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		try {
			resultado = validarDatosArrendatario(empresaDIReDto);
			if (!resultado.getError()) {
				EmpresaDIReVO empresaDIReVO = conversor.transform(empresaDIReDto, EmpresaDIReVO.class);
				BigDecimal estadoAnt = empresaDIReVO.getEstado();
				empresaDIReVO.setEstado(new BigDecimal(EstadoCaycEnum.Validado.getValorEnum()));
				resultado = guardar(empresaDIReDto, empresaDIReVO, idUsuario);
				if (!resultado.getError()) {
					servicioEvolucionCayc.guardarEvolucion(empresaDIReVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, empresaDIReVO.getEstado(), TipoActualizacion.MOD.getValorEnum());
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

	private ResultConsultaEmpresaDIReBean guardar(EmpresaDIReDto empresaDIReDto, EmpresaDIReVO empresaDIReVO,
			BigDecimal idUsuario) {

		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		try {
			if (empresaDIReDto.getCodigoDire().length() < 5) {
				empresaDIReDto.setCodigoDire("");

				empresaDIReDto.setCodigoDire(
						empresaDIReDao.Generar_Numero_DIRe(empresaDIReDto.getPais() + empresaDIReDto.getNif()));
			}

			empresaDIReVO.setCodigoDire(empresaDIReDto.getCodigoDire());

			empresaDIReDao.guardarOActualizar(empresaDIReVO);
		} catch (Exception e) {
			resultado.setError(true);
			resultado.setMensaje("Error de BD al dar de alta EmpresaDIRe:" + e.getMessage());
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultConsultaEmpresaDIReBean consultarEmpresaDIRe(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				EmpresaDIReVO empresaDIReVO = getEmpresaDIRePorExpVO(numExpediente, Boolean.TRUE);
				BigDecimal estadoAnt = empresaDIReVO.getEstado();
				resultado = validarArrenadatarioConsulta(empresaDIReVO);
				if (!resultado.getError()) {
					modificarDatos(empresaDIReVO);
					// resultado = realizarConsulta(empresaDIReVO, idUsuario);
					if (!resultado.getError()) {
						servicioEvolucionCayc.guardarEvolucion(empresaDIReVO.getNumExpediente(), idUsuario.longValue(),
								new Date(), estadoAnt, empresaDIReVO.getEstado(), TipoActualizacion.MOD.getValorEnum());
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
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultConsultaEmpresaDIReBean validarArrenadatarioConsulta(EmpresaDIReVO empresaDIReVO) {
		ResultConsultaEmpresaDIReBean result = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		if (empresaDIReVO == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("No se encuentran datos para el Arrendatario");
		} else if (!EstadoCaycEnum.Validado.getValorEnum().equals(empresaDIReVO.getEstado().toString())) {
			result.setError(Boolean.TRUE);
			result.setMensaje("El tramite no está en estado validado con numero de expediente"
					+ empresaDIReVO.getNumExpediente().toString());
		}
		return result;
	}

	/**
	 * Valida los datos obligatorios para nuestra plataforma.
	 * 
	 * @param empresaDIReDto
	 * @return
	 */
	private ResultConsultaEmpresaDIReBean validarDatosEmpresaDIRe(EmpresaDIReDto empresaDIReDto) {
		ResultConsultaEmpresaDIReBean result = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		if (empresaDIReDto == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Debe de rellenar los datos obligatorios para poder realizar una alta de EmpresaDIRe.");
			return result;
		}
		if (empresaDIReDto.getContrato() != null && empresaDIReDto.getContrato().getIdContrato() == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Debe de seleccionar un contrato para poder realizar una alta de EmpresaDIRe.");
			return result;
		}

		if (empresaDIReDto.getNombre().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Es necesario el nombre de la empresa");
			return result;
		}
		if (empresaDIReDto.getDireccion().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Es necesario introducir la direccion");
			return result;
		}

		if (empresaDIReDto.getTelefono().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Es necesario introducir el telefono");
			return result;
		}

		if (empresaDIReDto.getEmail().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Es necesario introducir el Email");
			return result;
		}

		if (empresaDIReDto.getCodigoDire().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("ES necesario el código DIRe");
			return result;
		}

		return result;
	}

	/**
	 * Valida los datos una vez el tramite está guardado.
	 * 
	 * @param empresaDIReDto
	 * @return
	 */
	private ResultConsultaEmpresaDIReBean validarDatosArrendatario(EmpresaDIReDto empresaDIReDto) {
		return new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
	}

	public void rellenarDatosGuardadoInicial(EmpresaDIReDto empresaDIReDto, EmpresaDIReVO empresaDIReVO, Date fecha)
			throws Exception {

		if (empresaDIReVO.getFechaCreacion() == null) {
			empresaDIReVO.setFechaCreacion(fecha);
		}

		if (empresaDIReVO.getFechaActualizacion() == null) {
			empresaDIReVO.setFechaActualizacion(fecha);
		}

		if (empresaDIReDto.getContrato() != null) {

			ContratoDto contratoDto = servicioContrato.getContratoDto(empresaDIReDto.getContrato().getIdContrato());

			empresaDIReVO.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
		}
		// empresaDIReVO.setIdContrato(empresaDIReDto.getIdContrato());
		empresaDIReVO.setEstado(new BigDecimal(EstadoCaycEnum.Iniciado.getValorEnum()));

		empresaDIReVO.setNumExpediente(generarNumExpediente(empresaDIReVO.getNumColegiado(),
				TipoOperacionCaycEnum.Alta_EmpresaDIRe.getTipo()));
		empresaDIReDto.setNumExpediente(empresaDIReVO.getNumExpediente());
	}

	private BigDecimal generarNumExpediente(String numColegiado, String tipoOperacion) throws Exception {
		return empresaDIReDao.generarNumExpediente(numColegiado, tipoOperacion);
	}

	private void modificarDatos(EmpresaDIReVO empresaDIReVO) {
	}

	@Override
	@Transactional(readOnly = true)
	public EmpresaDIReDto getEmpresaDIRePorIdDto(BigDecimal idArrendatario, Boolean tramiteCompleto) {
		try {
			if (idArrendatario != null) {
				EmpresaDIReVO empresaDIReVO = getEmpresaDIRePorIdConsulta(idArrendatario, tramiteCompleto);
				if (empresaDIReVO != null) {
					return conversor.transform(empresaDIReVO, EmpresaDIReDto.class);
				} else {
					log.error("No se han encontrado datos de EmpresaDIRe para el id: " + idArrendatario);
				}
			} else {
				log.error(
						"No se puede realizar una consulta a la BBDD con el id a null para las consultas de arrendatario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta de EmpresaDIRe con idEmpresaDIRe: "
					+ idArrendatario + " , error: ", e);
		}
		return null;
	}

	@Override
	public EmpresaDIReVO getEmpresaDIRePorIdConsulta(BigDecimal idArrendatario, Boolean tramiteCompleto) {
		try {
			if (idArrendatario != null) {
				return empresaDIReDao.getEmpresaDIRePorId(idArrendatario.longValue(), tramiteCompleto);
			} else {
				log.error(
						"No se puede realizar una consulta a la BBDD con el id a null para las consultas de arrendatario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el EmpresaDIRe con idEmpresaDIRe: " + idArrendatario
					+ " , error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultConsultaEmpresaDIReBean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario,
			BigDecimal estadoNuevo, Boolean accionesAsociadas) {
		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				EmpresaDIReVO empresaDIReVO = empresaDIReDao.getEmpresaDIRePorExpediente(numExpediente, false);
				if (empresaDIReVO != null) {
					BigDecimal estadoAnt = empresaDIReVO.getEstado();
					empresaDIReVO.setEstado(estadoNuevo);
					empresaDIReDao.actualizar(empresaDIReVO);
					// Actualizo evolucion
					servicioEvolucionCayc.guardarEvolucion(empresaDIReVO.getNumExpediente(), idUsuario.longValue(),
							new Date(), estadoAnt, estadoNuevo, TipoActualizacion.MOD.getValorEnum());
					if (accionesAsociadas) {
						resultado = accionesCambiarEstadoSinValidacion(empresaDIReVO, estadoAnt);
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

	private ResultConsultaEmpresaDIReBean accionesCambiarEstadoSinValidacion(EmpresaDIReVO empresaDIReVO,
			BigDecimal estadoAnt) {
		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		try {
			if (EstadoCaycEnum.Pdte_Respuesta_DGT.getValorEnum().equals(estadoAnt.toString())) {
				/// OJO servicioCola.eliminar(new
				/// BigDecimal(empresaDIReVO.getIdArrendatario()),
				// ProcesosEnum.ARRENDATARIOS.toString());
			}
		} catch (Exception e) {
			log.error(
					"Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de arrendatario con num.Expediente: "
							+ empresaDIReVO.getNumExpediente() + ", error: ",
					e);
			resultado.setError(true);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de arrendatario con num.Expediente: "
							+ empresaDIReVO.getNumExpediente() + ".");
		}
		return resultado;
	}

	private ResultConsultaEmpresaDIReBean guardarXml(byte[] xml, String subTipo, String nombreFichero,
			BigDecimal numExpediente) {
		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
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
	public ResultConsultaEmpresaDIReBean guardarArrendatarioProceso(BigDecimal numExpediente, BigDecimal idUsuario,
			BigDecimal estadoNuevo, String respuesta, String numRegistro) {
		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				EmpresaDIReVO empresaDIReVO = getEmpresaDIRePorExpVO(numExpediente, Boolean.TRUE);
				if (empresaDIReVO != null) {
					BigDecimal estadoAnt = empresaDIReVO.getEstado();
					empresaDIReVO.setEstado(estadoNuevo);
					// empresaDIReVO.setRespuesta(respuesta);
					// empresaDIReVO.setNumRegistro(numRegistro);
					empresaDIReDao.actualizar(empresaDIReVO);
					servicioEvolucionCayc.guardarEvolucion(empresaDIReVO.getNumExpediente(), idUsuario.longValue(),
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
	public ResultConsultaEmpresaDIReBean modificarEmpresaDIRe(BigDecimal numExpediente, BigDecimal idUsuario,
			Date dateIni, Date dateFin) {
		ResultConsultaEmpresaDIReBean resultado = new ResultConsultaEmpresaDIReBean(Boolean.FALSE);
		try {
			if (numExpediente != null && dateIni != null && dateFin != null) {
				EmpresaDIReVO arrendatarioBBDD = getEmpresaDIRePorExpVO(numExpediente, Boolean.TRUE);
				if (arrendatarioBBDD != null) {
					if (EstadoCaycEnum.Finalizado.getValorEnum().equals(arrendatarioBBDD.getEstado().toString())) {
						EmpresaDIReVO empresaDIReVO = construirArrendatarioModificacion(arrendatarioBBDD);
						// resultado = realizarConsulta(empresaDIReVO,
						// idUsuario);
						resultado.setNumExpediente(empresaDIReVO.getNumExpediente());
						// Meter Evolución
						servicioEvolucionCayc.guardarEvolucion(empresaDIReVO.getNumExpediente(), idUsuario.longValue(),
								new Date(), numExpediente, empresaDIReVO.getEstado(),
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
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private EmpresaDIReVO construirArrendatarioModificacion(EmpresaDIReVO arrendatarioBBDD)
			throws Exception {

		EmpresaDIReVO empresaDIReVO = conversor.transform(arrendatarioBBDD, EmpresaDIReVO.class);
		// El número de expediente será nuevo, las fechas y estado.
		empresaDIReVO.setNumExpediente(generarNumExpediente(empresaDIReVO.getNumColegiado(),
				TipoOperacionCaycEnum.Modif_Arrendatario.getTipo()));
		/*
		 * empresaDIReVO.setIdArrendatario(null);
		 * empresaDIReVO.setNumRegistro(null); empresaDIReVO.setRespuesta(null);
		 * empresaDIReVO.setFechaAlta(new Date());
		 * empresaDIReVO.setFechaIni(dateIni);
		 * empresaDIReVO.setFechaFin(dateFin);
		 */
		empresaDIReVO.setOperacion(TipoOperacionCaycEnum.Modif_Arrendatario.getValorEnum());
		return empresaDIReVO;
	}

	@Override
	public ResultConsultaEmpresaDIReBean validarEmpresaDIRe(EmpresaDIReDto empresaDIReDto,
			BigDecimal idUsuarioDeSesion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultConsultaEmpresaDIReBean consultar(String codSeleccionados, BigDecimal idUsuarioDeSesion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultConsultaEmpresaDIReBean eliminar(String codSeleccionados, BigDecimal usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultConsultaEmpresaDIReBean cambiarEstado(String codSeleccionados, String estadoNuevo,
			BigDecimal idUsuarioDeSesion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultConsultaEmpresaDIReBean validar(String codSeleccionados, BigDecimal idUsuarioDeSesion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultConsultaEmpresaDIReBean modificarFechas(String codSeleccionados, BigDecimal idUsuarioDeSesion,
			Fecha fechaIniModificacion, Fecha fechaFinModificacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaEmpresaDIReBean> convertirListaEnBeanPantalla(List<EmpresaDIReVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<ConsultaEmpresaDIReBean> listaBean = new ArrayList<ConsultaEmpresaDIReBean>();
			for (EmpresaDIReVO arrendatarioVO : lista) {
				ConsultaEmpresaDIReBean empresaBean = conversor.transform(arrendatarioVO,
						ConsultaEmpresaDIReBean.class);

				listaBean.add(empresaBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}
}